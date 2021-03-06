/*******************************************************************************
 * Copyright (c) 2012-2016 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.ide.ext.java;

import org.eclipse.che.jdt.JavaNavigation;
import org.eclipse.che.jdt.SourcesFromBytecodeGenerator;
import org.eclipse.che.ide.ext.java.shared.Jar;
import org.eclipse.che.ide.ext.java.shared.JarEntry;

import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Evgen Vidolob
 */
public class JarNavigationTest extends BaseTest {

    private final JavaNavigation navigation = new JavaNavigation(new SourcesFromBytecodeGenerator());

    @Test
    public void testJars() throws Exception {
        List<Jar> jars = navigation.getProjectDependecyJars(project);
        assertThat(jars).isNotNull().isNotEmpty().onProperty("name").contains("rt.jar", "zipfs.jar", "dnsns.jar");
    }

    @Test
    public void testPackageFragmentContent() throws Exception {
        String javaHome = System.getProperty("java.home") + "/lib/rt.jar";
        IPackageFragmentRoot root = project.getPackageFragmentRoot(new File(javaHome));
        List<JarEntry> rootContent = navigation.getPackageFragmentRootContent(project, root.hashCode());
        assertThat(rootContent).isNotNull().isNotEmpty().onProperty("name").contains("META-INF", "java", "javax");
        assertThat(rootContent).onProperty("path").contains("/META-INF");
    }

    @Test
    public void testDoNotIncludeDefaultEmptyPackage() throws Exception {
        String javaHome = System.getProperty("java.home") + "/lib/ext/zipfs.jar";
        IPackageFragmentRoot root = project.getPackageFragmentRoot(new File(javaHome));
        List<JarEntry> rootContent = navigation.getPackageFragmentRootContent(project, root.hashCode());
        assertThat(rootContent).isNotNull().isNotEmpty().onProperty("name").excludes("(default package)");
        assertThat(rootContent).onProperty("path").contains("/META-INF");
    }

    @Test
    public void testNonJavaElement() throws Exception {
        String javaHome = System.getProperty("java.home") + "/lib/ext/zipfs.jar";
        IPackageFragmentRoot root = project.getPackageFragmentRoot(new File(javaHome));
        List<JarEntry> rootContent = navigation.getPackageFragmentRootContent(project, root.hashCode());
        assertThat(rootContent).isNotNull().isNotEmpty().onProperty("name").contains("META-INF");
        assertThat(rootContent).onProperty("path").contains("/META-INF");
    }

    @Test
    public void testNonJavaFolder() throws Exception {
        String javaHome = System.getProperty("java.home") + "/lib/ext/zipfs.jar";
        IPackageFragmentRoot root = project.getPackageFragmentRoot(new File(javaHome));
        List<JarEntry> rootContent = navigation.getChildren(project, root.hashCode(), "/META-INF");
        assertThat(rootContent).isNotNull().isNotEmpty().onProperty("name").contains("services", "MANIFEST.MF");
    }

    @Test
    public void testNonJavaFile() throws Exception {
        String javaHome = System.getProperty("java.home") + "/lib/ext/zipfs.jar";
        IPackageFragmentRoot root = project.getPackageFragmentRoot(new File(javaHome));
        List<JarEntry> rootContent = navigation.getChildren(project, root.hashCode(), "/META-INF/services");
        assertThat(rootContent).isNotNull().isNotEmpty().onProperty("name").containsExactly("java.nio.file.spi.FileSystemProvider");
    }

    @Test
    public void testJavaPackage() throws Exception {
        String javaHome = System.getProperty("java.home") + "/lib/rt.jar";
        IPackageFragmentRoot root = project.getPackageFragmentRoot(new File(javaHome));
        List<JarEntry> rootContent = navigation.getChildren(project, root.hashCode(), "java");
        assertThat(rootContent).isNotNull().isNotEmpty().onProperty("name").contains("lang", "io", "util", "net", "nio");
    }

    @Test
    public void testPackageCollapsing() throws Exception {
        String javaHome = System.getProperty("java.home") + "/lib/rt.jar";
        IPackageFragmentRoot root = project.getPackageFragmentRoot(new File(javaHome));
        List<JarEntry> rootContent = navigation.getChildren(project, root.hashCode(), "org");
        assertThat(rootContent).isNotNull().isNotEmpty().onProperty("name").contains("omg", "w3c.dom", "xml.sax");
    }

    @Test
    public void testClassFile() throws Exception {
        String javaHome = System.getProperty("java.home") + "/lib/rt.jar";
        IPackageFragmentRoot root = project.getPackageFragmentRoot(new File(javaHome));
        List<JarEntry> rootContent = navigation.getChildren(project, root.hashCode(), "java.lang");
        assertThat(rootContent).isNotNull().isNotEmpty().onProperty("name").contains("Object.class", "String.class", "Integer.class");
    }

    @Test
    public void testClassFileFQN() throws Exception {
        String javaHome = System.getProperty("java.home") + "/lib/rt.jar";
        IPackageFragmentRoot root = project.getPackageFragmentRoot(new File(javaHome));
        List<JarEntry> rootContent = navigation.getChildren(project, root.hashCode(), "java.lang");
        assertThat(rootContent).isNotNull().isNotEmpty().onProperty("path").contains("java.lang.Object", "java.lang.String", "java.lang.Integer");
    }

