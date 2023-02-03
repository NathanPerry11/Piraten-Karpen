package pk;
import pk.Dice;
import java.util.Random;
public class Player {
    public int score;

    public int card;
    public int skulls;
    public Player(){ //initialize player class
        score=0;
        skulls=0;
        card=0;
    }
    public int dice_choice(){ //chooses random number of dice to keep
        Random rand = new Random();
        return (rand.nextInt(7-skulls)+2);

    }

    public int draw_card(boolean onoff){ //onoff variable refers to final command line arg to print everything
        int[] card_deck = new int[35];
        for (int i=0;i<10;i++){
            if (i<2){
                card_deck[i]=2; //create card deck with two of each kind of sea battle card (2,3,4) and four monkey business cards(13)
            }else if(i<4){
                card_deck[i]=3;
            }else if (i<6){
                card_deck[i]=4;
            }else if (i<10){
                card_deck[i]=13;
            }
        }
        Random rand = new Random();
        int draw = rand.nextInt(35); //choose random card from array
        if (card_deck[draw]>0 && card_deck[draw]<5){
            if (onoff) System.out.println("You drew a sea battle card, roll " + card_deck[draw] + " sabers for a bonus");
        }else if (card_deck[draw]==0){
            if (onoff) System.out.println("You drew a NOP card :(");
        }else if (card_deck[draw]==13){
            if (onoff) System.out.println("You drew a Monkey Business card!");
        }
        return (card_deck[draw]);
    }

    public void sim_turn(String arg,String prnt){

        boolean turn = false; //var initialization
        boolean testMode=false;
        int dice_num;
        Dice myDice = new Dice();
        pk.Faces[] roll = new pk.Faces[8];
        card=draw_card(testMode);
        if (prnt.equals("yes")) testMode=true; //test mode also refers to the final command line arg which prints everything

        roll = myDice.full_roll(8); //roll 8 dice and print if testMode
        if (testMode){
            for (Faces face:roll){
                System.out.print(face +", ");
            }
            System.out.print("\n");
        }

        skulls=myDice.count_skulls(roll,8); //count skulls of first roll

        if (skulls>=3){ //end turn if 3 skulls
            turn=true;
        }
        int cnt=0;
        while (!turn){ //loops until turn is false
            dice_num=dice_choice();
            if (arg.equals("random")){ //random strategy
                roll = myDice.reRollRandom(roll,dice_num,skulls,testMode);
                Random num = new Random(); //randomly end turn 1/3 of the time
                int turn_rand = num.nextInt(3);
                if (turn_rand==0) {
                    turn = true;
                }
                skulls=myDice.count_skulls(roll,8);//count skulls
            }else if (arg.equals("combo")){//combo strategy
                if (card>0 && card <5){ //if sea battle card
                    Faces[] temp = new Faces[8];
                    int recur_check=0;
                    for (int i=0;i<8;i++){
                        temp[i]=roll[i];
                    }
                    roll = myDice.rerollSeaBattle(roll,skulls,card,testMode);//reroll method
                    for (int i=0;i<8;i++){ //checks if two rolls are identical and terminates turn if true
                        if (temp[i]==roll[i]){ //fixes bug where rolling strategy would keep looping without actually rerolling any dice
                            recur_check+=1;
                        }
                    }
                    if (recur_check==8){
                        turn = true;
                    }
                    skulls=myDice.count_skulls(roll,8); //count skulls
                    if (skulls == 2){
                        turn = true;//cautious player ends turn after two skulls show up
                    }

                }else{ //if no sea battle card
                    Faces[] temp = new Faces[8];
                    int recur_check=0;
                    for (int i=0;i<8;i++){
                        temp[i]=roll[i];
                    }
                    roll = myDice.reRollCombo(roll,skulls,testMode);//combo rolling strategy
                    for (int i=0;i<8;i++){//same bug as above
                        if (temp[i]==roll[i]){
                            recur_check+=1;
                        }
                    }
                    if (recur_check==8){
                        turn = true;
                    }
                    skulls=myDice.count_skulls(roll,8);
                    if (skulls == 2){ //also cautious player
                        turn = true;
                    }
                }
            }
            if (testMode) { //prints each reroll
                for (Faces face:roll){
                    System.out.print(face +", ");
                }
                System.out.print("\n");
            }

            if (skulls>=3){//ends turn
                turn=true;
            }

        }
        if (skulls<3){
            score=myDice.diamonds_gold(score,roll,8); //scoring methods if no disqualification
            score=myDice.three_of_kind(score,roll,8,card);
            score=myDice.sea_battle_score(score,roll,card,testMode);
        }
        if (testMode) {
            System.out.println("This turn has ended");
            System.out.println("current score:" + score);
        }


    }
}