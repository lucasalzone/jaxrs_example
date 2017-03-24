package it.test;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import it.test.dto.User;

@Path("hello")
public class HelloWorld {

	private static final Logger LOGGER = Logger.getLogger(HelloWorld.class.getName());
	
	@GET
	@Path("/utenti")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUtenti() throws JsonProcessingException {
		LOGGER.info("getUtenti");
		return Response.ok().entity(ParserUtility.parseObject(Users.getSingleton().get()))
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, OPTION, POST, DELETE, PUT")
				.build();
	}

	@GET
	@Path("/utenti/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUtente(@PathParam("name") String user) throws JsonProcessingException {
		LOGGER.info("getUtente");
		return Response.ok().entity(ParserUtility.parseObject(Users.getSingleton().getByName(user)))
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.build();
	}

	//----------------------------------------------------------------------------------------------------
	@POST
	@Path("/utenti") 
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUtente(String user) throws JsonParseException, JsonMappingException, IOException {
		LOGGER.info("updateUtente");
		Users.getSingleton().add(ParserUtility.parseString(user, User.class));
		return Response.ok().entity(ParserUtility.parseObject(Result.build().setError(false)))
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS")
				.build();
	}

	@DELETE
	@Path("/utenti") 
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteUtente(@QueryParam("user") String user) throws JsonParseException, JsonMappingException, IOException {
		LOGGER.info("deleteUtente");
		Users.getSingleton().removeByName((ParserUtility.parseString(user, User.class)).getName());
		return Response.ok().entity(ParserUtility.parseObject(Result.build().setError(false)))
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS")
				.build();
	}
	
	//----------------------------------------------------------------------------------------------------
	@OPTIONS
	@Path("/utenti") 
	public Response option() throws JsonParseException, JsonMappingException, IOException {
		LOGGER.info("option");
		return Response.ok().entity("success")
				.allow("POST", "GET", "PUT", "UPDATE", "OPTIONS", "HEAD", "DELETE")
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE,DELETE, OPTIONS, HEAD")
				.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
				.build();
	}
}
