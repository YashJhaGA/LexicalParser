import java.util.ArrayList;
import java.util.Arrays;

public class YashCondition {

    private ArrayList<String> operators;
    public YashCondition() {
        operators = new ArrayList<>(Arrays.asList("<",">","<=",">=","==","!="));
    }

    /*
    This function is used to determine if an operator is a valid token.
    Above in the constructor is a list of the valid operator tokens.
     */
    public boolean isValidOperator(String token) {
        if(operators.contains(token)) {
            return true;
        }
        return false;
    }

    /*
    A bunch of conditional statements that speak for themselves
    Takes 2 numbers as parameters and returns the operation between them
     */
    public boolean lessThan(int a, int b) {
        return a < b;
    }

    public boolean greaterThan(int a, int b) {
        return a > b;
    }

    public boolean lessThanOrEqual(int a, int b) {
        return a <= b;
    }

    public boolean greaterThanOrEqual(int a, int b) {
        return a >= b;
    }

    public boolean isEqual(int a, int b) {
        return a == b;
    }

    public boolean isNotEqual(int a, int b) {
        return a != b;
    }
}
