import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<Character, Integer> numeralsMap = new HashMap<Character, Integer>();

    static {
        numeralsMap.put('I', 1);
        numeralsMap.put('V', 5);
        numeralsMap.put('X', 10);
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение через пробел (оба операнда от 1 до 10): ");
        String input = scanner.nextLine().trim();
        System.out.println(calc(input));
    }

    public static String calc(String input) throws Exception {

        String[] elements = input.split(" ");
        if (elements.length != 3) {
            throw new IllegalArgumentException("Ошибка! Выражение не соответветсвует условию");
        }

        String firstOperand = elements[0];
        char operator = elements[1].charAt(0);
        String secondOperand = elements[2];

        if ((!isRoman(firstOperand) && !isArab(firstOperand)) || (!isRoman(secondOperand) && !isArab(secondOperand))) {
            throw new ArithmeticException("Ошибка! Числа должны быть римские или арабскими в диапазоне 1-10");
        }
        if (isRoman (firstOperand) && !checkCorrectRoman(firstOperand) || isRoman(secondOperand) && !checkCorrectRoman(secondOperand)){
            throw new Exception("Неправильно введена римская цифра");
        }

        int num1 = isRoman(firstOperand)? toArab(firstOperand) : Integer.parseInt(firstOperand);
        int num2 = isRoman(secondOperand) ? toArab(secondOperand): Integer.parseInt(secondOperand);

        if ((!isRoman(firstOperand) && isRoman(secondOperand)) || (isRoman(firstOperand) && !isRoman(secondOperand))) {
            throw new IllegalArgumentException("Ошибка! Оба числа должны быть либо арабскими, либо римскими");
        }

        int result = performOperation(num1, num2, String.valueOf(operator));
        if (!(result > 0) && !(isArab(String.valueOf(result)))) {
            throw new Exception("Результат отрицательный");
        }
        String resultStr = isRoman(firstOperand) || isRoman(secondOperand) ? toRoman(result) : String.valueOf(result);
        return resultStr;

    }

    private static int toArab(String str) {
        int result = 0, Value = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            int arabEquivalent = numeralsMap.get(str.charAt(i));
            result += (arabEquivalent < Value) ? -arabEquivalent : arabEquivalent;
            Value = arabEquivalent;
        }
        return result;
    }

    private static String toRoman(int number)  {
        String[] symbols = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder result = new StringBuilder();
        for (int i = 0; number > 0; i++) {
            while (number >= values[i]) {
                number -= values[i];
                result.append(symbols[i]);
            }
        }
        return result.toString();
    }

    private static boolean isRoman(String str) {
        if (str.isEmpty()) return false;
        for (char c : str.toCharArray()) {
            if (!numeralsMap.containsKey(c)) {
                return false;
            }
        }
        int value = toArab(str);
        return value >= 1 && value <= 10;
    }

    private static boolean isArab(String str) {
        try {
            int value = Integer.parseInt(str);
            return value >= 1 && value <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int performOperation(int a, int b, String oper) {
        switch (oper) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b;
            default:
                throw new IllegalArgumentException("Неизвестная операция: " + oper);
        }
    }

    public static Boolean checkCorrectRoman(String romanNum) {
        String[] Roman = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        for (int i = 0; i < Roman.length; i++) {
            if (romanNum.equals(Roman[i])) {
                return true;
            }
        } return false;
    }
}

