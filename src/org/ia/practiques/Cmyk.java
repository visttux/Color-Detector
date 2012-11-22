package org.ia.practiques;

/**
 * This class store a CMYK object
 * 
 * @author Victor Martinez
 */
public class Cmyk {

	private int cyan = 0;
    private int black = 0;
    private int yellow = 0;
    private int magenta = 0;
    
    /**
     * Constructor of the class from integers
     * 
     * @param cyan: integer value for cyan (0...255)
     * @param magenta: integer value for magenta (0...255)
     * @param yellow: integer value for yellow (0...255)
     * @param black: integer value for black (0...255)
     */
    public Cmyk (int cyan, int magenta, int yellow, int black)
    {
        this.cyan = cyan;
        this.black = black;
        this.yellow = yellow;
        this.magenta = magenta;
    }
    
    /**
     * @return cyan value
     */
    public int getCyan ()
    {
        return (this.cyan);
    }
    
    /**
     * @return black value
     */
    public int getBlack ()
    {
        return (this.black);
    }
    
    /**
     * @return yellow value
     */
    public int getYellow ()
    {
        return (this.yellow);
    }
    
    /**
     * @return magenta value
     */
    public int getMagenta ()
    {
        return (this.magenta);
    }
}
