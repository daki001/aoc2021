package Day11;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day11 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<int[]> input = readFile("src/Day11/testInput");
        System.out.println(part1(input));
        input = readFile("src/Day11/testInput");
        System.out.println(part2(input));
        System.out.println("Time for Tests: " + (System.currentTimeMillis() - startTime) + " ms\n");
        startTime = System.currentTimeMillis();
        input = readFile("src/Day11/input");
        System.out.println(part1(input));
        input = readFile("src/Day11/input");
        System.out.println(part2(input));
        System.out.println("Time for Solution: " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static int blink(int x, int y, List<int[]> input) {
        int result = 1;
        input.get(x)[y] = -1;
        for (int i = Math.max(0, x - 1); i < Math.min(input.size(), x + 2); i++) {
            for (int j = Math.max(0, y - 1); j < Math.min(input.get(0).length, y + 2); j++) {
                if (input.get(i)[j] != -1) {
                    input.get(i)[j] += 1;
                    if (input.get(i)[j] > 9) {
                        result += blink(i, j, input);
                    }
                }
            }
        }
        return result;
    }

    public static int round(List<int[]> input) {
        int result = 0;
        for (int i = 0; i < input.size(); i++) {
            int[] line = input.get(i);
            for (int j = 0; j < line.length; j++) {
                if (line[j] != -1) {
                    line[j]++;

                    if (line[j] > 9) {
                        result += blink(i, j, input);
                    }
                }
            }
        }
        for (int i = 0; i < input.size(); i++) {
            int[] line = input.get(i);
            for (int j = 0; j < line.length; j++) {
                if (line[j] == -1) {
                    line[j] = 0;
                }
            }
        }

        return result;
    }

    public static int part1(List<int[]> input) {
        int result = 0;
        for (int i = 0; i < 100; i++) {
            result += round(input);
        }

        return result;
    }

    public static int part2(List<int[]> input) {
        int counter = 0;
        while(true){
            counter ++;
            if(round(input) == input.size() * input.get(0).length){
                return counter;
            }
        }
    }

    public static List<int[]> readFile(String path) {
        List<int[]> result = new LinkedList<>();
        try (
                BufferedReader in = Files.newBufferedReader(Paths.get(path))
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                line = line.trim();
                int[] lineValues = new int[line.length()];
                for (int i = 0; i < line.length(); i++) {
                    lineValues[i] = line.charAt(i) - '0';
                }
                result.add(lineValues);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}