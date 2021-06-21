package gameobjects;

import javax.swing.JComponent;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A simple Particle class!
 * @author Yağız Can Aslan 22001943
 * @version 01.04.2021
*/
@SuppressWarnings("serial")
public class Particle extends JComponent implements ActionListener
{
    // Properties
    private final Color DARK_RED = new Color( 150, 20, 20);
    private final int HEIGHT;
    private final int BORDER_SIZE = 1;
    private final int SIZE = 8;
    private final int SPEED = 12;
    private final int GRAVITY = 1;
    private final int FPS = 120;
    public Timer timer = new Timer( (1000 / FPS), this);
    public int x;
    public int y;
    public int velocityX;
    public int velocityY;
    
    // Constructors
    public Particle( int x, int y, int height) {
        HEIGHT = height;
        
        this.x = x;
        this.y = y;
        velocityX = randomNum( 5 * SPEED);
        velocityY = randomNum( 80 * GRAVITY);
        
        repaint();
    }
    
    
    // Methods    
    @Override
    public void paintComponent( Graphics g) {
        if ( y < HEIGHT + (2 * SIZE) ) {
            super.paintComponent( g);
            
            // Border
            g.setColor( Color.BLACK);
            g.fillRect( x - BORDER_SIZE, y - BORDER_SIZE, SIZE + (2 * BORDER_SIZE), SIZE + (2 * BORDER_SIZE));
            
            // Particle (Maybe Graphics2D object later?)
            g.setColor( DARK_RED);
            g.fillRect( x, y, SIZE, SIZE);
            
            timer.start();
        }
    }


	@Override
	public void actionPerformed(ActionEvent e) {
        y = y + velocityY;
        
        if ( velocityY < 15 * GRAVITY ) {
            velocityY = velocityY + GRAVITY; 
        }
        
        x = x + velocityX;
        
        timer.stop();
        repaint();
    }
    
    /**
     * Generates a random number between the
     * negative value of the given parameter and
     * the positive value of the given parameter 
     * @param num the number parameter
     * @return a random number between the negative
     * value of the given parameter and the positive
     * value of the given parameter
     * @author Yağız Can Aslan 22001943
     * @version 17.06.2021
    */ 
    private int randomNum( int num ) {
        int random = (int) (Math.random() * (num + 1));
        
        if ( Math.random() > 0.5 ) {
            random = -random;
        }
        
        return random;
    }
    
    /**
     * Increases the speed on the y-axis 
     * @author Yağız Can Aslan 22001943
     * @version 01.04.2021
    */ 
    public void increaseSpeed() {
        velocityY = -12;
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
}