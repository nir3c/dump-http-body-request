package com.util;

import java.io.File;

/**
 * Created by Nir.
 */
public class FileUtils {

    public static boolean ensureDirectoriesExists(String dirsPath){
        File dirs = new File(dirsPath);
        return dirs.exists() || dirs.mkdirs();
    }

}
