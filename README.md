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

***

# Abilitare il CROSS DOMAIN sui servizi RESTful.
In questa sezione vedremo come abilitare l'accesso da domini differenti ad i nostri servizi restful.

## Return Type
Secondo la specifica JAX-RS, possiamo fornire come risultato di una chiamata i seguenti tipi:

|TYPE| DESCRIZIONE| 
|--- | ---|
|GenericEntity<T>| Tipo generico| 
|Response | Response generica |
|altro| Qualsiasi cosa |


Nei primo caso abbiamo un'oggetto che vincola lo sviluppatore a produrre un oggetto tipizzato ben definito. Il secondo caso ci permette di decidere a runtime il tipo di oggeto da passare. In ogni caso questi due TYPE previsti dalla specifica contengono delle informazioni e dei comportamenti aggiuntivi.
Infatti con essi è possibile definire anche attributi specifici, come l'header ed inoltre vengono gestiti gli stati di ritorno in maniera esplicita (status).  
Questa particolare caratteristica ci permetterà di fornire in risposta ad una chiamata dei parametri specifici dell'header e dunque anche di specificare il funzionamento del CROSS DOMAIN.

## Definizione dell' header
Per poter abilitare il client (Browser) a vedere le informazioni presenti nel servizio, dovremmo impostare i seguenti parametri nell'header dello stesso. 

```
Access-Control-Allow-Origin: *
Access-Control-Allow-Credentials: true
Access-Control-Allow-Methods: GET, POST, DELETE, PUT
```

Il primo parametro _Access-Control-Allow-Origin__ definisce quale/quali sono gli host che possono effettuare le chiamate.
Il secondo definisce se è necessario o meno fornire delle credenziali per poter accedere.
Mentre il terzo ci dice con quali metodi sarà possibile utilizzare il servizio.

## Impostazione Response
Il nostro servizio JAX-RS, dunque potrà abilitare tutti i client aggiungendo le informazioni dell'header nella risposta, come mostrato di seguito:

```java

	@GET
	@Path("/utenti/{user}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUtente(@PathParam("user") String user, 
			@QueryParam("age") int age,
			@QueryParam("street") String street) {
		...
		//EXAMPLE from API Reference.
		JsonObject value = Json.createObjectBuilder()
			     .add("firstName", user)
			     .add("lastName", "Smith")
			     .add("age", age)
			     .add("address", Json.createObjectBuilder()
			         .add("streetAddress", street!=null?street:"")
			         .add("city", "New York")
			         .add("state", "NY")
			         .add("postalCode", "10021"))
			     .add("phoneNumber", Json.createArrayBuilder()
			         .add(Json.createObjectBuilder()
			             .add("type", "home")
			             .add("number", "212 555-1234"))
			         .add(Json.createObjectBuilder()
			             .add("type", "fax")
			             .add("number", "646 555-4567")))
			     .build();
		return Response.ok().entity(value.toString())
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.build();
	}
	
```
 




