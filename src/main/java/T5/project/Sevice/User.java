package T5.project.Sevice;

/**
 * CONCEPT: Abstraction + Inheritance
 * Abstract base class for all users in the library system.
 */
public abstract class User implements IUser {

    // CONCEPT: Encapsulation — all fields private
    private String userId;
    private String fullName;
    private String username;
    private String password;
    private boolean active;

    // CONCEPT: Abstraction — subclasses MUST implement this
    @Override
    public abstract boolean can(String action);

    // CONCEPT: Constructor
    public User(String userId, String fullName, String username, String password) {
        setUserId(userId);
        setFullName(fullName);
        setUsername(username);
        setPassword(password);
        this.active = true;
    }

    // CONCEPT: Access Control — Getters (public read)
    @Override
    public String getUserId()   { return userId; }

    @Override
    public String getFullName() { return fullName; }

    @Override
    public String getUsername() { return username; }

    @Override
    public boolean isActive()   { return active; }

    @Override
    public boolean checkPassword(String input) {
        return password != null && password.equals(input);
    }

    // CONCEPT: Access Control — Setters with validation
    public void setUserId(String userId) {
        if (isBlank(userId)) this.userId = "UNKNOWN";
        else this.userId = userId.trim();
    }

    public void setFullName(String fullName) {
        if (isBlank(fullName)) this.fullName = "No Name";
        else this.fullName = fullName.trim();
    }

    public void setUsername(String username) {
        if (isBlank(username)) this.username = "user_" + this.userId;
        else this.username = username.trim();
    }

    public void setPassword(String password) {
        // CONCEPT: Exception Handling — throw if invalid
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters.");
        }
        this.password = password;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // Helper
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    @Override
    public String toString() {
        return "User{userId='" + userId + "', fullName='" + fullName +
               "', username='" + username + "', active=" + active + "}";
    }
}