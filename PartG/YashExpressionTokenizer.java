import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class YashExpressionTokenizer {

    private String expr;
    private int start;
    private int end;

    private HashMap<String, String[]> variables;

    ArrayList<String> symbols;
    ArrayList<String> types;

    public YashExpressionTokenizer(String expression) {
        expr = expression;
        symbols = new ArrayList<>(Arrays.asList("+","-","/","*","%","[","]"));
        types = new ArrayList<>();
        start = 0;
        end = 0;
        nextToken();
    }

    /*
    This will return the current nextToken in the expression
     */
    public String peekToken() {
       // System.out.println(expr.substring(start, end));
        if (start >= expr.length() || expr.substring(start, end).equals("")) {
            return null;
        } else {
            return expr.substring(start, end);
        }
    }

    public void addType(String s) {
        types.add(s);
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    /*
    This will return what the 'current' nextToken is and update the indexes to the 'next' nextToken.
     */
    public String nextToken() {
        String next = peekToken();
        start = end;
        if (start >= expr.length()) {
        //    System.out.println("if out of range");
            return next;
        }
        if (Character.isDigit(expr.charAt(start))) {
         //   System.out.println("if isDigit");
            end = start + 1;
            while (end < expr.length() && Character.isDigit(expr.charAt(end))) {
                end++;
            }
        } else {
            end = start + 1;
        }

     //   System.out.println(next);
        return next;
    }
}
