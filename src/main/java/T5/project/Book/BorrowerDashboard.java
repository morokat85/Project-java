package T5.project.Book;

import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import T5.project.App;
import T5.project.Library.Library;
import T5.project.Sevice.AuthService;
import T5.project.Sevice.LoginForm;


public class BorrowerDashboard extends JFrame {

    private Library library;
    private JTextArea displayArea;
    private int borrowerId;
    private String borrowerName;

    public BorrowerDashboard(int borrowerId) {
        this.borrowerId = borrowerId;

        //  Initialize library safely
        this.library = App.library;
        if (library == null) {
            JOptionPane.showMessageDialog(this, "Library system not initialized!");
            return;
        }

        //  Get borrower name safely
        this.borrowerName = AuthService.getBorrowerName(borrowerId);
        if (borrowerName == null) {
            borrowerName = "Unknown";
        }

        setTitle("Borrower Dashboard");
        setSize(600, 400);
        setLayout(null);

        // Buttons
        JButton borrowBtn = new JButton("Borrow Book");
        borrowBtn.setBounds(20, 20, 140, 30);
        add(borrowBtn);

        JButton viewLoansBtn = new JButton("View My Loans");
        viewLoansBtn.setBounds(180, 20, 160, 30);
        add(viewLoansBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(450, 320, 100, 30);
        add(logoutBtn);

        // Text Area
        displayArea = new JTextArea();
        displayArea.setBounds(20, 70, 540, 230);
        add(displayArea);

        // Actions
        borrowBtn.addActionListener(e -> borrowBook());
        viewLoansBtn.addActionListener(e -> viewMyLoans());

        logoutBtn.addActionListener(e -> {
            new LoginForm();
            dispose();
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center screen
        setVisible(true);
    }

    //  FIXED borrowBook with safe input handling
    private void borrowBook() {
        try {
            String input = JOptionPane.showInputDialog(this, "Enter Book ID to borrow:");

            // Handle cancel
            if (input == null || input.trim().isEmpty()) {
                return;
            }

            int bookId = Integer.parseInt(input);
            LocalDate borrowDate = LocalDate.now();

            String message = library.borrowBook(bookId, borrowerId, borrowerName, borrowDate);
            JOptionPane.showMessageDialog(this, message);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    //  FIXED: removed wrong casting
    private void viewMyLoans() {
        displayArea.setText("");

        List<Borrow_book> loans = library.getLoansByBorrower(borrowerId);

        if (loans == null || loans.isEmpty()) {
            displayArea.setText("No loans found.");
            return;
        }

        for (Borrow_book loan : loans) {
            displayArea.append(loan.toString() + "\n");
        }
    }
}