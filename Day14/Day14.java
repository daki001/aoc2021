package Day14;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day14 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String> input = readFile("src/Day14/testInput");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Tests: " + (System.currentTimeMillis() - startTime) + " ms\n");
        startTime = System.currentTimeMillis();
        input = readFile("src/Day14/input");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Solution: " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static long polymerGrowth(List<String> input, int rounds){
        String start = input.get(0);
        String s1 = start.substring(0,1);
        String s2 = start.substring(start.length() - 1);
        Pattern pat = Pattern.compile("([A-Za-z]+) -> ([A-Za-z]+)");
        Map<String, String> translator = new HashMap<>();
        for (int i = 1; i < input.size(); i++) {
            Matcher m = pat.matcher(input.get(i));
            if(m.matches()){
                translator.put(m.group(1), m.group(2));
            }
        }

        Map<String, Long> pairs = new HashMap<>();
        for (int i = 0; i < start.length() - 1; i++) {
            String p = start.charAt(i) + "" + start.charAt(i + 1);
            long counter = 1;
            if (pairs.containsKey(p)){
                counter += pairs.get(p);
            }
            pairs.put(p,counter);
        }
        Map<String, Long> newPairs = new HashMap<>();
        for (int i = 0; i < rounds; i++) {
            for (String key:pairs.keySet()) {
                long counter1 = pairs.get(key);
                long counter2 = pairs.get(key);
                String p1 = key.charAt(0) + translator.get(key);
                String p2 = translator.get(key) + key.charAt(1);
                if (newPairs.containsKey(p1)){
                    counter1 += newPairs.get(p1);
                }
                newPairs.put(p1, counter1);
                if (newPairs.containsKey(p2)){
                    counter2 += newPairs.get(p2);
                }

                newPairs.put(p2, counter2);
            }
            pairs = newPairs;
            newPairs = new HashMap<>();
        }

        Map<String, Long> counters = new HashMap<>();
        for (String key:pairs.keySet()) {
            long c1 = pairs.get(key);
            long c2 = pairs.get(key);
            if(counters.containsKey(key.substring(0,1))){
                c1 += counters.get(key.substring(0,1));
            }
            counters.put(key.substring(0,1), c1);
            if(counters.containsKey(key.substring(1))){
                c2 += counters.get(key.substring(1));
            }
            counters.put(key.substring(1),c2);
        }
        for (String key: counters.keySet()) {
            counters.put(key, counters.get(key) / 2);
        }
        if(counters.containsKey(s1)){
            counters.put(s1, counters.get(s1) + 1);
        }else{
            counters.put(s1, (long)1);
        }
        if(counters.containsKey(s2)){
            counters.put(s2, counters.get(s2) + 1);
        }else{
            counters.put(s2, (long)1);
        }
        return counters.values().stream().mapToLong(x->x).max().getAsLong() - counters.values().stream().mapToLong(x->x).min().getAsLong();
    }

    public static long part1(List<String> input) {
        return polymerGrowth(input, 10);
    }

    public static long part2(List<String> input) {
        return polymerGrowth(input, 40);
    }

    public static List<String> readFile(String path) {
        List<String> result = new LinkedList<>();
        try {
            BufferedReader in = Files.newBufferedReader(Paths.get(path));
            String line;
            while ((line = in.readLine()) != null) {
                line = line.trim();
                if(line.length() > 0){
                    result.add(line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}