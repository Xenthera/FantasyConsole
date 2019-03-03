package com.bobby.fantasyConsole;

public class TextBuffer {

    char[] terminalChars;

    int width = 43;
    int height = 43;

    public TextBuffer(int size){
        terminalChars = new char[size];
        for (int i = 0; i < terminalChars.length; i++) {
            terminalChars[i] = (char)0;
        }
    }

    public TextBuffer(TextBuffer buffer, int lineOffset){
        terminalChars = new char[buffer.terminalChars.length];
        for (int i = 0; i < buffer.terminalChars.length - (width * lineOffset); i++) {
            terminalChars[i] = buffer.terminalChars[i + (width * lineOffset)];
        }
    }
}
