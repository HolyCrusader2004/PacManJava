import javax.swing.*;

/**
 * In this class, an object of the game type is declared, and a window in which the game will take place is initialized.
 * @author Ricu Razvan
 */
public class PacMan extends JFrame {
    /**
     * A newJoc class is being added in this class.
     */
    public PacMan(){
        add(new Joc());
    }

    /**
     * The usual syntax for the main class.
     * @param args arguments
     */
    public static void main(String[] args) {
        PacMan x =new PacMan();
        x.setTitle("PacMan");
        x.setSize(810,650);
        x.setDefaultCloseOperation(EXIT_ON_CLOSE);
        x.setVisible(true);
    }
}