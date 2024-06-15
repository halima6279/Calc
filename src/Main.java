import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<Character, Integer> MyMap = new HashMap<Character, Integer>();
    static {
        MyMap.put('I', 1);
        MyMap.put('V', 5);
        MyMap.put('X', 10);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение (через пробел): ");
        String input = scanner.nextLine().trim();
        System.out.println (calc(input));
    }
    public static String calc (String input){

        String [] elements = input.split(" ");
        if (elements.length != 3) {
            throw new IllegalArgumentException("Ошибка: вы неправильно ввели значения");
        }

        String firstOperand = elements[0];
        char operator = elements[1].charAt(0);
        String secondOperand = elements[2];

        if ((!isRoman(firstOperand) && !isArab(firstOperand)) || (!isRoman(secondOperand) && !isArab(secondOperand))) {
            throw new ArithmeticException("Ошибка: нужно ввести числа от 1 до 10");
        }

        int num1 = isRoman(firstOperand) ? toArab(firstOperand) : Integer.parseInt(firstOperand);
        int num2 = isRoman(secondOperand) ? toArab(secondOperand) : Integer.parseInt(secondOperand);

        if ((!isRoman(firstOperand) && isRoman(secondOperand)) || (isRoman(firstOperand) && !isRoman(secondOperand))) {
            throw new IllegalArgumentException("Ошибка: оба числа должны быть либо арабскими, либо римскими!");
        }

        int result = performOperation(num1, num2, String.valueOf(operator));
        String resultStr = isRoman(firstOperand) || isRoman(secondOperand) ? toRoman(result) : String.valueOf(result);
        return resultStr;
    }
    private static int toArab(String str) {
        int result = 0, Value = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            int arabEquivalent = MyMap.get(str.charAt(i));
            result += arabEquivalent < Value ? -arabEquivalent : arabEquivalent;
            Value = arabEquivalent;
        }
        return result;
    }

    private static String toRoman(int number) {
        String[] symbols = {"C", "L", "X", "IX", "V", "IV", "I"};
        int[] values = {100, 50, 10, 9, 5, 4, 1};
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
            if (!MyMap.containsKey(c)) {
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
}
