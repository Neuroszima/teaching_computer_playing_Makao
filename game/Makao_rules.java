package game;

public class Makao_rules extends Card_Rules
{	
	boolean rule;
	int rule_int;
	static final int effect_cards = 5;
	static final int dk_numbers = 13;
	static final int dk_colors = 4;
	private static long genes[][] = new long[dk_colors][dk_numbers];
	private static Card card_thrown;
	private static Card card_on_stack;
	private static Card card_rule;
	static final long [][]rules = Makao_rules.rules();
	
	public Makao_rules()
	{
		super(Makao_rules.rules);
	}
	
	private static long[][] rules() {
		setMainRule(genes);
		setSecondaryRule(genes);
		setTetraryRule(genes);
		printRules(genes);
		return genes;
	}
	

	/** Metoda ustawia bitowo regułę:
	*  </p>
	*  rzuć kartę tylko gdy pasuje do koloru lub rodzaju karty
	*  np. na 5 pik mogę rzucić tylko piki lub inne piątki
	*  </p>
	*  reprezentacja bitowa:
	*  </p>
	*  gdy 5 PIK na stosie:
	*  1111111111111000000001000000000000100000000000010000
	*  </p>
	*  pierwszy rząd bitów 1 odpowiada wszystkim kartom koloru pik, a pojedyńcze 1-nki pośród zer, piątki innych kolorów.
	*  </p>
	*/
	static private void setMainRule(long genes[][])
	{
		long mask = 0;
		
		for (int i = 0; i<dk_colors; i++)
		{
			for (int j = 0; j<dk_numbers; j++)
			{
				System.out.println("gdy na stosie jest karta:");
				card_on_stack = new Card(j, i);
				card_on_stack.printCard();
				System.out.println("Czy mogę rzucić:");
				for (int x = 0; x<dk_colors; x++)
				{
					for (int y = 0; y<dk_numbers; y++)
					{
						System.out.println("");
						mask = y + 13*x;
						mask = 1L<<mask;
						System.out.println(Long.toBinaryString(mask));
						card_thrown = new Card(y, x);
						card_thrown.printCard();
						if (y == j)
						{
							genes[i][j] = (genes[i][j] | mask);
							System.out.println("tak");
							continue;
						}
						if (x == i)
						{
							genes[i][j] = (genes[i][j] | mask);
							System.out.println("tak");
							continue;
						}
						//System.out.println("nie");
					}
					
				}
				
			}
			Makao_rules.printRules(genes);
		}
		
	}
	
	
	/**
	* gdy jest 2 lub 3, i zostało dopiero rzucone, nie można rzucić innej karty
	* dopóki modyfikator efektu karty jest aktywny
	* karta 2 i 3 mają efekt, tak samo jak as, walet(żadania), 4(stanie kolejki).
	* </p>
	* metoda dodaje efekty na zasadzie:
	* </p>
	* po 52 bicie: jeżeli bit nr 53 w long jest = 1, karta o numerze ( int z wartości bitów 54-57 (włącznie))
	* w tym samym chromosomie ma efekt.
	* </p>
	* bit 58-61: int z tych bitów: numer karty która może być zagrana przy efekcie na tą kartę dodatkowo
	* </p>
	* bit 62: karta żadana przez graczy może być także rzucana w takim wypadku
	*/
	static private void setSecondaryRule(long genes[][]) 
	{
		//dodatkowe reguły po 52 bicie - nr kart efektów:
		
		int[] effects = {0, 1, 2, 3, 10};
		long mask;
		for (int i = 0; i<dk_colors; i++)
		{
			for (int j = 0; j<dk_numbers; j++)
			{
				mask = 1L<<53;
				//System.out.println("gdy na stosie jest karta specjalna:");
				//card_on_stack = new Card(j, i);
				//card_on_stack.printCard();
				//System.out.println("Czy mogę rzucić kartę (numer):");
				for (int c = 0; c<effect_cards; c++)
				{
					if (effects[c] == j) //sprawdzaj co efekt, czy nie zgadza się przypadkiem z nr karty na stosie
					{
						//System.out.println("stos jest kartą specjalną");
						genes[i][j] = (genes[i][j] | mask);
						mask = (long)effects[c];
						mask = mask<<54;
						System.out.println(Long.toBinaryString(genes[i][j]));
						genes[i][j] = (genes[i][j] | mask);
						if ((effects[c] == 10) || (effects[c] == 0))
						{
							mask = 1L<<62;
							genes[i][j] = (genes[i][j] | mask);
						}
						if (effects[c] == 1)
						{
							mask = (long)effects[2];
							mask = mask<<58;
							genes[i][j] = (genes[i][j] | mask);
						} else if (effects[c] == 2)
						{
							mask = (long)effects[1];
							mask = mask<<58;
							genes[i][j] = (genes[i][j] | mask);
						}
						break;
					}
				}
				
			}
			Makao_rules.printRules(genes);
		}
		
	}
	
	
	/**Metoda ustawia bitowo zasadę: dama na wszystko wszystko na dame
	 * 
	 */
	static private void setTetraryRule(long genes[][]) 
	{
		long mask;
		for (int i = 0; i<dk_colors; i++)
		{
			for (int j = 0; j<dk_numbers; j++)
			{
				//System.out.println("gdy na stosie jest karta:");
				//card_on_stack = new Card(j, i);
				//card_on_stack.printCard();
				//System.out.println("Czy mogę rzucić:");
				for (int x = 0; x<dk_colors; x++)
				{
					for (int y = 0; y<dk_numbers; y++)
					{
						//System.out.println("");
						mask = y + 13*x;
						mask = 1L<<mask;
						//card_thrown = new Card(y, x);
						//card_thrown.printCard();
						if (j == 11)
						{
							genes[i][j] = (genes[i][j] | mask);
							//System.out.println("tak");
							continue;
						}
						if (y == 11)
						{
							genes[i][j] = (genes[i][j] | mask);
							//System.out.println("tak");
							continue;
						}
						//System.out.println("nie");
					}
					
				}
				
			}
			
		}
		Makao_rules.printRules(genes);
	}

	/** wypisuje reguły w postaci bitowej
	 * 
	 * @param gene
	 */
	public static void printRules(long[][] gene)
	{
		for (int i = 0; i<dk_colors; i++)
		{
			System.out.println();
			for (int j = 0; j<dk_numbers; j++)
			{
				card_rule = new Card(j, i);
				card_rule.printCard();
				System.out.println(Long.toBinaryString(gene[i][j]));
			}
		}
	}
}