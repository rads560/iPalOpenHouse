package com.example.venky.tictactoe;

public class Tuple {

	private int PlayerValue;
	private int SectorValue;

	public Tuple(int s, int i)
	{
		this.PlayerValue = s;
		this.SectorValue = i;
	}

	public void resetTuple()
	{
		this.PlayerValue = -1;
		this.SectorValue = -1;
	}

	public int getPlayerValue()
	{
		return this.PlayerValue;
	}

	public int getSectorValue(){
		return this.SectorValue;
	}

	public void setTuple(int x, int sec)
	{
		this.PlayerValue = x;
		this.SectorValue = sec;
	}

}
