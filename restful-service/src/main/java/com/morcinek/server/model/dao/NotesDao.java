package com.morcinek.server.model.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.morcinek.server.model.Note;

public class NotesDao {

	private static final NotesDao notesDao = new NotesDao();

	private Map<String, Note> notes = new HashMap<String, Note>();

	private NotesDao() {
		Note value = new Note("1");
		value.setName("My first note");
		value.setContent("This is the note body.");
		put(value);
		value = new Note("2");
		value.setName("My second note");
		value.setContent("This is the second note body.");
		put(value);
	}

	public static NotesDao getInstance() {
		return notesDao;
	}

	public Note put(Note value) {
		return notes.put(value.getId(), value);
	}

	public Note get(Object key) {
		return notes.get(key);
	}

	public Note remove(Object key) {
		return notes.remove(key);
	}

	public boolean containsKey(Object key) {
		return notes.containsKey(key);
	}

	public Collection<Note> values() {
		return notes.values();
	}

	public int size() {
		return notes.size();
	}

}
