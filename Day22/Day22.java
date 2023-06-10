package Day22;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day22 {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String[]> input = readFile("src/Day22/testInput");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Tests: " + (System.currentTimeMillis() - startTime) + " ms\n");
        startTime = System.currentTimeMillis();
        input = readFile("src/Day22/input");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Solution: " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static long part1(List<String[]> input) {
        boolean[][][] field = new boolean[101][101][101];
        for (String[] round : input) {
            int[] x = new int[]{Math.max(Integer.parseInt(round[1].split("\\.\\.")[0]), -50), Math.min(Integer.parseInt(round[1].split("\\.\\.")[1]), 50)};
            int[] y = new int[]{Math.max(Integer.parseInt(round[2].split("\\.\\.")[0]), -50), Math.min(Integer.parseInt(round[2].split("\\.\\.")[1]), 50)};
            int[] z = new int[]{Math.max(Integer.parseInt(round[3].split("\\.\\.")[0]), -50), Math.min(Integer.parseInt(round[3].split("\\.\\.")[1]), 50)};

            boolean test = round[0].equals("on");

            for (int i = x[0] + 50; i <= x[1] + 50; i++) {
                for (int j = y[0] + 50; j <= y[1] + 50; j++) {
                    for (int k = z[0] + 50; k <= z[1] + 50; k++) {
                        field[i][j][k] = test;
                    }
                }
            }

        }

        int result = 0;
        for (boolean[][] arr1 : field) {
            for (boolean[] arr2 : arr1) {
                for (boolean bool : arr2) {
                    if (bool) {
                        result++;
                    }
                }
            }
        }
        return result;
    }

    public static long part2(List<String[]> input) {
        return -1;
    }

    public static List<String[]> readFile(String path) {
        List<String[]> input = new LinkedList<>();
        try (
                BufferedReader in = Files.newBufferedReader(Paths.get(path))
        ) {
            String line = "";
            String[] parts;
            while ((line = in.readLine()) != null) {
                parts = line.trim().split("[, ]");
                for (int i = 1; i < 4; i++) {
                    parts[i] = parts[i].substring(2);
                }
                input.add(parts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }
}
