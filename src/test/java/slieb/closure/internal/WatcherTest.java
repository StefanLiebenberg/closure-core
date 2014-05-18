package slieb.closure.internal;

import org.junit.Test;
import slieb.closure.resources.Resource;
import slieb.closure.resources.ResourceProvider;
import slieb.closure.resources.ResourceWatcher;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WatcherTest {

    private class MutableStringResource implements Resource {

        private String content = null;


        public MutableStringResource setContent(String content) {
            this.content = content;
            return this;
        }

        @Nonnull
        @Override
        public Reader getReader() throws IOException {
            return new StringReader(content);
        }
    }

    private class MutableIterableResourceProvider implements ResourceProvider {

        private Iterable<Resource> resources;

        private MutableIterableResourceProvider setResources(Iterable<Resource> resources) {
            this.resources = resources;
            return this;
        }

        @Override
        public Iterable<Resource> getResources() throws IOException {
            return resources;
        }
    }

    private MutableStringResource createStringResource(String content) {
        return new MutableStringResource().setContent(content);
    }


    @Test
    public void testScan() throws Exception {

        Resource a, b, c;

        a = createStringResource("some content for a");
        b = createStringResource("content for b");
        c = createStringResource("content for c");

        MutableIterableResourceProvider provider = new MutableIterableResourceProvider();
        provider.setResources(newHashSet(a, b, c));

        ResourceWatcher watcher = new ResourceWatcher(provider);

        assertTrue(watcher.scan());
        assertFalse(watcher.scan());

        provider.setResources(newHashSet(a, b));
        assertTrue(watcher.scan());
        assertFalse(watcher.scan());

        provider.setResources(newHashSet(a, b, c));
        assertTrue(watcher.scan());
        assertFalse(watcher.scan());

        ((MutableStringResource) a).setContent("Hi!");
        assertTrue(watcher.scan());

    }
}
