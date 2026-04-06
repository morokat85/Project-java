package T5.project.Sevice;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import T5.project.Book.BorrowerDashboard;

public class LoginForm extends JFrame {

    public LoginForm() {
        setTitle("Library Login");
        setSize(300, 200);
        setLayout(null);

        JButton staffBtn = new JButton("Login as Staff");
        staffBtn.setBounds(50, 30, 180, 30);
        add(staffBtn);

        JButton borrowerBtn = new JButton("Login as Borrower");
        borrowerBtn.setBounds(50, 80, 180, 30);
        add(borrowerBtn);

        staffBtn.addActionListener(e -> loginStaff());
        borrowerBtn.addActionListener(e -> loginBorrower());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loginStaff() {
        // Accept either username OR staffId (e.g. "admin" or "L001")
        String username = JOptionPane.showInputDialog(this, "Username or Staff ID:");
        if (username == null) return;

        String password = JOptionPane.showInputDialog(this, "Password:");
        if (password == null) return;

        if (AuthService.loginStaff(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            // Pass credentials so StaffDashboard can log into Library too
            new StaffDashboard(username, password);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!");
        }
    }

    private void loginBorrower() {
        try {
            String input = JOptionPane.showInputDialog(this, "Enter Borrower ID:");
            if (input == null) return;
            int borrowerId = Integer.parseInt(input.trim());

            if (AuthService.loginBorrower(borrowerId)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                new BorrowerDashboard(borrowerId);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Borrower ID not found!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID format!");
        }
    }
}