// Users access to the system, controls accessibility of user accounts
public enum Access {
    ADMIN,
    CLIENT;

    @Override
    public String toString() {
        return switch (this) {
            case Access.ADMIN -> "Admin";
            case Access.CLIENT -> "Client";
        };
    }
}
