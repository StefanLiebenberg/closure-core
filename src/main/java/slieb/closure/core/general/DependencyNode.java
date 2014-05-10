package slieb.closure.core.general;

import com.google.common.collect.ImmutableSet;

import javax.annotation.concurrent.Immutable;
import java.util.Set;

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
}
