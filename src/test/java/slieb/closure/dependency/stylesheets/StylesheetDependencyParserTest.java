package slieb.closure.dependency.stylesheets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import slieb.closure.dependency.DependencyNode;
import slieb.closure.resources.InputStreamResource;
import slieb.closure.resources.StringResource;

import java.io.InputStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class StylesheetDependencyParserTest {
    public StylesheetDependencyParser parser;

    @Before
    public void setupParser() {
        parser = new StylesheetDependencyParser();
    }

    @Test
    public void testParseProvides() throws Throwable {
        DependencyNode node = parser.parse(
                new StringResource(
                        new StringBuilder()
                                .append("@provide abc;")
                                .toString()));
        assertTrue(node.getProvides().contains("abc"));
    }

    @Test
    public void testParseLessExample() throws Throwable {
        InputStream inputStream = getClass().getResourceAsStream("/stylesheets/less-example.less");
        InputStreamResource resource = new InputStreamResource(inputStream);
        DependencyNode node = parser.parse(resource);
        assertTrue(node.getProvides().contains("less-example"));
        assertTrue(node.getRequires().contains("base-css"));
    }


    @Test
    public void testParseCssExample() throws Throwable {
        InputStream inputStream = getClass().getResourceAsStream("/stylesheets/base.css");
        InputStreamResource resource = new InputStreamResource(inputStream);
        DependencyNode node = parser.parse(resource);
        assertTrue(node.getProvides().contains("base-css"));
        assertFalse(node.getRequires().contains("does-not-exist"));
    }

    @Test
    public void testParseRequires() throws Throwable {
        DependencyNode node = parser.parse(
                new StringResource(
                        new StringBuilder()
                                .append("@require the-children;\n")
                                .toString()));
        assertTrue(node.getRequires().contains("the-children"));
    }
}
