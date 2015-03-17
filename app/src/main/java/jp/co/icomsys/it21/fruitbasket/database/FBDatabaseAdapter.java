package jp.co.icomsys.it21.fruitbasket.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import jp.co.icomsys.it21.fruitbasket.dao.Authors;
import jp.co.icomsys.it21.fruitbasket.dao.AuthorsDao;
import jp.co.icomsys.it21.fruitbasket.dao.BookTitles;
import jp.co.icomsys.it21.fruitbasket.dao.BookTitlesDao;
import jp.co.icomsys.it21.fruitbasket.dao.DaoMaster;
import jp.co.icomsys.it21.fruitbasket.dao.DaoSession;
import jp.co.icomsys.it21.fruitbasket.dao.Publishers;
import jp.co.icomsys.it21.fruitbasket.dao.PublishersDao;
import jp.co.icomsys.it21.fruitbasket.dao.RegisteredBooks;
import jp.co.icomsys.it21.fruitbasket.dao.RegisteredBooksDao;

/**
 * FruitBasketデータベースアダプタ*
 * アプリとデータベースアクセス用ライブラリをつなぐ機能 *
 * アプリケーションロジックからはこのクラスを介してデータベース更新を行う。*
 * Created by masaharu on 15/03/16.
 */
public class FBDatabaseAdapter {
    // データベース名
    static final String DATABASE_NAME = "fb-db";
    // Log Tag
    private static final String LOG_TAG = FBDatabaseAdapter.class.getSimpleName();

    // Dao関連
    private DaoMaster.OpenHelper mHelper;
    private DaoSession mSession;

    /**
     * コンストラクタ*
     *
     * @param context コンテキストオブエジェクト
     */
    public FBDatabaseAdapter(Context context) {
        mHelper = new FBOpenHelper(context, DATABASE_NAME, null);
        SQLiteDatabase mDB;
        mDB = mHelper.getWritableDatabase();
        DaoMaster mDaoMaster;
        mDaoMaster = new DaoMaster(mDB);
        mSession = mDaoMaster.newSession();
    }


    /**
     * 登録図書を全件、一括で取得する*
     *
     * @return 登録済み図書のリスト
     */
    public List<RegisteredBooks> findAllRegisteredBooks() {
        RegisteredBooksDao registeredBooksDao = mSession.getRegisteredBooksDao();
        return registeredBooksDao.queryBuilder().orderAsc(RegisteredBooksDao.Properties.Id).list();
    }

    /**
     * 図書情報の保存を行う。*
     * 既に登録されている情報の場合、新規に登録を行わない。*
     *
     * @param registeredBook 登録する図書情報
     * @return 登録した図書情報
     */
    public RegisteredBooks saveOneBook(RegisteredBooks registeredBook) {
        BookTitles title = new BookTitles();
        title.setTitle(registeredBook.getTitle());
        title.setTitleKana(registeredBook.getTitleKana());

        Authors author = new Authors();
        author.setAuthor(registeredBook.getAuthor());
        author.setAuthorKana(registeredBook.getAuthorKana());

        Publishers publisher = new Publishers();
        publisher.setPublisher(registeredBook.getPublisher());
        publisher.setPublisherKana(registeredBook.getPublisherKana());

        BookTitlesDao titleDao = mSession.getBookTitlesDao();
        AuthorsDao authorDao = mSession.getAuthorsDao();
        PublishersDao publisherDao = mSession.getPublishersDao();
        RegisteredBooksDao registeredBookDao = mSession.getRegisteredBooksDao();

        // タイトル情報の登録
        title = registerBookTitle(titleDao, title);

        // 著者情報の登録
        author = registerBookAuthor(authorDao, author);

        // 出版社情報の登録
        publisher = registerPublisher(publisherDao, publisher);

//        // 図書登録情報の登録
//        registeredBook.setBookTitleID(title.getId());
//        registeredBook.setBookTitles(title);
//        registeredBook.setAuthorID(author.getId());
//        registeredBook.setAuthors(author);
//        registeredBook.setPublisherID(publisher.getId());
//        registeredBook.setPublishers(publisher);

        return registerRegisteredBook(registeredBookDao, registeredBook);
    }

