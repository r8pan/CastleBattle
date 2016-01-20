import javax.swing.JOptionPane; //Import pop-up boxes.

import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;
public class GameDemo
{
   public static void main(String[] args) throws IOException
   {
      
      // Creates our Instructions object and play String.
      Instructions instructions = new Instructions();
      String play = "";
      // Start game/Read instructions/credits while loop.
      while (!play.equalsIgnoreCase("play"))
      {
         play = startGame(play, instructions);
      } // End of Start game/Read Instructions/Credits while loop.
      
      // Creates our 2 Castles.
      Castle c1 = new Castle();
      Castle c2 = new Castle();
      String c1n;
      String c2n;
      int turn = 0; // Track turns.
      int l = JOptionPane.showConfirmDialog(null,"Do you want to load the game with a previous record?","Load",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      if (l == JOptionPane.YES_OPTION) {
    	  load(c1,c2,turn);
      }
      else if (l == JOptionPane.NO_OPTION) {
    	  // Set both player's names for the player turns.
    	  c1n = JOptionPane.showInputDialog("Player 1's name: ");
    	  c1.setName(c1n);
    	  c2n = JOptionPane.showInputDialog("Player 2's name: ");
    	  c2.setName(c2n);
      }
      // gameEnd boolean variable to end game if castle is destroyed.
      boolean gameEnd = false;
      // Game loop.
      while (gameEnd != true)
      {
         // Player 1's turn.
         if (turn % 2 == 0 && gameEnd != true)
         {
            JOptionPane.showMessageDialog(null,(c1.getName() + "'s turn. " + c2.getName() + ", please turn away."));
            enumeration(c1, c2);
            save(c1,c2,turn);
            startTurn(c1); // Generate gold, show current gold, unit and castle info.
            deployTroops(c1, c2);// Create and deploy troops.
            movePhase(c1, c2);// Move/Attack phase.
            if (c2.castleDestroyed(c2) == true)
            {
               gameEnd = true;
               JOptionPane.showMessageDialog(null,(c1.getName() + " wins!"));
            }
            turn++;
         } // End of Player 1's turn.
         // Player 2's turn.
         if (turn % 2 == 1 && gameEnd != true)
         {
            JOptionPane.showMessageDialog(null,(c2.getName() + "'s turn. " + c1.getName() + ", please turn away."));
            enumeration(c1, c2);
            save(c1, c2,turn);
            startTurn(c2); // Generate gold, show current gold, unit and castle info.
            deployTroops(c2, c1); // Create and deploy troops.
            movePhase(c2, c1); // Move/Attack phase.
            if (c1.castleDestroyed(c1) == true)
            {
               gameEnd = true;
               JOptionPane.showMessageDialog(null,(c2.getName() + " wins!"));
            }
            turn++;
         } // End of Player 2's turn.
      } // End of Game loop.
   
   
   
   }// End of main method.
   
     
      // Method for starting the game.
      public static String startGame(String p, Instructions i)
      {
         p = JOptionPane.showInputDialog("Type 'info' to read the instructions, 'credits' to see the creators " + 
                                         "or 'play' to play the game.");
         if (p.equalsIgnoreCase("info")) // Give instructions.
         {
            JOptionPane.showMessageDialog(null,(i.getInstructions())); // Outputs instructions.
         }
         else if (p.equalsIgnoreCase("credits")) // Give credits.
         {
            JOptionPane.showMessageDialog(null,(i.getCredits())); // Outputs credits.
         }
         return p;
      } // End of startGame method.
      
      // Method for start of each Player's turn.
      public static void startTurn(Castle c)
      {
         c.generateGold(); // Generate's Player's gold for the turn.
         JOptionPane.showMessageDialog(null,(c.getName() + " turn stats:\nGold:" + c.getGold() + "\nCastle HP: " +
                                       c.getCHP())); // Show gold and Castle HP.
      } // End of startTurn method.
      
      // Method to buy/deploy troops.
      public static void deployTroops(Castle c, Castle enemy)
      {
         String done = "";
         // While loop to buy/deploy troops.
         while (!done.equalsIgnoreCase("done"))
         {
            done = JOptionPane.showInputDialog("Type 'done' to finish buying and placing troops.\n" +
                     "Type 1 for spearman, 2 for archer or 3 for cavalry.\nCurrent gold: " +
                     c.getGold() + "\nAll troops cost: 2 gold"); // Buy troop or end buy/deploy phase.
            if (done.equals("1")) // Spearman.
            {
               done = JOptionPane.showInputDialog("Type 1, 2 or 3 to place spearman in numbered lane.");
               if (done.equals("1")) // Spearman down lane 1.
               {
                  c.spawnUnit(1, 0, enemy);
               }
               else if (done.equals("2"))// Spearman down lane 2.
               {
                  c.spawnUnit(1, 1, enemy);
               }
               else if (done.equals("3"))// Spearman down lane 3.
               {
                  c.spawnUnit(1, 2, enemy);
               }
            }
            else if (done.equals("2")) // Archer.
            {
               done = JOptionPane.showInputDialog("Type 1, 2 or 3 to place archer in numbered lane.");
               if (done.equals("1"))// Archer down lane 1.
               {
                  c.spawnUnit(2, 0, enemy);
               }
               else if (done.equals("2"))// Archer down lane 2.
               {
                  c.spawnUnit(2, 1, enemy);
               }
               else if (done.equals("3"))// Archer down lane 3.
               {
                  c.spawnUnit(2, 2, enemy);
               }
            }
            else if (done.equals("3")) // Cavalry.
            {
               done = JOptionPane.showInputDialog("Type 1, 2 or 3 to place cavalry in numbered lane.");
               if (done.equals(1))// Cavalry down lane 1.
               {
                  c.spawnUnit(3, 0, enemy);
               }
               else if (done.equals("2"))// Cavalry down lane 2.
               {
                  c.spawnUnit(3, 1, enemy);
               }
               else if (done.equals("3"))// Cavalry down lane 3.
               {
                  c.spawnUnit(3, 2, enemy);
               }
            }
         }
      } // End of deployTroops method.
      
      public static void movePhase(Castle c, Castle enemy)
      {
         c.gatherDead(); // Remove and clear spots of dead units.
         for (int i = 0; i < 3; i++)
         {
            if (enemy.castleDestroyed(enemy) != true)
               c.advanceUnto(i, enemy);
         }
      } // End of movePhase method.
      
      public static void enumeration (Castle c1, Castle c2) {
  		System.out.print("\t\t|");
  		String[][] c3 = new String[3][10];
  		for (int k = 0; k < 10; k++) {
  			if (c1.getUnits()[0][k] != null) {
  				c3[0][k] = c1.getUnits()[0][k].getSymbol();
  			}
  			else if (c2.getUnits()[0][9-k] != null) {
  				c3[0][k] = c2.getUnits()[0][9-k].getSymbol();
  			}
  			else {
  				c3[0][k] = " ";
  			}
  			System.out.print(c3[0][k] + "\t|");
  		}
  		System.out.print("\n" + c1.getName() +"'s Castle\t|");
  		for (int k = 0; k < 10; k++) {
  			if (c1.getUnits()[1][k] != null) {
  				c3[1][k] = c1.getUnits()[1][k].getSymbol();
  			}
  			else if (c2.getUnits()[1][9-k] != null) {
  				c3[1][k] = c2.getUnits()[1][9-k].getSymbol();
  			}
  			else {
  				c3[1][k] = " ";
  			}
  			System.out.print(c3[1][k] + "\t|");
  		}
  		System.out.print("\t"+ c2.getName() + "'s Castle\n");
  		System.out.print("\t\t|");
  		for (int k = 0; k < 10; k++) {
  			if (c1.getUnits()[2][k] != null) {
  				c3[2][k] = c1.getUnits()[2][k].getSymbol();
  			}
  			else if (c2.getUnits()[2][9-k] != null) {
  				c3[2][k] = c2.getUnits()[2][9-k].getSymbol();
  			}
  			else {
  				c3[2][k] = " ";
  			}
  			System.out.print(c3[2][k] + "\t|");
  		}
  		System.out.println();
  	}
      
    public static void save(Castle c1, Castle c2, int turn) throws IOException {
  		int l = JOptionPane.showConfirmDialog(null,"Do you want to save the game?","Save",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if (l == JOptionPane.YES_OPTION) {
        	String filename = JOptionPane.showInputDialog("Enter the file name to save: ");
        	filename = filename + ".txt";
        	PrintWriter outputFile = new PrintWriter(new FileWriter(filename));
        	outputFile.println(turn);
        	outputFile.println(c1.getName());
        	outputFile.println(c2.getName());
        	outputFile.println(c1.getGold());
        	for (int i = 0; i < 3; i++) {
        		for (int j = 0; j < 10; j++) {
        			if (c1.getUnits()[i][j] == null)
        				outputFile.print("V 0 ");
        			else
        				outputFile.print(c1.getUnits()[i][j].getType() + " " + c1.getUnits()[i][j].getHp() + " ");
        		}
        		outputFile.println();
        	}
        	outputFile.println(c2.getGold());
        	for (int i = 0; i < 3; i++) {
        		for (int j = 0; j < 10; j++) {
        			if (c2.getUnits()[i][j] == null)
        				outputFile.print("V 0 ");
        			else
        				outputFile.print(c2.getUnits()[i][j].getType() + " " + c2.getUnits()[i][j].getHp() + " ");
        		}
        		outputFile.println();
        	}
        	outputFile.close();
            System.out.println("Successfully saved!");
        }
  	}
    
    public static void load(Castle c1, Castle c2, int turn) throws IOException {
		String filename = JOptionPane.showInputDialog("Enter the file name to load: ");
		filename = filename + ".txt";
		File newFile = new File(filename);
		Scanner inputFile = new Scanner(newFile);
		while (!newFile.exists()) {
			System.out.println(filename + "does not exist.");
			filename = JOptionPane.showInputDialog("Enter the file name to load: ");
	  	  	filename = filename + ".txt";
			newFile = new File(filename);
			inputFile = new Scanner(newFile);
		}
		String line;
		Unit num;
		String type = "";
		turn = inputFile.nextInt();
		c1.setName(inputFile.next());
		c2.setName(inputFile.next());
		c1.setGold(inputFile.nextInt());
		for (int i = 0; i < 3; i++) {
			//line = inputFile.nextLine();
			int hp = 0;
			int n = 0;
			for (int j = 0; j < 10; j++) {
				type = inputFile.next();
				if (type.equals("V")) {
					c1.getUnits()[i][j] = null;
					hp = inputFile.nextInt();
				}
				else {
					if (type.equals("spearman"))
						n = 1;
					else if (type.equals("archer"))
						n = 2;
					else if (type.equals("cavalry"))
						n = 3;
					hp = inputFile.nextInt();
					c1.getUnits()[i][j] = new Unit(n, c1, hp);
				}
			}
		}
		c2.setGold(inputFile.nextInt());
		for (int i = 0; i < 3; i++) {
			//line = inputFile.nextLine();
			int hp = 0;
			int n = 0;
			for (int j = 0; j < 10; j++) {
				type = inputFile.next();
				if (type.equals("V")) {
					c2.getUnits()[i][j] = null;
					hp = inputFile.nextInt();
				}
				else {
					if (type.equals("spearman"))
						n = 1;
					else if (type.equals("archer"))
						n = 2;
					else if (type.equals("cavalry"))
						n = 3;
					hp = inputFile.nextInt();
					c1.getUnits()[i][j] = new Unit(n, c2, hp);
				}
			}
		}
		inputFile.close();
	}
      
}// End of GameDemo class.