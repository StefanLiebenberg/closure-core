package slieb.closure.dependency;


import java.util.Collection;

public class DependencyExceptionHandler {

    public DependencyException nothingProvides(String namespace) {
        return new DependencyException();
    }

    public DependencyException circularDependencies(DependencyNode namespace, Collection parents) {
        return new DependencyException();
    }
}
