package demo.rakutendemo;

import java.util.List;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URL;
import java.net.URLEncoder;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class RakutenDemoActivity extends ActionBarActivity {
    // 検索条件の入力用 EditText
    private EditText titleInput, creatorInput, publisherInput, isbnInput;

    // 検索実行用ボタン
    private Button searchButton;

    // 検索結果の表示用リスト
    private ListView searchResultListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rakuten_demo);

        titleInput = (EditText)findViewById(R.id.webSerchCond_title);
        creatorInput = (EditText)findViewById(R.id.webSerchCond_author);
        publisherInput = (EditText)findViewById(R.id.webSerchCond_publisher);
        isbnInput = (EditText)findViewById(R.id.webSerchCond_isbn);
        searchButton = (Button)findViewById(R.id.webSerchExec);
        searchResultListView = (ListView)findViewById(R.id.webSerch_resultItemList);

        searchButton.setOnClickListener(new SearchButtonOnClickListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rakuten_demo, menu);
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

    // 検索ボタンを押下された際に、非同期処理で楽天APIと通信するためのリスナ
    private class SearchButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            (new SearchAsyncTask()).execute();
        }
    }

    class SearchAsyncTask extends AsyncTask<Void, Void, List<SearchResultItem>> {

        // 検索クエリ構成用文字列
        private String conditionSpacer = getString(R.string.webSerch_queryBuild_Spacer);

        // 国立国会図書館検索APIになげるクエリ
        private String searchQuery;


        /**
         * onPreExecute は、非同期処理を実行する前に, UIスレッド側から呼び出されるメソッド.
         */
        @Override
        protected void onPreExecute() {
            StringBuilder queryBuilder = new StringBuilder();

            String title = createSearchConditionContent(RakutenDemoActivity.this.titleInput);
            String creator = createSearchConditionContent(RakutenDemoActivity.this.creatorInput);
            String publisher = createSearchConditionContent(RakutenDemoActivity.this.publisherInput);
            String isbn = createSearchConditionContent(RakutenDemoActivity.this.isbnInput);

            try {
                if (!"".equals(title)) {
                    queryBuilder.append("title=" +
//                            URLEncoder.encode(doubleQuote + title + doubleQuote, "UTF-8") + "AND");
                            URLEncoder.encode(title, "UTF-8") + "&");
                }
                if (!"".equals(creator)) {
                    queryBuilder.append("author=" +
//                            URLEncoder.encode(doubleQuote + creator + doubleQuote, "UTF-8") + "AND");
                            URLEncoder.encode(creator, "UTF-8") + "&");
                }
                if (!"".equals(publisher)) {
                    queryBuilder.append("publisherName=" +
//                            URLEncoder.encode(doubleQuote + publisher + doubleQuote, "UTF-8") + "AND");
                            URLEncoder.encode(publisher, "UTF-8") + "&");
                }

                queryBuilder.replace(queryBuilder.lastIndexOf("&"), queryBuilder.length(), "");
                searchQuery = queryBuilder.toString();

            } catch (UnsupportedEncodingException e) {
                Toast.makeText(RakutenDemoActivity.this,
                        "Error: Query Encoding Failed", Toast.LENGTH_LONG);
            }
        }

        /**
         * 検索条件に "AND" や "OR" が含まれたら、条件パラメータの両端にスペースを挟む
         * @param targetInput 条件入力用の EditText
         * @return NDL での検索条件の記載法に従った条件内容
         */
        private String createSearchConditionContent(EditText targetInput) {
            String inputString = targetInput.getText().toString();

            if (containsSearchKeywords(inputString)) {
                inputString = conditionSpacer + inputString + conditionSpacer;
            }

            return inputString;
        }

        private boolean containsSearchKeywords(String target) {
            if (target == null)
            {
                return false;
            }

            return target.contains("AND") || target.contains("OR") ||
                    target.contains("and") || target.contains("or");
        }

        @Override
        // AsyncTask が、非同期処理中にメインプロセスのバックグラウンドで行う処理を記述するメソッド
        // 本来は、このメソッド中に、楽天APIとの通信処理を記述すればいい
        protected List<SearchResultItem> doInBackground(Void... voids) {
            String serviceURLHead = getString(R.string.webSerch_conn_url_head),
                serviceURLTail = getString(R.string.webSerch_conn_url_tail);


            try {
                Object content = (new URL(serviceURLHead + searchQuery + serviceURLTail)).getContent();
                if (content instanceof InputStream) {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader((InputStream)content));
                    String bf = null;

                    while ((bf = reader.readLine()) != null) {
                        System.out.println(bf);
                    }

                }
            } catch (IOException e) {
                Toast.makeText(RakutenDemoActivity.this,
                        "Error: Connecting to the Service Failed", Toast.LENGTH_LONG);
            }

            // TODO 受け取った検索結果から、リストにアイテムを格納
//            try {
//                Object content = (InputStream)((new URL(serviceURL + searchQuery)).getContent());
//                if (content instanceof InputStream) {
//                    NDLSearchResultParser ndlParser = new NDLSearchResultParser();
//
//                    try {
//                        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
//                        parser.parse((InputStream)content, ndlParser);
//                        searchResult = ndlParser.getParseResult();
//                    } catch (ParserConfigurationException e) {
//                    } catch (SAXException e) {
//                    }
//                }
//
//            } catch (IOException e) {
//            }
//
//            return searchResult;
            return null;
       }

        @Override
        protected void onPostExecute(List<SearchResultItem> SearchResultItems) {

        }
    }
}
