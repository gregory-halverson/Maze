// Gregory Halverson
// Pierce College
// CS 532
// Spring 2014

// Prints a random maze to system printVertical with size determined by first command line argument
public class MazeGen
{
    public static void main(String[] args)
    {
    	// Check for size input from argument list and generate maze
    	if (args.length > 0)
        {
            // Create maze
    		Maze maze = new Maze(Integer.parseInt(args[0]));

            // Output
            System.out.println();
            System.out.println("N = " + maze.getSize()); // Size of maze
            System.out.print(maze.printVertical()); // List of walls in maze
        }
    	else
        {
            // Show usage if no arguments are found
            System.out.println();
    		System.out.println("usage: MazeGen n");
        }
    }
}
