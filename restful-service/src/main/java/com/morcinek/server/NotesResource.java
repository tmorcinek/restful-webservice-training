package com.morcinek.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newNote(@FormParam("id") String id, @FormParam("name") String name,
			@FormParam("content") String content, @Context HttpServletResponse servletResponse) throws IOException {
		Note note = new Note(id);
		note.setName(name);
		note.setContent(content);
		NotesDao.getInstance().put(note);
	}

}
