package com.example.venky.tictactoe;

import java.util.Random;

public class AIPlayer extends Object {
	
	private int difficulty;
	private GameState gs;
	private Random easygen = new Random();
	// 1 = easy
	// 2 = medium 
	// 3 = hard

	
	public AIPlayer(int d)
	{
		//set the difficulty when you create the AI Player
		difficulty = d;
	}
	
	public void setDifficulty(int sd)
	{
		difficulty = sd;
	}
	
	//take the state
	//decide upon best move based on difficulty
	//return a tuple of the player num and the 
	//decision made by the function (sector)
	//use the tuple in GameState.updateState()
	
	public Tuple aiMove(GameState cState)
	{
		int decision = -1;
		gs = cState;
		
		if(difficulty == 3)
		{
			decision = hardLogic(cState.getRawState());
		}
		
		if(difficulty == 2)
		{
			decision = mediumLogic(cState.getRawState());
		}
		
		if(difficulty == 1)
		{
			decision = easyLogic(cState.getRawState());
		}
			
		//call AI player 2
		//user is player 1

		return new Tuple(2, decision);

	}
	
	private int hardLogic(int[][] board)
	{

		//logic (win > block > proceed > fork)
		
		
		//does the ai have a winning move?
		if(gs.hasConcecutiveWithWinner(2) != -1)
		{
			//if yes then take it
			return gs.hasConcecutiveWithWinner(2);
			
		}else
		{
			//if no then check if player 1 does
			if(gs.hasConcecutiveWithWinner(1) != -1)
			{
				//if yes then block by moving to their position
				return gs.hasConcecutiveWithWinner(1);
			}
		}
		
		return easyLogic(gs.getRawState());
		

	}


	private int mediumLogic(int[][] board)
	{

		//logic (win > block > proceed > fork)
		
		
		//does the ai have a winning move?
		if(gs.hasConcecutiveWithWinner(2) != -1)
		{
			//if yes then take it
			return gs.hasConcecutiveWithWinner(2);
			
		}else
		{
			return easyLogic(gs.getRawState());
		}

	}

	/**
	 * Function to return random move
	 * @param board
	 * @return
	 */
	private int easyLogic(int[][] board)
	{
		
		int a;
		int b;
		
		do
		{
			a = easygen.nextInt(3);
			b = easygen.nextInt(3);
			//System.out.println("heere : " + a + " " + b);
			
		}while(board[a][b] != 0);
		//logic
		
		return this.indexToSector(a, b);
	}

	/**
	 * // map 2D matrix to 1D
	 * @param a row
	 * @param b column
	 * @return index
	 */
	private int indexToSector(int a, int b)
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
	

	
	

}
