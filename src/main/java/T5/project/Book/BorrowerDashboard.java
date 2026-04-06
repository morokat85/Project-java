package T5.project.Book;

import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
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
        this.library = App.library;

        if (library == null) {
            JOptionPane.showMessageDialog(this, "Library system not initialized!");
            return;
        }

        this.borrowerName = AuthService.getBorrowerName(borrowerId);
        if (borrowerName == null) borrowerName = "Unknown";

        setTitle("Borrower Dashboard - " + borrowerName);
        setSize(600, 450);
        setLayout(null);

        JButton borrowBtn = new JButton("Borrow Book");
        borrowBtn.setBounds(20, 20, 140, 30);
        add(borrowBtn);

        JButton viewLoansBtn = new JButton("View My Loans");
        viewLoansBtn.setBounds(180, 20, 160, 30);
        add(viewLoansBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(460, 380, 100, 30);
        add(logoutBtn);

        // Use JScrollPane so content scrolls if there are many loans
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(displayArea);
        scroll.setBounds(20, 70, 540, 290);
        add(scroll);

        borrowBtn.addActionListener(e -> borrowBook());
        viewLoansBtn.addActionListener(e -> viewMyLoans());
        logoutBtn.addActionListener(e -> {
            new LoginForm();
            dispose();
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void borrowBook() {
        try {
            String input = JOptionPane.showInputDialog(this, "Enter Book ID to borrow:");
            if (input == null || input.trim().isEmpty()) return;

            int bookId = Integer.parseInt(input.trim());

            library.login(AuthService.getBorrowers(), "borrower", "1234");
            library.borrowBook(bookId, borrowerId, borrowerName, LocalDate.now());
            JOptionPane.showMessageDialog(this, library.getLastMessage());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void viewMyLoans() {
        displayArea.setText("");
        List<BorrowBook> loans = library.getLoansByBorrower(borrowerId);

        if (loans == null || loans.isEmpty()) {
            displayArea.setText("  No loans found.");
            return;
        }

        // Clean formatted display for each loan
        for (int i = 0; i < loans.size(); i++) {
            BorrowBook loan = loans.get(i);
            String status = loan.isReturned() ? "Returned" : "Not Returned";

            displayArea.append("------------------------------------------\n");
            displayArea.append("  Loan #" + (i + 1) + "\n");
            displayArea.append("  Book ID   : " + loan.getBookId() + "\n");
            displayArea.append("  Title     : " + loan.getBookTitle() + "\n");
            displayArea.append("  Borrower  : " + loan.getBorrowerName() + "\n");
            displayArea.append("  Borrow    : " + loan.getBorrowDate() + "\n");
            displayArea.append("  Return by : " + loan.getReturnDate() + "\n");
            displayArea.append("  Status    : " + status + "\n");
        }
        displayArea.append("------------------------------------------\n");
    }
}