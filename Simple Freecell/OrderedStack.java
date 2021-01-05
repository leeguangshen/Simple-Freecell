//OrderedStack.java



import java.util.*;

/**
 *Class file named OrderedStack
 *@author Pung Hui Ling
 *@author Lee Guang Shen
 *
 */

public class OrderedStack{
	//create String[] allCards
	private String[] allCards = {"cA", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "cX", "cJ", "cQ", "cK", "dA", "d2", "d3", "d4", "d5", "d6", "d7", "d8", "d9", "dX", "dJ", "dQ", "dK", "hA", "h2", "h3", "h4", "h5", "h6", "h7", "h8", "h9", "hX", "hJ", "hQ", "hK", "sA", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9", "sX", "sJ", "sQ", "sK"};
	
	//create String[] piles
	private String[] piles = {"Pile c", "Pile d", "Pile h", "Pile s", "Column 1",  "Column 2", "Column 3", "Column 4", "Column 5", "Column 6", "Column 7", "Column 8", "Column 9"};
	
	//create HashMap<String, Stack<String>> cells
	private HashMap<String, Stack<String>> cells = new HashMap<>();
	
	//create ArrayList<String>, named movingCards as an Intermediate place to check value of cards
	private ArrayList<String> movingCards = new ArrayList<>();
	
	//constructor
	/**
	 *Default OrderedStack Constructor.
	 *
	 */
	public OrderedStack(){
		
		//create new obj of type Hashmap, key = pile/column, value = Stack<String>
		cells.put("Pile c", new Stack<String>());
		cells.put("Pile d", new Stack<String>());
		cells.put("Pile h", new Stack<String>());
		cells.put("Pile s", new Stack<String>());

		cells.put("Column 1", new Stack<String>());
		cells.put("Column 2", new Stack<String>());
		cells.put("Column 3", new Stack<String>());
		cells.put("Column 4", new Stack<String>());
		cells.put("Column 5", new Stack<String>());
		cells.put("Column 6", new Stack<String>());
		cells.put("Column 7", new Stack<String>());
		cells.put("Column 8", new Stack<String>());
		cells.put("Column 9", new Stack<String>());

		//create List of type String, named cards, put in allCards
		//Motive: To Shuffle Cards
		List<String> cards = Arrays.asList(allCards);
		Collections.shuffle(cards);

		//insert cards into piles using for-loop after each shuffle
		for (int i =0; i<cards.size(); i++){
			String t = "Column " + Integer.toString((i%8)+1);
			//start with i = 0, divide by 8 then plus 1, equals to 1 >> Column 1
			this.cells.get(t).push(cards.get(i));
		}
	}

	/**
	 *Method toString() to display the game dashboard
	 *@return ""
	 */
	public String toString(){
		for(int i =0; i<13; i++){
			//13 = 4 piles + 9 columns
			System.out.println(piles[i] + " : " + this.cells.get(piles[i]));
		}
		return "";
	}
	
	/**
	 *Method pop(String source, String card, String destination) to allow moving of cards
	 *@param source Source pile
	 *@param card Card(s) to be moved
	 *@param destination Destination pile
	 *@return Return nothing if there is no error message, else return Invalid Move.
	 */
	
	public String pop(String source, String card, String destination){
		source = source.toLowerCase();
		card = card.toLowerCase();
		destination.toLowerCase();
		String errorMessage = "";
		
		if ("cdhs".contains(source)){
			errorMessage = "Cannot move from pile";
		}
		else if (this.cells.get("Column "+source).empty()){
			errorMessage = "Column "+source+" is empty";
		}
		// move to pile
		else if ("cdhs".contains(destination)){
			boolean valid = true;
			boolean emptyPile = false;
			if (!card.substring(0,1).equals(destination)){
				errorMessage = "Type of source and destination are not match("+card+", "+destination+")";
				valid = false;
			}
			while(valid && !this.movingCards.contains(card)){
				System.out.println("movingCards:"+movingCards);
				System.out.println("card:"+card);
				System.out.println(this.cells.get("Column "+source).peek().toLowerCase());
				System.out.println(this.cells.get("Column "+source).peek().substring(0,1).toLowerCase());
				
				if (!this.cells.get("Column "+source).peek().substring(0,1).toLowerCase().equals(destination)){
					errorMessage = "Type of source and destination are not match("+this.cells.get("Column "+source).peek()+", "+destination+")";
					valid = false;
					break;
				} else{
					String nextCard = this.cells.get("Column "+source).pop().toLowerCase();
					String prevCard = "";
					if (!movingCards.isEmpty()){
						prevCard = this.movingCards.get(this.movingCards.size()-1);
					} else {
						if (this.cells.get("Pile   "+destination).empty()){
							if (nextCard.equals(destination+"a")){
								System.out.println("EMpty");
								this.movingCards.add(nextCard);
								emptyPile = true;
							} else {
								errorMessage = "First card in this pile must be "+destination+"A";
								valid = false;
								break;
							}
						} else {
							prevCard = this.cells.get("Pile   "+destination).peek();
						}
					}
					System.out.println(this.movingCards);
					System.out.println(prevCard +" , "+nextCard);
					System.out.println(!emptyPile);
					if (!emptyPile){
						this.movingCards.add(nextCard);
						if(!isLessThan(prevCard,nextCard)){
							errorMessage = prevCard+" is not less than "+nextCard+" by 1";
							valid = false;
							break;
						}
					}
				}
			}
			if (valid){
				while(!this.movingCards.isEmpty()){
					String[] o = this.movingCards.remove(0).split("");
					this.cells.get("Pile   "+destination).push(o[0]+o[1].toUpperCase());
				}
			} else {
				while(!this.movingCards.isEmpty()){
					String[] o = this.movingCards.remove(0).split("");
					this.cells.get("Column "+source).push(o[0]+o[1].toUpperCase());
				}
			}
		}
		// to column
		else if ("123456789".contains(destination)){
			boolean valid = true;
			if (!isLessThan(card,this.cells.get("Column "+destination).peek())){
				valid = false;
			} else {
				this.movingCards.add(this.cells.get("Column "+source).pop().toLowerCase());
			}
			
			System.out.println(this.movingCards);
			while(valid && !this.movingCards.contains(card)){
				System.out.println("movingCards:"+movingCards);
				System.out.println("card:"+card);
				System.out.println(this.cells.get("Column "+source).peek().toLowerCase());
				System.out.println(this.cells.get("Column "+source).peek().substring(0,1).toLowerCase());
				
				String nextCard = this.cells.get("Column "+source).pop().toLowerCase();
				String prevCard = this.movingCards.get(this.movingCards.size()-1);
				
				System.out.println(this.movingCards);
				System.out.println(prevCard +" , "+nextCard);
				this.movingCards.add(nextCard);
				if(!isLessThan(nextCard,prevCard) && !this.cells.get("Column "+destination).empty()){
					errorMessage = prevCard+" is not less than "+nextCard+" by 1";
					valid = false;
					break;
				}
			}
			if (valid){
				System.out.println("Valid");
				System.out.println(this.movingCards);
				while(!this.movingCards.isEmpty()){
					String[] o = this.movingCards.remove(this.movingCards.size()-1).split("");
					this.cells.get("Column "+destination).push(o[0]+o[1].toUpperCase());
				}
			} else {
				System.out.println("Invalid");
				while(!this.movingCards.isEmpty()){
					String[] o = this.movingCards.remove(this.movingCards.size()-1).split("");
					this.cells.get("Column "+source).push(o[0]+o[1].toUpperCase());
				}
			}
		}
		if (errorMessage.equals(""))
			return "";
		else
			return "[INVALID MOVE] "+errorMessage;
	}
	
	
	
	
	
	
	
//
//	public String pop(String source, String card, String destination){
//		source = source.toLowerCase();
//		card = card.toLowerCase();
//		destination = card.toLowerCase();
//		String errorMessage = new String();
//
//		if("cdhs".contains(source)){
//			//if user try to move from pile (c/d/h/s), show errorMessage
//			errorMessage = "You cannot move from pile!";
//		}else if(this.cells.get("Column " + source).empty()){
//			errorMessage = "Column " + source + " is empty! Cannot move from empty source pile. ";
//		}else if("cdhs".contains(destination)){
//			//Move to Pile
//			boolean valid = true;
//			boolean emptyPile = false;
//			while(valid && !this.movingCards.contains(card)){
//				System.out.println(movingCards);
//				System.out.println("card: " + card);
//				if(!this.cells.get("Column " + source).peek().substring(0,1).toLowerCase().equals(destination)){
//					errorMessage = "Type of source and destination are not matched! \n";
//					valid = false;
//				}else{
//					String nextCard = this.cells.get("Column " + source).pop().toLowerCase();
//					String prevCard = new String();
//					if (!movingCards.isEmpty()){
//						prevCard = this.movingCards.get(this.movingCards.size()-1);
//					}else{
//						if(this.cells.get("Pile " + destination).empty()){
//							//check if pile stack is empty
//							if(nextCard.equals(destination+"a")){
//								System.out.println("Empty stack!");
//								this.movingCards.add(nextCard);
//								emptyPile = true;
//							}else{
//								errorMessage = "First card in the pile must be " + destination + "A";
//								valid = false;
//							}
//						}else{
//							prevCard = this.cells.get("Pile " + destination).peek();
//						}
//					}
//					System.out.println(this.movingCards);
//					System.out.println(prevCard + ", " + nextCard);
//					System.out.println(!emptyPile);
//					try{
//						if(isLessThan(prevCard,nextCard)){
//							this.movingCards.add(nextCard);
//						}else{
//							errorMessage = prevCard + " is not less than " + nextCard;
//						}
//					}catch(Exception e){
//
//					}
//				}
//			}
//			if(valid){
//				while(!this.movingCards.isEmpty()){
//					this.cells.get("Pile " + destination).push(this.movingCards.remove(0));
//				}
//			}else{
//				while(!this.movingCards.isEmpty()){
//					this.cells.get("Column " + source).push(this.movingCards.remove(this.movingCards.size()-1));
//				}
//			}
//		}
//		//to column
//		if(errorMessage.equals(""))
//			return "";
//		else
//			return "[	INVALID MOVE!	] " + errorMessage;
//	}
//
	
	/**
	 *Boolean Method to check if the current card is 1 value larger than the target card
	 *@param target target card in the pile
	 *@param card current card
	 *@return return true if current card is 1 value larger than target card, false if otherwise
	 */
	//boolean function to check if card is 1 value larger than target
	public boolean isLessThan(String target, String card){
		card = card.toLowerCase();
		target = target.toLowerCase();
		int c = toIntValue(card);
		int t = toIntValue(target);
		System.out.println(t+" is less than " + c + "? : " + (c-1==1));
		
		if (c-1==1)
			return true;
		else
			return false;
	}
	
	/**
	 *Method to convert the card face to integer value
	 *@param a The card face in String
	 *@return return Integer value of each card face
	 */
	public Integer toIntValue(String a){
		System.out.println("toIntValue: " + a);
		switch(a.substring(1)){
			case "k":
				return 12;
			case "q":
				return 11;
			case "j":
				return 10;
			case "a":
				return 11;
			default:
				return Integer.parseInt(a.substring(1));
				//substring starts from index 0
		}
	}
	

}



