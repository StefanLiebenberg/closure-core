package slieb.closure.core.general;


import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Reader;

public interface Resource {

    /**
     * @return The reader to access this resource.
     * @throws IOException
     */
    @Nonnull
    public Reader getReader() throws IOException;
}
