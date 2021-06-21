package gameobjects;

import javax.swing.JComponent;

import java.awt.Graphics;
import java.awt.Color;

/**
 * A simple Player class!
 * @author Yağız Can Aslan 22001943
 * @version 01.04.2021
*/
@SuppressWarnings("serial")
public class Obstacle extends JComponent
{
    // Properties
    private final Color BORDER_COLOR = new Color( 50, 50, 50); // Dark Gray
    private final int BORDER_SIZE = 2;
    private final int WIDTH;
    private final int HEIGHT;
    public final int SIZE = 30;
    public final int GAP = 190;
    private int startOfBottom;
    private boolean gameOver;
    public int x;
    public int y;
    
    // Constructors
    public Obstacle( int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        
        x = (WIDTH / 2) - (SIZE / 2);
        y = (WIDTH / 2) - (SIZE / 2);
        
        gameOver = false;
        startOfBottom = ((int) (Math.random() * (HEIGHT - (2 * GAP)))) + (2 * GAP);
        
        repaint();
    }
    
    
    // Methods    
    @Override
    public void paintComponent( Graphics g) {
        super.paintComponent( g);
        
        // Obstacle Border
        g.setColor( BORDER_COLOR);
        g.fillRect( x - BORDER_SIZE, 0, SIZE + (2 * BORDER_SIZE), startOfBottom - GAP + BORDER_SIZE);
        
        g.fillRect( x - BORDER_SIZE, startOfBottom - BORDER_SIZE, SIZE + (2 * BORDER_SIZE), HEIGHT);
        
        // Obstacle
        g.setColor( Color.BLACK);
        g.fillRect( x, 0, SIZE, startOfBottom - GAP);
        
        g.fillRect( x, startOfBottom, SIZE, HEIGHT);
        // System.out.println( startOfBottom);
    }
    
    /**
     * Resets the obstacle place 
     * @author Yağız Can Aslan 22001943
     * @version 01.04.2021
    */ 
    public void reset() {
        
        boolean coin = false;
        
        if ( Math.random() > 0.5 ) {
            coin = true;
        }
        
        if ( !gameOver ) {
            startOfBottom = ((int) (Math.random() * (HEIGHT - (2 * GAP)))) + (2 * GAP);
            
            if ( coin ) {
                startOfBottom = HEIGHT - startOfBottom + ((3 * GAP) / 2);
            }
            
            repaint();
        }
    }
    
    // Methods
    /**
     * Getter method for the start of the bottom obstacle
     * @return the start of the bottom obstacle
     * @author Yağız Can Aslan 22001943
     * @version 30.03.2021
    */
    public int getStartOfBottom() {
        return startOfBottom;
    }
}