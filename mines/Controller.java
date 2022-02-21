package mines;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Controller {
	private HBox hbox;
	private Mines game;
	private int height,width,mine;
	private ButtonsData[][] buttons;
    private Label showLabel;
    private Stage stage;
    @FXML
    private TextField HeightField;

    @FXML
    private TextField MinesAmount;

    @FXML
    private TextField WidthField;

    @FXML
    void ResetData(ActionEvent event) {
    	startgame();
    }
	public TextField getTextFieldWidth() {//return to other function our width
		return WidthField;
	}

	public TextField getTextFieldHeight() {//return to other function our height
		return HeightField;
	}

	public TextField getTextFieldMines() {//return to other function our mines
		return MinesAmount;
	}
	public void setHbox(HBox hbox) {//
		this.hbox = hbox;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
    public void startgame() {
    	GridPane gridPane=new GridPane();// we create a new grid pane for our our board play 
    	List<ColumnConstraints> col=new ArrayList<>();// we will create a new 2 array lists that will define our
    	// length of the board , the row list will hold the height amount of cells that are required to be .
    	// the column will do the same 
    	List<RowConstraints> row=new ArrayList<>();
    	 width=Integer.valueOf(WidthField.getText());// we save the inputed text fiend in width to int 
      	 height=Integer.valueOf(HeightField.getText());// we save the inputed text fiend in height to int 	
      	 mine=Integer.valueOf(MinesAmount.getText());	// we save the inputed text fiend in mine to int  	
      	buttons=new ButtonsData[height][width];// we set a buttons matrix by height and width/
      	// each buttons will hold his info if we want to change it 
    	for(int i=0; i<width; i++)
    		col.add(new ColumnConstraints(35));// we set our the size of each button for our gridpane , then we store it 
    	// in our arraylist
    	for(int i=0; i<height; i++)
    		row.add(new RowConstraints(35));
      	game= new Mines(height,width,mine); // we set our board with the Mines class we created 
      	// he sets all the mines and all the other cells.
      	start(gridPane);//we create our game 
      	gridPane.getColumnConstraints().addAll(col);
      	gridPane.getRowConstraints().addAll(row);
    	hbox.getChildren().remove(hbox.getChildren().size()-1);
    	hbox.getChildren().add(gridPane);
    	hbox.autosize();
    	stage.sizeToScene();
      	
      	
    }
    
    private void start(GridPane grindPane) {
    	for(int i=0;i<height;i++)
    		for(int j=0;j<width;j++) {
    			buttons[i][j]=new ButtonsData(i,j);// we define new button
    	        buttons[i][j].setText(game.get(i, j));//we set the button as "" or F or X or "."
    	        buttons[i][j].setStyle("    -fx-background-color: \r\n"
    	        		+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
    	        		+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
    	        		+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
    	        		+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
    	        		+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
    	        		+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
    	        		+ "    -fx-background-radius: 9,8,5,4,3;\r\n"
    	        		//+ "    -fx-padding: 15 30 15 30;\r\n"
    	        		+ "    -fx-font-family: \"Helvetica\";\r\n"
    	        		+ "    -fx-font-size: 18px;\r\n"
    	        		+ "    -fx-font-weight: bold;\r\n"
    	        		+ "    -fx-text-fill: white;\r\n"
    	        		+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1)");
    	        buttons[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {//we create action for our mouse clicks
    	        	// left click revel the button
    	        	//right click puts flag
    	        	
					@Override
					
					public void handle(MouseEvent event) {
						if(event.getButton()==MouseButton.PRIMARY)//left click of the button
						{
							if(!buttons[((ButtonsData)event.getSource()).ReturnX()][((ButtonsData)event.getSource()).ReturnY()].getText().equals("F")) {
							boolean res=game.open(((ButtonsData)event.getSource()).ReturnX(),((ButtonsData)event.getSource()).ReturnY());
							update();//after we opened the button we update all the other buttons in order to get updated view
							if(res==false)// in case we clicked a bomb we will finish the game 
							{

								Lose();
							}
							if(game.isDone()) popmessage(false,true);// in case all the buttons are open we will print that we won because
							//we have opened all the buttons 
						}}
						if(event.getButton()==MouseButton.SECONDARY)//right click for flag 
						{
							int x = ((ButtonsData)event.getSource()).ReturnX();
							int y = ((ButtonsData)event.getSource()).ReturnY();
							if(!buttons[x][y].getText().equals(" ")  && !buttons[x][y].getText().equals("X")
									&&!buttons[x][y].getText().equals("1")&&!buttons[x][y].getText().equals("2")
									&&!buttons[x][y].getText().equals("3")&&!buttons[x][y].getText().equals("4")&&!buttons[x][y].getText().equals("5")
									&&!buttons[x][y].getText().equals("6")&&!buttons[x][y].getText().equals("7")
									&&!buttons[x][y].getText().equals("8")) {//we check if our current button is not any number from 1-8 or is opened
								// by being marked as X or empty  
								String musicFile2 = "src/mines/click.mp3";     // For example

								Media sound = new Media(new File(musicFile2).toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(sound);
								mediaPlayer.play();
							game.toggleFlag(((ButtonsData)event.getSource()).ReturnX(), ((ButtonsData)event.getSource()).ReturnY());//we change our current
							// button from dot to F or from F to dot

							update()//we update our current board
							;}
						}

						}
						});
    	        buttons[i][j].setMaxHeight(Double.MAX_VALUE);// we define each button its size
    	        buttons[i][j].setMaxWidth(Double.MAX_VALUE);
    	        grindPane.add(buttons[i][j], j, i);// we add the button to our gridpane
    	        }
    		}
    
    private void update() {// reopen all the cells again in order to update their view according to their status
    	for(int i=0;i<height;i++)
    		for(int j=0;j<width;j++) {
        buttons[i][j].setText(game.get(i, j));
    	if(buttons[i][j].getText().equals(".")) buttons[i][j].setGraphic(null);
        Bomb(i,j);
        Flag(i,j);
    		}
    }

	private void popmessage(boolean isOver, boolean isDone) {
		// this function will pop a window when request it 
		// it will use the hboxfunc in order to define what message to print 
		Scene popUp = new Scene(Hboxfunc(isOver, isDone), 300, 100);
		Stage popUpStage = new Stage();
		popUpStage.setScene(popUp);
		popUpStage.show();
	}
	
	private HBox Hboxfunc(boolean isOver, boolean isDone) {
		// this function creates a new Hbox that will hold a label that we will define by
		// our inputed inputs that the function hboxfunc receives .
		// in case we receive TRUE,FLASE we will print that we won the game
		// in case we receive FALSE in isOver it means we lost the game 
		HBox hbox = new HBox();
		if(isOver)
			showLabel = new Label("You just lost!");
		else if(isDone)
			showLabel = new Label("You Won!");
		showLabel.setFont(Font.font(null, FontWeight.BOLD, 20));
		hbox.setPadding(new Insets(30, 20, 20, 80));
		hbox.getChildren().addAll(showLabel);
		return hbox;
	}
	private void Lose() {
		game.setShowAll(true);//we use our Mines class function in order to update Showall 
		
		String musicFile = "src/mines/explosion.mp3";     // For example

		Media sound = new Media(new File(musicFile).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
		// in order to revel all the button
		update();//after we opened the button we update all the other buttons in order to get updated view
		popmessage(true,false);// pop message that we lost the game 
	}
	
	private void Bomb(int i,int j) {//we set our picture for mine that marked as X
		if(buttons[i][j].getText().equals("X")) {
			buttons[i][j].setText("");
			Image x = new Image(new File("src/mines/mine.png").toURI().toString());
			ImageView view = new ImageView(x);
			view.setFitHeight(20);
			view.setFitWidth(25);
			view.setPreserveRatio(true);
			buttons[i][j].setGraphic(view);
		}
	}
	
	private void Flag(int i,int j) {// in case we get flag we will put the flag picture 
		if(buttons[i][j].getText().equals("F")) {
			buttons[i][j].setText("");
			Image x = new Image(new File("src/mines/flag.png").toURI().toString());
			ImageView view = new ImageView(x);
			view.setFitHeight(25);
			view.setFitWidth(25);
			view.setPreserveRatio(true);
			buttons[i][j].setGraphic(view);
	}
	}
	

	
}
