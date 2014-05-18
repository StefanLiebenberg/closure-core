package slieb.closure.dependency.javascript;


import com.google.common.collect.ImmutableSet;
import slieb.closure.dependency.DependencyNode;
import slieb.closure.resources.Resource;

import javax.annotation.concurrent.Immutable;

import static com.google.common.collect.ImmutableSet.copyOf;

@Immutable
public class ClosureDependencyNode extends DependencyNode {

    // is this resource the goog/base.js file
    private final Boolean isBase;

    public ClosureDependencyNode(Resource resource, ImmutableSet<String> provides, ImmutableSet<String> requires, Boolean base) {
        super(resource, provides, requires);
        isBase = base;
    }

    public Boolean isBase() {
        return isBase;
    }

    public static class Builder extends DependencyNode.Builder {

        protected Boolean isBase = false;

        public Builder(Resource resource) {
            super(resource);
        }

        public void setBase(Boolean base) {
            this.isBase = base;
        }

        public ClosureDependencyNode build() {
            return new ClosureDependencyNode(resource,
                    copyOf(provides), copyOf(requires), isBase);
        }

    }
}
