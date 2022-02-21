package mines;

import javafx.scene.control.Button;

public class ButtonsData extends Button{
	private int x,y;
	public ButtonsData(int x,int y) {
		this.x=x;
		this.y=y;
		
	}
	public int ReturnX()
	{
		return x;
	}
	public int ReturnY()
	{
		return y;
	}
}
