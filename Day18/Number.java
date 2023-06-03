package Day18;

public class Number {
    int leftValue;
    int rightValue;
    boolean leftInt;
    boolean rightInt;
    Number leftNumber;
    Number rightNumber;

    public Number(){
        leftInt = false;
        rightInt = false;
    }

    public long calculateMagnitude(){
        long result = 0;
        if(leftInt){
            result += 3L * leftValue;
        }else{
            result += 3L * leftNumber.calculateMagnitude();
        }

        if(rightInt){
            result += 2L * rightValue;
        }else{
            result += 2L * rightNumber.calculateMagnitude();
        }

        return result;
    }
    public Number(int l, int r){
        leftValue = l;
        rightValue = r;
        leftInt = true;
        rightInt = true;
    }

    public Number(Number l, Number r){
        leftNumber = l;
        rightNumber = r;
        leftInt = false;
        rightInt = false;
    }

    public String toString(){
        String result = "[";
        if(leftInt){
            result += leftValue;
        }else{
            result += leftNumber.toString();
        }

        result += ",";

        if(rightInt){
            result += rightValue;
        }else{
            result += rightNumber.toString();
        }

        return result + "]";
    }



}
