package slieb.closure.dependency.stylesheets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import slieb.closure.resources.Resource;
import slieb.closure.resources.ResourceProvider;
import slieb.closure.resources.StringResource;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StylesheetResourceProviderTest {


    @Mock
    private ResourceProvider mockResourceProvider;

    private StylesheetResourceProvider provider;

    @Before
    public void setup() {
        provider = new StylesheetResourceProvider(mockResourceProvider, newArrayList("entry-point"));

    }

    @Test
    public void testProvideCorrectResources() throws Exception {

        final Resource a, b, c, d;

        a = new StringResource("@provide entry-point; @require b;");
        b = new StringResource("@provide b; @require c;");
        c = new StringResource("@provide c;");
        d = new StringResource("@provide d;");

        Iterable<Resource> resources = newArrayList(a, c, d, b);

        when(mockResourceProvider.getResources()).thenReturn(resources);

        List<Resource> dependencyList = provider.getResources();

        assertEquals(3, dependencyList.size());
        assertEquals(a, dependencyList.get(2));
        assertEquals(b, dependencyList.get(1));
        assertEquals(c, dependencyList.get(0));

    }
}
