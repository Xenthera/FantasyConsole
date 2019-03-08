package com.bobby.fantasyConsole;

import com.bobby.fantasyConsole.Modules.GPU;
import com.bobby.fantasyConsole.Modules.Shell;
import com.bobby.fantasyConsole.Modules.Terminal.Terminal;
import com.bobby.fantasyConsole.jython.JythonClassLoader;
import com.bobby.fantasyConsole.jython.PythonProgram;
import org.python.core.PyString;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Computer {//Handle creation of python environment, and boot the bios.
    Stack<PythonProgram> programStack;
    PythonProgram currentProgram;
    JythonClassLoader classLoader;
    Terminal terminalReference;


    String biosPath = "Python/bios.py";

    ArrayList<Module> modules;

    //TODO: add file system
    public Computer(GPU g) {
        classLoader = new JythonClassLoader(Thread.currentThread().getContextClassLoader());
        modules = new ArrayList<>();
        terminalReference = new Terminal();
        modules.add(terminalReference);
        modules.add(g);
        modules.add(new Shell(this));

        this.programStack = new Stack<>();

        this.run("Python/Rom/Shell.py");
    }

    public void run(String path) {
        try {

            String code = Utility.readFile(path);
            String bios = Utility.readFile(biosPath);
            currentProgram = new PythonProgram(this.classLoader, code, bios, this.modules);
            String errMsg = currentProgram.execute();
            if(errMsg != null){
                this.terminalReference.setTextColor(13);
                this.terminalReference.print(errMsg);
            }
            if(currentProgram.hasGameLoop){
                this.programStack.push(currentProgram);
            }

        } catch (IOException e) {
            terminalReference.setTextColor(13);
            terminalReference.print(e.getMessage());
        }

    }

    public void draw(GPU g, float delta) {
        if(this.programStack.size() > 0) {
            PythonProgram prg = this.programStack.elementAt(0);
            prg.draw();
        }else{
            this.terminalReference.draw(g);
        }
    }
    public void keyPressed(int code) {
        this.terminalReference.keyPressed(code);
        if(this.programStack.size() > 0){
            this.programStack.elementAt(0).keyPressed(code);
        }
    }
    public void keyReleased(int code){
        this.terminalReference.keyReleased(code);
    }
    public void keyTyped(char c){
        //this.terminalReference.keyTyped(c);
    }

//    private void runWithTimeout(int timeout, String code) {
//        try {
//            Thread t = (new Thread(() -> {
//                try {
//                    this.currentProgram.interpreter.set("_code", new PyString(code));
//                    this.currentProgram.interpreter.exec("msg = \"\"\ntry:\n exec(_code)\nexcept Exception as e:\n msg = str(e)");
//                    PyString err = (PyString) this.currentProgram.interpreter.get("msg");
//                    this.terminalReference.setTextColor(13);
//                    this.terminalReference.print(err.toString());
//                } catch (Exception e) {
//                    System.out.println("Error: " + e.getLocalizedMessage());
//                }
//            }));
//
//            t.setDaemon(true);
//            t.start();
//            t.join(timeout);
//
//            if (t.isAlive()) {
//                t.stop();
//                while (t.isAlive()) {
//                    Thread.sleep(200);
//                }
//                this.terminalReference.clear();
//                this.terminalReference.setTextColor(13);
//                this.terminalReference.print("Process timed out");
//            }
//
//        } catch(InterruptedException e){
//
//        }
//    }

}
