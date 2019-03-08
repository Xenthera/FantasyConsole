package com.bobby.fantasyConsole.Modules;

import com.bobby.fantasyConsole.Computer;
import com.bobby.fantasyConsole.Module;

public class Shell extends Module {

    private Computer computerReference;
    String cwd = "/home";

    public Shell(Computer computer){
        super("shell");
        this.computerReference = computer;
    }

    public void run(String path){
        this.computerReference.run(path);
    }

    public String getCWD(){
        return this.cwd;
    }
}
