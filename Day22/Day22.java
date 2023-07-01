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
        List<int[]> test1 = new LinkedList<>();
        test1.add(new int[]{-49, -5});
        test1.add(new int[]{-3, 45});
        test1.add(new int[]{-29, 18});
        List<int[]> test2 = new LinkedList<>();
        test2.add(new int[]{-18, 26});
        test2.add(new int[]{-33, 15});
        test2.add(new int[]{-7, 46});

        overlay(test2, test1).forEach(a -> System.out.println(Arrays.toString(a)));

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


    public static boolean equalField(List<int[]> element1, List<int[]> element2) {
        if (Math.min(element1.size(), element2.size()) == 0) {
            return false;
        }
        boolean result = true;
        for (int i = 0; i < element1.size(); i++) {
            result &= Arrays.equals(element1.get(i), element2.get(i));
        }
        return result;
    }

    public static long recursiveOverlays(List<int[]> current, List<List<List<int[]>>> elements, boolean add, int ind) {
        long result = 0;
        if (ind + 1 < elements.size()) {
            elements.set(ind + 1, new LinkedList<>());
        }
        if (ind < elements.size()) {
            for (List<int[]> e : elements.get(ind)) {
                List<int[]> overlay = overlay(current, e);
                if (overlay.size() > 0) {

                    long value = calculateFields(overlay);
                    if (add) {
                        result += value;
                    } else {
                        result -= value;
                    }

                    result += recursiveOverlays(overlay, elements, !add, ind + 1);

                    elements.get(ind + 1).add(overlay);
                }
            }
        } else {
            elements.add(new LinkedList<>());
        }
        return result;
    }

    public static long part2(List<String[]> input) {
        long result = 0;

        Collections.reverse(input);
        List<List<int[]>> used = new LinkedList<>();
        for (String[] round : input) {

            int[] x = new int[]{Integer.parseInt(round[1].split("\\.\\.")[0]), Integer.parseInt(round[1].split("\\.\\.")[1])};
            int[] y = new int[]{Integer.parseInt(round[2].split("\\.\\.")[0]), Integer.parseInt(round[2].split("\\.\\.")[1])};
            int[] z = new int[]{Integer.parseInt(round[3].split("\\.\\.")[0]), Integer.parseInt(round[3].split("\\.\\.")[1])};

            boolean test = round[0].equals("on");

            List<int[]> currentElement = new LinkedList<>();
            currentElement.add(x);
            currentElement.add(y);
            currentElement.add(z);

            if (test) {
                List<List<List<int[]>>> recTest = new LinkedList<>();
                recTest.add(used);
                long overlay = recursiveOverlays(currentElement, recTest, true, 0);
                result += calculateFields(currentElement);
                result -= overlay;

            }
            used.add(currentElement);

        }

        return result;
    }


    public static long calculateFields(List<int[]> cube) {
        return (long) (cube.get(0)[1] + 1 - cube.get(0)[0]) *
                (cube.get(1)[1] + 1 - cube.get(1)[0]) *
                (cube.get(2)[1] + 1 - cube.get(2)[0]);
    }

    public static List<int[]> overlay(List<int[]> currentElement, List<int[]> otherElement) {
        List<int[]> result = new LinkedList<>();

        int[] currentX = currentElement.get(0);
        int[] currentY = currentElement.get(1);
        int[] currentZ = currentElement.get(2);

        int[] otherX = otherElement.get(0);
        int[] otherY = otherElement.get(1);
        int[] otherZ = otherElement.get(2);
        if ((otherX[1] >= currentX[0] && otherX[0] <= currentX[0]) ||
                (otherX[1] >= currentX[1] && otherX[0] <= currentX[1]) ||
                (otherX[0] >= currentX[0] && otherX[0] <= currentX[1]) ||
                (otherX[1] >= currentX[0] && otherX[1] <= currentX[1])) {

            if ((otherY[1] >= currentY[0] && otherY[0] <= currentY[0]) ||
                    (otherY[1] >= currentY[1] && otherY[0] <= currentY[1]) ||
                    (otherY[0] >= currentY[0] && otherY[0] <= currentY[1]) ||
                    (otherY[1] >= currentY[0] && otherY[1] <= currentY[1])) {

                if ((otherZ[1] >= currentZ[0] && otherZ[0] <= currentZ[0]) ||
                        (otherZ[1] >= currentZ[1] && otherZ[0] <= currentZ[1]) ||
                        (otherZ[0] >= currentZ[0] && otherZ[0] <= currentZ[1]) ||
                        (otherZ[1] >= currentZ[0] && otherZ[1] <= currentZ[1])) {
                    result.add(new int[]{Math.max(currentX[0], otherX[0]), Math.min(currentX[1], otherX[1])});
                    result.add(new int[]{Math.max(currentY[0], otherY[0]), Math.min(currentY[1], otherY[1])});
                    result.add(new int[]{Math.max(currentZ[0], otherZ[0]), Math.min(currentZ[1], otherZ[1])});
                }
            }
        }
        return result;
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
