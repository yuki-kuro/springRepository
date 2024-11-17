package jp.co.sysystem.springWorkout.domain.jooqRepository;

import static jp.co.sysystem.springWorkout.domain.jooqObject.tables.User.*;
import static jp.co.sysystem.springWorkout.domain.jooqObject.tables.Userdetail.*;
import static org.jooq.impl.DSL.*;

import java.util.ArrayList;
import java.util.List;
import jp.co.sysystem.springWorkout.domain.table.User;
import jp.co.sysystem.springWorkout.web.form.UserForm;
import lombok.NonNull;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JOOQを利用したクエリを定義するクラス<br>
 * O/Rマッパーを利用したタイプセーフなクエリ実装を目指す.

 * @see <a href="https://www.jooq.org/">jooq.org</a>
 * @version 1.0.0 2020/05/13 新規作成
 */
@Component
public class SearchUserJooqRepository {
  @Autowired
  private DSLContext dsl;
  /**
   * .
   */
  
  public List<UserForm> findById(String searchId, String searchName, String searchKana) {
    Condition condition = noCondition();
    if (!searchId.isEmpty()) {
      condition = condition.and(USER.ID.like(searchId + "%"));
    }
    if (!searchName.isEmpty()) {
      condition = condition.and(USER.NAME.like(searchName + "%"));
    }
    if (!searchKana.isEmpty()) {
      condition = condition.and(USER.KANA.like(searchKana + "%"));
    }

    List<UserForm> result = new ArrayList<UserForm>();
    result = dsl.select(USER.ID, USER.NAME, USER.KANA, USERDETAIL.BIRTH, USERDETAIL.CLUB)
        .from(USER.join(USERDETAIL).on(USERDETAIL.ID.eq(USER.ID)))
        .where(condition)
        .fetchInto(UserForm.class);

    for (UserForm s : result) {
      String[] str = s.getBirth().split(" ");
      str[0] = str[0].replace('-', '/');
      s.setBirth(str[0]);
    }
    return result;
  }

  /**
   * ユーザーマスタから、有効なユーザーデータを更新用にロックを取得.

   * @param loginId id
   * @return User
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
