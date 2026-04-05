package ui;

import model.*;
import service.LibraryService;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// ============================================================
// Main dashboard shown after staff login.
// Uses tabs to keep things simple and organized.
// ============================================================
public class StaffDashboard extends JFrame {

    private LibraryService library;
    private JFrame loginForm;
    private JTextArea outputArea;   // shared text area to show results
    private JLabel statusBar;

    public StaffDashboard(LibraryService library, JFrame loginForm) {
        this.library   = library;
        this.loginForm = loginForm;

        setTitle("Library System  —  Logged in as: " + library.getLoggedInStaff().getFullName());
        setSize(680, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(6, 6));

        // ---- Top: status bar ----
        statusBar = new JLabel("  " + library.getLastMessage());
        statusBar.setFont(new Font("SansSerif", Font.ITALIC, 12));
        statusBar.setForeground(new Color(0, 100, 0));
        add(statusBar, BorderLayout.NORTH);

        // ---- Center: tabs ----
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("📚 Books",     buildBooksPanel());
        tabs.addTab("👥 Borrowers", buildBorrowersPanel());
        tabs.addTab("🔄 Loans",     buildLoansPanel());

        // Only admin can manage staff
        if (library.getLoggedInStaff().can(LibraryService.ADD_STAFF)) {
            tabs.addTab("🔑 Staff",  buildStaffPanel());
        }

        add(tabs, BorderLayout.CENTER);

        // ---- Bottom: output area + logout ----
        outputArea = new JTextArea(6, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(outputArea);
        scroll.setBorder(BorderFactory.createTitledBorder("Output"));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            library.logout();
            loginForm.setVisible(true);
            dispose();
        });

