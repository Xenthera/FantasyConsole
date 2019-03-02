package com.bobby.fantasyConsole;

import org.python.util.PythonInterpreter;

import java.io.IOException;

public class Test{

    GPU gpu;
    int count = 0;
    PythonInterpreter interpreter;

    public Test(GPU gpu){
        this.gpu = gpu;
        interpreter = new PythonInterpreter();
        interpreter.set("GPU", this.gpu);
        try {
            String code = Utility.readFile("src/com/bobby/fantasyConsole/python/test.py");

            Thread t = (new Thread(){
                @Override
                public void run() {
                    interpreter.exec(code);
                }
            });

            t.start();
            t.join(4000);
            if(t.isAlive()){
                t.stop();
                gpu.setColor(13);
                gpu.drawString(0,0, "Too long without yielding");
            }




        } catch (IOException e) {
            gpu.setColor(13);
            gpu.drawString(0, 0, e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
