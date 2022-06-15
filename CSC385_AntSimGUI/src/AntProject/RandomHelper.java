package AntProject;

import java.util.Random;

/**
 * Helper class for random generation
 */
public class RandomHelper {

    static Random numberGenerator = new Random();

    public static int randomNumbers(int num){
        //next int is upperbound - 1
        return numberGenerator.nextInt(num);
    }
}