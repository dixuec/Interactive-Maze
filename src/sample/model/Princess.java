package sample.model;

import java.net.URL;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;


public class Princess extends GameCharacter implements java.io.Serializable{

    private static Princess p = new Princess(1150,550);

    public Princess(int x, int y) {
        super(x,y);
        URL u = Princess.class.getResource("/resources/Images/p.png");
        super.setImage(new Image(u.toString()));
    }

    public static Princess getPrincess(){
        return p;
    }

    public Princess setPosition(int x, int y){
        super.setPositionX(x);
        super.setPositionY(y);
        return p;
    }

    @Override
    public boolean isDestroyable() {
        return false;
    }
}
