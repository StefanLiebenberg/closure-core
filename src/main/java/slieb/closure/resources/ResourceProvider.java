package slieb.closure.resources;


import java.io.IOException;

public interface ResourceProvider {
    public Iterable<Resource> getResources() throws IOException;
}
