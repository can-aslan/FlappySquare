package gameobjects.borderobjects;

/**
 * A simple Point class!
 * @author Yağız Can Aslan 22001943
 * @version 01.04.2021
*/
public class Point
{
    // Properties
    private int x;
    private int y;    
    
    // Constructors
    public Point( int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // Methods
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
     * Setter method for the x coordinate
     * @param x the new x coordinate
     * @author Yağız Can Aslan 22001943
     * @version 30.03.2021
    */
    public void setX( int x) {
        this.x = x;
    }
    
    /**
     * Setter method for the y coordinate
     * @param y the new y coordinate
     * @author Yağız Can Aslan 22001943
     * @version 30.03.2021
    */
    public void setY( int y) {
        this.y = y;
    }
}