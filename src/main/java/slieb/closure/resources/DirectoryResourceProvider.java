package slieb.closure.resources;


import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.apache.commons.io.FileUtils.iterateFiles;

/**
 * The DirectoryResourceProvider scans a set of source directories and
 * returns all files found.
 */
@Immutable
public final class DirectoryResourceProvider implements ResourceProvider {

    private final Set<File> sourceRoots;

    private final String[] extensions;

    /**
     * @param sourceRoots
     * @param extensions
     */
    public DirectoryResourceProvider(@Nonnull Set<File> sourceRoots,
                                     @Nonnull String[] extensions) {
        this.sourceRoots = sourceRoots;
        this.extensions = extensions;
    }

    @Nonnull
    public Iterable<File> getFiles() throws IOException {
        Set<File> files = new HashSet<>();
        for (File sourceRoot : sourceRoots) {
            Iterator<File> iterator = iterateFiles(sourceRoot, extensions, true);
            while (iterator.hasNext()) {
                files.add(iterator.next());
            }

        }
        return files;
    }

    @Override
    public Set<Resource> getResources() throws IOException {
        Set<Resource> resources = new HashSet<>();
        for (File file : getFiles()) {
            resources.add(new FileResource(file));
        }
        return resources;
    }
}
