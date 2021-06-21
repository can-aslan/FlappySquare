import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.util.ArrayList;

import gameobjects.*;

/**
 * Main class for Flappy Square
 * @author Yağız Can Aslan 22001943
 * @version v0.2.1
*/ 
public class Main
{
    // Global Constants
    static final int WIDTH = 1100;
    static final int HEIGHT = 640;
    
    // Global Variables
    static JLabel label = new JLabel( "<html><b><font color='black'>Score: 0");
    
    // Create and set the JFrame, add GamePanel to the frame
    static JFrame frame = new JFrame();
    static Obstacle obstacle  = new Obstacle( WIDTH, HEIGHT - 40);
    static Player player = new Player( WIDTH, HEIGHT - 40, obstacle);
    static GamePanel panel = new GamePanel( player, obstacle, WIDTH, HEIGHT);
    
    public static void main(String[] args)
    {
        // Constants
        final String PROG_NAME = "Not PotLuck";
        
        // Variables
        Timer timer = new Timer( 5, new ActionListener() {
            // ArrayList<Integer> mountainDrawnForValues = new ArrayList<Integer>();
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( !player.hasLost() ) {
                    label.setText( "<html><b><font color='black'>Score: " + panel.player.getScore());
                    label.repaint();
                    
                    /*
                    if ( *Score must be divisible by 5* panel.player.getScore() % 5 == 0 && 
                    *Mountain must not have been drawn previously for the specific Player score* 
                    !mountainDrawnForValues.contains( panel.player.getScore())) {
                        try {
                            panel.drawMountain();
                            mountainDrawnForValues.add( panel.player.getScore());
                        }
                        catch ( Exception e1 ) {
							e1.printStackTrace();
						}
                    }
                    */
                }
                else {
                    label.setText( "<html><b><font color='black'>" + player.getLosingReason() + " || Score: " + panel.player.getScore());
                    label.repaint();
                    
                    if ( !panel.player.hasSpawnedParticles() ) {
                        int numOfParticles = 10 * panel.player.getScore();
                        
                        if ( numOfParticles > 200 ) {
                            numOfParticles = 200;
                        }
                        else if ( numOfParticles < 20 ) {
                            numOfParticles = 20;
                        }
                        
                        for ( int i = numOfParticles; i > 0; i-- ) {
                            panel.addParticle( new Particle( panel.player.getX(), panel.player.getY(), HEIGHT));
                            panel.repaint();
                        }
                        
                        panel.player.setSpawnedParticles( true);
                    }
                }
            }
        });
        JPanel textPanel = new JPanel();
        
        // Program Code
        // Game Panel
        panel.setBounds( 0, 0, frame.getWidth(), frame.getHeight());
        frame.add( panel, BorderLayout.CENTER);
        
        panel.addMouseListener( addNewMouseListener( timer));
        
        // Text Panel
        textPanel.setBackground( Color.GRAY);
        
        label.setBounds( 15, panel.getHeight() + 10, 120, 25);
        textPanel.add( label);
        
        frame.add( textPanel, BorderLayout.SOUTH);
        
        frame.setTitle( PROG_NAME);
        frame.setPreferredSize( new Dimension( WIDTH, HEIGHT));
        frame.setResizable( false);
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        
        frame.pack();
        frame.setVisible(true);
        
        timer.start();
    }
    
    /**
     * Creates a simple mouse listener
     * @return the mouse listener created
     * @author Yağız Can Aslan 22001943
     * @version 31.03.2021
    */
    public static MouseListener addNewMouseListener( Timer timer) {
        return new MouseListener() {
            @Override
            public void mouseClicked( MouseEvent e) {
                if ( !player.hasLost() ) {
                    timer.start();
                    label.setText( "<html><b><font color='black'>Score: " + panel.player.getScore());
                    label.repaint();
                }
            }

			@Override
            public void mousePressed( MouseEvent e) {
                if ( !player.hasLost() ) {
                    panel.player.increaseSpeed();
                    timer.start();
                    label.setText( "<html><b><font color='black'>Score: " + panel.player.getScore());
                    label.repaint();
                }
            }

            @Override
            public void mouseReleased( MouseEvent e) {
                if ( !player.hasLost() ) {
                    timer.start();
                    label.setText( "<html><b><font color='black'>Score: " + panel.player.getScore());
                    label.repaint();
                }
            }

            @Override
            public void mouseEntered( MouseEvent e) {
                if ( !player.hasLost() ) {
                    timer.start();
                    label.setText( "<html><b><font color='black'>Score: " + panel.player.getScore());
                    label.repaint();
                }
            }

            @Override
            public void mouseExited( MouseEvent e) {
                if ( !player.hasLost() ) {
                    timer.start();
                    label.setText( "<html><b><font color='black'>Score: " + panel.player.getScore());
                    label.repaint();
                }
            }
        };
    }
}