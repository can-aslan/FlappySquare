package gameobjects;

import javax.swing.JComponent;

import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A simple Tree class!
 * @author Yağız Can Aslan 22001943
 * @version 01.04.2021
*/
@SuppressWarnings("serial")
public class Tree extends JComponent
{
    // Properties
    public final static int BORDER_SIZE = 2;
    public final static int SIZE = 30 - (int) (Math.random() * 17);
    private final Color OAK = new Color( 5, (104 - (int) (Math.random() * 32)), 2); // Dark Green rgb(5, 104, 2)
    private final Color OAK_BORDER = new Color( 5, (70 - (int) (Math.random() * 32)), 2); // Dark Green Border rgb(5, 70, 2)
    private final Color PINE = new Color( 5, (50 - (int) (Math.random() * 20)), 2); // Darker Green rgb(5, 50, 2)
    private final Color PINE_BORDER = new Color( 5, (35 - (int) (Math.random() * 20)), 2); // Darker Green rgb(5, 50, 2)
    private final Color DARK_BROWN_BORDER = new Color( 76, 40, 42);
    private final Color DARK_BROWN = new Color( 90, (51 - (int) (Math.random() * 8)), 42);
    private final Color PINE_LOG = new Color( 36, 15, 12); // Very Dark Brown (36, 15, 12)
    private final String NOISE1_FILENAME = "perlin.jpg";
    private final String NOISE2_FILENAME = "noise.jpg";
    private final int PINE_HEIGHT_LIMIT = 375 - (int) (Math.random() * 25);
    private final int OAK_SIZE = 180 - (int) (Math.random() * SIZE) + (int) (Math.random() * (SIZE / 2));
    private final int PINE_SIZE = 50 - (((int) (Math.random() * SIZE)) / 5) + (int) (Math.random() * (SIZE / 3));
    private final int PINE_LEAVE_SET_DELTA = SIZE;
    private final int PINE_EXTRA_LEAVE_SET = 3 + (int) (Math.random() * 2);
    private final int OAK_LEAVE_SET_DELTA = (3 * SIZE) / 4;
    private final int OAK_EXTRA_LEAVE_SET = ((int) (Math.random() * 2)) + 1;
    private final double OAK_LEAVES_GAP_COEFFICIENT = 1.5 + Math.random();
    private final int HEIGHT;
    private final int OAK_X_DELTA;
    private final int OAK_Y_DELTA;
    private BufferedImage noiseImage1;
    private BufferedImage noiseImage2;
    private File noiseFile1;
    private File noiseFile2;
    private int oakDownscaleFactor;
    private int treePriority; // 100 = OPAQUE, 80 = LESS OPAQUE, 60 = EVEN LESS OPAQUE...
    public int height;
    public int x;
    public int y;
    
    // Constructors
    public Tree( int x, int heightWindow, int imageColumn, int treePriority) throws IOException {      
        noiseFile1 = new File( NOISE1_FILENAME);
        noiseImage1 = ImageIO.read( noiseFile1);
        
        noiseFile2 = new File( NOISE2_FILENAME);
        noiseImage2 = ImageIO.read( noiseFile2);
        
        HEIGHT = heightWindow;
        setHeight( imageColumn);
        
        this.treePriority = (255 * treePriority) / 100;
        
        if ( height > PINE_HEIGHT_LIMIT ) {
            OAK_X_DELTA = ((int) (Math.random() * (OAK_SIZE / 20)));
            OAK_Y_DELTA = SIZE - 5 + ((int) (Math.random() * (OAK_SIZE / 20)));
        }
        else {
            OAK_X_DELTA = 0;
            OAK_Y_DELTA = 0;
        }
        
        this.x = x;
        y = HEIGHT - height;
        
        generateNewOakDownscaleFactor();
        
        repaint();
    }
    
