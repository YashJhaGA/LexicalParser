import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class YashLexer {

    public static HashMap<String, Integer> lexemeConverter;
    public static ArrayList<Integer> listOfTokens;

    public static ArrayList<String> variables;

    public static void main(String[] args) throws FileNotFoundException {

        // Inserting all the token key value pairs into the HashMap

        lexemeConverter = new HashMap<>();
        lexemeConverter.put("+",5);
        lexemeConverter.put("-",6);
        lexemeConverter.put("*",7);
        lexemeConverter.put("/",8);
        lexemeConverter.put("%",9);
        lexemeConverter.put("<",15);
        lexemeConverter.put(">",16);
        lexemeConverter.put("<=",17);
        lexemeConverter.put(">=",18);
        lexemeConverter.put("==",19);
        lexemeConverter.put("!=",20);
        lexemeConverter.put("=",21);
        lexemeConverter.put("[",22);
        lexemeConverter.put("]",23);
        lexemeConverter.put("++",24);
        lexemeConverter.put("+=",25);
        lexemeConverter.put("--",26);
        lexemeConverter.put("-=",27);
        lexemeConverter.put("int_1",30);
        lexemeConverter.put("int_2",31);
        lexemeConverter.put("int_4",32);
        lexemeConverter.put("int_8",33);
        lexemeConverter.put("num",34);
        lexemeConverter.put("cond",40);
        lexemeConverter.put("donc",41);
        lexemeConverter.put("or",42);
        lexemeConverter.put("ro",43);
        lexemeConverter.put("flower",44);
        lexemeConverter.put("rewolf",45);
        lexemeConverter.put("water",46);
        lexemeConverter.put("retaw",47);
        lexemeConverter.put("varName",48);
        lexemeConverter.put("START",0);
        lexemeConverter.put("END",99);

        // Initializing the listOfTokens(what the program returns if no errors) and variables(to store the lexeme if declared).
        listOfTokens = new ArrayList<>();
        variables = new ArrayList<>();

        File f = null;
        try {
            f = new File(args[0]);
        } catch(Exception e) {
            System.out.println("Could not locate file. Check the scope.");
        }

        if(f != null) getTokens(f);
        System.out.println(listOfTokens);


    }

    /*
    Function where we traverse through every single token.
    We first go to each line and perform a split function with space(" ") as the parameter.
    After that, we navigate through each part of the line separated by space.
     */
    private static void getTokens(File f) throws FileNotFoundException {
        File file = f;
        Scanner scan = new Scanner(file);
        // while loop runs until end of file
        while(scan.hasNextLine()) {
            String line = scan.nextLine();
            // Line split
            String[] lexemes = line.split(" ");
            // Traversing through each part of the split line
            for(int i = 0; i<lexemes.length; i++) {
                String currentLexeme = lexemes[i];
                if(lexemeConverter.containsKey(currentLexeme)) {
                    listOfTokens.add(lexemeConverter.get(currentLexeme));
                } else if(Character.isDigit(currentLexeme.charAt(0))) {
                    isTokenDigit(currentLexeme);
                } else if(Character.isLetter(currentLexeme.charAt(0)) || currentLexeme.charAt(0) == '_') {
                    isTokenVariable(currentLexeme);
                } else  {
                    System.out.println(currentLexeme+ " is not a valid token in this language!");
                    System.exit(1);
                }
            }
        }
    }
    
    /*
    Function that determines if a variable is in our language. First, we detect if the first character is a letter or _ (underscore).
    We keep checking if the String only contains letters and underscores. If that passes, we check if the previous token was an declaration(int_1,int_2,int_4,or int_8)
    If it is, we check if it meets the size requirement of 6-8 characters.
    If it fails either of these checks, we throw an error.
    Also, we check if the variable was already initalized, so that declared variables don't throw an error as an unidentified token.
     */
    private static void isTokenVariable(String currentLexeme) {
        for(int j = 0; j<currentLexeme.length(); j++) {
            if(!Character.isLetter(currentLexeme.charAt(j)) && currentLexeme.charAt(0) != '_') {
                System.out.println("Invalid variable naming convention");
                System.exit(1);
            }
        }
        int previousIndexValue = listOfTokens.get(listOfTokens.size()-1);
        if(previousIndexValue >= 30 && previousIndexValue <=33) {
            if (currentLexeme.length() >= 6 && currentLexeme.length() <= 8) {
                variables.add(currentLexeme);
                listOfTokens.add(lexemeConverter.get("varName"));
            } else {
                System.out.println("Variable length must be 6-8 characters long");
                System.exit(1);
            }
        } else if(variables.contains(currentLexeme)) {
            listOfTokens.add(lexemeConverter.get("varName"));
        } else {
            System.out.println(currentLexeme+ " is not declared!");
            System.exit(1);
        }
    }
    
    /*
    Here we detect if it is a digit. We traverse through each index of the String and determine if it is a digit. 
    If a single index is not a digit, we throw an error because there is nothing in our language that starts with a digit that isn't a number.
     */
    private static void isTokenDigit(String currentLexeme) {
        for(int index = 0; index<currentLexeme.length(); index++) {
            if(!Character.isDigit(currentLexeme.charAt(index))) {
                System.out.println(currentLexeme+ " is a invalid token");
                System.exit(1);
            }
        }
        listOfTokens.add(lexemeConverter.get("num"));
    }
}