package T5.project;

public class Borrow_book {

    private String bookTitle;
    private String borrowerName;
    private String borrowDate;
    private String returnDate;
    private boolean isReturned;

    public Borrow_book(String bookTitle, String borrowerName, String borrowDate, String returnDate, boolean isReturned) {
        setBookTitle(bookTitle);
        setBorrowerName(borrowerName);
        setBorrowDate(borrowDate);
        setReturnDate(returnDate);
        setReturned(isReturned);
    }
   public String getBookTitle(){
    return bookTitle;
   }
    public String getBorrowerName() {
        return borrowerName;
    }
    public String getBorrowDate() {
        return borrowDate;
    }
    public String getReturnDate() {
        return returnDate;
    }
    public boolean isReturned() {
        return isReturned;
    }
    public void setBookTitle(String bookTitle) {
        if (bookTitle == null || bookTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        this.bookTitle = bookTitle;
    }
    public void setBorrowerName(String borrowerName) {
        if (borrowerName == null || borrowerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Borrower name cannot be null or empty");
        }
        this.borrowerName = borrowerName;       
    }
    public void setBorrowDate(String borrowDate) {
        if (borrowDate == null || borrowDate.trim().isEmpty()) {
            throw new IllegalArgumentException("Borrow date cannot be null or empty");
        }
        this.borrowDate = borrowDate;
    }
    public void setReturnDate(String returnDate) {  
        if (returnDate == null || returnDate.trim().isEmpty()) {
            throw new IllegalArgumentException("Return date cannot be null or empty");
        }
        this.returnDate = returnDate;}
   public void setReturned(boolean isReturned) {
    this.isReturned = isReturned;
}

      
    }

}
