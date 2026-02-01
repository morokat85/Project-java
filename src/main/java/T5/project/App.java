package T5.project;


public class App 
{
    public static void main( String[] args )
    {
        Borrow_book borrowBook = new Borrow_book("The Great Gatsby", "John Doe", "2023-01-01", "2023-01-15", false);

        System.out.println("Book Title: " + borrowBook.getBookTitle());
        System.out.println("Borrower Name: " + borrowBook.getBorrowerName());
        System.out.println("Borrow Date: " + borrowBook.getBorrowDate());
        System.out.println("Return Date: " + borrowBook.getReturnDate());
        System.out.println("Is Returned: " + borrowBook.isReturned());

      
    }
}
