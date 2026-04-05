package model;

import java.time.LocalDate;

// ============================================================
// CONCEPT: Reference types holding other objects
// Loan stores a REFERENCE to a Book object and a Borrower object.
// If those objects change, the Loan sees the change too.
// ============================================================
public class Loan {

    private String loanId;
    private Book book;         // reference type — points to a Book object
    private Borrower borrower; // reference type — points to a Borrower object
    private IStaff handledBy;  // reference to whoever processed the loan (interface type!)
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean returned;

    private static final int LOAN_DAYS = 7; // constant

    // CONCEPT: Constructor
    public Loan(String loanId, Book book, Borrower borrower, IStaff handledBy) {
        this.loanId    = loanId;
        this.book      = book;
        this.borrower  = borrower;
        this.handledBy = handledBy;
        this.borrowDate = LocalDate.now();
        this.dueDate    = borrowDate.plusDays(LOAN_DAYS);
        this.returned   = false;
    }

    // ===== Getters =====
    public String getLoanId()      { return loanId; }
    public Book getBook()          { return book; }
    public Borrower getBorrower()  { return borrower; }
    public IStaff getHandledBy()   { return handledBy; }
    public LocalDate getBorrowDate(){ return borrowDate; }
    public LocalDate getDueDate()  { return dueDate; }
    public boolean isReturned()    { return returned; }

    public void markReturned() {
        this.returned = true;
    }

    @Override
    public String toString() {
        return "Loan [" + loanId + "] | Book: \"" + book.getTitle() + "\""
                + " | Borrower: " + borrower.getFullName()
                + " | Due: " + dueDate
                + " | " + (returned ? "RETURNED" : "ACTIVE");
    }
}
