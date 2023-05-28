package Day3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 {
        public static void main(String[] args) {
            List<String> input = readFile("src/Day3/input");
            System.out.println(part1(input));
            System.out.println(part2(input));
        }

        public static int part1(List<String> input){
            int[] most = new int[input.get(0).length()];
            int mask = 0;
            for (int i = 0; i < input.get(0).length(); i++) {
                mask <<= 1;
                mask += 1;
                most[i] = 0;
            }
            int max = input.size();
            for (String s:input) {
                for (int i = 0; i < s.length(); i++) {
                    most[i] += s.charAt(i) - '0';
                }
            }
            int gamma = 0;
            for (int i = 0; i < input.get(0).length(); i++) {
                gamma <<= 1;
                if(max - most[i] > max/2) {
                    gamma += 1;
                }
            }
            return gamma * (~gamma & mask);
        }


        public static int part2(List<String> input){
            List<String> input1 = new LinkedList<>(input);
            List<String> input2 = new LinkedList<>(input);
            int value1 = 0;
            int value2 = 0;
            int ones1 = 0;
            int ones2 = 0;
            int max = input.size();
            for (int i = 0; i < input.get(0).length(); i++) {
                if(input1.size() != 1) {
                    ones1 = 0;
                    for (String s : input1) {
                        ones1 += s.charAt(i) - '0';
                    }

                    if (input1.size() - ones1 <= input1.size() / 2) {
                        value1 = 1;
                    } else {
                        value1 = 0;
                    }
                    int finalValue = value1;
                    int finalI = i;
                    input1 = input1.stream().filter(x -> x.charAt(finalI) - '0' == finalValue).collect(Collectors.toList());
                }

                if(input2.size() != 1) {
                    ones2 = 0;
                    for (String s : input2) {
                        ones2 += s.charAt(i) - '0';
                    }

                    if (input2.size() - ones2 <= input2.size() / 2) {
                        value2 = 0;
                    } else {
                        value2 = 1;
                    }
                    int finalI = i;
                    int finalValue2 = value2;
                    input2 = input2.stream().filter(x -> x.charAt(finalI) - '0' == finalValue2).collect(Collectors.toList());
                }
            }

            int erg1 = 0;
            int erg2 = 0;
            for (int i = 0; i < input1.get(0).length(); i++) {
                erg1 <<= 1;
                erg1 += input1.get(0).charAt(i) - '0';
                erg2 <<= 1;
                erg2 += input2.get(0).charAt(i) - '0';
            }
            return erg1 * erg2;
        }

        public static List<String> readFile(String path){
            try {
                List<String> erg = new LinkedList<>();
                BufferedReader in = Files.newBufferedReader(Paths.get(path));
                String line = "";
                while((line = in.readLine()) != null){
                    erg.add(line);
                }

                return erg;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new LinkedList<>();
        }
}
