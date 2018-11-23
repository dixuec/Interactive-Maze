
package sample.model;

import sample.controller.LabelObserver;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;



public class Game {

    private static Game g = new Game();
    List imgNames = new ArrayList<Character>();
    private Color c;
    private Player p = Player.getPlayer();
    private int bullets=6;
    private int health=100;
    private int score=0;
    private int n = 10;
    private List charArr;
    private int imgSize;
    private LabelObserver scoreObserver;
    private LabelObserver healthObserver;
    private LabelObserver bulletsObserver;


    private Game(){initializeImageNames();}
    
    public static Game getGame(){
        return g;
    }

    //generate map randomly
    private void initializeImageNames(){
        imgNames.add("m");imgNames.add("g");imgNames.add("B");imgNames.add("s");imgNames.add(" ");
        imgNames.add(" ");imgNames.add(" ");imgNames.add(" ");imgNames.add(" ");imgNames.add(" ");
        imgNames.add(" ");imgNames.add(" ");imgNames.add(" ");imgNames.add(" ");imgNames.add(" ");
    }
    
    public ArrayList<GameCharacter> generateMap(int n){
        ArrayList y = new ArrayList<Character>();
        for(int i=0;i<n;i++){
            for(int k=0;k<2*n;k++){
                int x = (int) (Math.random()*imgNames.size());
                y.add(imgNames.get(x));
            }
        }
        
        y.remove(0);
        y.add(0, Player.getPlayer().getLetter());
        y.remove(y.size()-1);
        y.add('d');
        CellFactory c = new CellFactory(y,n);
        return c.getNewMap();
    }
    
    public int getN(){
        return n;
    }

    public void setN(int m){ //自定义的时候用
        n = m;
        g.imgSize=300/n;
    }




    public void setSize(String s){
        switch(s){
            case "Easy" : n=10; break;
            case "Moderate" : n=15; break;
            case "Hard" : n=20; break;
            default : System.out.println("Unknown difficulty");
        }
        g.imgSize = 600/n;  //the height of the game board is 600, the width is 1200
    }

    public int getImgSize(){
        return g.imgSize;
    }

    public void incrementScore(int n){
        score+=n;
        scoreObserver.update(score);
    }

    public Player getPlayer(){
        return p;
    }

    public void addBullet(){this.bullets++;bulletsObserver.update(bullets);}

    public boolean fireBullet(){
        if(bullets>0){
            this.bullets--;
            bulletsObserver.update(bullets);
            return true;
        }else
            return false;}


    //TODO set the health,score and bullets and track the changes of them during the game

    public void setScoreObserver(LabelObserver scoreObserver) {
        this.scoreObserver = scoreObserver;
    }


    public void setHealthObserver(LabelObserver healthObserver) {
        this.healthObserver = healthObserver;
    }


    public void setBulletsObserver(LabelObserver bulletsObserver) {
        this.bulletsObserver = bulletsObserver;
    }

    public void setHealth(int health) {
        this.health = health;
        healthObserver.update(health);

    }

    public void setScore(int score){
        this.score = score;
        scoreObserver.update(score);
    }

    public void setBullets(int n){
        this.bullets = n;
        bulletsObserver.update(bullets);
    }

    public void setPlayer(boolean blue){
        p.setType(blue);
    }

    public int getHealth() {
        return health;
    }

    public int getBullets(){
        return bullets;
    }

    public int getScore(){
        return score;
    }


}
