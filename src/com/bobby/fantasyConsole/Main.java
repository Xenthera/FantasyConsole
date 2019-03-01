package com.bobby.fantasyConsole;

import org.python.util.PythonInterpreter;

import java.util.Random;



public class Main extends Thread implements GameInterface{


    Random random;
    GameWindow game;
    float time;
    boolean flash;
    char[][] terminalChars;
    int cursX = 0;
    int cursY = 0;

    public Main(){



        game = new GameWindow(this);


    }

    public void run(){
        try {
            game.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void setup(GPU g) {
        random = new Random();
        game.setSize(258 * 3, 144 * 3);
        terminalChars = new char[43][24];



        Test test = new Test(g);
        test.start();
        System.out.println("Setup");
    }

    public void draw(GPU g, float delta) {
        //interpreter.exec("g.drawCircle(40, 40, 30)");
        //g.clear(0);
        time += 1;
//        for (int i = 0; i < 6; i++) {
//
//            double x = time * 0.03;
//            double y = Math.cos(time * 0.001) * 50;
//            int color = i + 9;
//
//            g.setColor(color);
//            g.fillCircle((int)x, (int)y + ((i - 3) * 10) + g.getHeight() / 2, 5);
//
//            color = 14 - i;
//
//            g.setColor(color);
//            g.fillCircle((int)x, 1 - (int)y + ((i - 3) * 10) + g.getHeight() / 2, 5);
//
//
//
//        }
//        boldString((int)10, (int)10, "Presenting", 1, g);
//        boldString((int)10, (int)18, "Bobbys retro computer concept", 2, g);
//        boldString((int)10, (int)26, "Written in java scripted through python", 3, g);
//        boldString((int)10, (int)34, "pretty colors", 5, g);
//        boldString((int)10, (int)42, "testing testing one two three", 6, g);
//        g.setColor(13);
//        g.fillCircle(g.getWidth() / 2, g.getHeight() / 2, 70);
//        if(time % 10 == 0){
//            flash = !flash;
//        }
//
//        for (int i = 0; i < terminalChars.length; i++) {
//            for (int j = 0; j < terminalChars[i].length; j++) {
//                g.setColor(15);
//                g.drawString(i * 6, j * 6, String.valueOf(terminalChars[i][j]));
//                if(i == cursX && j == cursY && flash){
//                    g.setColor(15);
//                    g.drawLine(i * 6 + 1, j * 6 + 6,i * 6 + 4, j * 6 + 6);
//                }
//            }
//        }

    }

    @Override
    public void keyPressed(int code) {
        if(code == 8) {
            if (this.cursX > 0) {
                this.cursX -= 1;
                this.terminalChars[cursX][cursY] = (char) 0;
                //System.out.println(cursX);

            }else if(this.cursX == 0 && this.cursY > 0){
                this.cursY -= 1;
                this.cursX = 42;
                while(this.terminalChars[cursX][cursY] == (char)0){
                    if(this.cursX > 0) {
                        cursX -= 1;
                    }else{
                        break;
                    }
                }
                if(terminalChars[cursX][cursY] != (char)0 && cursX != 42) cursX += 1;

                this.terminalChars[cursX][cursY] = (char)0;
            }
        }else if(code == 10){
            if(cursY < 24){
                this.cursX = 0;
                this.cursY += 1;
            }
        }else{
            this.terminalChars[cursX][cursY] = (char)code;
            if(cursX == 42){
                cursY += 1;
                cursX = 0;
            }else {
                cursX += 1;
            }

        }

    }

    @Override
    public void keyReleased(int code) {

    }

    public void boldString(int x, int y, String s, int color, GPU g){
        g.setColor(color);
        g.drawString(x - 1, y, s);
        g.drawString(x + 1, y, s);
        g.drawString(x, y - 1, s);
        g.drawString(x, y + 1, s);
        g.drawString(x - 1, y - 1, s);
        g.drawString(x + 1, y - 1, s);
        g.drawString(x - 1, y + 1, s);
        g.drawString(x + 1, y + 1, s);
        g.setColor(color + 8);
        g.drawString(x, y, s);
    }


    public static void main(String[] args){
        Main main = new Main();
        main.start();


    }

}
