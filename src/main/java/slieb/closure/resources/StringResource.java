package slieb.closure.resources;


import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.io.StringReader;

@Immutable
public class StringResource implements Resource {

    private final String content;

    public StringResource(@Nonnull String content) {
        this.content = content;
    }

    @Nonnull
    public String getContent() {
        return content;
    }

    @Override
    @Nonnull
    public StringReader getReader() {
        return new StringReader(content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringResource that = (StringResource) o;

        if (!content.equals(that.content)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}
