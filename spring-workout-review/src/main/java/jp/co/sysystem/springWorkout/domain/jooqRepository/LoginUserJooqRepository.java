package jp.co.sysystem.springWorkout.domain.jooqRepository;

import static jp.co.sysystem.springWorkout.domain.jooqObject.tables.User.*;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.sysystem.springWorkout.domain.table.User;
import lombok.NonNull;

/**
 * JOOQを利用したクエリを定義するクラス<br>
 * O/Rマッパーを利用したタイプセーフなクエリ実装を目指す。
 * @see <a href="https://www.jooq.org/">jooq.org</a>
 * @version 1.0.0 2020/05/13 新規作成
 */
@Component
public class LoginUserJooqRepository {
  @Autowired
  private DSLContext dsl;

  /**
   * ユーザーマスタから、有効なユーザーデータを取得
   * @param loginId
   * @return
   */
  public User findById(@NonNull String loginId) {
    User result = dsl.select()
        .from(USER)
        .where(
            USER.ID.eq(loginId))
        .fetchOneInto(User.class);
   return result;
  }

  /**
   * ユーザーマスタから、有効なユーザーデータを更新用にロックを取得
   * @param loginId
   * @return
   */
  public User findByIdForUpdate(@NonNull String loginId) {
    User result = dsl.select()
        .from(USER)
        .where(
            USER.ID.eq(loginId))
        .forUpdate()
        .fetchOneInto(User.class);
   return result;
  }
}
