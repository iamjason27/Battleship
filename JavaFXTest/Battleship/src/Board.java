import java.util.Scanner;
public class Board 
{

	private int[][] theBoard;
	
	public Board()
	{
		theBoard = new int[20][20];
		for(int i = 0; i < theBoard.length;i++)
		{
			for(int j = 0; j < theBoard[i].length;j++)
			{
				theBoard[i][j] = 0;
			}
		}
	}
	
	public int[][] getBoard()
	{
		return theBoard;
	}
	
	public void addShip(ship theShip, int row, int col)
	{
		int length = theShip.getLength();
		if(theShip.getDirection().equals("horizontal"))
		{
			for(int i = 0; i < length;i++)
			{
				if(theBoard[row][(int)(col)-(int)('A')+i] >1)
				{
					System.out.println("ship cannot be placed");
				}
				else
				theBoard[row][(int)(col)-(int)('A')+i]+=2;
			}
		}
		if(theShip.getDirection().equals("vertical"))
		{
			for(int i = 0 ;i< length ; i++)
			{
				if(theBoard[row+i][(int)(col)-(int)('A')] >1)
				{
					System.out.println("ship cannot be placed");
				}
				
				else
					theBoard[row+i][(int)(col)-(int)('A')]+=2;
			}
		}
	}
	
	
	private void scan(int row, char col)
	{
		
		if(col > 'S')
		{
		if(col < 'B')
			{
			if(row > 20)
			{
				if(row < 0)
				{
			
			System.out.println("Choose a valid columhn");
				}
			}
			
			}
		}
		else
		{
		    if(theBoard[row][(int)col - (int)('A')] >1)
		    	System.out.println("Ship found in area!");
		    else 
		    {
		    	if(theBoard[row+1][(int)col - (int)('A')] >1)
		    	{
		    		System.out.println("Ship found in area!");
		    	}
		    	else 
			    {
			    	if(theBoard[row+1][(int)col - (int)('A')-1] >1)
			    	{
			    		System.out.println("Ship found in area!");
			    	}
			    	else 
				    {
				    	if(theBoard[row+1][(int)col - (int)('A')+1] >1)
				    	{
				    		System.out.println("Ship found in area!");
				    	}
				    	else 
					    {
					    	if(theBoard[row][(int)col - (int)('A')+1] >1)
					    	{
					    		System.out.println("Ship found in area!");
					    	}
					    	else 
						    {
						    	if(theBoard[row][(int)col - (int)('A')-1] >1)
						    	{
						    		System.out.println("Ship found in area!");
						    	}
						    	else 
							    {
							    	if(theBoard[row-1][(int)col - (int)('A')] >1)
							    	{
							    		System.out.println("Ship found in area!");
							    	}
							    	else 
								    {
								    	if(theBoard[row-1][(int)col - (int)('A')+1] >1)
								    	{
								    		System.out.println("Ship found in area!");
								    	}
								    	else 
									    {
									    	if(theBoard[row-1][(int)col - (int)('A')-1] >1)
									    	{
									    		System.out.println("Ship found in area!");
									    	}
									    }
								    }
							    }
						    }
					    }
				    }
			    }
		    } 	
		}
	}
	
	private void superTorpedo(int row)
	{
		if(row < 0)
		{
			if(row > 20)
			{
				System.out.println("Choose a valid row");
			}
		}
		
		else
		{
			for(int i =0 ; i < 21; i++)
			{
				theBoard[row][i]--;
			}
		}
		
	}
	
	private void superTorpedo(char col)
	{
		
		if(col > 'T')
		{
			if(col < 'A')
			{
				System.out.println("Choose a valid column");
			}
		}
		
		else
		{
			for(int i = 0 ; i < 21 ; i++)
			{
				if(theBoard[i][(int)(col)-(int)('A')]>1)
				theBoard[i][(int)(col)-(int)('A')]--;
			}
		}
		
	}
	
	private void shield(int row, char col)
	{
		if(theBoard[row][(int)(col)-(int)('A')] == 2)
		theBoard[row][(int)(col)-(int)('A')]++;
		else
			System.out.println("This is not a valid move");
	}
	
	private void torpedo(int row,char col)
	{
		theBoard[row][(int)(col)-(int)('A')]--;
	}
	