    @Test
    public void testDoesNotReturnInnerClasses() throws Exception {
        String javaHome = System.getProperty("java.home") + "/lib/rt.jar";
        IPackageFragmentRoot root = project.getPackageFragmentRoot(new File(javaHome));
        List<JarEntry> rootContent = navigation.getChildren(project, root.hashCode(), "java.lang");
        assertThat(rootContent).isNotNull().isNotEmpty().onProperty("name").excludes("Character$Subset.class");
    }

    @Test
    public void testJavaSource() throws Exception {
        String javaHome = System.getProperty("java.home") + "/lib/rt.jar";
        IPackageFragmentRoot root = project.getPackageFragmentRoot(new File(javaHome));
        String content = navigation.getContent(project, root.hashCode(), "java.lang.Object");
        assertThat(content).isNotNull().isNotEmpty().contains("public class Object");
    }

    @Test
    public void testNonJavaFileContent() throws Exception {
        String javaHome = System.getProperty("java.home") + "/lib/ext/zipfs.jar";
        IPackageFragmentRoot root = project.getPackageFragmentRoot(new File(javaHome));
        String content = navigation.getContent(project, root.hashCode(), "/META-INF/services/java.nio.file.spi.FileSystemProvider");
        assertThat(content).isNotNull().isNotEmpty().contains("");
    }

    @Test
    public void testExternalJar() throws Exception {
        String javaHome = getClass().getResource("/temp").getPath() + "/ws/test/gwt-user.jar";
        IPackageFragmentRoot root = project.getPackageFragmentRoot(new File(javaHome));
        List<JarEntry> content = navigation.getPackageFragmentRootContent(project, root.hashCode());
        assertThat(content).isNotNull().isNotEmpty().onProperty("name").contains("com.google", "javax", "org", "META-INF", "about.html");
    }

    @Test
    public void testSortJarEntries() throws Exception {
        String javaHome = getClass().getResource("/temp").getPath() + "/ws/test/gwt-user.jar";
        IPackageFragmentRoot root = project.getPackageFragmentRoot(new File(javaHome));
        List<JarEntry> content = navigation.getPackageFragmentRootContent(project, root.hashCode());
        assertThat(content).isNotNull().isNotEmpty().onProperty("name").containsSequence("about_files","com.google", "javax", "org", "META-INF", "about.html", "plugin.properties");
    }

    @Test
    public void testSortJarEntriesWithClasses() throws Exception {
        String javaHome = getClass().getResource("/temp").getPath() + "/ws/test/gwt-user.jar";
        IPackageFragmentRoot root = project.getPackageFragmentRoot(new File(javaHome));
        List<JarEntry> content = navigation.getChildren(project, root.hashCode(), "org.hibernate.validator");
        assertThat(content).isNotNull().isNotEmpty().onProperty("name").containsSequence("engine","HibernateValidationMessageResolver.class", "ValidationMessages.class", "HibernateValidator.gwt.xml", "README.txt");
    }

    @Test
    public void testExternalJarFileContentInRoot() throws Exception {
        String javaHome = getClass().getResource("/temp").getPath() + "/ws/test/gwt-user.jar";
        IPackageFragmentRoot root = project.getPackageFragmentRoot(new File(javaHome));
        String content = navigation.getContent(project, root.hashCode(), "/about.html");
        assertThat(content).isNotNull().contains("<p>The Eclipse Foundation makes available all content in this plug-in");
    }

    @Test
    public void testFileContentInPackage() throws Exception {
        String javaHome = getClass().getResource("/temp").getPath() + "/ws/test/gwt-user.jar";
        IPackageFragmentRoot root = project.getPackageFragmentRoot(new File(javaHome));
        String content = navigation.getContent(project, root.hashCode(), "/com/google/gwt/user/User.gwt.xml");
        assertThat(content).isNotNull().contains("<!-- Combines all user facilities into a single module for convenience.     -->")
                           .contains("<!-- Most new code should inherit this module.                              -->")
        .contains("<inherits name=\"com.google.gwt.core.Core\"/>");
    }

    @Test
    public void testGetFileBypath() throws Exception {
        String javaHome = getClass().getResource("/temp").getPath() + "/ws/test/gwt-user.jar";
        IPackageFragmentRoot root = project.getPackageFragmentRoot(new File(javaHome));
        JarEntry entry = navigation.getEntry(project, root.hashCode(), "/com/google/gwt/user/User.gwt.xml");
        assertThat(entry).isNotNull();
        assertThat(entry.getType()).isEqualTo(JarEntry.JarEntryType.FILE);
        assertThat(entry.getPath()).isEqualTo("/com/google/gwt/user/User.gwt.xml");
        assertThat(entry.getName()).isEqualTo("User.gwt.xml");
    }

    @Test
    public void testGetClassByPath() throws Exception {
        String javaHome = System.getProperty("java.home") + "/lib/rt.jar";
        IPackageFragmentRoot root = project.getPackageFragmentRoot(new File(javaHome));
        JarEntry entry = navigation.getEntry(project, root.hashCode(), "java.lang.Object");
        assertThat(entry).isNotNull();
        assertThat(entry.getType()).isEqualTo(JarEntry.JarEntryType.CLASS_FILE);
        assertThat(entry.getPath()).isEqualTo("java.lang.Object");

    }
}
