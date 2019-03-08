package com.bobby.fantasyConsole.Modules.Terminal;


import com.bobby.fantasyConsole.Module;
import com.bobby.fantasyConsole.Modules.GPU;

import java.util.Random;

public class Terminal extends Module {

    boolean[] keysPressed;

    TextBuffer textBuffer;

    boolean isCursorFlashing = true;
    private boolean _cursorFlash = true;

    int backgroundColor = 0;
    int textBackgroundColor = 0;
    int textColor = 15;

    int cursX = 0;
    int cursY = 0;

    int width = 43;
    int height = 43;
    int count = 0;
    int color = 0;

    public Terminal(){
        super("terminal");
        textBuffer = new TextBuffer(width * height);
        keysPressed = new boolean[256];
    }

    public void clear(){
        for (int i = 0; i < textBuffer.terminalChars.length; i++) {
            textBuffer.terminalChars[i] = (char)0;
        }
    }

    public void clearLine(int line){
        if(line < height){
            int idx = getIndex(line, 0);
            for (int i = 0; i < width; i++) {
                textBuffer.terminalChars[idx + i] = (char)0;
            }
        }
    }

    public void write(String s){
        int idx = getIndex(cursX, cursY);
        for (int i = 0; i < s.length(); i++) {

            if(idx + i == textBuffer.terminalChars.length){
                textBuffer = new TextBuffer(textBuffer, 1);
                idx -= width;
            }

            textBuffer.terminalChars[idx + i] = s.charAt(i);
            textBuffer.charColors[idx + i] = this.textColor;
            textBuffer.charBgColors[idx + i] = this.textBackgroundColor;
        }
        idx += s.length();
        this.cursX = (idx % width);
        this.cursY = (idx / width);
    }

    public void print(String s){
            String out = "";
            String[] lines;

            if(s.indexOf("\t") != -1) {
                lines = s.split("\t");
                //System.out.println(Arrays.toString(lines));
                for (int i = 0; i < lines.length; i++) {
                    out += lines[i];
                    if(i < lines.length - 1) {
                        for (int j = 0; j < 2; j++) {
                            out += " ";
                        }
                    }
                }
                if(lines.length == 1){ // There was a tab but no strings surrounding it

                    for (int j = 0; j < 2; j++) {
                        out += " ";
                    }
                }

            }else{
                out = s;
            }
            if(out.indexOf("\n") != -1) {
                lines = out.split("\n");

                for (int i = 0; i < lines.length; i++) {
                    this.write(lines[i]);
                    if(i < lines.length - 1) {
                        this.cursY += 1;
                        this.cursX = 0;
                    }
                }

                if(lines.length == 0){ // There was a new line but no strings surrounding it
                    this.cursY += 1;
                    this.cursX = 0;
                }
            }else{
                this.write(out);
            }


    }


    public void print(float s){
        this.print(String.valueOf(s));
    }

    public void print(int s){
        this.print(String.valueOf(s));
    }

    public void print(boolean s){
        this.print(String.valueOf(s));
    }

    public void write(float s){
        this.write(String.valueOf(s));
    }

    public void write(int s){
        this.write(String.valueOf(s));
    }

    public void write(boolean s){
        this.write(String.valueOf(s));
    }

    public void setCursorPos(int x, int y){
        if(x >= width) x = width - 1;
        if(y >= height) y = height - 1;
        if(x < 0) x = 0;
        if(y < 0) y = 0;

        this.cursX = x;
        this.cursY = y;
    }

    private int getIndex(int x, int y){
        return (width * y + x);
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public void setTextColor(int color){
        this.textColor = color;
    }
    public void setBackgroundColor(int color){
        this.textBackgroundColor = color;
    }

    public void draw(GPU g) {
        count += 1;

        g.clear(backgroundColor);
        for (int i = 0; i < textBuffer.terminalChars.length; i++) {
            g.setColor(textBuffer.charBgColors[i]);
            g.fillRect((i % 43) * 6, (i / 43) * 6, 6,6);
            g.setColor(textBuffer.charColors[i]);
            g.drawString((i % 43) * 6, (i / 43) * 6, String.valueOf(textBuffer.terminalChars[i]));

        }
        if(_cursorFlash && isCursorFlashing)
        g.drawLine(this.cursX * 6, this.cursY * 6 + 6, this.cursX * 6 + 5, this.cursY * 6 + 6);


        //setCursorPos(10,10);
        String s = "abcdefghijklmnopqrstuvwxyz";

        if(isCursorFlashing){
            if(count % 15 == 0){
                _cursorFlash = !_cursorFlash;
            }
        }
//        String str = getSaltString();
//        setCursorPos(width / 2 - str.length() / 2, height / 2);
//        write(str);

//        //Test code
//        g.setColor(color);
//        g.drawString(0,g.getHeight() - 12, String.valueOf(color));
//        g.setColor(15);
//        g.drawString(0,g.getHeight() - 6, "(" + cursX + ", " + cursY + ")");

    }

    public void setCursorFlash(boolean flash){
        this.isCursorFlashing = flash;
    }

    public void keyReleased(int code){
        keysPressed[code] = false;
    }

    public boolean isKeyDown(int code){
        return keysPressed[code];
    }

    public void keyTyped(char c){
        this.setTextColor(color);
        int code = (int)c;
        if(code == 8) { // backspace
            int idx = getIndex(cursX, cursY);
            if(idx > 0) {
                idx -= 1;
                textBuffer.terminalChars[idx] = (char) 0;
                textBuffer.charColors[idx] =15;
                textBuffer.charBgColors[idx] = 0;
            }
            if(idx > 0) {
                if (textBuffer.terminalChars[idx - 1] == (char) 0) {
                    while (textBuffer.terminalChars[idx - 1] == (char) 0) { //go to next available string
                        if (idx - 1 == 0){
                            idx = 0;
                            break;
                        }

                        if(idx % width == 0){
                            break;
                        }
                        idx -= 1;
                    }
                }
            }
            this.cursX = (idx % width);
            this.cursY = (idx / width);
        }else if(code == 10){ // enter
            if(cursY < 43 - 1){
                this.cursX = 0;
                this.cursY += 1;
            }

        }else {
            if (getIndex(cursX, cursY) < textBuffer.terminalChars.length) {
                textBuffer.terminalChars[getIndex(cursX, cursY)] = c;
                textBuffer.charColors[getIndex(cursX, cursY)] = this.textColor;
                textBuffer.charBgColors[getIndex(cursX, cursY)] = this.textBackgroundColor;
            }
            if (getIndex(cursX, cursY) < textBuffer.terminalChars.length) {
                if (cursX == 42) {
                    cursY += 1;
                    cursX = 0;
                } else {
                    cursX += 1;
                }
            }
        }
    }

    public int getCursX(){
        return cursX;
    }

    public int getCursY(){
        return cursY;
    }

    public void keyPressed(int code){
        keysPressed[code] = true;
        if(code == 37){
            color--;
        }
        if(code == 39){
            color++;
        }

        if(color < 0){
            color = 0;
        }
        if(color > 15){
            color = 15;
        }
    }

}