    /**
     * 図書登録情報の登録・更新*
     * 前提：パラメータはNULLではないこと*
     *
     * @param registeredBookDao 図書登録情報DAO
     * @param registeredBook    登録・更新する図書登録情報
     * @return 登録・更新した図書登録情報
     */
    private RegisteredBooks registerRegisteredBook(RegisteredBooksDao registeredBookDao, RegisteredBooks registeredBook) {
        if (registeredBook.getId() == null) {
            // new
            return registerNewRegisteredBook(registeredBookDao, registeredBook);
        } else {
            // update
            return updateRegisteredBookById(registeredBookDao, registeredBook);
        }
    }


    /**
     * 図書登録情報のPKでの更新*
     * 前提：パラメータはNULLではないこと*
     *
     * @param registeredBookDao 図書登録情報DAO
     * @param registeredBook    更新する図書登録情報
     * @return 更新した図書登録情報
     */
    private RegisteredBooks updateRegisteredBookById(RegisteredBooksDao registeredBookDao, RegisteredBooks registeredBook) {
        long registeredBookId = registeredBook.getId();

        List<RegisteredBooks> result = registeredBookDao.queryBuilder().where(RegisteredBooksDao.Properties.Id.eq(registeredBookId)).list();

        // PKで検索しているので、0or１件のみの返却
        int size = result.size();
        if (size == 1) {
            // 全項目が設定されている前提（差分反映も全項目の設定が必要）
            registeredBookDao.update(registeredBook);
        } else if (size == 0) {
            // 新規登録
            registeredBook.setId((long) 0);
            registeredBook = insertRegisteredBook(registeredBookDao, registeredBook);
        }

        return registeredBook;

    }


    /**
     * 図書登録情報の登録・更新*
     * 前提：パラメータはNULLではないこと*
     *
     * @param registeredBookDao 図書登録情報DAO
     * @param registeredBook    登録・更新する図書登録情報
     * @return 登録・更新した図書登録情報
     */
    private RegisteredBooks registerNewRegisteredBook(RegisteredBooksDao registeredBookDao, RegisteredBooks registeredBook) {
        return insertRegisteredBook(registeredBookDao, registeredBook);
//        List<RegisteredBooks> result =
//                registeredBookDao.queryBuilder()
//                        .where(RegisteredBooksDao.Properties.Id.eq(registeredBook.getId()))
//                        .orderAsc(RegisteredBooksDao.Properties.Id)
//                        .list();
//
//        int size = result.size();
//        if (size == 0) {
//            return insertRegisteredBook(registeredBookDao, registeredBook);
//        } else if (size == 1) {
//            return result.get(0);
//        } else {
//            return result.get(0);
//        }
    }


    /**
     * 図書登録情報の登録*
     * 前提：パラメータはNULLではないこと*
     *
     * @param registeredBookDao 図書登録情報DAO
     * @param registeredBook    登録する図書登録情報
     * @return 登録した図書登録情報
     */
    private RegisteredBooks insertRegisteredBook(RegisteredBooksDao registeredBookDao, RegisteredBooks registeredBook) {
        long registeredBookId = registeredBookDao.insert(registeredBook);
        registeredBook.setId(registeredBookId);
        return registeredBook;
    }

    private Publishers registerPublisher(PublishersDao publisherDao, Publishers publisher) {
        if (publisher.getId() == null) {
            // new
            return registerNewPublisher(publisherDao, publisher);
        } else {
            // update
            return updatePublisherById(publisherDao, publisher);
        }
    }

