package application;

import java.awt.event.MouseEvent;

import javax.swing.event.ChangeListener;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application 
{
	private Stage window;
	private Scene scene, scene2;
	private int maxRow;
	private int maxCol;
	private int cruiser = 1;
	private int battleship = 1;
	private int submarine = 1;
	private int destroyer = 1;
	private Button[][] coordButtons = new Button[21][21];
	private int[][] shipPosition = new int[21][21];
	private CheckBox cruiserCB = new CheckBox("Cruiser(" + cruiser + ")");
	private CheckBox battleShipCB = new CheckBox("Battleship(" + battleship + ")" );
	private CheckBox destroyerCB = new CheckBox("Destroyer(" + destroyer + ")");
	private CheckBox submarineCB = new CheckBox("Submarine(" + submarine + ")");
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		
		window = primaryStage;
		
		GridPane shipPlacementTab = new GridPane();
		GridPane attackTab = new GridPane();
        
        cruiserCB.setIndeterminate(false);
		battleShipCB.setIndeterminate(false);
		destroyerCB.setIndeterminate(false);
		submarineCB.setIndeterminate(false);
		disableCheckBoxes(cruiserCB, battleShipCB, destroyerCB, submarineCB);
		disableCheckBoxes(battleShipCB, cruiserCB, destroyerCB, submarineCB);
		disableCheckBoxes(destroyerCB, battleShipCB, cruiserCB, submarineCB);
		disableCheckBoxes(submarineCB, battleShipCB, destroyerCB, cruiserCB);

		for(int a = 0; a < shipPosition.length; a++)
		{
			for(int b = 0; b< shipPosition.length; b++)
			{
				shipPosition[a][b] = 0;
			}
		}
        for(int baseRow = 1; baseRow < 21; baseRow++)
        {
        	Text defaultRow = new Text(Character.toString((char)('A'+ baseRow-1)));
        	defaultRow.setStyle("-fx-font: 40 arial; -fx-text-fill: black; -fx-font-weight: bolder;");
        	setHeadingRow(attackTab, defaultRow, baseRow);
        }
        
        for(int baseCol = 1; baseCol < 21; baseCol++)
        {
        	Text defaultCol = new Text("" + (baseCol));;
        	defaultCol.setStyle("-fx-font: 40 arial; -fx-text-fill: black; -fx-font-weight: bolder;");
        	setHeadingCol(attackTab, defaultCol, baseCol);
		    
        }
        
        
		for(int row = 1; row < 21; row++)
		{
			for(int col = 1; col < 21 ; col++)
			{
				Button coordButton = new Button();
				coordButton.setText(Character.toString((char)('A'+col-1)) + (row));
				coordButton.setStyle("-fx-font: 15 arial; -fx-text-fill: antiquewhite; -fx-font-weight: bolder;");
				coordButton.setBackground(createBackground("file:///C:/Users/Student%203/Desktop/JavaFXTest/images/water.png"));
				coordButton.setOnAction(new EventHandler<ActionEvent>() 
				{
				    @Override public void handle(ActionEvent e) 
				    {
				    	coordButton.setDisable(true);
				    	coordButton.setStyle("-fx-font: 15 arial; -fx-text-fill: black; -fx-font-weight: bolder;");
				    	coordButton.setBackground(createBackground("file:///C:/Users/Student%203/Desktop/JavaFXTest/images/explosion.jpg"));
				    	System.out.println(coordButton.getText() + " was exploded.");
				    }
				});
				coordButtons[row][col] = coordButton;
				setButtons(attackTab, coordButtons[row][col], col, row);
			}
		}
		
		// gridpane2
		 for(int baseRow = 1; baseRow < 21; baseRow++)
	        {
	        	Text defaultRow = new Text(Character.toString((char)('A'+ baseRow-1)));
	        	defaultRow.setStyle("-fx-font: 40 arial; -fx-text-fill: black; -fx-font-weight: bolder;");
	        	setHeadingRow(shipPlacementTab, defaultRow, baseRow);
	        }
	        
	        for(int baseCol = 1; baseCol < 21; baseCol++)
	        {
	        	Text defaultCol = new Text("" + (baseCol));;
	        	defaultCol.setStyle("-fx-font: 40 arial; -fx-text-fill: black; -fx-font-weight: bolder;");
	        	setHeadingCol(shipPlacementTab, defaultCol, baseCol);
	        }
	        
	        
			for(int row = 1; row < 21; row++)
			{
				for(int col = 1; col < 21 ; col++)
				{
					maxRow = row;
					maxCol = col;
					Button coordButton = new Button();
					coordButtons[row][col] = coordButton;
					coordButtons[row][col].setText(Character.toString((char)('A'+col-1)) + (row));
					coordButtons[row][col].setStyle("-fx-font: 15 arial; -fx-text-fill: antiquewhite; -fx-font-weight: bolder;");
					coordButtons[row][col].setBackground(createBackground("file:///C:/Users/Student%203/Desktop/JavaFXTest/images/water.png"));
					coordButtons[row][col].setOnAction(new EventHandler<ActionEvent>() 
					{
					    @Override public void handle(ActionEvent e) 
					    {
					    	//battleship
					    	if(mouseEnteredRow(e) + 4 <= maxRow && battleship == 1 && (battleShipCB.isSelected() == true))
					    	{
					    		if(shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)] == 0 && shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+1] == 0
					    			&&	shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+2] == 0 && shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+3]== 0
					    			&& shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+4] == 0)
					    		{
					    				
						    		battleship --;
						    		shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)] = 1; 
						    		shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+1] = 1;
							    	shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+2] = 1; 
							    	shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+3]= 1;
							    	shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+4] = 1;
						    		battleShipCB.setText("Battleship(" + battleship + ")");
						    		
						    		coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)].
							    	setBackground(createBackground("file:///C:/Users/Student%203/Desktop/JavaFXTest/images/battleship1.png"));
							    	
							    	coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)+1].
							    	setBackground(createBackground("file:///C:/Users/Student%203/Desktop/JavaFXTest/images/battleship2.png"));
							    	
							    	coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)+2].
							    	setBackground(createBackground("file:///C:/Users/Student%203/Desktop/JavaFXTest/images/battleship3.png"));
							    	
							    	coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)+3].
							    	setBackground(createBackground("file:///C:/Users/Student%203/Desktop/JavaFXTest/images/battleship4.png"));
							    	
							    	coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)+4].
							    	setBackground(createBackground("file:///C:/Users/Student%203/Desktop/JavaFXTest/images/battleship5.png"));
					    		}
					    	}
					    	
					    	
					    	// cruiser
					    	
					    	if(mouseEnteredRow(e) + 3 <= maxRow && cruiser ==1 && (cruiserCB.isSelected() == true))
					    	{
					    		if(shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)] == 0 && shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+1] == 0
						    			&&	shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+2] == 0 &&	shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+3] == 0)
						    		{
							    		cruiser--;
							    		shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)] = 1; 
							    		shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+1] = 1;
								    	shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+2] = 1; 
								    	shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+3]= 1;
							    		cruiserCB.setText("Cruiser(" + cruiser + ")");
							    		
							    		coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)].
								    	setBackground(createBackground("file:///C:/Users/Student%203/Desktop/JavaFXTest/images/cruiser1.png"));
								    	
								    	coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)+1].
								    	setBackground(createBackground("file:///C:/Users/Student%203/Desktop/JavaFXTest/images/cruiser2.png"));
								    	
								    	coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)+2].
								    	setBackground(createBackground("file:///C:/Users/Student%203/Desktop/JavaFXTest/images/cruiser3.png"));
								    	
								    	coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)+3].
								    	setBackground(createBackground("file:///C:/Users/Student%203/Desktop/JavaFXTest/images/cruiser4.png"));
						    		}
					    	}
					    	
					    	
					    	
					    	//submarine
					    	if(mouseEnteredRow(e) + 2 <= maxRow && submarine == 1 && (submarineCB.isSelected() == true))
					    	{
					    		if(shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)] == 0 && shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+1] == 0
					    				&&	shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+2] == 0)
					    		{	
					    			{
							    		submarine --; 
							    		shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)] = 1; 
							    		shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+1] = 1;
								    	shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+2] = 1; 
							    		submarineCB.setText("Submarine(" + submarine + ")");
							    		
							    		coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)].
								    	setBackground(createBackground("file:///C:/Users/Student%203/Desktop/JavaFXTest/images/sub1.png"));
								    	
								    	coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)+1].
								    	setBackground(createBackground("file:///C:/Users/Student%203/Desktop/JavaFXTest/images/sub2.png"));
								    	
								    	coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)+2].
								    	setBackground(createBackground("file:///C:/Users/Student%203/Desktop/JavaFXTest/images/sub3.png"));
						    		}
					    		}
					    	}
					    	
					    	//destroyer
					    	
					    	
					    	if(mouseEnteredRow(e) + 1 <= maxRow && destroyer ==1 && (destroyerCB.isSelected() == true))
					    	{
					    		if(shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)] == 0 &&	shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+1] == 0)
					    		{	
						    		destroyer--;
						    		shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)] = 1; 
						    		shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+1] = 1;
						    		destroyerCB.setText("Destroyer(" + destroyer + ")");
						    		
						    		coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)].
							    	setBackground(createBackground("file:///C:/Users/Student%203/Desktop/JavaFXTest/images/destroyer1.png"));
							    	
							    	coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)+1].
							    	setBackground(createBackground("file:///C:/Users/Student%203/Desktop/JavaFXTest/images/destroyer2.png"));
					    	
					    		}
					    	}
					    	
					    	
					    }
					});
					setButtons(shipPlacementTab, coordButtons[row][col], col, row);
				}
			}
		Button button2= new Button("Go to scene 2");
		button2.setOnAction(e -> primaryStage.setScene(scene2));
		
        
		HBox layout = new HBox(20);
		VBox buttonContainer = new VBox(10);
		buttonContainer.getChildren().addAll(submarineCB,cruiserCB,battleShipCB,destroyerCB);
		layout.getChildren().addAll(shipPlacementTab,button2,buttonContainer);
		scene = new Scene(layout,1255,945);
		scene2= new Scene(attackTab,1245,945);
		
		window.setScene(scene);
		window.setTitle("test");
		window.show();
	}
	
	public void setButtons(GridPane grid, Button coord, int col, int row)
	{
		coord.setPrefSize(50, 50);
		grid.setRowIndex(coord, col);
		grid.setColumnIndex(coord, row);
		grid.setHalignment(coord, HPos.CENTER);
		grid.getChildren().addAll(coord);
	}
	public void disableButtons()
	{
		for(int row = 1; row < 21; row++)
		{
			for(int col = 1; col < 21 ; col++)
			{
				coordButtons[row][col].setDisable(true);
			}
		}
	}
	
	public void enableButtons()
	{
		for(int row = 1; row < 21; row++)
		{
			for(int col = 1; col < 21 ; col++)
			{
				coordButtons[row][col].setDisable(false);
			}
		}
	}
	public void setHeadingCol(GridPane pane, Text defaultCol, int col)
	{
		pane.setRowIndex(defaultCol, 0);
		pane.setColumnIndex(defaultCol, col);
		pane.setHalignment(defaultCol, HPos.CENTER);
		pane.getChildren().addAll(defaultCol);
	}
	
	public void setHeadingRow(GridPane pane, Text defaultCol, int row)
	{
		pane.setRowIndex(defaultCol, row);
		pane.setColumnIndex(defaultCol, 0);
		pane.setHalignment(defaultCol, HPos.CENTER);
		pane.getChildren().addAll(defaultCol);
	}
	
	public Background createBackground(String fileName)
	{
		BackgroundImage bgImage = new BackgroundImage(new Image
	    		(fileName), 
	    		BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
	    		BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background bg = new Background(bgImage);
		return bg;
	}
	 private int mouseEnteredRow(ActionEvent e) 
	 {
	        Node source = (Node)e.getSource();
	        Integer rowIndex = GridPane.getRowIndex(source);
	        return rowIndex;
	 }
	 private int mouseEnteredCol(ActionEvent e) 
	 {
	        Node source = (Node)e.getSource();
	        Integer colIndex = GridPane.getColumnIndex(source);
	        return colIndex;
	 }
	 public void disableCheckBoxes(CheckBox currentCB, CheckBox cb1, CheckBox cb2, CheckBox cb3)
	 {
		 EventHandler eh = new EventHandler<ActionEvent>() 
			{
			    @Override
			    public void handle(ActionEvent event) 
			    {
			        if (event.getSource() instanceof CheckBox) 
			        {
			            CheckBox chk = (CheckBox) event.getSource();
			            if (currentCB.isSelected() == true) 
			            {
			                cb1.setSelected(false);
			                cb2.setSelected(false);
			                cb3.setSelected(false);
			            }
			        }
			    }
			};
			currentCB.setOnAction(eh);
	 }
	 
}
