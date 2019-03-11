package com.bobby.fantasyConsole.jython;

import com.bobby.fantasyConsole.Module;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import java.util.ArrayList;

public class PythonProgram {

    public PythonInterpreter interpreter;
    private PyFunction drawMethod;
    private PyFunction keyPressedMethod;
    public boolean hasGameLoop = false;
    private String code;

    public PythonProgram(PythonInterpreter interpreter, String code){
        this.interpreter = interpreter;
        this.code = code;
    }

    public String execute(){
        this.interpreter.set("_code", new PyString(code));
        this.interpreter.exec("msg = \"\"\ntry:\n exec(_code)\nexcept Exception as e:\n msg = str(e)");
        this.drawMethod = (PyFunction)this.interpreter.get("draw");
        this.keyPressedMethod = (PyFunction)this.interpreter.get("key_pressed");
        PyString err = (PyString) this.interpreter.get("msg");
        if(this.drawMethod != null && !err.asString().equals("null")){
            this.hasGameLoop = true;
        }

        return err.asString();
    }

    public void draw(){
        this.drawMethod.__call__();
    }

    public void keyTyped(char code){
        if(this.keyPressedMethod != null) {
            this.keyPressedMethod.__call__(new PyInteger(Integer.valueOf(code)));
        }
    }
}
