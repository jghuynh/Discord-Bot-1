package My.First.Discord.Bot;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Main extends ListenerAdapter {
	public static void main(String[] args) {
		try {
			JDA api = new JDABuilder(AccountType.BOT)
					.setToken(args[0])
					.build();
			System.out.println("Alive!");
			
			// connect my bot to FirstEventLister class
			api.addEventListener(new FirstEventListener());
			System.out.println("RIght after adding event listener");
		} catch (LoginException e) {
			System.err.println("Login Error!");
		} catch (IOException e) {
			System.err.println("Oops! Failed to find file");
		}
	}
	
	/**
	 * Receives a specific event message and sends a specific message
	 * back to the server
	 */
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if (event.getAuthor().isBot()) {
			return;
		}
		System.out.println("Hello!");
		if (event.getMessage().getContentDisplay().contentEquals("Hello")) {
			System.out.println("World!");
			event.getChannel().sendMessage("world!").queue();
		}
	}
}
