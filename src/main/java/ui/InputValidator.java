package ui;

import enums.InputFlag;
import enums.Size;
import exceptions.IllegalInputException;
import exceptions.OutOfBoundsException;

import java.util.Scanner;

public class InputValidator {
    private static final Scanner scanner = new Scanner(System.in);
    public static String getStringInput(String msg, InputFlag flag) throws IllegalInputException {
        System.out.print(msg);
        String str = scanner.nextLine();

        if (flag != InputFlag.CANBEBLANK) {
            if (str.isBlank()) throw new IllegalInputException("Blank input entered!");
        } else if (str.isBlank()) return "";

        if (str.equals("null")) throw new IllegalInputException("Input cannot be 'null'!");

        return str;
    }

    public static String getStringInput(String msg, InputFlag flag ,int minLen, int maxLen) throws IllegalInputException {
        String str = getStringInput(msg, flag);

        if (str.isEmpty()) return str;

        if (str.length() < minLen)
            throw new IllegalInputException("Insufficient input length! Length must be at least " + minLen);

        if (str.length() > maxLen)
            throw new IllegalInputException("Input length too high! Length must be lower than or equal to " + maxLen);

        return str;
    }

    public static int getIntInput(String msg, InputFlag flag) throws IllegalInputException {
        String str = getStringInput(msg, flag);

        if (flag == InputFlag.CANBEBLANK && str.isBlank()) return 1;

        int i;
        if (str.contains(".")) {
            throw new IllegalInputException("Input cannot be a decimal value!");
        }

        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new IllegalInputException("Non number input entered!");
        }

        return i;
    }

    public static int getIntInput(String msg, InputFlag flag, int min, int max) throws IllegalInputException, OutOfBoundsException {
        int i = getIntInput(msg, flag);

        if (i < min || i > max) {
            throw new OutOfBoundsException("Input out of bounds for range %d and %d!".formatted(min, max));
        }

        return i;
    }

    public static double getDoubleInput(String msg, InputFlag flag) throws IllegalInputException {
        String str = getStringInput(msg, flag);

        if (flag == InputFlag.CANBEBLANK && str.isBlank()) return 1.0;

        double d;
        try {
            d = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            throw new IllegalInputException("Non number input entered!");
        }

        return d;
    }

    public static Size getSizeInput(String msg, InputFlag flag) throws IllegalInputException {
        String str = getStringInput(msg, flag);

        if (Size.toSize(str) == null) {
            throw new IllegalInputException("Wrong format for Size, Input must in format 'XS / M / L / XL / XXL'");
        }

        return Size.toSize(str);
    }
}

