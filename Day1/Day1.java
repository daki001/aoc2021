package Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Day1 {


    public static void main(String[] args) {

        List<String> input = readFile("src/Day1/input");
        part1(input);
        part2(input);
    }

    public static void part2(List<String> input){
        List<String> newList = new LinkedList<>();
        for (int i = 0; i < input.size(); i++) {
            try{
                int sum = 0;
                for (int j = 0; j < 3; j++) {
                    sum += Integer.parseInt(input.get(i + j));
                }
                newList.add(Integer.toString(sum));
            }catch (IndexOutOfBoundsException e){

            }
        }

        part1(newList);
    }

    public static void part1(List<String> input){
        int prev = -1;
        int erg = 0;
        for (String s: input) {
            int num = Integer.parseInt(s);
            if(num > prev && prev != -1){
                erg++;
            }
            prev = num;
        }

        System.out.println(erg);
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
