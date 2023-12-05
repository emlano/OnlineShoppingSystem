// Sizes of clothing sold by the system
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
            case Size.XS -> "XS";
            case Size.S -> "S";
            case Size.M -> "M";
            case Size.L -> "L";
            case Size.XL -> "XL";
            case Size.XXL -> "XXL";
        };
    }
}
