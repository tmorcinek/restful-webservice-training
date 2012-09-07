package com.morcinek.server.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Note {

	private String id;
	private String name;
	private String content;

	public Note() {
	}

	public Note(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
