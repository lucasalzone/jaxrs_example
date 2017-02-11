# Esempio utilizzo JAX-RS with Jersey Implementation

## Introduzione
In questo esempio si farà riferimento alle seguenti tecnologie:


1. JSR339 di **JAX-RS 2.0** [link to JSR339](https://jcp.org/en/jsr/detail?id=339)
2. **Jersey Implementation 2.25** [link to Jersey Guide](https://jersey.java.net/documentation/latest/user-guide.html)
3. Json Processing implementation **JSONP** [link JSON Processing](https://jsonp.java.net/download.html) 


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




