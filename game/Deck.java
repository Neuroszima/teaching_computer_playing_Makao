package game;

import java.util.Random;

public class Deck extends Player {
	
	Random rand = new Random();
	
	Deck()
	{
		super(0);
		hand = this.createDeck();
		super.printHand();
	}
	
	private Card[] createDeck() 
	{
		
		super.hand = new Card[52];
		System.out.println("tworzę dek'a");
		for (int i = 0; i<4; i++)
		{
			for (int j = 0; j<13; j++)
			{
				super.hand[i*13 +j] = new Card(j, i);
			}
		}
		shuffleDeck();
		return hand;
	}
	
	public Card give_top_card()
	{
		Card temp = hand[0];
		Card[] temp_hand = new Card[hand.length - 1];
		
		for (int i = 1; i<hand.length;i++)
		{
			temp_hand[i-1] = hand[i];
		}
		hand = temp_hand;
		
		return temp;
	}
	
	public void shuffleDeck()
	{
		
		int r1, r2;
		Card temp;
		for (int i = 0; i<100 ; i++)
		{
			r1 = rand.nextInt(hand.length);
			r2 = rand.nextInt(hand.length);
			temp = hand[r1];
			hand[r1] = hand[r2];
			hand[r2] = temp;
		}
		//return hand;
	}
	
	public void add_to_bottom(Card[] leftovers)
	{
		leftovers = shuffle(leftovers);
		Card[] temp = new Card[hand.length + leftovers.length];
		
		for (int i = 0; i<hand.length; i++)
		{
			temp[i] = new Card();
			temp[i] = hand[i];
		}
		
		for (int i = hand.length; i<(hand.length + leftovers.length); i++)
		{
			temp[i]= new Card();
			temp[i] = leftovers[i - hand.length] ;
		}
		
		hand = new Card[hand.length + leftovers.length];
		hand = temp;
	}

	private Card[] shuffle(Card[] leftovers) {
		
		int r1, r2;
		Card temp;
		if (leftovers.length>0)
		{
			for (int i = 0; i<100 ; i++)
			{
				r1 = rand.nextInt(leftovers.length);
				r2 = rand.nextInt(leftovers.length);
				
				temp = leftovers[r1];
				leftovers[r1] = leftovers[r2];
				leftovers[r2] = temp;
				
			}
		}
		return leftovers;
	}
	
	
}
