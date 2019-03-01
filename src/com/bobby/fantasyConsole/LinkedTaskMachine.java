package com.bobby.fantasyConsole;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedTaskMachine {
    public static LinkedBlockingQueue<ITask> queue;
    static Object m_lock;

    static {
        m_lock = new Object();
        queue = new LinkedBlockingQueue<ITask>();
    }


}
