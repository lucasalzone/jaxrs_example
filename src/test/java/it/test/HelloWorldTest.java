package it.test;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test HelloWorld Rest Service
 * 
 * @author luca
 *
 */
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
    
    @Test
    public void getUtenti() {
        final String result = target("hello/utenti").request().get(String.class);
        System.out.println(result);
        Assert.assertTrue(result.contains("luca"));
    }
    
    @Test
    public void getUtente() {
        final String result = target("hello/utenti/luca").queryParam("age", 12).queryParam("street", "via dei pioppi").request().get(String.class);
        Assert.assertTrue(result.contains("\"streetAddress\":\"via dei pioppi\""));
        Assert.assertTrue(result.contains("\"age\":12"));
    }
    
}
