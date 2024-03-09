import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * In the Game class, the logic behind the Pacman game is written, covering aspects from drawing and movement to the design of the game map.
 * @author Ricu Razvan
 */
public class Joc extends JPanel implements ActionListener {
    /**
     * I check whether the game runs or no.
     */
    private int jocul_ruleaza=0;
    /**
     * Direction 1
     */
    private int directie_oxpoz;
    /**
     * Direction 2
     */
    private int directie_oypoz;
    /**
     * Direction 3
     */
    private int directie_oxneg;
    /**
     * Direction 4
     */
    private int directie_oyneg;
    /**
     * board height
     */
    private static final int boardheight=20;
    /**
     * board width
     */
    private static final int boardwidth=27;
    /**
     * size of a block
     */
    private static final int size=30;
    /**
     * screen width
     */
    private static final int screen_width = boardwidth*size;
    /**
     * screen height
     */
    private static final int screen_height = boardheight*size;
    /**
     * Image 1
     */
    private static final Image pacman_sus = new ImageIcon("D:\\OOP\\PacManjava\\pacman_up.gif").getImage();
    private static final Image pacman_jos = new ImageIcon("D:\\OOP\\PacManjava\\pacman_down.gif").getImage();
    private static final Image pacman_stanga =new ImageIcon("D:\\OOP\\PacManjava\\pacman_left.gif").getImage();
    private static final Image pacman_dreapta = new ImageIcon("D:\\OOP\\PacManjava\\pacman_right.gif").getImage();
    private static final Image ghost=new ImageIcon("D:\\OOP\\PacManjava\\ghost.gif").getImage();
    private static final Image life = new ImageIcon("D:\\OOP\\PacManjava\\heart.gif").getImage();
    private static final Image portal= new ImageIcon("D:\\OOP\\PacManjava\\portal.gif").getImage();
    private static final Image orb =new ImageIcon("D:\\OOP\\PacManjava\\orb.gif").getImage();
    /**
     * Timer
     */
    private Timer Timer;
    /**
     * x coordinate of pacman
     */
    private int cordx_pac;
    /**
     * y coordinate of pacman
     */
    private int cordy_pac;
    /**
     * score
     */
    private int score;
    /**
     * number of lives
     */
    private int nroflives;
    /**
     * pacmans speed
     */
    private int pacmanspeed;
    /**
     * ghost speed
     */
    private int ghostspeed;
    /**
     * check if pacman is invincible
     */
    private int invincibil;
    /**
     * check if death
     */
    private int moarte;
    /**
     * array for storing the game progression and its design
     */
    private int []mapping_game;
    private static final int nrghosts=4;
    /**
     * how many tiles moved in same direction for each ghost
     */
    private int []tilesmovedinsamedirection;
    /**
     * cord x ghost
     */
    private int []cordx_ghost;
    /**
     * cord y ghost
     */
    private int []cordy_ghost;
    /**
     * direction 1 ghost
     */
    private int []directieox_pozghost;
    /**
     * direction 2 ghost
     */
    private int []directieox_negghost;
    /**
     * direction 3 ghost
     */
    private int []directieoy_pozghost;
    /**
     * direction 4 ghost
     */
    private int []directieoy_negghost;
    /**
     * check if ghost alive
     */
    private int []ghost_alive;
    /**
     * time passed while pacman is invincible
     */
    private int invincibiltime;
    /**
     * game layout
     */
    private final int[] mapping = {
            9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
            9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9,
            9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 9,
            9, 0, 0, 9, 1, 0, 0, 2, 0, 0, 1, 1, 1, 1, 1, 0, 0, 2, 0, 0, 9, 0, 0, 2, 0, 10, 9,
            9, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 2, 0, 0, 9, 0, 10, 9,
            9, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 9, 0, 0, 0, 0, 10, 9,
            9, 0, 0, 9, 1, 0, 0, 2, 0, 0, 1, 9, 1, 1, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 9,
            9, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 9,
            9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 9, 0, 0, 10, 9,
            9, 0, 0, 0, 0, 0, 0, 9, 1, 9, 1, 1, 0, 0, 9, 0, 0, 1, 9, 0, 0, 0, 0, 9, 0, 10, 9,
            6, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 5,
            6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 5,
            9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9, 1, 1, 0, 0, 9, 1, 1, 1, 1, 0, 0, 2, 0, 10, 9,
            9, 0, 0, 1, 1, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 9, 0, 10, 9,
            9, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 10, 9, 0, 10, 9,
            9, 0, 0, 0, 0, 0, 0, 9, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 10, 9, 0, 0,10, 9,
            9, 0, 0, 1, 1, 0, 0, 2, 0, 0, 0, 9, 1, 1, 0, 0, 0, 0, 0, 9, 1, 1, 0, 0, 0, 10, 9,
            9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 9,
            9, 0, 13, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 13, 10, 9,
            9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
    };

