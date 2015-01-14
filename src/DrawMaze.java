// Gregory Halverson
// Pierce College
// Spring 2014
// CS 532

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Create a window to draw the maze, printVertical the wall list and tree, and create new mazes at different sizes
public class DrawMaze extends JFrame 
{
    // Constants
    private static final int DEFAULT_SIZE = 10;

    // Components
    private BufferedPanel graphicsPane;
    private JLabel treeLabel;
    private JLabel wallsLabel;
    private JLabel sizeLabel;
    private JScrollPane wallListPane;
    private JScrollPane treePane;
    private JButton newButton;
    private JButton showPathButton;
    private JSpinner sizeSpinner;
    private JTextPane treeOutput;
    private JTextPane wallListOutput;

    // Member data
    private static int initialSize;
    private Maze maze;
    
    // Constructor
    public DrawMaze() 
    {    
    	initComponents();
        newMaze();
        graphicsPane.setFocusable(true);
        graphicsPane.requestFocus();
    }
    
    // Generate a new random maze
    public void newMaze()
    {
        maze = new Maze((int)(Integer) sizeSpinner.getValue());
        wallListOutput.setText(maze.printVertical());
        treeOutput.setText(maze.getTree().printVertical());
        maze.draw(graphicsPane.getBackBuffer(), graphicsPane.getBounds());
        graphicsPane.repaint();
    }
    
    // Initialize components on form
    private void initComponents() {

    	// Allocate components
        newButton = new JButton(); // Button used to generate a new maze
        showPathButton = new JButton(); // Button used to show the shortest path on the maze
        treeLabel = new JLabel(); // Label for Union/Find Tree printVertical
        treePane = new JScrollPane(); // Scrollbar for printVertical of Union/Find Tree nodes
        treeOutput = new JTextPane(); // Output for Union/Find Tree nodes
        graphicsPane = new BufferedPanel(); // Output for maze
        sizeLabel = new JLabel(); // Label for size selection
        sizeSpinner = new JSpinner(); // Spinner used to select the n x n size of the maze, creates a new maze when changed
        wallsLabel = new JLabel(); // Label for printVertical of wall list
        wallListPane = new JScrollPane(); // Scrollbar for printVertical of wall list
        wallListOutput = new JTextPane(); // Output for wall list

        // Set main frame properties
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Maze - Gregory Halverson - CS 532 - Spring 2014");
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        setResizable(false);

        // Customize components
        newButton.setIcon(new ImageIcon(getClass().getResource("Icons/dice.jpg")));

        newButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newButtonMouseClicked(evt);
            }
        });

        treeOutput.setEditable(false);
        treePane.setViewportView(treeOutput);
        
        graphicsPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                graphicsPaneMouseClicked(evt);
            }
        });
        	
        graphicsPane.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                graphicsPaneKeyPressed(evt);
            }
        });

        GroupLayout graphics_pane_layout = new GroupLayout(graphicsPane);
        graphicsPane.setLayout(graphics_pane_layout);
        
        graphics_pane_layout.setHorizontalGroup(
                graphics_pane_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 500, Short.MAX_VALUE));
        
        graphics_pane_layout.setVerticalGroup(
                graphics_pane_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 511, Short.MAX_VALUE));

        sizeSpinner.setModel(new SpinnerNumberModel(initialSize, 3, 30, 1));
        sizeSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sizeSpinnerStateChanged(evt);
            }
        });

        treeLabel.setText("Tree");

        wallsLabel.setText("Walls");

        wallListOutput.setEditable(false);
        wallListPane.setViewportView(wallListOutput);

        sizeLabel.setText("Size");

        showPathButton.setText("Show Path");
        showPathButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showPathButtonMouseClicked(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        // Form generated in NetBeans
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addComponent(wallsLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(treeLabel)
                                .addGap(55, 55, 55))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(29, 29, 29)
                                                .addComponent(wallListPane, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                                                                .addComponent(graphicsPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(51, 51, 51))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(249, 249, 249)
                                                                .addComponent(showPathButton)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(treePane, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(sizeLabel)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(sizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(wallsLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(treeLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(showPathButton)
                                                .addGap(22, 22, 22)
                                                .addComponent(graphicsPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(wallListPane, javax.swing.GroupLayout.PREFERRED_SIZE, 597, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addContainerGap())
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                        .addComponent(treePane, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(22, 22, 22)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(sizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(sizeLabel))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(newButton)
                                                        .addGap(22, 22, 22))))));

        pack();
    }

    // Clicking the dice generates a new maze
    private void newButtonMouseClicked(java.awt.event.MouseEvent evt)
    {
        newMaze();
        graphicsPane.requestFocus();
    }

    // Changing the size on the spinner control generates a new maze
    private void sizeSpinnerStateChanged(javax.swing.event.ChangeEvent evt)
    {
        newMaze();
    }
    
    // Set focus to graphics pane on mouse click
    private void graphicsPaneMouseClicked(java.awt.event.MouseEvent evt)
    {                                           
        graphicsPane.requestFocus();
    }

    // Show shortest path through maze
    private void showPathButtonMouseClicked(java.awt.event.MouseEvent evt)
    {
        maze.showPath();
        maze.draw(graphicsPane.getBackBuffer(), graphicsPane.getBounds());
        graphicsPane.repaint();
        graphicsPane.requestFocus();
    }

    // Move cursor on maze with arrow keys
    private void graphicsPaneKeyPressed(java.awt.event.KeyEvent evt)
    {                                         
        switch(evt.getKeyCode())
        {
            case KeyEvent.VK_UP:

                // Move the cursor to the cell above
                maze.moveCursor(Maze.Direction.up);
                break;
                
            case KeyEvent.VK_RIGHT:
            	 
                // If the cursor is at the end of the maze, make a new maze
                if (maze.getCursor() == maze.getSize() * maze.getSize() - 1)
                {
                    newMaze();
                    return;
                }
                else
                {
                    maze.moveCursor(Maze.Direction.right);
                }

                break;

            case KeyEvent.VK_DOWN:

                // Move the cursor to the cell below
                maze.moveCursor(Maze.Direction.down);
                break;
                
            case KeyEvent.VK_LEFT:

                // Move the cursor to the cell on the left
                maze.moveCursor(Maze.Direction.left);
                break;
        }

        // Redraw maze
        maze.draw(graphicsPane.getBackBuffer(), graphicsPane.getBounds());
        graphicsPane.repaint();
    }

    public static void main(String args[])
    {
    	// Check argument list for initial size
    	if (args.length > 0)
    		initialSize = Integer.parseInt(args[0]); // Assign the size of the maze from the first argument
    	else
    		initialSize = DEFAULT_SIZE; // Set the maze to the default size
    	    
    	// Load appearance for components
        try
        {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName())) 
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(DrawMaze.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(DrawMaze.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(DrawMaze.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(DrawMaze.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        // Display form
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run() 
            {
                new DrawMaze().setVisible(true);
            }
        });
    }
}
