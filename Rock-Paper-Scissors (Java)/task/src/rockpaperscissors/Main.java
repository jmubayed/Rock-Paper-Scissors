package rockpaperscissors;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final String filePath = "C:\\Users\\mubay\\Desktop\\marketplace\\Rock-Paper-Scissors (Java)\\Rock-Paper-Scissors (Java)\\task\\src\\rockpaperscissors\\rating.txt";

    public static void main(String[] args) throws Exception {
        // write your code here
        System.out.print("Enter your name:");
        Scanner nameInput = new Scanner(System.in);
        String name = nameInput.nextLine();
        System.out.printf("Hello, %s\n", name);
        updateFile(name, 350);
        Scanner input = new Scanner(System.in);
        String inputOptions = input.nextLine();
        System.out.print("Okay, let's start\n");
        int point = FileReader(name);
        do{
            Scanner inputOption = new Scanner(System.in);
            String userOption = inputOption.nextLine();
            List<String> myList;
            if(inputOptions.isBlank()){
                myList = new algorithm().listOfNormalOption();
            }else{
                myList = Arrays.stream(inputOptions.split(",")).collect(Collectors.toList());
            }
            String randomElement = myList.get(new Random().nextInt(myList.size()));
            if(userOption.equals(randomElement)){
                System.out.printf("There is a draw (%s)",userOption);
                point +=  50;
            }else if(userOption.equals("!exit") || userOption.equals("Bye!")){
                break;
            }else if(userOption.equals("!rating") ){
                System.out.printf("Your rating: %d", point);

            }
            else if(userOption.equals("paper") && randomElement.equals("rock") ||
                    userOption.equals("scissors") && randomElement.equals("paper") ||
                    userOption.equals("rock") && randomElement.equals("scissors") ||
                    !inputOptions.isBlank() && new algorithm(inputOptions, userOption).beatedListOption().contains(randomElement)
            ){
                System.out.printf("Well done. The computer chose %s and failed", randomElement);
                point += 100;
            }
            else if(!myList.contains(userOption)){
                System.out.print("Invalid input");
            }
            else{
                System.out.printf("Sorry, but the computer chose %s", randomElement);
            }
            System.out.print("\n");
            updateFile(name, point);
        }while(true);
    }

    private static int FileReader(String currentName){
       List<String> filterLines = null;
        try (Stream<String> lines = Files.lines(Path.of(filePath))) {
            filterLines = lines.filter(name -> name.contains(currentName)).collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Failed to read file: " + e.getMessage());
        }
        return (filterLines != null && filterLines.size() > 0) ?
                Integer.parseInt(filterLines.get(0).split(" ")[1]) : 0;
    }

    public static void updateFile(String name, int score) {
        String newEntry = name + " " + score;
        boolean found = false;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder fileContent = new StringBuilder();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2 && parts[0].equals(name)) {
                    // Update existing entry
                    fileContent.append(newEntry).append(System.lineSeparator());
                    found = true;
                } else {
                    fileContent.append(line).append(System.lineSeparator());
                }
            }
            if (!found) {
                // Append new entry if not found
                fileContent.append(newEntry).append(System.lineSeparator());
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                bw.write(fileContent.toString());
            } catch (IOException e) {
                System.err.println("Failed to write to file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Failed to read file: " + e.getMessage());
        }
    }

}
