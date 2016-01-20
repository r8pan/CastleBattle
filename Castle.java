/*
 * NOTE FROM NATHAN: I rewrote the spawnUnits method so that it no longer converts
 * 					 the parameter lane to the index number. So if you want
 * 					 spawn a unit in the first lane, you have to enter 0 for the index.
 * 					 My reason is that any conversion from user input should be done 
 * 					 from the demo class.
 */

public class Castle // Castle object class to make both of our Castles.
{   
   private static int castle_count = 0; //Counts how many castles are instantiated.
   private int marker; //Used to mark the castle with its number count.
   private int chp; // Castle hit-points.
   private int gold; // Gold castle holds.
   private Unit[][] units; // Lanes units move through.
   private int spear_men;
   private int archers;// A reserve for extra units.
   private String name;
      
   // Constructor method to create our castle.
   public Castle ()
   {
      chp = 10;
      gold = 10;
      castle_count++; //Increments when a castle is instantiated.
      marker = castle_count; //Integer marker numbers the castle.
      units = new Unit[3][10];
      spear_men = 0;
      archers = 0;
   }
   
   // Method to set Castle's hit-points.
   public void setCHP(int hp)
   {
      chp = hp;
   }
   
   // Method to return current Castle hit-points.
   public int getCHP()
   {
      return chp;
   }
   
   // Method to set Castle's gold
   public void setGold(int gd)
   {
      gold=gd;
   }
   
   // Method to return current Castle gold.
   public int getGold()
   {
      return gold;
   }
   
   // Method to set Player's name.
   public void setName(String n)
   {
      name = n;
   }
   
   // Method to get Player's name.
   public String getName ()
   {
      return name;
   }
   
   // Method to deal X amount of damage to castle.
   public int takeDamage(int damage)
   {
      return (chp - damage);
   }
   
   // Checks if Castle is destroyed.
   public boolean castleDestroyed(Castle c)
   {
      if (c.getCHP() <= 0)
         return true;
      else
         return false;
   }
   
   public int getMarker()
   {
      return marker;   
   }
   
   //generate 3 gold each time
   public void generateGold()
   {
      gold=gold+3;
   }
   
   // This method creates a unit and stores it in the given lane.
   public void spawnUnit(int type, int lane, Castle enemy)
   {
	   
	   //If player input for lane is 4, the unit is put in the reserves.
	   if (lane == 4)
		   reserve(type);
	 
	   //Prints cannot spawn if the content of the index
	   //in the lane is not null.
	   else if (units[lane][0] != null || enemy.getUnits()[lane][9] != null) 
		   System.out.println("Cannot spawn unit. Space is full.");
	   
	   //Else it spawns unit at the index in the lane.
	   else
		   units[lane][0] = new Unit(type, this);
   }
   
   // Returns the content at the index in array lanes.
   public Unit getUnitAt(int lane, int index)
   {
	   return units[lane][index];
   }
  // Returns all units. 
   public Unit[][] getUnits()
   {
	   return units;
   }
   
   public void reserve(int n)
   {
	   if (n == 1)
		   spear_men++;
	   
	   if (n ==2)
		   archers++;
   }
   // toString Method
   public String toString()
   {
     return "Castle " + marker + "\n" +
      "HP = " + chp + "\n" +
      "Gold = " + gold + "\n";
   }
   
   // This method compares this castle to another.
   // This method may be obsolete.
   public boolean isEqual(Castle c)
   {
	   return marker == c.getMarker();
   }
   
   public void advanceUnto(int lane, Castle enemy)
   {
	   int index_enemy_in_range; // This variable is used to store the index
	   							  // of an enemy unit.
	   
	   int max_reach; // Variable used to avoid null exceptions.
	   
	   boolean has_attacked; // This boolean is used to keep units from attacking more
	   						 // than once.
	   
	   boolean unit_ahead;	// This boolean is used to prevent units from overwriting
	   						// those in the index ahead of them.
	   	   
	   for (int i = 9; i >= 0; i--) // Method for loop runs through each index of the lane.
	   {
		   if (units[lane][i] != null) // Method checks if there is a unit in the index
		   {
			   if (i == 9)
				   units[lane][i].attack(enemy);
			   else
			   {
				   if (i + units[lane][i].getSpeed() > 9) // Variable max_reach is set to 9 if
					   max_reach = 9;					// the index and the speed of the unit
					   									// combined are greater than 9.

				   // Else, max_reach equals the index
				   // plus the unit's speed.
				   else									
					   max_reach = i + units[lane][i].getSpeed(); 
				   
				   has_attacked = false;
				   unit_ahead = false;
				   
				   for (int j = i; j < max_reach && !has_attacked && !unit_ahead; j++)
				   {   
					   // The unit scouts for enemies.
					   index_enemy_in_range = units[lane][j].scoutForEnemies(lane, j, enemy);
					   
					   // Method checks if the unit found an enemy.
					   if (index_enemy_in_range != -1)
					   {
						   // If true, the unit attacks the enemy unit at the returned index.
						   units[lane][j].attack(enemy.getUnitAt(lane, index_enemy_in_range));
						   has_attacked = true;
					   }
					   
					   // Else, the method checks if the unit can reach the castle. 									
					   else if (j + units[lane][j].getRange() > 9)
					   {
						   // If true, the unit attacks the castle.
						   units[lane][j].attack(enemy);
						   has_attacked = true;
					   }
					   // Else, the unit attempts to move.
					   else 
					   {
						   // If the index head of the unit is taken, the unit stays put.
						   if (units[lane][j + 1] != null)
							   unit_ahead = true; // boolean is set to true.
						   
						   // Else, the unit moves.
						   else
						   {							   
							   moveUnit(lane, j); // Method moveUnit moves the unit ahead.
						   }
					   }
				   }
			   }
		   }
	   }
   }
   
   public void moveUnit(int lane, int i) // This method moves the unit.
   {
	   units[lane][i + 1] = units[lane][i]; // The unit is copied to the index ahead.
	   
	   units[lane][i] = null; // The copy in the previous index is set to null.
   }
   
	public void gatherDead() // This method searches for and removes dead units.
	{
		for (int i = 0; i < 3; i++) // The nested for loop searches each index
			for (int j = 0; j < 9; j++) // of each lane for any units with HP equal to
				if (units[i][j] != null) // or less than 0. 
					if (units[i][j].getHp() <= 0) // If dead unit is found,
					{								// it prints a message and 
													// the index is set to null.
						System.out.println("What a miserable fate!");	
						units[i][j] = null;
						
						System.out.println();
					}
	}
	
}// End of Castle class.