package lib;

import enums.Access;

public class Manager extends User {
    public Manager(String username, String password) {
        // Manager accounts are always created with Admin access
        super(username, password, Access.ADMIN);
    }
}
