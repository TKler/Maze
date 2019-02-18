/*
 * Our Agent searching a way through the maze
 */
public class Mazerunner
{
	private Level _level;
	private char[][] _output;
	private int _startHeight, _startWidth;
	private int _xMax, _yMax;
	private Boolean pathFound = null;
	private boolean[][] _visited;
	
	public Mazerunner(Level level)
	{
		_output = level.getCharMap();
		_visited = new boolean[level.getMaxHeight()][level.getMaxWidth()];
		
		_startWidth = level.getStartWidth();
		_startHeight = level.getStartHeight();
		
		_xMax = level.getMaxWidth()-1;
		_yMax = level.getMaxHeight()-1;
		
		_level = level;
	}

	public void findAPathAndPrint()
	{
		pathFound = findPath(_startWidth, _startHeight);
		printResult();
	}
	
	private void printResult()
	{
		assert(pathFound != null);
		
		if(pathFound)
		{
			//repaint S since I X it over
			_output[_startHeight][_startWidth] = 'S';
			for(int i = 0; i < _output.length; i++)
			{
				for(int j = 0; j < _output[0].length; j++)
				{
					System.out.print(_output[i][j] + " ");
				}
				System.out.println();
			}
//			System.out.println(Arrays.deepToString(_output));
		}
		else
		{
			System.out.println("The runnner found no path.");
		}
	}
	
	//there is a multitude of ways to solve PATH S-T
	//be DFS, BFS, A* ...
	//I opted for a simpler approach, pathfinding with avoidance of visited fields
	//Finds all direct paths from start to finish
	//As this is quasi bfs recursion this might run into problems if we have a maze with many possible paths.
	private boolean findPath(int xPosition, int yPosition)
	{
		//finish if in the goalstate
		if(_level.isGoal(xPosition, yPosition))
			return true;
	
		//stop if at an invalid position
		if(!_level.isTraversable(xPosition, yPosition) || _visited[yPosition][xPosition])
			return false;
		
		//mark as visited
		_visited[yPosition][xPosition] = true;;
		
		//go East minding wrapping
		if(findPath(xPosition+1 <= _xMax ? xPosition+1 : 0, yPosition))
		{
			markPosition(xPosition, yPosition);
			return true;
		}
		//go North minding wrapping
		if(findPath(xPosition, yPosition-1 >= 0 ? yPosition-1 : _yMax))
		{
			markPosition(xPosition, yPosition);
			return true;
		}
		//go West minding wrapping
		if(findPath(xPosition-1 >= 0 ? xPosition-1 : _xMax, yPosition))
		{
			markPosition(xPosition, yPosition);
			return true;
		}		
		//go South minding wrapping
		if(findPath(xPosition, yPosition+1 <= _yMax ? yPosition+1 : 0))
		{
			markPosition(xPosition, yPosition);
			return true;
		}
		//if neither direction worked, none will ;)
		return false;
	}

	private void markPosition(int posX, int posY)
	{
		_output[posY][posX] = 'X';
	}
}
