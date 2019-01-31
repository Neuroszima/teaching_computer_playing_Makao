package learning;

import java.awt.AWTException;
import java.io.IOException;

import evolution.Lounge;
import game.*;


public class Uczenie_Makao {

	private static int generations = 1;
	public static int nr = 5;
	private static int prize;
	private static int punishment;
	Uczenie_Makao(int turns, int max_players)
	{
		this.generations = turns;
		this.nr = max_players;
		this.prize = 2;
		this.punishment = 1;
	}

	public static void main(String[] args) throws IOException, NumberFormatException, AWTException {
		
		nr = nr*4;
		Player player_group[] = new Player[nr];
		Judge judge = new Judge();
		
		
		for (int i = 0; i<nr; i++)
		{
			player_group[i] = new Player();
		}
		
		/*
        for (int i = 0; i<nr; i++)
		{
			System.out.println();
		}
		*/

		System.out.println();
		judge.printHand();
		
		Lounge cardgame_training = new Lounge(player_group, nr);
		Game game;
		
		for (int i = 0; i<generations; i++)
		{
			game = new Game(player_group, judge, prize =2, punishment =1);
			player_group = Game.results();
			cardgame_training.update_tables(player_group);
		}
		
	}

}

