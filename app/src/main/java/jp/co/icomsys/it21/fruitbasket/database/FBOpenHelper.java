package jp.co.icomsys.it21.fruitbasket.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import jp.co.icomsys.it21.fruitbasket.dao.DaoMaster;

/**
 * FruitBasket用OpenHelper. *
 * GreenDaoで自動生成されるDaoMasterの内部クラスのアプリ独自拡張。*
 * DBバージョンの更新に伴うマイグレーション処理はここで行う。*
 * Created by masaharu on 15/03/16.
 */
public class FBOpenHelper extends DaoMaster.OpenHelper {
    // Mode(Develop or Product)
    public static final String DB_MODE_PRODUCT = "Product";
    public static final String DB_MODE_DEVELOP = "Develop";
    private String mDBMode = DB_MODE_DEVELOP;
    // Log Tag
    private static final String LOG_TAG = FBOpenHelper.class.getSimpleName();

    public FBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
        if (DB_MODE_DEVELOP.equals(mDBMode)) {
            DaoMaster.dropAllTables(db, true);
            onCreate(db);
        } else if (db.needUpgrade(newVersion)) {
            // 将来的にDBの定義のバージョンが上がったとき、マイグレーションの処理を書く
            for (int patchVersion = oldVersion + 1; patchVersion <= newVersion; ++patchVersion) {
                switch (patchVersion) {
                    default:
                        break;
                }
            }
        }
    }
}
