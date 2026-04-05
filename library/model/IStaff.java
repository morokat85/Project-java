package model;

// ============================================================
// CONCEPT: Interface
// An interface is a "contract" - any class that implements it
// MUST provide these methods. It has no fields, no constructor.
// ============================================================
public interface IStaff {
    String getStaffId();
    String getFullName();
    String getUsername();
    boolean isActive();
    boolean checkPassword(String input);

    // Every staff type must define what actions they can do
    boolean can(String action);
}
