package T5.project;

public abstract class LibraryUser {
    protected String name;

    public LibraryUser(String name) {
        this.name = name;
    }

    // Abstract method for polymorphism
    public abstract void borrowBook();
}
