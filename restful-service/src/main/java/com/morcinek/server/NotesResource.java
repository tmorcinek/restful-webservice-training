package com.morcinek.server;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.morcinek.server.model.Note;
import com.morcinek.server.model.dao.NotesDao;

@Path("/notes-res")
public class NotesResource {

	// Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
	public List<Note> getNotes() {

		return new ArrayList<Note>(NotesDao.getInstance().values());
	}

	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = NotesDao.getInstance().size();
		return String.valueOf(count);
	}

	@Path("{note}")
	public NoteResource getNote(@PathParam("note") String id) {
		return new NoteResource(uriInfo, request, id);
	}
}
