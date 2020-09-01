import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ConnectFour extends TicTacToe 
{
	//field variables
	private final int[][]board=new int[6][6];
	private int turn=1;
        private int[] move=new int[4]; //col,row
        ArrayList<Integer> playerMoved=new ArrayList<>();
        
	
	//default constructor
	public ConnectFour()
	{
		super();
                playerMoved.clear();
	}
	
        
        public int[] lastMove()
        {
            return move;
        }
        
        public boolean undoable()
        {
           
            if (playerMoved.isEmpty())
                return false;
           
            else if (playerMoved.size()==1)
                return true;
          
            else return !Objects.equals(playerMoved.get(playerMoved.size()-1), playerMoved.get(playerMoved.size()-2));
        }
        
        public void undoMove()
        {
            int row=move[1];
            int col=move[0];
            board[row][col]=0;
            if (turn==1)
                 turn=2;
            else
                 turn=1;
            move=new int[2];
           
        }
	
	
	public boolean makeMove(int col)
        {
            boolean valid=false;
            for (int row=board.length-1;row>-1;row--)
            { 
                    if (board[row][col]==0)
                    {
                            board[row][col]=this.turn;
                            
                            move[0]=col; 
                            move[1]=row;
                            playerMoved.add(turn);
                            if (this.turn==1)
                                this.turn=2;
                            else
                                this.turn=1;
                            return true;
                    }
            }
            return valid;
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
			
			for (int c=0;c<this.board[r].length;c++)
			{
				if (c==this.board[r].length-1)
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
	       
	        turn=1;
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
					if (board[r][c]==player)
						count++;
					else
						count=0;
					
					if (count>=4)
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
					if (board[r][c]==player)
					
						count++;
					else
						count=0;
					
					if (count>=4)
						return true;
					}
		}
		return win;		
	}
	
	private boolean inADiagonal1(int player)
	{
		boolean flag=false;
		int i;
		int count;
		for (int r=0;r<board.length;r++)
			for(int c=0;c<board[r].length;c++)
			{
				i=0;
				count=0;
				while (r+i<board.length && c+i<board[r].length)
				{	
					ArrayList<String> s=new ArrayList<String>();
					if (board[r+i][c+i]==player)
						
						{count++;
						s.add(r+i+","+c+i);}
						
					else
						{count=0;
						s.clear();}
					
						
					if (count>=4)
						return true;
					
					i++;
				}
			}
		return flag;
	}
	
	
	private boolean inADiagonal2(int player)
	{
		boolean flag=false;
		int i;
		int count;
		for (int r=board.length-1;r>=0;r--)
			for(int c=0;c<board[r].length;c++)
			{
				i=0;
				count=0;
				while (r-i>=0 && c+i<board[r].length)
				{	
					if (board[r-i][c+i]==player)
						
						count++;
					else
						count=0;
					if (count>=4)
						return true;
					i++;
				}
			}
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