        JPanel bottomPanel = new JPanel(new BorderLayout(4, 4));
        bottomPanel.add(scroll, BorderLayout.CENTER);
        bottomPanel.add(logoutBtn, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Refresh the output area with a message
    private void showMessage(String msg) {
        outputArea.setText(msg);
        statusBar.setText("  " + library.getLastMessage());
    }

    // ================================================================
    //  BOOKS PANEL
    // ================================================================
    private JPanel buildBooksPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input fields
        JTextField idField       = new JTextField();
        JTextField titleField    = new JTextField();
        JTextField authorField   = new JTextField();
        JTextField categoryField = new JTextField();

        panel.add(new JLabel("Book ID (number):")); panel.add(idField);
        panel.add(new JLabel("Title:"));            panel.add(titleField);
        panel.add(new JLabel("Author:"));           panel.add(authorField);
        panel.add(new JLabel("Category:"));         panel.add(categoryField);

        JButton addBtn    = new JButton("Add Book");
        JButton deleteBtn = new JButton("Delete Book");
        JButton viewBtn   = new JButton("View All Books");

        panel.add(addBtn);
        panel.add(deleteBtn);
        panel.add(viewBtn);
        panel.add(new JLabel());

        // CONCEPT: method calls triggered by button events
        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                library.addBook(id, titleField.getText(), authorField.getText(), categoryField.getText());
                showMessage(library.getLastMessage());
            } catch (NumberFormatException ex) {
                // CONCEPT: Exception handling — bad input won't crash the app
                showMessage("Error: Book ID must be a number.");
            }
        });

        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                library.deleteBook(id);
                showMessage(library.getLastMessage());
            } catch (NumberFormatException ex) {
                showMessage("Error: Book ID must be a number.");
            }
        });

        viewBtn.addActionListener(e -> {
            // CONCEPT: loop through ArrayList and build display string
            ArrayList<Book> books = library.getBooks();
            if (books.isEmpty()) {
                showMessage("No books in the library.");
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < books.size(); i++) {
                sb.append((i + 1)).append(". ").append(books.get(i)).append("\n");
            }
            showMessage(sb.toString());
        });

        return panel;
    }

    // ================================================================
    //  BORROWERS PANEL
    // ================================================================
    private JPanel buildBorrowersPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField idField    = new JTextField();
        JTextField nameField  = new JTextField();
        JTextField phoneField = new JTextField();

        panel.add(new JLabel("Borrower ID (number):")); panel.add(idField);
        panel.add(new JLabel("Full Name:"));             panel.add(nameField);
        panel.add(new JLabel("Phone:"));                 panel.add(phoneField);

        JButton addBtn  = new JButton("Add Borrower");
        JButton viewBtn = new JButton("View All Borrowers");

        panel.add(addBtn);
        panel.add(viewBtn);

        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                library.addBorrower(id, nameField.getText(), phoneField.getText());
                showMessage(library.getLastMessage());
            } catch (NumberFormatException ex) {
                showMessage("Error: Borrower ID must be a number.");
            }
        });

        viewBtn.addActionListener(e -> {
            ArrayList<Borrower> list = library.getBorrowers();
            if (list.isEmpty()) { showMessage("No borrowers."); return; }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append((i + 1)).append(". ").append(list.get(i)).append("\n");
            }
            showMessage(sb.toString());
        });

        return panel;
    }

    // ================================================================
    //  LOANS PANEL
    // ================================================================
    private JPanel buildLoansPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField bookIdField     = new JTextField();
        JTextField borrowerIdField = new JTextField();

        panel.add(new JLabel("Book ID:"));     panel.add(bookIdField);
        panel.add(new JLabel("Borrower ID:")); panel.add(borrowerIdField);

        JButton borrowBtn = new JButton("Borrow Book");
        JButton returnBtn = new JButton("Return Book");
        JButton viewBtn   = new JButton("View All Loans");

        panel.add(borrowBtn);
        panel.add(returnBtn);
        panel.add(viewBtn);
        panel.add(new JLabel());

        borrowBtn.addActionListener(e -> {
            try {
                int bookId     = Integer.parseInt(bookIdField.getText().trim());
                int borrowerId = Integer.parseInt(borrowerIdField.getText().trim());
                library.borrowBook(bookId, borrowerId);
                showMessage(library.getLastMessage());
            } catch (NumberFormatException ex) {
                showMessage("Error: IDs must be numbers.");
            }
        });

        returnBtn.addActionListener(e -> {
            try {
                int bookId     = Integer.parseInt(bookIdField.getText().trim());
                int borrowerId = Integer.parseInt(borrowerIdField.getText().trim());
                library.returnBook(bookId, borrowerId);
                showMessage(library.getLastMessage());
            } catch (NumberFormatException ex) {
                showMessage("Error: IDs must be numbers.");
            }
        });

        viewBtn.addActionListener(e -> {
            ArrayList<Loan> loans = library.getLoans();
            if (loans.isEmpty()) { showMessage("No loans recorded."); return; }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < loans.size(); i++) {
                sb.append((i + 1)).append(". ").append(loans.get(i)).append("\n");
            }
            showMessage(sb.toString());
        });

        return panel;
    }

    // ================================================================
    //  STAFF PANEL (admin only)
    // ================================================================
    private JPanel buildStaffPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField idField       = new JTextField();
        JTextField nameField     = new JTextField();
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField roleField     = new JTextField("Admin or Librarian");

        panel.add(new JLabel("Staff ID:"));   panel.add(idField);
        panel.add(new JLabel("Full Name:"));  panel.add(nameField);
        panel.add(new JLabel("Username:"));   panel.add(usernameField);
        panel.add(new JLabel("Password:"));   panel.add(passwordField);
        panel.add(new JLabel("Role:"));       panel.add(roleField);

        JButton addBtn  = new JButton("Add Staff");
        JButton viewBtn = new JButton("View All Staff");

        panel.add(addBtn);
        panel.add(viewBtn);

        addBtn.addActionListener(e -> {
            library.addStaff(idField.getText(), nameField.getText(),
                    usernameField.getText(), passwordField.getText(), roleField.getText());
            showMessage(library.getLastMessage());
        });

        viewBtn.addActionListener(e -> {
            ArrayList<Staff> list = library.getStaffList();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append((i + 1)).append(". ").append(list.get(i)).append("\n");
            }
            showMessage(sb.toString());
        });

        return panel;
    }
}
