package demo.rakutendemo;

// 2014/01/04 WI
// 検索結果の各図書の情報を保持するクラス。
// リスト表示で使う情報は持つが、 DO とは別の扱いとする認識。
// 本番の FB でも、表示に使う情報は一通りもたせて、サービスによる差異のないクラスにする？
public class SearchResultItem {

    // 以下の各属性は、デモなので package private にしています。

    String author;

    String authorKana;

    String publisherName;

    String publisherNameKana;

    String title;

    String titleKana;

    String isbn;

    String hatsubaibi;

    String description;

    int price;

    String imageURLS;

    String imageURLM;

    String imageURLL;
}
