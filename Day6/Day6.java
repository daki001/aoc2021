package Day6;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day6 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        long[] input = readFile("src/Day6/testInput");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Tests: " + (System.currentTimeMillis() - startTime) + " ms\n");
        startTime = System.currentTimeMillis();
        input = readFile("src/Day6/input");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Solution: " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static long[] round(long[] roundInput){
        long[] roundOutput = new long[roundInput.length];
        long additions = roundInput[0];
        for (int i = 1; i < 9; i++) {
            roundOutput[i-1] = roundInput[i];
        }
        roundOutput[6] += additions;
        roundOutput[8] = additions;
        return roundOutput;
    }

    public static long part1(long[] input) {
        long[] inputCopy = input.clone();
        for (int i = 0; i < 80; i++) {
            inputCopy = round(inputCopy);
        }
        return Arrays.stream(inputCopy).sum();
    }


    public static long part2(long[] input) {
        long[] inputCopy = input.clone();
        for (int i = 0; i < 256; i++) {
            inputCopy = round(inputCopy);
        }
        return Arrays.stream(inputCopy).sum();
    }

    public static long[] readFile(String path) {
        long[] result = new long[9];
        try {
            BufferedReader in = Files.newBufferedReader(Paths.get(path));
            String line;
            while ((line = in.readLine()) != null) {
                line = line.trim();
                for (int value:Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toList())) {
                    result[value]++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}