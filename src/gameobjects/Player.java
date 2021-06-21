package gameobjects;

import javax.swing.JComponent;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
        
import gameobjects.borderobjects.*;

/**
 * A simple Player class!
 * @author Yağız Can Aslan 22001943
 * @version 01.04.2021
*/
@SuppressWarnings("serial")
public class Player extends JComponent implements ActionListener
{
    // Properties
    private final Color DARK_RED = new Color( 150, 20, 20);
    private final int WIDTH;
    private final int HEIGHT;
    private final int BORDER_SIZE = 4;
    private final int SIZE = 40;
    private final int SPEED = 12;
    private final int GRAVITY = 1;
    private final int FPS = 120;
    private ScheduledExecutorService updater;
    private Obstacle obstacle;
    private boolean particlesSpawned = false;
    private boolean gameOver;
    private String lossReason;
    private int score;
    // private Point[] points = new Point[ 4 * SIZE];
    private Point topLeftPoint;
    private Point topRightPoint;
    private Point bottomLeftPoint;
    private Point bottomRightPoint;
    private Point centerPoint;
    private Point[] borderPoints = new Point[ 5]; // TopLeft, TopRight, BottomLeft, BottomRight and Center points to check
    public Timer timer = new Timer( (1000 / FPS), this);
    public int x;
    public int y;
    public int velocityX;
    public int velocityY;
    
    // Constructors
    public Player( int width, int height, Obstacle obs) {
        WIDTH = width;
        HEIGHT = height;
        
        x = 20;
        y = (HEIGHT / 2) - (SIZE / 2);
        velocityX = SPEED;
        velocityY = GRAVITY;
        
        gameOver = false;
        score = 0;
        
        obstacle = obs;
        
        updateBorderPoints();
        
        /*
        for ( int j = 0; j < 40; j++ ) { // left border
            points[ j] = new Point( x, y + j);
        }
        
        for ( int j = 40; j < 80; j++ ) { // bottom border
            points[ j] = new Point( x + j, y + SIZE - 1);
        }
        
        for ( int j = 80; j < 120; j++ ) { // right border
            points[ j] = new Point( x + SIZE - 1, y + j);
        }
        
        for ( int j = 120; j < 160; j++ ) { // top border
            points[ j] = new Point( x + j, y);
        }
        */
        
        updater = Executors.newSingleThreadScheduledExecutor();
        updater.schedule( this::checkPos, 50, TimeUnit.MILLISECONDS);
        
        repaint();
    }
    
    
    // Methods    
    @Override
    public void paintComponent( Graphics g) {
        super.paintComponent( g);
        
        // Border
        g.setColor( Color.BLACK);
        g.fillRect( x - BORDER_SIZE, y - BORDER_SIZE, SIZE + (2 * BORDER_SIZE), SIZE + (2 * BORDER_SIZE));
        
        // Player (Maybe Graphics2D object later?)
        g.setColor( DARK_RED);
        g.fillRect( x, y, SIZE, SIZE);
        
        timer.start();
    }


	@Override
	public void actionPerformed(ActionEvent e) {
        if ( !gameOver ) {
            if ( x < 0 || x > (WIDTH - SIZE)) {
                velocityX = -velocityX;
                score++;
                obstacle.reset();
                obstacle.repaint();
            }
            
            if ( y < 0 || y > (HEIGHT - SIZE)) {
                gameLost( "OUTSIDE BORDERS");
                
                // System.exit( 0);
            }
            
            // checkBorders();
            
            y = y + velocityY;
            
            if ( velocityY < 15 * GRAVITY ) {
                velocityY = velocityY + GRAVITY; 
            }
            
            x = x + velocityX;
            
            updateBorderPoints();
        }
        
        timer.stop();
        repaint();
    }
    
    /**
     * Updates the border points of the Player character 
     * @author Yağız Can Aslan 22001943
     * @version 15.06.2021
    */
    private void updateBorderPoints() {
        topLeftPoint = new Point( x, y);
        borderPoints[ 0] = topLeftPoint;
        
        topRightPoint = new Point( x + SIZE, y);
        borderPoints[ 1] = topRightPoint;
        
        bottomLeftPoint = new Point( x, y + SIZE);
        borderPoints[ 2] = bottomLeftPoint;
        
        bottomRightPoint = new Point( x + SIZE, y + SIZE);
        borderPoints[ 3] = bottomRightPoint;
        
        centerPoint = new Point( x + (SIZE / 2), y + (SIZE / 2));
        borderPoints[ 4] = centerPoint;
        
        repaint();
    }
    
