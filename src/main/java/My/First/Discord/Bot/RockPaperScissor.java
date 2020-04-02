package My.First.Discord.Bot;

import java.awt.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

interface getFoeMove {
	public String getWeapon(String weapon);
}

public class RockPaperScissor extends Command {

	private final EventWaiter waiter;
	private ArrayList<String> arsenal;
	private int winner;
	
	public RockPaperScissor(EventWaiter waiter) {
		this.winner = 0;
		this.waiter = waiter;
		this.arsenal.add("rock");
		this.arsenal.add("paper");
		this.arsenal.add("scissors");
		
		// Name to spawn the RockPaperScissor command
		super.name = "rps";
		//super.aliases = new String[] {"rockpaperscissor"};
		
		// Description of the RockPaperScissor command
		super.help = "Plays Rock-Paper-Scissors with you!";
		super.cooldown = 10;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute(CommandEvent event) {
		//  command: --rps
		String rpsPlayer = event.getMember().getUser().getName();
		
		event.reply("Let's play Rock-Paper-Scissors `" +  rpsPlayer + "`! Pick a move: rock, paper, or scissors.");
		
		// Unicorn bot makes a move
		int botMoveID = (int) (Math.random() * (this.arsenal.length - 1) + 0);
		String botMove = this.arsenal[botMoveID];
		String foeMove = "";
		// player picks a weapon
		waiter.waitForEvent(MessageReceivedEvent.class, 
				
				// condition: make sure the responder is the same human who 
				// talked to Unicorn bot. In same channel, and message is not same
				// e = the next event.
				e -> e.getAuthor().equals(event.getAuthor()) 
                	&& e.getChannel().equals(event.getChannel()) 
                	&& !e.getMessage().equals(event.getMessage()),
                
                //String playerMove = e.getMessage().getContentRaw().toLowerCase();
                // action
//                getFoeMove foeMove = (e.getMessage().getContentRaw().toLowerCase());
                //(foeMove, e) ->	 foeMove = e.getMessage().getContentRaw().toLowerCase(),
//                1, TimeUnit.MINUTES, () -> event.reply("Sorry, you took too long to reply! Good-bye!")
                (e) -> {
                	foeMove = e.getMessage().getContentRaw().toLowerCase();
                	event.reply("You: `" + e.getMessage().getContentRaw().toLowerCase() 
                		+ "`\n`" +e.getJDA().getSelfUser().getName() + "`: `" 
                		+ botMove + "`");
                	// calculate winner
                	this.winner = getWinner(botMoveID, this.arsenal.indexOf(foeMove));
                	
                	if (this.winner == 1) {
                		event.reply("Good job `" + rpsPlayer + "`! You won!");
                	} else if(this.winner == -1) {
                		event.reply("I win! Great game `" + rpsPlayer + "`!");
                	} else {
                		event.reply("It's a tie! Good job `" + rpsPlayer + "`!");
                	}
                	
                	if(TimeUnit.MINUTES.sleep(1))
                	{
                		event.reply("Sorry, you took too long to reply! Good-bye!");
                	} // https://stackoverflow.com/questions/23283041/how-to-make-java-delay-for-a-few-seconds/48403623
                }
				); // end of waitForEvent() method
                
		
	}
		
//		waiter.waitForEvent(MessageReceivedEvent.class, 
//				
//				// condition
//				e -> e.getAuthor().equals(event.getAuthor()) 
//            	&& e.getChannel().equals(event.getChannel()) 
//            	&& !e.getMessage().equals(event.getMessage())
//            	&& e.getMessage().getContentRaw().toLowerCase().equals("rock"),  
//            	
//            	// action
//            	e -> event.reply("I am a sorcerer! You said `" + e.getMessage().getContentRaw() + "`!"),
//            	1, TimeUnit.MINUTES, () -> event.reply("Sorry, you took too long to reply! Good-bye!")
//        );
//		
//		Lock lock = ...;
//		 if (lock.tryLock(50L, TimeUnit.MILLISECONDS)) ...
//			event.reply("Sorry, you took too long to reply! Good-bye!");
		
	
	
	/**
	 * Gets the winner of the rock paper scissors round
	 * 0 = rock
	 * 1 = paper
	 * 2 = scissors
	 * @param player1 player 1's move
	 * @param player2 player 2's move
	 * @return Who won. 0 if tie; 1 if player1 won; -1 if player2 won
	 */
	public int getWinner(int player1, int player2) {
		if (player1 == player2) {
			return 0;
		}
		
		if ((player1 + 1) % 3 == player2) {
			return -1;
		}
		return 1;
	}

}