    // Methods    
    @Override
    public void paintComponent( Graphics g) {
        super.paintComponent( g);
        
        Graphics2D graphics2D = (Graphics2D) g;
        
        graphics2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Determine Tree Type
        // ############
        // ### PINE ###
        // ############
        if ( height <= PINE_HEIGHT_LIMIT ) {
            // Border
            graphics2D.setColor( new Color( DARK_BROWN_BORDER.getRed(), DARK_BROWN_BORDER.getGreen(), DARK_BROWN_BORDER.getBlue(), treePriority));
            graphics2D.fillRect( x - BORDER_SIZE, y - BORDER_SIZE, SIZE + (2 * BORDER_SIZE), height + (2 * BORDER_SIZE));
        
            // Tree Log
            graphics2D.setColor( new Color( PINE_LOG.getRed(), PINE_LOG.getGreen(), PINE_LOG.getBlue(), treePriority));        
            graphics2D.fillRect( x, y, SIZE, height);
                        
            // Pine Leaves Border
            graphics2D.setColor( new Color( PINE_BORDER.getRed(), PINE_BORDER.getGreen(), PINE_BORDER.getBlue(), treePriority));
            for ( int i = 0; i <= PINE_EXTRA_LEAVE_SET * PINE_LEAVE_SET_DELTA; i = i + PINE_LEAVE_SET_DELTA) {
                graphics2D.fillPolygon( new int[] { x - PINE_SIZE - BORDER_SIZE * 2,
                                                    x + SIZE / 2,
                                                    x + SIZE + PINE_SIZE + BORDER_SIZE * 2},
                                        new int[] { y + i + BORDER_SIZE,
                                                    y - 2 * PINE_SIZE + i - BORDER_SIZE * 2,
                                                    y + i + BORDER_SIZE},
                                        3);
            }     
            
            // Pine Leaves
            graphics2D.setColor( new Color( PINE.getRed(), PINE.getGreen(), PINE.getBlue(), treePriority));
            for ( int i = 0; i <= PINE_EXTRA_LEAVE_SET * PINE_LEAVE_SET_DELTA; i = i + PINE_LEAVE_SET_DELTA) {
                graphics2D.fillPolygon( new int[] { x - PINE_SIZE, x + SIZE / 2, x + SIZE + PINE_SIZE}, new int[] { y + i, y - 2 * PINE_SIZE + i, y + i}, 3);
            }
            
            /*
            for ( int i = PINE_EXTRA_LEAVE_SET * PINE_LEAVE_SET_DELTA; i > 0; i = i - PINE_LEAVE_SET_DELTA) {
                g.setColor( PINE);
                g.fillPolygon( new int[] { x - PINE_SIZE, x + SIZE / 2, x + SIZE + PINE_SIZE}, new int[] { y + i, y - 2 * PINE_SIZE + i, y + i}, 3);
                
                // Pine Leaves Border
                g.setColor( PINE_BORDER);
                g.drawPolygon( new int[] { x - PINE_SIZE, x + SIZE / 2, x + SIZE + PINE_SIZE}, new int[] { y + i, y - 2 * PINE_SIZE + i, y + i}, 3);
            }
            */
        }
        // ###########
        // ### OAK ###
        // ###########
        else if ( height > PINE_HEIGHT_LIMIT ) {
            // Border
            graphics2D.setColor( new Color( DARK_BROWN_BORDER.getRed(), DARK_BROWN_BORDER.getGreen(), DARK_BROWN_BORDER.getBlue(), treePriority));
            graphics2D.fillRect( x - BORDER_SIZE, y - BORDER_SIZE, SIZE + (2 * BORDER_SIZE), height + (2 * BORDER_SIZE));
        
            // Tree Log
            graphics2D.setColor( new Color( DARK_BROWN.getRed(), DARK_BROWN.getGreen(), DARK_BROWN.getBlue(), treePriority));        
            graphics2D.fillRect( x, y, SIZE, height);
            
            // Oak Leaves Border
            graphics2D.setColor( new Color( OAK_BORDER.getRed(), OAK_BORDER.getGreen(), OAK_BORDER.getBlue(), treePriority));
            for ( int i = 0; i <= OAK_EXTRA_LEAVE_SET * OAK_LEAVE_SET_DELTA; i = i + OAK_LEAVE_SET_DELTA) {
                graphics2D.fillOval(    x - BORDER_SIZE - (OAK_SIZE / 2) + SIZE / 2 + ((oakDownscaleFactor * (i / OAK_LEAVE_SET_DELTA)) / 2),
                                        (int) (y - BORDER_SIZE - (OAK_SIZE / 2) + SIZE / 2 - ((i / OAK_LEAVE_SET_DELTA) * OAK_LEAVES_GAP_COEFFICIENT * SIZE) + (OAK_EXTRA_LEAVE_SET * 2 * SIZE)),
                                        OAK_SIZE + (2 * BORDER_SIZE) - OAK_X_DELTA - (oakDownscaleFactor * (i / OAK_LEAVE_SET_DELTA)),
                                        OAK_SIZE + (2 * BORDER_SIZE) - OAK_Y_DELTA - (oakDownscaleFactor * (i / OAK_LEAVE_SET_DELTA)));
            }
            
            // Oak Leaves
            graphics2D.setColor( new Color( OAK.getRed(), OAK.getGreen(), OAK.getBlue(), treePriority));
            // graphics2D.fillOval( x - (OAK_SIZE / 2) + SIZE / 2, y - (OAK_SIZE / 2) + SIZE / 2, OAK_SIZE - OAK_X_DELTA, OAK_SIZE - OAK_Y_DELTA);
            for ( int i = 0; i <= OAK_EXTRA_LEAVE_SET * OAK_LEAVE_SET_DELTA; i = i + OAK_LEAVE_SET_DELTA) {
                graphics2D.fillOval(    x - (OAK_SIZE / 2) + SIZE / 2 + ((oakDownscaleFactor * (i / OAK_LEAVE_SET_DELTA)) / 2),
                                        (int) (y - (OAK_SIZE / 2) + SIZE / 2 - ((i / OAK_LEAVE_SET_DELTA) * OAK_LEAVES_GAP_COEFFICIENT * SIZE) + (OAK_EXTRA_LEAVE_SET * 2 * SIZE)),
                                        OAK_SIZE - OAK_X_DELTA - (oakDownscaleFactor * (i / OAK_LEAVE_SET_DELTA)),
                                        OAK_SIZE - OAK_Y_DELTA - (oakDownscaleFactor * (i / OAK_LEAVE_SET_DELTA)));
            }
        }
    }
    
