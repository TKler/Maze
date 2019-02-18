import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

/**
 * A Maze is 2D array of - at creation - specified dimensions. 
 * The content of the array is modelled as a boolean where 
 * false stands for a traversable passage way in the maze, 
 * true for a wall. 
 * @author felix
 */
public class Level
{
	private boolean[][] _maze;
	private int _height, _width;
	private int _goalWidth, _goalHeight;
	private int _startWidth, _startHeight;
	private char[][] _charMap = null; //basis for the later output
	
	public Level(Path path) throws IOException
	{
		initializeMaze(path);
	}
	
	private void initializeMaze(Path path) throws IOException
	{
		Scanner scanner = new Scanner(path);
		
		//Line1
        _width =  scanner.nextInt();
        _height = scanner.nextInt();
        _maze = new boolean[_height][_width];
     
        //Line2
        _startWidth = scanner.nextInt();
        _startHeight = scanner.nextInt();
       
        //Line3
        _goalWidth = scanner.nextInt();
        _goalHeight = scanner.nextInt();
      
        //Line3+ till end
        for(int curHeight = 0; curHeight < _height; curHeight++)
        {
        	for(int curWidth = 0; curWidth < _width; curWidth++)
        	{
        		_maze[curHeight][curWidth] = scanner.nextInt() == 1;
        	}
        }
      // this could also be handle more elegantly with multiply and mod and I suspect there is a good stream solution, but is simple enough to KISS it.
        scanner.close();
	}

	/*
	 * return whether or not the agent can move there
	 */
	public boolean isTraversable(int xPosition, int yPosition)
	{
		// you can also do exceptions here, I prefer assert for internal stuff.
		assert(xPosition >= 0 && xPosition < _width);
		assert(yPosition >= 0 && yPosition < _height);
		
		return !_maze[yPosition][xPosition];
	}
	
	public boolean isGoal(int xPosition, int yPosition)
	{
		return (xPosition == _goalWidth) && (yPosition == _goalHeight);
	}

	public char[][] getCharMap()
	{
		if(_charMap == null)
			initializeCharMap();
		
		return _charMap;
	}

	private void initializeCharMap()
	{
		// create and fill charMap with ' '
		_charMap = new char[_height][_width];
		for(int i = 0; i < _charMap.length; i++)
		{
			Arrays.fill(_charMap[i], ' ');
		}
		
		// add # at the appropriat places
		for(int curHeight = 0; curHeight < _height; curHeight++)
        {
        	for(int curWidth = 0; curWidth < _width; curWidth++)
        	{
        		if(_maze[curHeight][curWidth])
        			_charMap[curHeight][curWidth] = '#';
        	}
        }
		
		//add S and E
		_charMap[_startHeight][_startWidth] = 'S';
		_charMap[_goalHeight][_goalWidth] = 'E';
	}

	public int getStartWidth()
	{
		return _startWidth;
	}

	public int getStartHeight()
	{
		return _startHeight;
	}

	public int getMaxWidth()
	{
		return _width;
	}
	
	public int getMaxHeight()
	{
		return _height;
	}
}
