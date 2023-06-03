package Day18;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String> input = readFile("src/Day18/testInput");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Tests: " + (System.currentTimeMillis() - startTime) + " ms\n");
        startTime = System.currentTimeMillis();
        input = readFile("src/Day18/input");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Solution: " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static long part1(List<String> input) {
        Number n1 = createNumber(input.get(0));
        for (String element : input.subList(1, input.size())) {
            Number n2 = createNumber(element);
            n1 = addNumbers(n1, n2);
        }
        System.out.println(n1);
        return n1.calculateMagnitude();
    }

    public static long part2(List<String> input) {
        long bestResult = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = i + 1; j < input.size(); j++) {
                bestResult = Math.max(bestResult, addNumbers(createNumber(input.get(i)), createNumber(input.get(j))).calculateMagnitude());
                bestResult = Math.max(bestResult, addNumbers(createNumber(input.get(j)), createNumber(input.get(i))).calculateMagnitude());
            }
        }
        return bestResult;
    }


    public static Number addNumbers(Number line1, Number line2) {
        Number n = new Number(line1, line2);
        int status1 = 1;
        int status2 = 1;
        while (status1 != 0 || status2 != 0) {
            status1 = explode(n, 0, null, null, new Number[1], new boolean[1], new int[1], 0);
            status2 = split(n);
        }
        return n;
    }

    public static Number createNumber(String line) {
        int level = 0;
        Number result = new Number();
        line = line.substring(1, line.length() - 1);
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == ',' && level == 0) {
                String part1 = line.substring(0, i);
                String part2 = line.substring(i + 1);
                if (part1.contains(",")) {
                    result.leftNumber = createNumber(part1);
                } else {
                    result.leftValue = Integer.parseInt(part1);
                    result.leftInt = true;
                }

                if (part2.contains(",")) {
                    result.rightNumber = createNumber(part2);
                } else {
                    result.rightValue = Integer.parseInt(part2);
                    result.rightInt = true;
                }
            }
            if (c == '[') {
                level++;
            } else if (c == ']') {
                level--;
            }
        }
        return result;
    }

    public static int explode(Number line, int level, Number last, Boolean lastNumberSide, Number[] leftNumber, boolean[] left, int[] changeRight, int status) {
        if (status == 2) {
            return status;
        }
        if (level >= 4 && line.leftInt && line.rightInt && status == 0) {
            if (lastNumberSide) {
                last.leftValue = 0;
                last.leftInt = true;
            } else {
                last.rightValue = 0;
                last.rightInt = true;
            }

            if (leftNumber[0] != null) {
                if (left[0]) {
                    leftNumber[0].leftValue += line.leftValue;
                } else {
                    leftNumber[0].rightValue += line.leftValue;
                }
            }

            leftNumber[0] = null;
            left[0] = false;

            changeRight[0] = line.rightValue;
            return 1;
        }

        do {
            if (level == 0) {
                status = 0;
            }
            if (!line.leftInt) {
                status = explode(line.leftNumber, level + 1, line, true, leftNumber, left, changeRight, status);
                if (status == 2 && level != 0) {
                    return status;
                }
            } else {
                if (status == 1) {
                    line.leftValue += changeRight[0];
                    return 2;
                }
                leftNumber[0] = line;
                left[0] = true;
            }

            if (!line.rightInt) {
                status = explode(line.rightNumber, level + 1, line, false, leftNumber, left, changeRight, status);
            } else {
                if (status == 1) {
                    line.rightValue += changeRight[0];
                    return 2;
                }
                leftNumber[0] = line;
                left[0] = false;
            }
        } while (level == 0 && status != 0);
        return status;
    }

    public static int split(Number line) {
        if (line.leftInt) {
            if (line.leftValue >= 10) {
                line.leftNumber = new Number(line.leftValue / 2, (line.leftValue + 1) / 2);
                line.leftInt = false;
                return 1;
            }
        } else {
            int status = split(line.leftNumber);
            if (status == 1) {
                return status;
            }
        }

        if (line.rightInt) {
            if (line.rightValue >= 10) {
                line.rightNumber = new Number(line.rightValue / 2, (line.rightValue + 1) / 2);
                line.rightInt = false;
                return 1;
            }
        } else {
            int status = split(line.rightNumber);
            if (status == 1) {
                return status;
            }
        }

        return 0;
    }

    public static List<String> readFile(String path) {
        List<String> input = new LinkedList<>();
        try (
                BufferedReader in = Files.newBufferedReader(Paths.get(path))
        ) {
            String line = "";
            while ((line = in.readLine()) != null) {
                input.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }
}