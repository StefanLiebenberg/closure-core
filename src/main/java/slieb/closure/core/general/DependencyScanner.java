package slieb.closure.core.general;


import java.io.IOException;

public class DependencyScanner {

    private final DependencyParser dependencyParser;

    private final ResourceProvider resourceProvider;

    public DependencyScanner(DependencyParser dependencyParser,
                             ResourceProvider resourceProvider) {
        this.dependencyParser = dependencyParser;
        this.resourceProvider = resourceProvider;
    }


    public DependencyTree scan() throws DependencyException {
        try {
            DependencyTree.Builder depTree = new DependencyTree.Builder();
            for (Resource resource : resourceProvider.getResources()) {
                depTree.add(dependencyParser.parse(resource));
            }
            return depTree.build();
        } catch (IOException e) {
            throw new DependencyException(e);
        }
    }

}
