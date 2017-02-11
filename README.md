# Esempio utilizzo JAX-RS with Jersey Implementation

## Introduzione
In questo esempio si farà riferimento alle seguenti tecnologie:


1. JSR339 di **JAX-RS 2.0** [link to JSR339](https://jcp.org/en/jsr/detail?id=339)
2. **Jersey Implementation 2.25** [link to Jersey Guide](https://jersey.java.net/documentation/latest/user-guide.html)
3. Json Processing implementation **JSONP** [link JSON Processing](https://jsonp.java.net/download.html) 

## Le dependencies
Il progetto è stato creato come progetto Maven base e sono state aggiunge le seguenti **dependencies**:

```xml
		<dependency>
			<groupId>org.glassfish.jersey.containers</groupId>
			<artifactId>jersey-container-servlet</artifactId>
			<version>2.25.1</version>
		</dependency>
		<dependency>
			<groupId>javax.json</groupId>
			<artifactId>javax.json-api</artifactId>
			<version>1.0</version>
		</dependency>
		<dependency>
			<groupId>org.glassfish</groupId>
			<artifactId>javax.json</artifactId>
			<version>1.0.4</version>
		</dependency>
```	
Di seguito una breve descrizione:

* **jersey-container-servlet**: implementazione delle specifiche JAX-RS.
* **javax.json-api**: interfacce previste delle specifiche Json Processing
* **javax.json** (org.glassfish): implementazione javax.json

## Configure Servlet
Per poter utilizzare l'implementazione Jersey, è possibile utilizzare una **servlet** oppure un **listener**, che Jersey mette a disposizione per gestire le risorse. Nel progetto è stata utilizzata la prima soluzione.

Inoltre è stato scelto di utilizzare il *package-scanning* come soluzione per trovare le classi annotate come risorse, attraverso l'utilizzo del provider **jersey.config.server.provider.packages**.


```	xml
	<servlet>
		<servlet-name>MyApplication</servlet-name>
		<servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
		<init-param>
			<param-name>jersey.config.server.provider.packages</param-name>
			<param-value>it.test</param-value>
		</init-param>
		<init-param>
			<param-name>jersey.config.server.provider.scanning.recursive</param-name>
			<param-value>false</param-value>
		</init-param>
	</servlet>
	<servlet-mapping>
		<servlet-name>MyApplication</servlet-name>
		<url-pattern>/api/*</url-pattern>
	</servlet-mapping>
```		

## Resource definition
Per poter definire le risorse sono state utilizzate alcune delle annotazioni visibili nelle specifiche JAX-RS. In particolare:

* @Path
* @GET
* @Produces
* @PathParam
* @QueryParam

Inoltre per poter creare il JSON in output e stato utilizzato JsonObject.








