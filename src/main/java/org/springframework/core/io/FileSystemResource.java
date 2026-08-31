package org.springframework.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
/*
 * @date 2026/8/30
 */
public class FileSystemResource implements Resource {
    private final String path;
    FileSystemResource(String path) {
        this.path = path;
    }
    @Override
    public InputStream getInputStream() {
        try {
            return new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
