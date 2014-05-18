package slieb.closure.dependency.stylesheets;


import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import slieb.closure.dependency.DependencyException;
import slieb.closure.dependency.DependencyNode;
import slieb.closure.dependency.DependencyParser;
import slieb.closure.resources.Resource;

import javax.annotation.Nonnull;
import java.io.Reader;

public class StylesheetDependencyParser implements DependencyParser {

    private void visit_provide_context(CssDependencyGrammarParser.ProvideContext provideContext,
                                       DependencyNode.Builder builder) {
        builder.addProvide(provideContext.namespace().getText());
    }

    private void visit_require_context(CssDependencyGrammarParser.RequireContext requireContext,
                                       DependencyNode.Builder builder) {
        builder.addRequire(requireContext.namespace().NAME().getText());
    }


    private void visit_expression(CssDependencyGrammarParser.ExpressionContext expressionContext,
                                  DependencyNode.Builder builder) {

        CssDependencyGrammarParser.ProvideContext provideContext = expressionContext.provide();
        if (provideContext != null) {
            visit_provide_context(provideContext, builder);
        }


        CssDependencyGrammarParser.RequireContext requireContext = expressionContext.require();
        if (requireContext != null) {
            visit_require_context(requireContext, builder);
        }

    }

    private void visit(CssDependencyGrammarParser.StylesheetContext stylesheetContext, DependencyNode.Builder builder) {
        for (CssDependencyGrammarParser.ExpressionContext expressionContext : stylesheetContext.expression()) {
            visit_expression(expressionContext, builder);
        }
    }


    @Override
    public DependencyNode parse(@Nonnull Resource resource) throws DependencyException {
        try {
            Reader reader = resource.getReader();
            ANTLRInputStream readerStream = new ANTLRInputStream(reader);
            Lexer lexer = new CssDependencyGrammarLexer(readerStream);
            TokenStream tokenStream = new CommonTokenStream(lexer);
            CssDependencyGrammarParser parser = new CssDependencyGrammarParser(tokenStream);
            DependencyNode.Builder builder = new DependencyNode.Builder(resource);
            CssDependencyGrammarParser.StylesheetContext stylesheetContext = parser.stylesheet();
            if (stylesheetContext != null) {
                visit(stylesheetContext, builder);
            }
            return builder.build();
        } catch (Exception e) {
            throw new DependencyException(e);
        }
    }
}
