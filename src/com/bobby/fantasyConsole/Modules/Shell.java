package com.bobby.fantasyConsole.Modules;

import com.bobby.fantasyConsole.Computer;
import com.bobby.fantasyConsole.Module;

public class Shell extends Module {

    private Computer computerReference;
    String cwd = "/";

    public Shell(Computer computer){
        super("shell");
        this.computerReference = computer;
    }

    public void run(String path){
        this.computerReference.run(path);
    }

    public void exit(){
        this.computerReference.exitProgram();
    }

    public String getCWD(){
        return this.cwd;
    }

    public void setCWD(String path){
        this.cwd = path + "/";
    }
}
