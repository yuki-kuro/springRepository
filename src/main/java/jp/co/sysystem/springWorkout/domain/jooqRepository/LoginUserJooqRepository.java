package jp.co.sysystem.springWorkout.domain.jooqRepository;

import static jp.co.sysystem.springWorkout.domain.jooqObject.tables.User.*;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.sysystem.springWorkout.domain.table.User;
import lombok.NonNull;

@Component
public class LoginUserJooqRepository {
  @Autowired
  private DSLContext dsl;

  /**
   * ユーザーマスタから、有効なユーザーデータを取得
   * @param loginId
   * @return
   */
  public User findByIdWithActive(@NonNull String loginId) {
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
  public User findByIdWithActiveAndForUpdate(@NonNull String loginId) {
    User result = dsl.select()
        .from(USER)
        .where(
            USER.ID.eq(loginId))
        .forUpdate()
        .fetchOneInto(User.class);
   return result;
  }
}
