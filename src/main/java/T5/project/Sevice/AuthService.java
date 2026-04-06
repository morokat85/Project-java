package T5.project.Sevice;

import java.util.ArrayList;
import java.util.List;

public class AuthService {

    private static List<LibrarianUser> librarians = new ArrayList<>();
    private static List<BorrowerUser>  borrowers  = new ArrayList<>();

    static {
        librarians.add(new LibrarianUser("L001", "Admin Librarian", "admin", "1234", "LIB001"));
        borrowers.add(new BorrowerUser("B001", "Test Borrower", "borrower", "1234", 1001));
    }

    // =========================
    // LOGIN — accepts username OR staffId
    // =========================
    public static boolean loginStaff(String usernameOrId, String password) {
        if (usernameOrId == null || password == null) return false;
        String input = usernameOrId.trim();

        for (LibrarianUser lib : librarians) {
            boolean matchUsername = lib.getUsername().equalsIgnoreCase(input);
            boolean matchId       = lib.getUserId().equalsIgnoreCase(input);

            if ((matchUsername || matchId) && lib.checkPassword(password) && lib.isActive()) {
                return true;
            }
        }
        return false;
    }

    // Returns the matching librarian (needed by StaffDashboard to pass into library.login)
    public static LibrarianUser getLibrarianByUsernameOrId(String usernameOrId) {
        if (usernameOrId == null) return null;
        String input = usernameOrId.trim();
        for (LibrarianUser lib : librarians) {
            if (lib.getUsername().equalsIgnoreCase(input) || lib.getUserId().equalsIgnoreCase(input)) {
                return lib;
            }
        }
        return null;
    }

    public static boolean loginBorrower(int borrowerId) {
        for (BorrowerUser b : borrowers) {
            if (b.getBorrowerId() == borrowerId && b.isActive()) return true;
        }
        return false;
    }

    // =========================
    // REGISTER
    // =========================
    public static void registerLibrarian(String userId, String fullName,
                                          String username, String password,
                                          String staffCode) {
        for (LibrarianUser lib : librarians) {
            if (lib.getUsername().equalsIgnoreCase(username)) {
                throw new IllegalArgumentException("Username already exists: " + username);
            }
        }
        librarians.add(new LibrarianUser(userId, fullName, username, password, staffCode));
    }

    public static void registerBorrower(String userId, String fullName,
                                         String username, String password,
                                         int borrowerId) {
        for (BorrowerUser bor : borrowers) {
            if (bor.getUsername().equalsIgnoreCase(username)) {
                throw new IllegalArgumentException("Username already exists: " + username);
            }
        }
        borrowers.add(new BorrowerUser(userId, fullName, username, password, borrowerId));
    }

    // =========================
    // GETTERS
    // =========================
    public static List<LibrarianUser> getLibrarians() { return librarians; }
    public static List<BorrowerUser>  getBorrowers()  { return borrowers; }

    public static List<IUser> getAllUsers() {
        List<IUser> all = new ArrayList<>();
        all.addAll(librarians);
        all.addAll(borrowers);
        return all;
    }

    public static String getBorrowerName(int borrowerId) {
        for (BorrowerUser b : borrowers) {
            if (b.getBorrowerId() == borrowerId) return b.getFullName();
        }
        return null;
    }
}