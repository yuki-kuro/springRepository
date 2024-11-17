package jp.co.sysystem.springWorkout.domain.jooqRepository;

import static jp.co.sysystem.springWorkout.domain.jooqObject.tables.User.*;
import static jp.co.sysystem.springWorkout.domain.jooqObject.tables.Userdetail.*;

import java.sql.Timestamp;
import java.util.Date;
import jp.co.sysystem.springWorkout.web.form.ResultTable;
import jp.co.sysystem.springWorkout.web.form.UpdateUserForm;
import lombok.NonNull;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.MappingException;
import org.jooq.exception.TooManyRowsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *idを指定してユーザーマスタとユーザーマスタ詳細のデータを取得、ユーザーマスタのデータを更新するクラス。
 *
 * @see <a href="https://www.jooq.org/">jooq.org</a>
 * @version 1.0.0 2020/06/10 新規作成
 */
@Component
public class UpdateUserJooqRepository {
  @Autowired
  private DSLContext dsl;

  /**
   * idを指定してユーザーマスタとユーザーマスタ詳細のデータを取得。
   *
   * <p>ユーザーマスタとユーザーマスタ詳細を外部結合。
   *
   * @param id String型、NotNull。更新対象のユーザーID。
   * @return ResultTable USER、USERDETAILテーブルから検索された更新対象のレコードを1件返す。
   * @throws DataAccessException DataAccessException
   * @throws MappingException MappingException
   * @throws TooManyRowsException TooManyRowsException
   */
  public UpdateUserForm selectUserAndUserdetail(@NonNull String id)
      throws DataAccessException, MappingException, TooManyRowsException {

    UpdateUserForm form = dsl.select()
        .from(USER)
        .leftOuterJoin(USERDETAIL).on(USER.ID.eq(USERDETAIL.ID))
        .where(USER.ID.eq(id))
        .fetchOneInto(UpdateUserForm.class);
    return form;

  }

  /**
   * ユーザーマスタ、ユーザーマスタ詳細から、更新する前にロックをする。
   *
   * @param loginId String型、NotNull。更新対象のユーザーID。
   * @return ResultTable USER、USERDETAILテーブルから検索されたレコードを一件返す。
   *     0件の場合Nullが返される。
   */
  public ResultTable findByIdForUpdate(@NonNull String loginId) {
    ResultTable result = dsl.select()
        .from(USER)
        .leftOuterJoin(USERDETAIL).on(USER.ID.eq(USERDETAIL.ID))
        .where(
            USER.ID.eq(loginId))
        .forUpdate()
        .fetchOneInto(ResultTable.class);
    return result;
  }

  /**
   * ユーザーマスタのデータを更新。
   *
   * @param id String型、NotNull。更新するユーザーID。
   * @param name String型、NotNull。更新する名前。
   * @param kana String型、NotNull。更新するカナ。
   */
  public void updateUser(@NonNull String id, @NonNull String name, @NonNull String kana)
      throws DataAccessException {

    // ユーザーマスタ更新処理
    dsl.update(USER)
        .set(USER.NAME, name)
        .set(USER.KANA, kana)
        .where(USER.ID.eq(id))
        .execute();
  }

  /**
   * ユーザーマスタ詳細のデータを更新。
   *
   * @param id String型、NotNull。更新するユーザーID。
   * @param birth Date型、NotNull。更新する生年月日。
   * @param club String型、NotNull。更新する委員会。
   */
  public void updateUserDetail(@NonNull String id, @NonNull Date birth, String club) {
    // ユーザーマスタ詳細更新処理
    dsl.update(USERDETAIL)
        .set(USERDETAIL.BIRTH, new Timestamp(birth.getTime()))
        .set(USERDETAIL.CLUB, club)
        .where(USERDETAIL.ID.eq(id))
        .execute();
  }
}
