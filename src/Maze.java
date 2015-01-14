// Gregory Halverson
// Pierce College
// Spring 2014
// CS 532

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.awt.*;

// Creates a maze of n x n size using Kruskal's Algorithm and a Union/Find Tree
public class Maze
{
    // Container for wall data
    class MazeWall
    {
        // Member data
        public int c1;
        public int c2;
        public boolean up;

        // Constructor
        MazeWall(int init1, int init2)
        {
            c1 = init1;
            c2 = init2;
            up = true;
        }
        
        // Check if given adjacent cells match the adjacent cells of the wall
        boolean match(int comp1, int comp2)
        {
            return (((c1 == comp1) && (c2 == comp2)) || ((c1 == comp2) && (c2 == comp1)));
        }

        // Output cells and status of wall to string
        String output()
        {
            return "(" + c1 + ", " + c2 + ") - " + (up ? "UP" : "DOWN");
        }
    }

    public enum Direction {up, right, down, left};
    
    // Size n in n x n
    private int size;

    // List of walls
    private MazeWall[] walls;

    // Union find tree used to generate path
    private UnionFindTree tree;

    // Holds position of current cell for visual navigation
    private int cursor;

    // Instructs drawing function to show path
    private boolean showPath;

    // Holds shortest path when path is shown
    private int [] path;

    // Constructor
    Maze(int size)
    {
    	// Initialize member data
        this.size = size;
        walls = new MazeWall[wallCount()];
        tree = new UnionFindTree(cellCount());
        cursor = 0;
        showPath = false;
        path = null;

        // Generate maze
        generateWalls();
        randomizeWalls();
        generatePath();
    }

    // Generate walls by iterating through each cell
    protected void generateWalls()
    {
        // Array iterator
        int w = 0;

        // Iterate rows
        for (int y = 0; y < size; y++)
        {
            // Iterate columns
            for (int x = 0; x < size; x++)
            {
                // Create wall to the right unless it's the last column
                if (x < (size - 1))
                    walls[w++] = new MazeWall(y * size + x, y * size + x + 1);

                // Create wall to the bottom unless it's the last row
                if (y < (size - 1))
                    walls[w++] = new MazeWall(y * size + x, (y + 1) * size + x);
            }
        }
    }

    // Swap the order of two walls
    private void swap(int key1, int key2)
    {
        MazeWall temp = walls[key1];
        walls[key1] = walls[key2];
        walls[key2] = temp;
    }

    // Randomize order of walls
    void randomizeWalls()
    {
        // Seed pseudo-random number generator
        Random rand = new Random(System.currentTimeMillis());

        // Swap each wall with a random index
        for (int i = 0; i < wallCount(); i++)
            swap(i, Math.abs(rand.nextInt()) % (i + 1));
    }

    // Kruskal's Algorithm using Union/Find Tree
    void generatePath()
    {
        // Iterate list of walls
        for (int w = 0; w < (wallCount()); w++)
        {
            // If the boundary cells of the walls are in separate sets, unite their sets and knock down the wall
            if (!tree.sameRoot(walls[w].c1, walls[w].c2))
            {
                // Unite the disjoint sets
                tree.wunion(walls[w].c1, walls[w].c2);

                // Knock down the wall
                walls[w].up = false;
            }
        }
    }

    // Find path through maze using breadth-first search with a queue
    void showPath()
    {
        // First in, first out queue used to process cells
        Queue<Integer> queue = new LinkedList<Integer>();

        // Used to keep track of which cells have already been visited
        boolean [] already_visited = new boolean[cellCount()];

        // Used to hold path
        Integer [] predecessor = new Integer[cellCount()];
        int [] path = new int[cellCount()];

        // Iterators
        int cell = 0;
        int next_move = 0;
        int depth = 0;

        // Boundaries
        int start = 0;
        int end = cellCount() - 1;

        // Initialize path

        // Iterate cells
        for (int c = 0; c < cellCount(); c++)
        {
            // None of the cells have been visited at the start of the search
            already_visited[c] = false;
            predecessor[c] = null;
            path[c] = 0;
        }

        // Put starting cell of maze into queue
        already_visited[start] = true;
        queue.add(start);
        predecessor[0] = 0;

        // Breadth-first search using first-in, first-out queue
        while (!queue.isEmpty() && (Integer)(queue.peek()) != end)
        {
            // Check first cell in queue
            cell = (Integer)queue.peek();

            // Check each direction from each cell
            for (Maze.Direction direction : Maze.Direction.values())
            {
                // Check if it's possible to move in this direction
                if (possibleMove(cell, direction))
                {
                    // Move in this direction
                    next_move = adjacent(cell, direction);

                    // Check if this cell has already been visited
                    if (!already_visited[next_move])
                    {
                        // Set cell to already visited
                        already_visited[next_move] = true;

                        // Add cell to path
                        predecessor[next_move] = cell;

                        // Queue cell for processing
                        queue.add(next_move);
                    }
                }
            }

            // Remove first cell in queue once it's processed
            queue.remove();
        }

        // Find shortest path

        // Start at the end
        path[depth++] = end;

        // Work back to the beginning
        while (path[depth - 1] != start)
        {
            // Build path
            path[depth] = (int)predecessor[path[depth - 1]];
            depth++;
        }

        // Allocate path
        this.path = new int[depth];

        // Copy path
        for (int r = depth; r > 0; r--)
            this.path[r - 1] = path[depth - r];

        // Tell drawing function to render path
        showPath = true;
    }
    
    // Get maze size n in (n x n)
    int getSize()
    {
    	    return size;
    }

    // Get number of walls
    int wallCount() { return 2 * size * (size - 1); }

