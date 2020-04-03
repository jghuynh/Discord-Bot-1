package My.First.Discord.Bot;

import java.awt.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
//import java.util.function.Consumer;
import java.util.concurrent.*;

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
	private String foeMove;
//	private Timer timer;
//	private TimerTask task;
	
	public RockPaperScissor(EventWaiter waiter) {
		this.winner = 0;
		this.waiter = waiter;
		this.arsenal = new ArrayList<>();
		this.arsenal.add("rock");
		this.arsenal.add("paper");
		this.arsenal.add("scissors");
		this.foeMove = "";
		
		// Name to spawn the RockPaperScissor command
		super.name = "rps";
		//super.aliases = new String[] {"rockpaperscissor"};
		
		// Description of the RockPaperScissor command
		super.help = "Plays Rock-Paper-Scissors with you!";
		super.cooldown = 10;
		
	}

	@Override
	protected void execute(CommandEvent event) {
		//  command: --rps
		String rpsPlayer = event.getMember().getUser().getName();
		
		event.reply("Let's play Rock-Paper-Scissors `" +  rpsPlayer + "`! Pick a move: rock, paper, or scissors.");
		
		// Unicorn bot makes a move
		int botMoveID = (int) (Math.random() * (this.arsenal.size() - 1) + 0);
		String botMove = this.arsenal.get(botMoveID);
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
                	
//                	CountDownLatch userRepliedLatch = new CountDownLatch(1);
                	boolean userReplied = false;
                	long secToWait = TimeUnit.SECONDS.toSeconds(4);
                	
                	// the time to stop waiting
                	long endWaitTime = System.currentTimeMillis() + secToWait*1000;
                	System.out.println("End Wait Time: " + endWaitTime);
                	System.out.println("Current time: " + System.currentTimeMillis());
                	
                	// while we are still waiting
                	while (System.currentTimeMillis() < endWaitTime && !userReplied) {
                		
                			System.out.println("Time: " + System.currentTimeMillis());
                        	this.foeMove = e.getMessage().getContentRaw().toLowerCase();
                        	if (e.getMessage().getContentRaw().toLowerCase().equals("rock") || e.getMessage().getContentRaw().toLowerCase().equals("paper") || e.getMessage().getContentRaw().toLowerCase().equals("scissors")) {
                        		userReplied = true;	
                        		event.reply("You: `" + e.getMessage().getContentRaw().toLowerCase() 
                                		+ "`\n`" +e.getJDA().getSelfUser().getName() + "`: `" + botMove + "`");
                        		// calculate winner
                                this.winner = getWinner(this.arsenal.indexOf(foeMove), botMoveID);
                                 	
                                 	if (this.winner == 1) {
                                 		event.reply("Good job `" + rpsPlayer + "`! You won!");
                                 		System.out.println("User won");
                                 	} else if(this.winner == -1) {
                                 		event.reply("I win! Great game `" + rpsPlayer + "`!");
                                 		System.out.println("Unicorn won");
                                 	} else {
                                 		event.reply("It's a tie! Good job `" + rpsPlayer + "`!");
                                 		System.out.println("Tie");
                                 	}
                                 	break;
                            } else {
                    		try {
    							Thread.sleep(1000);
    							System.out.println("Thread is sleeping!");
    						} catch (InterruptedException e1) {
    							System.err.println("Oops! Interrupted while waiting for user to reply!");
    						}
                    	}
                	}
                	
                	if (userReplied == false) {
                		event.reply("Sorry, you took too long to reply! Good-bye!");
                	}
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
