package slieb.closure.resources;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileResource that = (FileResource) o;

        if (!file.equals(that.file)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return file.hashCode();
    }
}
