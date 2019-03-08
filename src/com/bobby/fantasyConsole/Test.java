//package com.bobby.fantasyConsole;
//
//import com.bobby.fantasyConsole.Modules.GPU;
//import com.bobby.fantasyConsole.Modules.Terminal.Terminal;
//import com.bobby.fantasyConsole.jython.JythonClassLoader;
//import org.python.core.PyString;
//import org.python.util.PythonInterpreter;
//
//import java.io.IOException;
//
//public class Test{
//
//    public Terminal terminal;
//
//    PythonInterpreter interpreter;
//
//    String appPath = "test.py";
//    String biosPath = "bios.py";
//
//    public Test(GPU gpu){
//        this.terminal = new Terminal();
//
//        interpreter = new PythonInterpreter();
//        interpreter.getSystemState().setClassLoader(classLoader);
//        interpreter.set("terminal", this.terminal);
//        try {
//            String code = Utility.readFile("Python/" + appPath);
//            String bios = Utility.readFile("Python/" + biosPath);
//
//
//    }
//
//}
