package jp.co.sysystem.springWorkout.domain.jooqRepository;

import static jp.co.sysystem.springWorkout.domain.jooqObject.tables.User.*;
import static jp.co.sysystem.springWorkout.domain.jooqObject.tables.Userdetail.*;

import jp.co.sysystem.springWorkout.web.form.ResultTable;
import lombok.NonNull;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ユーザーIDによって、USERテーブル、USERDETAILテーブルから、データを削除するクラス。
 *
 * @see <a href="https://www.jooq.org/">jooq.org</a>
 * @version 1.0.0 2020/06/24 新規作成
 */

@Component

public class DeleteUserJooqRepository {
  @Autowired
  private DSLContext dsl;

  /**
   * 削除対象検索処理。
   *
   * <p>削除対象のレコードをUSER、USERDETAILテーブルからユーザーIDで検索。
   * 結合して返す。
   *
   * @param id String型、NotNull。削除対象のユーザーID。
   * @return ResultTable型。検索されたUSER、USERDETAILテーブルのレコードを1件返す。
   *     検索結果が0件の場合Nullが返される。
   */
  public ResultTable findById(@NonNull String id) {
    ResultTable result = dsl.select()
        .from(USER)
        .leftOuterJoin(USERDETAIL).on(USER.ID.eq(USERDETAIL.ID))
        .where(
            USER.ID.eq(id))
        .fetchOneInto(ResultTable.class);
    return result;
  }

  /**
   * 削除用に有効なレコードを検索してロックを行う。
   *
   * @param id String型、NotNull。削除対象のユーザーID。
   * @return ResultTable型。USERテーブルから検索された削除対象のレコード。
   *     検索結果が0件の場合Nullが返される。
   */
  public ResultTable findByIdForUpdate(@NonNull String id) {
    ResultTable result = dsl.select()
        .from(USER)
        .leftOuterJoin(USERDETAIL).on(USER.ID.eq(USERDETAIL.ID))
        .where(
            USER.ID.eq(id))
        .forUpdate()
        .fetchOneInto(ResultTable.class);
    return result;
  }

  /**
   * USERテーブルから削除対象のIDに一致するレコードを削除する。
   *
   * @param id String型、NotNull。削除対象のユーザーID。
   * @return int型。削除成功した件数。
   */
  public int userDelete(@NonNull String id) {

    int result = dsl.delete(USER)
        .where(
            USER.ID.eq(id))
        .execute();
    return result;

  }

  /**
   * USERDETAILテーブルから削除対象のIDに一致するレコードを削除する。
   *
   * @param id String型、NotNull。削除対象のユーザーID。
   * @return int型。削除成功した件数。
   */
  public int userDetailDelete(@NonNull String id) {

    int result = dsl.delete(USERDETAIL)
        .where(
            USERDETAIL.ID.eq(id))
        .execute();
    return result;
  }

}
