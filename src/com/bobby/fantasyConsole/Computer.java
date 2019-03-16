package com.bobby.fantasyConsole;

import com.bobby.fantasyConsole.Modules.GPU;
import com.bobby.fantasyConsole.Modules.Shell;
import com.bobby.fantasyConsole.Modules.Terminal.Terminal;
import com.bobby.fantasyConsole.jython.JythonClassLoader;
import com.bobby.fantasyConsole.jython.PythonProgram;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Computer {//Handle creation of python environment, and boot the bios.
    Stack<PythonProgram> programStack;
    PythonProgram currentProgram;
    JythonClassLoader classLoader;
    Terminal terminal;

    PythonInterpreter interpreter;
    String biosPath = "Python/bios.py";

    ArrayList<Module> modules;

    //TODO: add file system
    public Computer(GPU gpu) {
        classLoader = new JythonClassLoader(Thread.currentThread().getContextClassLoader());
        modules = new ArrayList<>();
        terminal = new Terminal();
        modules.add(terminal);
        modules.add(gpu);
        modules.add(new Shell(this));

        this.interpreter = new PythonInterpreter();
        this.interpreter.getSystemState().setClassLoader(classLoader);
        for (int i = 0; i < modules.size(); i++) {
            this.interpreter.set(modules.get(i).name, modules.get(i));
        }
        this.run(biosPath);
        this.programStack = new Stack<>();

        this.run("Python/Rom/Shell.py");
    }

    public void run(String path) {
        try {

            String code = Utility.readFile(path);
            currentProgram = new PythonProgram(this.interpreter, code, path);
            String errMsg = currentProgram.execute();
            terminal.print("");
            if(!errMsg.equals("")){
                this.terminal.setTextColor(13);
                this.terminal.print(errMsg);
            }
            if(currentProgram.hasGameLoop){
                this.programStack.push(currentProgram);
            }

        } catch (IOException e) {
            terminal.setTextColor(13);
            terminal.print(e.getMessage());
        }

    }

    public void exitProgram(){
        this.programStack.pop();
        if(this.programStack.size() == 0){
            System.exit(0); // We're done
        }
        this.currentProgram = this.programStack.lastElement();
    }

    public void draw(GPU g, float delta) {

        if(this.programStack.size() == 0){
            System.exit(0); // We're done
        }

        PythonProgram prg = this.programStack.lastElement();
        prg.draw();
    }
    public void keyPressed(int code) {
        if(this.programStack.size() > 0){
            this.programStack.lastElement().keyPressed(code); //TODO: Idk if this will be permanent... gotta figure out what to do here
        }

    }
    public void keyReleased(int code){
        this.terminal.keyReleased(code);
    }
    public void keyTyped(char c){
        if(this.programStack.size() > 0){
            this.programStack.lastElement().keyTyped(c); //TODO: Idk if this will be permanent... gotta figure out what to do here
        }
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
