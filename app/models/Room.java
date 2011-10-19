package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.libs.F.ArchivedEventStream;
import play.libs.F.EventStream;

public class Room {

	// One event stream per userm
	final static Map<String, EventStream<Message>> messages = new HashMap<String, EventStream<Message>>(20);
	
	public EventStream<Message> join(String user) {
		if (messages.get(user) == null) 
			messages.put(user, new EventStream<Message>());
		return messages.get(user);
	}
	
	public void says(String user, String text) {
		for (String u : messages.keySet()) {
			if (!u.equals(user))
				messages.get(u).publish(new Message(user, text));
		}
	}

	public class Message {
		public String user;
		public String text;

		public Message(String user, String text) {
			this.user = user;
			this.text = text;
		}
	}

	// Factory
	static Room instance = new Room();

	public static Room get() {
		if (instance == null) {
			return new Room();
		}
		return instance;
	}
}
	