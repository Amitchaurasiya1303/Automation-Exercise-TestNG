package Utility;

import java.io.File;

public class PathUtil {
    public static String getPath(String fileName){
        String path = System.getProperty("user.dir")
                +File.separator+"src"
                +File.separator+"test"
                +File.separator+"resources"
                +File.separator+ fileName;

        return path;
    }
}
