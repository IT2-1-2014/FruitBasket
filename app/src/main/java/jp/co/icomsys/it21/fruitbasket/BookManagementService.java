package jp.co.icomsys.it21.fruitbasket;

import java.util.Date;
import java.util.List;

/**
 * Created by yuka on 2015/02/02.
 */
public class BookManagementService {

    private Integer RrecordID_m;      //登録情報ID
    private Integer ISBNNo_m;         //ISBN番号
    private Date Date_m;              //購入日
    private Integer Volume_m;         //巻数
    private Integer Price_m;          //金額
    private Integer State_m;          //状態
    private Integer Progress_m;      //読破状況
    private String Memo_m;           //メモ
    private Integer GroupID_m;      //グループID
    private Integer BookID_m;       //図書ID
    private String BookTitle_m;    //図書タイトル
    private String BookKana_m;     //図書ふりがな
    private String BookImage_m;    //図書イメージ
    private Integer AuthorID_m;    //著者ID
    private String AuthorName_m;   //著者名
    private String AuthorKana_m;   //著者ふりがな
    private Integer PublisherID_m; //出版社ID
    private String PublisherName_m;//出版社名
    private String PublisherKana_m;//出版社ふりがな
    private Integer SortID;       //
    private List Records;         //

    //登録情報ID
    public int setRrecordID_m(Integer setRrecordID_a){
        this.RrecordID_m = setRrecordID_a;

        return 0;
    }

    //ISBN番号
    public int setISBNNo_m(Integer setISBNNo_a){
        this.ISBNNo_m = setISBNNo_a;

        return 0;
    }

    //発売日
    public int setDate_m(Date setDate_a){
        this.Date_m = setDate_a;

        return 0;
    }

    //巻数
    public int setVolume_m(Integer setVolume_a){
        this.Volume_m = setVolume_a;

        return 0;
    }

    //金額
    public int setPrice_m(Integer setPrice_a){
        this.Price_m = setPrice_a;

        return 0;
    }

    //状態
    public int setState_m(Integer setState_a){
        this.State_m = setState_a;

        return 0;
    }

    //読破状況
    public int setProgress_m(Integer setProgress_a){
        this.Progress_m = setProgress_a;

        return 0;
    }

    //メモ
    public int setMemo_m(String setMemo_a){
        this.Memo_m = setMemo_a;

        return 0;
    }

    //グループID
    public int setGroupID_m(Integer setGroupID_a){
        this.GroupID_m = setGroupID_a;

        return 0;
    }

    // 図書ID
    public int setBookID_m(Integer setBookID_a){
        this.BookID_m = setBookID_a;

        return 0;
    }

    //図書タイトル
    public int setBookTitle_m(String setBookTitle_a){
        this.BookTitle_m = setBookTitle_a;

        return 0;
    }

    //図書タイトルふり仮名
    public int setBookKana_m(String setBookKana_a){
        this.BookKana_m = setBookKana_a;

        return 0;
    }

    //図書イメージ
    public int setBookImage_m(String setBookImage_a){
        this.BookImage_m = setBookImage_a;

        return 0;
    }

    //著者ID
    public int setAuthorID_m(Integer setAuthorID_a){
        this.AuthorID_m = setAuthorID_a;

        return 0;
    }

    //著者名
    public int setAuthorName_m(String setAuthorName_a){
        this.AuthorName_m = setAuthorName_a;

        return 0;
    }

    //著者名ふりがな
    public int setAuthorKana_m(String setAuthorKana_a){
        this.AuthorKana_m = setAuthorKana_a;

        return 0;
    }

    //出版社ID
    public int setPublisherID_m(Integer setPublisherID_a){
        this.PublisherID_m = setPublisherID_a;

        return 0;
    }

    //出版社名
    public int setPublisherName_m(String setPublisherName_a){
        this.PublisherName_m = setPublisherName_a;

        return 0;
    }

    //出版社名ふりがな
    public int setPublisherKana_m(String setPublisherKana_a){
        this.PublisherKana_m = setPublisherKana_a;

        return 0;
    }


    int UpRecord(){
        Records Info = new Records();

        Info.setISBNNo(ISBNNo_m);
        Info.setDate(Date_m);
        Info.setVolume(Volume_m);
        Info.setPrice(Price_m);
        Info.setState(State_m);
        Info.setProgress(Progress_m);
        Info.setMemo(Memo_m);
        Info.setBookTitle(BookTitle_m);
        Info.setBookKana(BookKana_m);
        Info.setBookImage(BookImage_m);
        Info.setAuthorName(AuthorName_m);
        Info.setAuthorKana(AuthorKana_m);
        Info.setPublisherName(PublisherName_m);
        Info.setPublisherKana(PublisherKana_m);

        /*Info.Up();



         */

        return 0;
    }

    int DelRecord(){

        return 0;
    }

    int Sort(){

        return 0;
    }

    int Filtering(){

        return 0;
    }

}

