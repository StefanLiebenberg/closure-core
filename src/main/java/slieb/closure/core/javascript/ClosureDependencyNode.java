package slieb.closure.core.javascript;


import com.google.common.collect.ImmutableSet;
import slieb.closure.core.general.DependencyNode;
import slieb.closure.core.general.Resource;

import javax.annotation.concurrent.Immutable;
import java.util.HashSet;
import java.util.Set;

@Immutable
public class ClosureDependencyNode extends DependencyNode {

    // is this resource the goog/base.js file
    private final Boolean isBase;

    public ClosureDependencyNode(Resource resource, ImmutableSet<String> provides, ImmutableSet<String> requires, Boolean base) {
        super(resource, provides, requires);
        isBase = base;
    }

    public Boolean getBase() {
        return isBase;
    }

    public static class Builder {
        private Resource resource;
        private boolean isBase;
        private Set<String> provides = new HashSet<>(), requires = new HashSet<>();

        public void setResource(Resource resource) {
            this.resource = resource;
        }

        protected boolean getIsBase() {
            return isBase;
        }

        public void setBase(boolean base) {
            isBase = base;
        }

        public void addProvide(String provide) {
            this.provides.add(provide);
        }

        public void addRequire(String require) {
            this.requires.add(require);
        }

        public ClosureDependencyNode build() {
            return new ClosureDependencyNode(resource,
                    new ImmutableSet.Builder<String>().addAll(provides).build(),
                    new ImmutableSet.Builder<String>().addAll(requires).build(),
                    isBase);
        }
    }
}
