# Introduction #

Automating the class diagrams generation is quite simple, you just use the @Domain annotation on the classes that you want to be in the diagram, specifying the diagram name (or names, if the class will appear in multiple diagrams).


# Maven Configuration #

> Steps:
    1. Configure the Jsigner repository
    1. Configure the plugin repository
    1. Declare the jsigner-annotations dependency in your project
    1. Declare the jsigner-maven-plugin dependency


### 1. Configure the Jsigner repository ###
> You can configure the repository on the general settings.xml or on the pom.xml of your project.

> The url for the repository is:
> > http://repo.pyrata.org/release/maven2/


> #### pom.xml configuration: ####

> Use the following code snippet inside the 

&lt;repositories&gt;

 tag:

```
<repository>
    <id>pyrata</id>
    <url>http://repo.pyrata.org/release/maven2/</url>
</repository>
```

### 2. Configure the plugin repository ###

> Use the following code snippet inside the 

&lt;pluginRepositories&gt;

 tag:

```
<pluginRepository>
    <id>pyrata</id>
    <url>http://repo.pyrata.org/release/maven2/</url>
</pluginRepository>
```

### 3. Declare the jsigner-annotations dependency in your project ###
> You must declare the following dependency in your pom.xml for importing the @Domain annotation:

```
<dependency>
  <groupId>br.com.jsigner</groupId> 
  <artifactId>jsigner-annotations</artifactId>
  <version>projectVersion</version>	
</dependency>
```
Where 'projectVersion' should be replaced by the version that you wish to use.

### 4. Declare the jsigner-maven-plugin dependency in your project ###
> You must declare the following plugin in your pom.xml for using the **mvn jsigner:generate** command.

```
<plugin>
	<groupId>br.com.jsigner</groupId>
	<artifactId>jsigner-maven-plugin</artifactId>
	<version>projectVersion</version>
	<configuration>
		<outputFolder>outputFolder</outputFolder>
	</configuration>
</plugin>
```

Where projectVersion should be replaced by the version of the plugin that you wish to use, and outputFolder is the folder that you wish to store the diagrams. If outputFolder is not provided, jsigner will generate the diagrams at target/jsigner

# Usage #

The plugin is invoked with the `mvn jsigner:generate` command, wich scans the jars in the project recursively, and draw the diagrams into the configured output folder.

# Plugin Configuration #
For more details on plugin configuration see
[Jsigner Plugin Configuration](http://code.google.com/p/jsigner/wiki/pluginParameters)

### Third-party Acknowledgements ###
This product includes software developed by ModSL Project (http://www.modsl.org).