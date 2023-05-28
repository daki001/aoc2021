package Day2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Day2 {

    public static void main(String[] args) {
        List<String> input = readFile("src/Day2/input");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static int part1(List<String> input){
        int hor = 0;
        int vert = 0;
        for (String s : input) {
            String[] vars = s.split(" ");
            switch (vars[0]){
                case "forward":
                    hor += Integer.parseInt(vars[1]);
                    break;
                case "up":
                    vert -= Integer.parseInt(vars[1]);
                    break;
                case "down":
                    vert += Integer.parseInt(vars[1]);
                    break;
            }
        }

        return hor * vert;
    }


    public static int part2(List<String> input){
        int hor = 0;
        int vert = 0;
        int aim = 0;
        for (String s : input) {
            String[] vars = s.split(" ");
            switch (vars[0]){
                case "forward":
                    hor += Integer.parseInt(vars[1]);
                    vert += aim*Integer.parseInt(vars[1]);
                    break;
                case "up":
                    aim -= Integer.parseInt(vars[1]);
                    break;
                case "down":
                    aim += Integer.parseInt(vars[1]);
                    break;
            }
        }

        return hor * vert;
    }

    public static List<String> readFile(String path){
        try (
                BufferedReader in = Files.newBufferedReader(Paths.get(path))
        ) {
            List<String> erg = new LinkedList<>();
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
