package Day10;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day10 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String> input = readFile("src/Day10/testInput");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Tests: " + (System.currentTimeMillis() - startTime) + " ms\n");
        startTime = System.currentTimeMillis();
        input = readFile("src/Day10/input");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Solution: " + (System.currentTimeMillis() - startTime) + " ms");
    }


    public static int part1(List<String> input) {
        int finalScore = 0;
        Map<Character, Integer> score = new HashMap<>();
        score.put(')', 3);
        score.put(']', 57);
        score.put('}', 1197);
        score.put('>', 25137);
        for (String line : input) {
            List<Character> closing = new LinkedList<>();
            next:
            for (int i = 0; i < line.length(); i++) {
                switch (line.charAt(i)) {
                    case '(':
                        closing.add(')');
                        continue next;
                    case '<':
                        closing.add('>');
                        continue next;
                    case '[':
                        closing.add(']');
                        continue next;
                    case '{':
                        closing.add('}');
                        continue next;
                }

                if (closing.size() > 0 && closing.get(closing.size() - 1) != line.charAt(i)) {
                    finalScore += score.get(line.charAt(i));
                    break;
                } else if (closing.size() > 0) {
                    closing.remove(closing.size() - 1);
                }
            }
        }
        return finalScore;
    }

    public static int part2(List<String> input) {
        Map<Character, Integer> score = new HashMap<>();
        List<Integer> finalScores = new LinkedList<>();
        score.put(')', 1);
        score.put(']', 2);
        score.put('}', 3);
        score.put('>', 4);
        nextLine:
        for (String line : input) {
            LinkedList<Character> closing = new LinkedList<>();
            next:
            for (int i = 0; i < line.length(); i++) {
                switch (line.charAt(i)) {
                    case '(':
                        closing.add(')');
                        continue next;
                    case '<':
                        closing.add('>');
                        continue next;
                    case '[':
                        closing.add(']');
                        continue next;
                    case '{':
                        closing.add('}');
                        continue next;
                }

                if (closing.get(closing.size() - 1) != line.charAt(i)) {
                    continue nextLine;
                } else {
                    closing.remove(closing.size() - 1);
                }
            }

            if (closing.size() > 0) {
                int finalScore = 0;
                for (Iterator iter = closing.descendingIterator(); iter.hasNext(); ) {
                    finalScore *= 5;
                    finalScore += score.get(iter.next());
                }
                finalScores.add(finalScore);
            }
        }
        return finalScores.stream().mapToInt(x -> x).sorted().skip((long) (finalScores.size() / 2)).findFirst().getAsInt();
    }

    public static List<String> readFile(String path) {
        List<String> result = new LinkedList<>();
        try (
                BufferedReader in = Files.newBufferedReader(Paths.get(path))
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                line = line.trim();
                result.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}