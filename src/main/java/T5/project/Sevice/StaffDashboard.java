package T5.project.Sevice;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import T5.project.App;
import T5.project.Book.Book;
import T5.project.Book.BorrowBook;
import T5.project.Library.Library;

public class StaffDashboard extends JFrame {

    private Library library = App.library;
    private JTextArea displayArea;

    public StaffDashboard(String username, String password) {
        setTitle("Staff Dashboard");
        setSize(600, 400);
        setLayout(null);

        // Login into the library system using the staff credentials
        library.login(AuthService.getLibrarians(), username, password);

        JButton addBtn = new JButton("Add Book");
        addBtn.setBounds(20, 20, 120, 30);
        add(addBtn);

        JButton deleteBtn = new JButton("Delete Book");
        deleteBtn.setBounds(160, 20, 120, 30);
        add(deleteBtn);

        JButton viewBooksBtn = new JButton("View Books");
        viewBooksBtn.setBounds(300, 20, 120, 30);
        add(viewBooksBtn);

        JButton viewLoansBtn = new JButton("View Loans");
        viewLoansBtn.setBounds(440, 20, 120, 30);
        add(viewLoansBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(480, 330, 100, 30);
        add(logoutBtn);

        displayArea = new JTextArea();
        displayArea.setBounds(20, 70, 540, 250);
        add(displayArea);

        addBtn.addActionListener(e -> {
            try {
                String idInput = JOptionPane.showInputDialog(this, "Book ID:");
                if (idInput == null) return;
                int id = Integer.parseInt(idInput.trim());

                String title = JOptionPane.showInputDialog(this, "Book Title:");
                if (title == null || title.trim().isEmpty()) return;

                library.addBook(new Book(title.trim(), id));
                JOptionPane.showMessageDialog(this, library.getLastMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Book ID must be a number!");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        deleteBtn.addActionListener(e -> {
            try {
                String idInput = JOptionPane.showInputDialog(this, "Book ID to delete:");
                if (idInput == null) return;
                int id = Integer.parseInt(idInput.trim());
                library.deleteBook(id);
                JOptionPane.showMessageDialog(this, library.getLastMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID format!");
            }
        });

        viewBooksBtn.addActionListener(e -> {
            displayArea.setText("");
            List<Book> books = library.getBooks();
            if (books.isEmpty()) {
                displayArea.setText("No books found.");
                return;
            }
            for (Book b : books) {
                displayArea.append(b.toString() + "\n");
            }
        });

        viewLoansBtn.addActionListener(e -> {
            displayArea.setText("");
            List<BorrowBook> loans = library.getLoans();
            if (loans.isEmpty()) {
                displayArea.setText("No loans found.");
                return;
            }
            for (BorrowBook l : loans) {
                displayArea.append(l.toString() + "\n");
            }
        });

        logoutBtn.addActionListener(e -> {
            library.logout();
            new LoginForm();
            dispose();
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}