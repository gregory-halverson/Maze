// Gregory Halverson
// Pierce College
// CS 532
// Spring 2014

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

// Extended JPanel for drawing to a back buffer so that the drawing doesn't disappear on repaint
public class BufferedPanel extends JPanel
{
    // Member data
    private BufferedImage backBuffer = null;
    private Graphics2D backBufferGraphics = null;

    // Create graphics context for back buffer
    Graphics2D getBackBuffer()
    {
        if (backBuffer == null)
        {
            backBuffer = (BufferedImage)(createImage(getWidth(), getHeight()));
            backBufferGraphics = backBuffer.createGraphics();
	    backBufferGraphics.setBackground(getBackground());
	    backBufferGraphics.clearRect(0, 0, getWidth(), getHeight());
        }
        
        return backBufferGraphics;
    }
    
    // Draw back buffer to screen
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (backBuffer != null)
            ((Graphics2D) g).drawImage(backBuffer, null, 0, 0);
    }
}
