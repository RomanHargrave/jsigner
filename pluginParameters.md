## Introduction ##
Jsigner diagrams can be configured to fit your needs. This is done by setting some parameters in the plugin configuration.

The following table describes each configuration parameter.

| parameter Name | Description | Type |Default Value | Required |
|:---------------|:------------|:-----|:-------------|:---------|
| outputFolder | Path to the folder where the diagrams will be created | String | none | yes |
|discoverMultiplicityByPersistenceAnnotations | Set this to true improves the multiplicity discovery algorithm, drawing the multiplicity in the diagram according to javax.persistence annotations on source code. When this is false, the collections will be always mapped as One-to-Many relationships and others relationships as One-to-One   | boolean | false | no |
| hidePrivateMethods | Configures the omission of private methods | boolean | true | no |
| hideSetters | Configures the omission of setters methods | boolean | true | no |
| hideGetters | Configures the omission of getters methods | boolean | true | no |
| hideEquals | Configures the omission of equals methods | boolean | true | no |
| hideHashcode | Configures the omission of hashCode methods | boolean | true | no |
| hideSerialVersion | Configures the omission of SerialVersionUID fields | boolean | true | no |



### Configuration Example ###
```
<plugin>
	<groupId>br.com.jsigner</groupId>
	<artifactId>jsigner-maven-plugin</artifactId>
	<version>1.1</version>
	<configuration>
		<outputFolder>/home/rafa/diagramas</outputFolder>
		<discoverMultiplicityByPersistenceAnnotations>true</discoverMultiplicityByPersistenceAnnotations>
	</configuration>
</plugin>
```