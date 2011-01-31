package masterjava.common.web2.util;

import org.apache.log4j.Logger;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @author GKislin
 *         Date: 20.11.2008
 *         <p/>
 *         base on Apache commons-io
 */

public class IOUtil {
    private static Logger LOGGER = Logger.getLogger(IOUtil.class);

    public static void close(Closeable ioResource) {
        if (ioResource != null) {
            try {
                ioResource.close();
            } catch (Exception e) {
                LOGGER.warn("Resource closing problem", e);
            }
        }
    }

    public static URL safelyGetResource(String fileName) {
        return IOUtil.class.getClassLoader().getResource(fileName);
    }

    private static InputStream safelyGetResourceAsStream(String fileName) {
        return IOUtil.class.getClassLoader().getResourceAsStream(fileName);
    }

    public static File urlToFile(URL url) {
        try {
            return new File(url.toURI());
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Url '%s' is invalid", url), e);
        }
    }


    public static URL getResource(String fileName) {
        URL url = safelyGetResource(fileName);
        if (url == null) {
            throw new IllegalArgumentException(String.format("Resource file '%s' does not existed", fileName));
        }
        return url;
    }

    private static InputStream getResourceAsStream(String fileName) {
        InputStream in = safelyGetResourceAsStream(fileName);
        if (in == null) {
            throw new IllegalArgumentException(String.format("Resource file '%s' does not existed", fileName));
        }
        return in;
    }

    public static File getResourceAsFile(String fileName) {
        return urlToFile(getResource(fileName));
    }

    public static Properties getResourceAsProperties(String fileName) {
        Properties props = new Properties();
        try {
            props.load(getResourceAsStream(fileName));
            return props;
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("Properties '%s' is not valid", fileName), e);
        }
    }

    public static File getFile(String name, boolean create) {
        File file = new File(name);
        if (!file.exists()) {
            if (create) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new IllegalArgumentException(String.format("File '%s' could not be created", file.getAbsolutePath()), e);
                }
            } else {
                throw new IllegalArgumentException(String.format("File '%s' does not existed", file.getAbsolutePath()));
            }
        }
        if (!file.canRead()) {
            throw new IllegalArgumentException(String.format("File '%s' coudn't be readed", file.getAbsolutePath()));
        }
        return file;
    }
}
