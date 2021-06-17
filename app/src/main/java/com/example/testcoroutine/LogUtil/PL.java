package com.example.testcoroutine.LogUtil;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

public class PL {
    private static PL instance = new PL();

    public static PL getInstance() {
        return instance;
    }

    private PL() {
        logSubject = new HashSet<>();
    }

    public interface LogListener {

        void onLog(String log);
    }

    //    public Subject<String, String> logSubject;
    public HashSet<LogListener> logSubject;

    public enum LEVEL {
        TRACE("T", 5),
        DEBUG("D", 4),
        INFO("I", 3),
        WARN("W", 2),
        ERR("E", 1),
        CRASH("C", 0);

        private String name;
        private int level;

        LEVEL(String name, int level) {
            this.name = name;
            this.level = level;
        }

        @Override
        public String toString() {
            return name;
        }

        public int getLevel() {
            return level;
        }
    }

    public static void t(String msg) {
        log(LEVEL.TRACE, msg);
    }

    public static void d(String msg) {
        log(LEVEL.DEBUG, msg);
    }

    public static void d(String tag, String msg) {
        log(LEVEL.DEBUG, String.format("[%s] %s", tag, msg));
    }

    public static void i(String tag, String msg) {
        log(LEVEL.INFO, String.format("[%s] %s", tag, msg));
    }

    public static void i(String msg) {
        log(LEVEL.INFO, msg);
    }

    public static void w(String tag, String msg) {
        log(LEVEL.WARN, String.format("[%s] %s", tag, msg));
    }

    public static void w(String msg) {
        log(LEVEL.WARN, msg);
    }

    public static void e(String tag, String msg) {
        log(LEVEL.ERR, String.format("[%s] %s", tag, msg));
    }

    public static void e(String msg) {
        log(LEVEL.ERR, msg);
    }

    public static void ex(Throwable ex) {
        exception(ex, "");
    }

    public static void ex(Throwable ex, String msg) {
        exception(ex, msg);
    }

    public static void crash(Throwable ex, String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("[" + LOG_TIME_FORMAT.format(new Date()) + "] ");
        sb.append("[" + LEVEL.ERR.toString() + "] ");
        sb.append("[" + android.os.Process.myPid() + "] ");
        sb.append("[" + Thread.currentThread().getName() + "] ");
        sb.append(msg);
        sb.append("\n########## CRASH ##########\n");
        sb.append(makeStackTraceString(ex));
//        getInstance().logSubject.onNext(sb.toString());
        broadcastLog(sb.toString());
    }

    private static final SimpleDateFormat LOG_TIME_FORMAT = new SimpleDateFormat("yy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH);

    private static void log(LEVEL level, String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("[" + LOG_TIME_FORMAT.format(new Date()) + "] ");
        sb.append("[" + level.toString() + "] ");
        sb.append("[" + android.os.Process.myPid() + "] ");
        sb.append("[" + Thread.currentThread().getName() + "] ");
        sb.append(msg);
//        getInstance().logSubject.onNext(sb.toString());
        broadcastLog(sb.toString());
    }

    private static void exception(Throwable ex, String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("[" + LOG_TIME_FORMAT.format(new Date()) + "] ");
        sb.append("[" + LEVEL.ERR.toString() + "] ");
        sb.append("[" + android.os.Process.myPid() + "] ");
        sb.append("[" + Thread.currentThread().getName() + "] ");
        sb.append(msg);
        sb.append("\n########## EXCEPTION ##########\n");
        sb.append(makeStackTraceString(ex));
        broadcastLog(sb.toString());
    }

    private static void broadcastLog(String log) {
//        for (LogListener ll : getInstance().logSubject) {
//            ll.onLog(log);
//        }
        System.out.println(log);
    }

    private static String makeStackTraceString(Throwable ex) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ex.printStackTrace(new PrintStream(baos));
        byte[] data = baos.toByteArray();
        try {
            baos.close();
        } catch (IOException ignore) {
        }

        return new String(data);
    }

    public static void printStack() {
        Throwable t = new Throwable();
        d(makeStackTraceString(t));
    }
}
