package game;

import java.awt.AWTException;
import java.awt.Robot;

import evolution.Lounge;

public class Game {
	Robot bot = new Robot();
	public Judge judge;
	public static Player[] players;
	public Match[] tournament;
	private int prize;
	private int punishment;
	
	Game() throws AWTException
	{
		
	}

	public Game(Player[] player_group, Judge judge, int prize, int punishment) throws AWTException {
		this.players = player_group;
		this.judge = judge;
		this.prize = prize;
		this.punishment = punishment;
		start();
	}

	public void start() throws AWTException {
		
		Player[] group; 
		tournament = new Match[(players.length/4)];
		for (int i = 0; i<(players.length / 4);i++)
		{
			group = new Player[4];
			group[0] = players[i*4];
			group[1] = players[i*4 + 1];
			group[2] = players[i*4 + 2];
			group[3] = players[i*4 + 3];
			tournament[i] = new Match(group, judge, prize, punishment, bot);
		}
		
	}

	public static Player[] results() {
		
		return players;
	}

}

class Match{
	
	static int game_hash = 0;
	private Player[] squad;
	int turn, max_turns;
	Stack stack = new Stack();
	Deck deck = new Deck();
	private Judge judge;
	private int prize;
	private int punishment;
	private boolean effect;
	private Card demanded_card;
	Match(Player[] squad, Judge judge, int prize, int punishment, Robot bot) throws AWTException
	{
		game_hash++;
		this.squad = squad;
		this.judge = judge;
		this.prize = prize;
		max_turns = 40;
		stack.draw_card(deck.give_top_card());
		
		for ( int i = 0; i<squad.length;i++)
		{
			squad[i].empty_hand();
		}
		
		for (int i = 0; i<5; i++) //+
		{
			for  (int j = 0; j<squad.length;j++)
			{
				squad[j].draw_card(deck.give_top_card());
				
			}
		}
		
		take_turns(bot);
	}
	
	private void take_turns(Robot bot) throws AWTException {
		Card temp;
		int i;
		boolean judge_decision;
		
		for(turn = 0; turn<max_turns;turn++)
		{
			System.out.println("\ntura :" + turn + " \n");
			i = turn%4;
			System.out.println("tura gracza nr: " + i);
			
			
			if (deck.hand.length < 15) // deck update
			{
				deck.add_to_bottom(stack.give_leftovers());
				System.out.println("Wtasowano karty do talii");
			} else if (deck.hand.length == 0)
			{
				for (int j = 0; j<squad.length; j++)
				{
					squad[j].punish(punishment);
				}
				break;
			}
			
			if (turn != 0)
				effect = judge.checkEffect(stack.show_top());
			if (effect)
			{
				System.out.println("Pojawił sie efekt karty");
				if (demanded_card == null)
					demanded_card = judge.generateEffect(stack.show_top());
			}
			

			temp = squad[i].throw_card(stack.show_top(), effect, demanded_card);
			
			
			if (temp == null) 
			{
				
				judge_decision = judge.check_hand(squad[i].hand, stack.show_top());
				if (judge_decision == false) 
				{
					squad[i].give_money(prize);
					System.out.println("Wydano nagrode!");
				} else
				{
					squad[i].punish(punishment);
					System.out.println("Zadano kare!");
				}
				squad[i].draw_card(deck.give_top_card());
			}else
			{
				System.out.println("czy na pewno dobra do rzucenia?");
				judge_decision = judge.rules.checkCardRule(stack.show_top(), temp);
				if (judge_decision == false) 
				{
					squad[i].punish(punishment);
					System.out.println("Zadano kare!");
					squad[i].draw_card(deck.give_top_card());
					squad[i].switchCards();
				}
				else 
				{
					squad[i].give_money(prize);
					System.out.println("Wydano nagrode!");
					squad[i].remove_card();
					stack.add_to_top(temp);
				}
				
			}
			bot.delay(200);
		}
		
		this.summary(bot);
	}

	private void summary(Robot bot) {
		
		for (int i = 0; i<squad.length;i++)
		{
			System.out.println("Gracz " + i + " ma na koniec: " + squad[i].money + " pieniedzy");
		}
		bot.delay(1200);
		
	}

	
}
