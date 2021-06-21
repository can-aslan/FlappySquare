package gameobjects;

import javax.swing.JComponent;

import java.awt.Graphics;
import java.awt.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A simple Grass class!
 * @author Yağız Can Aslan 22001943
 * @version 01.04.2021
*/
public class Grass extends JComponent
{
    /**
	 *  7220713965389263767L
	 */
    private static final long serialVersionUID = 7220713965389263767L;
    
	// Properties
    public final static int SIZE = 15;
    public final static int BORDER_SIZE = 2;
    private final Color DARK_GREEN_BORDER = new Color( 20, 100 - (int) (Math.random() * 21), 20);
    private final Color DARK_GREEN = new Color( 20, (150 - (int) (Math.random() * 32)), 20);
    private final String NOISE_FILENAME = "perlin.jpg";
    private final int HEIGHT;
    private BufferedImage noiseImage;
    private File noiseFile;
    public int height;
    public int x;
    public int y;
    
    // Constructors
    public Grass( int x, int heightWindow, int imageColumn) throws IOException {
        noiseFile = new File( NOISE_FILENAME);
        noiseImage = ImageIO.read( noiseFile);
        
        HEIGHT = heightWindow;
        setHeight( imageColumn);
        
        this.x = x;
        y = HEIGHT - height;
        
        repaint();
    }
    
    // Methods    
    @Override
    public void paintComponent( Graphics g) {
        super.paintComponent( g);
        
        // Border
        g.setColor( DARK_GREEN_BORDER);
        g.fillRect( x - BORDER_SIZE, y - BORDER_SIZE, SIZE + (2 * BORDER_SIZE), height + (2 * BORDER_SIZE));
    
        // Grass Line
        g.setColor( DARK_GREEN);        
        g.fillRect( x, y, SIZE, height);
    }
    
    /**
     * Gets the height value from a 2D noise map 
     * @param y y-coordinate from the noise map
     * @author Yağız Can Aslan 22001943 (Code taken from BasicImageProcessor project -06.12.2020-)
     * @version 17.06.2021
    */
    public void setHeight( int y ) {
        // Get pixel color by position x and y 
        int data = noiseImage.getRGB( x, y);
        int red =   (data & 0x00ff0000) >> 16;
        int green = (data & 0x0000ff00) >> 8;
        int blue =   data & 0x000000ff;
        
        // Set the height as [0,100] instead of [0,255]
        height = 5 * ((int) Math.round( ((red + green + blue) * 100.0) / (3 * 255)));
        height = height / 2;
    }
    
    @Override
    public String toString() {
        return "" + height;
    }
}