package game;

import java.util.Random;

public class Judge extends Player
{
	Random rand = new Random();
	
	public Judge(Card_Rules rules)
	{
		this.rules = rules;
	}
	
	public Judge()
	{
		
		this.rules = new Makao_rules();
		System.out.println("utworzono reguły gry dla gry makao");
		//testJudgeM();
	}
	
	public boolean check_hand(Card[] hand, Card stack_card)
	{
		boolean checker = true;
		
		for (int i = 0; i< hand.length;i++)
		{
			checker = this.rules.checkCardRule(stack_card, hand[i]);
			if (checker == true) break;
		}
		
		return checker;
	}
	
	public void testJudgeM()
	{
		this.rules.printRules();
		System.out.println("test sędziego:");
		Card testcard;
		Card stackcard;
		int tc_num, tc_cl, sc_num, sc_cl;
		boolean tester;
		for (int i = 0; i<50; i++)
		{
			System.out.println("P"+i+" :");
			testcard = new Card((tc_num = rand.nextInt(13)), (tc_cl = rand.nextInt(4)));
			stackcard = new Card((sc_num = rand.nextInt(13)), (sc_cl = rand.nextInt(4)));
			System.out.println("czy można rzucic:");
			testcard.printCard();
			System.out.println("na:");
			stackcard.printCard();
			System.out.println("");
			this.rules.getCardRule(tc_cl, tc_num);
			tester = this.rules.checkCardRule(testcard, stackcard);
			if (tester == true)
			{
				System.out.println("tak");
			} else System.out.println("nie. \n");
		}
		
	}

	public boolean checkEffect(Card temp) {
		long mask = 1L<<53;
		
		return ((this.rules.genes[temp.color][temp.number] & mask)!=0);//?true:false;
	}

	public Card generateEffect(Card stack) {
		
		Integer cd_num = stack.number, cd_cl = stack.color;
		
		if (cd_num > 12) 
		{
			return (new Card(20, cd_cl));
		}
		if (cd_cl > 4)
		{
			return (new Card(cd_num, 20));
		} else return new Card(20, 20);
		
	}
}