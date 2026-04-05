package T5.project.Sevice;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private static Map<String, String> staffAccounts = new HashMap<>();
    private static Map<Integer, String> borrowerAccounts = new HashMap<>();

    static {
        staffAccounts.put("admin", "1234");
        staffAccounts.put("staff", "1234");

        borrowerAccounts.put(101, "Alice");
        borrowerAccounts.put(102, "Bob");
    }

    public static boolean loginStaff(String username, String password) {
        return staffAccounts.containsKey(username) && staffAccounts.get(username).equals(password);
    }

    public static boolean loginBorrower(int borrowerId) {
        return borrowerAccounts.containsKey(borrowerId);
    }

    public static String getBorrowerName(int borrowerId) {
        return borrowerAccounts.get(borrowerId);
    }
}
