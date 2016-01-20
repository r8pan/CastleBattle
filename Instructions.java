public class Instructions {
   private String instruct;
   private String creds;
   
   public Instructions ()
   {
      instruct = "Start the game by naming the two Castles. After that, you will begin the game." +
      "\nWhen the game begins, you generate your gold and then enter the buy/deploy phase." +
      "\nIn the buy/deploy phase, you will buy units (up to 3 per turn due to lanes) and deploy them." +
      "\nAfter buying/deploying your troops, you will type in 'done'. Once you type 'done' you will enter " +
      "\nthe move phase, which your units will move or attack if in range. Once that is complete, your turn will end." +
      "\n\nThe game continues until one castle is destroyed." +
      "\n\nUnit Stats\nSpearman: hp=6 attack=4 speed=1 range=1" +
      "\nArcher: hp=1 attack=2 speed=1 range=5\nCavalry: hp=20 attack=1 speed=4 range=1";
   }
   
   public String getInstructions ()
   {
      return instruct;
   }
   
   public String getCredits()
   {
      creds = "Creators: \nDwayne Chapman\nNathan Clowes\nRunqiu Pan\nNingyuan Pei\nGuanying Zhao";
      return creds;
   }
} // End of Instructions class.