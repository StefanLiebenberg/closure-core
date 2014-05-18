package slieb.closure.core.general;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import slieb.closure.dependency.DependencyNode;
import slieb.closure.dependency.DependencyParser;
import slieb.closure.dependency.DependencyScanner;
import slieb.closure.dependency.DependencyTree;
import slieb.closure.resources.Resource;
import slieb.closure.resources.ResourceProvider;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DependencyScannerTest {

    @Mock
    private DependencyParser mockDependencyParser;

    @Mock
    private ResourceProvider mockResourceProvider;

    @Mock
    private Resource mockResourceA, mockResourceB, mockResourceC;

    @Mock
    private DependencyNode mockNodeA, mockNodeB, mockNodeC;

    private DependencyScanner scanner;


    @Before
    public void setupScanner() throws Throwable {
        when(mockNodeA.getProvides()).thenReturn(newHashSet("nodeA"));
        when(mockNodeB.getProvides()).thenReturn(newHashSet("nodeB", "extraSnowflake"));
        when(mockNodeC.getProvides()).thenReturn(newHashSet("nodeC"));
        when(mockDependencyParser.parse(mockResourceA)).thenReturn(mockNodeA);
        when(mockDependencyParser.parse(mockResourceB)).thenReturn(mockNodeB);
        when(mockDependencyParser.parse(mockResourceC)).thenReturn(mockNodeC);
        scanner = new DependencyScanner(mockDependencyParser, mockResourceProvider);
    }

    @Test
    public void testScan() throws Throwable {
        Iterable<Resource> resources = newArrayList(mockResourceA, mockResourceB, mockResourceC);
        when(mockResourceProvider.getResources()).thenReturn(resources);
        final DependencyTree dependencyTree = scanner.scan();
        assertEquals(mockNodeA, dependencyTree.getProviderNode("nodeA"));
        assertEquals(mockNodeB, dependencyTree.getProviderNode("nodeB"));
        assertEquals(mockNodeC, dependencyTree.getProviderNode("nodeC"));
        assertEquals(mockNodeB, dependencyTree.getProviderNode("extraSnowflake"));
    }
}
