package rockpaperscissors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class algorithm {

    private final List<String> myList = Arrays.asList("paper", "scissors", "rock");

    private HashMap<String, List<String>> result = new HashMap<>();

    private String listOfPlayOptions;

    private String inputOption;

    public algorithm(){

    }
    public algorithm(String listOfPlayOptions, String inputOption) throws Exception {
        this.listOfPlayOptions = listOfPlayOptions;
        this.inputOption = inputOption;
        this.result = determineDefeatBeated();
    }
    public List<String> listOfNormalOption(){
        return myList;
    }
    public List<String> defeatListOption(){
        return result.get("defeat");
    }
    public List<String> beatedListOption(){
        return result.get("beated");
    }

    private HashMap<String, List<String>> determineDefeatBeated() throws Exception {
        List<String> firstFiler = new ArrayList<>();
        List<String> inputArray = Arrays.stream(listOfPlayOptions.split(",")).collect(Collectors.toList());
        AtomicInteger count = new AtomicInteger();
        inputArray.forEach(input -> {
            if(input.equalsIgnoreCase(inputOption)){
                count.set(1);
            }
            if(count.get() == 1 && !input.equalsIgnoreCase(inputOption)){
                firstFiler.add(input);
            }
        });
        firstFiler.forEach(inputArray::remove);
        inputArray.remove(inputOption);
        firstFiler.addAll(inputArray);
        if(firstFiler.size() % 2 != 0){
            throw new Exception ("No posible to determine the half");
        }
        result.put("defeat", firstFiler.subList(0, firstFiler.size() / 2));
        result.put("beated", firstFiler.subList(firstFiler.size()/2 , firstFiler.size()));
        return result;
    }
}
