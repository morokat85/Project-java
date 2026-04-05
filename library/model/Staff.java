package model;

// ============================================================
// CONCEPT: Abstract Class + Inheritance
// - "abstract" means you CANNOT do: new Staff(...)
// - It gives shared fields/methods to child classes
// - "implements IStaff" means it fulfills the interface contract
// ============================================================
public abstract class Staff implements IStaff {

    // CONCEPT: Access Control - private fields (encapsulation)
    // Only accessible through getters/setters
    private String staffId;
    private String fullName;
    private String username;
    private String password;
    private boolean active;   // CONCEPT: primitive boolean (not Boolean object)

    // CONCEPT: Constructor
    public Staff(String staffId, String fullName, String username, String password) {
        setStaffId(staffId);
        setFullName(fullName);
        setUsername(username);
        setPassword(password);
        this.active = true; // new staff is active by default
    }

    // CONCEPT: Abstract method
    // Child classes MUST override this — each role has different permissions
    @Override
    public abstract boolean can(String action);

    // =====================
    // GETTERS (public read)
    // =====================
    @Override public String getStaffId()  { return staffId; }
    @Override public String getFullName() { return fullName; }
    @Override public String getUsername() { return username; }
    @Override public boolean isActive()   { return active; }

    // CONCEPT: method that uses private field without exposing it
    @Override
    public boolean checkPassword(String input) {
        return password != null && password.equals(input);
    }

    // =====================
    // SETTERS (with validation)
    // =====================
    public void setStaffId(String staffId) {
        if (staffId == null || staffId.trim().isEmpty())
            this.staffId = "UNKNOWN";
        else
            this.staffId = staffId.trim();
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty())
            this.fullName = "No Name";
        else
            this.fullName = fullName.trim();
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty())
            this.username = "user_" + staffId;
        else
            this.username = username.trim();
    }

    public void setPassword(String password) {
        // CONCEPT: condition - enforce minimum password length
        if (password == null || password.length() < 4)
            this.password = "0000";
        else
            this.password = password;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // =====================
    // toString
    // =====================
    @Override
    public String toString() {
        return "[" + staffId + "] " + fullName + " (@" + username + ") active=" + active;
    }
}
