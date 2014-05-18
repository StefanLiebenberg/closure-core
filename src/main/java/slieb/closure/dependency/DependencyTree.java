package slieb.closure.dependency;


import com.google.common.collect.ImmutableMap;

import javax.annotation.concurrent.Immutable;
import java.util.Collection;
import java.util.HashMap;

@Immutable
public class DependencyTree {

    private final ImmutableMap<String, DependencyNode> dependencyNodesMap;

    public DependencyTree(ImmutableMap<String, DependencyNode> dependencyNodesMap) {
        this.dependencyNodesMap = dependencyNodesMap;
    }

    public DependencyNode getProviderNode(String namespace) {
        return dependencyNodesMap.get(namespace);
    }

    public static class Builder {

        private HashMap<String, DependencyNode> hashMap = new HashMap<>();

        public void add(DependencyNode dependencyNode) {
            for (String provideName : dependencyNode.getProvides()) {
                if (hashMap.containsKey(provideName)) {
                    throw new RuntimeException("Already provides's " + provideName);
                }
                hashMap.put(provideName, dependencyNode);
            }
        }

        public void addAll(Collection<DependencyNode> dependencyNodes) {
            for (DependencyNode dependencyNode : dependencyNodes) {
                add(dependencyNode);
            }
        }

        public DependencyTree build() {
            return new DependencyTree(
                    new ImmutableMap.Builder<String, DependencyNode>()
                            .putAll(hashMap)
                            .build());
        }

    }
}
