import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
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
	private Scene scene, scene2, scene3;
	private int boardRowSize= 21;
	private int boardColSize= 21;
	private int maxRow;
	private int maxCol;
	private int cruiser = 1;
	private int battleship = 1;
	private int submarine = 1;
	private int destroyer = 1;
	private boolean targetHit = false;
	private int targetR;
	private int targetC;
	private int lastR;
	private int lastC;
	private int targetDirection = 0;
	private Button[][] coordButtons = new Button[21][21];
	private int[][] shipPosition = new int[21][21];
	private int[][] cShips = new int[21][21];
	private HBox layout2 = new HBox(20);
	private VBox gameLogContainer = new VBox(10);
	private CheckBox submarineCB = new CheckBox("Submarine(" + submarine + ")");
	private CheckBox cruiserCB = new CheckBox("Cruiser(" + cruiser + ")");
	private CheckBox battleShipCB = new CheckBox("Battleship(" + battleship + ")" );
	private CheckBox destroyerCB = new CheckBox("Destroyer(" + destroyer + ")");
	private CheckBox vpCB = new CheckBox("Vertical");
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
		vpCB.setIndeterminate(false);
		disableCheckBoxes(cruiserCB, battleShipCB, destroyerCB, submarineCB);
		disableCheckBoxes(battleShipCB, cruiserCB, destroyerCB, submarineCB);
		disableCheckBoxes(destroyerCB, battleShipCB, cruiserCB, submarineCB);
		disableCheckBoxes(submarineCB, battleShipCB, destroyerCB, cruiserCB);
		
		ScrollPane scroll = new ScrollPane();
		scroll.setContent(gameLogContainer);
		scroll.setId("gameLog");
		scroll.setPrefViewportWidth(175);
		scroll.setPrefViewportHeight(950);
		scroll.vvalueProperty().bind(gameLogContainer.heightProperty());
		Button readyButton= new Button("Ready!");
		readyButton.setId("readyButton");
		Button playAgain = new Button("Game Over");
		readyButton.setDisable(true);
		readyButton.setOnAction(e -> primaryStage.setScene(scene2));

		
		int battleShipDirection = direction();
		if(battleShipDirection == 0)
		{
			cShips = createHBattleship(cShips);
		}
		else
		{
			cShips = createVBattleship(cShips);
		}
		
		int destroyerDirection = direction();
		if(destroyerDirection == 0)
		{
			cShips = createHDestroyer(cShips);
		}
		else
		{
			cShips = createVDestroyer(cShips);
		}
		
		int subDirection = direction();
		if(subDirection == 0)
		{
			cShips = createHSub(cShips);
		}
		else
		{
			cShips = createVSub(cShips);
		}

		int cruiserDirection = direction();
		if(cruiserDirection == 0)
		{
			cShips = createHCruiser(cShips);
		}
		else
		{
			cShips = createVCruiser(cShips);
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
				coordButton.setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/water.png"));
				coordButton.setOnAction(new EventHandler<ActionEvent>() 
				{
				    @Override public void handle(ActionEvent e) 
				    {
				    	coordButton.setDisable(true);
				    	coordButton.setStyle("-fx-font: 15 arial; -fx-text-fill: black; -fx-font-weight: bolder;");
				    	coordButton.setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/explosion.jpg"));
				    	 gameLogContainer.getChildren().add( new Text ("You shot a torpedo at " + coordButton.getText() + "."));
				    	if(cShips[mouseEnteredCol(e)][mouseEnteredRow(e)] == 1 )
				    	{
				    		cShips[mouseEnteredCol(e)][mouseEnteredRow(e)] = 0; 
				    		 gameLogContainer.getChildren().add( new Text("HIT! Target hit at " + coordButton.getText() + "."));
				    		
				    		coordButton.setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/hit.png"));
				    		if(!gameChecker(cShips))
				    		{
				    			 gameLogContainer.getChildren().add( new Text("You Win!"));
				    			primaryStage.setScene(scene3);
				    		}
				    	}
				    	else
				    	{
				    		coordButton.setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/explosion.jpg"));
				    	}
				    	AIMove();
				    	if(!gameChecker(shipPosition))
						{
				    		 gameLogContainer.getChildren().add( new Text("You Lose!"));
			    			primaryStage.setScene(scene3);
						}
				    }
				});
				coordButtons[row][col] = coordButton;
				setButtons(attackTab, coordButtons[row][col], col, row);
			}
		}
		
		// gridpane1
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
					coordButtons[row][col].setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/water.png"));
					coordButtons[row][col].setOnAction(new EventHandler<ActionEvent>() 
					{
					    @Override public void handle(ActionEvent e) 
					    {

					    	// vertical battleship
					    	if(mouseEnteredRow(e) + 4 <= maxRow && battleship == 1 && (battleShipCB.isSelected() == true) && vpCB.isSelected() == true)
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
							    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/battleship1.png"));
							    	
							    	coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)+1].
							    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/battleship2.png"));
							    	
							    	coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)+2].
							    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/battleship3.png"));
							    	
							    	coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)+3].
							    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/battleship4.png"));
							    	
							    	coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)+4].
							    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/battleship5.png"));
							    	
					    		}
					    		if(battleship ==0 && cruiser == 0 && submarine == 0 && destroyer == 0)
						    	{
					    			readyButton.setDisable(false);
						    	}
					    		
					    	}
					    	// horizontal battleship
					    	else
					    	{
					    		if(mouseEnteredRow(e) + 4 <= maxCol && battleship == 1 && (battleShipCB.isSelected() == true))
					    		{
					    			if(shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)] == 0 && shipPosition[mouseEnteredCol(e)+1][mouseEnteredRow(e)] == 0
							    			&&	shipPosition[mouseEnteredCol(e)+2][mouseEnteredRow(e)] == 0 && shipPosition[mouseEnteredCol(e)+3][mouseEnteredRow(e)]== 0
							    			&& shipPosition[mouseEnteredCol(e)+4][mouseEnteredRow(e)] == 0)
							    		{
						    				battleship --;
								    		shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)] = 1; 
								    		shipPosition[mouseEnteredCol(e)+1][mouseEnteredRow(e)] = 1;
									    	shipPosition[mouseEnteredCol(e)+2][mouseEnteredRow(e)] = 1; 
									    	shipPosition[mouseEnteredCol(e)+3][mouseEnteredRow(e)]= 1;
									    	shipPosition[mouseEnteredCol(e)+4][mouseEnteredRow(e)] = 1;
								    		battleShipCB.setText("Battleship(" + battleship + ")");
								    		
								    		coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)].
									    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/Horizontal/Hbattleship1.png"));
									    	
									    	coordButtons[mouseEnteredCol(e)+1][mouseEnteredRow(e)].
									    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/Horizontal/Hbattleship2.png"));
									    	
									    	coordButtons[mouseEnteredCol(e)+2][mouseEnteredRow(e)].
									    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/Horizontal/Hbattleship3.png"));
									    	
									    	coordButtons[mouseEnteredCol(e)+3][mouseEnteredRow(e)].
									    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/Horizontal/Hbattleship4.png"));
									    	
									    	coordButtons[mouseEnteredCol(e)+4][mouseEnteredRow(e)].
									    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/Horizontal/Hbattleship5.png"));
							    		}
					    			
					    		}
					    		
					    		if(battleship ==0 && cruiser == 0 && submarine == 0 && destroyer == 0)
						    	{
					    			readyButton.setDisable(false);
						    	}
					    	}
					    	
					    	
					    	// vertical cruiser
					    	
					    	if(mouseEnteredRow(e) + 3 <= maxRow && cruiser ==1 && (cruiserCB.isSelected() == true) && vpCB.isSelected() == true)
					    	{
					    		if(shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)] == 0 && shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+1] == 0
						    			&&	shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+2] == 0 && shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+3]== 0)
						    		{
							    		cruiser--;
							    		shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)] = 1; 
							    		shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+1] = 1;
								    	shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+2] = 1; 
								    	shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+3]= 1;
							    		cruiserCB.setText("Cruiser(" + cruiser + ")");
							    		
							    		coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)].
								    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/cruiser1.png"));
								    	
								    	coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)+1].
								    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/cruiser2.png"));
								    	
								    	coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)+2].
								    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/cruiser3.png"));
								    	
								    	coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)+3].
								    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/cruiser4.png"));
						    		}
					    		if(battleship ==0 && cruiser == 0 && submarine == 0 && destroyer == 0)
						    	{
					    			readyButton.setDisable(false);
						    	}
					    	}
					    	// horizontal cruiser
					    	else
					    	{
					    		if(mouseEnteredRow(e) + 3 <= maxCol && cruiser ==1 && (cruiserCB.isSelected() == true))
			    				{
					    			if(shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)] == 0 && shipPosition[mouseEnteredCol(e)+1][mouseEnteredRow(e)] == 0
							    			&&	shipPosition[mouseEnteredCol(e)+2][mouseEnteredRow(e)] == 0 &&	shipPosition[mouseEnteredCol(e)+3][mouseEnteredRow(e)] == 0)
							    		{
								    		cruiser--;
								    		shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)] = 1; 
								    		shipPosition[mouseEnteredCol(e)+1][mouseEnteredRow(e)] = 1;
									    	shipPosition[mouseEnteredCol(e)+2][mouseEnteredRow(e)] = 1; 
									    	shipPosition[mouseEnteredCol(e)+3][mouseEnteredRow(e)]= 1;
								    		cruiserCB.setText("Cruiser(" + cruiser + ")");
								    		
								    		coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)].
									    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/Horizontal/Hcruiser1.png"));
									    	
									    	coordButtons[mouseEnteredCol(e)+1][mouseEnteredRow(e)].
									    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/Horizontal/Hcruiser2.png"));
									    	
									    	coordButtons[mouseEnteredCol(e)+2][mouseEnteredRow(e)].
									    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/Horizontal/Hcruiser3.png"));
									    	
									    	coordButtons[mouseEnteredCol(e)+3][mouseEnteredRow(e)].
									    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/Horizontal/Hcruiser4.png"));
							    		}
			    				}
					    		if(battleship ==0 && cruiser == 0 && submarine == 0 && destroyer == 0)
						    	{
					    			readyButton.setDisable(false);
						    	}
					    	}
					    	
					    	
					    	// vertical submarine
					    	if(mouseEnteredRow(e) + 2 <= maxRow && submarine == 1 && (submarineCB.isSelected() == true) && vpCB.isSelected() == true)
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
								    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/sub1.png"));
								    	
								    	coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)+1].
								    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/sub2.png"));
								    	
								    	coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)+2].
								    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/sub3.png"));
						    		}
					    		}
					    		if(battleship ==0 && cruiser == 0 && submarine == 0 && destroyer == 0)
						    	{
					    			readyButton.setDisable(false);
						    	}
					    	}
					    	// horizontal submarine
					    	else
					    	{
					    		if(mouseEnteredRow(e) + 2 <= maxCol && submarine == 1 && (submarineCB.isSelected() == true))
					    		{
					    			if(shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)] == 0 && shipPosition[mouseEnteredCol(e)+1][mouseEnteredRow(e)] == 0
							    			&&	shipPosition[mouseEnteredCol(e)+2][mouseEnteredRow(e)] == 0)
							    		{
								    		submarine--;
								    		shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)] = 1; 
								    		shipPosition[mouseEnteredCol(e)+1][mouseEnteredRow(e)] = 1;
									    	shipPosition[mouseEnteredCol(e)+2][mouseEnteredRow(e)] = 1; 
									    	submarineCB.setText("Submarine(" + submarine + ")");
								    		
								    		coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)].
									    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/Horizontal/Hsub1.png"));
									    	
									    	coordButtons[mouseEnteredCol(e)+1][mouseEnteredRow(e)].
									    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/Horizontal/Hsub2.png"));
									    	
									    	coordButtons[mouseEnteredCol(e)+2][mouseEnteredRow(e)].
									    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/Horizontal/Hsub3.png"));
									    	
							    		}
					    		}
					    		if(battleship ==0 && cruiser == 0 && submarine == 0 && destroyer == 0)
						    	{
					    			readyButton.setDisable(false);
						    	}
					    	}
					    	
					    	// vertical destroyer
					    	
					    	
					    	if(mouseEnteredRow(e) + 1 <= maxRow && destroyer ==1 && (destroyerCB.isSelected() == true) && vpCB.isSelected() == true)
					    	{
					    		if(shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)] == 0 &&	shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+1] == 0)
					    		{	
						    		destroyer--;
						    		shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)] = 1; 
						    		shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)+1] = 1;
						    		destroyerCB.setText("Destroyer(" + destroyer + ")");
						    		
						    		coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)].
							    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/destroyer1.png"));
							    	
							    	coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)+1].
							    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/destroyer2.png"));
					    	
					    		}
					    		if(battleship ==0 && cruiser == 0 && submarine == 0 && destroyer == 0)
						    	{
					    			readyButton.setDisable(false);
						    	}
					    	}
					    	// horizontal destroyer
					    	else
					    	{
					    		if(mouseEnteredRow(e) + 1 <= maxCol && destroyer ==1 && (destroyerCB.isSelected() == true))
					    		{
					    			if(shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)] == 0 &&	shipPosition[mouseEnteredCol(e)+1][mouseEnteredRow(e)] == 0)
						    		{	
							    		destroyer--;
							    		shipPosition[mouseEnteredCol(e)][mouseEnteredRow(e)] = 1; 
							    		shipPosition[mouseEnteredCol(e)+1][mouseEnteredRow(e)] = 1;
							    		destroyerCB.setText("Destroyer(" + destroyer + ")");
							    		
							    		coordButtons[mouseEnteredCol(e)][mouseEnteredRow(e)].
								    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/Horizontal/Hdestroyer1.png"));
								    	
								    	coordButtons[mouseEnteredCol(e)+1][mouseEnteredRow(e)].
								    	setBackground(createBackground("file:///C:/Users/Jason/Desktop/JavaFXTest/images/Horizontal/Hdestroyer2.png"));
						    	
						    		}
					    		}
					    		if(battleship ==0 && cruiser == 0 && submarine == 0 && destroyer == 0)
						    	{
						    		readyButton.setDisable(false);
						    	}
					    	}
					    	
					    }
					});
					setButtons(shipPlacementTab, coordButtons[row][col], col, row);
				}
			}
		
        
		HBox layout = new HBox(20);
		VBox buttonContainer = new VBox(10);
		buttonContainer.getChildren().addAll(destroyerCB,submarineCB,cruiserCB,battleShipCB,vpCB);
		layout.getChildren().addAll(shipPlacementTab,readyButton,buttonContainer);
		layout2.getChildren().addAll(attackTab, scroll);
		
		
		scene = new Scene(layout,1255,950);
		scene.getStylesheets().add("file:///C:/Users/Jason/Desktop/JavaFXTest/scene1.css");
		scene2= new Scene(layout2,1245,950);
		scene2.getStylesheets().add("file:///C:/Users/Jason/Desktop/JavaFXTest/scene1.css");
		scene3 = new Scene(playAgain,200,200); 
		scene3.getStylesheets().add("file:///C:/Users/Jason/Desktop/JavaFXTest/scene1.css");
		
		window.setScene(scene);
		window.setTitle("BattleShip");
		window.show();
	}
	
	public boolean gameChecker(int[][] ships)
	{
		boolean stillShips = false;
		for(int r = 0; r < ships.length; r++)
		{
			for(int c =0; c < ships.length; c++)
			{
				if(ships[r][c] == 1)
				{
					stillShips = true;
				}
			}
		}
		return stillShips;
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
	 public int randomCoord()
	 {
		 Random rand = new Random();
		 int coord = rand.nextInt(20);
		 return coord;
	 }
	 
	 public int[][] createHBattleship(int[][] position)
	 {
		 int r = randomCoord();
		 int c = randomCoord();
		 if(r+1 < 20 && r+2 < 20 && r+3 < 20 && r+4 < 20)
		 {
			 if(position[r][c] == 0 && position[r+1][c] == 0 && position[r+2][c] == 0 && position[r+3][c] == 0 && position[r+4][c] == 0)
			 {
				position[r][c] = 1;
				position[r+1][c] = 1;
				position[r+2][c] = 1;
				position[r+3][c] = 1;
				position[r+4][c] = 1;
			 }
		 }
		 else
		 {
			createHBattleship(position);
		 }
		 
		 return position;
		 
	 }
	 
	 public int[][] createHCruiser(int[][] position)
	 {
		 int r = randomCoord();
		 int c = randomCoord();
		 if(r+1 < 20 && r+2 < 20 && r+3 < 20)
		 {
			 if(position[r][c] == 0 && position[r+1][c] == 0 && position[r+2][c] == 0 && position[r+3][c] == 0)
			 {
				position[r][c] = 1;
				position[r+1][c] = 1;
				position[r+2][c] = 1;
				position[r+3][c] = 1;
			 }
		 }
		 else
		 {
			createHCruiser(position);
		 }
		 
		 return position;
		 
	 }
	 
	 public int[][] createHSub(int[][] position)
	 {
		 int r = randomCoord();
		 int c = randomCoord();
		 if(r+1 < 20 && r+2 < 20)
		 {
			 if(position[r][c] == 0 && position[r+1][c] == 0 && position[r+2][c] == 0)
			 {
				position[r][c] = 1;
				position[r+1][c] = 1;
				position[r+2][c] = 1;
			 }
		 }
		 else
		 {
			createHSub(position);
		 }
		 
		 return position;
		 
	 }
	 
	 public int[][] createHDestroyer(int[][] position)
	 {
		 int r = randomCoord();
		 int c = randomCoord();
		 if(r+1 < 20 && r+2 < 20)
		 {
			 if(position[r][c] == 0 && position[r+1][c] == 0)
			 {
				position[r][c] = 1;
				position[r+1][c] = 1;
			 }
		 }
		 else
		 {
			 createHDestroyer(position);
		 }
		 
		 return position;
		 
	 }
	 
	 public int[][] createVBattleship(int[][] position)
	 {
		 int r = randomCoord();
		 int c = randomCoord();
		 if(c+1 < 20 && c+2 < 20 && c+3 < 20 && c+4 < 20)
		 {
			 if(position[r][c] == 0 && position[r][c+1] == 0 && position[r][c+2] == 0 && position[r][c+3] == 0 && position[r][c+4] == 0)
			 {
				position[r][c] = 1;
				position[r][c+1] = 1;
				position[r][c+2] = 1;
				position[r][c+3] = 1;
				position[r][c+4] = 1;
			 }
		 }
		 else
		 {
			 createVBattleship(position);
		 }
		 
		 return position;
		 
	 }
	 
	 public int[][] createVCruiser(int[][] position)
	 {
		 int r = randomCoord();
		 int c = randomCoord();
		 if(c+1 < 20 && c+2 < 20 && c+3 < 20)
		 {
			 if(position[r][c] == 0 && position[r][c+1] == 0 && position[r][c+2] == 0 && position[r][c+3] == 0)
			 {
				position[r][c] = 1;
				position[r][c+1] = 1;
				position[r][c+2] = 1;
				position[r][c+3] = 1;
			 }
		 }
		 else
		 {
			 createVCruiser(position);
		 }
		 
		 return position;
		 
	 }
	 
	 public int[][] createVSub(int[][] position)
	 {
		 int r = randomCoord();
		 int c = randomCoord();
		 if(c+1 < 20 && c+2 < 20)
		 {
			 if(position[r][c] == 0 && position[r][c+1] == 0 && position[r][c+2] == 0)
			 {
				position[r][c] = 1;
				position[r][c+1] = 1;
				position[r][c+2] = 1;

			 }
		 }
		 else
		 {
			 createVSub(position);
		 }
		 
		 return position;
		 
	 }
	 
	 public int[][] createVDestroyer(int[][] position)
	 {
		 int r = randomCoord();
		 int c = randomCoord();
		 if(c+1 < 20)
		 {
			 if(position[r][c] == 0 && position[r][c+1] == 0)
			 {
				position[r][c] = 1;
				position[r][c+1] = 1;

			 }
		 }
		 else
		 {
			 createVDestroyer(position);
		 }
		 
		 return position;
		 
	 }
	 
	 public int direction()
	 {
		 return (int) (Math.random() *2);
		 
	 }
	 
	 public void AIMove()
	 {
		 int r = randomCoord();
		 int c = randomCoord();
		 if(shipPosition[r][c] != -1)
		 {
			 if(shipPosition[r][c] == 1)
			 {
				 gameLogContainer.getChildren().add( new Text("ALERT! Your ship was hit (" + getRowLetter(r) + (c+1) + ")."));
				 shipPosition[r][c] -= 1;
			 }
			 else if(shipPosition[r][c] == 0)
			 {
				 gameLogContainer.getChildren().add( new Text("Computer fired at " + getRowLetter(r) + (c+1) + ".")); 
				 shipPosition[r][c] -= 1;
			 }
		 }
		 else
		 {
			 AIMove();
		 }
	 }
	 
	 public char getRowLetter(int row)
	 {
		 char[] letters = new char[20];
		    int c = 0;
		    for (char i = 'A' ; i <= 'T' ; i++) 
		    {
		        letters[c] = i;
		        c++;
		    }
		    return letters[row];
	 }
	 
}
