package mines;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MinesFX extends Application{
	private  BackgroundSize backgroundSize = new BackgroundSize(150,200,true,true,true,false);//we set our background for our side panel
	private BackgroundImage picture = new BackgroundImage(new Image(new File("src/mines/Backgroundside.png").toURI().toString()),
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            backgroundSize);
    public static void main(String[] args)// we set our main as launch 
    {
        launch(args);
    }
    
    @Override
    public void start(Stage stage)// we start our program 
    {
     HBox hbox;
     Controller control;
     stage.setTitle("MineSweeper");
try {
	FXMLLoader loader = new FXMLLoader();
	loader.setLocation(getClass().getResource("Screenbuild.fxml"));// we set the distanation to the screenbuilder file
	hbox = loader.load();
	control=loader.getController();
    GridPane grindPane = new GridPane();
    control.setHbox(hbox);
    /*when the user will open the game, by defult it will be height=width=mines=10.
     * The user can change the parameters by inserting new values and clicking on the reset button.*/
	TextField textFieldWidth = control.getTextFieldWidth();
    TextField textFieldHeight = control.getTextFieldHeight();
    TextField textFieldMines = control.getTextFieldMines();
    textFieldWidth.setText("12");//we set our game to start at 12x12
    textFieldHeight.setText("12");
    textFieldMines.setText("12");

	hbox.setBackground(new Background(picture));// we set our hbox 
    control.setStage(stage);
    hbox.getChildren().add(grindPane);
    control.startgame();
} catch (IOException e) {
	e.printStackTrace();
	return;
}
Scene scene=new Scene(hbox);// we declare our scene that will receive our Vbox and the resolution of the window
stage.setScene(scene);// setScene will decides what scene to run 

stage.show();

}

}




