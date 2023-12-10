package enums;

public enum Size {
    XS,
    S,
    M,
    L,
    XL,
    XXL;

    @Override
    public String toString() {
        return switch (this) {
            case XS -> "XS";
            case S -> "S";
            case M -> "M";
            case L -> "L";
            case XL -> "XL";
            case XXL -> "XXL";
        };
    }

    public static Size toSize(String s) {
        return switch (s) {
            case "XS" -> Size.XS;
            case "S" -> Size.S;
            case "M" -> Size.M;
            case "L" -> Size.L;
            case "XL" -> Size.XL;
            case "XXL" -> Size.XXL;
            default -> null;
        };
    }
}