    // Get number of cells
    int cellCount() { return size * size; }
    
    // Get cursor cell
    int getCursor()
    {
    	    return cursor;
    }

    // Get Union/Find Tree used to calculate maze
    UnionFindTree getTree()
    {
        return tree;
    }
    
    // Check if a wall is up or down
    boolean wallStatus(int cell1, int cell2)
    {
        // Iterate walls
        for (int w = 0; w < (wallCount()); w++)
            if (walls[w].match(cell1, cell2)) return walls[w].up;
        
        return false;
    }
    
    // Check if a move from given cell in given direction is possible or blocked by a wall
    boolean possibleMove(int cell, Direction direction)
    {
        switch (direction)
        {
            case up:

            if (cell / size == 0)
                return false;
            else
                return !wallStatus(cell - size, cell);
        
            case right:
                
            if (cell % size == size - 1)
                return false;
            else
                return !wallStatus(cell, cell + 1);
        
            case down:
                
            if (cell / size == size - 1)
                return false;
            else
                return !wallStatus(cell, cell + size);
        
            case left:
                
            if (cell % size == 0)
                return false;
            else
                return !wallStatus(cell - 1, cell);
        }
        
        return false;
    }

    // Get adjacent cell number in given direction
    int adjacent(int cell, Direction direction)
    {
        int result = 0;

        switch (direction)
        {
            case up:

                result = cell - size;
                break;

            case right:

                result = cell + 1;
                break;

            case down:

                result = cell + size;
                break;

            case left:

                result = cell - 1;
                break;
        }

        return result;
    }

    // Attempt to move the cursor in given direction
    boolean moveCursor(Direction direction)
    {
        switch(direction)
        {
            
        case up:
            
			  if (possibleMove(cursor, Direction.up))
			  {
				 cursor -= size;
				 return true;
			  }
			  else
			  {
				 return false;
			  }
        
         case right:
            
			  if (possibleMove(cursor, Direction.right))
			  {
				 cursor += 1;
				 return true;
			  }
			  else
			  {
				 return false;
			  }
         
           case down:
            
			  if (possibleMove(cursor, Direction.down))
			  {
				 cursor += size;
				 return true;
			  }
			  else
			  {
				 return false;
			  }
           
            case left:
            
			  if (possibleMove(cursor, Direction.left))
			  {
				 cursor -= 1;
				 return true;
			  }
			  else
			  {
				 return false;
			  }         
        }
        
        return false;
    }

    // Output list of walls to string
    String printVertical()
    {
        StringWriter outputString = new StringWriter();
        PrintWriter output = new PrintWriter(outputString);

        // Iterate walls
        for (int w = 0; w < (wallCount()); w++)
            output.println(walls[w].output());

        return outputString.toString();
    }
    
    // Draw maze to graphics context
    void draw(Graphics2D g, Rectangle bounds)
    {
        int height = bounds.height - 1;
        int width = bounds.width - 1;
        int gradient = ((width < height) ? width : height) / size;
        int x = 0;
        int y = 0;
        
        // Turn on anti-aliasing for smoother edges
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHints(rh);
        
        // Clear the screen
        g.clearRect(0, 0, bounds.width, bounds.height);
        
        // Draw borders
        g.drawLine(0, 0, gradient * size, 0);
        g.drawLine(0, gradient * size, gradient * size, gradient * size);
        g.drawLine(0, gradient, 0, gradient * size);
        g.drawLine(gradient * size, 0, gradient * size, gradient * (size - 1));

        // Iterate through walls
        for (int w = 0; w < (wallCount()); w++)
        {
            // Check if wall is visible
            if (walls[w].up)
            {
                // Check if wall is on the right or bottom of first cell in wall
                if (walls[w].c2 == walls[w].c1 + 1)
                {
                    // Draw wall on the right
                    x = walls[w].c1 % size;
                    y = walls[w].c1 / size;
                    g.drawLine((x + 1) * gradient, y * gradient, (x + 1) * gradient, (y + 1) * gradient); 
                }
                else
                {
                    // Draw wall on the bottom
                    x = walls[w].c1 % size;
                    y = walls[w].c1 / size;
                    g.drawLine(x * gradient, (y + 1) * gradient, (x + 1) * gradient, (y + 1) * gradient);   
                }
            }
        }

        // Draw path
        if (showPath)
        {
            // Iterate through path
            for (int i = 0; i < path.length; i++)
            {
                // Convert to 2-dimensional coordinates
                x = path[i] % size;
                y = path[i] / size;

                // Draw small, green circle at the center of each cell in path
                g.setColor(Color.green);
                g.fillOval(x * gradient + (int)(gradient * 0.333), y * gradient + (int)(gradient * 0.333), (int)(gradient * 0.333), (int)(gradient * 0.333));
                g.setColor((Color.black));
                g.drawOval(x * gradient + (int)(gradient * 0.333), y * gradient + (int)(gradient * 0.333), (int)(gradient * 0.333), (int)(gradient * 0.333));
            }
        }
        
        // Draw cursor

        // Convert to 2-deminsional coordinates
        x = cursor % size;
        y = cursor / size;

        // Draw large blue circle at the current cell
        g.setColor(Color.blue);
        g.fillOval(x * gradient + (int)(gradient * 0.15), y * gradient + (int)(gradient * 0.15), (int)(gradient * 0.7), (int)(gradient * 0.7));
        g.setColor((Color.black));
        g.drawOval(x * gradient + (int)(gradient * 0.15), y * gradient + (int)(gradient * 0.15), (int)(gradient * 0.7), (int)(gradient * 0.7));
    }
}