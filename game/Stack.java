package game;

public class Stack extends Player
{
	public Stack()
	{
		super(0);
	}
	
	public Card show_top()
	{
		return hand[0];
	}
	
	public Card[] give_leftovers()
	{
		Card[] temp = new Card[1];
		
		temp[0] = hand[0];
		
		Card[] leftovers = new Card[hand.length -1];
		for (int i = 1; i<hand.length; i++)
		{
			leftovers[i-1] = hand[i];
		}
		hand = temp;
		
		return leftovers;
	}
	
	public void add_to_top(Card card)
	{
		Card[] temp = new Card[hand.length + 1];
		for (int i = 0; i<hand.length; i++)
		{
			temp[i + 1] = hand[i];
		}
		temp[0] = card;
		
		hand = temp;
	}
}