    /**
     * 出版社情報のPKでの更新登録*
     * 前提：パラメータはNULLではないこと *
     *
     * @param publisherDao 出版社情報DAO
     * @param publisher    更新する出版社情報
     * @return 更新した出版社情報
     */
    private Publishers updatePublisherById(PublishersDao publisherDao, Publishers publisher) {
        long publisherId = publisher.getId();

        List<Publishers> result = publisherDao.queryBuilder().where(PublishersDao.Properties.Id.eq(publisherId)).list();

        // PKで検索しているので、0or１件のみの返却
        int size = result.size();
        if (size == 1) {
            // 全項目が設定されている前提（差分反映も全項目の設定が必要）
            publisherDao.update(publisher);
        } else if (size == 0) {
            // 新規登録
            publisher.setId(null);
            publisher = insertPublisher(publisherDao, publisher);
        }

        return publisher;
    }

    /**
     * 出版社情報の新規登録*
     * 前提：パラメータはNULLではないこと *
     *
     * @param publisherDao 出版社情報DAO
     * @param publisher    登録・更新する出版社情報
     * @return 登録・更新した出版社情報
     */
    private Publishers registerNewPublisher(PublishersDao publisherDao, Publishers publisher) {
        List<Publishers> result =
                publisherDao.queryBuilder()
                        .where(PublishersDao.Properties.Publisher.eq(publisher.getPublisher())
                                , PublishersDao.Properties.PublisherKana.eq(publisher.getPublisherKana()))
                        .orderAsc(PublishersDao.Properties.Id)
                        .list();

        int size = result.size();
        if (size == 0) {
            return insertPublisher(publisherDao, publisher);
        } else if (size == 1) {
            return result.get(0);
        } else {
            return result.get(0);
        }

    }

    /**
     * 出版社情報の新規登録*
     * 前提：パラメータはNULLではないこと *
     *
     * @param publisherDao 出版社情報DAO
     * @param publisher    登録する出版社情報
     * @return 登録した出版社情報
     */
    private Publishers insertPublisher(PublishersDao publisherDao, Publishers publisher) {
        long titleId = publisherDao.insert(publisher);
        publisher.setId(titleId);
        return publisher;
    }

    /**
     * 著者情報登録*
     * PK項目の有無で新規登録か更新かを判定する。*
     * 前提：パラメータはNULLではないこと*
     *
     * @param authorsDao 著者情報DAO
     * @param author     登録・更新する著者情報情報
     * @return 登録・更新したIDを付与したエンティティ
     */
    private Authors registerBookAuthor(AuthorsDao authorsDao, Authors author) {
        if (author.getId() == null) {
            // new
            return registerNewAuthor(authorsDao, author);
        } else {
            // update
            return updateAuthorById(authorsDao, author);
        }

    }

    /**
     * 新規著者情報登録*
     * 前提：パラメータはNULLではないこと*
     *
     * @param authorsDao 著者情報DAO
     * @param author     登録・更新する著者情報情報
     * @return 登録・更新した著者情報情報
     */
    private Authors registerNewAuthor(AuthorsDao authorsDao, Authors author) {
        List<Authors> result =
                authorsDao.queryBuilder()
                        .where(AuthorsDao.Properties.Author.eq(author.getAuthor())
                                , AuthorsDao.Properties.AuthorKana.eq(author.getAuthorKana()))
                        .orderAsc(AuthorsDao.Properties.Id)
                        .list();

        int resultCount = result.size();
        if (resultCount == 0) {
            return insertAuthor(authorsDao, author);
        } else if (resultCount == 1) {
            return result.get(0);
        } else {
            return result.get(0);
        }
    }

    /**
     * 著者情報情報の新規登録*
     *
     * @param authorsDao 著者情報DAO
     * @param author     登録する著者情報情報
     * @return 登録した著者情報情報
     */
    private Authors insertAuthor(AuthorsDao authorsDao, Authors author) {
        long titleId = authorsDao.insert(author);
        author.setId(titleId);
        return author;
    }

