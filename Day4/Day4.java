package Day4;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String> input = readFile("src/Day4/testInput");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Tests: " + (System.currentTimeMillis() - startTime) + " ms\n");
        startTime = System.currentTimeMillis();
        input = readFile("src/Day4/input");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Solution: " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static int calculateScore(List<int[]> field, int lastInt) {
        int result = field.stream().flatMapToInt(Arrays::stream).filter(x -> x != -1).sum();
        return result * lastInt;
    }

    public static boolean calculateWinning(List<int[]> field) {
        boolean winning = false;
        for (int i = 0; i < field.size(); i++) {
            boolean winningRow = true;
            for (int j = 0; j < field.get(i).length; j++) {
                winningRow &= field.get(i)[j] == -1;
            }
            if (winningRow) {
                return true;
            }
        }
        for (int i = 0; i < field.get(0).length; i++) {
            int finalI = i;
            winning |= field.stream().map(x -> x[finalI]).noneMatch(x -> x != -1);
        }

        return winning;
    }

    public static int part1(List<String> input) {
        int[] instructions = findInstructions(input);
        List<List<int[]>> fields = findFields(input);
        for (int currentNumber : instructions) {
            for (List<int[]> f : fields) {
                for (int j = 0; j < f.size(); j++) {
                    int[] line = f.get(j);
                    f.set(j, Arrays.stream(line).map(x -> {
                        if (x == currentNumber) {
                            return -1;
                        }
                        return x;
                    }).toArray());

                }
                if (calculateWinning(f)) {
                    return calculateScore(f, currentNumber);
                }
            }
        }
        return -1;

    }


    public static int part2(List<String> input) {
        List<List<int[]>> finished = new LinkedList<>();
        int[] instructions = findInstructions(input);
        List<List<int[]>> fields = findFields(input);
        for (int currentNumber : instructions) {
            for (List<int[]> f : fields) {
                if (!finished.contains(f)) {
                    for (int j = 0; j < f.size(); j++) {
                        int[] line = f.get(j);
                        f.set(j, Arrays.stream(line).map(x -> {
                            if (x == currentNumber) {
                                return -1;
                            }
                            return x;
                        }).toArray());

                    }
                    if (calculateWinning(f)) {
                        finished.add(f);
                        if (finished.size() == fields.size()) {
                            return calculateScore(f, currentNumber);
                        }
                    }
                }
            }
        }
        return -1;
    }


    public static int[] findInstructions(List<String> input) {
        return Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
    }

    public static List<List<int[]>> findFields(List<String> input) {
        List<List<int[]>> allFields = new LinkedList<>();
        Pattern pat = Pattern.compile("\\s*(\\d+)");
        LinkedList<int[]> field = new LinkedList<>();
        int[] fieldLine = new int[5];
        for (int i = 1; i < input.size(); i++) {
            String line = input.get(i);
            line = line.trim();
            if (line.length() > 0) {
                Matcher m = pat.matcher(line);
                int counter = 0;

                while (m.find()) {
                    fieldLine[counter] = Integer.parseInt(m.group(1));
                    counter++;
                }
                int[] finalLine = fieldLine.clone();
                field.add(finalLine);
            } else {
                if (field.size() > 0) {
                    allFields.add(field);
                }
                field = new LinkedList<>();
            }
        }
        if (field.size() > 0) {
            allFields.add(field);
        }
        return allFields;
    }

    public static List<String> readFile(String path) {
        try {
            List<String> erg = new LinkedList<>();
            BufferedReader in = Files.newBufferedReader(Paths.get(path));
            String line;
            while ((line = in.readLine()) != null) {
                erg.add(line);
            }
            return erg;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new LinkedList<>();
    }
}