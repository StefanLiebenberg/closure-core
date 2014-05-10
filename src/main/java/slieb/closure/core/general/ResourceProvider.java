package slieb.closure.core.general;


import java.io.IOException;

public interface ResourceProvider {
    public Iterable<Resource> getResources() throws IOException;
}
