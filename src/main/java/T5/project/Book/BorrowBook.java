package T5.project.Book;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * CONCEPT: Primitive vs Reference
 * Stores a reference to the actual Book object, not just a copy.
 * This means if Book changes, BorrowBook reflects it.
 *
 * Renamed from Borrow_book → BorrowBook (Java naming convention: PascalCase)
 */
public class BorrowBook {

    // CONCEPT: Reference type — holds the actual Book object
    private Book book;

    // CONCEPT: Primitive types
    private int borrowerId;
    private boolean isReturned;

    // Reference types
    private String borrowerName;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    private static final int BORROW_PERIOD = 7;

    // CONCEPT: Constructor
    public BorrowBook(Book book, int borrowerId, String borrowerName, LocalDate borrowDate) {
        // CONCEPT: Exception Handling
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }
        if (borrowerName == null || borrowerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Borrower name cannot be empty.");
        }

        // CONCEPT: Reference — storing the object itself, not a copy
        this.book = book;
        this.borrowerId = borrowerId;
        this.borrowerName = borrowerName.trim();
        this.borrowDate = borrowDate;
        this.returnDate = borrowDate.plusDays(BORROW_PERIOD);
        this.isReturned = false;
    }

    // CONCEPT: Getters
    public int getBookId()        { return book.getBookId(); }      // from the Book reference
    public String getBookTitle()  { return book.getBookTitle(); }   // from the Book reference
    public Book getBook()         { return book; }                  // full reference

    public int getBorrowerId()    { return borrowerId; }
    public String getBorrowerName() { return borrowerName; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public boolean isReturned()   { return isReturned; }

    public void returnBook() {
        this.isReturned = true;
    }
@Override
public String toString() {
    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd MMM yyyy");

    return "Book ID: " + book.getBookId() +
           "\nTitle: " + book.getBookTitle() +
           "\nBorrower: " + borrowerName +
           "\nBorrow Date: " + borrowDate.format(df) +
           "\nReturn Date: " + returnDate.format(df) +
           "\nStatus: " + (isReturned ? "Returned" : "Not Returned") +
           "\n---------------------------";
}
    
}