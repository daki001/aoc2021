package Day9;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day9 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<int[]> input = readFile("src/Day9/testInput");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Tests: " + (System.currentTimeMillis() - startTime) + " ms\n");
        startTime = System.currentTimeMillis();
        input = readFile("src/Day9/input");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Solution: " + (System.currentTimeMillis() - startTime) + " ms");
    }


    public static int part1(List<int[]> input) {
        int result = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length; j++) {
                boolean isLowPoint = true;
                int current = input.get(i)[j];
                isLowPoint &= (i == 0 || input.get(i - 1)[j] > current)
                        && (i == input.size() - 1 || input.get(i + 1)[j] > current)
                        && (j == 0 || input.get(i)[j - 1] > current)
                        && (j == input.get(i).length - 1 || input.get(i)[j + 1] > current);
                if (isLowPoint) {
                    result += current + 1;
                }

            }
        }
        return result;
    }

    public static int basin(List<int[]> input, int x, int y, List<String> used) {
        if (x < 0 || y < 0 || x >= input.size() || y >= input.get(0).length || input.get(x)[y] == 9 || used.contains(x + "/" + y)) {
            return 0;
        }
        int counter = 1;
        used.add(x + "/" + y);
        counter += basin(input, x + 1, y, used);
        counter += basin(input, x - 1, y, used);
        counter += basin(input, x, y + 1, used);
        counter += basin(input, x, y - 1, used);
        return counter;
    }

    public static int part2(List<int[]> input) {
        int result = 0;
        List<Integer> basins = new LinkedList<>();
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length; j++) {
                boolean isLowPoint = true;
                int current = input.get(i)[j];
                isLowPoint = (i == 0 || input.get(i - 1)[j] > current)
                        && (i == input.size() - 1 || input.get(i + 1)[j] > current)
                        && (j == 0 || input.get(i)[j - 1] > current)
                        && (j == input.get(i).length - 1 || input.get(i)[j + 1] > current);
                if (isLowPoint) {
                    basins.add(basin(input, i, j, new LinkedList<>()));
                }
            }
        }
        return basins.stream().sorted(Comparator.reverseOrder()).limit(3).mapToInt(x -> x).reduce(1, (x, y) -> x * y);
    }

    public static List<int[]> readFile(String path) {
        List<int[]> result = new LinkedList<>();
        try (
                BufferedReader in = Files.newBufferedReader(Paths.get(path))
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                line = line.trim();
                char[] lineChars = line.toCharArray();
                int[] lineInts = new int[lineChars.length];
                for (int i = 0; i < lineChars.length; i++) {
                    lineInts[i] = lineChars[i] - '0';
                }
                result.add(lineInts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}