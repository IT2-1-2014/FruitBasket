package jp.co.icomsys.it21.fruitbasket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import jp.co.icomsys.it21.fruitbasket.dao.RegisteredBooks;
import jp.co.icomsys.it21.fruitbasket.database.FBDatabaseAdapter;


public class BookRegistrationActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, View.OnClickListener {

    /**
     * ログ用のタグ
     */
    private static final String LOG_TAG = BookRegistrationActivity.class.getSimpleName();
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private FBDatabaseAdapter mFBDB;

    private Button mTitleClearButton, mTitleKanaClearButton;
    private Button mAuthorClearButton, mAuthorKanaClearButton;
    private Button mPublisherClearButton, mPublisherKanaClearButton;
    private Button mISBNClearButton;

    private EditText mTitleEdit, mTitleKanaEdit;
    private EditText mAuthorEdit, mAuthorKanaEdit;
    private EditText mPublisherEdit, mPublisherKanaEdit;
    private EditText mISBNEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // エラーハンドラ設定
        Context context = getApplicationContext();
        Thread.setDefaultUncaughtExceptionHandler(new FBUncaughtExceptionHandler(context));

        // データベースアダプタ
        mFBDB = new FBDatabaseAdapter(context);

        // LayoutXMLとバインディング
        //setContentView(R.layout.activity_book_registration);
        setContentView(R.layout.activity_fbbook_registr_layout);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        findViews();
        setEventListener();


    }

    void findViews() {
        // 書籍イメージ

        // タイトル情報関連
        mTitleClearButton = (Button) findViewById(R.id.title_clear_button);
        mTitleKanaClearButton = (Button) findViewById(R.id.title_kana_clear_button);
        mTitleEdit = (EditText) findViewById(R.id.title_edit);
        mTitleKanaEdit = (EditText) findViewById(R.id.title_kana_edit);

        // 著者情報関連
        mAuthorClearButton = (Button) findViewById(R.id.author_clear_button);
        mAuthorKanaClearButton = (Button) findViewById(R.id.author_kana_clear_button);
        mAuthorEdit = (EditText) findViewById(R.id.author_edit);
        mAuthorKanaEdit = (EditText) findViewById(R.id.author_kana_edit);

        // 出版社情報
        mPublisherClearButton = (Button) findViewById(R.id.publisher_clear_button);
        mPublisherKanaClearButton = (Button) findViewById(R.id.publisher_kana_clear_button);
        mPublisherEdit = (EditText) findViewById(R.id.publisher_edit);
        mPublisherKanaEdit = (EditText) findViewById(R.id.publisher_kana_edit);

        // ISBNコード
        mISBNClearButton = (Button) findViewById(R.id.isbn_clear_button);
        mISBNEdit = (EditText) findViewById(R.id.isbn_edit);
    }

    void setEventListener() {
        mTitleClearButton.setOnClickListener(this);
        mTitleKanaClearButton.setOnClickListener(this);
        mTitleEdit.setOnClickListener(this);
        mTitleKanaEdit.setOnClickListener(this);
        mAuthorClearButton.setOnClickListener(this);
        mPublisherClearButton.setOnClickListener(this);
        mISBNClearButton.setOnClickListener(this);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.book_registration, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_register) {
            registrationData();
            transitionActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == mTitleClearButton) {
            mTitleEdit.setText("");
        } else if (v == mTitleKanaEdit) {
            mTitleKanaEdit.setText("");
        } else if (v == mAuthorClearButton) {
            mAuthorEdit.setText("");
        } else if (v == mAuthorKanaClearButton) {
            mAuthorKanaEdit.setText("");
        } else if (v == mPublisherClearButton) {
            mPublisherEdit.setText("");
        } else if (v == mPublisherKanaClearButton) {
            mPublisherKanaEdit.setText("");
        } else if (v == mISBNClearButton) {
            mISBNEdit.setText("");
        }
    }

    private void registrationData() {


//        // 仮・・・
//        Context context = getApplicationContext();
//        SQLiteDatabase db = new DaoMaster.DevOpenHelper(context, "fb-db", null).getWritableDatabase();
//        DaoSession daoSession = new DaoMaster(db).newSession();
//
//        BookTitlesDao bookTitlesDao = daoSession.getBookTitlesDao();
//        AuthorsDao authorsDao = daoSession.getAuthorsDao();
//        PublishersDao publishersDao = daoSession.getPublishersDao();
//        RegisteredBooksDao regiBooksDao = daoSession.getRegisteredBooksDao();

//        // タイトル情報
//        BookTitles titleEntity = new BookTitles();
//        titleEntity.setTitle(mTitleEdit.getText().toString());
//        titleEntity.setTitleKana(mTitleKanaEdit.getText().toString());
//
//        // 著者情報
//        Authors authorEntity = new Authors();
//        authorEntity.setAuthor(mAuthorEdit.getText().toString());
//        authorEntity.setAuthorKana(mAuthorKanaEdit.getText().toString());
//
//        // 出版社情報
//        Publishers publisherEntity = new Publishers();
//        publisherEntity.setPublisher(mPublisherEdit.getText().toString());
//        publisherEntity.setPublisherKana(mPublisherKanaEdit.getText().toString());

        // 図書登録情報
        RegisteredBooks registeredBook = new RegisteredBooks();
        registeredBook.setTitle(mTitleEdit.getText().toString());
        registeredBook.setTitleKana(mTitleKanaEdit.getText().toString());

        registeredBook.setAuthor(mAuthorEdit.getText().toString());
        registeredBook.setAuthorKana(mAuthorKanaEdit.getText().toString());

        registeredBook.setPublisher(mPublisherEdit.getText().toString());
        registeredBook.setPublisherKana(mPublisherKanaEdit.getText().toString());

        registeredBook.setIsbn(mISBNEdit.getText().toString());

        RegisteredBooks rsltRegisteredBook = mFBDB.saveOneBook(registeredBook);

        Log.v(LOG_TAG, "登録データ：" + rsltRegisteredBook.toString());

    }

    private void fetchBooks() {
        List<RegisteredBooks> books = mFBDB.findAllRegisteredBooks();
        for (RegisteredBooks book : books) {
            Log.v(LOG_TAG, book.toString());
        }
    }

    private void transitionActivity() {
        Intent intent = new Intent(getApplication(), BookListActivity.class);
//        Intent intent = new Intent(getApplication(), BookListsActivity.class);
        //Intent intent = new Intent(getApplication(), BookRegistrationActivity.class);
        //Intent intent = new Intent(getApplication(), WebSearchActivity.class);
        startActivity(intent);
        //SplashActivity.this.finish();
        this.finish();
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_book_registration, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((BookRegistrationActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }


    }

}
