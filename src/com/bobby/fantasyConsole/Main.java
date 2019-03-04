package com.bobby.fantasyConsole;

import org.python.util.PythonInterpreter;

import java.util.Random;



public class Main implements GameInterface{

    Random random;
    GameWindow game;
    Terminal terminal;

    public Main(){
        game = new GameWindow(this);
        game.start();
    }

    public void setup(GPU g) {
        random = new Random();
        game.setSize(258 * 3, 258 * 3);
        terminal = new Terminal();
    }

    public void draw(GPU g, float delta) {
        terminal.draw(g, delta);
    }

    @Override
    public void keyPressed(int code) {
        terminal.keyPressed(code);
    }

    @Override
    public void keyReleased(int code) {
        terminal.keyReleased(code);
    }
    @Override
    public void keyTyped(char c){
        terminal.keyTyped(c);
    }

    public static void main(String[] args){
        Main main = new Main();
    }

}
