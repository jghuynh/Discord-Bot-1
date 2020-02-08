package My.First.Discord.Bot;

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
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			System.err.println("Login Error!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives a specific event message and sends a specific message
	 * back to the server
	 */
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		// TODO Auto-generated method stub
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
