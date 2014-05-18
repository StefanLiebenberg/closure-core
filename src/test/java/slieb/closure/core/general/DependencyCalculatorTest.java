package slieb.closure.core.general;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import slieb.closure.dependency.*;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DependencyCalculatorTest {

    private final DependencyExceptionHandler dependencyExceptionHandler =
            new DependencyExceptionHandler();

    private DependencyCalculator dependencyCalculator;

    @Mock
    private DependencyTree mockDependencyTree;

    @Mock
    private DependencyNode mockNodeA, mockNodeB, mockNodeC, mockNodeD, mockNodeE;

    @Before
    public void setupCalculator() {
        dependencyCalculator = new DependencyCalculator(dependencyExceptionHandler);
    }

    public void setupMocks() {
        when(mockNodeA.getProvides()).thenReturn(newHashSet("nodeA"));
        when(mockNodeB.getProvides()).thenReturn(newHashSet("nodeB"));
        when(mockNodeC.getProvides()).thenReturn(newHashSet("nodeC"));
        when(mockNodeD.getProvides()).thenReturn(newHashSet("nodeD"));
        when(mockNodeE.getProvides()).thenReturn(newHashSet("nodeE"));
    }

    @Test
    public void testCalculateSimpleCase() throws Throwable {
        when(mockNodeB.getRequires()).thenReturn(newHashSet("nodeA"));
        when(mockDependencyTree.getProviderNode("nodeA")).thenReturn(mockNodeA);
        when(mockDependencyTree.getProviderNode("nodeB")).thenReturn(mockNodeB);
        List<DependencyNode> nodes = dependencyCalculator.calculate(mockDependencyTree, newArrayList(mockNodeB));
        assertEquals(mockNodeA, nodes.get(0));
        assertEquals(mockNodeB, nodes.get(1));
    }


    @Test(expected = DependencyException.class)
    public void testCalculateCircularError() throws Throwable {
        when(mockNodeA.getRequires()).thenReturn(newHashSet("nodeB"));
        when(mockNodeB.getRequires()).thenReturn(newHashSet("nodeA"));
        when(mockDependencyTree.getProviderNode("nodeA")).thenReturn(mockNodeA);
        when(mockDependencyTree.getProviderNode("nodeB")).thenReturn(mockNodeB);
        dependencyCalculator.calculate(mockDependencyTree, newArrayList(mockNodeB));
    }

    @Test(expected = DependencyException.class)
    public void testCalculateNothingProvides() throws Throwable {
        when(mockNodeA.getRequires()).thenReturn(newHashSet("nodeA"));
        dependencyCalculator.calculate(mockDependencyTree, newArrayList(mockNodeA));
    }

}
