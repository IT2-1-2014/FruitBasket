package jp.co.icomsys.it21.fruitbasket;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class WebSearchActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {


    // 各条件入力コンポーネント
    private EditText titleInput;

    private EditText authorInput;

    private EditText publisherInput;

    private EditText isbnInput;

    // 検索ボタン
    private Button searchButton;

    // 図書検索結果のlistview
    private ListView resultListView;

    // 図書検索結果リストの表示用アダプタ
    private SearchResultAdapter resultAdapter;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_search);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        this.titleInput = (EditText)findViewById(R.id.inputTitle);
        this.authorInput = (EditText)findViewById(R.id.inputAuthor);
        this.publisherInput = (EditText)findViewById(R.id.inputPublisher);
        this.isbnInput = (EditText)findViewById(R.id.inputIsbn);

        this.resultListView = (ListView) findViewById(R.id.webSearchBookList);
        this.resultAdapter = new SearchResultAdapter(this, 0, new ArrayList<BookSearchItem>());
        this.resultListView.setAdapter(this.resultAdapter);

        this.searchButton = (Button)findViewById(R.id.webSearchButton);
        this.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new SearchAsyncTask(WebSearchActivity.this)).execute();
            }
        });
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
            getMenuInflater().inflate(R.menu.web_search, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

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

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_sort_filter, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((WebSearchActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public void setSearchResultList(List<BookSearchItem> results) {
        this.resultAdapter.clear();
        for (BookSearchItem b : results) {
            this.resultAdapter.add(b);
        }
    }

    public String getTitleInputStr() {
        return this.titleInput.getText().toString();
    }

    public String getAuthorInputStr() {
        return this.authorInput.getText().toString();
    }

    public String getPublisherInputStr() {
        return this.publisherInput.getText().toString();
    }

    public String getIsbnInputStr() {
        return this.isbnInput.getText().toString();
    }


    private class SearchResultAdapter extends ArrayAdapter {
        private LayoutInflater inflater;

        public SearchResultAdapter(Context context, int textViewResourceId, List<BookSearchItem> objects) {
            super(context, textViewResourceId, objects);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // positionの位置に表示する図書検索結果アイテムを取得
            BookSearchItem item = (BookSearchItem)getItem(position);

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
