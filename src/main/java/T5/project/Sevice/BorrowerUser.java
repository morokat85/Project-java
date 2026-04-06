package T5.project.Sevice;

import T5.project.Library.Library;
//   CONCEPT: Inheritance
//   BorrowerUser extends User — inherits all fields and methods.

public class BorrowerUser extends User {

    private int borrowerId;

    // CONCEPT: Constructor calling super()
    public BorrowerUser(String userId, String fullName, String username,
                        String password, int borrowerId) {
        super(userId, fullName, username, password); // calls User constructor
        this.borrowerId = borrowerId;
    }

    // CONCEPT: Polymorphism — overrides can() from User
    // Borrowers can only borrow books and view their own loans
    @Override
    public boolean can(String action) {
        if (action.equals(Library.BORROW_BOOK) || action.equals(Library.VIEW_LOANS)) {
            return true;
        }
        return false;
    }

    public int getBorrowerId() { return borrowerId; }

    // CONCEPT: Polymorphism — overrides toString()
    @Override
    public String toString() {
        return super.toString() + " [Borrower, borrowerId=" + borrowerId + "]";
    }
}