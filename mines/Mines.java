package mines;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class Mines {
	
	private int height,width;
	private Zone[][] zone;
	private List<Zone> openslot= new ArrayList<>();
	private boolean showAll;
	public Mines(int height, int width, int numMines)
	{
		this.height=height;
		this.width=width;

		zone=new Zone[height][width];//we define our matrix "game board" as 
		for(int i=0; i<height; i++)
			for(int j=0; j<width; j++)
				zone[i][j] = new Zone();
		Random RandomMine = new Random();
		int i, j;
		for(int mCount = 0; mCount < numMines; mCount++) {// we put our mines in  a random places in our graph
			i = RandomMine.nextInt(height);
			j = RandomMine.nextInt(width);
			while(zone[i][j].ifMine) {// in case our i , j are at the same index that we got bomb already
				// we will change it until we will receive a index without a bomb
				i = RandomMine.nextInt(height);
				j = RandomMine.nextInt(width);
			}
			zone[i][j].ifMine = true;
			updateNeighbours(i, j);
		}
	}
	
	public boolean addMine(int i, int j) 
	//we add mines to our game
	//in case we try to put mine in a slot that already has been set as mine , we will receive exception 
	// otherwise we will update the slot and update all his neighbors (we add the closemine numbers to them)
	{
		if(zone[i][j].ifMine==true) 
			throw new IllegalArgumentException();
		if(i>=height||j>=width)
			throw new IndexOutOfBoundsException();
		zone[i][j].ifMine=true;

		updateNeighbours(i,j);
		return true;
		
	}

	public boolean open(int i, int j) 
	// we will open the slots when an action has accord
	// if we press slot that is not a mine , we will open all his neighbors that are not mines in recursion 
	{
		if(i>=height||j>=width)
			throw new IndexOutOfBoundsException();
		if(zone[i][j].ifOpen==true) return true;
		
		if(zone[i][j].ifMine==true)
		{
			return false;
		}
		if(!zone[i][j].ifMine && zone[i][j].Closemines!=0) {
			zone[i][j].ifOpen = true;
			return true;
		}
		if(zone[i][j].Closemines==0) zone[i][j].ifOpen=true;
		openslot.add(zone[i][j]);// we open the slot 
		
		// we check in recursion if the neighbors are not bombs and if we are allowed to open them 
		if (i - 1 >= 0 && j - 1 >= 0 && !zone[i - 1][j - 1].ifMine) 
			if (!openslot.contains(zone[i - 1][j - 1]))
				open(i - 1, j - 1);
		if (i - 1 >= 0 && !zone[i - 1][j].ifMine)
			if (!openslot.contains(zone[i - 1][j]))
				open(i - 1, j);
		if (i - 1 >= 0 && j + 1 < width && !zone[i - 1][j + 1].ifMine)
			if (!openslot.contains(zone[i - 1][j + 1]))
				open(i - 1, j + 1);

		if (j - 1 >= 0 && !zone[i][j - 1].ifMine)
			if (!openslot.contains(zone[i][j - 1]))
				open(i, j - 1);
		if (j + 1 < width && !zone[i][j + 1].ifMine)
			if (!openslot.contains(zone[i][j + 1]))
				open(i, j + 1);

		if (i + 1 < height && j - 1 >= 0 && !zone[i + 1][j - 1].ifMine)
			if (!openslot.contains(zone[i + 1][j - 1]))
				open(i + 1, j - 1);
		if (i + 1 < height && !zone[i + 1][j].ifMine)
			if (!openslot.contains(zone[i + 1][j]))
				open(i + 1, j);
		if (i + 1 < height && j + 1 < width && !zone[i + 1][j + 1].ifMine)
			if (!openslot.contains(zone[i + 1][j + 1]))
				open(i + 1, j + 1);
		return true;
	}

	
	
	private void updateNeighbours(int i, int j) {//we update the negibhors of each slot
		// we update the counter of mines that are nested near our slot .
		
		if (i - 1 >= 0 && j - 1 >= 0 && !zone[i - 1][j - 1].ifMine)
			zone[i - 1][j - 1].Closemines=zone[i - 1][j - 1].Closemines + 1;
		if (i - 1 >= 0 && !zone[i - 1][j].ifMine)
			zone[i - 1][j].Closemines=zone[i - 1][j].Closemines + 1;
		if (i - 1 >= 0 && j + 1 < width && !zone[i - 1][j + 1].ifMine)
			zone[i - 1][j + 1].Closemines=zone[i - 1][j + 1].Closemines + 1;

		if (j - 1 >= 0 && !zone[i][j - 1].ifMine)
			zone[i][j - 1].Closemines=zone[i][j - 1].Closemines + 1;
		if (j + 1 < width && !zone[i][j + 1].ifMine)
			zone[i][j + 1].Closemines=zone[i][j + 1].Closemines + 1;

		if (i + 1 < height && j - 1 >= 0 && !zone[i + 1][j - 1].ifMine)
			zone[i + 1][j - 1].Closemines=zone[i + 1][j - 1].Closemines + 1;
		if (i + 1 < height && !zone[i + 1][j].ifMine)
			zone[i + 1][j].Closemines=zone[i + 1][j].Closemines + 1;
		if (i + 1 < height && j + 1 < width && !zone[i + 1][j + 1].ifMine)
			zone[i + 1][j + 1].Closemines=zone[i + 1][j + 1].Closemines + 1;	
	}
	

	public void toggleFlag(int x, int y) 
	//in case our flag is set as true we will remove it and set it as false 
	// otherwise we will set it as true 
	{
		if(zone[x][y].ifFlag==true) zone[x][y].ifFlag=false;
		else if(zone[x][y].ifFlag!=true) zone[x][y].ifFlag=true;
	}

	public boolean isDone() 
// we check if all our slots are opened ( that are not mines )
    {
        int i,j;
        for(i=0;i<height;i++)
            for(j=0;j<width;j++)
                if(zone[i][j].ifMine==false && zone[i][j].ifOpen==false) return false;

        return true;
    }
	public String get(int i, int j) {
		
		if(zone[i][j].ifFlag && !showAll)
			return "F";
		if((!zone[i][j].ifOpen && !showAll) || (zone[i][j].ifMine && !showAll))
			return ".";
		if(zone[i][j].ifOpen || showAll)
			if(zone[i][j].ifMine)
				return "X";
		if((zone[i][j].ifOpen || showAll) && zone[i][j].Closemines!=0)
				return String.valueOf(zone[i][j].Closemines);
		return " ";
	}
	

	public void setShowAll(boolean showAll) 
	{
		//we set that status of showall
		
		this.showAll=showAll;
	}

	public String toString() 
	{
		//we print our board to our gridpane ,we the text that each cell holds in our zone[][] field.
		StringBuilder minesBuilder = new StringBuilder();
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++)
				minesBuilder.append(get(i,j));
			minesBuilder.append("\n");
		}
		return minesBuilder.toString();
	}
	




	private class Zone{
		private boolean ifFlag,ifOpen,ifMine;
		private int  Closemines;

		
	}
	

}
