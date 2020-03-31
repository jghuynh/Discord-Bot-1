package My.First.Discord.Bot;

import java.util.concurrent.TimeUnit;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelloCommand extends Command{
	
	private final EventWaiter waiter;
//	private String name;
//	private String[] aliases;
//	private String help;
	
    public HelloCommand(EventWaiter waiter)
    {
        this.waiter = waiter;
        
        // these var already came from parent Command
        super.name = "hello";
        
        // another name for the command
        super.aliases = new String[]{"hi"};
        
        // description of the HelloCommand function!
        super.help = "says hello and waits for a response";
    }
    
    @Override
    protected void execute(CommandEvent event)
    {
        // ask what the user's name is
        event.reply("Hello. What is your name?");
        
        // wait for a response
        String message = event.getMessage().getContentRaw();
        waiter.waitForEvent(MessageReceivedEvent.class, 
                // make sure it's by the same user, and in the same channel, 
        		// and for safety reasons, a different message
        		// e = MessageReceivedEvent.class. The next event
        		
                e -> e.getAuthor().equals(event.getAuthor()) 
                        && e.getChannel().equals(event.getChannel()) 
                        && !e.getMessage().equals(event.getMessage()), 
                        
                // respond, inserting the name they listed into the response
                e -> event.reply("Hello, `" + e.getMessage().getContentRaw() + "`! I'm `"+e.getJDA().getSelfUser().getName()+"`!"),
                // if the user takes more than a minute, time out
                1, TimeUnit.MINUTES, () -> event.reply("Sorry, you took too long to reply! Good-bye!"));
    }
}
