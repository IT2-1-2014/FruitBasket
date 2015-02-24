package jp.co.icomsys.it21.fruitbasket;


import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class RakutenSearchResultHandler extends DefaultHandler {

    private List<BookSearchItem> parseResult;

    private BookSearchItem parsingItem;

    private String parsingElementName;

    private StringBuilder readingCharBuilder;

    @Override
    public void startDocument() {
        this.parseResult = new ArrayList<>();
    }

    @Override
    public void endDocument() {
    }

    @Override
    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes atts) {
        System.out.println("Start Element: " + qName);

        switch (qName) {
            case "Item":
                // Item タグの読み込み開始は、1つの検索結果の開始と見なし、
                // 新たに検索結果格納オブジェクトを生成
                this.parsingItem = new BookSearchItem();
                this.parseResult.add(this.parsingItem);
                break;
            case "title":
            case "titleKana":
            case "author":
            case "authorKana":
            case "publisherName":
            case "publisherNameKana":
            case "isbn":
            case "itemCaption":
            case "salesDate":
            case "itemPrice":
            case "smallImageUrl":
            case "mediumImageUrl":
            case "largeImageUrl":
                // FB 内で利用するタグの読み込み開始時点で、
                // 文字列読み込みの準備
                this.parsingElementName = qName;
                this.readingCharBuilder = new StringBuilder();
                break;
            default:
                this.parsingElementName = "";
        }
    }

    @Override
    public void endElement(String namespaceURI,
                           String localName,
                           String qName) {
        System.out.println("End Element: " + qName);

        if (this.readingCharBuilder != null) {
            String chValue = this.readingCharBuilder.toString();
            System.out.println("Parsed Characters: " + chValue);

            switch (qName) {
                // 読み込み完了したタグの名前によって、保持する文字列をどの属性に設定するか分岐
                case "title":
                    this.parsingItem.setTitle(chValue);
                    break;
                case "titleKana":
                    this.parsingItem.setTitleKana(chValue);
                    break;
                case "author":
                    this.parsingItem.setAuthor(chValue);
                    break;
                case "authorKana":
                    this.parsingItem.setAuthorKana(chValue);
                    break;
                case "publisherName":
                    this.parsingItem.setPublisher(chValue);
                    break;
                case "publisherNameKana":
                    this.parsingItem.setPublisherKana(chValue);
                    break;
                case "isbn":
                    this.parsingItem.setIsbn(chValue);
                    break;
                default:
                    break;
            }
        }

        this.parsingElementName = "";
        this.readingCharBuilder = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        System.out.println("Character at: " + this.parsingElementName);

        // 読み込んだ文字配列から、読み込み中のタグでの文字列を取り出して String 化
        String chValue = new String(ch, start, length);

        switch (this.parsingElementName) {
            case "title":
            case "titleKana":
            case "author":
            case "authorKana":
            case "publisherName":
            case "publisherNameKana":
            case "isbn":
            case "itemCaption":
            case "salesDate":
            case "itemPrice":
            case "smallImageUrl":
            case "mediumImageUrl":
            case "largeImageUrl":
                // 読み込み中の同一タグにつき、このメソッドで読み込んだ文字列を連結
                // （SAXParser では、1つのタグの文字列要素を複数に分割して読み込み得るため）
                this.readingCharBuilder.append(chValue);
                break;
            default:
                break;
        }
    }

    public List<BookSearchItem> getParseResult() {
        return this.parseResult;
    }
}