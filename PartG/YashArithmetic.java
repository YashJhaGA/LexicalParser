import java.util.ArrayList;
import java.util.HashMap;

public class YashArithmetic {
    private YashExpressionTokenizer tokenizer;


    private String[] symbols = {"+", "-", "%", "*", "%"};


    public YashArithmetic(String expression) {
        tokenizer = new YashExpressionTokenizer(expression);
    }

    /*
    Going back to our grammar, this is the very top, meaning the lowest precedence.
    So here, we have + and -.
     */
    public int getExpressionValue() {
        int value = getTermValue();
        boolean done = false;
        while (!done) {
            String next = tokenizer.peekToken();
            if ("+".equals(next) || "-".equals(next)) {
                tokenizer.nextToken();
                int value2 = getTermValue();
                if ("+".equals(next)) {
                    value = value + value2;
                } else {
                    value = value - value2;
                }
            } else {
                done = true;
            }
        }
        return value;
    }

    /*
    Next in our grammar, we have multiplication, division, and modulo at our 2nd step as the 2nd level of precedence.
     */
    public int getTermValue() {
        int value = getFactorValue();
        boolean done = false;
        while (!done) {
            String next = tokenizer.peekToken();
            if ("*".equals(next) || "/".equals(next) || "%".equals(next)) {
                tokenizer.nextToken();
                int value2 = getFactorValue();
                if ("*".equals(next)) {
                    value = value * value2;
                } else if ("%".equals(next)) {
                    value = value % value2;
                } else {
                    value = value / value2;
                }
            } else {
                done = true;
            }
        }
        return value;

    }

    /*
    Lastly, this is where the brackets([]) are highest precedence being the furthest down and integers are collected.
     */
    public int getFactorValue() {
        int value;
   //     System.out.println("Inside of getFactor");
        String next = tokenizer.peekToken();


    /*    if (!"[".equals(next) && !Character.isDigit(next.charAt(0))) {
            try {
                String getValue = variables.get(next)[1];
                value = Integer.parseInt(getValue);
                return value;
            } catch (Exception e) {
                System.out.println("Error: Variable doesn't exist or variable is null!");
                System.exit(1);
            }
        } */

            if ("[".equals(next)) {
                tokenizer.nextToken();
                value = getExpressionValue();
                tokenizer.nextToken();

            } else {
          //      System.out.println("Here!");
                value = Integer.parseInt(tokenizer.nextToken());
            }
            return value;
        }


        public ArrayList<String> getTypes() {
            return tokenizer.getTypes();
        }
    }

