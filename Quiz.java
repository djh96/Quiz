/*Daniel Hodgson
CS 0401
This code will represent taking a quiz.
It will have features such as taking the quiz, displaying the results for the user, displaying the results for each question, and displaying the hardest and easiest questions
*/

//import special classes
import java.util.Scanner;
import java.util.ArrayList;	
import java.io.*;  
public class Quiz
{
	//Create global variables that can be used in any method:
	//ArrayList of type Question to hold all the Question objects
	//Scanner object to read in input
	public static ArrayList<Question> qArray = new ArrayList<Question>();
	public static Scanner keyboard = new Scanner(System.in);

	public static void main(String[] args) throws IOException
	{
		String file = "";
		//Get the file that the user inputs
		if(args.length > 0)
		{
			file = setFile(args[0]);
		}
		else
		{
			System.out.println("\nNo file given!  Please type file name after \"java Quiz\"");	
			System.exit(0);
		}

		//Get the number of questions from a method that creates all of the questions
		int numQ = createQuestions(file);

		//For every question
		for(int i = 0; i < numQ; i++) 
		{
			//Print question number
			System.out.println("Question " + i + ":");
			
			//Creates Question object that is equal to the equivalent question in the ArrayList
			Question q = qArray.get(i);
			
			//Print the question
			System.out.println(q.getQuestion()); 
			
			//For every element of the answers array, or for every answer
			for(int j = 0; j < q.getArrayLength(); j++) 
			{
				//Print the answer number and the answer choice
				System.out.print("(" + j + "): ");
				System.out.println(q.getArrayElement(j));
			}

			//Get the user's guess and set it to a value
			int input = getAnswer(q.getArrayLength()-1);
			
			//Check the user's answer by passing the input and the Question object and set the return value equal to a boolean variable
			boolean correct = checkAnswer(input, q);
			
			//Set the user's guess equal to the Object variable guessed
			q.setGuessed(input);

			//If the user got the answer correct, call the correct method to increase both the times tried and the times correct
			//Else call the incorrect method to only increase the times tried
			if(correct == true)
			{
				q.correct();
			}
			else
			{
				q.incorrect();
			}
			//Add blank line for formatting purposes
			System.out.println();
		}

		//Save the updated results to a file
		saveToFile(file);

		System.out.println("Thanks for taking the quiz!");
		System.out.println("Here are your results:\n\n");

		//Call methods to display the user's results, the cumulative results, the easiest question, and the hardest question
		displayUserQuestions();
		displayCumulativeQuestions();
		easiest();
		hardest();
	}

	//Method that will return a valid file name to the main method
	public static String setFile(String input)
	{
		File myFile = new File(input);
		if(myFile.exists())
		{

		}
		else
		{
			System.out.println("\nCannot find file!");
			System.exit(0);
		}

		return input;
	}

	//Method createQuestions will read in text from a file and store the input in object variables
	// @param String file - the file name
	public static int createQuestions(String file) throws IOException
	{
		//Create File and Scanner objects
		File myFile = new File(file);
		Scanner inputFile = new Scanner(myFile);
		
		//Create a variable to hold the question
		String question;

		//while the file has another line
		while(inputFile.hasNext())
		{
			//Set the question from the next line
			question = inputFile.nextLine();
			
			//Create a Question object with an array the size of the next int
			Question q = new Question(inputFile.nextInt());
			
			//Set the question to the object question variable
			q.setQuestion(question);
			
			//Advance the cursor
			inputFile.nextLine();

			//For every answer
			for(int i = 0; i < q.getArrayLength(); i++) 
			{
				//Set the next line input equal to the corresponding element in the answers array
				q.setArrayElement(inputFile.nextLine(), i);
			}

			//Set the correct answer, times tried, and times correct equal to the next int
			q.setCorrectAnswer(inputFile.nextInt());
			q.setTimesTried(inputFile.nextInt());
			q.setTimesCorrect(inputFile.nextInt());
			
			//Advance the cursor
			inputFile.nextLine();
			
			//Add the new Question object to the ArrayList
			qArray.add(q);
		}	
		//Close the file
		inputFile.close();

		//Return the size of the ArrayList
		return qArray.size();
	}

	//Method getAnswer will get the user's answer and make sure it is value
	// @param int size - the size of the array of answers
	public static int getAnswer(int size)
	{
		//Create variable to hold the user's answer
		int answer = 1;

		//Try to see if the user's input is an integer
		try
		{
			System.out.print("What is your answer? Enter a number > ");
			answer = Integer.parseInt(keyboard.next());
		}
		//If the user's input is not an integer, call the method again
		catch(Exception e)
		{
			answer = getAnswer(size);
		}

		//If the answer is not in the range of the answer choices, call the method again
		if(answer < 0 || answer > size)
		{
			answer = getAnswer(size);	
		}
		//Return the answer
		return answer;
	}

	//Method checkAnswer checks the users answer to see if it is correct and returns a boolean value of if it is correct or not
	// @param int answer - the int the user chose
	// @param Question q - the question object
	public static boolean checkAnswer(int answer, Question q)
	{
		boolean test = false;
		//If user answer is equal to the correct answer, change the value to be true
		if(answer == q.getCorrectAnswer()) 
		{
			test = true;
		}
		
		//Return the boolean value
		return test;
	}

