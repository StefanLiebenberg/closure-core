package slieb.closure.dependency;

import com.google.common.collect.ImmutableSet;
import slieb.closure.resources.Resource;

import javax.annotation.concurrent.Immutable;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.ImmutableSet.copyOf;

@Immutable
public class DependencyNode {

    private final Resource resource;

    private final ImmutableSet<String> provides, requires;

    public DependencyNode(Resource resource, ImmutableSet<String> provides, ImmutableSet<String> requires) {
        this.resource = resource;
        this.provides = provides;
        this.requires = requires;
    }

    public Resource getResource() {
        return resource;
    }

    public Set<String> getProvides() {
        return provides;
    }

    public Set<String> getRequires() {
        return requires;
    }

    public static class Builder {

        protected final Set<String>
                provides = new HashSet<>(),
                requires = new HashSet<>();

        protected final Resource resource;

        public Builder(Resource resource) {
            this.resource = resource;
        }

        public synchronized Builder addProvide(String provide) {
            if (!provides.contains(provide)) {
                provides.add(provide);
            }
            return this;
        }

        public synchronized Builder addRequire(String require) {
            if (!requires.contains(require)) {
                requires.add(require);
            }
            return this;
        }

        public synchronized DependencyNode build() {
            return new DependencyNode(resource, copyOf(provides), copyOf(requires));
        }
    }
}
