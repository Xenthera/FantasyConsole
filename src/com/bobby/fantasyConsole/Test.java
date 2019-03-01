package com.bobby.fantasyConsole;

import org.python.util.PythonInterpreter;

import java.io.IOException;

public class Test extends Thread {

    GPU gpu;
    int count = 0;
    PythonInterpreter interpreter;

    public Test(GPU gpu){
        this.gpu = gpu;
        interpreter = new PythonInterpreter();
    }

    public void run(){

        interpreter.set("GPU", this.gpu);
        try {
            String code = Utility.readFile("src/com/bobby/fantasyConsole/python/test.py");
            interpreter.exec(code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
