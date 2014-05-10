package slieb.closure.core.general;


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
    public StringReader getReader() {
        return new StringReader(content);
    }
}
