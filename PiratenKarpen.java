import pk.Dice;
import pk.Player;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
public class PiratenKarpen {
    private static Logger demolog = LogManager.getLogger(PiratenKarpen.class.getName());
    public static void main(String[] args) {
        if (args.length!=5){ //instructions on how to properly enter command line args
            System.out.println("Enter command line args: <Strategy 1> <Strategy 2> <yes/no (trace option)> <number of games> <yes/no PRINT EVERYTHING (It's a lot)>");
            System.exit(0);
        }
        Dice myDice = new Dice(); //dice win tracker and game tracking variables
        double play1_wins=0;
        double play2_wins=0;
        int games=Integer.parseInt(args[3]);
        int game_num=0;

        Player player_1 = new Player();
        Player player_2 = new Player();
        while (game_num<games){ //loop for x amount of games
            if (args[2].equals("yes")){ //checks trace option
                demolog.trace("NEW GAME\n");
            }
            while(player_1.score<6000 && player_2.score<6000) { //loops until target score reached
                if (args[2].equals("yes")){
                    demolog.trace("Player 1 Turn");
                }
                player_1.sim_turn(args[0],args[4]); //player 1 turn
                if (args[2].equals("yes")){
                    demolog.trace("Current Player 1 score: " + player_1.score);
                    demolog.trace("Player 2 Turn");
                }
                player_2.sim_turn(args[1],args[4]); //player 2 turn
                if (args[2].equals("yes")){
                    demolog.trace("Current Player 2 score: " + player_2.score);
                }
                player_1.skulls=0; //reset skulls
                player_2.skulls=0;
            }
            if (player_1.score>player_2.score){
                if (args[2].equals("yes")){
                    demolog.trace("Player 1 wins!!!\n"); //determine winner
                }
                play1_wins+=1;
            }else if (player_1.score<player_2.score){
                if (args[2].equals("yes")){
                    demolog.trace("Player 2 wins!!!\n");
                }
                play2_wins+=1;
            }else
            if (args[2].equals("yes")){
                demolog.trace("It's a tie\n"); //check for tie
            }
            if (args[2].equals("yes")){
                demolog.trace("Tracing Player 1 score: " + player_1.score); //score tracker
                demolog.trace("Tracing Player 2 score: " + player_2.score + "\n");
            }

            player_1.skulls=0; //reset all attributes and increment game
            player_2.skulls=0;
            player_2.score=0;
            player_1.score=0;
            game_num+=1;
        }
        double percent1;
        percent1 = (play1_wins/games*100);
        double percent2;
        percent2 = (play2_wins/games*100);
        System.out.printf("Player 1 won %.2f percent of the games\n",percent1); //calculates percentage of wins
        System.out.printf("Player 2 won %.2f percent of the games\n",percent2);
        System.out.println("Number of ties: " + (games-play1_wins-play2_wins));
    }
}
