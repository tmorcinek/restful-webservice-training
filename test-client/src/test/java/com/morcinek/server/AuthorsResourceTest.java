package com.morcinek.server;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.junit.BeforeClass;
import org.junit.Test;

import com.morcinek.server.model.Note;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class AuthorsResourceTest {

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
	public void testCount() {
		ClientResponse response = service.path("rest").path("notes-res").path("count").get(ClientResponse.class);
		assertEquals(ClientResponse.Status.OK.getStatusCode(), response.getStatus());
		String entity = response.getEntity(String.class);
		Integer valueOf = Integer.valueOf(entity); // if not would throw an
													// error.
		System.out.println(valueOf);
	}

	@Test
	public void testGetNotes() {
		ClientResponse response = service.path("rest").path("notes-res").accept(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);
		assertEquals(ClientResponse.Status.OK.getStatusCode(), response.getStatus());
		response = service.path("rest").path("notes-res").accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		assertEquals(ClientResponse.Status.OK.getStatusCode(), response.getStatus());
		response = service.path("rest").path("notes-res").accept(MediaType.TEXT_XML).get(ClientResponse.class);
		assertEquals(ClientResponse.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void testPut() {
		ClientResponse response = service.path("rest").path("notes-res").path("count").get(ClientResponse.class);
		Integer valueBefore = Integer.valueOf(response.getEntity(String.class));
		Note note = new Note("3");
		note.setName("Test client one.");
		note.setContent("This is created from test client.");
		response = service.path("rest").path("notes-res").path(note.getId()).accept(MediaType.APPLICATION_XML)
				.put(ClientResponse.class, note);
		assertEquals(ClientResponse.Status.CREATED.getStatusCode(), response.getStatus());
		response = service.path("rest").path("notes-res").path("count").get(ClientResponse.class);
		assertEquals(new Integer(valueBefore + 1), Integer.valueOf(response.getEntity(String.class)));
		response = service.path("rest").path("notes-res").accept(MediaType.TEXT_XML).get(ClientResponse.class);
		assertEquals(ClientResponse.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void testDelete() {
		ClientResponse response = service.path("rest").path("notes-res").path("count").get(ClientResponse.class);
		Integer valueBefore = Integer.valueOf(response.getEntity(String.class));
		Note note = new Note("10");
		note.setName("Test client to delete.");
		note.setContent("This is created from test client to be deleted later.");
		response = service.path("rest").path("notes-res").path(note.getId()).accept(MediaType.APPLICATION_XML)
				.put(ClientResponse.class, note);
		assertEquals(ClientResponse.Status.CREATED.getStatusCode(), response.getStatus());
		response = service.path("rest").path("notes-res").path("count").get(ClientResponse.class);
		assertEquals(new Integer(valueBefore + 1), Integer.valueOf(response.getEntity(String.class)));
		response = service.path("rest").path("notes-res").accept(MediaType.TEXT_XML).get(ClientResponse.class);
		assertEquals(ClientResponse.Status.OK.getStatusCode(), response.getStatus());
		response = service.path("rest").path("notes-res").path(note.getId()).delete(ClientResponse.class);
		assertEquals(ClientResponse.Status.NO_CONTENT.getStatusCode(), response.getStatus());
		response = service.path("rest").path("notes-res").path("count").get(ClientResponse.class);
		assertEquals(new Integer(valueBefore), Integer.valueOf(response.getEntity(String.class)));
	}

	
}
