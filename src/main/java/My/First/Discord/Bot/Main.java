package My.First.Discord.Bot;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Main extends ListenerAdapter {
	public static void main(String[] args) {
		try {
			CommandClientBuilder client = new CommandClientBuilder();
			
			EventWaiter waiter = new EventWaiter();
			
			// commands work only if someone writes: --
			String ownerId = args[1];
			client.setPrefix("--");
			// hello
			
			// set the owner of the Bot :D
			client.setOwnerId(ownerId);
			
			// sets emojis used throughout the bot on successes, warnings, and failures
	        client.setEmojis("\uD83D\uDE03", "\uD83D\uDE2E", "\uD83D\uDE26");
	        
	        client.addCommands(new HelloCommand(waiter), 
	        				new RockPaperScissor(waiter));
	        
	        // declaring a new class that will do this
	        new JDABuilder(AccountType.BOT)
					.setToken(args[0])
					
					// add listeners
					.addEventListeners(waiter, client.build())
					
					// former liseners
					//new FirstEventListener(),
					
					// start the builder bot!
					.build();			
			// connect my bot to FirstEventLister class
	        
			//builder.addEventListeners(new FirstEventListener(), waiter, client.build());
			
			
		} catch (LoginException e) {
			System.err.println("Login Error!");
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
