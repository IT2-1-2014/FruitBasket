package jp.co.icomsys.it21.fruitbasket;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * 未キャッチな例外のハンドリングクラス.
 * ハンドリングした情報はSDカードに出力
 */
public class FBUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static Context sContext = null;

    private static File BUG_REPORT_FILE = null;

    static {
        String sdcard = Environment.getExternalStorageDirectory().getPath();
        String path = sdcard + File.separator + "bug.txt";
        BUG_REPORT_FILE = new File(path);
    }

    public FBUncaughtExceptionHandler(Context context) {
        sContext = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            saveBugReport(ex);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
     *
     */
    private void saveBugReport(Throwable th) throws FileNotFoundException {
        StackTraceElement[] stacks = th.getStackTrace();
        File file = BUG_REPORT_FILE;
        PrintWriter pw;
        pw = new PrintWriter(new FileOutputStream(file));

        StringBuilder sb = new StringBuilder();
        for (StackTraceElement stack : stacks) {
            sb.setLength(0);
            // <class name>#<method name>:<line number>
            sb.append(stack.getClassName()).append("#");
            sb.append(stack.getMethodName()).append(":");
            sb.append(stack.getLineNumber());
            pw.println(sb.toString());
        }
        pw.close();

    }
}
