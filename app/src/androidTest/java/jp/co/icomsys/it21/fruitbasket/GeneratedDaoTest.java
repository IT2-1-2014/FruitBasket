package jp.co.icomsys.it21.fruitbasket;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import jp.co.icomsys.it21.fruitbasket.dao.AuthorsDao;
import jp.co.icomsys.it21.fruitbasket.dao.BookTitles;
import jp.co.icomsys.it21.fruitbasket.dao.BookTitlesDao;
import jp.co.icomsys.it21.fruitbasket.dao.DaoMaster;
import jp.co.icomsys.it21.fruitbasket.dao.DaoSession;
import jp.co.icomsys.it21.fruitbasket.dao.PublishersDao;

/**
 * 自動生成Daoクラスのユニットテスト
 *
 * Created by masaharu on 14/12/17.
 */

@Config(emulateSdk = 18, manifest = "src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class GeneratedDaoTest {

    Activity activity;
    DaoMaster.DevOpenHelper helper;
    SQLiteDatabase db;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(FBBookListActivity.class).create().get();
        helper = new DaoMaster.DevOpenHelper(activity, "fruitbasket-db", null);
        db = helper.getWritableDatabase();
    }

    @After
    public void teardrop() {
        if( db!=null ) db.close();
    }

    
    
    
    @Test
    public void getRecordCountFromEmptyTableTest() {
        DaoSession session = getSession();

        // BookTitlesDao
        BookTitlesDao bookTitlesDao = session.getBookTitlesDao();
        Assert.assertEquals(0, bookTitlesDao.count());
        
        // AuthorsDao
        AuthorsDao authorsDao = session.getAuthorsDao();
        Assert.assertEquals(0, authorsDao.count());
        
        // PublishersDao
        PublishersDao publishersDao = session.getPublishersDao();
        Assert.assertEquals(0, publishersDao.count());
        
        // RegisteredBooksDao
        
    }
    
    @Test
    public void insertTest() {
        DaoSession session = getSession();
        BookTitlesDao titlesDao = session.getBookTitlesDao();

        BookTitles booktitle = new BookTitles();
        booktitle.setTitle("ABC");
        booktitle.setTitleKana("えいびーしー");
        titlesDao.insert(booktitle);
        
        Assert.assertEquals(1, titlesDao.count());
        
        
    } 
    

    private DaoSession getSession() {
        return new DaoMaster(db).newSession();
    }
}
