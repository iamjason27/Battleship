
public class game 
{

	private Board userBoard;
	private Board aiBoard;
	private Boolean userTurn = false;
	
	
	public game()
	{
		userBoard = new Board();
		aiBoard = new Board();
	}
	
	public void aiMove()
	{
	    if(!userTurn)
	    {
	   int row	= (int)(Math.random() * 20);
	   int col =  (int)(Math.random() * 20);
	   aiBoard.oneTurn(row,col);
	   userTurn = true;
	    }
	  
	}
	
	public void oneGameTurn()
	{
		aiMove();
		if(userTurn)
		{	
			userBoard.oneTurn();
		}
		  userTurn = false;;
		  
		
		isGameOver();
	}
	
	public void isGameOver()
	{
		int[][] theAiBoard = aiBoard.getBoard();
		int[][] theUserBoard = userBoard.getBoard();
		if(isAiBoardDestroyed(theAiBoard))
		{
			System.out.println("Congratulations! You have won the game!");
		}
		if(isUserBoardDestroyed(theUserBoard))
		{
			System.out.println(" You have lost the game! All your ships have been destroyed. ");
		}
		
	}
	
	private boolean isAiBoardDestroyed(int[][] board)
	{
		for(int i = 0; i < board.length;i++)
		{
			for(int j = 0 ; j < board[i].length;j++)
			{
				if(board[i][j] >1)
					return false;
			}
		}
		return true;
	}
	
	private boolean isUserBoardDestroyed(int[][] board)
	{
		for(int i = 0; i < board.length;i++)
		{
			for(int j = 0 ; j < board[i].length;j++)
			{
				if(board[i][j] >1)
					return false;
			}
		}
		return true;
	}
	
	
	
	
	
	

	
	
			
	
}
