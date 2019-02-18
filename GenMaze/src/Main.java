import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;


/*
 * Run as per usual. File pathes are relativ to the project path so in this case Samples/SOMETITLE.txt, 
 * though I guess you can add your own folders to alter said structure.
 */
public class Main
{

	public static void main(String[] args)
	{
//		runPseudoTest();
		Level level = null;

		try
		{
			String path = askForPathToLevel();
			level = new Level(Paths.get(path));
		} catch (IOException | java.util.NoSuchElementException e)
		{
			System.out.println("This path was invalid. Please start again");
			System.exit(0); 
			// would be nicer with reask and such, but I guess
			//this is not the focus here and I find quite okay for a one function programm
		
		}

	    Mazerunner runner = new Mazerunner(level);
	    runner.findAPathAndPrint();
	}

	private static String askForPathToLevel() throws IOException
	{
		String path; 
	    Scanner input = new Scanner(System.in); 

	    System.out.println("Please enter a path too a level: ");
	    path = input.nextLine();
	    input.close(); 
	    return path;
	}
	
	/*
	 * feel free to enter your own files in here
	 */
	private static void runPseudoTest()
	{
		String[] input = {"Samples/input.txt", "Samples/large_input.txt", 
				"Samples/medium_input.txt", "Samples/small_input.txt", 
				"Samples/small_wrap_input.txt", "Samples/sparse_medium.txt"};
		// Yes , "Samples/sparse_large.txt" fails since it has too many possible paths that can be pursued at the same time
		for(String s : input)
		{
			Mazerunner runner;
			try
			{
				System.out.println(s);
				runner = new Mazerunner(new Level(Paths.get(s)));
				runner.findAPathAndPrint();
				System.out.println();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		    
		}
	}

}
