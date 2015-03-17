package jp.co.icomsys.it21.fruitbasket;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class WebSearchActivity extends ActionBarActivity {

    // 各条件入力コンポーネント
    private EditText titleInput;

    private EditText authorInput;

    private EditText publisherInput;

    private EditText isbnInput;

    // 検索ボタン
    private Button searchButton;

    // 図書検索結果のlist
    private List<BookSearchItem> resultList;

    // 図書検索結果のlistview
    private ListView resultListView;

    // 図書検索結果リストの表示用アダプタ
    private SearchResultAdapter resultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_search);

        this.titleInput = (EditText) findViewById(R.id.inputTitle);
        this.authorInput = (EditText) findViewById(R.id.inputAuthor);
        this.publisherInput = (EditText) findViewById(R.id.inputPublisher);
        this.isbnInput = (EditText) findViewById(R.id.inputIsbn);

        this.resultList = new ArrayList<BookSearchItem>();
        this.resultListView = (ListView) findViewById(R.id.webSearchBookList);
        this.resultAdapter = new SearchResultAdapter(this, 0, this.resultList);
        this.resultListView.setAdapter(this.resultAdapter);

        this.searchButton = (Button) findViewById(R.id.webSearchButton);
        this.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new SearchAsyncTask(WebSearchActivity.this)).execute();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web_search, menu);
        return true;
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

    public void setSearchResultList(List<BookSearchItem> results) {
        this.resultList = results;
        for (BookSearchItem i : this.resultList) {
            this.resultAdapter.add(i);
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
            BookSearchItem item = (BookSearchItem) getItem(position);

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
