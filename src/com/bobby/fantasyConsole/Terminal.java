package com.bobby.fantasyConsole;


import java.util.Random;

public class Terminal {

    boolean[] keysPressed;

    TextBuffer textBuffer;

    boolean isCursorFlashing = true;
    private boolean _cursorFlash = true;

    int backgroundColor = 0;
    int textColor = 15;

    int cursX = 0;
    int cursY = 0;

    int width = 43;
    int height = 43;
    int count = 0;

    public Terminal(){
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
        }
        idx += s.length();
        this.cursX = (idx % width);
        this.cursY = (idx / width);
    }

    public void print(String s){
        this.write(s);
        this.cursX = 0;
        this.cursY += 1;
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

    public void draw(GPU g, float delta) {
        count += 1;
        g.clear(backgroundColor);
        g.setColor(8);
        for (int i = 0; i < textBuffer.terminalChars.length; i++) {
            //g.drawString((i % 43) * 6 + 1, (i / 43) * 6 + 1, String.valueOf(textBuffer.terminalChars[i]));
            //g.drawString((i % 43) * 6, (i / 43) * 6 + 1, String.valueOf(textBuffer.terminalChars[i]));
            //g.drawString((i % 43) * 6 + 1, (i / 43) * 6, String.valueOf(textBuffer.terminalChars[i]));
        }
        g.setColor(15);
        for (int i = 0; i < textBuffer.terminalChars.length; i++) {
            g.drawString((i % 43) * 6, (i / 43) * 6, String.valueOf(textBuffer.terminalChars[i]));
        }
        if(_cursorFlash)
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


    }

    public void keyReleased(int code){
        keysPressed[code] = false;
    }

    public boolean isKeyDown(int code){
        return keysPressed[code];
    }

    public void keyTyped(char c){

        int code = (int)c;

        System.out.println(code);

        if(code == 8) { // backspace
            int idx = getIndex(cursX, cursY);
            if(idx > 0) {
                idx -= 1;
                textBuffer.terminalChars[idx] = (char) 0;
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

    public void keyPressed(int code){
        keysPressed[code] = true;
    }

}
