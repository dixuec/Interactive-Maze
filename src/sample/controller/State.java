
package sample.controller;


public class State {


    public void pause(Clock t) {
        t.pause();
    }

    public void play(Clock t) { t.cont(); }

    public void stop(Clock t) { t.stop(); }
    
}
