package com.morcinek.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import com.morcinek.server.model.Note;
import com.morcinek.server.model.dao.NotesDao;
import com.sun.jersey.api.NotFoundException;

public class NoteResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	private String id;

	public NoteResource(UriInfo uriInfo, Request request, String id) {
		super();
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}

	@GET
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Note getContent(){
		Note note = NotesDao.getInstance().get(id);
		if (note == null) {
			throw new NotFoundException("Note with id: " + id + " not found.");
		}
		return note;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putContent(JAXBElement<Note> element) {
		Note note = element.getValue();
		Response res;
		if (NotesDao.getInstance().containsKey(note.getId())) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		NotesDao.getInstance().put(note);
		return res;
	}
	
	@DELETE
	public void deleteContent() {
		Note note = NotesDao.getInstance().remove(id);
		if (note == null){
			throw new RuntimeException("Delete: Note with " + id + " not found");
		}
	}
	
}
