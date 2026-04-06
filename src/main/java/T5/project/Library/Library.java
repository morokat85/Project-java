package T5.project.Library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import T5.project.Book.Book;
import T5.project.Book.BorrowBook;
import T5.project.Sevice.IUser;

// 
//  CONCEPT: Methods & Classes, ArrayLists, Conditions & Loops
//  All operations go through Library so permissions can be checked.

public class Library {

    // Permission action constants (mirrors CoffeeShop constants)
    public static final String ADD_BOOK    = "ADD_BOOK";
    public static final String DELETE_BOOK = "DELETE_BOOK";
    public static final String BORROW_BOOK = "BORROW_BOOK";
    public static final String RETURN_BOOK = "RETURN_BOOK";
    public static final String VIEW_LOANS  = "VIEW_LOANS";

    // CONCEPT: ArrayLists — store all data
    private List<Book> books = new ArrayList<>();
    private List<BorrowBook> loans = new ArrayList<>();

    // CONCEPT: Reference type — currently logged-in user
    // CONCEPT: Interface used as type (IUser), not concrete class
    private IUser loggedInUser;

    private String lastMessage;

    // =========================
    // LOGIN / LOGOUT
    // =========================

    // CONCEPT: Primitive vs Reference — users list passed by reference
    public void login(List<? extends IUser> users, String username, String password) {
        if (username == null || password == null) {
            lastMessage = "Login failed: missing credentials.";
            return;
        }

        // CONCEPT: Conditions & Loops
        for (IUser user : users) {
            if (user.getUsername().equalsIgnoreCase(username.trim())) {
                if (!user.isActive()) {
                    lastMessage = "Login failed: account is inactive.";
                    return;
                }
                if (!user.checkPassword(password)) {
                    lastMessage = "Login failed: wrong password.";
                    return;
                }
                loggedInUser = user;
                lastMessage = "Login success. Welcome " + user.getFullName() + "!";
                return;
            }
        }
        lastMessage = "Login failed: username not found.";
    }

    public void logout() {
        loggedInUser = null;
        lastMessage = "Logged out successfully.";
    }

    public boolean isLoggedIn()      { return loggedInUser != null; }
    public IUser getLoggedInUser()   { return loggedInUser; }
    public String getLastMessage()   { return lastMessage; }

    // =========================
    // PERMISSION CHECK (mirrors requirePermission in CoffeeShop)
    // =========================
    private boolean requirePermission(String action) {
        if (loggedInUser == null) {
            lastMessage = "Action denied: please login first.";
            return false;
        }
        if (!loggedInUser.isActive()) {
            loggedInUser = null;
            lastMessage = "Action denied: account is inactive (auto logout).";
            return false;
        }
        // CONCEPT: Polymorphism — calls can() on whatever IUser subtype is logged in
        if (!loggedInUser.can(action)) {
            lastMessage = "Permission denied: you cannot perform [" + action + "].";
            return false;
        }
        return true;
    }

    // =========================
    // ADD BOOK (Librarian only)
    // =========================
    public void addBook(Book book) {
        if (!requirePermission(ADD_BOOK)) return;

        // CONCEPT: Exception Handling — null check
        if (book == null) {
            lastMessage = "Cannot add book: book is null.";
            return;
        }

        // duplicate check
        for (Book b : books) {
            if (b.getBookId() == book.getBookId()) {
                lastMessage = "Cannot add book: book ID already exists.";
                return;
            }
        }

        books.add(book);
        lastMessage = "Book added successfully: " + book.getBookTitle();
    }

    // =========================
    // DELETE BOOK (Librarian only)
    // =========================
    public void deleteBook(int bookId) {
        if (!requirePermission(DELETE_BOOK)) return;

        // CONCEPT: Conditions & Loops
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getBookId() == bookId) {
                String title = books.get(i).getBookTitle();
                books.remove(i);
                lastMessage = "Book deleted: " + title;
                return;
            }
        }
        lastMessage = "Book not found with ID: " + bookId;
    }

    // =========================
    // BORROW BOOK
    // =========================
    public void borrowBook(int bookId, int borrowerId, String borrowerName, LocalDate borrowDate) {
        if (!requirePermission(BORROW_BOOK)) return;

        // CONCEPT: Conditions & Loops
        for (Book b : books) {
            if (b.getBookId() == bookId) {
                if (!b.isAvailable()) {
                    lastMessage = "Cannot borrow: book is already borrowed.";
                    return;
                }

                // CONCEPT: Primitive vs Reference
                // b is a reference — setAvailable() changes the actual object in the list
                b.setAvailable(false);

                // CONCEPT: Reference type — BorrowBook stores the Book object reference
                loans.add(new BorrowBook(b, borrowerId, borrowerName, borrowDate));
                lastMessage = "Book borrowed! Return by: " + borrowDate.plusDays(7);
                return;
            }
        }
        lastMessage = "Cannot borrow: book not found.";
    }

    // =========================
    // RETURN BOOK (Librarian only)
    // =========================
    public void returnBook(int bookId, int borrowerId) {
        if (!requirePermission(RETURN_BOOK)) return;

        for (BorrowBook loan : loans) {
            if (loan.getBookId() == bookId &&
                loan.getBorrowerId() == borrowerId &&
                !loan.isReturned()) {

                loan.returnBook();

                // Update the actual Book object (reference type)
                for (Book b : books) {
                    if (b.getBookId() == bookId) {
                        b.setAvailable(true);
                    }
                }

                lastMessage = "Book returned successfully.";
                return;
            }
        }
        lastMessage = "Loan not found or already returned.";
    }

    // =========================
    // VIEW HELPERS
    // =========================
    public List<Book> getBooks()       { return books; }
    public List<BorrowBook> getLoans() { return loans; }

    public List<BorrowBook> getLoansByBorrower(int borrowerId) {
        List<BorrowBook> result = new ArrayList<>();
        for (BorrowBook loan : loans) {
            if (loan.getBorrowerId() == borrowerId) {
                result.add(loan);
            }
        }
        return result;
    }

    public void printBooks() {
        System.out.println("\n--- Books (" + books.size() + ") ---");
        if (books.isEmpty()) { System.out.println("No books."); return; }
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ") " + books.get(i));
        }
    }

    public void printLoans() {
        System.out.println("\n--- Loans (" + loans.size() + ") ---");
        if (loans.isEmpty()) { System.out.println("No loans."); return; }
        for (int i = 0; i < loans.size(); i++) {
            System.out.println((i + 1) + ") " + loans.get(i));
        }
    }
}