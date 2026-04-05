package T5.project.Library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import T5.project.Book.Book;
import T5.project.Book.Borrow_book;

public class Library {
    private List<Book> books = new ArrayList<>();
    private List<Borrow_book> loans = new ArrayList<>();

    public void addBook(Book book) { books.add(book); }
    public void deleteBook(int bookId) {
        books.removeIf(b -> b.getBookId() == bookId);
    }

    public List<Book> getBooks() { return books; }
    public List<Borrow_book> getLoans() { return loans; }

    public String borrowBook(int bookId, int borrowerId, String borrowerName, LocalDate borrowDate) {
        for (Book b : books) {
            if (b.getBookId() == bookId) {
                if (!b.isAvailable()) return "Book is already borrowed!";
                b.setAvailable(false);
                loans.add(new Borrow_book(b, borrowerId, borrowerName, borrowDate));
                return "Book borrowed successfully! Return date: " + borrowDate.plusDays(7);
            }
        }
        return "Book not found!";
    }

    public String returnBook(int bookId, int borrowerId) {
        for (Borrow_book loan : loans) {
            if (loan.getBookId() == bookId && loan.getBorrowerId() == borrowerId && !loan.isReturned()) {
                loan.returnBook();
                for (Book b : books) {
                    if (b.getBookId() == bookId) b.setAvailable(true);
                }
                return "Book returned successfully!";
            }
        }
        return "Loan not found or already returned!";
    }

    public List<Borrow_book> getLoansByBorrower(int borrowerId) {
        List<Borrow_book> result = new ArrayList<>();
        for (Borrow_book loan : loans) {
            if (loan.getBorrowerId() == borrowerId) result.add(loan);
        }
        return result;
    }
}