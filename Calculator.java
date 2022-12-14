import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Calculator {
    public static void main(String[] args) throws Exception {
        System.out.println("Введите пример: ");
        Scanner scanner = new Scanner(System.in);
        String x = scanner.nextLine();
        System.out.println("Ответ: " + calc(x));
        scanner.close();
    }
    public static String calc(String input) throws Exception {
        Pattern patternRome = Pattern.compile("[I-X]");
        Pattern patternArab = Pattern.compile("[0-9]");
        Matcher matcherRome = patternRome.matcher(input);
        Matcher matcherArab = patternArab.matcher(input);
        input = input.replaceAll("\\s", "").toUpperCase();

        if(matcherRome.find() && matcherArab.find()) {
            throw new Exception();
        }

        if (matcherRome.find()) {
            String operators = input.replaceAll("[I-X]+", "");
            String operands[]  = input.split("[+/*-]");

            if(operands.length > 2) {
                throw new Exception();
            }

            int [] x = new int[] {romanToArabic(operands[0]), romanToArabic(operands[1])};
            return convertToRoma(mathematicalAction(operators,x));
        } else {
            String result = "";
            String operators = input.replaceAll("[0-9]+", "");
            String operands[]  = input.split("[+/*-]");
            int [] x = new int[] {Integer.parseInt(operands[0]), Integer.parseInt(operands[1])};

            if(x[0] < 1 | x[0] > 10 |
                    x[1] < 1 | x[1] > 10) {
                throw new Exception();
            }

            if(operands.length > 2) {
                throw new Exception();
            }

            result += mathematicalAction(operators, x);
            return result;
        }
    }
    static int romanToArabic(String input) throws Exception {
        String romNumeral = input.toUpperCase();
        int result = 0;

        List<romanNumeral> romNumerals = romanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romNumeral.length() > 0) && (i < romNumerals.size())) {
            romanNumeral symbol = romNumerals.get(i);
            if (romNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romNumeral = romNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romNumeral.length() > 0) {
            throw new Exception();
        }

        return result;
    }
    enum romanNumeral {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100),
        CD(400), D(500), CM(900), M(1000);

        private int value;

        romanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static List<romanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((romanNumeral e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }
    static String convertToRoma(int number) throws Exception {
        if ((number <= 0) || (number > 4000)) {
            throw new Exception();
        }
        List<romanNumeral> romanNumerals = romanNumeral.getReverseSortedValues();
        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            romanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }
        return sb.toString();
    }
    static int mathematicalAction(String operators, int [] x) {
        int result = 0;
        switch (operators) {
            case "+": result = x[0] + x[1];
                break;
            case "-": result = x[0] - x[1];
                break;
            case "*": result = x[0] * x[1];
                break;
            case "/": result = x[0] / x[1];
                break;
        }
        return result;
    }
}
