package Day17;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day17 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        int[] input = readFile("src/Day17/testInput");
        System.out.println(part1(input));
        //System.out.println(part2(input));
        System.out.println("Time for Tests: " + (System.currentTimeMillis() - startTime) + " ms\n");
        startTime = System.currentTimeMillis();
        input = readFile("src/Day17/input");
        System.out.println(part1(input));
        //System.out.println(part2(input));
        System.out.println("Time for Solution: " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static long part1(int[] input) {
        int max_y = Math.max(Math.abs(input[2]), Math.abs(input[3]));
        Map<Integer, List<Integer>> coords = new HashMap<>();
        for (int i = max_y; i >= -max_y; i--) {
            int y_coord = 0;
            int y_velocity = i;
            int turn = 0;
            int max_round = 0;
            while (!(y_coord < Math.min(input[2], input[3]) && y_velocity < 0)) {
                y_coord += y_velocity;
                max_round = Math.max(max_round, y_coord);
                y_velocity--;
                turn++;
                if (Math.min(input[2], input[3]) <= y_coord && y_coord <= Math.max(input[2], input[3])) {
                    if (!coords.containsKey(max_round)) {
                        coords.put(max_round, new ArrayList<>());
                    }
                    coords.get(max_round).add(turn);
                }
            }
        }

        Iterator<Integer> it = coords.keySet().stream().sorted(Collections.reverseOrder()).iterator();
        while (it.hasNext()) {
            int value = it.next();
            next:
            for (int v : coords.get(value)) {
                for (int i = Math.max(input[0], input[1]); i >= 0; i--) {
                    long distance = horizontalDistance(v, i);
                    if (distance >= Math.min(input[0], input[1]) && distance <= Math.max(input[0], input[1])) {
                        return value;
                    }
                    if (distance < Math.min(input[0], input[1])) {
                        continue next;
                    }
                }
            }
        }
        return -1;
    }

    public static long horizontalDistance(int rounds, int initialVelocity) {
        long result = 0;
        for (int i = 0; i < rounds; i++) {
            if (initialVelocity - i > 0) {
                result += initialVelocity - i;
            }
        }
        return result;
    }

    public static long part2(int[] input) {
        return -1;
    }

    public static int[] readFile(String path) {
        int[] field = new int[4];
        try (
                BufferedReader in = Files.newBufferedReader(Paths.get(path))
        ) {
            String line = in.readLine();
            Pattern p = Pattern.compile("target area: x=(-?\\d+)\\.\\.(-?\\d+), y=(-?\\d+)\\.\\.(-?\\d+)");
            Matcher m = p.matcher(line);
            if (m.matches()) {
                for (int i = 0; i < 4; i++) {
                    field[i] = Integer.parseInt(m.group(i + 1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return field;
    }
}