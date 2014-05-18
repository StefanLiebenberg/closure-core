package slieb.closure.resources;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;


public class InputStreamResource implements Resource {

    private final InputStream inputStream;

    public InputStreamResource(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Nonnull
    @Override
    public Reader getReader() throws IOException {
        return new InputStreamReader(inputStream);
    }
}
