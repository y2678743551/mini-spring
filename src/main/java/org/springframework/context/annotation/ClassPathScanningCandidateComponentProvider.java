package org.springframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.filter.AnnotationTypeFilter;
import org.springframework.core.filter.TypeFilter;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassPathScanningCandidateComponentProvider {
    private Set<TypeFilter> includeFilters= new HashSet<>();
    private Set<TypeFilter> excludeFilters= new HashSet<>();
    public ClassPathScanningCandidateComponentProvider() {
        this.includeFilters.add(new AnnotationTypeFilter(Component.class));
    }
    public void addIncludeFilter(TypeFilter includeFilter) {
        this.includeFilters.add(includeFilter);
    }
    public void addExcludeFilter(TypeFilter excludeFilter) {
        this.excludeFilters.add(excludeFilter);
    }
    public Set<BeanDefinition> findCandidateComponents(String basePackage) throws IOException {
        Set<String> classNames = getClassNamesInPackage(basePackage);
        Set<BeanDefinition> candidates = new LinkedHashSet<BeanDefinition>();
        for (String className : classNames) {
            try {
                if (isCandidateComponent(className)) {
                    BeanDefinition beanDefinition = new BeanDefinition();

                    beanDefinition.setBeanClass(ClassUtil.loadClass(className));
                    candidates.add(beanDefinition);
                }
            } catch (IOException e) {

            }
        }
        return candidates;
    }
    private Set<String> getClassNamesInPackage(String basePackage) throws IOException {
        Set<String> classNames = new HashSet<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(basePackage.replace('.', '/'));
        while (resources.hasMoreElements()){
            URL url = resources.nextElement();
            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                String filePath = url.getPath();
                getClassNamesInPackageByFile(basePackage, filePath, classNames);
            } else if ("jar".equals(protocol)) {

                getClassNamesInPackageByJar(basePackage, url, classNames);
            }
        }
        return classNames;
    }

    private void getClassNamesInPackageByFile(String basePackage, String filePath, Set<String> classNames) {
        File[] files = new File(filePath).listFiles();
        if (files == null) {
            return ;
        }
        for (File file : files){
            if (file.isDirectory()) {
                getClassNamesInPackageByFile(basePackage, file.getPath(), classNames);
            } else {
                String rootPath =filePath.replace('\\', '.');
                rootPath = rootPath.substring(!rootPath.contains(basePackage) ? 0 : rootPath.indexOf(basePackage));
                classNames.add(rootPath + "." + file.getName().replace(".class", ""));

            }
        }

    }

    private void getClassNamesInPackageByJar(String basePackage, URL url, Set<String> classNames) throws IOException {
        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
        JarFile jarFile = jarURLConnection.getJarFile();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            if (name.startsWith(basePackage) && name.endsWith(".class")) {
                classNames.add(name.replace(".class", "").replace('/', '.'));
            }
        }
    }

    protected boolean isCandidateComponent(String className) throws IOException {

        for (TypeFilter tf : this.excludeFilters) {
            if (tf.match(className)) {
                return false;
            }
        }
        for (TypeFilter tf : this.includeFilters) {
            if (tf.match(className)) {
                return true;
            }
        }
        return false;
    }
}
