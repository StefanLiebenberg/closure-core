package slieb.closure.core.general;


import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Immutable
public class FileResource implements Resource {

    private final File file;

    public FileResource(@Nonnull File file) {
        this.file = file;
    }

    @Nonnull
    public File getFile() {
        return file;
    }

    @Override
    @Nonnull
    public FileReader getReader() throws IOException {
        return new FileReader(file);
    }
}
