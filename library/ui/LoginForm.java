package ui;

import service.LibraryService;
import javax.swing.*;
import java.awt.*;

// ============================================================
// Simple Login Window
// ============================================================
public class LoginForm extends JFrame {

    private LibraryService library;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public LoginForm(LibraryService library) {
        this.library = library;

        setTitle("Library System - Login");
        setSize(340, 220);
        setLocationRelativeTo(null);          // center on screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 8, 8));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginBtn = new JButton("Login");
        add(loginBtn);
        add(new JLabel()); // empty cell

        messageLabel = new JLabel(library.getLastMessage());
        messageLabel.setForeground(Color.DARK_GRAY);
        add(messageLabel);

        // CONCEPT: Event (like calling a method when button is clicked)
        loginBtn.addActionListener(e -> doLogin());

        // Allow Enter key to login
        passwordField.addActionListener(e -> doLogin());

        setVisible(true);
    }

    private void doLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        library.login(username, password);
        messageLabel.setText(library.getLastMessage());

        if (library.isLoggedIn()) {
            // CONCEPT: create a new object (StaffDashboard) and pass the library reference
            new StaffDashboard(library, this);
            setVisible(false);
        }
    }
}
