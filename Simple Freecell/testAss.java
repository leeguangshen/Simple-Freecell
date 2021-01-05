//testAss.java


import java.util.*;

/**
 *Compile and run this class file in order to show demonstrations.
 *@author Pung Hui Ling
 *@author Lee Guang Shen
 *
 */


public class testAss {
	
	
	
	
	/**Main Method
	 *@param args The command line arguments
	 *
	 */
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		OrderedStack cells = new OrderedStack();
//		String errorMessage = "";
		System.out.println(cells);
		System.out.println("Command >>> ");
		String input = scanner.nextLine();
		
		
		while(true){
			if (input.equalsIgnoreCase("x")){
				System.out.println(" ----- Exiting program! ----- \n");
				System.exit(0);
			}else if(input.equals("R") || input.equals("r")){
				restart(args);
			}else{
				System.out.println("Enter R to restart, X to quit. \n");
			}
			
			String[] command = input.split(" ");
			String errorMessage = cells.pop(command[0],command[1],command[2]);
			System.out.println(cells);
			System.out.println(errorMessage + "\n");
			System.out.print("\nCommand >>> ");
			input = scanner.nextLine();
			
			
		}
	}
	
	/**Method to restart the game.
	 *@param strArr string array
	 *@return
	 *@throws no exception
	 */
	private static void restart(String[] strArr){
		System.out.println(" ----- Restarting the game! ----- \n");
		main(strArr);
	}
	
}
