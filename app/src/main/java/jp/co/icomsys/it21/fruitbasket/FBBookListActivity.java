package jp.co.icomsys.it21.fruitbasket;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.List;


public class FBBookListActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    BookManagementService BookManagementService  = new BookManagementService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbbook_list);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
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
            View rootView = inflater.inflate(R.layout.fragment_fbbook_list, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((FBBookListActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public void button_onClick(View view){
        //図書タイトル
        EditText text_1 = (EditText)this.findViewById(R.id.editText);
        String text1 = text_1.getText().toString();
        BookManagementService.setBookTitle_m(text1);

        //図書タイトルふりがな
        EditText text_2 = (EditText)this.findViewById(R.id.editText2);
        String text2 = text_2.getText().toString();
        BookManagementService.setBookKana_m(text2);

        //著者名
        EditText text_3 = (EditText)this.findViewById(R.id.editText3);
        String text3 = text_3.getText().toString();
        BookManagementService.setAuthorName_m(text3);

        //著者名ふりがな
        EditText text_4 = (EditText)this.findViewById(R.id.editText4);
        String text4 = text_4.getText().toString();
        BookManagementService.setAuthorKana_m(text4);

        //出版社名
        EditText text_5 = (EditText)this.findViewById(R.id.editText5);
        String text5 = text_5.getText().toString();
        BookManagementService.setPublisherName_m(text5);

        //出版社名ふりがな
        EditText text_6 = (EditText)this.findViewById(R.id.editText6);
        String text6 = text_6.getText().toString();
        BookManagementService.setPublisherKana_m(text6);

        //ISBN番号
        EditText text_7 = (EditText)this.findViewById(R.id.editText7);
        String text7 =text_7.getText().toString();
        Integer text7_i = Integer.parseInt(text7);
        BookManagementService.setISBNNo_m(text7_i);

        //発売日
        EditText text_8 = (EditText)this.findViewById(R.id.editText8);
        String text8 = text_8.getText().toString();
        //Integer text8_i = Integer.parseInt(text8);
        //BookManagementService.setDate_m(text8_i);

        //巻数
        EditText text_9 = (EditText)this.findViewById(R.id.editText9);
        String text9 =text_9.getText().toString();
        Integer text9_i = Integer.parseInt(text9);
        BookManagementService.setVolume_m(text9_i);

        //金額
        EditText text_10 = (EditText)this.findViewById(R.id.editText10);
        String text10 = text_10.getText().toString();
        Integer text10_i = Integer.parseInt(text10);
        BookManagementService.setPrice_m(text10_i);
    }
}
