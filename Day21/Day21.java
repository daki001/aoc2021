package Day21;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Day21 {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        int [] input = readFile("src/Day21/testInput");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Tests: " + (System.currentTimeMillis() - startTime) + " ms\n");
        startTime = System.currentTimeMillis();
        input = readFile("src/Day21/input");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Solution: " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static long part1(int[] input) {
        input[0]--;
        input[1]--;
        int [] player = new int[]{0,0};
        int counter = 0;
        boolean turn = true;
        int values = 0;
        while (true) {
            for (int i = 1; i <= 100; i++) {
                values += i;


                counter++;



                if(counter % 3 == 0){
                    if(turn){
                        input[0] += values;
                        input[0] %= 10;
                        player[0] += input[0] + 1;
                    }else{
                        input[1] += values;
                        input[1] %= 10;
                        player[1] += input[1] + 1;
                    }
                    turn = !turn;
                    values = 0;
                    if(player[0] >= 1000){
                        return (long) player[1] * counter;
                    }
                    if(player[1] >= 1000){
                        return (long) player[0] * counter;
                    }
                }


            }
        }
    }

    public static long part2(int[] input) {
        return -1;
    }

    public static int[] readFile(String path) {
        int[] input = new int[]{0,0};
        try (
                BufferedReader in = Files.newBufferedReader(Paths.get(path))
        ) {
            String line = "";
            int counter = 0;
            while ((line = in.readLine()) != null) {
                line = line.split(":")[1].trim();
                input[counter] = Integer.parseInt(line);
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }
}
