package jp.co.icomsys.it21.fruitbasket;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 未キャッチな例外のハンドリングクラス.
 * ハンドリングした情報はSDカードに出力
 */
public class FBUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final static String BUG_REPORT_FILE_NAME = "fbbugreport.txt";

    private static final String LOG_TAG;
    private static final String PACKAGE_NAME;

    private static Context sContext = null;
    private File mBugReportFile = null;

    static {
        LOG_TAG = FBUncaughtExceptionHandler.class.getSimpleName();
        PACKAGE_NAME = FBUncaughtExceptionHandler.class.getPackage().getName();
    }


    public FBUncaughtExceptionHandler(Context context) {
        sContext = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            saveBugReport(ex);
        } catch (IOException e) {
            Log.e(LOG_TAG, "バグレポート出力に失敗", e);
            Log.e(LOG_TAG, "キャッチした例外", ex);
        }
    }

    /**
     * バグレポートファイルの生成　*
     */
    private void prepareBugReportFile() {

        if (mBugReportFile != null) return;

        File dataDir = sContext.getExternalFilesDir(null);

//        mBugReportFile = new File(dataDir.getAbsolutePath() + File.separator + PACKAGE_NAME + File.separator + BUG_REPORT_FILE_NAME);
        mBugReportFile = new File(dataDir.getPath() + File.separator + BUG_REPORT_FILE_NAME);
        Log.v(LOG_TAG, "BugReportFile[" + mBugReportFile.getPath() + "]");

        try {
            if (!dataDir.exists()) {
                if (!dataDir.mkdirs()) {
                    Log.w(LOG_TAG, "ディレクトリの生成が出来ませんでした。[" + mBugReportFile.getAbsolutePath() + "]");
                    mBugReportFile = null;
                } else if (!mBugReportFile.createNewFile()) {
                    Log.w(LOG_TAG, "ファイルの生成が出来ませんでした。[" + mBugReportFile.getAbsolutePath() + "]");
                    mBugReportFile = null;
                }
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "バグレポートファイルの生成に失敗[" + mBugReportFile.toString() + "]", e);
        }

    }

    /*
     *
     */
    private void saveBugReport(Throwable th) throws IOException {
        StackTraceElement[] stacks = th.getStackTrace();

        // バグレポートファイル生成
        prepareBugReportFile();

        if (mBugReportFile == null) {
            th.printStackTrace();
            return;
        }

        PrintWriter pw;
        pw = new PrintWriter(new FileOutputStream(mBugReportFile));

        StringBuilder sb = new StringBuilder();
        for (StackTraceElement stack : stacks) {
            sb.setLength(0);
            // <class name>#<method name>:<line number>
            sb.append(stack.getClassName()).append("#");
            sb.append(stack.getMethodName()).append(":");
            sb.append(stack.getLineNumber());
            pw.println(sb.toString());

        }
        pw.println(th.toString());
        pw.close();

    }
}