    /**
     *This is a constructor that takes no parameters and initializes a new key listener along with the actual game.
     */
    public Joc(){
        addKeyListener(new InputTastatura());
        setFocusable(true);
        initgame();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    /**
     * This class, along with the keyPressed subroutine, handles user keyboard input and updates the directions of Pacman. To start the game, the SPACE key is pressed, and to reset it, the E key is pressed.
     * @author Ricu Razvan
     */
    class InputTastatura extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int cheie_apasata=e.getKeyCode();
            if(jocul_ruleaza!=0){
                if(cheie_apasata == KeyEvent.VK_LEFT){
                    directie_oxpoz=0;
                    directie_oxneg=1;
                    directie_oypoz=0;
                    directie_oyneg=0;
                }
                if(cheie_apasata == KeyEvent.VK_RIGHT){
                    directie_oxpoz=1;
                    directie_oxneg=0;
                    directie_oypoz=0;
                    directie_oyneg=0;
                }
                if(cheie_apasata == KeyEvent.VK_UP){
                    directie_oxpoz=0;
                    directie_oxneg=0;
                    directie_oypoz=0;
                    directie_oyneg=1;
                }
                if(cheie_apasata == KeyEvent.VK_DOWN){
                    directie_oxpoz=0;
                    directie_oxneg=0;
                    directie_oypoz=1;
                    directie_oyneg=0;
                }
                if(cheie_apasata == KeyEvent.VK_E && Timer.isRunning()){
                   jocul_ruleaza=1;
                   initgame();
                }
            } else if (cheie_apasata == KeyEvent.VK_SPACE) {
                jocul_ruleaza=1;
            }
        }
    }

    /**
     *This subroutine represents the basic structure of the game, checking the score, the number of lives, and the game state (whether it is running or not). Based on these parameters, the game generation continues or stops.
     * @param g is of type graphics and with it the game and screens are drawn.
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,screen_width+50,screen_height+50);
        if(jocul_ruleaza == 1){
            board(g);
            score(g);
            game(g);
        }else{
            if(score>=336){
                done(g);
            }else {
                if (nroflives == 0) {
                    String mesaj = "YOU DIED";
                    String mesajscor = "Your score was: " + score;
                    g.setColor(Color.GREEN);
                    g.drawString(mesaj, screen_width / 2 - 70, screen_height / 2 - 50);
                    g.drawString(mesajscor, screen_width / 2 - 90, screen_height / 2);
                    jocul_ruleaza = 0;
                } else {
                    intro(g);
                }
            }
        }
    }
    /**
     * This subroutine draws the game map on the screen. If the value is 0, a dot is drawn. For values 1 and 2, two rectangles are drawn vertically and horizontally.
     * For 9, a block is drawn. Values 5 and 6 represent two portals. If the value is 10, an open space is drawn, and for 13, a "power pellet" or "orb" is drawn.
     * @param g is of type graohics and with it the game and screens are drawn.
     */
    public void board(Graphics g) {
        int aux = 0;
        for (int i = 0; i < screen_height; i += size) {
            for (int j = 0; j < screen_width; j += size) {
                g.setColor(Color.GREEN);
                if (mapping_game[aux] == 0) {
                    g.setColor(Color.yellow);
                    g.fillOval(j + 10, i + 10, 6, 6);
                }
                if (mapping_game[aux] == 1) {
                    g.fillRect(j, i + size / 2 - 5, size, 25 / 2);
                }
                if (mapping_game[aux] == 2) {
                    g.fillRect(j + size / 2 - 5, i, size / 2, size);
                }
                if (mapping_game[aux] == 9) {
                    g.fillRect(j, i, size, size);
                }
                if(mapping_game[aux]==5){
                    g.drawImage(portal,j,i,this);
                }
                if(mapping_game[aux]==6){
                    g.drawImage(portal,j,i,this);
                }
                if(mapping_game[aux]==10){
                    g.setColor(Color.BLACK);
                    g.fillRect(j,i,size,size);
                }
                if(mapping_game[aux]==13){
                    g.drawImage(orb,j,i,this);
                }
                aux++;
            }
        }
    }
    /**
     * In this subroutine, all the positions of the ghosts and Pacman are initialized, and the initial map of the game is copied into the mapping_game to avoid modifying the original design of the level.
     */
    public void initgame(){
        mapping_game=new int[screen_height * screen_width];
        for(int i=0; i < screen_height * screen_width; i++){
            mapping_game[i] = mapping[i % mapping.length];
        }
        ghost_alive=new int[4];
        cordx_ghost=new int[4];
        cordy_ghost=new int[4];
        directieox_negghost=new int[4];
        directieoy_negghost=new int[4];
        directieox_pozghost=new int[4];
        directieoy_pozghost=new int[4];
        tilesmovedinsamedirection=new int[4];
        nroflives=3;
        score=0;
        pacmanspeed=3;
        ghostspeed=4;
        invincibil=0;
        Timer = new Timer(40, this);
        Timer.start();
        cordx_pac=21*size-2;
        cordy_pac=15*size-2;
        cordx_ghost[0]=2*size-2;
        cordx_ghost[1]=9*size-2;
        cordx_ghost[2]=16*size-2;
        cordx_ghost[3]=21*size-2;
        invincibiltime=0;
        for(int i=0;i<nrghosts;i++){
            cordy_ghost[i]=2*size-2;
            ghost_alive[i]=1;
        }
        directie_oxpoz=directie_oxneg=directie_oyneg=directie_oypoz=0;
        moarte=0;
    }
    /**
     *This subroutine draws the score and updates it on the screen.
     * @param g is of type graohics and with it the game and screens are drawn.
     */
    public void score(Graphics g){
        String mesaj="SCORE:";
        g.drawString(mesaj+score,630,620);
        for(int i=0;i<nroflives;i++){
            g.drawImage(life,10+i*30,610,this);
        }
    }
    /**
     *This subroutine draws PacMan based on its current movement direction. For a more visually appealing interface, PacMan is depicted as a GIF with a small animation.
     * @param g is of type graohics and with it the game and screens are drawn.
     */
    public void deseneaza_pacman(Graphics g) {
            if (directie_oxpoz == 1) {
                g.drawImage(pacman_dreapta, cordx_pac+1, cordy_pac+1, this);
            }
            if (directie_oxneg == 1) {
                g.drawImage(pacman_stanga, cordx_pac + 1, cordy_pac + 1, this);
            }
            if (directie_oyneg == 1) {
                g.drawImage(pacman_sus, cordx_pac + 1, cordy_pac + 1, this);
            }
            if (directie_oypoz == 1) {
                g.drawImage(pacman_jos, cordx_pac + 1, cordy_pac + 1, this);
            }
            if(directie_oxneg==0&&directie_oxpoz==0&&directie_oyneg==0&&directie_oypoz==0){
                g.drawImage(pacman_dreapta, cordx_pac+1, cordy_pac+1, this);
            }
    }

    /**
     * In this subroutine, the collision of PacMan with various obstacles is checked in all four directions: positive x, negative x, positive y, and negative y. The coordinates of PacMan are then updated accordingly.
     */
    public void movepac() {
        int nextX = cordx_pac + pacmanspeed * directie_oxpoz - pacmanspeed * directie_oxneg;
        int nextY = cordy_pac + pacmanspeed * directie_oypoz - pacmanspeed * directie_oyneg;

        int currentSquareX = cordx_pac / size;
        int currentSquareY = cordy_pac / size;

        int pacmanWidth = 30;
        int pacmanHeight = 30;

        boolean canMoveX = true;
        boolean canMoveY = true;
        if(mapping_game[currentSquareX+boardwidth*currentSquareY]==0){
            score++;
            mapping_game[currentSquareX+boardwidth*currentSquareY]=10;
        }
        if(mapping_game[currentSquareX+boardwidth*currentSquareY]==13){
            invincibil=1;
            invincibiltime=0;
            mapping_game[currentSquareX+boardwidth*currentSquareY]=10;
        }
        invincibiltime++;
        if(invincibiltime>=500){
            invincibil=0;
            invincibiltime=0;
        }
        if(mapping_game[currentSquareX+boardwidth*currentSquareY]==5){
            cordx_pac=size;
            cordy_pac=size;
        }else {
            if (mapping_game[currentSquareX + boardwidth * currentSquareY + 1] == 6) {
                cordx_pac = 25 * size;
                cordy_pac = size;
            }
        }
        if (directie_oxpoz == 1) {
            int nextSquareRightIndex = currentSquareX + 1 + boardwidth * currentSquareY;
            if ((nextX + size + pacmanWidth) % size != 0) {
                canMoveX = mapping_game[nextSquareRightIndex] != 9 && mapping_game[nextSquareRightIndex + boardwidth] != 9&&mapping_game[nextSquareRightIndex] != 1 && mapping_game[nextSquareRightIndex + boardwidth] != 1&&mapping_game[nextSquareRightIndex] != 2 && mapping_game[nextSquareRightIndex + boardwidth] != 2;
            }
        } else if (directie_oxneg == 1) {
            int nextSquareLeftIndex = currentSquareX + boardwidth * currentSquareY;
            if ((nextX + pacmanWidth) % size != 0) {
                canMoveX = mapping_game[nextSquareLeftIndex] != 9 && mapping_game[nextSquareLeftIndex + boardwidth] != 9&& mapping_game[nextSquareLeftIndex] != 1 && mapping_game[nextSquareLeftIndex + boardwidth] != 1&& mapping_game[nextSquareLeftIndex] != 2 && mapping_game[nextSquareLeftIndex + boardwidth] != 2;
            }
        }

        if (directie_oypoz == 1) {
            int nextSquareBottomIndex = currentSquareX + boardwidth * (currentSquareY + 1);
            if ((nextY + size + pacmanHeight) % size != 0) {
                canMoveY = mapping_game[nextSquareBottomIndex] != 9 && mapping_game[nextSquareBottomIndex + 1] != 9 &&mapping_game[nextSquareBottomIndex] != 1 && mapping_game[nextSquareBottomIndex + 1] != 1&&mapping_game[nextSquareBottomIndex] != 2 && mapping_game[nextSquareBottomIndex + 1] != 2;
            }
        } else if (directie_oyneg == 1) {
            int nextSquareTopIndex = currentSquareX + boardwidth * (currentSquareY);
            if ((nextY + pacmanHeight) % size != 0) {
                canMoveY = mapping_game[nextSquareTopIndex] != 9 && mapping_game[nextSquareTopIndex + 1] != 9&&mapping_game[nextSquareTopIndex] != 1 && mapping_game[nextSquareTopIndex + 1] != 1&&mapping_game[nextSquareTopIndex] != 2 && mapping_game[nextSquareTopIndex + 1] != 2;
            }
        }
        if (canMoveX) {
            cordx_pac = nextX;
        } else {
                cordx_pac = cordx_pac - 3 * (directie_oxpoz - directie_oxneg);
                directie_oxpoz = directie_oxneg = 0;
        }

        if (canMoveY) {
            cordy_pac = nextY;
        } else {
                cordy_pac = cordy_pac - 3 * (directie_oypoz - directie_oyneg);
                directie_oypoz = directie_oyneg = 0;
        }
    }

    /**
     * This subroutine represents the fundamental principle on which the game operates. If Pacman dies, the coordinates of the ghosts are reinitialized along with those of Pacman, and one life out of the three is deducted.
     * If Pacman has no lives left, the game stops running; otherwise, it continues with Pacman's movement, drawing it, moving the ghosts, and checking if there are any more dots to collect on the map.
     * @param g is of type graohics and with it the game and screens are drawn.
     */
    public void game(Graphics g){
        if(moarte==1){
            nroflives--;
            moarte=0;
            cordx_pac=21*size-2;
            cordy_pac=15*size-2;
            cordx_ghost[0]=2*size-2;
            cordx_ghost[1]=9*size-2;
            cordx_ghost[2]=16*size-2;
            cordx_ghost[3]=21*size-2;

            for(int i=0;i<nrghosts;i++){
                cordy_ghost[i]=2*size-2;
            }
         if(nroflives==0){
             jocul_ruleaza=0;
         }
        }else{
            movepac();
            deseneaza_pacman(g);
            moveghosts(g);
            done(g);
        }
    }

    /**
     *This method checks the collisions of each ghost and updates their positions. After 100 steps in the same direction, it randomly calculates a new direction, and the process repeats.
     * @param g is of type graohics and with it the game and screens are drawn.
     */
    public void moveghosts(Graphics g){

        for(int i=0;i<nrghosts;i++){
            int nextX = cordx_ghost[i] + ghostspeed * directieox_pozghost[i] - ghostspeed * directieox_negghost[i];
            int nextY = cordy_ghost[i] + ghostspeed * directieoy_pozghost[i] - ghostspeed * directieoy_negghost[i];

            int currentSquareX = cordx_ghost[i] / size;
            int currentSquareY = cordy_ghost[i] / size;

            int ghostwidth = 22;
            int ghostheight = 22;

            boolean canMoveX = true;
            boolean canMoveY = true;

            if (directieox_pozghost[i] == 1 && ghost_alive[i]==1) {
                int nextSquareRightIndex = currentSquareX + 1 + boardwidth * currentSquareY;
                if ((nextX + size + ghostwidth) % size != 0) {
                    canMoveX = mapping_game[nextSquareRightIndex] != 9 && mapping_game[nextSquareRightIndex + boardwidth] != 9&&mapping_game[nextSquareRightIndex] != 1 && mapping_game[nextSquareRightIndex + boardwidth] != 1&&mapping_game[nextSquareRightIndex] != 2 && mapping_game[nextSquareRightIndex + boardwidth] != 2;
                }
            } else if (directieox_negghost[i] == 1&& ghost_alive[i]==1) {
                int nextSquareLeftIndex = currentSquareX + boardwidth * currentSquareY;
                if ((nextX + ghostwidth) % size != 0) {
                    canMoveX = mapping_game[nextSquareLeftIndex] != 9 && mapping_game[nextSquareLeftIndex + boardwidth] != 9&& mapping_game[nextSquareLeftIndex] != 1 && mapping_game[nextSquareLeftIndex + boardwidth] != 1&& mapping_game[nextSquareLeftIndex] != 2 && mapping_game[nextSquareLeftIndex + boardwidth] != 2;
                }
            }

            if (directieoy_pozghost[i] == 1&& ghost_alive[i]==1) {
                int nextSquareBottomIndex = currentSquareX + boardwidth * (currentSquareY + 1);
                if ((nextY + size + ghostheight) % size != 0) {
                    canMoveY = mapping_game[nextSquareBottomIndex] != 9 && mapping_game[nextSquareBottomIndex + 1] != 9 &&mapping_game[nextSquareBottomIndex] != 1 && mapping_game[nextSquareBottomIndex + 1] != 1&&mapping_game[nextSquareBottomIndex] != 2 && mapping_game[nextSquareBottomIndex + 1] != 2;
                }
            } else if (directieoy_negghost[i] == 1&& ghost_alive[i]==1) {
                int nextSquareTopIndex = currentSquareX + boardwidth * (currentSquareY);
                if ((nextY + ghostheight) % size != 0) {
                    canMoveY = mapping_game[nextSquareTopIndex] != 9 && mapping_game[nextSquareTopIndex + 1] != 9&&mapping_game[nextSquareTopIndex] != 1 && mapping_game[nextSquareTopIndex + 1] != 1&&mapping_game[nextSquareTopIndex] != 2 && mapping_game[nextSquareTopIndex + 1] != 2;
                }
            }
            if (canMoveX&& ghost_alive[i]==1) {
                cordx_ghost[i] = nextX;
                tilesmovedinsamedirection[i]++;
            } else {
                cordx_ghost[i] = cordx_ghost[i] - 5 * (directieox_pozghost[i] - directieox_negghost[i]);
                directieox_pozghost[i]=directieox_negghost[i]=0;
            }

            if (canMoveY&& ghost_alive[i]==1) {
                cordy_ghost[i] = nextY;
                tilesmovedinsamedirection[i]++;
            } else {
                cordy_ghost[i] =  cordy_ghost[i] - 5 * (directieoy_pozghost[i] - directieoy_negghost[i]);
                directieoy_pozghost[i] = directieoy_negghost[i] = 0;
            }

            if (tilesmovedinsamedirection[i] >= 100 && ghost_alive[i]==1) {
                Random rand = new Random();
                int random = rand.nextInt(4);
                if (random == 0) {
                    directieox_pozghost[i] = 1;
                    directieoy_pozghost[i] = directieox_negghost[i] = directieoy_negghost[i] = 0;
                } else if (random == 1) {
                    directieox_negghost[i] = 1;
                    directieoy_pozghost[i] = directieox_pozghost[i] = directieoy_negghost[i] = 0;
                } else if (random == 2) {
                    directieoy_pozghost[i] = 1;
                    directieox_negghost[i] = directieox_pozghost[i] = directieoy_negghost[i] = 0;
                } else {
                    directieoy_negghost[i] = 1;
                    directieoy_pozghost[i] = directieox_pozghost[i] = directieox_negghost[i] = 0;
                }
                tilesmovedinsamedirection[i] = 0;
            }
            if (cordx_pac + 22 >= cordx_ghost[i] && cordx_pac <= cordx_ghost[i] + ghostwidth && cordy_pac + 22 >= cordy_ghost[i] && cordy_pac <= cordy_ghost[i] + ghostheight && invincibil==0) {
                moarte = 1;
            }
            if (cordx_pac + 22 >= cordx_ghost[i] && cordx_pac <= cordx_ghost[i] + ghostwidth && cordy_pac + 22 >= cordy_ghost[i] && cordy_pac <= cordy_ghost[i] + ghostheight && invincibil==1 && ghost_alive[i]==1) {
                score+=5;
                ghost_alive[i]=0;
            }
            if(invincibil==0){
                ghost_alive[i]=1;
            }
            if(ghost_alive[i]==1) {
                g.drawImage(ghost, cordx_ghost[i], cordy_ghost[i], this);
            }
        }
    }

    /**
     * This method checks if the score is above a certain threshold, and in this case, it stops the game, displaying a winning screen.
     * @param g is of type graohics and with it the game and screens are drawn.
     */
    public void done(Graphics g){
        if(score>=336) {
            String mesaj2 = "YOU WIN";
            String mesajscor2 = "Your score was: " + score;
            g.setColor(Color.GREEN);
            g.drawString(mesaj2, screen_width / 2 - 70, screen_height / 2 - 50);
            g.drawString(mesajscor2, screen_width / 2 - 90, screen_height / 2);
            jocul_ruleaza = 0;
        }
    }

    /**
     * This method displays the game's intro screen, advising the user to press the SPACE key to start the game.
     * @param g is of type graohics and with it the game and screens are drawn.
     */
    public void intro(Graphics g){
        String mesaj3 ="Press Space to start";
        g.setColor(Color.GREEN);
        g.drawString(mesaj3,screen_width/2-70,screen_height/2-50);
    }
}
