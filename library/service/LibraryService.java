package service;

import model.*;
import java.util.ArrayList;

// ============================================================
// CONCEPT: This is the "brain" of the system (like CoffeeShop.java)
// It holds all the data in ArrayLists and controls all actions.
// It enforces login + permission checks before doing anything.
// ============================================================
public class LibraryService {

    // ===== Permission Action Constants =====
    public static final String ADD_BOOK     = "ADD_BOOK";
    public static final String DELETE_BOOK  = "DELETE_BOOK";
    public static final String VIEW_BOOKS   = "VIEW_BOOKS";
    public static final String BORROW_BOOK  = "BORROW_BOOK";
    public static final String RETURN_BOOK  = "RETURN_BOOK";
    public static final String VIEW_LOANS   = "VIEW_LOANS";
    public static final String ADD_STAFF    = "ADD_STAFF";
    public static final String ADD_BORROWER = "ADD_BORROWER";

    // ===== "Tables" using ArrayList (CONCEPT: Array/ArrayList) =====
    private ArrayList<Staff> staffList;
    private ArrayList<Book> bookList;
    private ArrayList<Borrower> borrowerList;
    private ArrayList<Loan> loanList;

    // ===== Logged-in staff reference (null = nobody logged in) =====
    private IStaff loggedInStaff; // CONCEPT: interface as a type

    // ===== Feedback message for the UI =====
    private String lastMessage;

    // ===== CONCEPT: Constructor =====
    public LibraryService() {
        staffList    = new ArrayList<>();
        bookList     = new ArrayList<>();
        borrowerList = new ArrayList<>();
        loanList     = new ArrayList<>();
        loggedInStaff = null;
        lastMessage   = "";

        seedData(); // add some starting data
    }

    // ===== Seed some default data so demo works right away =====
    private void seedData() {
        // Default admin
        staffList.add(new AdminStaff("S001", "Admin", "admin", "1234", 2000));
        // A librarian
        staffList.add(new LibrarianStaff("S002", "Sara", "sara", "1234", "Morning"));

        // Some books — CONCEPT: loop to add multiple books
        bookList.add(new Book(1, "Harry Potter", "J.K. Rowling", "Fantasy"));
        bookList.add(new Book(2, "Clean Code", "Robert Martin", "Technology"));
        bookList.add(new Book(3, "The Alchemist", "Paulo Coelho", "Fiction"));

        // Some borrowers
        borrowerList.add(new Borrower(101, "Alice", "012111111"));
        borrowerList.add(new Borrower(102, "Bob",   "012222222"));

        lastMessage = "System ready. Login: admin/1234 or sara/1234";
    }

    // =============================================================
    // GETTERS
    // =============================================================
    public String getLastMessage()    { return lastMessage; }
    public boolean isLoggedIn()       { return loggedInStaff != null; }
    public IStaff getLoggedInStaff()  { return loggedInStaff; }
    public ArrayList<Book> getBooks()         { return bookList; }
    public ArrayList<Borrower> getBorrowers() { return borrowerList; }
    public ArrayList<Loan> getLoans()         { return loanList; }
    public ArrayList<Staff> getStaffList()    { return staffList; }

    private void setLastMessage(String msg) { lastMessage = msg; }

    // =============================================================
    // LOGIN / LOGOUT
    // =============================================================
    public void login(String username, String password) {
        // CONCEPT: loop through ArrayList to find matching staff
        for (int i = 0; i < staffList.size(); i++) {
            Staff s = staffList.get(i);
            if (s.getUsername().equalsIgnoreCase(username)) {
                if (!s.isActive()) {
                    setLastMessage("Login failed: account is inactive.");
                    return;
                }
                if (!s.checkPassword(password)) {
                    setLastMessage("Login failed: wrong password.");
                    return;
                }
                loggedInStaff = s;
                setLastMessage("Welcome, " + s.getFullName() + "!");
                return;
            }
        }
        setLastMessage("Login failed: username not found.");
    }

    public void logout() {
        loggedInStaff = null;
        setLastMessage("Logged out.");
    }

    // =============================================================
    // PERMISSION HELPERS
    // =============================================================
    private boolean requireLogin() {
        if (loggedInStaff == null) {
            setLastMessage("Please login first.");
            return false;
        }
        return true;
    }

    // CONCEPT: Using the interface method can() — works for ANY staff type
    private boolean requirePermission(String action) {
        if (!requireLogin()) return false;
        if (!loggedInStaff.can(action)) {
            setLastMessage("Permission denied: you cannot do '" + action + "'.");
            return false;
        }
        return true;
    }

