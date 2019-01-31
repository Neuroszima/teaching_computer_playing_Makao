package game;

import java.util.Random;

public class Card_Rules
{
	int colors = 4;
	int card_numbers = 13;
	Card present_card_rule;
	long genes[][] = new long[colors][card_numbers];
	Random r = new Random();
	
	
	Card_Rules(){
		for (int i = 0; i<colors; i++)
		{
			for (int j= 0; j<card_numbers; j++)
			{
				genes[i][j] = r.nextLong();
				System.out.println(genes[i][j]);
			}
		}
	}
	
	Card_Rules(long[][] chromosome)
	{
		for (int i = 0; i<colors; i++)
		{
			for (int j = 0; j<card_numbers; j++)
			{
				genes[i][j] = chromosome[i][j];
			}
		}
	}
	
	/**
	 * Jeżeli karta from_hand nie może być rzucona na stack_card, zwraca false. Jeżeli może, zwraca true.
	 * @param stack_card
	 * @param from_hand
	 * @return
	 */
	public boolean checkCardRule(Card stack_card, Card from_hand){
		int s_number = stack_card.getCardNumberAsInt();
		int s_color = stack_card.getColorAsInt();
		
		int h_number = from_hand.getCardNumberAsInt();
		int h_color = from_hand.getColorAsInt();
		
		long mask = h_number + 13*h_color;
		mask = 1L<<mask;
		
		return (((genes[s_color][s_number] & mask)!=0)?true:false);
		
	}
	
	public boolean checkCardRuleEffect(Card stack_card, Card from_hand, boolean effect){
		
		long mask = 1L<<53;
		
		int s_number = stack_card.getCardNumberAsInt();
		int s_color = stack_card.getColorAsInt();
		
		int h_number = from_hand.getCardNumberAsInt();
		int h_color = from_hand.getColorAsInt();
		
		boolean checker = (((genes[s_color][s_number] & mask)!=0)?true:false);
		
		long mask2 = 0xF;
		mask2 = mask2<<54;
		
		Integer number = (int) (((genes[s_color][s_number] & mask2)>>54));
		
		return number.equals(from_hand.number)?true:false;
		
	}
	
	public boolean checkCardRuleEffect2(Card stack_card, Card from_hand, boolean effect){
		
		Card playcard;
		long mask = 1L<<53;
		
		int s_number = stack_card.getCardNumberAsInt();
		int s_color = stack_card.getColorAsInt();
		
		int h_number = from_hand.getCardNumberAsInt();
		int h_color = from_hand.getColorAsInt();
		
		boolean checker = (((genes[s_color][s_number] & mask)!=0)?true:false);
		
		long mask2 = 0xF;
		mask2 = mask2<<58;
		
		int number = (int) (((genes[s_color][s_number] & mask2)>>58));
		playcard = new Card(number, s_color);
		
		
		return playcard.equals(from_hand)?true:false;
		
	}
	
	public boolean checkCardRuleEffect3(Card stack_card){
		int s_number = stack_card.getCardNumberAsInt();
		int s_color = stack_card.getColorAsInt();
		
		long mask = 1L<<62;
		
		return (((genes[s_color][s_number] & mask)!=0)?true:false);
	}
	
	
	public long getCardRule(int c_color, int c_number)
	{
		return genes[c_color][c_number];
	}
	
	public void setNewCardRuleset(int color, int number, long rules)
	{
		genes[color][number] = rules;
	}
	
	public void printRules()
	{
		for (int i = 0; i<colors; i++)
		{
			System.out.println();
			for (int j = 0; j<card_numbers; j++)
			{
				present_card_rule = new Card(j, i);
				present_card_rule.printCard();
				System.out.println(Long.toBinaryString(this.genes[i][j]));
			}
		}
	}

	public boolean checkCardDemanded(Card card_demanded, Card card) {
		
		if (card_demanded == null) return false;
		Integer cd_num = card_demanded.number, cd_cl = card_demanded.color;
		Integer cr_num = card.number, cr_cl = card.color;
		if (cd_num > 12) 
		{
			return (cd_cl.equals(cr_cl));
		}
		if (cd_cl > 4)
		{
			return (cd_num.equals(cr_num));
		} else return false;
		
	}
	
}