	//Method saveToFile will save all of the information to a file name that is sent to the method
	// @param String file - the file name it will be saved to 
	public static void saveToFile(String file) throws IOException
	{
		//Write all of the current values to the file
		PrintWriter writer = new PrintWriter(file);
		
		//For every question in the ArrayList
		for(int i = 0; i < qArray.size(); i++) 
		{
			//Get the quesiton at each index
			//Write the following values to the file in this order:
			//The question, the number of answers, the answers, the correct answer as an int, the times tried, and the times correct
			//Then close the file

			Question q = qArray.get(i);
			writer.println(q.getQuestion());
			writer.println(q.getArrayLength());
			for(int j = 0; j < q.getArrayLength(); j++) 
			{
				writer.println(q.getArrayElement(j));
			}
			writer.println(q.getCorrectAnswer());
			writer.println(q.getTimesTried());
			writer.println(q.getTimesCorrect());
		}
		writer.close();	
	}
	//Method displayUserQuestions will display the question, the actual answer, and the user's answer as well as if they are correct or not
	public static void displayUserQuestions()
	{
		//Create variables to store the number of right questions, wrong questions, and the percent
		double numRight = 0, numWrong = 0;
		double percent = 0;

		//For every question that was asked
		for(int i = 0; i < qArray.size(); i++) 
		{
			//Get the question object at each index
			//Print the question
			//Print the correct answer
			//Print the user's answer
			//Print if the user got the answer right or wrong
			Question q = qArray.get(i);
			System.out.println("\nQuestion: " + q.getQuestion());
			System.out.println("Answer: " + q.getArrayElement(q.getCorrectAnswer()));
			System.out.println("Player Guess: " + q.getArrayElement(q.getGuessed()));
			if(q.getCorrectAnswer() == q.getGuessed()) 
			{
				System.out.println("	Result: CORRECT! Great Work!");
				numRight++;
			}
			else
			{
				System.out.println("	Result: INCORRECT! Remember the answer for next time!");
				numWrong++;
			}
		}	

		//Print the overall performance (number right, number wrong, and percentage right)	
		System.out.println("Your overall performance was:");
		System.out.printf("	Right:  %.0f\n", numRight);
		System.out.printf("	Wrong:  %.0f\n", numWrong);
		percent = (numRight/(numRight+numWrong)) * 100;
		System.out.printf("	Pct:    %.1f%%\n", percent);
	}
	//Method displayCumulativeQuestions will display the question and the number of times it has been tried and been correct
	public static void displayCumulativeQuestions()
	{
		System.out.println("Here are some cumulative statistics INCLUDING your answers:\n\n");
		
		//Create variable to store the percent
		double percent = 0;

		//For every question in the array
		for(int i = 0; i < qArray.size(); i++) 
		{
			//Get the question object for each index
			//Set the times tried and times correct
			//Print the question
			//Print the times tried, times correct, and percent correct
			Question q = qArray.get(i);

			System.out.println("Question: " + q.getQuestion());
			System.out.println("	Times Tried:      " + q.getTimesTried());
			System.out.println("	Times Correct:    " + q.getTimesCorrect());
			q.setPercent();
			System.out.printf("	Percent Correct:  %.1f%%\n", q.getPercent());
		}		
	}

	//Method hardest will find the hardest question and display the question results to the user
	public static void hardest()
	{
		System.out.println("\nHardest Question:\n");
		//Create variables to hold the minimum percent and the index of the hardest question
		double minPct = 100;
		int hardQ = 0;
		
		//For every question object
		for(int i = 0; i < qArray.size(); i++) 
		{
			//Get the question object at each index
			//Set the percent
			//Get the percent and compare it to the current lowest percent
			//If the percent is lower, set it as the lowest percent and set the index as the hardest question
			Question q = qArray.get(i);
			q.setPercent();
			if(minPct > q.getPercent()) 
			{
				minPct = q.getPercent();
				hardQ = i;
			}
		}
		//Get the question object that had the hardest question
		//Print the question, times tried, times correct, and percent correct
		Question q = qArray.get(hardQ);
		System.out.println("Question: " + q.getQuestion());
		System.out.println("	Times Tried:      " + q.getTimesTried());
		System.out.println("	Times Correct:    " + q.getTimesCorrect());
		System.out.printf("	Percent Correct:  %.1f%%\n", q.getPercent());
	}

	//Method easiest will find the easiest question and display the question results to the user
	public static void easiest()
	{
		System.out.println("\nEasiest Question:\n");
		//Create variables to hold the maximum percent and the index of the easiest question

		double maxPct = 0;
		int easyQ = 0;

		//For every question object

		for(int i = 0; i < qArray.size(); i++) 
		{
			//Get the question object at each index
			//Set the percent
			//Get the percent and compare it to the current highest percent
			//If the percent is higher, set it as the highest percent and set the index as the easiest question
			Question q = qArray.get(i);
			q.setPercent();
			if(maxPct < q.getPercent()) 
			{
				maxPct = q.getPercent();
				easyQ = i;
			}
		}

		//Get the question object that had the hardest question
		//Print the question, times tried, times correct, and percent correct
		Question q = qArray.get(easyQ);
		System.out.println("Question: " + q.getQuestion());
		System.out.println("	Times Tried:      " + q.getTimesTried());
		System.out.println("	Times Correct:    " + q.getTimesCorrect());
		System.out.printf("	Percent Correct:  %.1f%%\n", q.getPercent());	
	}
}