    /**
     * Increases the speed on the y-axis 
     * @author Yağız Can Aslan 22001943
     * @version 01.04.2021
    */ 
    public void increaseSpeed() {
        if ( !gameOver ) {
            velocityY = -12;
        }
    }
    
    /**
     * Getter method for the score
     * @return returns score
     * @author Yağız Can Aslan 22001943
     * @version 30.03.2021
    */
    public int getScore() {
        return score;
    }
    
    /**
     * Getter method for game loss status
     * @return true if the Player has lost the game, false otherwise
     * @author Yağız Can Aslan 22001943
     * @version 15.06.2021
    */
    public boolean hasLost() {
        return gameOver;
    }
    
    /**
     * Getter method for particle spawn status
     * @return true if the particle have spawned, false otherwise
     * @author Yağız Can Aslan 22001943
     * @version 15.06.2021
    */
    public boolean hasSpawnedParticles() {
        return particlesSpawned;
    }
    
    /**
     * Setter method for particle spawn status
     * @param bool boolean for setting the particle spawn status to
     * @author Yağız Can Aslan 22001943
     * @version 15.06.2021
    */
    public void setSpawnedParticles( boolean bool ) {
        particlesSpawned = bool;
    }
    
    /**
     * Getter method for game loss reason
     * @return the reason for losing the game
     * @author Yağız Can Aslan 22001943
     * @version 15.06.2021
    */
    public String getLosingReason() {
        return lossReason;
    }
    
    /**
     * Getter method for the x coordinate
     * @return the x coordinate
     * @author Yağız Can Aslan 22001943
     * @version 30.03.2021
    */
    public int getX() {
        return x;
    }
    
    /**
     * Getter method for the y coordinate
     * @return the y coordinate
     * @author Yağız Can Aslan 22001943
     * @version 30.03.2021
    */
    public int getY() {
        return y;
    }
    
    /**
     * Checks the borders of the player
     * @author Yağız Can Aslan 22001943
     * @version 30.03.2021
    */
    /*
    public void checkBorders() {
        for ( int i = 0; i < points.length; i++ ) {
            Point p = points[ i];
            
            if ( p.getX() > obstacle.x && p.getX() < obstacle.x + 30 ) { // check x bounds
                System.out.println( "CHECKING X COORDINATE CHECKING X COORDUNATE");
                if ( p.getY() > obstacle.getStartOfBottom() || p.getY() < obstacle.getStartOfBottom() - 120 ) { // check y bounds
                    System.out.println( "GAME LOST");
                    gameOver = true;
                    System.exit( 0);
                }
            }
        }
    }
    */
    
    /**
     * Checks if the player is inside an Obstacle or not
     * @author Yağız Can Aslan 22001943
     * @version 15.06.2021
    */
    public void checkPos() {
        if ( !gameOver ) {
            updater.schedule( this::checkPos, (1000 / FPS), TimeUnit.MILLISECONDS); // ~120 checks per second (120hz) -if the FPS constant is 120-
            
            for ( Point p : borderPoints ) {
                // Check the x-coordinate
                if ( p.getX() > obstacle.x && p.getX() < obstacle.x + obstacle.SIZE ) {
                    
                    // Check the y-coordinate
                    if ( p.getY() > obstacle.getStartOfBottom() || p.getY() < obstacle.getStartOfBottom() - obstacle.GAP ) {
                        gameLost( "OBSTACLE HIT");
                        
                        // System.exit( 0);
                    }
                }
            }
        }
    }
    
    /**
     * Sets the properties for a game loss and prints game loss statement
     * @param reason the reason for losing the game
     * @author Yağız Can Aslan 22001943
     * @version 15.06.2021
    */
    private void gameLost( String reason) {
        gameOver = true;
        
        velocityX = 0;
        velocityY = 0;
        
        lossReason = "GAME LOST: " + reason;
        
        updater.schedule( this::closeGame, 8, TimeUnit.SECONDS);
    }
    
    /**
     * Closes the application
     * @author Yağız Can Aslan 22001943
     * @version 15.06.2021
    */
    private void closeGame() {
        System.exit( 0);
    }
}