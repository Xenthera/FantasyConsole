package com.bobby.fantasyConsole;

import org.python.core.Py;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import java.io.IOException;

public class Test{

    public Terminal terminal;

    PythonInterpreter interpreter;

    String appPath = "test.py";
    String biosPath = "bios.py";

    public Test(Terminal terminal){
        this.terminal = terminal;
        interpreter = new PythonInterpreter();
        interpreter.set("terminal", this.terminal);
        try {
            String code = Utility.readFile("Python/" + appPath);
            String bios = Utility.readFile("Python/" + biosPath);

            Thread t = (new Thread(() -> {
                try {
                    interpreter.exec(bios);
                    interpreter.set("_code", new PyString(code));
                    interpreter.exec("msg = \"\"\ntry:\n exec(_code)\nexcept Exception as e:\n msg = str(e)");
                    PyString err = (PyString)interpreter.get("msg");
                    terminal.setTextColor(13);
                    terminal.print(err.toString());
                }catch (Exception e){
                    System.out.println("Error: " + e.getLocalizedMessage());
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
                this.terminal.clear();
                this.terminal.setTextColor(13);
                this.terminal.print("Process timed out");
            }

        } catch (IOException e) {
            terminal.setTextColor(13);
            terminal.print(e.getMessage());
        } catch (InterruptedException e) {

        }
    }

}
