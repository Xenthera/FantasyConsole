package com.bobby.fantasyConsole;

public class FontSet {
    private String[] font;

    public FontSet(){
        font = new String[128];
        for (int i = 0; i < font.length; i++) {
            font[i] = "000000000000000000000000000000000000";
        }
        font[65] = "011100100010111110100010100010000000";
        font[66] = "111100100010111100100010111100000000";
        font[67] = "011100100010100000100010011100000000";
        font[68] = "111100100010100010100010111100000000";
        font[69] = "111110100000111100100000111110000000";
        font[70] = "111110100000111100100000100000000000";
        font[71] = "011110100000100110100010011110000000";
        font[72] = "100010100010111110100010100010000000";
        font[73] = "111110001000001000001000111110000000";
        font[74] = "000010000010000010100010011100000000";
        font[75] = "100010100100111000100100100010000000";
        font[76] = "100000100000100000100000111110000000";
        font[77] = "110110101010100010100010100010000000";
        font[78] = "100010110010101010100110100010000000";
        font[79] = "011100100010100010100010011100000000";
        font[80] = "111100100010111100100000100000000000";
        font[81] = "111100100010100010100110111110000000";
        font[82] = "111100100010111100100010100010000000";
        font[83] = "011110100000011100000010111100000000";
        font[84] = "111110001000001000001000001000000000";
        font[85] = "100010100010100010100010011100000000";
        font[86] = "100010100010100010010100001000000000";
        font[87] = "100010100010100010101010110110000000";
        font[88] = "100010010100001000010100100010000000";
        font[89] = "100010100010010100001000001000000000";
        font[90] = "111110000100001000010000111110000000";
    }

    public String get(int c){
        if(c < 0){
            c = 0;
        }else if(c >= this.font.length){
            c = this.font.length - 1;
        }

        return this.font[c];
    }
}