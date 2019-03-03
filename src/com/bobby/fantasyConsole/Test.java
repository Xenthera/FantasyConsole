package com.bobby.fantasyConsole;

import org.python.util.PythonInterpreter;

import java.io.IOException;

public class Test{

    GPU gpu;

    PythonInterpreter interpreter;

    public Test(GPU gpu){
        this.gpu = gpu;
        interpreter = new PythonInterpreter();
        interpreter.set("GPU", this.gpu);
        try {
            String code = Utility.readFile("src/com/bobby/fantasyConsole/python/test.py");

            Thread t = (new Thread(() -> {
                try {
                    interpreter.exec(code);
                }catch (Exception e){
                    System.out.println("Thread was timed out... probably");
                }
            }));

            t.setDaemon(true);
            t.start();
            t.join(3000);

            if(t.isAlive()){
                t.stop();
                while (t.isAlive()){
                    Thread.sleep(200);
                }
                gpu.clear(0);
                gpu.setColor(13);
                gpu.drawString(0,0, "Process timed out");
                System.out.println("[WARNING] Thread killed, process took too long to execute");
            }

        } catch (IOException e) {
            gpu.setColor(13);
            gpu.drawString(0, 0, e.getMessage());
        } catch (InterruptedException e) {

        }
    }

}
