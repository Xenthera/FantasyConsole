package com.bobby.fantasyConsole.Modules.Terminal;

public class TextBuffer {

    char[] terminalChars;
    int[] charColors;
    int[] charBgColors;

    int width = 43;
    int height = 43;

    public TextBuffer(int size){
        terminalChars = new char[size];
        charColors = new int[size];
        charBgColors = new int[size];
        for (int i = 0; i < terminalChars.length; i++) {
            terminalChars[i] = (char)0;
            charColors[i] = 15;
            charBgColors[i] = 0;
        }
    }

    public TextBuffer(TextBuffer buffer, int lineOffset){
        terminalChars = new char[buffer.terminalChars.length];
        charColors = new int[buffer.terminalChars.length];
        charBgColors = new int[buffer.terminalChars.length];
        for (int i = 0; i < buffer.terminalChars.length - (width * lineOffset); i++) {
            terminalChars[i] = buffer.terminalChars[i + (width * lineOffset)];
            charColors[i] = buffer.charColors[i + (width * lineOffset)];
            charBgColors[i] = buffer.charBgColors[i + (width * lineOffset)];
        }
    }
}
