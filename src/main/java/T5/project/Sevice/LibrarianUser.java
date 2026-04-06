package T5.project.Sevice;

import T5.project.Library.Library;
//   CONCEPT: Inheritance
//  ibrarianUser extends User — inherits all fields and methods.
public class LibrarianUser extends User {

    private String staffCode;

    // Permission constants (mirrors CoffeeShop action constants)
    public static final String ADD_BOOK    = Library.ADD_BOOK;
    public static final String DELETE_BOOK = Library.DELETE_BOOK;
    public static final String VIEW_LOANS  = Library.VIEW_LOANS;
    public static final String RETURN_BOOK = Library.RETURN_BOOK;

    // CONCEPT: Constructor calling super()
    public LibrarianUser(String userId, String fullName, String username,
                         String password, String staffCode) {
        super(userId, fullName, username, password); // calls User constructor
        setStaffCode(staffCode);
    }

    // CONCEPT: Polymorphism — overrides can() from User
    // Librarians can do everything
    @Override
    public boolean can(String action) {
        return true;
    }

    public String getStaffCode() { return staffCode; }

    public void setStaffCode(String staffCode) {
        if (staffCode == null || staffCode.trim().isEmpty()) {
            this.staffCode = "LIB000";
        } else {
            this.staffCode = staffCode.trim();
        }
    }

    // CONCEPT: Polymorphism — overrides toString()
    @Override
    public String toString() {
        return super.toString() + " [Librarian, staffCode='" + staffCode + "']";
    }
}