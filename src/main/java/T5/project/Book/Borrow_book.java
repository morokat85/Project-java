package T5.project.Book;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Borrow_book {
    private int bookId;
    private String bookTitle;
    private String borrowerName;
    private int borrowerId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean isReturned;

    private static final int BORROW_PERIOD = 7; // 7 days

    public Borrow_book(Book book, int borrowerId, String borrowerName, LocalDate borrowDate) {
        this.bookId = book.getBookId();
        this.bookTitle = book.getBookTitle();
        this.borrowerId = borrowerId;
        this.borrowerName = borrowerName;
        this.borrowDate = borrowDate;
        this.returnDate = borrowDate.plusDays(BORROW_PERIOD);
        this.isReturned = false;
    }

    public int getBookId() { return bookId; }
    public int getBorrowerId() { return borrowerId; }
    public String getBorrowerName() { return borrowerName; }
    public String getBookTitle() { return bookTitle; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public boolean isReturned() { return isReturned; }

    public void returnBook() {
        isReturned = true;
    }

    @Override
    public String toString() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return bookId + " | " + bookTitle + " | " + borrowerName +
                " | Borrow: " + borrowDate.format(df) +
                " | Return: " + returnDate.format(df) +
                " | " + (isReturned ? "Returned" : "Borrowed");
    }
}