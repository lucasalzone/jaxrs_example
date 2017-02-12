# Esempio utilizzo JAX-RS with Jersey Implementation

## Introduzione
In questo esempio si farà riferimento alle seguenti tecnologie:


1. JSR339 di **JAX-RS 2.0** [link to JSR339](https://jcp.org/en/jsr/detail?id=339)
2. **Jersey Implementation 2.25** [link to Jersey Guide](https://jersey.java.net/documentation/latest/user-guide.html)
3. Json Processing implementation **JSONP** [link JSON Processing](https://jsonp.java.net/download.html) 

## Download esempio
**GitHub** [link example source code](https://github.com/lucasalzone/jaxrs_example)



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
		<!-- SCOPE TEST -->
		<dependency>
			<groupId>org.glassfish.jersey.test-framework.providers</groupId>
			<artifactId>jersey-test-framework-provider-inmemory</artifactId>
			<version>2.25.1</version>
			<scope>test</scope>
		</dependency>
```	
Di seguito una breve descrizione:

* **jersey-container-servlet**: implementazione delle specifiche JAX-RS.
* **javax.json-api**: interfacce previste delle specifiche Json Processing
* **javax.json** (org.glassfish): implementazione javax.json
* **jersey-test-framework-provider-inmemory**: framework jersey per testare le risorse.

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

* **@Path**: Defines a URI template for the resource class or method.
* **@GET**: Indicates that the annotated method responds to HTTP GET requests.
* **@Produces**: Defines the media type(s) that the methods of a resource class or can produce.
* **@PathParam**: Binds the value of a URI template parameter or for path segment.
* **@QueryParam**: Binds the value(s) of a HTTP query parameter to a resource method parameter.

Inoltre per poter creare il JSON in output e stato utilizzato JsonObject.

### Example Resource

```java
@Path("hello") 
public class HelloWorld {
	@GET 
	@Produces("text/plain")
	public String getHello() {
		return "ciao Mondo";
	}
	...
}
```

## Test Resource with jersey
To test resource, we can use JUNIT and extends JerseyTest class.
With ResourceConfig we can create a server and configure a resource, then we can test it using WebTarget client.

### Example Test.

```java
public class HelloWorldTest extends JerseyTest{
 
	/**
	 * Create a new resource configuration (with Class Provided) for 'in memory' server application.
	 */
	@Override
    protected Application configure() {
        return new ResourceConfig(HelloWorld.class);
    }
 
	/**
	 * Use webtarget class to create a client and inizialize with "hello" resource.
	 */
    @Test
    public void hello() {
    	//call method get on resource "hello".
        final String result = target("hello").request().get(String.class);
        Assert.assertTrue(result.contains("ciao Mondo"));
    }
}
```






