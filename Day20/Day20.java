package Day20;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class Day20 {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String> input = readFile("src/Day20/testInput");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Tests: " + (System.currentTimeMillis() - startTime) + " ms\n");
        startTime = System.currentTimeMillis();
        input = readFile("src/Day20/input");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Solution: " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static List<List<Integer>> createField(List<String> inputs) {
        System.out.println(inputs);
        List<List<Integer>> result = new LinkedList<>();
        for (int i = 1; i < inputs.size(); i++) {
            result.add(new LinkedList<>());
            for (int j = 0; j < inputs.get(i).length(); j++) {
                if (inputs.get(i).charAt(j) == '.') {
                    result.get(i - 1).add(0);
                } else {
                    result.get(i - 1).add(1);
                }
            }
        }
        return result;
    }

    public static List<List<Integer>> round(List<List<Integer>> currentField, int emptySpace, String translation) {
        List<Integer> border = new LinkedList<>();
        List<Integer> border2 = new LinkedList<>();
        for (int i = 0; i < currentField.get(0).size() + 2; i++) {
            border.add(emptySpace);
            border2.add(emptySpace);
        }
        currentField.add(0, border);
        currentField.add(border2);

        for (int i = 0; i < currentField.size(); i++) {
            currentField.get(i).add(0, emptySpace);
            currentField.get(i).add(emptySpace);
        }
        List<List<Integer>> result = new LinkedList<>();
        for (int i = 0; i < currentField.size(); i++) {
            result.add(new LinkedList<>());
            for (int j = 0; j < currentField.get(i).size(); j++) {
                int value = 0;
                for (int k = -1; k < 2; k++) {
                    for (int l = -1; l < 2; l++) {
                        value <<= 1;
                        if (k + i >= 0 && k + i < currentField.size() && l + j >= 0 && l + j < currentField.get(k + i).size()) {
                            value |= currentField.get(k + i).get(l + j);
                        } else {
                            value |= emptySpace;
                        }
                    }
                }

                if (translation.charAt(value) == '#') {
                    result.get(i).add(1);
                } else {
                    result.get(i).add(0);
                }
            }
        }
        return result;
    }

    public static long part1(List<String> input) {
        int empty = 0;
        List<List<Integer>> field = createField(input);
        for (int i = 0; i < 2; i++) {


            field = round(field, empty, input.get(0));
            if (empty == 0) {
                if (input.get(0).charAt(0) == '#') {
                    empty = 1;
                }
            } else {
                if (input.get(0).charAt(input.get(0).length() - 1) == '.') {
                    empty = 0;
                }
            }
        }
        System.out.println(field);
        return field.stream().flatMap(Collection::stream).filter(x -> x == 1).count();
    }

    public static long part2(List<String> input) {
        int empty = 0;
        List<List<Integer>> field = createField(input);
        for (int i = 0; i < 50; i++) {


            field = round(field, empty, input.get(0));
            if (empty == 0) {
                if (input.get(0).charAt(0) == '#') {
                    empty = 1;
                }
            } else {
                if (input.get(0).charAt(input.get(0).length() - 1) == '.') {
                    empty = 0;
                }
            }
        }
        return field.stream().flatMap(Collection::stream).filter(x -> x == 1).count();
    }

    public static List<String> readFile(String path) {
        List<String> input = new LinkedList<>();
        try (
                BufferedReader in = Files.newBufferedReader(Paths.get(path))
        ) {
            String line = "";
            while ((line = in.readLine()) != null) {
                line = line.trim();
                if (line.length() > 0) {
                    input.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }
}
