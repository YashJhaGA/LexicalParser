import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class YashSyntax {

    // HashMap to store variables. The key is the variable name. The value is a 2 element array. 1st index is variable type and 2nd value is variable value.
    public HashMap<String, String[]> variables;

    public YashCondition cond;

    public ArrayList<String> validTokens = new ArrayList<>(Arrays.asList("+", "-", "*", "/", "%", "[", "]"));
    public YashSyntax() {
        variables = new HashMap<>();
        cond = new YashCondition();
    }

    /*
    What this function does is that it is used for declaring variables in the language.
    I have a HashMap created for this class where we have it set so that they key is String and the value is a String array.
    One might wonder why I didn't make it <String, Integer> because that's what a key value pair is for an integer.
    The reason for this is because I need to keep track of what type of integer it is(1 byte, 2, 4 , or 8).
    So what happens is, I set the key to be the variable name.
    Say.. our line is int_8 x
    Our key will be 'x'.
    As for the array, I create an array of 2 elements. I put the type in the first slot, so the array is [int_8, null].
    That 2nd element of the array is where I will store the value when initialization occurs.
     */
    public void declare(String[] tokens) {
      //  System.out.println("Declare");
        validateVarName(tokens[1]);
        insertType(tokens[0]);
        String[] typeValue = new String[2];
        String type = tokens[0];
        String varName = tokens[1];
        typeValue[0] = type;

        variables.put(varName, typeValue);
    }

    /*
    Validating declaration type. In our language, the only types are int_1, int_2, int_4, and int_8.
    Anything else is not our problem and terminate the program.
     */
    private void insertType(String token) {
        if(token.equals("int_1")) {

        } else if(token.equals("int_2")) {

        } else if(token.equals("int_4")) {

        } else if(token.equals("int_8")) {

        }   else {
            System.out.println("Not a valid type. The 4 types are int_1, int_2, int_4, and int_8");
            System.exit(1);
        }
    }

    /*
    Here we validate the variable name. Which is the following 2 criteria
    1. Name must be of 6 to 8 characters.
    2. Name can only contain letters and underscores.
     */
    private void validateVarName(String token) {

        if(token.length() < 6 || token.length() > 8) {
            System.out.println("Variable name is too short/long. Must be 6-8 characters long");
            System.exit(1);
        }

        for(int i = 0; i<token.length(); i++) {
            if(!Character.isLetter(token.charAt(i)) && token.charAt(i) != '_') {
                System.out.println("Invalid Character in Variable Name. Can only be Letters and Underscore");
                System.exit(1);
            }
        }

    }

    /*
    This is the comparison function for the cond statement and its boolean expression.
    Firstly, it should already be detected, but to make sure, 1st token must be "cond".
    Next, we use the 3 tokens for our boolean expression.
    The 2nd and 4th token are 'values' and the 3rd token is an operator.
    Over in our YashCondition class, we have every comparison readily available for the languages tokens.
     */
    public boolean cmp(String[] tokens) {

      //  System.out.println("Condition");
        if(!tokens[0].equals("cond")) {
            System.exit(1);
        }
        String first = tokens[1];
        if(!Character.isDigit(tokens[1].charAt(0))) {
            try {
                 first = variables.get(tokens[1])[1];

            } catch (Exception e) {
                System.out.println("Condition statement must have variable or integer afterwards");
                System.exit(1);
            }
        } else {

        }
        String operator = tokens[2];
        boolean isOper = cond.isValidOperator(operator);
        if(!isOper) {
            System.out.println("Not a valid operator!");
            System.exit(1);
        }


        String second = tokens[3];
        if(!Character.isDigit(tokens[3].charAt(0))) {
            try {
                 second = variables.get(tokens[3])[1];

            } catch (Exception e) {
                System.out.println("Condition statement must have variable or integer afterwards");
                System.exit(1);
            }
        } else {

        }
        boolean makeComp = getBool(first,operator,second);
       // System.out.println(makeComp);
        return makeComp;
    }

    /*
    This is an extension of the cmp function where we direct the operator to the correct function and return the boolean.
    For example, operator.equals("<") will take you to a function that does "returns first < second";
     */
    public boolean getBool(String first, String operator, String second) {
        if(operator.equals("<")) {
            return cond.lessThan(Integer.parseInt(first), Integer.parseInt(second));
        } else if (operator.equals(">")) {
            return cond.greaterThan(Integer.parseInt(first), Integer.parseInt(second));
        } else if (operator.equals("<=")) {
            return cond.lessThanOrEqual(Integer.parseInt(first), Integer.parseInt(second));
        } else if (operator.equals(">=")) {
            return cond.greaterThanOrEqual(Integer.parseInt(first), Integer.parseInt(second));
        } else if (operator.equals("==")) {
            return cond.isEqual(Integer.parseInt(first), Integer.parseInt(second));
        } else {
            return cond.isNotEqual(Integer.parseInt(first), Integer.parseInt(second));
        }
    }

    /*
    I was taught how to write a PEMDAS expression in CS1301. However that used a connected string with no spaces.
    Since I wanted to re-use that code and convert it to BODMAS, I shrunk my tokens into one connected String rather than spaced out tokens.
    I have also included modulo into the expression.
     */
    public String shrinkLine(String [] tokens) {
        String s = "";
        for(int i = 2; i < tokens.length; i++) {
            if (Character.isDigit(tokens[i].charAt(0)) || validTokens.contains(tokens[i])) {
                s = s + tokens[i];
            } else if (variables.containsKey(tokens[i])) {
                s = s + variables.get(tokens[i])[1];
            } else {
                System.out.println("Invalid Token");
                System.exit(1);
            }
        }
        return s;
    }

    /*
    Mathmatical Operations between 2 different types of values is not allowed.
    Here we collect the types of all tokens in an expression(if they exist)..
     */
    public ArrayList<String> getTypes(String [] tokens) {
        ArrayList<String> types = new ArrayList<>();
        for(int i = 2; i < tokens.length; i++) {
             if (variables.containsKey(tokens[i])) {
                types.add(variables.get(tokens[i])[0]);
            }
        }
        return types;
    }
    /*
    Here before we even calculate the expression, we check for a type mismatch.
     */
    public boolean typeMatch(String varType, ArrayList<String> types) {
  //      System.out.println("Current Type: "+varType);
        for(String s: types) {
    //        System.out.println("Expression Types: "+s);
            if(!varType.equals(s)) {
                return false;
            }
        }
        return true;
    }

    /*
    Here is where we initialize a variable.
    The first token must be a variable that is already declared in our program.
    For a declared variable, there are 2 operations you can do. You can do assignment(=) or you can perform
    increment(++ or +=) or (-- or -=). The rest of the function is dedicated to =. The preceding functions are used to
    determine if we should even evaluate the expression. If there is a type mismatch, we don't even perform the arithmetic.
     */
    public void initialize(String[] tokens) {

        String var = tokens[0];
        if(!variables.containsKey(var)) {
            System.out.println("Error: Cannot perform initialization on non-declared variable");
            System.exit(1);
        }


        char equalOp = tokens[1].charAt(0);
        if(equalOp != '=') {
            crement(tokens);
            return;
        } else {

        }

        String thisVarType = variables.get(tokens[0])[0];
        ArrayList<String> otherTypes = getTypes(tokens);

        boolean passTypes = typeMatch(thisVarType,otherTypes);
        if(!passTypes) {
            System.out.println("Error: Type Mismatch between variables");
            System.exit(1);
        }

        String s = shrinkLine(tokens);

     //   System.out.println(s);


        YashArithmetic arithmetic = new YashArithmetic(s);
        int value = arithmetic.getExpressionValue();

        int adjustedValue = valueType(value,thisVarType);
        String[] arr = {thisVarType,Integer.toString(adjustedValue)};
        variables.put(var,arr);

    }

    /*
    Here we adjust the values to their byte size.
    1 byte is 2^8 - 1 which is 255, so we % 255 if the type is of int_1.
    2 bytes is 2^16 - 1, which is ~65000. We perform %65535 on it.
    For 4 and 8 bytes, the values are so large that it is not worth coding for the level of this class.
     */
    private int valueType(int value, String s) {
        if(s.equals("int_8")) {
            return value;
        } else if(s.equals("int_4")) {
            return value;
        } else if(s.equals("int_2")) {
            return value % 65535;
        } else  {
            return value % 255;
        }
    }

    /*
    As referenced in the comments of initialization, there were 2 paths. Increment/Decrement or Assignment.
    Here is the First Step to Increment/Decrement.
    We check if the value of the variable is null.
    This is for the case where say, someone does
    int_8 thisNum
    thisNum ++

    This is an invalid syntax cause thisNum was never initialized, so you can't increment null.
    If it passes the checkNull() test, then it gets sent to the increment or decrement functions respectively.
     */
    private void crement(String[] tokens) {
        if(tokens[1].equals("++") || tokens[1].equals("+=")) {
            checkIfNull(tokens);
            incrementVar(tokens);
        } else if(tokens[1].equals("--") || tokens[1].equals("-=")) {
            checkIfNull(tokens);
            decrementVar(tokens);
        } else {
            System.out.println("Invalid Assignment Operator");
            return;
        }
    }

    /*
    This is used to check if a variable is null. This is mainly used for determining if increment/decrementing is possible
    for a variable. I noted above for the comment of the previous function where it does not make sense.
     */
    private void checkIfNull(String[] tokens) {
        String[] arr = variables.get(tokens[0]);
        if(arr[1] == null) {
            System.out.println("Cannot perform increment/decrement on null value");
            System.exit(1);
        }
    }

    /*
    Function for decrementing a variable.
    If the token is just "--", we just subtract 1.
    Otherwise, if it is "-=", we evaluate the expression of everything after the symbol and subtract it from the value.
    We also use a previous function to make sure the value is still in the capacity for its type.
     */
    private void decrementVar(String[] tokens) {
        String[] arr = variables.get(tokens[0]);
        if(tokens[1].equals("--")) {

            int value = Integer.parseInt(arr[1]);
            value--;
            arr[1] = Integer.toString(value);
        } else {
            boolean isPass = typeMatch(arr[0],getTypes(tokens));
            if(!isPass) {
                System.out.println("Type Mismatch");
                System.exit(1);
            }
            String s = shrinkLine(tokens);
            YashArithmetic arithmetic = new YashArithmetic(s);

            int decr = arithmetic.getExpressionValue();
            int value = Integer.parseInt(arr[1]);
            value -= decr;
            int adjustedValue = valueType(value, arr[0]);
            arr[1] = Integer.toString(adjustedValue);
        }
    }

    /*
    Function for incrementing a variable.
    If the token is just "++", we just add 1.
    Otherwise, if it is "+=", we evaluate the expression of everything after the symbol and add it from the value.
    We also use a previous function to make sure the value is still in the capacity for its type.
     */
    private void incrementVar(String[] tokens) {
        String[] arr = variables.get(tokens[0]);
        if(tokens[1].equals("++")) {
            int value = Integer.parseInt(arr[1]);

            value++;
            arr[1] = Integer.toString(value);
        } else {
            boolean isPass = typeMatch(arr[0],getTypes(tokens));
            if(!isPass) {
                System.out.println("Type Mismatch");
                System.exit(1);
            }
            String s = shrinkLine(tokens);
            YashArithmetic arithmetic = new YashArithmetic(s);

            int incr = arithmetic.getExpressionValue();
            int value = Integer.parseInt(arr[1]);
            value += incr;
            int adjustedValue = valueType(value, arr[0]);
            arr[1] = Integer.toString(adjustedValue);
        }
    }

    /*
    Getter for the variables map.
     */
    public HashMap<String, String[]> getVar() {
        return variables;
    }

    /*
    Function that prints all the variables.
     */
    public void printKeyVal() {
        System.out.println("Program Variables: ");
        System.out.println("varName  varType  value");
        for(String key: variables.keySet()) {
            System.out.print(key);
            System.out.print("  ");
            System.out.print(variables.get(key)[0]);
            System.out.print("    ");
            System.out.print(variables.get(key)[1]);
            System.out.println();
        }
    }
}
