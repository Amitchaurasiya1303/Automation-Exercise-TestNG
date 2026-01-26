package Utility;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    public static String getProperty(String propName) throws IOException {
        FileReader fr = new FileReader(PathUtil.getPath("config.properties"));
        Properties properties = new Properties();
        properties.load(fr);

        return properties.getProperty(propName);
    }
}
