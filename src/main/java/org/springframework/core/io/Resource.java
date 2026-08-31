package org.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;
/*
 * @date 2026/8/30
 */
public interface Resource {
    public InputStream getInputStream() throws IOException;
}
