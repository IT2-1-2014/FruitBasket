package jp.co.icomsys.it21.fruitbasket;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import jp.co.icomsys.it21.fruitbasket.dao.AuthorsDao;
import jp.co.icomsys.it21.fruitbasket.dao.BookTitles;
import jp.co.icomsys.it21.fruitbasket.dao.BookTitlesDao;
import jp.co.icomsys.it21.fruitbasket.dao.DaoMaster;
import jp.co.icomsys.it21.fruitbasket.dao.DaoSession;
import jp.co.icomsys.it21.fruitbasket.dao.PublishersDao;
import jp.co.icomsys.it21.fruitbasket.dao.RegisteredBooksDao;


public class BookRegistrationActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, View.OnClickListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private Button mTitleClearButton, mTitleKanaClearButton;
    private Button mAuthorClearButton, mAuthorKanaClearButton;
    private Button mPublisherClearButton, mPublisherKanaClearButton;

    private EditText mTitleEdit, mTitleKanaEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // エラーハンドラ設定
        Context context = getApplicationContext();
        Thread.setDefaultUncaughtExceptionHandler(new FBUncaughtExceptionHandler(context));

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

        bindComponent();

    }

    void bindComponent() {
        mTitleClearButton = (Button) findViewById(R.id.title_clear_button);
        mTitleClearButton.setOnClickListener(this);
        mTitleKanaClearButton = (Button) findViewById(R.id.title_kana_clear_button);
        mTitleKanaClearButton.setOnClickListener(this);
        mTitleEdit = (EditText) findViewById(R.id.title_edit);
        mTitleEdit.setOnClickListener(this);
        mTitleKanaEdit = (EditText) findViewById(R.id.title_kana_edit);
        mTitleKanaEdit.setOnClickListener(this);

        mAuthorClearButton = (Button) findViewById(R.id.author_clear_button);
        mAuthorClearButton.setOnClickListener(this);

        mPublisherClearButton = (Button) findViewById(R.id.publisher_clear_button);
        mPublisherClearButton.setOnClickListener(this);
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
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
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
        if (id == R.id.action_settings) {
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
            //　テスト
            registrationData();

        } else if (v == mPublisherClearButton) {
            // テスト


        }
    }

    public void registrationData() {
        // 仮・・・
        Context context = getApplicationContext();
        SQLiteDatabase db = new DaoMaster.DevOpenHelper(context, "fb-db", null).getWritableDatabase();
        DaoSession daoSession = new DaoMaster(db).newSession();

        BookTitlesDao bookTitlesDao = daoSession.getBookTitlesDao();
        AuthorsDao authorsDao = daoSession.getAuthorsDao();
        PublishersDao publishersDao = daoSession.getPublishersDao();
        RegisteredBooksDao regiBooksDao = daoSession.getRegisteredBooksDao();

        BookTitles titleEntity = new BookTitles();
        titleEntity.setTitle(mTitleEdit.getText().toString());
        titleEntity.setTitleKana(mTitleKanaEdit.getText().toString());
        long titleId = bookTitlesDao.insert(titleEntity);


    }

    public void getBookData() {


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
