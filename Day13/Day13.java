package Day13;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<List<Integer>> input = readFile("src/Day13/testInput");
        System.out.println(part1(input));
        input = readFile("src/Day13/testInput");
        part2(input);
        System.out.println("Time for Tests: " + (System.currentTimeMillis() - startTime) + " ms\n");
        startTime = System.currentTimeMillis();
        input = readFile("src/Day13/input");
        System.out.println(part1(input));
        input = readFile("src/Day13/input");
        part2(input);
        System.out.println("Time for Solution: " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static int part1(List<List<Integer>> input) {
        List<Integer> instructions = input.get(input.size() - 1);
        List<List<Integer>> points = input.subList(0, input.size() - 1);

        int instr = instructions.get(0);
        for (List<Integer> point : points) {
            if (instr < 0) {
                if (point.get(1) > Math.abs(instr)) {
                    point.set(1, Math.abs(instr) - (point.get(1) - Math.abs(instr)));
                }
            } else {
                if (point.get(0) > Math.abs(instr)) {
                    point.set(0, Math.abs(instr) - (point.get(0) - Math.abs(instr)));
                }
            }
        }

        return (int) points.stream().distinct().count();
    }

    public static void part2(List<List<Integer>> input) {
        List<Integer> instructions = input.get(input.size() - 1);
        List<List<Integer>> points = input.subList(0, input.size() - 1);

        for (int instr : instructions) {
            for (List<Integer> point : points) {
                if (instr < 0) {
                    if (point.get(1) > Math.abs(instr)) {
                        point.set(1, Math.abs(instr) - (point.get(1) - Math.abs(instr)));
                    }
                } else {
                    if (point.get(0) > Math.abs(instr)) {
                        point.set(0, Math.abs(instr) - (point.get(0) - Math.abs(instr)));
                    }
                }
            }
        }
        printPassword(points);
    }

    public static void printPassword(List<List<Integer>> elements) {
        int xMax = elements.stream().mapToInt(x -> x.get(0)).max().getAsInt();
        int yMax = elements.stream().mapToInt(x -> x.get(1)).max().getAsInt();
        for (int i = 0; i <= yMax; i++) {
            for (int j = 0; j <= xMax; j++) {
                if (elements.contains(Arrays.asList(j, i))) {
                    System.out.print('#');
                } else {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
    }

    public static List<List<Integer>> readFile(String path) {
        List<List<Integer>> result = new LinkedList<>();
        try {
            BufferedReader in = Files.newBufferedReader(Paths.get(path));
            String line;
            List<Integer> instructions = new LinkedList<>();
            Pattern pat = Pattern.compile("fold along ([xy])=(\\d+)");
            boolean part1 = true;
            while ((line = in.readLine()) != null) {
                line = line.trim();
                if (line.equals("")) {
                    part1 = false;
                    continue;
                }
                if (part1) {

                    String[] l = line.split(",");
                    List<Integer> values = new LinkedList<>();
                    for (String element : l) {
                        values.add(Integer.parseInt(element));
                    }
                    result.add(values);
                } else {
                    Matcher m = pat.matcher(line);
                    if (m.matches()) {
                        if (m.group(1).equals("x")) {
                            instructions.add(Integer.parseInt(m.group(2)));
                        } else {
                            instructions.add(-Integer.parseInt(m.group(2)));
                        }
                    }
                }
            }
            result.add(instructions);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}