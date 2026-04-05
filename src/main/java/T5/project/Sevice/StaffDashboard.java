package T5.project.Sevice;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import T5.project.App;
import T5.project.Book.Book;
import T5.project.Book.Borrow_book;
import T5.project.Library.Library;

public class StaffDashboard extends JFrame {
    private Library library = App.library;
    private JTextArea displayArea;

    public StaffDashboard() {
        setTitle("Staff Dashboard");
        setSize(600,400);
        setLayout(null);

        JButton addBtn = new JButton("📚 Add Book");
        addBtn.setBounds(20,20,120,30); add(addBtn);

        JButton deleteBtn = new JButton("🗑️ Delete Book");
        deleteBtn.setBounds(160,20,120,30); add(deleteBtn);

        JButton viewBooksBtn = new JButton("📚 View Books");
        viewBooksBtn.setBounds(300,20,120,30); add(viewBooksBtn);

        JButton viewLoansBtn = new JButton("🔄 View Loans");
        viewLoansBtn.setBounds(440,20,120,30); add(viewLoansBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(500, 330, 100, 30);
        add(logoutBtn);
        logoutBtn.addActionListener(e -> {
            new LoginForm(); // go back to login
            dispose(); // close staff dashboard
        });

        displayArea = new JTextArea();
        displayArea.setBounds(20,70,540,250);
        add(displayArea);

        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Book ID:"));
                if (id <= 0) throw new Exception("ID must be positive");
                String title = JOptionPane.showInputDialog("Book Title:");
                library.addBook(new Book(title,id));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Book ID to delete:"));
                library.deleteBook(id);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID");
            }
        });

        viewBooksBtn.addActionListener(e -> {
            displayArea.setText("");
            for (Book b : library.getBooks()) displayArea.append(b.toString()+"\n");
        });

        viewLoansBtn.addActionListener(e -> {
            displayArea.setText("");
            @SuppressWarnings("unchecked")
            List<Borrow_book> loans = (List<Borrow_book>) library.getLoans();
            for (Borrow_book l : loans) displayArea.append(l.toString()+"\n");
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}

