package model;

// ============================================================
// CONCEPT: Class + Object + Constructor + Encapsulation
// Book is a blueprint. Each "new Book(...)" creates one object.
// All fields are private — only accessible through methods.
// ============================================================
public class Book {

    // CONCEPT: primitive vs reference types
    private int bookId;        // primitive int — stored by VALUE
    private String title;      // reference type — stores a POINTER to a String object
    private String author;
    private String category;
    private boolean available; // primitive boolean

    // CONCEPT: Constructor — runs when you do "new Book(...)"
    public Book(int bookId, String title, String author, String category) {
        setBookId(bookId);
        setTitle(title);
        setAuthor(author);
        setCategory(category);
        this.available = true; // all new books are available
    }

    // ===== Getters =====
    public int getBookId()      { return bookId; }
    public String getTitle()    { return title; }
    public String getAuthor()   { return author; }
    public String getCategory() { return category; }
    public boolean isAvailable(){ return available; }

    // ===== Setters with validation =====
    public void setBookId(int bookId) {
        // CONCEPT: condition for validation
        if (bookId <= 0) this.bookId = 0;
        else this.bookId = bookId;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) this.title = "Unknown Title";
        else this.title = title.trim();
    }

    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) this.author = "Unknown Author";
        else this.author = author.trim();
    }

    public void setCategory(String category) {
        if (category == null || category.trim().isEmpty()) this.category = "General";
        else this.category = category.trim();
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Book #" + bookId + " | \"" + title + "\" by " + author
                + " | " + category + " | " + (available ? "Available" : "Borrowed");
    }
}
