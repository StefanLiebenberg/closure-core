package slieb.closure.core.general;


import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class DependencyCalculator {

    private final DependencyExceptionHandler dependencyExceptionHandler;

    public DependencyCalculator(DependencyExceptionHandler dependencyExceptionHandler) {
        this.dependencyExceptionHandler = dependencyExceptionHandler;
    }

    private List<DependencyNode> getDependencyNodes(final DependencyTree dependencyTree,
                                                    final Collection<String> entryPoints)
            throws DependencyException {

        final List<DependencyNode> nodes = new ArrayList<>();
        for (String entry : entryPoints) {
            DependencyNode dependencyNode = dependencyTree.getProviderNode(entry);

            if (dependencyNode == null) {
                throw dependencyExceptionHandler.nothingProvides(entry);
            }

            if (!nodes.contains(dependencyNode)) {
                nodes.add(dependencyNode);
            }

        }
        return nodes;
    }


    @Nonnull
    private List<DependencyNode> resolveDependencies(
            @Nonnull DependencyTree dependencyTree,
            @Nonnull final DependencyNode providerNode,
            @Nonnull final List<DependencyNode> dependencies,
            @Nonnull final Collection<DependencyNode> parents)
            throws DependencyException {

        if (!dependencies.contains(providerNode)) {
            parents.add(providerNode);
            for (DependencyNode dependencyNode : getDependencyNodes(dependencyTree, providerNode.getRequires())) {

                if (parents.contains(dependencyNode)) {
                    throw dependencyExceptionHandler.circularDependencies(dependencyNode, parents);
                }

                resolveDependencies(dependencyTree, dependencyNode, dependencies, parents);
            }
            parents.remove(providerNode);
            dependencies.add(providerNode);
        }

        return dependencies;
    }


    public ImmutableList<DependencyNode> calculate(DependencyTree dependencyTree, List<DependencyNode> entryNodes) throws DependencyException {
        final List<DependencyNode> nodes = new ArrayList<>();
        for (DependencyNode entryNode : entryNodes) {
            for (DependencyNode node : resolveDependencies(dependencyTree, entryNode, nodes, new HashSet<DependencyNode>())) {
                if (!nodes.contains(node)) {
                    nodes.add(node);
                }
            }
        }
        return new ImmutableList.Builder<DependencyNode>().addAll(nodes).build();
    }

    public ImmutableList<DependencyNode> calculateFromEntryPoints(DependencyTree dependencyTree, List<String> entryPoints) throws DependencyException {
        return calculate(dependencyTree, getDependencyNodes(dependencyTree, entryPoints));
    }
}
