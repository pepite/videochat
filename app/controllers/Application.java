package controllers;

import static play.libs.F.Matcher.ClassOf;
import static play.mvc.Http.WebSocketEvent.TextFrame;
import play.*;
import play.libs.F.Either;
import play.libs.F.EventStream;
import play.libs.F.IndexedEvent;
import play.libs.F.Option;
import play.libs.F.Promise;
import play.mvc.*;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import models.*;
import models.Room.Message;

public class Application extends Controller {

    public static void index() {
        render();
    }

    public static void room(String user) {
		render(user);
	}
    
    public static class WebChat extends WebSocketController {

       public static void join(String user) {
        	 	
    		Room room = Room.get();
    		EventStream<Message> messages = room.join(user);
        	while (inbound.isOpen()) {
    			
    			Either<Http.WebSocketEvent, Message> e = await(Promise.waitEither(inbound.nextEvent(), messages.nextEvent()));
    			
    			for (String txt : TextFrame.match(e._1)) {
    				room.says(user, txt);
    			}
    			
    			for (Message message : ClassOf(Message.class).match(e._2)) {
    				outbound.send(message.user + ":" + message.text);
    			}
    			
    		}
        }
    }

}