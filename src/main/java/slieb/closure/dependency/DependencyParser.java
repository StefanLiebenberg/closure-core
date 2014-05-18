package slieb.closure.dependency;


import slieb.closure.resources.Resource;

import javax.annotation.Nonnull;

public interface DependencyParser {
    public DependencyNode parse(@Nonnull Resource resource) throws DependencyException;
}

