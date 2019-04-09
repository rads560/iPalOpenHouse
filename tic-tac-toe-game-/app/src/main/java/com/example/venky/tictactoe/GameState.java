package com.example.venky.tictactoe;

public class GameState {

	//2d board state
	private int[][] state = new int[3][3];
	//scores 
	private int[] score = new int[2];


	public GameState()
	{
		//set the state to an empty board
		score[0] = 0;
		score[1] = 0;

	}

	/**
	 * Update game board to move
	 * @param t tuple (player number, sector of move)
	 * @return return true if update is made else false
	 */
	public boolean Update(Tuple t)
	{
		//tuple (player number, sector of move)

		switch(t.getSectorValue()){

		case 0:	if(state[0][0] == 0)
		{
			state[0][0] = t.getPlayerValue();
			return true;
		}else
		{
			return false;
		}

		case 1: if(state[0][1] == 0)
		{
			state[0][1] = t.getPlayerValue();
			return true;
		}else
		{
			return false;
		}

		case 2: if(state[0][2] == 0)
		{
			state[0][2] = t.getPlayerValue();
			return true;
		}else
		{
			return false;
		}

		case 3: if(state[1][0] == 0)
		{
			state[1][0] = t.getPlayerValue();
			return true;
		}else
		{
			return false;
		}

		case 4: if(state[1][1] == 0)
		{
			state[1][1] = t.getPlayerValue();
			return true;
		}else
		{
			return false;
		}

		case 5: if(state[1][2] == 0)
		{
			state[1][2] = t.getPlayerValue();
			return true;
		}else
		{
			return false;
		}

		case 6: if(state[2][0] == 0)
		{
			state[2][0] = t.getPlayerValue();
			return true;
		}else
		{
			return false;
		}

		case 7: if(state[2][1] == 0)
		{
			state[2][1] = t.getPlayerValue();
			return true;
		}else
		{
			return false;
		}

		case 8: if(state[2][2] == 0)
		{
			state[2][2] = t.getPlayerValue();
			return true;
		}else
		{
			return false;
		}
		}

		return false;
	}


	public int[][] getRawState()
	{
		return state;
	}

	// map 2D matrix to 1D
	public int indexToSector(int a, int b)
	{

		if(a == 0 && b == 0 )
		{
			return 0;
		}

		if(a == 0 && b == 1 )
		{
			return 1;
		}

		if(a == 0 && b == 2 )
		{
			return 2;
		}

		if(a == 1 && b == 0 )
		{
			return 3;
		}

   		if(a == 1 && b == 1 )
		{
			return 4;
		}

		if(a == 1 && b == 2 )
		{
			return 5;
		}

		if(a == 2 && b == 0 )
		{
			return 6;
		}

		if(a == 2 && b == 1 )
		{
			return 7;
		}

		if(a == 2 && b == 2 )
		{
			return 8;
		}

		return -1;

	}

	/**
	 * Check for any combinations of winning
	 * @return
	 */
	public int checkForWinner()
	{
		if((state[0][0] == 1 && state[0][1] == 1 && state[0][2] == 1) || (state[0][0] == 2 && state[0][1] == 2 && state[0][2] == 2))
		{
			//win
			if(state[0][0] == 1)
			{
				//player 1 win
				this.updateScore(1);
				return 1;
			}else
			{
				//player 2 win
				this.updateScore(2);
				return 2;
			}

		}

		if((state[1][0] == 1 && state[1][1] == 1 && state[1][2] == 1) || (state[1][0] == 2 && state[1][1] == 2 && state[1][2] == 2))
		{
			//win
			if(state[1][0] == 1)
			{
				//player 1 win
				this.updateScore(1);
				return 1;
			}else
			{
				//player 2 win
				this.updateScore(2);
				return 2;
			}
		}

		if((state[2][0] == 1 && state[2][1] == 1 && state[2][2] == 1) || (state[2][0] == 2 && state[2][1] == 2 && state[2][2] == 2))
		{
			//win
			if(state[2][0] == 1)
			{
				//player 1 win
				this.updateScore(1);
				return 1;
			}else
			{
				//player 2 win
				this.updateScore(2);
				return 2;
			}

		}

		if((state[0][0] == 1 && state[1][0] == 1 && state[2][0] == 1) || (state[0][0] == 2 && state[1][0] == 2 && state[2][0] == 2))
		{
			//win
			if(state[0][0] == 1)
			{
				//player 1 win
				this.updateScore(1);
				return 1;
			}else
			{
				//player 2 win
				this.updateScore(2);
				return 2;
			}
		}

		if((state[0][1] == 1 && state[1][1] == 1 && state[2][1] == 1) || (state[0][1] == 2 && state[1][1] == 2 && state[2][1] == 2))
		{
			//win
			if(state[0][1] == 1)
			{
				//player 1 win
				this.updateScore(1);
				return 1;
			}else
			{
				//player 2 win
				this.updateScore(2);
				return 2;
			}
		}

		if((state[0][2] == 1 && state[1][2] == 1 && state[2][2] == 1) || (state[0][2] == 2 && state[1][2] == 2 && state[2][2] == 2))
		{
			//win
			if(state[0][2] == 1)
			{
				//player 1 win
				this.updateScore(1);
				return 1;
			}else
			{
				//player 2 win
				this.updateScore(2);
				return 2;
			}

		}

		if((state[0][2] == 1 && state[1][2] == 1 && state[2][2] == 1) || (state[0][2] == 2 && state[1][2] == 2 && state[2][2] == 2))
		{
			//win
			if(state[0][2] == 1)
			{
				//player 1 win
				this.updateScore(1);
				return 1;
			}else
			{
				//player 2 win
				this.updateScore(2);
				return 2;
			}
		}

		if((state[2][0] == 1 && state[1][1] == 1 && state[0][2] == 1) || (state[2][0] == 2 && state[1][1] == 2 && state[0][2] == 2))
		{
			//win
			if(state[2][0] == 1)
			{
				//player 1 win
				this.updateScore(1);
				return 1;
			}else
			{
				//player 2 win
				this.updateScore(2);
				return 2;
			}

		}

		if((state[0][0] == 1 && state[1][1] == 1 && state[2][2] == 1) || (state[0][0] == 2 && state[1][1] == 2 && state[2][2] == 2))
		{
			//win
			if(state[0][0] == 1)
			{
				//player 1 win
				this.updateScore(1);
				return 1;
			}else
			{
				//player 2 win
				this.updateScore(2);
				return 2;
			}
		}

		return 0;
	}