	private void torpedo(int row,int col)
	{
		theBoard[row][col]--;
	}
	
	
	private void mine(int row, char col)
	{
		if(col > 'S')
		{
		if(col < 'B')
			{
			if(row > 20)
			{
				if(row < 0)
				{
			
			System.out.println("Choose a valid place to put the mine in.");
				}
			}
			
			}
		}
		else
		{
			if(theBoard[row][(int)col - (int)('A')] >1)
			theBoard[row][(int)col - (int)('A')]--;
			
			if(theBoard[row][(int)col - (int)('A')-1]>1)
			theBoard[row][(int)col - (int)('A')-1]--;
			
			if(theBoard[row][(int)col - (int)('A')+1]>1)
			theBoard[row][(int)col - (int)('A')+1]--;
			
			if(theBoard[row-1][(int)col - (int)('A')]>1)
			theBoard[row-1][(int)col - (int)('A')]--;
			
			if(theBoard[row-1][(int)col - (int)('A')-1]>1)
			theBoard[row-1][(int)col - (int)('A')-1]--;
			
			if(theBoard[row-1][(int)col - (int)('A')+1]>1)
			theBoard[row-1][(int)col - (int)('A')+1]--;
			
			if(theBoard[row+1][(int)col - (int)('A')]>1)
			theBoard[row+1][(int)col - (int)('A')]--;
			
			if(theBoard[row+1][(int)col - (int)('A')-1]>1)
			theBoard[row+1][(int)col - (int)('A')-1]--;
			
			if(theBoard[row+1][(int)col - (int)('A')+1]>1)
			theBoard[row+1][(int)col - (int)('A')+1]--;
		}
	}
	
	public void getPowerup()
	{
		
		int chance = (int) Math.ceil(Math.random() * 100);
		if(chance <= 10)
		{
			if(chance ==1)
			{
				getMineCoords();
			}
			if(chance >= 2)
			{
				if(chance < 5)
				{
					getSonarCoords();
				}
			}
			
			if(chance >=5)
			{
				if(chance <= 9)
				{
					getShieldCoords();
				}
			}
			if(chance == 10)
			{
				getNukeCoords();
			}
		}
	}
	
	private void getMineCoords()
	{
		Scanner theScanner = new Scanner(System.in);
		System.out.println("You have gotten a mine! This is a 3x3 clear. Please type in the row you want to bomb." );
		int theRow = theScanner.nextInt();
		System.out.println("Type in the column");
		String theCol = theScanner.nextLine();
	    if(theCol.length()>1)
	    {
	    	theScanner.close();
	    	getMineCoords();
	    }
		this.mine(theRow, theCol.charAt(0));
		theScanner.close();
	}
	private void getSonarCoords()
	{
		Scanner theScanner = new Scanner(System.in);
		System.out.println("You have gotten your sonar functional,but only for one use! This scans a 3x3 area for ships. Please type in the row you want to scan." );
		int theRow = theScanner.nextInt();
		System.out.println("Type in the column");
		String theCol = theScanner.nextLine();
	    if(theCol.length()>1)
	    {
	    	theScanner.close();
	    	getSonarCoords();
	    }
	    this.scan(theRow, theCol.charAt(0));
	    theScanner.close();
	}
	private void getShieldCoords()
	{
		Scanner theScanner = new Scanner(System.in);
		System.out.println("You have gotten enough power to activate shields! This gives an extra hit to one part of your ship. Please type in the row you want to shield." );
		int theRow = theScanner.nextInt();
		System.out.println("Type in the column");
		String theCol = theScanner.nextLine();
	    if(theCol.length()>1)
	    {
	    	theScanner.close();
	    	getShieldCoords();
	    }
	    this.shield(theRow, theCol.charAt(0));
	    theScanner.close();
	}
	
	private void getNukeCoords()
	{
		Scanner theScanner = new Scanner(System.in);
		System.out.println("you have been granted a nuke! This will clear out an entire row or column. Please specify if you want to clear a row or a column." );
		String spec = theScanner.nextLine();
		if(spec.equals("row"))
		{
			System.out.println("You have chosen to take out an entire row! Please type in the row.");
			int row = theScanner.nextInt();
		    this.superTorpedo(row);
		    theScanner.close();
		}
		if(spec.equals("column"))
		{
			System.out.println("You have chosen to take out an entire row! Please type in the row.");
		    String col = theScanner.nextLine();
		    if(col.length()>1)
		    {
		    System.out.println("please choose a valid comunn");	
		    theScanner.close();
		    getNukeCoords();
		    }
		    this.superTorpedo(col.charAt(0));
		    theScanner.close();
		}
	   }
	
	public void oneTurn()
	{
		Scanner theScanner = new Scanner(System.in);
		this.getPowerup();
		System.out.println("Please type the row you want to hit.");
		int row = theScanner.nextInt();
		System.out.println("Please type the column you want to hit.");
		String col = theScanner.nextLine();
		if(col.length()>1)
		{
		System.out.println("this is not a valid column.Please choose a valid column");
		 col = theScanner.nextLine();
		}
		this.torpedo(row, col.charAt(0));
		theScanner.close();
		
	}
	
	public void oneTurn(int row, int col)
	{
		this.torpedo(row, col);
	}

	
	
	
	
	
}
