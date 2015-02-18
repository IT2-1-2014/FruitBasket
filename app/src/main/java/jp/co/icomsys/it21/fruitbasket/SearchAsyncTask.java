package jp.co.icomsys.it21.fruitbasket;

import android.os.AsyncTask;
import android.widget.Toast;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by WI on 2015/02/04.
 */
public class SearchAsyncTask extends AsyncTask<Void, Void, List<BookSearchItem>> {
    // 検索クエリ構成用文字列
    private String conditionSpacer;

    // 楽天BOOKS APIになげるクエリ
    private String searchQuery;

    // 連携するWeb検索画面
    private WebSearchActivity caller;

    public SearchAsyncTask(WebSearchActivity caller) {
        this.caller = caller;
        this.conditionSpacer = this.caller.getString(R.string.webSerch_rakuten_queryBuild_Spacer);
    }

    @Override
    protected void onPreExecute() {
        StringBuilder queryBuilder = new StringBuilder();

        String title = createSearchConditionContent(this.caller.getTitleInputStr());
        String creator = createSearchConditionContent(this.caller.getTitleInputStr());
        String publisher = createSearchConditionContent(this.caller.getTitleInputStr());
        String isbn = createSearchConditionContent(this.caller.getTitleInputStr());

        try {
            if (!"".equals(title)) {
                queryBuilder.append("title=" +
                        URLEncoder.encode(title, "UTF-8") + "&");
            }
            if (!"".equals(creator)) {
                queryBuilder.append("author=" +
                        URLEncoder.encode(creator, "UTF-8") + "&");
            }
            if (!"".equals(publisher)) {
                queryBuilder.append("publisherName=" +
                        URLEncoder.encode(publisher, "UTF-8") + "&");
            }

            queryBuilder.replace(queryBuilder.lastIndexOf("&"), queryBuilder.length(), "");
            searchQuery = queryBuilder.toString();

        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this.caller,
                    "Error: Query Encoding Failed", Toast.LENGTH_LONG);
        }
    }

    /**
     * 検索条件に "AND" や "OR" が含まれたら、条件パラメータの両端にスペースを挟む
     * @param inputStr 各条件に与えられた文字列
     * @return NDL での検索条件の記載法に従った条件内容
     */
    private String createSearchConditionContent(String inputStr) {
        String condStr = new String(inputStr);

        if (containsSearchKeywords(inputStr)) {
            condStr = conditionSpacer + inputStr + conditionSpacer;
        }

        return condStr;
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
    protected List<BookSearchItem> doInBackground(Void... voids) {
        String serviceURLHead = this.caller.getString(R.string.webSerch_rakuten_conn_url_head),
                serviceURLTail = this.caller.getString(R.string.webSerch_rakuten_conn_url_tail);

        List<BookSearchItem> searchResult = null;
        try {
            Object content = (new URL(serviceURLHead + searchQuery + serviceURLTail)).getContent();
            if (content instanceof InputStream) {

                RakutenSearchResultHandler handler = new RakutenSearchResultHandler();

                try {
                    // SAX により通信結果を解析し、検索結果リストを取得
                    SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
                    parser.parse((InputStream)content, handler);
                    searchResult = handler.getParseResult();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 検索結果読み込みの確認出力
        if (searchResult != null) {
            for (BookSearchItem item : searchResult) {
                System.out.println("----------------ReadItem----------------");
                System.out.println("title:" + item.getTitle());
                System.out.println("author:" + item.getAuthor());
                System.out.println("publisher:" + item.getPublisher());
            }
        }

        return searchResult;
    }

    @Override
    protected void onPostExecute(List<BookSearchItem> resultItems) {
        this.caller.setSearchResultList(resultItems);
    }
}
