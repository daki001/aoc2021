package Day12;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day12 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Map<String, List<String>> input = readFile("src/Day12/testInput");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Tests: " + (System.currentTimeMillis() - startTime) + " ms\n");
        startTime = System.currentTimeMillis();
        input = readFile("src/Day12/input");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Solution: " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static int path(Map<String, List<String>> input, List<String> used, String current) {
        if (current.equals("end")) {
            return 1;
        }
        int result = 0;
        for (String next : input.get(current)) {
            if (next.toUpperCase().equals(next) || !used.contains(next)) {
                used.add(next);
                result += path(input, used, next);
                used.remove(used.size() - 1);
            }
        }

        return result;
    }

    public static int part1(Map<String, List<String>> input) {
        String current = "start";
        List<String> used = new LinkedList<>();
        used.add(current);
        return path(input, used, current);
    }

    public static int path2(Map<String, List<String>> input, List<String> used, String current, boolean doubleUsed) {
        if (current.equals("end")) {
            return 1;
        }
        int result = 0;
        for (String next : input.get(current)) {
            if (next.equals("start")) {
                continue;
            }
            if (next.toUpperCase().equals(next)) {
                result += path2(input, used, next, doubleUsed);
            } else if (!used.contains(next)) {
                used.add(next);
                result += path2(input, used, next, doubleUsed);
                used.remove(used.size() - 1);
            } else if (!doubleUsed) {
                used.add(next);
                result += path2(input, used, next, true);
                used.remove(used.size() - 1);
            }
        }

        return result;
    }

    public static int part2(Map<String, List<String>> input) {
        String current = "start";
        List<String> used = new LinkedList<>();
        used.add(current);
        return path2(input, used, current, false);
    }

    public static Map<String, List<String>> readFile(String path) {
        Map<String, List<String>> result = new HashMap<>();
        try {
            BufferedReader in = Files.newBufferedReader(Paths.get(path));
            String line;
            while ((line = in.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split("-");
                if (!result.keySet().contains(parts[0])) {
                    result.put(parts[0], new LinkedList<>());
                }
                result.get(parts[0]).add(parts[1]);

                if (!result.keySet().contains(parts[1])) {
                    result.put(parts[1], new LinkedList<>());
                }
                result.get(parts[1]).add(parts[0]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}