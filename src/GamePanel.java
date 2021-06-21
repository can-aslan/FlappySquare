import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import gameobjects.*;

/**
 * A simple panel class which extends JPanel
 * @author Yağız Can Aslan 22001943
 * @version 30.03.2021
*/
@SuppressWarnings("unused")
public class GamePanel extends JPanel implements ActionListener
{
    /**
	 * 1924578159457656038L
	 */
    private static final long serialVersionUID = 1924578159457656038L;
    
    // Properties
    private final int BIOME_PLAINS_INTENSITY = 1;
    private final int BIOME_FOREST_INTENSITY = 5;
    private final int BIOME_DENSE_FOREST_INTENSITY = 5 * BIOME_FOREST_INTENSITY;
    private final int WIDTH;
    private final int HEIGHT;
    private double biomeDecider;
    private boolean grassDrawn;
    private boolean treesDrawn;
    public Obstacle obstacle;
    public Player player;
    public ArrayList<Particle> particles;
    public ArrayList<Grass> grass;
    public ArrayList<Tree> trees;
    public Timer timer = new Timer( 5, this);
    
    // Constructors
    public GamePanel( Player player, Obstacle obs, int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        
        setBackground( new Color( 178, 215, 255)); // Light Blue (178, 215, 255)
        setLayout( null);
        setBorder( BorderFactory.createEmptyBorder(5, 5, 10, 5));
        
        trees = new ArrayList<Tree>();
        
        try {
            grassDrawn = false;
            drawGrass();
            
            treesDrawn = false;
            biomeDecider = Math.random();
            System.out.println( "Biome Decider: " + biomeDecider);
            
            biomeDecider = 0.9; // BIOME IS SET TO PLAINS FOR BETTER PERFORMANCE
            if ( biomeDecider < 0.21 && biomeDecider >= 0.05 ) { // Forest Biome
                System.out.println( "BIOME: Forest");
                for ( int i = 0; i < BIOME_FOREST_INTENSITY; i++ ) {
                    drawTrees( 100);
                }
            }
            else if ( biomeDecider < 0.05 ) { // Dense Forest Biome
                System.out.println( "BIOME: Dense Forest");
                for ( int i = 0; i < BIOME_DENSE_FOREST_INTENSITY; i++ ) {
                    drawTrees( 100);
                }
            }
            else { // Plains
                System.out.println( "BIOME: Plains");
                for ( int i = 0; i < BIOME_PLAINS_INTENSITY; i++ ) {
                    drawTrees( 100);
                }
            }
            
            System.out.println( grass);
        }
        catch ( Exception e ) {
            grassDrawn = true;
            e.printStackTrace();
        }
        
        // Setup the player
        this.player = player;
        add( player);
        
        obstacle = obs;
        add( obstacle);
        repaint();
        
        particles = new ArrayList<Particle>();
    }
    
    // Methods
    @Override
    public void paintComponent( Graphics g) {
        super.paintComponent( g);
        
        for ( Tree t : trees ) {
            t.paintComponent( g);
            // t.repaint();
        }
        
        for ( Grass singleGrass : grass ) {
            singleGrass.paintComponent( g);
            // singleGrass.repaint();
        }
        
        player.paintComponent( g);
        // player.repaint();
        
        obstacle.paintComponent( g);
        // obstacle.repaint();
        
        for ( Particle p : particles ) {
            p.paintComponent( g);
            // p.repaint();
        }
        
        timer.start();
    }
    
    @Override
	public void actionPerformed( ActionEvent e) {
        
        // Method Variables
        
        // Method Code
        // player.checkBorders();
        repaint();
    }
    
    /**
     * Adds a Particle to the particles ArrayList 
     * @param p particle
     * @author Yağız Can Aslan 22001943
     * @version 17.06.2021
    */
    public void addParticle( Particle p) {
        particles.add( p);
    }
    
    /**
     * Adds a Tree to the trees ArrayList 
     * @param t Tree
     * @author Yağız Can Aslan 22001943
     * @version 17.06.2021
    */
    public void addTree( Tree t) {
        trees.add( t);
        System.out.println( "Tree number " + trees.size() + " added.");
    }
    
    /**
     * Adds a MountainLine to the mountain ArrayList 
     * @param m MountainLine
     * @author Yağız Can Aslan 22001943
     * @version 17.06.2021
    */
    public void addGrass( Grass g) {
        grass.add( g);
    }
    
    /**
     * Draws the grass
     * @author Yağız Can Aslan 22001943
     * @version 17.06.2021
     * @throws IOException
    */ 
    public void drawGrass() throws IOException {        
        grass = new ArrayList<Grass>();
        
        for ( int i = 0; i < WIDTH + 10; i = i + Grass.SIZE + Grass.BORDER_SIZE ) {
            addGrass( new Grass( i, HEIGHT, ((int) (Math.random() * (HEIGHT - 100)) + 1) ));
            
            // System.out.println( "Drawing grass... " + (100 * i) / WIDTH + "%");
        }
        
        repaint();
        grassDrawn = true;
    }
    
    /**
     * Draws trees
     * @param alpha tree priority (opaque/less opaque)
     * @author Yağız Can Aslan 22001943
     * @version 18.06.2021
     * @throws IOException
    */
    public void drawTrees( int alpha) throws IOException {
        int treePos;
        
        addTree( new Tree( (int) (Math.random() * WIDTH), HEIGHT, ((int) (Math.random() * (HEIGHT - 100)) + 1), alpha));
        
        for ( int i = 0; i < WIDTH + 10; i = i + Grass.SIZE + Grass.BORDER_SIZE ) {
            if ( Math.random() < 0.095 ) {
                treePos = (int) (Math.random() * WIDTH);                
                addTree( new Tree( treePos, HEIGHT, ((int) (Math.random() * (HEIGHT - 100)) + 1), alpha));
            }
        }
        
        repaint();
        // treesDrawn = true;
    }
}