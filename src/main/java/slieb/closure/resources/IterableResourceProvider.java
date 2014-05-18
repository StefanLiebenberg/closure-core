package slieb.closure.resources;


import javax.annotation.Nonnull;
import java.io.IOException;

public class IterableResourceProvider implements ResourceProvider {

    private final Iterable<Resource> resources;

    public IterableResourceProvider(@Nonnull Iterable<Resource> resources) {
        this.resources = resources;
    }

    @Override
    public Iterable<Resource> getResources() throws IOException {
        return resources;
    }
}
