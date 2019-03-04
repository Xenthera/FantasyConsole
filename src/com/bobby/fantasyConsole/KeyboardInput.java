package com.bobby.fantasyConsole;

import java.awt.event.KeyEvent;

public class KeyboardInput implements java.awt.event.KeyListener {
    GameInterface gameInterface;

    public KeyboardInput(GameInterface gi){
        this.gameInterface = gi;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        this.gameInterface.keyTyped(c);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        this.gameInterface.keyPressed(c);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int c = e.getKeyCode();
        this.gameInterface.keyReleased(c);
    }
}
