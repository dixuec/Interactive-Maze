package sample.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import sample.model.*;
import javafx.scene.shape.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable{

    @FXML
    private GridPane gamePane;

    @FXML
    private GridPane previewPane;


    @FXML
    private Pane canvasPane;

    @FXML
    private Pane startPane;
    @FXML
    private Pane customizePane;

    @FXML
    private Pane howToPlayPane;

    @FXML
    private Pane settingsPane;
    @FXML
    private Pane winPane;
    @FXML
    private Pane losePane;

    @FXML
    private Pane pausePane;

    @FXML
    private Canvas canvas;

    @FXML
    private Button newGameBtn;

    @FXML
    private Button createGameBtn;

    @FXML
    private Button howToPlayBtn;
    @FXML
    private Button settingsBtn;
    @FXML
    private Button howBackBtn;
    @FXML
    private Button cusBackBtn;
    @FXML
    private Button settingsBackBtn;
    @FXML
    private Button pauseBtn;
    @FXML
    private Button winExitBtn;
    @FXML
    private Button loseExitBtn;
    @FXML
    private Button continueBtn;
    @FXML
    private Button quitBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button saveBtn2;
    @FXML
    private ComboBox<String> difficulty;

    @FXML
    private Slider sizeSlider;


    @FXML
    private ImageView Prince1;

    @FXML
    private ImageView Prince2;
    @FXML
    private ImageView wall;
    @FXML
    private ImageView bomb;
    @FXML
    private ImageView gift;
    @FXML
    private ImageView monster;

    @FXML
    private Label timeLabel;
    @FXML
    private Label healthLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label bulletLabel;



    private Game game = Game.getGame();
    private ArrayList<GameCharacter> arr;
    private ArrayList<GameCharacter> customize=new ArrayList<GameCharacter>();
    private String difficult = "Easy";
    private boolean bluePrince=true;
    private String imageName;
    private Clock t;
    private int n = game.getN();

    @FXML
    public void handleButtonAction(ActionEvent event) {

        if(event.getSource()==newGameBtn){
            startPane.setVisible(false);
            gamePane.setVisible(true);
            game.setSize(difficult);
            t = new Clock();
            t.setObserver(new LabelObserver(timeLabel));
            game.setBulletsObserver(new LabelObserver(bulletLabel));
            game.setHealthObserver(new LabelObserver(healthLabel));
            game.setScoreObserver(new LabelObserver(scoreLabel));
            game.setHealth(100);
            game.setBullets(6);
            game.setScore(0);
            t.start(1000, 1000);
            game.setPlayer(bluePrince);
            game.setSize(difficult);

            //if user has created his own map,use this new map rather than the random one
            if(customize.size() != 0){
                customize.remove(0);
                customize.add(0,game.getPlayer().setAttr(0,0,true,true));
                customize.remove(customize.size()-1);
                customize.add(Princess.getPrincess().setPosition(1150,550));
                arr = (ArrayList<GameCharacter>)customize.clone();

            }else{
                arr = game.generateMap(game.getN());

            }
            updateCanvas(arr);


        }else if(event.getSource()==createGameBtn){  //Here the user is able to customize their own map.

            startPane.setVisible(false);
            customizePane.setVisible(true);

        }else if(event.getSource()==howToPlayBtn){

            startPane.setVisible(false);
            howToPlayPane.setVisible(true);

        }else if(event.getSource()==settingsBtn){  //我不准备选择颜色了

            settingsPane.setVisible(true);
            startPane.setVisible(false);
            Prince2.setBlendMode(BlendMode.OVERLAY);
            Prince1.setBlendMode(null);
        }else if(event.getSource()==howBackBtn){

            howToPlayPane.setVisible(false);
            startPane.setVisible(true);

        }else if(event.getSource()==cusBackBtn){
            customizePane.setVisible(false);
            startPane.setVisible(true);

        }else if(event.getSource()==settingsBackBtn){
            settingsPane.setVisible(false);
            startPane.setVisible(true);
            if(!difficulty.getSelectionModel().isEmpty()){
                difficult = difficulty.getSelectionModel().getSelectedItem();
            }
        }else if(event.getSource()==saveBtn){
            if(customize.size() !=0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.titleProperty().set("SUCCESS");
                alert.headerTextProperty().set("You have created a new map!");
                alert.showAndWait();
            }



        }else if(event.getSource()==saveBtn2){
            getCustomizeSize();
            creatGrids(n);

        }else if(event.getSource()==pauseBtn){

            pausePane.setVisible(true);
            new State().pause(t);

        }else if(event.getSource()==continueBtn){

            pausePane.setVisible(false);
            new State().play(t);


        }else if(event.getSource()==quitBtn){

            pausePane.setVisible(false);
            gamePane.setVisible(false);
            startPane.setVisible(true);
            new State().stop(t);

        }else if(event.getSource()==winExitBtn){
            winPane.setVisible(false);
            gamePane.setVisible(false);
            startPane.setVisible(true);
        }else if(event.getSource()==loseExitBtn){
            losePane.setVisible(false);
            gamePane.setVisible(false);
            startPane.setVisible(true);
        }


    }

    @FXML
    private void handleKeyPress(KeyEvent event){

        int currX = game.getPlayer().getIndexX();
        int currY = game.getPlayer().getIndexY();
        switch(event.getCode().toString()){
            case "RIGHT": moveRight(currX,currY);break;
            case "LEFT": moveLeft(currX,currY);break;
            case "UP": moveUp(currX,currY);break;
            case "DOWN": moveDown(currX,currY);break;
            default : System.out.println("Unrecognized key");
        }
        if(customize.size()!=0){
            updateCanvas(customize);
        }else{
            updateCanvas(arr);
        }

    }

    private void moveLeft(int x, int y){
        if(game.getPlayer().isLeft()){game.getPlayer().rotate();return;}
        int nextIndex = x-1;
        if(x/(game.getN()*2) == nextIndex/(game.getN()*2) && nextIndex>=0){
            moving(x,y,nextIndex,y);
        }
    }

    private void moveRight(int x, int y){
        if(!game.getPlayer().isLeft()){game.getPlayer().rotate();return;}
        int nextIndex = x+1;
        if(x/(game.getN()*2) == nextIndex/(game.getN()*2)){
            moving(x,y,nextIndex,y);
        }
    }

    private void moveUp(int x, int y){
        int nextIndex = y-1;
        if(nextIndex>=0){
            moving(x,y,x,nextIndex);
        }
    }

    private void moveDown(int x, int y){
        int nextIndex = y+1;
        if(nextIndex<game.getN()){
            moving(x,y,x,nextIndex);
        }
    }
    //the rules of the game
    private void moving(int x1, int y1, int x2, int y2){
        int arrIndex1 = (y1*2*game.getN())+x1;
        int arrIndex2 = (y2*2*game.getN())+x2;
        if(arr.get(arrIndex2).getClass().getSimpleName().equals("Space")){
            swapPositions(arrIndex1,arrIndex2);
            swapPlacesinArray(arrIndex1,arrIndex2);
        }
        else if(arr.get(arrIndex2).getClass().getSimpleName().equals("HealthGift")){
            game.setHealth(game.getHealth()+20);
            game.incrementScore(10);
            replaceBySpace(arrIndex2);
            swapPositions(arrIndex1,arrIndex2);
            swapPlacesinArray(arrIndex1,arrIndex2);
        }
        else if(arr.get(arrIndex2).getClass().getSimpleName().equals("StrongBomb")){
            if(game.getHealth()<80){ youLose();return;}
            game.setHealth(game.getHealth()-40);
            replaceBySpace(arrIndex2);
            swapPositions(arrIndex1,arrIndex2);
            swapPlacesinArray(arrIndex1,arrIndex2);
        }
        else if(arr.get(arrIndex2).getClass().getSimpleName().equals("StealerMonster")){
            if(game.fireBullet()){
                replaceBySpace(arrIndex2);
            }else{
                youLose();
            }
        }
        else if(arr.get(arrIndex2).getClass().getSimpleName().equals("Princess")){
            youWin();
        }

    }

    private void replaceBySpace(int x){
        int xPos = arr.get(x).getPositionX();
        int yPos = arr.get(x).getPositionY();
        arr.remove(x);
        arr.add(x,new Space(xPos,yPos));
    }

    private void swapPositions(int one, int two) {


        int x1 = arr.get(one).getPositionX();
        int y1 = arr.get(one).getPositionY();
        int x2 = arr.get(two).getPositionX();
        int y2 = arr.get(two).getPositionY();

        arr.get(one).setPositionX(x2);
        arr.get(one).setPositionY(y2);
        arr.get(two).setPositionX(x1);
        arr.get(two).setPositionY(y1);
    }

    private void swapPlacesinArray(int one, int two){
        GameCharacter temp1 = arr.get(one);
        GameCharacter temp2 = arr.get(two);
        arr.remove(one);arr.add(one, temp2);
        arr.remove(two);arr.add(two, temp1);
    }

    private void youWin(){
        t.stop();
        winPane.setVisible(true);
    }

    private void youLose(){
        t.stop();
        losePane.setVisible(true);
    }


    //选择不同角色
    @FXML
    private void Prin1Click(MouseEvent event){
        Prince2.setBlendMode(BlendMode.OVERLAY);
        Prince1.setBlendMode(null);
        bluePrince=true;
    }

    @FXML
    private void Prin2Click(MouseEvent event){
        Prince1.setBlendMode(BlendMode.OVERLAY);
        Prince2.setBlendMode(null);
        bluePrince=false;
    }

    //Draw the maze using canvas
    private void updateCanvas(ArrayList<GameCharacter> arr) {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 1200, 600);
        for(int i=0;i<arr.size();i++){
            arr.get(i).draw(canvas);
        }
    }

    //track the slider movement and get the value
    private void getCustomizeSize(){
        sizeSlider.valueProperty().addListener(new javafx.beans.value.ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int value = (int)sizeSlider.getValue();
                if(value!= 0){
                   game.setN(value);
                   n = value;
                }

            }
        });

    }

    //drag the game characters
    private void handleDrag(ImageView imag){
        imag.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = imag.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(imag.getImage());
                db.setContent(content);
                imageName = imag.getId();
                event.consume();
            }
        });


    }
    //drop the characters to the game board
    private void handleDrop(Rectangle rec, int rowIndex, int colIndex){

        //when a image is drag to this cell, the color will change to green
        //when move away,the color will return back to gray
        rec.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                rec.setFill(Color.GREEN);
            }
        });
        rec.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                rec.setFill(Color.GREY);
            }
        });
        //draw the image of the character if the character is droped to this cell
        rec.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != rec &&
                        event.getDragboard().hasImage()) {

                    event.acceptTransferModes(TransferMode.COPY);
                }

            }
        });
        rec.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasImage()) {
                    ImageView img = new ImageView(db.getImage());
                    img.setFitWidth(300/n);
                    img.setFitHeight(300/n);
                    rec.setVisible(false);
                    previewPane.add(img,colIndex,rowIndex);
                    //store the characters in a arraylist according to their positions
                    emptyMap(n);
                    customize.remove(2*n*rowIndex+colIndex);
                    customize.add(2*n*rowIndex+colIndex,generateCharacter(rowIndex,colIndex));
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();

            }
        });


    }

    //when user creates their own map, grids are show
    //to guide users to put the game characters to the appropriate positions
    private void creatGrids(int n){
        for(int row=0;row<n;row++){
            for(int col=0;col<2*n;col++){
                Rectangle rec = new Rectangle();
                rec.setHeight(300/n);
                rec.setWidth(300/n);
                rec.setFill(Color.GREY);
                rec.setStroke(Color.BLACK);
                GridPane.setRowIndex(rec,row);
                GridPane.setColumnIndex(rec,col);
                previewPane.getChildren().addAll(rec);
                handleDrop(rec,row,col);

            }
        }
    }
    //create a empty arraylist before adding any game characters
    private void emptyMap(int n){
        int colIndex;
        int rowIndex;
        for (int i=0;i<2*n*n;i++){
            colIndex=i%2*n;
            rowIndex=i/n;
            customize.add(new Space(colIndex*(600/n),rowIndex*(600/n)));
        }
    }
    //user can play with the map generated by himself
    private GameCharacter generateCharacter(int rowIndex,int colIndex){

        int xPos = colIndex*(600/n);
        int yPos = rowIndex*(600/n);
        GameCharacter character=null;
        switch (imageName){
            case "wall":character = new StoneWall(xPos,yPos);break;
            case "bomb":character = new StrongBomb(xPos,yPos);break;
            case "gift":character = new HealthGift(xPos,yPos);break;
            case "monster":character = new StealerMonster(xPos,yPos);break;
        }
        return character;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //handle drag operation
        handleDrag(wall);
        handleDrag(gift);
        handleDrag(bomb);
        handleDrag(monster);

        getCustomizeSize();
        //create grids and handle drop operation
        //creatGrids(n);

        //set difficulty of the game
        ObservableList dList = FXCollections.observableArrayList();
        dList.add("Easy");dList.add("Moderate");dList.add("Hard");
        difficulty.setItems(dList);



    }
}
