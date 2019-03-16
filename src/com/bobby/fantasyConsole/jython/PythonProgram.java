package com.bobby.fantasyConsole.jython;

import com.bobby.fantasyConsole.Module;
import org.python.core.*;
import org.python.util.PythonInterpreter;

import java.util.ArrayList;

public class PythonProgram {

    public PythonInterpreter interpreter;
    private PyFunction drawMethod;
    private PyFunction keyPressedMethod;
    private PyFunction keyTypedMethod;
    public boolean hasGameLoop = false;
    private String code;
    public String name;

    public PythonProgram(PythonInterpreter interpreter, String code, String name){
        this.interpreter = interpreter;
        this.code = code;
        this.name = name;
    }

    public String execute(){
        this.interpreter.set("_code", new PyString(code));
        this.interpreter.exec("msg = \"\"\ntry:\n exec(_code)\nexcept Exception as e:\n msg = str(e)");
        this.interpreter.exec("_hasLoop = \"def draw():\" in _code");
        PyBoolean hasLoop = (PyBoolean)this.interpreter.get("_hasLoop");
        PyString err = (PyString) this.interpreter.get("msg");
        if(hasLoop.getBooleanValue()) {
            this.drawMethod = (PyFunction) this.interpreter.get("draw");
            this.keyPressedMethod = (PyFunction) this.interpreter.get("key_pressed");
            this.keyTypedMethod = (PyFunction) this.interpreter.get("key_typed");
            if (this.drawMethod != null && !err.asString().equals("null")) {
                this.hasGameLoop = true;
            }
        }
        return err.asString();
    }


    public void draw(){
        this.drawMethod.__call__();
    }

    public void keyTyped(char code){
        if(this.keyTypedMethod != null) {
            this.keyTypedMethod.__call__(new PyInteger(Integer.valueOf(code)));
        }
    }

    public void keyPressed(int code){
        if(this.keyPressedMethod != null) {
            this.keyPressedMethod.__call__(new PyInteger(code));
        }
    }
}
