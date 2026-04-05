package T5.project.Book;

public class Book {
    private String bookTitle;
    private int bookId;
    private boolean isAvailable;

    public Book(String bookTitle, int bookId) {
        setBookTitle(bookTitle);
        setBookId(bookId);
        this.isAvailable = true; // default available
    }

    public void setBookTitle(String bookTitle) {
        if (bookTitle == null || bookTitle.isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be empty");
        }
        this.bookTitle = bookTitle;
    }

    public String getBookTitle() { return bookTitle; }

    public void setBookId(int bookId) {
        if (bookId <= 0) {
            throw new IllegalArgumentException("Book ID must be positive");
        }
        this.bookId = bookId;
    }

    public int getBookId() { return bookId; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return bookId + " | " + bookTitle + " | " + (isAvailable ? "Available" : "Borrowed");
    }
}