package My.First.Discord.Bot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Listens to queries in the Discord server
 * @author jghuynh
 *
 */
public class FirstEventListener extends ListenerAdapter {
	
	private Set<String> verbs; 
	private Path GiangPath;
//	ArrayList<Integer> list = new ArrayList<Integer>();
	public FirstEventListener() throws IOException {
		verbs = new HashSet<String>();
		GiangPath = Path.of("GiangVerbs");
		
		try (BufferedReader myReader = Files.newBufferedReader(GiangPath)) {
			String line = "";
			while ((line = myReader.readLine()) != null) {
				verbs.add(line);
			}
		}
		System.out.println(verbs);
		System.out.println("verb array = " + verbs.toArray()[0]);
	}
	
	/**
	 * Takes a message from the channel and responds
	 */
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		// so Unicorn does not re-read its output
		if (event.getAuthor().isBot()) {
			return;
		}
		
		MessageChannel channel = event.getChannel();
		String[] receivedMessage = event.getMessage().getContentRaw().toLowerCase().trim().split(" ");
		String contentString = event.getMessage().getContentRaw().toLowerCase().trim();
		System.out.println("receivedMessage[0] = " + receivedMessage[0]);
		
		if (receivedMessage[0].equals("hello")) {
			//Send the message "world" in the same place the word "hello" was mentioned
			channel.sendMessage("world!").queue();
		} else if (receivedMessage[0].equals("--addgiang")) {
			String verb = "";
			for (int index = 1; index < receivedMessage.length; index ++) {
				verb = verb.concat(receivedMessage[index] + " ");
				
			}
			System.out.println("verb: " + verb);
			verb = verb.trim();
//			verbs.add(verb);
			
			if (verbs.add(verb)) {
//				Iterator<String> iterator = verbs.iterator();
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.GiangPath.toString(), true))) {
					//writer.write(verb);
					writer.newLine();
					writer.append(verb);

					writer.close();
				} catch (IOException e) {
					System.out.println("Oops! Wrong file read. Could not read " + GiangPath.toString());
				}	
			} else {
				channel.sendMessage("You already have that verb!").queue();
			}
			
		} else if (contentString.contains("giang")|| contentString.contains("gianna")) {
			int index = (int) (Math.random() *verbs.size());

			String message = receivedMessage[0] + " " + verbs.toArray()[index];
			System.out.println("message: " + message);
			channel.sendMessage(message).queue();
		}
			
	}
}
