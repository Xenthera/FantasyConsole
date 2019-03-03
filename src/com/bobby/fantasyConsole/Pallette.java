package com.bobby.fantasyConsole;

import java.awt.*;

public class Pallette {


    private Color[] colors = new Color[32];

    public Pallette(){
        colors[0] = new Color(23, 17, 31);
        colors[1] = new Color(34,52,209);
        colors[2] = new Color(12,126,69);
        colors[3] = new Color(68,170,204);
        colors[4] = new Color(138, 54, 34);
        colors[5] = new Color(92, 46, 120);
        colors[6] = new Color(170, 92, 61);
        colors[7] = new Color(181, 181, 181);
        colors[8] = new Color(94, 96, 110);
        colors[9] = new Color(76, 129, 251);
        colors[10] = new Color(108, 217, 71);
        colors[11] = new Color(123, 226, 249);
        colors[12] = new Color(235, 138, 96);
        colors[13] = new Color(226, 61, 105);
        colors[14] = new Color(255, 217, 63);
        colors[15] = new Color(255, 251, 234);
    }

    public Color get(int color){
        if(color > 15){
            return this.colors[15];
        }else if(color < 0){
            return this.colors[0];
        }else{
            return this.colors[color];
        }
    }

}
