package T5.project;

public class Book {
    private String title;        
    private String author;       
    private boolean available;   

    public Book(String title, String author) {
        setTitle(title);
        setAuthor(author);
        this.available = true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title.isBlank() || title == null) {
            throw new IllegalArgumentException("Title must not empty");
        }
        this.title = title;

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
         if (author.isEmpty() || author == null) {
            throw new IllegalArgumentException("Author must not empty");
        }
        this.author = author;
    }

    public boolean isAvailable() {
        return available;
    }

    void markBorrowed() {
        this.available = false;

    }

    void markReturned() {
        this.available = true;
    }
}