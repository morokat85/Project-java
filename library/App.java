import service.LibraryService;
import ui.LoginForm;
import javax.swing.SwingUtilities;

// ============================================================
// Entry point — creates ONE LibraryService (the data brain)
// and passes it to the LoginForm (the first window).
//
// CONCEPT: One object (library) is shared across all windows.
// This is a "reference type" — windows point to the same data.
// ============================================================
public class App {
    public static void main(String[] args) {
        // CONCEPT: Create one service object (reference type)
        LibraryService library = new LibraryService();

        // Run GUI on the Event Dispatch Thread (Swing best practice)
        SwingUtilities.invokeLater(() -> new LoginForm(library));
    }
}
