package org.springframework.core.io;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
/*
 * @date 2026/8/30
 */
public class DefaultResourceLoader implements ResourceLoader {
    private final static String CLASSPATH_PREFIX = "classpath:";

    @Override
    public Resource getResource(String location) {
        if (location.startsWith(CLASSPATH_PREFIX)) {
            //System.out.println("ClassPathResource: " + location.substring(CLASSPATH_PREFIX.length()));
            return new ClassPathResource(location.substring(CLASSPATH_PREFIX.length()));
        } else {
            try{
                URL url = new URI(location).toURL();
                return new UrlResource(url);
            }catch (URISyntaxException | MalformedURLException e) {
                return new FileSystemResource(location);
            }

        }
    }
}
