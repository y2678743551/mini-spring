package org.springframework.core.type.filter;

import java.io.IOException;

public interface TypeFilter {

    boolean match(String className) throws IOException;

}
