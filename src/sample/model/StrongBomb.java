
package sample.model;

import java.net.URL;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;


public class StrongBomb extends GameCharacter implements java.io.Serializable{

    public StrongBomb(int x, int y) {
        super(x, y);
        URL u = StrongBomb.class.getResource("/resources/Images/SB.png");
        super.setImage(new Image(u.toString()));
    }

    @Override
    public boolean isDestroyable() {
        return false;
    }
    
}
