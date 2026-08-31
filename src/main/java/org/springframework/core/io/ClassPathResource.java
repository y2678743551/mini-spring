package org.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/*
 * @date 2026/8/30
 */

public class ClassPathResource implements Resource {
    private final String path;
    public ClassPathResource(String path) {
        this.path = path;
        //System.out.println("ClassPathResource: " + path);
    }

    @Override
    public InputStream getInputStream() throws IOException {

        return ClassPathResource.class.getClassLoader().getResourceAsStream(path);
    }
}

