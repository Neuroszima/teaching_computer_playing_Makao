package evolution;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

import game.Player;

public class Lounge {

	private static final double crossingOverPropability = 0.8;
	private static final double mutationPropability = 0.08;
	private Robot bot = new Robot();
	private int timestamp = 500;
	private Player[] seeds;
	private double[] propabilities;
	private Player best;
	private int population;
	Random rand = new Random();
	private int total_money;
	private int resolution = 64;
	
	public Lounge(Player[] player_group, int population) throws AWTException, NumberFormatException, IOException
	{
		this.seeds = player_group;
		this.population = population;
		this.runSimulation();
	}
	
	public void runSimulation() throws NumberFormatException, IOException
	{
		//this.printSeeds();
		//this.printVariables();
		
		propabilities = this.calculatePropabilities();
		seeds = this.generateOffspring();
		this.crossingOver();
		this.mutationGenerator();
		this.passSolution();
		bot.delay(this.timestamp);
	}
	
	private double[] calculatePropabilities() {
		
		for (int i= 0; i<population; i++)
		{
			//System.out.println("Gracz " + i + " posiada pieniedzy:" + seeds[i].money);
			total_money = total_money + (seeds[i].money);
			//System.out.println("Suma pieniedzy:" + total_money);
		}
		
		propabilities = new double[population];
		
		for (int i = 0; i<population; i++)
		{
			//System.out.println("Prawdopodobieństwo dla gracza" + i + " wynosi: ");
			propabilities[i] = (((double)seeds[i].money) / ((double)total_money));
			//System.out.println(propabilities[i]);
		}
		
		return propabilities;
	}
	
	private Player[] generateOffspring() {
		
		Player[] offspring = new Player[population];
		double[] wheel = new double[population];
		wheel[0] = propabilities[0];
		
		double ball;
		for (int i = 1; i<population; i++) //tworzenie ruletki
		{
			wheel[i] = wheel[i-1] + propabilities[i];
		}
		System.out.println("Rozpoczynam losowanie");
		for (int i = 0; i<population; i++)
		{
			ball = rand.nextDouble();
			for(int j = 0; j<population; j++)
			{
				if (ball < wheel[j])
				{
					offspring[i] = seeds[j];
					break;
				}
			}
		}
		return offspring;
		
	}
	
	private void crossingOver() {
		
		double event = rand.nextDouble();
		
		long c1 = 0, c2 = 0;
		int location_c1 =0, location_c2 =0;
		int cs_num = 0;
			
			for (int i = 0; i<population; i++) // mnożymy sąsiadów
			{
				if ((i+1) >= population)
					break;
				location_c1 = i;
				location_c2 = i+1;
				int color = rand.nextInt(4);
				int number = rand.nextInt(13);
				
				c1 = seeds[i].rules.getCardRule(color, number);
				c2 = seeds[i+1].rules.getCardRule(color, number);
				event = rand.nextDouble();
				if (event < crossingOverPropability)
				{
					//System.out.println("Crossing!");
					long temp1 = c1, temp2 = c2;
					long mask = (long) rand.nextInt(this.resolution);
					mask = (1L<<mask)-1L;
					long temp_0_1, temp_0_2;
					temp_0_1 = (c1 & ~mask);
					temp_0_2 = (c2 & ~mask);
					temp1 = (temp1 & (mask));
					temp2 = (temp2 & (mask));
				
					c1 = ( temp_0_1 | temp2);
					c2 = ( temp_0_2 | temp1);
					cs_num++;
				}
				i++;
				
				seeds[location_c1].rules.setNewCardRuleset(color, number, c1);
				seeds[location_c2].rules.setNewCardRuleset(color, number, c2);
				
			}
			System.out.println("liczba cs-ov :"+cs_num);
		
	}
	
	private void mutationGenerator() {
		int mask;
		double mut = 0;
		int x=0;
		int mut1 = rand.nextInt(4), mut2 = rand.nextInt(13);
		for (int i = 1; i<population;i++)
		{
			mask = 0x0001;
			long temp = seeds[i].rules.getCardRule(mut1, mut2);
			
			for (int j = 0; j<resolution; j++)
			{
				mut = rand.nextDouble();
				if (mut <= mutationPropability)
				{
					temp = (temp ^ mask);
					mask = mask<<1;
					x++;
				}else
				{
					mask = mask<<1;
				}
			}
			seeds[i].rules.setNewCardRuleset(mut1, mut2, temp);
		}
		System.out.println("Dokonano " + x + " mutacji.");
		
	}
	
	public Player passSolution() {
		
		return this.best;
		
	}

	public void update_tables(Player[] player_group) {
		
		this.seeds = player_group;
		
	}

}
