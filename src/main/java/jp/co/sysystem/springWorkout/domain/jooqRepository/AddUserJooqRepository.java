package jp.co.sysystem.springWorkout.domain.jooqRepository;

import static jp.co.sysystem.springWorkout.domain.jooqObject.tables.User.*;
import static jp.co.sysystem.springWorkout.domain.jooqObject.tables.Userdetail.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import jp.co.sysystem.springWorkout.domain.table.User;
import jp.co.sysystem.springWorkout.web.form.ResultTable;
import lombok.NonNull;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * USERテーブル、USERDETAILテーブルに、データを追加するクラス。
 *
 * @see <a href="https://www.jooq.org/">jooq.org</a>
 * @version 1.0.0 2020/06/18 新規作成
 */
@Component
public class AddUserJooqRepository {
  @Autowired
  private DSLContext dsl;

  /**
   * ユーザーテーブルに追加処理。
   *
   * <p>USERテーブルに、データを追加する。
   *
   * @param id String型、NotNull。登録するユーザーID。
   * @param pass String型。登録するパスワード。
   * @param name String型。登録する名前。
   * @param kana String型。登録するカナ。
   * @return int型。インサート成功した件数が返される。
   */
  public int addUser(@NonNull String id,String pass,String name,String kana) {
    int i = dsl.insertInto(USER)
        .values(id, pass, name, kana).execute();
    return i;
  }

  /**
   * ユーザー詳細テーブルに追加処理。
   *
   * <p>USERDETAILテーブルに、データを追加する。
   *
   * @param id String型。登録するユーザーID。
   * @param birth Date型。登録する生年月日。
   * @param club String型。登録する委員会。
   * @return int型。インサート成功した件数が返される。
   * @throws ParseException ParseException
   */
  public int addUserDetail(String id,Date birth,String club) throws ParseException {

    List<ResultTable> result = dsl.select()
        .from(USERDETAIL)
        .orderBy(USERDETAIL.NO.desc())
        .fetchInto(ResultTable.class);
    ResultTable detail = result.get(0);

    int j = detail.getNo() + 1;

    // sqlの実行件数を取得
    int i = dsl.insertInto(USERDETAIL)
        .values(String.valueOf(j),id,
            new Timestamp(birth.getTime()),
            club).execute();

    return i;
  }


  /**
   * ユーザーデータ取得処理。
   *
   * <p>ユーザーマスタから、有効なユーザーデータを取得。
   *
   * @param id String型、NotNull。検索するユーザーID。
   * @return User型。USERテーブルから検索された有効なユーザーデータ。
   *     検索結果が0件の場合Nullが返される。
   */
  public User findById(@NonNull String id) {

    User result = dsl.select()
        .from(USER)
        .where(
            USER.ID.eq(id))
        .fetchOneInto(User.class);

    return result;
  }
}
