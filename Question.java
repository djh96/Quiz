/*Daniel Hodgson
CS 0401
This code will represent Question class.
It will have features such as creating instance variables and manipulating those instance variables
*/
public class Question
{
	//Create private variables that all Question objects will have
	private String question;
	private int correct_answer, times_tried, times_correct, guessed;
	private String[] answers;
	private double percent = 0.0;

    /**
     * Constructor, sets the size of the string array
     * @param int size - the size of the string array
     */

	public Question(int size)
	{
		answers = new String[size];
	}

    /**
     * If the user answered correctly, this method is called
     * Increases both times_tried and times_correct
     */
	public void correct()
	{
		times_tried++;
		times_correct++;
	}

    /**
     * If the user answered incorrectly, this method is called
     * Increases only times_tried
    */
	public void incorrect()
	{
		times_tried++;
	}

    /**
     * Set what int the user guessed in the guessed variable
     * @param int i - the int the user guessed
     */
	public void setGuessed(int i)
	{
		guessed = i;
	}

    /**
     * Get the int the user guessed
     */
	public int getGuessed()
	{
		return guessed;
	}

    /**
     * Set the question
     * @param String s - the question
     */
	public void setQuestion(String s)
	{
		question = s;
	}

    /**
     * Get the question
     */	
	public String getQuestion()
	{
		return question;
	}

    /**
     * Get the array length
     */	
	public int getArrayLength()
	{
		return answers.length;
	}

    /**
     * Get the element at the the index specified
     * @param int i = i
     */
	public String getArrayElement(int i)
	{
		return answers[i];
	}

    /**
     * Set an index of the answers array with the string inputted
     * @param String s - the answer
     * @param int i - the index
     */
	public void setArrayElement(String s, int i)
	{
		answers[i] = s;
	}

    /**
     * Set the correct answer
     * @param int i - the int of the correct answer
     */
    public void setCorrectAnswer(int i)
	{
		correct_answer = i;
	}

    /**
     * Get the correct answer
     */	
	public int getCorrectAnswer()
	{
		return correct_answer;
	}

    /**
     * Set the times_tried
     * @param int i - the int of the correct answer
     */
	public void setTimesTried(int i)
	{
		times_tried = i;
	}

    /**
     * Get the times tried
     */
	public int getTimesTried()
	{
		return times_tried;
	}

    /**
     * Set the times_correct
     * @param int i - the int of the correct answer
     */
	public void setTimesCorrect(int i)
	{
		times_correct = i;
	}

    /**
     * Get the times correct
     */
	public int getTimesCorrect()
	{
		return times_correct;
	}

    /**
     * Set the percent correct
     * @param int i - the int of the correct answer
     */
    public void setPercent()
	{
		percent = ((double) times_correct/( (double) times_tried)) * 100;
	}

    /**
     * Get the percent correct
     */
	public double getPercent()
	{
		return percent;
	}

}