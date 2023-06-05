package Day19;

import Day18.Number;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day19 {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<List<int[]>> input = readFile("src/Day19/testInput");
        System.out.println(part1(input));
        input = readFile("src/Day19/testInput");
        System.out.println(part2(input));
        System.out.println("Time for Tests: " + (System.currentTimeMillis() - startTime) + " ms\n");
        startTime = System.currentTimeMillis();
        input = readFile("src/Day19/input");
        System.out.println(part1(input));
        input = readFile("src/Day19/input");
        System.out.println(part2(input));
        System.out.println("Time for Solution: " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static long part1(List<List<int[]>> input) {
        int points = 0;
        while (true) {

            for (List<int[]> e : input) {

                System.out.println(e.stream().sorted(Comparator.comparingInt(x -> x[0])).map(x -> Arrays.toString(x)).collect(Collectors.toList()));
            }
            System.out.println();
            if (points == input.size() - 1) {
                return input.get(0).size();
            }
            for (int i = 0; i < input.size(); i++) {
                for (int j = i + 1; j < input.size(); j++) {
                    if (input.get(i).size() == 0 || input.get(j).size() == 0) {
                        continue;
                    }
                    List<List<int[]>> deltas1 = createDeltas(input.get(i));
                    List<List<int[]>> deltas2 = createDeltas(input.get(j));
                    int[] overlay = findOverlays(deltas1, deltas2);
                    if (overlay[0] != -1) {
                        points++;
                        List<int[]> transform = transformDeltas(deltas2.get(overlay[1]), overlay[2]);

                        final int iCopy = i;
                        transform = transform.stream().map(x -> new int[]{x[0] + input.get(iCopy).get(overlay[0])[0],
                                x[1] + input.get(iCopy).get(overlay[0])[1],
                                x[2] + input.get(iCopy).get(overlay[0])[2]}).collect(Collectors.toList());
                        next:
                        for (int[] t : transform) {
                            for (int[] element : input.get(i)) {
                                if (Arrays.equals(element, t)) {
                                    continue next;
                                }
                            }
                            input.get(i).add(t);
                        }
                        input.set(j, new LinkedList<>());
                    }
                }
            }
        }
    }

    public static long calculateManhatten(int[] array1, int[] array2) {
        long result = 0;
        for (int i = 0; i < array1.length; i++) {
            result += Math.abs(array1[i] - array2[i]);
        }
        return Math.abs(result);
    }

    public static long part2(List<List<int[]>> input) {
        Map<Integer, List<int[]>> scanners = new HashMap<>();
        scanners.put(0, new LinkedList<>());
        scanners.get(0).add(new int[]{0, 0, 0});
        int points = 0;
        while (true) {
            if (points == input.size() - 1) {
                System.out.println(scanners);
                System.out.println(scanners.get(0).stream().map(Arrays::toString).collect(Collectors.toList()));
                long result = 0;
                for (int i = 0; i < scanners.get(0).size(); i++) {
                    for (int j = i + 1; j < scanners.get(0).size(); j++) {
                        result = Math.max(result, calculateManhatten(scanners.get(0).get(i), scanners.get(0).get(j)));
                    }
                }
                return result;
            }
            for (int i = 0; i < input.size(); i++) {
                for (int j = i + 1; j < input.size(); j++) {
                    if (input.get(i).size() == 0 || input.get(j).size() == 0) {
                        continue;
                    }
                    List<List<int[]>> deltas1 = createDeltas(input.get(i));
                    List<List<int[]>> deltas2 = createDeltas(input.get(j));
                    int[] overlay = findOverlays(deltas1, deltas2);
                    if (overlay[0] != -1) {
                        points++;
                        List<int[]> transform = transformDeltas(deltas2.get(overlay[1]), overlay[2]);
                        List<int[]> scannerPosition = new LinkedList<>();
                        scannerPosition.add(input.get(j).get(overlay[1]));
                        scannerPosition = transformDeltas(scannerPosition, overlay[2]);

                        final int iCopy = i;
                        transform = transform.stream().map(x -> new int[]{x[0] + input.get(iCopy).get(overlay[0])[0],
                                x[1] + input.get(iCopy).get(overlay[0])[1],
                                x[2] + input.get(iCopy).get(overlay[0])[2]}).collect(Collectors.toList());
                        scannerPosition = scannerPosition.stream().map(x -> new int[]{input.get(iCopy).get(overlay[0])[0] - x[0],
                                input.get(iCopy).get(overlay[0])[1] - x[1],
                                input.get(iCopy).get(overlay[0])[2] - x[2]}).collect(Collectors.toList());

                        if (!scanners.containsKey(i)) {
                            scanners.put(i, new LinkedList<>());
                        }
                        scanners.get(i).addAll(scannerPosition);
                        if (scanners.containsKey(j)) {
                            List<int[]> otherScanners = transformDeltas(scanners.get(j), overlay[2]);
                            for (int[] s : otherScanners) {
                                for (int k = 0; k < 3; k++) {
                                    s[k] += scannerPosition.get(0)[k];
                                }
                                scanners.get(i).add(s);
                            }
                            scanners.remove(j);
                        }
                        next:
                        for (int[] t : transform) {
                            for (int[] element : input.get(i)) {
                                if (Arrays.equals(element, t)) {
                                    continue next;
                                }
                            }
                            input.get(i).add(t);
                        }
                        input.set(j, new LinkedList<>());
                    }
                }
            }
        }
    }

    public static List<List<int[]>> createDeltas(List<int[]> scanner) {
        List<List<int[]>> result = new LinkedList<>();
        for (int i = 0; i < scanner.size(); i++) {
            result.add(new LinkedList<>());
            for (int j = 0; j < scanner.size(); j++) {
                if (i != j) {
                    int[] delta = new int[3];
                    for (int k = 0; k < 3; k++) {
                        delta[k] = scanner.get(j)[k] - scanner.get(i)[k];
                    }
                    result.get(i).add(delta);
                }
            }
        }
        return result;
    }

    public static List<int[]> transformDeltas(List<int[]> deltas, int direction) {
        /*int count = 0;
        List<int[]> result = new LinkedList<>();
        for (int[] d:deltas) {
            result.add(new int[]{d[0],d[1],d[2]});
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    if(count == direction){
                        return result;
                    }
                    for (int[] r:result) {
                        int change = r[2];
                        r[2] = r[1];
                        r[1] = -change;
                    }
                    count++;
                }
                for (int[] r:result) {
                    int change = r[2];
                    r[2] = r[0];
                    r[0] = -change;
                }
            }
            for (int[] r:result) {
                int change = r[1];
                r[1] = r[0];
                r[0] = -change;
            }
        }*/

        List<int[]> order = new LinkedList<>();
        order.add(new int[]{0, 1, 2});
        order.add(new int[]{0, 2, -1});
        order.add(new int[]{-1, 0, 2});
        order.add(new int[]{-2, 1, 0});
        order.add(new int[]{-2, 0, -1});
        order.add(new int[]{-1, -2, 0});
        List<int[]> result = new LinkedList<>();
        for (int[] delta : deltas) {
            int[] array = new int[3];
            for (int i = 0; i < 3; i++) {
                array[Math.abs(order.get(direction / 4)[i])] = delta[i];
                if (order.get(direction / 4)[i] < 0) {
                    array[Math.abs(order.get(direction / 4)[i])] *= -1;
                }
            }
            switch (direction % 4) {
                case 1 -> {
                    array[0] = -array[0];
                    array[1] = -array[1];
                }
                case 2 -> {
                    array[0] = -array[0];
                    array[2] = -array[2];
                }
                case 3 -> {
                    array[1] = -array[1];
                    array[2] = -array[2];
                }
            }
            result.add(array);
        }
        return result;
    }

    public static int[] findOverlays(List<List<int[]>> scanner1, List<List<int[]>> scanner2) {
        for (int index = 0; index < scanner1.size(); index++) {
            List<int[]> deltas = scanner1.get(index);
            for (int index2 = 0; index2 < scanner2.size(); index2++) {
                List<int[]> deltas2 = scanner2.get(index2);
                for (int i = 0; i < 24; i++) {
                    List<int[]> transformedDeltas2 = transformDeltas(deltas2, i);

                    int counter = 0;
                    for (int[] singleDelta1 : deltas) {
                        for (int[] singleDelta2 : transformedDeltas2) {
                            if (Arrays.equals(singleDelta1, singleDelta2)) {
                                counter++;
                            }
                        }
                    }

                    if (counter >= 11) {
                        return new int[]{index, index2, i};
                    }
                }
            }
        }
        return new int[]{-1};
    }

    public static List<List<int[]>> readFile(String path) {
        List<List<int[]>> input = new LinkedList<>();
        try (
                BufferedReader in = Files.newBufferedReader(Paths.get(path))
        ) {
            String line = "";
            while ((line = in.readLine()) != null) {
                line = line.trim();
                if (line.contains("scanner")) {
                    input.add(new LinkedList<>());
                } else if (line.length() > 0) {
                    int[] beaconCoords = new int[3];
                    int index = 0;
                    for (String coord : line.split(",")) {
                        beaconCoords[index] = Integer.parseInt(coord);
                        index++;
                    }
                    input.get(input.size() - 1).add(beaconCoords);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }
}
