package it.test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("hello")
public class HelloWorld {
	@GET
	@Produces("text/plain")
	public String getHello() {
		return "ciao Mondo";
	}

	// subpath
	@GET
	@Path("/utenti")
	@Produces("application/json")
	public String getUtenti() {
		return "[{'user':'luca'},{'user':'francesco'},{'user':'gianni'},{'user':'marcello'},{'user':'giovanni'}]";
	}

	/**
	 * Example of resource with url parameter and query parameter and utilization of JsonObject to create 
	 * @param user
	 * @return
	 */
	@GET
	@Path("/utenti/{user}")
	@Produces("application/json")
	public String getUtenti(@PathParam("user") String user, 
			@QueryParam("age") int age,
			@QueryParam("street") String street) {
		//TODO call method to populate JsonObject whit user
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
		return value.toString(); 
	}
}
