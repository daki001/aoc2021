package Day8;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day8 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<List<String>> input = readFile("src/Day8/testInput");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Tests: " + (System.currentTimeMillis() - startTime) + " ms\n");
        startTime = System.currentTimeMillis();
        input = readFile("src/Day8/input");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Solution: " + (System.currentTimeMillis() - startTime) + " ms");
    }


    public static long part1(List<List<String>> input) {
        return input.stream().map(x -> x.get(1)).flatMap(x -> Arrays.stream(x.split(" "))).filter(x -> x.length() != 5 && x.length() != 6).count();
    }

    public static String[] translation(List<String> line) {
        String[] translations = new String[10];
        Map<String, String> letters = new HashMap<>();
        String[] originals = line.get(0).split(" ");
        for (int i = 0; i < originals.length; i++) {
            switch (originals[i].length()) {
                case 2:
                    translations[1] = originals[i];
                    break;
                case 3:
                    translations[7] = originals[i];
                    break;
                case 4:
                    translations[4] = originals[i];
                    break;
                case 7:
                    translations[8] = originals[i];
                    break;
            }
        }

        for (int i = 0; i < translations[7].length(); i++) {
            if (!translations[1].contains(translations[7].charAt(i) + "")) {
                letters.put("a", translations[7].charAt(i) + "");
            }
        }
        for (String org : originals) {
            if (org.length() == 6) {
                for (Character c : translations[1].toCharArray()) {
                    if (!org.contains(c + "")) {
                        letters.put("c", c + "");
                        translations[6] = org;
                    }
                }
                if (!(translations[6] != null && translations[6].equals(org))) {
                    boolean isNine = true;
                    for (Character c : translations[4].toCharArray()) {
                        isNine &= org.contains(c + "");
                    }
                    if (isNine) {
                        translations[9] = org;
                        for (Character c : translations[8].toCharArray()) {
                            if (!org.contains(c + "")) {
                                letters.put("e", c + "");
                            }
                        }
                    } else {
                        translations[0] = org;
                        for (Character c : translations[8].toCharArray()) {
                            if (!org.contains(c + "")) {
                                letters.put("d", c + "");
                            }
                        }
                    }
                }
            }
        }
        for (Character c : translations[1].toCharArray()) {
            if (!letters.get("c").equals(c + "")) {
                letters.put("f", c + "");
            }
        }
        for (String org : originals) {
            if (org.length() == 5) {
                if (org.contains(letters.get("e"))) {
                    translations[2] = org;
                } else if (org.contains(letters.get("c"))) {
                    translations[3] = org;
                } else {
                    translations[5] = org;
                }
            }
        }


        return translations;
    }

    public static int part2(List<List<String>> input) {
        int result = 0;
        for (List<String> line : input) {
            String finalNumber = "";
            String[] test = translation(line);
            next:
            for (String number : line.get(1).split(" ")) {
                for (int i = 0; i < test.length; i++) {
                    if (number.length() == test[i].length()) {
                        boolean isNumber = true;
                        for (Character c : number.toCharArray()) {
                            isNumber &= test[i].contains(c + "");
                        }
                        if (isNumber) {
                            finalNumber += i;
                            continue next;
                        }
                    }
                }
            }
            result += Integer.parseInt(finalNumber);
        }
        return result;
    }

    public static List<List<String>> readFile(String path) {
        List<List<String>> result = new LinkedList<>();
        try (
                BufferedReader in = Files.newBufferedReader(Paths.get(path))
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split(" \\| ");
                result.add(Arrays.asList(parts));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}