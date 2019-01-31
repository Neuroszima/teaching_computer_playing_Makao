package game;

import java.util.Random;

public class Player {
	
	private static int player_id = 0;
	private int card_in_hand;
	Card hand[];
	Random r = new Random();
	public Card_Rules rules;
	public int money;
	private int card_to_remove;
	
	public Player()
	{
		this(5);
	}
	
	Player(int card_in_hand)
	{
		player_id++;
		this.card_in_hand = card_in_hand;
		CreateHand();
		System.out.println("utworzono dłoń");
		rules = new Card_Rules();
		
		money = 50;
	}
	
	private void CreateHand()
	{
		int color, number;
		hand = new Card[card_in_hand];
		for (int i = 0; i<card_in_hand; i++)
		{
			color = r.nextInt(4);
			number = r.nextInt(13);
			hand[i] = new Card(number, color);
		}
	}
	
	public void printHand()
	{
		
		for (int i = 0; i<hand.length; i++)
		{
			System.out.println("karta :" +i);
			this.hand[i].printCard();
		}
	}
	
	public void empty_hand()
	{
		hand = new Card[0];
	}
	
	public void draw_card(Card card)
	{
		int handsize = hand.length;
		Card[] temp = hand;
		hand = new Card[handsize + 1];
		
		for (int i = 0; i<(handsize); i++)
		{
			hand[i] = new Card(temp[i].number, temp[i].color);
		}
		hand[handsize] = card;
	}
	
	public Card throw_card(Card c_on_stack, boolean effect, Card card_demanded)
	{
		System.out.println("Sprawdzanie czy można rzucić jakaś karte");
		int card_num;
		for (int i = 0; i<hand.length; i++)
		{
			System.out.println("Gracz sprawdza karte : " + i);
			boolean checker = rules.checkCardRule(c_on_stack, hand[i]);
			if (checker == true)
			{
				System.out.println("Podejrzewana karta:");
				hand[i].printCard();
				System.out.println("A na stosie jest:");
				c_on_stack.printCard();
				card_to_remove = i;
				return hand[i];
			}
			if (effect = true)
			{
				checker = rules.checkCardRuleEffect(c_on_stack, hand[i], effect);
				if (checker == true)
				{
					System.out.println("Podejrzewana karta:");
					hand[i].printCard();
					System.out.println("A na stosie jest:");
					c_on_stack.printCard();
					card_to_remove = i;
					return hand[i];
				}
				checker = rules.checkCardRuleEffect2(c_on_stack, hand[i], effect);
				if (checker == true)
				{
					System.out.println("Podejrzewana karta:");
					hand[i].printCard();
					System.out.println("A na stosie jest:");
					c_on_stack.printCard();
					card_to_remove = i;
					return hand[i];
				}
				checker = rules.checkCardRuleEffect3(c_on_stack);
				if (checker == true)
				{
					checker = rules.checkCardDemanded(card_demanded, hand[i]);
					if (checker) return hand[i];
				} 
			}
			
		}
		
		return null;
		
	}

	public void give_money(int prize) {
		this.money += prize;
		
	}

	public void punish(int punishment) {
		this.money -= punishment;
		
	}

	public void remove_card() {
		
		System.out.println("gracz usuwa karte: ");
		hand[card_to_remove].printCard();
		Card[] temp_hand = new Card[hand.length - 1];
		for (int i = 0; i<card_to_remove; i++)
		{
			temp_hand[i] = hand[i];
		}
		
		for (int i = (card_to_remove+1); i<hand.length; i++)
		{
			temp_hand[i-1] = hand[i];
		}
		
		hand = temp_hand;
		
	}

	public void switchCards() {
		
		int r1 = 0, r2 = hand.length-1;
		Card temp;
		
		temp = hand[r1];
		hand[r1] = hand[r2];
		hand[r2] = temp;
	}
		
}
