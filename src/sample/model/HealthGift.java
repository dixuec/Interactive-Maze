
package sample.model;

import java.net.URL;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;


public class HealthGift extends Gift implements java.io.Serializable{

    public HealthGift(int x, int y) {
        super(x, y);
        URL u = HealthGift.class.getResource("/resources/Images/HG.png");
        super.setImage(new Image(u.toString()));
    }

    

    @Override
    public boolean isDestroyable() {
        return true;
    }
    
}
