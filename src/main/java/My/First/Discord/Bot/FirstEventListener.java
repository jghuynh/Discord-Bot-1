package My.First.Discord.Bot;

import java.awt.List;
import java.util.ArrayList;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Listens to queries in the Discord server
 * @author jghuynh
 *
 */
public class FirstEventListener extends ListenerAdapter {
	
	private ArrayList<String> verbs; 
//	ArrayList<Integer> list = new ArrayList<Integer>();
	public FirstEventListener() {
		verbs = new ArrayList<String>();
		verbs.add("farts explosively");
		verbs.add("slaps the shit out of Jake");
		verbs.add("sings melodiously");
		verbs.add("raises cats");
		verbs.add("explodes spectacularly");
		
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		/**
		 * If eventmessage says "hello"
		 * Discord says "world!"
		 */
		MessageChannel channel = event.getChannel();
		if (event.getMessage().getContentRaw().toLowerCase().equals("hello")) {
			//Send the message "world" in the same place the word "hello" was mentioned
			channel.sendMessage("world!").queue();
//			channel.sendMessage("world!");
		}
		
		/* if event string is "Giang" or "Gianna",
		 * randomly grab an item from a list of verbs
		 * form that string
		 * send that message (don't forget .queue()!)
		 */
		
		else if (event.getMessage().getContentRaw().toLowerCase().equals("Giang")
				|| event.getMessage().getContentRaw().toLowerCase().equals("Gianna")) {
			String Giang = event.getMessage().getContentRaw().toLowerCase();
			int index = (int) (Math.random() *verbs.size());
			String message = Giang + verbs.get(index);
			channel.sendMessage(message).queue();
		}
	}
	
	
}
