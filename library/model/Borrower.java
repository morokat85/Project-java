package model;

// ============================================================
// CONCEPT: Another class — shows how multiple classes work together
// Borrower is a separate "type" from Staff. Different purpose.
// ============================================================
public class Borrower {

    private int borrowerId;   // primitive int
    private String fullName;  // reference type
    private String phone;
    private boolean active;

    // CONCEPT: Constructor
    public Borrower(int borrowerId, String fullName, String phone) {
        setBorrowerId(borrowerId);
        setFullName(fullName);
        setPhone(phone);
        this.active = true;
    }

    // ===== Getters =====
    public int getBorrowerId()  { return borrowerId; }
    public String getFullName() { return fullName; }
    public String getPhone()    { return phone; }
    public boolean isActive()   { return active; }

    // ===== Setters =====
    public void setBorrowerId(int borrowerId) {
        if (borrowerId <= 0) this.borrowerId = 0;
        else this.borrowerId = borrowerId;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) this.fullName = "No Name";
        else this.fullName = fullName.trim();
    }

    public void setPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) this.phone = "N/A";
        else this.phone = phone.trim();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Borrower #" + borrowerId + " | " + fullName + " | " + phone + " | active=" + active;
    }
}
