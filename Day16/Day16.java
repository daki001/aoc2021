package Day16;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day16 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        BigInteger input = readFile("src/Day16/testInput");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Tests: " + (System.currentTimeMillis() - startTime) + " ms\n");
        startTime = System.currentTimeMillis();
        input = readFile("src/Day16/input");
        System.out.println(part1(input));
        System.out.println(part2(input));
        System.out.println("Time for Solution: " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static long part1(BigInteger input) {
        int startCounter = input.bitLength() - 2;
        long[] result = interpretePacket(input, startCounter);

        return result[1];
    }

    public static long[] interpretePacket(BigInteger input, int currentStart) {

        int version = input.shiftRight(currentStart - 2).intValue() & 0b111;
        int type = input.shiftRight(currentStart - 5).intValue() & 0b111;
        if (type == 4) {
            int current = currentStart - 10;
            long value = 0;
            while (true) {
                value += input.shiftRight(current).intValue() & 0b1111;
                if ((input.shiftRight(current + 4).intValue() & 1) != 1) {
                    return new long[]{value, version, current - 1};
                }
                current -= 5;
                value <<= 4;
            }
        } else {
            int current = currentStart - 6;
            if ((input.shiftRight(current).intValue() & 1) == 1) {
                current -= 11;
                int packets = input.shiftRight(current).intValue() & 0x7ff;
                current -= 1;
                long[] result = new long[]{-1, version, 0};

                for (int i = 0; i < packets; i++) {
                    long[] subpacket = interpretePacket(input, current);
                    if (result[0] == -1) {
                        result[0] = subpacket[0];
                    } else {
                        switch (type) {
                            case 0:
                                result[0] += subpacket[0];
                                break;
                            case 1:
                                result[0] *= subpacket[0];
                                break;
                            case 2:
                                result[0] = Math.min(subpacket[0], result[0]);
                                break;
                            case 3:
                                result[0] = Math.max(subpacket[0], result[0]);
                                break;
                            case 5:
                                result[0] = (result[0] > subpacket[0]) ? 1 : 0;
                                break;
                            case 6:
                                result[0] = (result[0] < subpacket[0]) ? 1 : 0;
                                break;
                            case 7:
                                result[0] = (result[0] == subpacket[0]) ? 1 : 0;
                                break;
                        }
                    }
                    result[1] += subpacket[1];
                    current = (int) subpacket[2];
                }
                result[2] = current;
                return result;
            } else {

                current -= 15;
                int bits = input.shiftRight(current).intValue() & 0x7fff;
                int end = current - bits;
                current -= 1;
                long[] result = new long[]{-1, version, 0};
                while (current >= end) {
                    long[] subpacket = interpretePacket(input, current);
                    if (result[0] == -1) {
                        result[0] = subpacket[0];
                    } else {
                        switch (type) {
                            case 0:
                                result[0] += subpacket[0];
                                break;
                            case 1:
                                result[0] *= subpacket[0];
                                break;
                            case 2:
                                result[0] = Math.min(subpacket[0], result[0]);
                                break;
                            case 3:
                                result[0] = Math.max(subpacket[0], result[0]);
                                break;
                            case 5:
                                result[0] = (result[0] > subpacket[0]) ? 1 : 0;
                                break;
                            case 6:
                                result[0] = (result[0] < subpacket[0]) ? 1 : 0;
                                break;
                            case 7:
                                result[0] = (result[0] == subpacket[0]) ? 1 : 0;
                                break;
                        }
                    }
                    result[1] += subpacket[1];
                    current = (int) subpacket[2];
                }
                result[2] = current;
                return result;
            }
        }
    }

    public static long part2(BigInteger input) {
        int startCounter = input.bitLength() - 2;
        long[] result = interpretePacket(input, startCounter);

        return result[0];
    }

    public static BigInteger readFile(String path) {
        BigInteger result = new BigInteger("16");
        try (
                BufferedReader in = Files.newBufferedReader(Paths.get(path))
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                line = line.trim();
                line = "1" + line;
                result = new BigInteger(line, 16);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}