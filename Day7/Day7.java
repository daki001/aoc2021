package Day7;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day7 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<Integer> input = readFile("src/Day7/testInput");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Tests: " + (System.currentTimeMillis() - startTime) + " ms\n");
        startTime = System.currentTimeMillis();
        input = readFile("src/Day7/input");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Solution: " + (System.currentTimeMillis() - startTime) + " ms");
    }


    public static int calculateFuel(List<Integer> input, int target) {
        int fuel = 0;
        for (int val : input) {
            fuel += Math.abs(val - target);
        }
        return fuel;
    }

    public static int part1(List<Integer> input) {
        int bestFuel = Integer.MAX_VALUE;
        for (int i = input.stream().mapToInt(x -> x).min().getAsInt(); i <= input.stream().mapToInt(x -> x).max().getAsInt(); i++) {
            bestFuel = Math.min(calculateFuel(input, i), bestFuel);
        }
        return bestFuel;
    }

    public static int calculateFuel2(List<Integer> input, int target) {
        int fuel = 0;
        for (int val : input) {
            for (int i = 0; i <= Math.abs(val - target); i++) {
                fuel += i;
            }
        }
        return fuel;
    }

    public static int part2(List<Integer> input) {
        int bestFuel = Integer.MAX_VALUE;
        for (int i = input.stream().mapToInt(x -> x).min().getAsInt(); i <= input.stream().mapToInt(x -> x).max().getAsInt(); i++) {
            bestFuel = Math.min(calculateFuel2(input, i), bestFuel);
        }
        return bestFuel;
    }

    public static List<Integer> readFile(String path) {
        try {
            BufferedReader in = Files.newBufferedReader(Paths.get(path));
            String line;
            while ((line = in.readLine()) != null) {
                line = line.trim();
                return Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toList());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new LinkedList<>();
    }
}