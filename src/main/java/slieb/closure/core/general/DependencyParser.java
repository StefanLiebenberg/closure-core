package slieb.closure.core.general;


import javax.annotation.Nonnull;

public interface DependencyParser {
    public DependencyNode parse(@Nonnull Resource resource) throws DependencyException;
}

