package jp.co.icomsys.it21.fruitbasket;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import jp.co.icomsys.it21.fruitbasket.dao.RegisteredBooks;
import jp.co.icomsys.it21.fruitbasket.database.FBDatabaseAdapter;


public class BookListActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, AdapterView.OnItemClickListener {
    /**
     * ログ用のタグ
     */
    private static final String LOG_TAG = BookListActivity.class.getSimpleName();

    /**
     * データベースアダプタ
     */
    private FBDatabaseAdapter mFBDB;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private ListView mBookListView;
    private List<RegisteredBooks> mBookList;
    private BookListAdapter mBookListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbbook_list);

        // エラーハンドラ設定
        Context context = getApplicationContext();
        Thread.setDefaultUncaughtExceptionHandler(new FBUncaughtExceptionHandler(context));

        // データベースアダプタ
        mFBDB = new FBDatabaseAdapter(context);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        findViews();
        setEventListeners();

        fetchBooks();

        mBookListAdapter = new BookListAdapter(context, 0, mBookList);
        mBookListView.setAdapter(mBookListAdapter);
    }

    private void fetchBooks() {
        mBookList = mFBDB.findAllRegisteredBooks();
//        for (RegisteredBooks book : books) {
//            Log.v(LOG_TAG, book.toString());
//        }
    }

    void findViews() {
        mBookListView = (ListView) findViewById(R.id.book_list);
    }

    void setEventListeners() {
        mBookListView.setOnItemClickListener(this);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
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
        ActionBar actionBar = getActionBar();
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
            getMenuInflater().inflate(R.menu.fbbook_list, menu);
            //           restoreActionBar();
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
        Intent intent;
        if (id == R.id.action_transition_registration) {
            intent = new Intent(getApplicationContext(), BookRegistrationActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_transition_web_search) {
            intent = new Intent(getApplicationContext(), WebSearchActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.v(LOG_TAG, "Click #" + id);
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
            View rootView = inflater.inflate(R.layout.fragment_fbbook_list, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((BookListActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }


    }

    private class BookListAdapter extends ArrayAdapter {
        private LayoutInflater inflater;

        public BookListAdapter(Context context, int textViewResourceId, List<RegisteredBooks> objects) {
            super(context, textViewResourceId, objects);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // positionの位置に表示する図書検索結果アイテムを取得
            RegisteredBooks item = (RegisteredBooks) getItem(position);

            if (item != null) {
                // 検索結果1件を表す行のViewをあらたに生成(nullならば)
                if (null == convertView) {
                    convertView = inflater.inflate(R.layout.viewgroup_book_list_item, null);
                }

                TextView titleView = (TextView) convertView.findViewById(R.id.bookListItemTitle);
                titleView.setText(item.getTitle());
                TextView authorView = (TextView) convertView.findViewById(R.id.bookListItemAuthor);
                authorView.setText(item.getAuthor());
                TextView publisherView = (TextView) convertView.findViewById(R.id.bookListItemPublisher);
                publisherView.setText(item.getPublisher());
            }

            return convertView;
        }
    }

}
