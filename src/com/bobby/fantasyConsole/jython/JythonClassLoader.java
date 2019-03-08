package com.bobby.fantasyConsole.jython;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class JythonClassLoader extends URLClassLoader {

    private static final String[] restrictedPackages = {"java"};


    public JythonClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public JythonClassLoader(URL[] urls) {
        super(urls);
    }
    public JythonClassLoader(ClassLoader parent) {
        super(new URL[0], parent);
    }

    @Override
    protected void addURL(URL url) {
        throw new RuntimeException("Cannot extend classloader at runtime");
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        for (String packageName : restrictedPackages)
            if (name.startsWith(packageName)) {
                return null;
            }
        return super.loadClass(name, resolve);
    }
}
