public class Manager extends User {
    public Manager(String username, String password) {
        // Manager accounts always created with Admin access
        super(username, password, Access.ADMIN);
    }
}
