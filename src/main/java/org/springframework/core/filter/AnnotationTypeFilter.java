package org.springframework.core.filter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.List;

public class AnnotationTypeFilter extends AbstractTypeHierarchyTraversingFilter {
    private final Class<? extends Annotation> annotationType;

    public AnnotationTypeFilter(Class<? extends Annotation> annotationType) {
        super(true, false);
        this.annotationType = annotationType;

    }
    public AnnotationTypeFilter(Class<? extends Annotation> annotationType, boolean considerInherited, boolean considerInterfaces) {
        super(considerInherited, considerInterfaces);
        this.annotationType = annotationType;
    }
    @Override
    protected boolean matchSelf(ClassNode classNode) {
        List<AnnotationNode> annotations = classNode.visibleAnnotations;
        if (annotations == null) return false;
        for (AnnotationNode annotation : annotations) {
            Type type = Type.getType(annotation.desc);
            if (type.getClassName().equals(annotationType.getName())) {
                return true;
            }
        }
        return false;
    }
    @Override
    protected boolean matchSuperTypes(String className) throws IOException {
        return hasAnnotationType(className);
    }
    @Override
    protected boolean matchInterfaces(String className) throws IOException {
        return hasAnnotationType(className);
    }

    private boolean hasAnnotationType(String className) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream(className.replace('.', '/') + ".class");
        ClassReader reader = new ClassReader(stream);
        ClassNode classNode = new ClassNode();
        reader.accept(classNode, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        return classNode.visibleAnnotations.stream().anyMatch(annotation -> annotation.desc.equals(annotationType.getName()));
    }
}
