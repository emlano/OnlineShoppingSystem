package comparators;

import lib.User;

import java.util.Comparator;

public class UsernameComparator implements Comparator<User> {
    public int compare(User first, User second) {
        return first.getUsername().compareTo(second.getUsername());
    }
}
