
package sample.controller;

import javafx.scene.control.Label;


public class LabelObserver {
    private Label l;

    public LabelObserver(Label l){
        this.l = l;
    }

    public void update(int val) {
        l.setText(String.valueOf(val));
    }
}
