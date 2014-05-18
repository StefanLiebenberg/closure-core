package slieb.closure.dependency.javascript;


import slieb.closure.dependency.DependencyCalculator;
import slieb.closure.dependency.DependencyExceptionHandler;
import slieb.closure.dependency.DependencyResourceProvider;
import slieb.closure.dependency.DependencyScanner;
import slieb.closure.resources.Resource;
import slieb.closure.resources.ResourceProvider;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

public class ClosureResourceProvider implements ResourceProvider {

    private static final DependencyExceptionHandler exceptionHandler = new DependencyExceptionHandler();
    private static final DependencyCalculator calculator = new DependencyCalculator(exceptionHandler);
    private static final ClosureDependencyParser parser = new ClosureDependencyParser();

    private DependencyResourceProvider dependencyResourceProvider;

    public ClosureResourceProvider(final @Nonnull ResourceProvider resourceProvider,
                                   final @Nonnull List<String> entryPoints) {
        this.dependencyResourceProvider =
                new DependencyResourceProvider(
                        new DependencyScanner(parser, resourceProvider), calculator, entryPoints);
    }

    @Override
    public Iterable<Resource> getResources() throws IOException {
        return this.dependencyResourceProvider.getResources();
    }
}
