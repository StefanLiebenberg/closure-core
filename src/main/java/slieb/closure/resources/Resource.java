package slieb.closure.resources;


import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;

public interface Resource extends Serializable {

    /**
     * The resource provides a reader, but you have to close it.
     *
     * @return The reader to access this resource.
     * @throws IOException
     */
    @Nonnull
    public Reader getReader() throws IOException;
}
