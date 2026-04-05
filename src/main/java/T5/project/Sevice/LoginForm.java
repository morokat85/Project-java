package T5.project.Sevice;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import T5.project.Book.BorrowerDashboard;


public class LoginForm extends JFrame {

    public LoginForm() {
        setTitle("Library Login");
        setSize(300,200);
        setLayout(null);

        JButton staffBtn = new JButton("Login as Staff");
        staffBtn.setBounds(50,30,180,30);
        add(staffBtn);

        JButton borrowerBtn = new JButton("Login as Borrower");
        borrowerBtn.setBounds(50,80,180,30);
        add(borrowerBtn);

        staffBtn.addActionListener(e -> loginStaff());
        borrowerBtn.addActionListener(e -> loginBorrower());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loginStaff() {
        String username = JOptionPane.showInputDialog("Username:");
        String password = JOptionPane.showInputDialog("Password:");
        if (AuthService.loginStaff(username, password)) {
            JOptionPane.showMessageDialog(this,"Staff login successful!");
            new StaffDashboard();
            dispose();
        } else JOptionPane.showMessageDialog(this,"Invalid credentials!");
    }

    private void loginBorrower() {
        try {
            int borrowerId = Integer.parseInt(JOptionPane.showInputDialog("Enter Borrower ID:"));
            if (AuthService.loginBorrower(borrowerId)) {
                JOptionPane.showMessageDialog(this,"Borrower login successful!");
                new BorrowerDashboard(borrowerId);
                dispose();
            } else JOptionPane.showMessageDialog(this,"Borrower ID not found!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,"Invalid ID format!");
        }
    }
}
