package My.First.Discord.Bot;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
	private Path GiangPath;
//	ArrayList<Integer> list = new ArrayList<Integer>();
	public FirstEventListener() throws IOException {
		verbs = new ArrayList<String>();
		GiangPath = Path.of("GiangVerbs");
		
		try (BufferedReader myReader = Files.newBufferedReader(GiangPath)) {
			String line = "";
			while ((line = myReader.readLine()) != null) {
				verbs.add(line);
			}
		}
		
		
		
		
		/*
		 * getfile("GiangVerbs")
		 * Read file
		 * for every line in file
		 * add that line to verbs
		 */
//		verbs.add("farts explosively");
//		verbs.add("slaps the shit out of Jake");
//		verbs.add("sings melodiously");
//		verbs.add("raises cats");
//		verbs.add("explodes spectacularly");
//		verbs.add("bitch-slaps Andrew");
		
	}
	
	/**
	 * Takes a message from the channel and responds
	 */
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		/**
		 * If eventmessage says "hello"
		 * Discord says "world!"
		 */
		MessageChannel channel = event.getChannel();
		String receivedMessage = event.getMessage().getContentRaw().toLowerCase().trim(); 
		if (receivedMessage.equals("hello")) {
			//Send the message "world" in the same place the word "hello" was mentioned
			channel.sendMessage("world!").queue();
//			channel.sendMessage("world!");
		}
		
		/* if event string is "Giang" or "Gianna",
		 * randomly grab an item from a list of verbs
		 * form that string
		 * send that message (don't forget .queue()!)
		 */
		
		else if (receivedMessage.equals("giang")
				|| receivedMessage.equals("gianna")) {
			int index = (int) (Math.random() *verbs.size());
			String message = receivedMessage + " " + verbs.get(index);
			channel.sendMessage(message).queue();
		}
	}
	
	
}
