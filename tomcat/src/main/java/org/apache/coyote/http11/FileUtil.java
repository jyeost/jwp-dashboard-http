package org.apache.coyote.http11;

import nextstep.jwp.exception.UncheckedServletException;
import org.apache.coyote.common.PathUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);
    private static final URL NOT_FOUND_RESOURCE = FileUtil.class.getClassLoader().getResource("static/404.html");

    private FileUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String getResource(final PathUrl resourceUrl){
        final URL resource = FileUtil.class.getClassLoader().getResource("static" + resourceUrl);

        if(Objects.isNull(resource)){
            assert NOT_FOUND_RESOURCE != null;
            return readResource(NOT_FOUND_RESOURCE);
        }
        return readResource(resource);
    }

    private static String readResource(final URL resource) {

        final Path path = Paths.get(resource.getPath());
        try (final BufferedReader fileReader = new BufferedReader(new FileReader(path.toFile()))) {

            final StringBuilder actual = new StringBuilder();
            fileReader.lines()
                    .forEach(br -> actual.append(br)
                            .append(System.lineSeparator()));

            return actual.toString();

        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }

        return "";
    }
}