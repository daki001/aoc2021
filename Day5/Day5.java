package Day5;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5 {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String> input = readFile("src/Day5/testInput");
        System.out.println(part1(input));
        System.out.println(part2(input, true));
        System.out.println("Time for Tests: " + (System.currentTimeMillis() - startTime) + " ms\n");
        startTime = System.currentTimeMillis();
        input = readFile("src/Day5/input");
        System.out.println(part1(input));
        System.out.println(part2(input, true));
        System.out.println("Time for Solution: " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static int part1(List<String> input) {
        return part2(input, false);
    }


    public static int part2(List<String> input, boolean isPart2) {
        int result = 0;
        List<Points> p = createPoints(input, isPart2);
        Map<String, Long> m = p.stream().collect(Collectors.groupingBy(x -> x.toString(), Collectors.counting()));
        for (long counter : m.values()) {
            if (counter > 1) {
                result++;
            }
        }

        return result;
    }

    public static List<Points> createPoints(List<String> input, boolean withDiagonals) {
        List<Points> p = new LinkedList<>();
        Pattern pat = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");
        for (String line : input) {
            Matcher m = pat.matcher(line);
            if (m.matches()) {
                int[] coords = new int[4];
                for (int i = 0; i < coords.length; i++) {
                    coords[i] = Integer.parseInt(m.group(i + 1));
                }
                if (coords[0] == coords[2]) {
                    p.addAll(IntStream.rangeClosed(Math.min(coords[1], coords[3]), Math.max(coords[1], coords[3])).mapToObj(y -> new Points(coords[0], y)).collect(Collectors.toList()));
                } else if (coords[1] == coords[3]) {
                    p.addAll(IntStream.rangeClosed(Math.min(coords[0], coords[2]), Math.max(coords[0], coords[2])).mapToObj(x -> new Points(x, coords[1])).collect(Collectors.toList()));
                } else if (withDiagonals) {
                    if ((coords[0] > coords[2] && coords[1] > coords[3]) || (coords[0] < coords[2] && coords[1] < coords[3])) {
                        int i = Math.min(coords[0], coords[2]);
                        int j = Math.min(coords[1], coords[3]);
                        while (i <= Math.max(coords[0], coords[2]) && j <= Math.max(coords[1], coords[3])) {
                            p.add(new Points(i, j));
                            i++;
                            j++;
                        }
                    } else {
                        int i = Math.min(coords[0], coords[2]);
                        int j = Math.max(coords[1], coords[3]);
                        while (i <= Math.max(coords[0], coords[2]) && j >= Math.min(coords[1], coords[3])) {
                            p.add(new Points(i, j));
                            i++;
                            j--;
                        }
                    }
                }
            }
        }
        return p;
    }

    public static List<String> readFile(String path) {
        try (
                BufferedReader in = Files.newBufferedReader(Paths.get(path))
        ) {
            List<String> erg = new LinkedList<>();
            String line;
            while ((line = in.readLine()) != null) {
                erg.add(line.trim());
            }
            return erg;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new LinkedList<>();
    }
}


class Points {
    private int x;
    private int y;

    public Points(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "Point " + this.x + ", " + y;
    }
}