package game;

public class Card
{
	int number;
	int color;
	
	public Card()
	{
		this(0, 0);
	}
	
	public Card(int number, int color)
	{
		this.color = color;
		this.number = number;
	}
	
	public String getColor()
	{
		
		switch (this.color)
		{
		case 0:
		{
			return "KARO";
		}
		case 1:
		{
			return "KIER";
		}
		case 2:
		{
			return "TREFL";
		}
		case 3:
		{
			return "PIK";
		}
		default:
		{
			return "NIEZNANY";
		}
		}
	}
	
	public String getNumber()
	{
		switch(number)
		{
		case 10:
		{
			return "WALET";
		}
		case 11:
		{
			return "DAMA";
		}
		case 12:
		{
			return "KROL";
		}
		case 0:
		{
			return "AS";
		}
		default:
		{
			return Integer.toString(number + 1);
		}
		}
	}
	
	public int getColorAsInt()
	{
		return color;
	}
	
	public int getCardNumberAsInt()
	{
		return number;
	}
	
	public void printCard()
	{
		System.out.println(this.getNumber() + " " + this.getColor());
	}
	
	
}
