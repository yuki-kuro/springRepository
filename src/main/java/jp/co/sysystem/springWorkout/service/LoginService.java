package jp.co.sysystem.springWorkout.service;

import jp.co.sysystem.springWorkout.domain.jooqRepository.LoginUserJooqRepository;
import jp.co.sysystem.springWorkout.domain.repository.LoginUserRepository;
import jp.co.sysystem.springWorkout.domain.table.User;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ログイン処理定義クラス.

 * @version 1.0.0 2020/05/13 新規作成
 */
@Service
@Transactional
@Slf4j
public class LoginService {
  /**
   * SpringDataJDBCによるリポジトリ処理.
   */
  @Autowired
  protected LoginUserRepository rep;

  /**
   * JOOQによるデータ取得処理.
   */
  @Autowired
  private LoginUserJooqRepository jrep;

  /**
   * ログイン処理.
   * <pre>
   * ログイン画面から受け取った「ログインID」および「パスワード」を使用して、
   * DBで管理されたユーザー情報であるかを検証する
   * </pre>

   * @param loginId a
   *<pre>ログインID a</pre>
   * @param password a
   * @return ?
   */
  public Object checkLoginUser(@NonNull String loginId, @NonNull String password) {
    // TODO:ログイン処理を実装する。引数、戻り値も適宜変更可。
    // log.debug    ←　debugレベルのログ出力
    // log.trace    ←　traceレベルのログ出力
    // log.info     ←　infoレベルのログ出力
    // log.warn     ←　warnレベルのログ出力
    // log.error    ←　errorレベルのログ出力

    // ログインIDでユーザー情報を取得
    User u = jrep.findById(loginId);
    if (null != u) {
      // TODO: ユーザーが取得できた場合、パスワードの検証
      if (u.getPass().equals(password)) {
        return u;
      }
    } else {
      // TODO: 存在しないログインIDだった場合
      log.warn(String.format("指定されたIDは存在しませんでした。[id:%s]", loginId));
    }
    return null;
  }
}