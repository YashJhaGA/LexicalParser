import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class YashLexical {

    public static YashSyntax syntax;

    public static HashMap<String,Integer> lexemeConverter;

    public static ArrayList<String> listOfVariables;
    public static ArrayList<Integer> listOfTokens;
    public static Scanner scan;


    public static void main(String[] args) throws FileNotFoundException {


        File file = null;
        try {
            file = new File(args[0]);
        } catch(Exception e) {
            System.out.println("Could not locate file. Check the scope.");
        }

        if(file == null) {
            System.out.println("Error: File not found");
            System.exit(1);
        }
         lexemeConverter = new HashMap<>();
         fillConverter();
         listOfTokens = new ArrayList<>();
         listOfVariables = new ArrayList<>();
         fillTokens(file);
   //      System.out.println(listOfTokens);

        scan = new Scanner(file);
        syntax = new YashSyntax();
        String begin = "";
        if(scan.hasNextLine()) {
            begin = scan.nextLine();
            if(!begin.equals("START")) {
                System.out.println("Program MUST begin with 'START'");
                System.exit(1);
            }
        } else {
            System.out.println("Empty Program");
            System.exit(1);
        }

        while (scan.hasNextLine()) {
            String data = scan.nextLine();
       //     System.out.println("Printing Line: "+data);
            lex(data);
        }
        scan.close();
    }

    /*
    This is a replica of the YashLexer.java file. We are doing the same exact thing except this is all in one function rather than spread out.
     */

    private static void fillTokens(File f) throws FileNotFoundException {
        Scanner scanner2 = new Scanner(f);
        while(scanner2.hasNextLine()) {
            //   System.out.println(listOfTokens);
            String line = scanner2.nextLine();
            String[] lexemes = line.split(" ");
            for(int i = 0; i<lexemes.length; i++) {
                String currentLexeme = lexemes[i];
                //     System.out.println(currentLexeme);
                if(lexemeConverter.containsKey(currentLexeme)) {
                    listOfTokens.add(lexemeConverter.get(currentLexeme));
                } else if(Character.isDigit(currentLexeme.charAt(0))) {
                    for(int index = 0; index<currentLexeme.length(); index++) {
                        if(!Character.isDigit(currentLexeme.charAt(index))) {
                            System.out.println(currentLexeme+ " is a invalid token");
                            System.exit(1);
                        }
                    }
                    listOfTokens.add(lexemeConverter.get("num"));
                } else if(Character.isLetter(currentLexeme.charAt(0)) || currentLexeme.charAt(0) == '_') {
                    for(int j = 0; j<currentLexeme.length(); j++) {
                        if(!Character.isLetter(currentLexeme.charAt(j)) && currentLexeme.charAt(0) != '_') {
                            System.out.println("Invalid variable naming convention");
                            System.exit(1);
                        }
                    }
					if(listOfTokens.isEmpty()) {
						System.out.println(currentLexeme+" is not a valid token");
						System.exit(1);
					}
                    int previousIndexValue = listOfTokens.get(listOfTokens.size()-1);;
                    if(previousIndexValue >= 30 && previousIndexValue <=33) {
                        if (currentLexeme.length() >= 6 && currentLexeme.length() <= 8) {
                            listOfVariables.add(currentLexeme);
                            listOfTokens.add(lexemeConverter.get("varName"));
                        } else {
                            System.out.println("Variable length must be 6-8 characters long");
                            System.exit(1);
                        }
                    } else if(listOfVariables.contains(currentLexeme)) {
                        listOfTokens.add(lexemeConverter.get("varName"));
                    } else {
                        System.out.println(currentLexeme+ " is not declared!");
                        System.exit(1);
                    }
                } else  {
                    System.out.println(currentLexeme+ " is not a valid token in this language!");
                    System.exit(1);
                }
            }
        }
        scanner2.close();
    }

    /*
    This prints out each token in the file. So, just like YashLexer.java or Part E.
     */

    private static void printTokens() {
            System.out.println("Program Tokens:");
            System.out.println(listOfTokens);
        }


        /*
        This fills our converter map for lexemes to tokens.
         */
    private static void fillConverter() {
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
        lexemeConverter.put("",-1);
    }

    /*
    This is where we start our parsing.
    It splits the line into several lexemes and depending on the initial token, it chooses a path to go.
    Such as, if the first token is the "flower" token, it will start going to the flower loop. etc.
    It has the cases of flower, water, declaration, cond, initializing and Ending the program.
     */
    public static void lex(String data) {
        String[] tokens = data.split(" ");
        for (int i = 0; i < tokens.length; i++) {
      //      System.out.print(tokens[i] + " ");
        }
      //  System.out.println();

        String initialToken = tokens[0];

        if (initialToken.equals("flower")) {

            flower(tokens);
        } else if(initialToken.equals("water")) {

            water(tokens);
        }else if (initialToken.contains("int_")) {
            syntax.declare(tokens);
        } else if (initialToken.equals("cond")) {

            conditional(tokens);
        } else if (initialToken.equals("END")) {

            end();
        } else {
            syntax.initialize(tokens);
        }

    }

    /*
    This is code for performing the water loop in our code.
    The water loop has 4 steps.
    Step 1: first token is water.
    Step 2: perform the comparison in the boolean expression. If true, proceed to step 3. Otherwise quit the loop.
    Step 3: collect all the lines inside of the loop.
    Step 4: Stop collecting lines once we see "retaw", which is the ending token. Then repeat the cycle from Step 2.

     */
    private static void water(String[] tokens) {
        if (tokens.length != 4) {
            System.out.println("Error: Invalid Water Loop Syntax. Correct Syntax is water var operator num");
            System.exit(1);
        }

        if(!syntax.variables.containsKey(tokens[1])) {
            System.out.println("Error: Variable doesn't exist");
            System.exit(1);
        }
        boolean isValidOp = syntax.cond.isValidOperator(tokens[2]);
        if(!isValidOp) {
            System.out.println("Error: Not a valid operator used in the Water Loop Syntax.");
            System.exit(1);
        }

        boolean comparison = false;
        try {
         //   System.out.println(tokens[1] + tokens[2] +tokens[3]);
            comparison = syntax.getBool(syntax.variables.get(tokens[1])[1], tokens[2],tokens[3]);



        } catch(Exception e) {
            System.out.println("Error in Water Loop Syntax. Example of proper loop is 'water i < 10'");
            System.exit(1);
        }

        ArrayList<String> waterLines = new ArrayList<>();
        int index = 0;
        String next = scan.nextLine();
        while (!next.equals("retaw")) {
            waterLines.add(index, next);
            next = scan.nextLine();
            index++;
        }

        if(!comparison) {
            return;
        }
        while(comparison) {
            for(int i = 0; i<waterLines.size(); i++) {
                lex(waterLines.get(i));
            }
            comparison = syntax.getBool(syntax.variables.get(tokens[1])[1], tokens[2], tokens[3]);
        }

    }

    /*
    This is the code for the flower loop in our code.
    The flower loop has
    Step 1: initialToken is keyword "flower".
    Step 2: The next 3 tokens are numbers.(startingValue, finishingValue, and incrementer/decrementer).
    Step 3: Collect all the lines into a list until we see the "rewolf" token.
    Step 4: Using those 3 token numbers, execute the lines collected in Step 3 a fixed amount of times.
     */
    private static void flower(String[] tokens) {
      //  System.out.println("Flower Loop");
        if (tokens.length != 4) {
            System.out.println("Error: Invalid Flower Loop Syntax. Correct Syntax is flower startNum endNum step");
            System.exit(1);
        }
        ArrayList<String> flowerLines = new ArrayList<>();
        int index = 0;
        String next = scan.nextLine();
        while (!next.equals("rewolf")) {
            flowerLines.add(index, next);
            next = scan.nextLine();
            index++;
        }
        int startLoop = 0, finishLoop = 0, increment = 0;
     //   System.out.println(flowerLines);
        try {
            startLoop = Integer.parseInt(tokens[1]);
            finishLoop = Integer.parseInt(tokens[2]);
            increment = Integer.parseInt(tokens[3]);
        } catch (Exception e) {
            System.out.println("Error: Flower Loop requires 3 numbers- start, finish, increment- Ex: flower 0 10 1");
        }





        if(startLoop == finishLoop) return;

        if (startLoop < finishLoop) {
       //     System.out.println("Increment Loop");
            for (int i = startLoop; i < finishLoop; i += increment) {
                for (int j = 0; j < flowerLines.size(); j++) {
                //    System.out.println(flowerLines.get(j));
                    lex(flowerLines.get(j));
                }
            }

        } else {
            //System.out.println("Decrement Loop");
            for (int i = startLoop; i > finishLoop; i -= increment) {
                for (int j = 0; j < flowerLines.size(); j++) {
                    lex(flowerLines.get(j));
                }
            }

        }
    }

    /*
    This is the code for our 'or' function, If you remember, if-else is cond-or in this language. As you can see above in lex, you can't actually call
    this orFunc from the lex() function. You can only call 'orFunc' from within the conditional() function because you can only have or if you have cond.
    Worth noting, we will read and execute each line until we reach "ro" which is the matching reverse pair of "or".
     */
    private static void orFunc() {
        String next = scan.nextLine();
        while(!next.equals("ro")) {
            lex(next);
            next = scan.nextLine();
        }



    }

    /*
    This is the conditional function. Here we have the equivalent of an if-statement. You can do something like cond 10 < 15 x++ donc
    In that example, we would increment x(if it is declared and initialized).
    With a cond we also need a donc. We read every line inbetween and execute them.
     */
    public static void conditional(String[] tokens) {
        boolean condResult = syntax.cmp(tokens);

      //  System.out.println("Bool Result: "+condResult);
        if(condResult) {
            String next = scan.nextLine();
            while (!next.equals("donc") && scan.hasNextLine()) {
                lex(next);
                next = scan.nextLine();
            }
        } else {
            String next = scan.nextLine();
            while (!next.equals("donc") && scan.hasNextLine()) {
                next = scan.nextLine();
            }
        }

         String next = scan.nextLine();
        if(!condResult && next.equals("or")) {

            orFunc();
        } else {
            lex(next);
        }
     }

    /*
    Function that ends the program with the token "END" is read.
     */
    public static void end() {
       // System.out.println("END");
        printTokens();
        syntax.printKeyVal();
         System.exit(0);
    }
}
