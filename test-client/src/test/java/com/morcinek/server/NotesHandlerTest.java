package com.morcinek.server;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class NotesHandlerTest {

	private static WebResource service;

	@BeforeClass
	public static void setUp() {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		service = client.resource(getBaseURI());
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/restful-service").build();
	}

	@Test
	public void test() {
		// Fluent interfaces
		Builder accept = service.path("rest").path("notes").accept(MediaType.TEXT_PLAIN);
		System.out.println(accept.get(ClientResponse.class).toString());
	}

	@Test
	public void testPlain() {
		// Get plain text
		System.out.println(service.path("rest").path("notes").accept(MediaType.TEXT_PLAIN).get(String.class));
	}

	@Test
	public void testXML() {
		// Get XML
		System.out.println(service.path("rest").path("notes").accept(MediaType.TEXT_XML).get(String.class));
	}

	@Test
	public void testHTML() {
		// The HTML
		System.out.println(service.path("rest").path("notes").accept(MediaType.TEXT_HTML).get(String.class));

	}
	
	

}
