//Units.java

public class Unit {
	private String type;
    private int hp;
	private int attack;
	private int speed;
	private int range;
	private int cost;
	private Castle castle;
	private String symbol;
	//constructor
	public Unit(int n, Castle c) {
		castle = c;    
		if (n == 1)
		{
			type = "spearman";
			hp = 3;
			attack = 20;
			speed = 1;
			range = 1;
			cost = 2;
			if (c.getMarker() == 1) {
				symbol = "->";
			}
			else if (c.getMarker() == 2) {
				symbol = "<-";
			}
		}
      
		else if (n == 2)
		{
			type = "archer";
			hp = 1;
			attack = 2;
			speed = 1;
			range = 5;
			cost = 2;
			if (c.getMarker() == 1) {
				symbol = "+}";
			}
			else if (c.getMarker() == 2) {
				symbol = "{+";
			}
		}
      
		else if (n == 3)
		{
			type = "cavalry";
			hp = 20;
			attack = 1;
			speed = 4;
         range = 1;
         cost = 2;
         	if (c.getMarker() == 1) {
				symbol = "m/бу";
			}
			else if (c.getMarker() == 2) {
				symbol = "бу\\m";
			}
      	}    
		c.setGold(c.getGold() - cost);     
	}
	
	public Unit(int n, Castle c, int hp) {
		castle = c;    
		if (n == 1)
		{
			type = "spearman";
			hp = 6;
			attack = 4;
			speed = 1;
			range = 1;
			cost = 1;
			if (c.getMarker() == 1) {
				symbol = "->";
			}
			else if (c.getMarker() == 2) {
				symbol = "<-";
			}
		}
      
		else if (n == 2)
		{
			type = "archer";
			hp = 2;
			attack = 3;
			speed = 2;
			range = 5;
			cost = 1;
			if (c.getMarker() == 1) {
				symbol = "+}";
			}
			else if (c.getMarker() == 2) {
				symbol = "{+";
			}
		}
      
		else if (n == 3)
		{
			type = "cavalry";
			hp = 8;
			attack = 5;
			speed = 4;
         	range = 1;
         	cost = 2;
         	if (c.getMarker() == 1) {
				symbol = "m/бу";
			}
			else if (c.getMarker() == 2) {
				symbol = "бу\\m";
			}
      	}    
	}
	//set methods
	public void setHp(int h){
		hp = h;
	}
	public void setAttack(int a){
		attack = a;
	}
	public void setSpeed(int s){
		speed = s;
	}
	public void setRange(int r){
		range = r;
	}
	public void setCost(int c){
		cost = c;
	}
	public void setCastle(Castle c){
		castle = c;
	}
	//get methods
	public String getType() {
		return type;
	}
	public int getHp(){
		return hp;
	}
	public int getAttack(){
		return attack;
	}
	public int getSpeed(){
		return speed;
	}
	public int getRange(){
		return range;
	}
	public int getCost(){
		return cost;
	}
	public Castle getCastle(){
		return castle;
	}
	public String getSymbol() {
		return symbol;
	}
		
	public void attack(Unit enemy) // This method deals this unit's damage to 
	{								// the enemy unit.
		
		// States that this unit is attacking enemy unit.
		System.out.println(this + " attacks " + enemy + ".");
		
		// The enemy units HP is set to its current HP minus this unit's attack.
		enemy.setHp(enemy.getHp() - attack);	
		
		// If the enemy unit's health is now equal to or less than 0,
		// the enemy unit is declared dead and removed from the lane.
		if (enemy.getHp() <= 0)
		{
			System.out.println(enemy + " has been slain!");
			enemy.getCastle().gatherDead(); // This method removes the dead unit.
		}
		
		// Else, the method prints the enemy unit's remaining HP.
		else
		{
			System.out.println(enemy + "'s HP: " + enemy.getHp());
			System.out.println();
		}
	}
	
	
	public void attack(Castle enemy) // This method deals this unit's damage to the
	{								 // enemy castle.
		
		System.out.println(this + " attacks Castle " + enemy.getMarker() + ".");
		
		// The enemy castle's HP is set to its current HP minus this unit's attack.
		enemy.setCHP(enemy.getCHP() - attack);
		
		// If the enemy castle's HP is equal to or less than 0, the castle is
		// declared destroyed.
		if (enemy.getCHP() <= 0)
		{
			System.out.println("Castle " + enemy.getMarker() + 
					" has been destroyed!");
			System.out.println();
		}
		
		// Else, the method prints the enemy castle's remaining HP.
		else
		{	System.out.println("Castle " + enemy.getMarker() + 
					"'s HP: " + enemy.getCHP());
			System.out.println();
		}
	}
	
	// This method is used by this unit to look for enemies while it's advancing.
   public int scoutForEnemies(int lane, int index, Castle enemy)
   {
	   int enemy_found = -1; // This int variable is returned either with the index of 
	   						 // nearest enemy in range, or -1 if no enemy was spotted.
	   
	   int max_range; // This int variable is used to prevent a null exception.
	   
	   
	   if (index + range > 9) // If this unit's index plus its range is greater than 9,
		   max_range = 9;     // max_range is set to 9.
	   
	   else // Else, max_range equals this unit's index plus its range.
		   max_range = index + range;
		
	   // This unit scouts for an enemy within its range.
	   for (int i = index; i < max_range && enemy_found == -1; i++)
		   if (enemy.getUnits()[lane][9 - i] != null) // If the index does not equal null,
			   enemy_found = 9 - i; 				  // enemy_found is set to the index.
	   
	   return enemy_found; // The method returns enemy_found.
   }
   
   //toString Method
	public String toString()
   {
      return "Castle " + castle.getMarker() + "'s " +
         type;
   }
}
