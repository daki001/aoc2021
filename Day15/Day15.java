package Day15;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day15 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<List<Integer>> input = readFile("src/Day15/testInput");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Tests: " + (System.currentTimeMillis() - startTime) + " ms\n");
        startTime = System.currentTimeMillis();
        input = readFile("src/Day15/input");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Solution: " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static int[][] shortesPath(List<List<Integer>> input) {
        int[][] shortestPaths = new int[input.size()][input.get(0).size()];
        for (int i = 0; i < shortestPaths.length; i++) {
            for (int j = 0; j < shortestPaths[i].length; j++) {
                shortestPaths[i][j] = -1;
            }
        }
        int[] start = new int[]{0, 0, 0};
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[2], o2[2]));
        queue.add(start);

        while (!queue.isEmpty()) {
            int[] next = queue.poll();
            if (shortestPaths[next[0]][next[1]] != -1) {
                continue;
            }
            shortestPaths[next[0]][next[1]] = next[2];
            if (next[0] > 0) {
                queue.add(new int[]{next[0] - 1, next[1], next[2] + input.get(next[0] - 1).get(next[1])});
            }
            if (next[1] > 0) {
                queue.add(new int[]{next[0], next[1] - 1, next[2] + input.get(next[0]).get(next[1] - 1)});
            }
            if (next[0] < input.size() - 1) {
                queue.add(new int[]{next[0] + 1, next[1], next[2] + input.get(next[0] + 1).get(next[1])});
            }
            if (next[1] < input.get(0).size() - 1) {
                queue.add(new int[]{next[0], next[1] + 1, next[2] + input.get(next[0]).get(next[1] + 1)});
            }
        }

        return shortestPaths;
    }

    public static int part1(List<List<Integer>> input) {
        return shortesPath(input)[input.size() - 1][input.get(0).size() - 1];
    }

    public static int part2(List<List<Integer>> input) {
        List<List<Integer>> field = new LinkedList<>();

        for (int j = 0; j < 5; j++) {
            for (List<Integer> l : input) {
                List<Integer> line = new LinkedList<>();
                for (int i = 0; i < 5; i++) {
                    for (int element : l) {
                        int newValue = (element + i + j);
                        while (newValue >= 10) {
                            newValue -= 9;
                        }

                        line.add(newValue);
                    }
                }
                field.add(line);
            }
        }

        return shortesPath(field)[field.size() - 1][field.get(0).size() - 1];
    }

    public static List<List<Integer>> readFile(String path) {
        List<List<Integer>> result = new LinkedList<>();
        try {
            BufferedReader in = Files.newBufferedReader(Paths.get(path));
            String line;
            while ((line = in.readLine()) != null) {
                line = line.trim();
                String[] elements = line.split("");
                List<Integer> l = Arrays.stream(elements).map(Integer::parseInt).collect(Collectors.toList());
                result.add(l);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}