 
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;



public class TicTacToe 
{
	
	private int[][] board=new int [3][3];
	private int turn=1;
	
	
	
	public TicTacToe(int n)
	{
		int[][] newboard=new int [n][n];
		board=newboard;
	}
	
	
	
	public TicTacToe() {}



	public boolean makeMove(int x, int y)

	{
		if(board[x][y]!=0)
			return false;
		else
			{
			board[x][y]=this.turn;
			if (this.turn==1)
				this.turn=2;
			else
				this.turn=1;
			return true;
			}
	}
	
	 
	
	public int turn()
	{
		return this.turn;
	}
	
	
	
	public int gameStatus()
	{
		ArrayList<Integer>winner=new ArrayList<Integer>();
		if (inARow(turn())|| inAColumn(turn()) || inADiagonal1(turn()) || inADiagonal2(turn()) )
			winner.add(turn());
		else if (inARow(oppositeTurn()) || inAColumn(oppositeTurn()) || inADiagonal1(oppositeTurn())||  inADiagonal2(oppositeTurn()))
			winner.add(oppositeTurn()); 
		if (winner.size()==0)
		{
			if (fullBoard())
				return -1;
			else
				return 0;
		}
		if (winner.size()==1)
			return winner.get(0);
		else
			return -1; //both win
	}
	
	
	
	public boolean gameOver()
	{
		if (gameStatus()==0)
			{
				if (fullBoard()==true)
			
					return true;
				else
					return false;
			}
		else
			return true;
	}
	
	
	
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		for (int r=0;r<this.board.length;r++)
			
			for (int c=0;c<this.board.length;c++)
			{
				if (c==this.board.length-1)
					str.append(this.board[r][c]+"\n");
				else
					str.append(this.board[r][c]);
			}
		return str.toString();		
	}
	
	
	
	public void loadBoard(String fileName) 
	{
	    StringBuilder builder = new StringBuilder();
	   
		try (InputStream input = new FileInputStream(fileName)) 
		{
	        BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
		
	        String line;
	        
	        while((line=reader.readLine())!=null)
	        	builder.append(line+"\n");
	        	
	        String[] lines=builder.toString().split("\n");
	       
	       
	        for (int l=0;l<lines.length;l++)
			{
				for (int i=0; i<lines[l].length();i++)
					
				

					this.board[l][i]=Character.getNumericValue(lines[l].charAt(i));
				}
			}
		
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	public void saveBoard(String fileName) 
	{
		try (PrintWriter out = new PrintWriter(fileName)) 
		{
		    for (String line : toString().split("\n"))
		    	{
		    		out.println(line);
		    	}
		}
		catch (IOException e) 
        {
            e.printStackTrace();
        }
	}
	
	
	public int checkPosition(int x, int y)
	{
		if (x<0 || x>board.length-1 || y<0 || y>board[x].length-1)
			return -1;
		else
			return board[x][y];
	}
	
	
	
	
	
	//-----------------------helper methods--------------------------

	
	private boolean fullBoard()
	{
		boolean full=true;
		for (int r=0;r<this.board.length;r++)
			for (int c=0;c<this.board[r].length;c++)
			{
				if (checkPosition(r,c)==0)
					full=false; 
			}
		return full;
	}
	
	
	private boolean inARow(int player)
	{
		boolean win=false;
		
		int count;
		for (int r=0;r<board.length;r++)
		{
			count=0;
			for(int c = 0;c<board[r].length;c++)
				{
					if (checkPosition(r,c)==player)
						count++;
					else
						count=0;
					
					if (count==board.length)
						return true;
				}
		}
		return win;		
	}

	
	private boolean inAColumn(int player)
	{
		boolean win=false;
		int count;
		for (int c=0;c<board.length;c++)
		{
			count=0;
			for(int r = 0;r<board.length;r++)
			{
				if (checkPosition(r,c)==player)
					count++;
				else
					count=0;
			
				if (count==board.length)
					return true;
			}
		}
		return win;		
	}
	
	private boolean inADiagonal1(int player)
	{
		boolean flag=false;
		int i=0;
		int count=0;
		int r=0;
		int c=0;
		
		while (r+i<board.length && c+i<board[r].length)
		{	
			if (board[r+i][c+i]==player)
				
				count++;
				
			else
				count=0;
			
			i++;
		}
		if (count==board.length)
			return true;
		else	
			return flag;
	}
	
	private boolean inADiagonal2(int player)
	{
		boolean flag=false;
		int i=0;
		int count=0;
		int r=board.length-1;
		int c=0;
		while (r-i>=0 && c+i<board[r].length)
		{	
			if (board[r-i][c+i]==player)
				
				count++;
			else
				count=0;
			
			i++;
		}
		if (count==board.length)
			return true;
		else	
			return flag;
	}
	
	private int oppositeTurn()
	{
		if (this.turn==1)
			return 2;
		else
			return 1;
	}
	
}