	/**
	 * Function to reset game
	 */
	public void resetGameState()
	{
		for(int y = 0; y < 3; y++)
		{
			for(int z = 0; z < 3; z++)
			{
				state[y][z] = 0;
			}
		}

	}

	/**
	 * Function to update score .. we did not keep score
	 * @param winner
	 */
	public void updateScore(int winner)
	{
		if(winner == 1)
		{
			//increment player 1 score
			score[0] = score[0] + 1;

		}else
		{
			//increment player 2 score
			score[1] = score[1] + 1;
		}

	}

	public int[] getScore()
	{
		return score;
	}

	/**
	 * Find best next move for player
	 * @param player
	 * @return index of sector
	 */
	public int hasConcecutiveWithWinner(int player)
	{
		//horizontal rows checks
		if((state[0][0] == player && state[0][1] == player && state[0][2] == 0))
		{
			return this.indexToSector(0, 2);
		}
		
		if((state[0][0] == 0 && state[0][1] == player && state[0][2] == player))
		{
			return this.indexToSector(0, 0);
		}
		
		if((state[1][0] == player && state[1][1] == player && state[1][2] == 0))
		{
			return this.indexToSector(1, 2);
		}
		
		if((state[1][0] == 0 && state[1][1] == player && state[1][2] == player))
		{
			return this.indexToSector(1, 0);
		}
		
		if((state[2][0] == player && state[2][1] == player && state[2][2] == 0))
		{
			return this.indexToSector(2, 2);
		}
		
		if((state[2][0] == 0 && state[2][1] == player && state[2][2] == player))
		{
			return this.indexToSector(2, 0);
		}
		
		//vertical row checks
		
		if((state[0][0] == player && state[1][0] == player && state[2][0] == 0))
		{
			return this.indexToSector(2, 0);
		}
		
		if((state[0][0] == 0 && state[1][0] == player && state[2][0] == player))
		{
			return this.indexToSector(0, 0);
		}
		
		if((state[0][1] == player && state[1][1] == player && state[2][1] == 0))
		{
			return this.indexToSector(2, 1);
		}
		
		if((state[0][1] == 0 && state[1][1] == player && state[2][1] == player))
		{
			return this.indexToSector(0, 1);
		}
		
		if((state[0][2] == player && state[1][2] == player && state[2][2] == 0))
		{
			return this.indexToSector(2, 2);
		}
		
		if((state[0][2] == 0 && state[1][2] == player && state[2][2] == player))
		{
			return this.indexToSector(0, 2);
		}
		
		//diagonal checks
		
		if((state[0][2] == player && state[1][1] == player && state[2][0] == 0))
		{
			return this.indexToSector(2, 0);
		}
		
		if((state[0][2] == 0 && state[1][1] == player && state[2][0] == player))
		{
			return this.indexToSector(0, 2);
		}
		
		if((state[0][0] == player && state[1][1] == player && state[2][2] == 0))
		{
			return this.indexToSector(2, 2);
		}
		
		if((state[0][0] == 0 && state[1][1] == player && state[2][2] == player))
		{
			return this.indexToSector(0, 0);
		}
		
		return -1;

	
	}


	/**
	 * Return 9 digit encoded state to print in log
	 * @return [0 if "", 1 if "O", 2 if 1]^9
	 */
	public String getEncodedState(){

		String ans = "";

		for(int i=0;i<state.length;i++){
			for (int j=0;j<state[0].length;j++){
				ans+=state[i][j];
			}
		}

		return ans;

	}

}