    /**
     * 著者情報情報の更新*
     * 前提：パラメータはNULLではないこと*
     *
     * @param authorsDao 著者情報DAO
     * @param author     更新する著者情報情報
     * @return 更新した著者情報情報
     */
    private Authors updateAuthorById(AuthorsDao authorsDao, Authors author) {
        long authorId = author.getId();

        List<Authors> result = authorsDao.queryBuilder().where(AuthorsDao.Properties.Id.eq(authorId)).list();

        // PKで検索しているので、0or１件のみの返却
        int size = result.size();
        if (size == 1) {
            // 全項目が設定されている前提（差分反映も全項目の設定が必要）
            authorsDao.update(author);
        } else if (size == 0) {
            // 新規登録
            author.setId(null);
            author = insertAuthor(authorsDao, author);
        }

        return author;
    }

    /**
     * タイトル登録*
     * PK項目の有無で新規登録か更新かを判定する。*
     * 前提：パラメータはNULLではないこと*
     *
     * @param titlesDao 図書タイトルDAO
     * @param title     登録・更新するタイトル情報
     * @return 登録・更新したIDを付与したエンティティ
     */
    private BookTitles registerBookTitle(BookTitlesDao titlesDao, BookTitles title) {
        if (title.getId() == null) {
            // new
            return registerNewBookTitles(titlesDao, title);
        } else {
            // update
            return updateBookTitlesById(titlesDao, title);
        }
    }

    /**
     * 新規図書タイトルの登録*
     * 前提：パラメータはNULLではないこと*
     * 仕様：
     * ・タイトル、読み仮名の完全一致で検索
     * ・一致が１件の場合、新規登録をせず、既存のエンティティを返却。*
     * ・一致が０件の場合、新規に登録する。*
     * ・一致が２件以上の場合、IDの若いエンティティを返却*
     *
     * @param titlesDao 図書タイトルDAO
     * @param title     登録・更新するタイトル情報
     * @return 登録・更新したIDを付与したエンティティ
     */
    private BookTitles registerNewBookTitles(BookTitlesDao titlesDao, BookTitles title) {
        List<BookTitles> result =
                titlesDao.queryBuilder()
                        .where(BookTitlesDao.Properties.Title.eq(title.getTitle())
                                , BookTitlesDao.Properties.TitleKana.eq(title.getTitleKana()))
                        .orderAsc(BookTitlesDao.Properties.Id)
                        .list();

        int resultCount = result.size();
        if (resultCount == 0) {
            return insertBookTitle(titlesDao, title);
        } else if (resultCount == 1) {
            return result.get(0);
        } else {
            return result.get(0);
        }
    }

    /**
     * 図書タイトルの登録*
     * 前提：パラメータはNULLではないこと*
     *
     * @param titlesDao 図書タイトルDAO
     * @param title     登録するタイトル情報
     * @return 登録したIDを付与したエンティティ
     */
    private BookTitles insertBookTitle(BookTitlesDao titlesDao, BookTitles title) {
        long titleId = titlesDao.insert(title);
        title.setId(titleId);
        return title;
    }

    /**
     * 図書タイトルのPrimaryKeyでの更新*
     * 前提：パラメータはNULLではないこと*
     *
     * @param titlesDao 図書タイトルDAO
     * @param title     更新するタイトル情報
     * @return 登録したIDを付与したエンティティ
     */
    private BookTitles updateBookTitlesById(BookTitlesDao titlesDao, BookTitles title) {
        long titleId = title.getId();

        List<BookTitles> result = titlesDao.queryBuilder().where(BookTitlesDao.Properties.Id.eq(titleId)).list();

        // PKで検索しているので、0or１件のみの返却
        int size = result.size();
        if (size == 1) {
            // 全項目が設定されている前提（差分反映も全項目の設定が必要）
            titlesDao.update(title);
        } else if (size == 0) {
            // 新規
            title.setId(null);
            title = insertBookTitle(titlesDao, title);
        }

        return title;
    }


}
