package slieb.closure.core.javascript;


import com.google.javascript.rhino.head.Node;
import com.google.javascript.rhino.head.Parser;
import com.google.javascript.rhino.head.ast.*;
import slieb.closure.core.general.DependencyException;
import slieb.closure.core.general.DependencyParser;
import slieb.closure.core.general.Resource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Reader;

public class ClosureDependencyParser implements DependencyParser {

    @Override
    public ClosureDependencyNode parse(@Nonnull Resource resource) throws DependencyException {
        try {
            ClosureDependencyNode.Builder builder = new ClosureDependencyNode.Builder();
            Reader reader = resource.getReader();
            Parser parser = new Parser();
            visit(builder, parser.parse(reader, "resource", 0).getFirstChild());
            reader.close();
            return builder.build();
        } catch (IOException e) {
            throw new DependencyException(e);
        }
    }

    private boolean isName(@Nonnull final AstNode node) {
        return node instanceof Name;
    }

    private boolean isName(@Nonnull final AstNode node, @Nonnull final String name) {
        return isName(node) && ((Name) node).getIdentifier().equals(name);
    }

    private boolean isPropertyGet(@Nonnull final AstNode node) {
        return node instanceof PropertyGet;
    }

    private boolean hasNamedProperty(@Nonnull final PropertyGet node,
                                     @Nonnull final String left,
                                     @Nonnull final String right) {
        return isName(node.getLeft(), left) && isName(node.getRight(), right);
    }


    private void visit_assignment(@Nonnull final ClosureDependencyNode.Builder builder,
                                  @Nonnull final Assignment assignment) {
        final AstNode left = assignment.getLeft();
        if (isPropertyGet(left) &&
                hasNamedProperty((PropertyGet) left, "goog", "base")) {
            builder.setBase(true);
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private <T extends AstNode> T getArgument(
            @Nonnull final FunctionCall functionCall,
            @Nonnull final Integer index,
            @Nonnull final Class<T> tClass) {
        final AstNode node = functionCall.getArguments().get(index);
        if (tClass.isInstance(node)) {

            return (T) node;
        } else {
            return null;
        }
    }

    @Nullable
    private String getStringArgumentValue(
            @Nonnull final FunctionCall functionCall,
            @Nonnull final Integer index) {
        final StringLiteral stringNode =
                getArgument(functionCall, index, StringLiteral.class);
        if (stringNode != null) {
            return stringNode.getValue();
        } else {
            return null;
        }

    }


    private void visit_PropertyGet_googScan(
            @Nonnull final ClosureDependencyNode.Builder builder,
            @Nonnull final FunctionCall funcCall,
            @Nonnull final PropertyGet propertyGet) {
        if (isName(propertyGet.getLeft(), "goog")) {
            final AstNode right = propertyGet.getRight();
            if (isName(right, "provide")) {
                builder.addProvide(
                        getStringArgumentValue(funcCall, 0));
            } else if (isName(right, "require")) {
                builder.addRequire(
                        getStringArgumentValue(funcCall, 0));
            }
        }
    }

    private void visit_FunctionCall(
            @Nonnull final ClosureDependencyNode.Builder builder,
            @Nonnull final FunctionCall functionCall) {
        final AstNode target = functionCall.getTarget();
        if (isPropertyGet(target)) {
            visit_PropertyGet_googScan(builder, functionCall,
                    (PropertyGet) target);
        }
    }

    private void visit(@Nonnull final ClosureDependencyNode.Builder builder,
                       @Nullable final Node node) {
        if (node == null) {
            return;
        }

        if (node instanceof FunctionCall) {
            visit_FunctionCall(builder, (FunctionCall) node);
            visit(builder, node.getNext());
        }

        if (!builder.getIsBase()) {

            if (node instanceof ExpressionStatement) {
                visit(builder, ((ExpressionStatement) node).getExpression());
                visit(builder, node.getNext());
                return;
            }

            if (node instanceof Assignment) {
                visit_assignment(builder, (Assignment) node);
                visit(builder, node.getNext());
                return;
            }
        }
        visit(builder, node.getNext());
    }
}
