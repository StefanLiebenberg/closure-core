package slieb.closure.dependency;


import slieb.closure.resources.Resource;
import slieb.closure.resources.ResourceProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DependencyResourceProvider implements ResourceProvider {

    private final DependencyScanner scanner;

    private final DependencyCalculator calculator;

    private final List<String> entryPoints;

    public DependencyResourceProvider(DependencyScanner scanner, DependencyCalculator calculator, List<String> entryPoints) {
        this.scanner = scanner;
        this.calculator = calculator;
        this.entryPoints = entryPoints;
    }

    @Override
    public List<Resource> getResources() throws IOException {
        try {
            final List<Resource> resources = new ArrayList<>();
            for (DependencyNode node : calculator.calculateFromEntryPoints(scanner.scan(), entryPoints)) {
                resources.add(node.getResource());
            }
            return resources;
        } catch (DependencyException e) {
            throw new RuntimeException(e);
        }
    }
}
