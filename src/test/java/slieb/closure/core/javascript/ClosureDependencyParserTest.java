package slieb.closure.core.javascript;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import slieb.closure.core.general.StringResource;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ClosureDependencyParserTest {

    private ClosureDependencyParser parser;

    @Before
    public void setupParser() {
        parser = new ClosureDependencyParser();
    }

    @Test
    public void testParseProvide() throws Throwable {

        ClosureDependencyNode node = parser.parse(
                new StringResource(new StringBuilder()
                        .append("goog.provide('nodeX');")
                        .toString()));

        assertTrue(node.getProvides().contains("nodeX"));
    }

    @Test
    public void testParseRequire() throws Throwable {
        ClosureDependencyNode node = parser.parse(
                new StringResource(new StringBuilder()
                        .append("goog.require('nodeX');")
                        .toString()));

        assertTrue(node.getRequires().contains("nodeX"));
    }

    @Test
    public void testParseIsBase() throws Throwable {
        ClosureDependencyNode node = parser.parse(
                new StringResource(new StringBuilder()
                        .append("goog.base = function () {};")
                        .toString()));
        assertTrue(node.getBase());
    }

    @Test
    public void testMultipleParses() throws Throwable {
        parser.parse(new StringResource(new StringBuilder()
                        .append("var a = red;")
                        .toString()));

        parser.parse(new StringResource(new StringBuilder()
                .append("var a = blue;")
                .toString()));
    }
}
