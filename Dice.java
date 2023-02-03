package pk;
import java.util.Arrays;
import java.util.Random;

public class Dice {

    public Faces roll() {
        int howManyFaces = Faces.values().length;
        System.out.println("  (DEBUG) there are " + howManyFaces + " faces");
        System.out.println("  (DEBUG) " + Arrays.toString(Faces.values()));
        Random bag = new Random();
        return Faces.values()[bag.nextInt(howManyFaces)];
    }

    public  Faces[] full_roll(int number_of_dice) {
        Faces[] DiceRolls = new Faces[number_of_dice];
        Random num = new Random();
        int howManyFaces = Faces.values().length;
        System.out.println("Rolling Eight Dice");
        for (int i=0;i<number_of_dice;i++){
            DiceRolls[i] = Faces.values()[num.nextInt(howManyFaces)];
            System.out.println(DiceRolls[i]);
        }
        return DiceRolls;
    }

    public int diamonds_gold(int score, Faces[] array){
        for (Faces face: array){
            if (face == Faces.values()[2] || face == Faces.values()[3]){
                score+=100;
            }

        }
        return score;
    }

    public void check_end_turn(Faces[] array){
        int count = 0;
        for (Faces face: array){
            if(face == Faces.values()[5]){
                count+=1;
            }
        }
        if (count>=3){
            System.out.println("Your turn has ended");
        }
    }

   /// public int three_of_kind(int score, Faces[] array){
      //  int count;
       // double score_placeholder=0;
        //for(Faces face: array){
          //  count = 0;
           // Faces temp = face;
            //for (int i =0;i<array.length; i++){
              //  if (temp==array[i]){
                //    count+=1;
               // }

            //}
            //if (count >= 3){

           // }

        //}
        //score += (int) score_placeholder;
        //return score;
    //}
    
}
