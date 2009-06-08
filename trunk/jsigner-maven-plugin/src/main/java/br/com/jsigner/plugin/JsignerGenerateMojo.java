/**
 * Copyright (C) 2008 Rafael Farias Silva <rafaferry@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.jsigner.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactCollector;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.dependency.tree.DependencyNode;
import org.apache.maven.shared.dependency.tree.DependencyTreeBuilder;
import org.apache.maven.shared.dependency.tree.DependencyTreeBuilderException;

/**
 * @goal generate
 * @aggregator
 */
public class JsignerGenerateMojo extends AbstractMojo {

    /**
     * The Maven project.
     * 
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * The artifact repository to use.
     * 
     * @parameter expression="${localRepository}"
     * @required
     * @readonly
     */
    private ArtifactRepository localRepository;

    /**
     * The artifact factory to use.
     * 
     * @component
     * @required
     * @readonly
     */
    private ArtifactFactory artifactFactory;

    /**
     * The artifact metadata source to use.
     * 
     * @component
     * @required
     * @readonly
     */
    private ArtifactMetadataSource artifactMetadataSource;

    /**
     * The artifact collector to use.
     * 
     * @component
     * @required
     * @readonly
     */
    private ArtifactCollector artifactCollector;

    /**
     * The dependency tree builder to use.
     * 
     * @component
     * @required
     * @readonly
     */
    private DependencyTreeBuilder dependencyTreeBuilder;

    /**
     * @parameter
     */
    private Set<String> packs;

    /**
     * @parameter expression="${basedir}/target/jsigner"
     */
    private File dest;

    /**
     * Map of of plugin artifacts.
     * 
     * @parameter expression="${plugin.artifactMap}"
     * @required
     * @readonly
     */
    private Map<String, Artifact> pluginArtifactMap;

    public JsignerGenerateMojo() {
        this.packs = new TreeSet<String>(Arrays.asList("jar", "ejb", "war"));
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        List<File> projects = new ArrayList<File>();
        List<File> classpath = getClasspath(projects);
        classpath.addAll(getJsignerDependencies());
        exec(classpath, projects);
    }

    private List<File> getJsignerDependencies() {
        getLog().info("Getting plugin dependencies...");
        Collection<Artifact> artifacts = pluginArtifactMap.values();
        List<File> files = getArtifactsClasspath(artifacts);
        return files;
    }

    private void exec(List<File> classpath, List<File> projects) throws MojoExecutionException {
        try {
            List<URL> urls = new ArrayList<URL>(classpath.size());
            for (File file : classpath) {
                if (!file.exists()) {
                    getLog().warn("File not found to isolated classloader: " + file);
                } else {
                    urls.add(file.toURI().toURL());
                }
            }

            urls = new ArrayList<URL>(new HashSet<URL>(urls));
            getLog().info("Creating classloader for: " + urls);
            ClassLoader loader = URLClassLoader.newInstance(urls.toArray(new URL[urls.size()]));
            JsignerRunner runner = new JsignerRunner(getLog(), loader, projects, dest);
            runner.start();
            runner.join();
            Throwable throwable = runner.getThrowable();
            if (throwable != null) {
                throw new MojoExecutionException("error", throwable);
            }

            // StringBuilder builder = new StringBuilder();
            // for (URL url : urls) {
            // builder.append(url.toURI().toURL());
            // builder.append(":");
            // }
            // StringBuilder ps = new StringBuilder();
            // for (File file : projects) {
            // ps.append(file);
            // ps.append(":");
            // }
            //
            // Commandline cl = new Commandline("java");
            // cl.addArguments(new String[] { "-cp", builder.toString(),
            // Starter.class.getName(), ps.toString() });
            // StreamConsumer output = new DefaultConsumer();
            // StreamConsumer error = new DefaultConsumer();
            // int returnValue = CommandLineUtils.executeCommandLine(cl, null,
            // output, error);
            // System.out.println(returnValue);
        } catch (MalformedURLException e) {
            throw new MojoExecutionException("error", e);
        } catch (InterruptedException e) {
            throw new MojoExecutionException("error", e);
        }
    }

    @SuppressWarnings("unchecked")
    private List<File> getClasspath(List<File> projectFiles) throws MojoExecutionException {
        Set<Artifact> artifacts = new HashSet<Artifact>();
        List<MavenProject> collectedProjects = project.getCollectedProjects();
        Set<MavenProject> projects = new HashSet<MavenProject>();
        for (MavenProject module : collectedProjects) {
            if (packs.contains(module.getPackaging())) {
                projects.add(module);
                mountClasspath(module, artifacts);
            }
        }
        removeProjects(projects, artifacts);
        List<File> files = getArtifactsClasspath(artifacts);
        projectFiles.addAll(getProjectsClasspath(projects));
        files.addAll(projectFiles);
        return files;
    }

    @SuppressWarnings("unchecked")
    private List<File> getProjectsClasspath(Set<MavenProject> projects) throws MojoExecutionException {
        try {
            List<File> ret = new ArrayList<File>(projects.size());
            for (MavenProject module : projects) {
                List<String> elements = module.getCompileClasspathElements();
                for (String element : elements) {
                    ret.add(new File(element));
                }
            }
            return ret;
        } catch (DependencyResolutionRequiredException e) {
            throw new MojoExecutionException("error", e);
        }
    }

    private List<File> getArtifactsClasspath(Collection<Artifact> artifacts) {
        List<File> ret = new ArrayList<File>(artifacts.size());
        for (Artifact artifact : artifacts) {
            String path = localRepository.getBasedir() + "/" + localRepository.pathOf(artifact);
            ret.add(new File(path));
        }
        return ret;
    }

    private void removeProjects(Set<MavenProject> projects, Set<Artifact> artifacts) {
        for (MavenProject module : projects) {
            Artifact artifact = module.getArtifact();
            artifacts.remove(artifact);
        }
    }

    @SuppressWarnings("unchecked")
    private void mountClasspath(MavenProject module, Set<Artifact> artifacts) throws MojoExecutionException {
        try {
            Artifact artifact = module.getArtifact();
            getLog().info("Mounting classpath: " + artifact);
            artifacts.add(artifact);
            getLog().debug("Dependency: " + artifact);

            DependencyNode root = dependencyTreeBuilder.buildDependencyTree(module, localRepository, artifactFactory,
                    artifactMetadataSource, null, artifactCollector);
            Iterator<DependencyNode> it = root.iterator();
            while (it.hasNext()) {
                DependencyNode node = it.next();
                Artifact dependency = node.getArtifact();
                if ((Artifact.SCOPE_COMPILE.equals(dependency.getScope()) || Artifact.SCOPE_PROVIDED.equals(dependency
                        .getScope()))
                        && packs.contains(dependency.getType())) {
                    boolean add = artifacts.add(dependency);
                    if (add) {
                        getLog().debug("Dependency: " + artifact);
                    }
                }
            }
        } catch (DependencyTreeBuilderException e) {
            throw new MojoExecutionException("error", e);
        }
    }
}
