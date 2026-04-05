package model;

import service.LibraryService;

// ============================================================
// CONCEPT: Inheritance again — same parent, different behavior
// Librarian can only do book and loan related actions.
// This shows POLYMORPHISM: same method "can()" behaves differently
// depending on which child class is being used.
// ============================================================
public class LibrarianStaff extends Staff {

    private String shift; // "Morning" or "Evening"

    public LibrarianStaff(String staffId, String fullName, String username, String password, String shift) {
        super(staffId, fullName, username, password);
        setShift(shift);
    }

    // CONCEPT: Overriding — Librarian has limited permissions
    @Override
    public boolean can(String action) {
        // CONCEPT: loop through allowed actions using switch
        switch (action) {
            case LibraryService.ADD_BOOK:
            case LibraryService.DELETE_BOOK:
            case LibraryService.VIEW_BOOKS:
            case LibraryService.BORROW_BOOK:
            case LibraryService.RETURN_BOOK:
            case LibraryService.VIEW_LOANS:
                return true;
            default:
                return false; // cannot manage staff
        }
    }

    public String getShift() { return shift; }

    public void setShift(String shift) {
        if (shift == null || shift.trim().isEmpty())
            this.shift = "Morning";
        else
            this.shift = shift.trim();
    }

    @Override
    public String toString() {
        return super.toString() + " | Role: Librarian | Shift: " + shift;
    }
}
