package jp.co.icomsys.it21.fruitbasket;

import java.util.Date;

/**
 * Created by yuka on 2015/02/16.
 */
public class Records {

    Integer RrecordID;      //登録情報ID
    Integer ISBNNo;         //ISBN番号
    Date Date;              //購入日
    Integer Volume;         //巻数
    Integer Price;          //金額
    Integer State;          //状態
    Integer Progress;      //読破状況
    String Memo;           //メモ
    Integer GroupID;      //グループID
    Integer BookID;       //図書ID
    String BookTitle;    //図書タイトル
    String BookKana;     //図書ふりがな
    String BookImage;    //図書イメージ
    Integer AuthorID;    //著者ID
    String AuthorName;   //著者名
    String AuthorKana;   //著者ふりがな
    Integer PublisherID; //出版社ID
    String PublisherName;//出版社名
    String PublisherKana;//出版社ふりがな

    public int setRrecordID(Integer setRrecordID){
        this.RrecordID = setRrecordID;

        return 0;
    }

    public int setISBNNo(Integer setISBNNo){
        this.ISBNNo = setISBNNo;

        return 0;
    }

    public int setDate(Date setDate){
        this.Date = setDate;

        return 0;
    }

    public int setVolume(Integer setVolume){
        this.Volume = setVolume;

        return 0;
    }

    public int setPrice(Integer setPrice){
        this.Price = setPrice;

        return 0;
    }

    public int setState(Integer setState){
        this.State = setState;

        return 0;
    }

    public int setProgress(Integer setProgress){
        this.Progress = setProgress;

        return 0;
    }

    public int setMemo(String setMemo){
        this.Memo = setMemo;

        return 0;
    }

    public int setGroupID(Integer setGroupID){
        this.GroupID = setGroupID;

        return 0;
    }

    public int setBookID(Integer setBookID){
        this.BookID = setBookID;

        return 0;
    }

    public int setBookTitle(String setBookTitle){
        this.BookTitle = setBookTitle;

        return 0;
    }
    public int setBookKana(String setBookKana){
        this.BookKana = setBookKana;

        return 0;
    }
    public int setBookImage(String setBookImage){
        this.BookImage = setBookImage;

        return 0;
    }
    public int setAuthorID(Integer setAuthorID){
        this.AuthorID = setAuthorID;

        return 0;
    }
    public int setAuthorName(String setAuthorName){
        this.AuthorName = setAuthorName;

        return 0;
    }
    public int setAuthorKana(String setAuthorKana){
        this.AuthorKana = setAuthorKana;

        return 0;
    }
    public int setPublisherID(Integer setPublisherID){
        this.PublisherID = setPublisherID;

        return 0;
    }
    public int setPublisherName(String setPublisherName){
        this.PublisherName = setPublisherName;

        return 0;
    }
    public int setPublisherKana(String setPublisherKana){
        this.PublisherKana = setPublisherKana;

        return 0;
    }



}
