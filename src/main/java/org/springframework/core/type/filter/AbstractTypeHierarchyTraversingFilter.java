package org.springframework.core.type.filter;

import cn.hutool.core.util.ClassUtil;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.List;

public abstract class AbstractTypeHierarchyTraversingFilter  implements TypeFilter {
    boolean considerInherited = false;
    boolean considerInterfaces=false;

    protected AbstractTypeHierarchyTraversingFilter(boolean considerInherited, boolean considerInterfaces) {
        this.considerInherited = considerInherited;
        this.considerInterfaces = considerInterfaces;
    }
    @Override
    public boolean match(String className)  {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream stream = classLoader.getResourceAsStream(className.replace('.', '/') + ".class");
            ClassReader reader = new ClassReader(stream);
            ClassNode classNode = new ClassNode();



            if (matchSelf(classNode)) {
                return true;
            }

            reader.accept(classNode, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
            if (considerInherited) {

                String superClass = classNode.superName;
                return match(superClass);

            }
            if (considerInterfaces) {
                List<String> interfaces = classNode.interfaces;

                for (String Interface : interfaces) {
                    if (matchInterfaces(Interface)) {
                        return true;
                    }
                }
            }
        }catch (IOException e){
            return false;
        }
        return false;
    }


    protected boolean matchSelf(ClassNode classNode) {
        return false;
    }
    protected boolean matchSuperTypes(String className) throws IOException {
        return false;
    }
    protected boolean matchInterfaces(String className) throws IOException {
        return false;
    }
}
