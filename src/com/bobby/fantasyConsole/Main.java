package com.bobby.fantasyConsole;

import com.bobby.fantasyConsole.Modules.GPU;
import com.bobby.fantasyConsole.Modules.Terminal.Terminal;

import java.util.Random;



public class Main implements GameInterface{
    Computer computer;
    GameWindow game;

    public Main(){
        game = new GameWindow(this);
        game.start();
    }

    public void setup(GPU g) {
        game.setSize(258 * 3, 258 * 3);
        computer = new Computer(g);
    }

    public void draw(GPU g, float delta) {
        computer.draw(g, delta);
    }

    @Override
    public void keyPressed(int code) {
        computer.keyPressed(code);
    }

    @Override
    public void keyReleased(int code) {
        computer.keyReleased(code);
    }
    @Override
    public void keyTyped(char c){
        computer.keyTyped(c);
    }

    public static void main(String[] args){
        Main main = new Main();
    }

}
