package jp.co.icomsys.it21.fruitbasket;

import java.io.Serializable;

/**
 * 検索結果の図書リストのアイテム.
 */
public class BookSearchItem implements Serializable {

    private String title;

    private String titleKana;

    private String author;

    private String authorKana;

    private String publisher;

    private String publisherKana;

    private String isbn;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String t) {
        this.title = t;
    }

    public String getTitleKana() {
        return this.titleKana;
    }

    public void setTitleKana(String tk) {
        this.titleKana = tk;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String a) {
        this.author = a;
    }

    public String getAuthorKana() {
        return this.authorKana;
    }

    public void setAuthorKana(String ak) {
        this.authorKana = ak;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String p) {
        this.publisher = p;
    }

    public String getPublisherKana() {
        return this.publisherKana;
    }

    public void setPublisherKana(String pk) {
        this.publisherKana = pk;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String i) {
        this.isbn = i;
    }
}