    /**
     * Generates a new random OAK Tree downscale factor for leaves 
     * @author Yağız Can Aslan 22001943
     * @version 18.06.2021
    */ 
    private void generateNewOakDownscaleFactor() {
        oakDownscaleFactor = SIZE + ((int) (Math.random() * (SIZE / 2)));
    }
    
    /**
     * Gets the height value from a 2D noise map 
     * @param y y-coordinate from the noise map
     * @author Yağız Can Aslan 22001943 (Code taken from BasicImageProcessor project -06.12.2020-)
     * @version 17.06.2021
    */
    public void setHeight( int y ) {
        // ## NOISE 1 ##
        // Get pixel color by position x and y
        int data = noiseImage1.getRGB( x, y);
        int red =   (data & 0x00ff0000) >> 16;
        int green = (data & 0x0000ff00) >> 8;
        int blue =   data & 0x000000ff;
        
        // Set the height as [0,100] instead of [0,255]
        height = (int) Math.round( ((red + green + blue) * 100.0) / (3 * 255));
        
        // ## NOISE 2 ##
        // Get pixel color by position x and y
        data = noiseImage2.getRGB( x, y);
        red =   (data & 0x00ff0000) >> 16;
        green = (data & 0x0000ff00) >> 8;
        blue =   data & 0x000000ff;
        
        // Set the height as [0,100] instead of [0,255]
        height = 5 * (height + (int) Math.round( ((red + green + blue) * 100.0) / (3 * 255)));
        System.out.println( height);
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
    
    @Override
    public String toString() {
        return "" + height;
    }
}