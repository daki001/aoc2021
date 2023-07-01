package Day21;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day21 {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        int[] input = readFile("src/Day21/testInput");
        System.out.println(part1(input));
        input = readFile("src/Day21/testInput");
        System.out.println(part2(input));
        System.out.println("Time for Tests: " + (System.currentTimeMillis() - startTime) + " ms\n");
        startTime = System.currentTimeMillis();
        input = readFile("src/Day21/input");
        System.out.println(part1(input));
        input = readFile("src/Day21/input");
        System.out.println(part2(input));
        System.out.println("Time for Solution: " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static long part1(int[] input) {
        input[0]--;
        input[1]--;
        int[] player = new int[]{0, 0};
        int counter = 0;
        boolean turn = true;
        int values = 0;
        while (true) {
            for (int i = 1; i <= 100; i++) {
                values += i;


                counter++;


                if (counter % 3 == 0) {
                    if (turn) {
                        input[0] += values;
                        input[0] %= 10;
                        player[0] += input[0] + 1;
                    } else {
                        input[1] += values;
                        input[1] %= 10;
                        player[1] += input[1] + 1;
                    }
                    turn = !turn;
                    values = 0;
                    if (player[0] >= 1000) {
                        return (long) player[1] * counter;
                    }
                    if (player[1] >= 1000) {
                        return (long) player[0] * counter;
                    }
                }


            }
        }
    }


    public static long[] diceUniverseSplit() {
        long[] universes = new long[10];
        Arrays.fill(universes, 0);
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                for (int k = 1; k < 4; k++) {
                    universes[k + i + j]++;
                }
            }
        }
        return universes;
    }

    public static long part2(int[] input) {
        long[] diceRoll = diceUniverseSplit();
        long[] wins = new long[]{0, 0};
        Map<Long, Long> scores = new HashMap<>();
        System.out.println(Arrays.toString(input));
        System.out.println(Arrays.toString(diceRoll));
        scores.put((((long) ((input[0] - 1) * 10) + ((input[1] - 1))) * 10), 1L);
        System.out.println(scores);
        int testcounter = 0;
        next:
        while (scores.keySet().size() > 0) {
                /*testcounter++;
                if(testcounter > 10){
                    return -1;
                }*/
            long lowestScore = scores.keySet().stream().min((x, y) -> {

                x = x / 1000;
                y = y / 1000;
                if (x / 100 < x % 100) {
                    if (x / 100 <= y % 100) {
                        return Long.compare(x / 100, y / 100);
                    }
                    if (y / 100 <= y % 100) {
                        return Long.compare(x / 100, y / 100);
                    }
                    return Long.compare(x % 100, y % 100);
                } else {
                    if (x % 100 <= y / 100) {
                        return Long.compare(x % 100, y % 100);
                    }
                    if (y % 100 <= y / 100) {
                        return Long.compare(x % 100, y % 100);
                    }
                    return Long.compare(x / 100, y / 100);
                }
            }).get();
            //lowestScore = scores.keySet().stream().mapToLong(x->x).min().getAsLong();
            //System.out.println(scores);
            //System.out.println(lowestScore);
            if ((lowestScore & 1) == 0) {
                long currentField = (lowestScore / 100) % 10;
                loop:
                for (int i = 0; i < 10; i++) {
                    if (diceRoll[i] > 0) {
                        long newScore = lowestScore - (currentField * 100);
                        newScore += ((currentField + i) % 10) * 100;
                        newScore += (((currentField + i) % 10) + 1) * 100000;
                        if ((newScore / 100000) % 100 >= 21) {
                            wins[0] += scores.get(lowestScore) * diceRoll[i];
                            //scores.remove(lowestScore);
                            continue loop;
                        }
                        newScore |= 1;
                        if (!scores.containsKey(newScore)) {
                            scores.put(newScore, 0L);
                        }
                        scores.put(newScore, scores.get(newScore) + scores.get(lowestScore) * diceRoll[i]);
                    }
                }
                scores.remove(lowestScore);
            } else {

                long currentField = (lowestScore / 10) % 10;
                loop2:
                for (int i = 0; i < 10; i++) {
                    if (diceRoll[i] > 0) {
                        long newScore = lowestScore - (currentField * 10);
                        newScore += ((currentField + i) % 10) * 10;
                        newScore += (((currentField + i) % 10) + 1) * 1000;
                        if ((newScore / 1000) % 100 >= 21) {
                            wins[1] += scores.get(lowestScore) * diceRoll[i];
                            //scores.remove(lowestScore);
                            continue loop2;
                        }
                        newScore ^= 1;
                        if (!scores.containsKey(newScore)) {
                            scores.put(newScore, 0L);
                        }
                        scores.put(newScore, scores.get(newScore) + scores.get(lowestScore) * diceRoll[i]);
                    }
                }
                scores.remove(lowestScore);
            }
        }
        System.out.println(Arrays.toString(wins));
        return Math.max(wins[0], wins[1]);
    }

    public static int[] readFile(String path) {
        int[] input = new int[]{0, 0};
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