    // =============================================================
    // STAFF ACTIONS
    // =============================================================
    public void addStaff(String staffId, String fullName, String username, String password, String role) {
        if (!requirePermission(ADD_STAFF)) return;

        // CONCEPT: loop to check for duplicate username
        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getUsername().equalsIgnoreCase(username)) {
                setLastMessage("Username already exists.");
                return;
            }
        }

        // CONCEPT: condition to pick the right subclass
        if (role.equalsIgnoreCase("Admin")) {
            staffList.add(new AdminStaff(staffId, fullName, username, password, 1500));
            setLastMessage("Admin staff added.");
        } else if (role.equalsIgnoreCase("Librarian")) {
            staffList.add(new LibrarianStaff(staffId, fullName, username, password, "Morning"));
            setLastMessage("Librarian staff added.");
        } else {
            setLastMessage("Unknown role. Use 'Admin' or 'Librarian'.");
        }
    }

    // =============================================================
    // BOOK ACTIONS
    // =============================================================
    public void addBook(int bookId, String title, String author, String category) {
        if (!requirePermission(ADD_BOOK)) return;

        // check duplicate ID
        if (findBookById(bookId) != null) {
            setLastMessage("Book ID already exists.");
            return;
        }

        bookList.add(new Book(bookId, title, author, category));
        setLastMessage("Book added: \"" + title + "\"");
    }

    public void deleteBook(int bookId) {
        if (!requirePermission(DELETE_BOOK)) return;

        Book book = findBookById(bookId);
        if (book == null) {
            setLastMessage("Book not found.");
            return;
        }
        if (!book.isAvailable()) {
            setLastMessage("Cannot delete: book is currently borrowed.");
            return;
        }

        bookList.remove(book);
        setLastMessage("Book deleted.");
    }

    // =============================================================
    // BORROWER ACTIONS
    // =============================================================
    public void addBorrower(int borrowerId, String fullName, String phone) {
        if (!requirePermission(ADD_BORROWER)) return;

        if (findBorrowerById(borrowerId) != null) {
            setLastMessage("Borrower ID already exists.");
            return;
        }

        borrowerList.add(new Borrower(borrowerId, fullName, phone));
        setLastMessage("Borrower added: " + fullName);
    }

    // =============================================================
    // LOAN ACTIONS
    // =============================================================
    public void borrowBook(int bookId, int borrowerId) {
        if (!requirePermission(BORROW_BOOK)) return;

        Book book = findBookById(bookId);
        if (book == null) {
            setLastMessage("Book not found.");
            return;
        }
        if (!book.isAvailable()) {
            setLastMessage("Book is already borrowed.");
            return;
        }

        Borrower borrower = findBorrowerById(borrowerId);
        if (borrower == null) {
            setLastMessage("Borrower not found.");
            return;
        }
        if (!borrower.isActive()) {
            setLastMessage("Borrower account is inactive.");
            return;
        }

        // Generate loan ID
        String loanId = "L" + (loanList.size() + 1);

        // CONCEPT: object reference — Loan holds references to book & borrower
        loanList.add(new Loan(loanId, book, borrower, loggedInStaff));
        book.setAvailable(false); // mark as borrowed

        setLastMessage("Loan created [" + loanId + "]. Due in 7 days.");
    }

    public void returnBook(int bookId, int borrowerId) {
        if (!requirePermission(RETURN_BOOK)) return;

        // CONCEPT: loop to find the active loan
        for (int i = 0; i < loanList.size(); i++) {
            Loan loan = loanList.get(i);
            if (loan.getBook().getBookId() == bookId
                    && loan.getBorrower().getBorrowerId() == borrowerId
                    && !loan.isReturned()) {

                loan.markReturned();
                loan.getBook().setAvailable(true);
                setLastMessage("Book returned successfully. Loan: " + loan.getLoanId());
                return;
            }
        }
        setLastMessage("No active loan found for this book + borrower.");
    }

    // =============================================================
    // FIND HELPERS (private — internal use only)
    // =============================================================
    private Book findBookById(int bookId) {
        // CONCEPT: loop + condition to search
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getBookId() == bookId)
                return bookList.get(i);
        }
        return null; // not found
    }

    private Borrower findBorrowerById(int borrowerId) {
        for (int i = 0; i < borrowerList.size(); i++) {
            if (borrowerList.get(i).getBorrowerId() == borrowerId)
                return borrowerList.get(i);
        }
        return null;
    }
}
