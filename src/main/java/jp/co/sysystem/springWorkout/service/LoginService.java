package jp.co.sysystem.springWorkout.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sysystem.springWorkout.domain.jooqRepository.LoginUserJooqRepository;
import jp.co.sysystem.springWorkout.domain.repository.LoginUserRepository;
import jp.co.sysystem.springWorkout.domain.table.User;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class LoginService implements UserDetailsService {
  // 規定値はテストログイン「できない」
//  @Value("${login.test.active}")
  private final boolean isAuth = false;

  // 規定値は「test」
//  @Value("${login.test.user}")
  private final String testUser = "test";

  // 規定値は「test」
//  @Value("${login.test.pass}")
  private final String testPass = "$2a$10$wcfw8KsV3YxUfWF/vHPaKeqjIvT8aH1dbXxWD9QJiRWQOo3xaNPU2";

  @Autowired
  protected LoginUserRepository rep;

  @Autowired
  private LoginUserJooqRepository jrep;

  /* (非 Javadoc)
   * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //「WebSecurityConfigurerAdapter」を継承したクラスのログイン処理にて利用されるメソッド
    // 認証処理から自動で呼び出しが行われる

    // ユーザー名（ID)が無い場合はエラー
    if (username == null || username.equals("")) {
      throw new UsernameNotFoundException("Username is empty");
    }

    // 設定ファイルで認証不要になっている場合は何もしない
    if (isAuth) {
      log.warn("認証なし application.properties#login.test.active を確認してください");
      if (testUser.equals(username)) {
        User uit = new User();
        uit.setId(testUser);
        uit.setName("テストユーザー");
        uit.setKana("テストユーザー");
        uit.setId(testUser);
        uit.setPass(testPass);
        return uit;
      }
    }

    User userInfo = jrep.findByIdWithActive(username);
    // ユーザーIDが存在しない場合はエラー
    if (userInfo == null) {
      throw new UsernameNotFoundException("Username is empty");
    }
    return userInfo;
  }

  /**
   * ログイン失敗の情報を DB に記録
   * @param userId
   */
  public void updateLoginFailureCount(String username) {
//    // ロックを取得
//    User userInfo = jrep.findByIdWithActive(username);
//    if (userInfo != null) {
//      // データを取得できている場合は、ログイン失敗回数を更新
//      userInfo.setLoginFailureCount(userInfo.getLoginFailureCount() + 1);
//      log.warn(
//          String.format(
//              "account failure count update(userId=%s,loginid=%s,failureCount=%d)",
//              userInfo.getUserId(),
//              username,
//              userInfo.getLoginFailureCount()));
//      rep.save(userInfo);
//    }
  }

  /**
   * 何回連続でログインに失敗したかの情報を返す
   * @param userId
   * @return
   */
  public int getLoginFailureCount(String username) {
//    int failureCount = 0;
//    // ロック無しでアカウント情報を取得
//    User userInfo = jrep.findByIdWithActive(username);
//    if (userInfo != null) {
//      failureCount = userInfo.getLoginFailureCount();
//    }
//    return failureCount;
    return 0;
  }

  /**
   * アカウントをロックアウトする
   * @param userId
   */
  public void lockoutUser(String username) {
//    // ロックを取得
//    User userInfo = jrep.findByIdWithActive(username);
//
//    if (userInfo != null) {
//      // データを取得できている場合は、ロック状態を更新
//      userInfo.setLockoutFlg(User.LOCKOUT_FLG__NOT_ACTIVE);
//      log.warn(
//          String.format(
//              "account locked(userId=%s,loginid=%s,failureCount=%d)",
//              userInfo.getUserId(),
//              username,
//              userInfo.getLoginFailureCount()));
//      rep.save(userInfo);
//    }
  }

  /**
   * ログイン成功時の処理<br>
   * アカウントロックフラグ、ログイン失敗回数を初期化
   * @param userId
   */
  public void loginSuccess(String username) {
//    // ロックを取得
//    User userInfo = jrep.findByIdWithActive(username);
//
//    if (userInfo != null) {
//      // データを取得できている場合は、アカウントロックフラグ、ログイン失敗回数を初期化
//      userInfo.setLoginFailureCount(0);
//      userInfo.setLockoutFlg(User.LOCKOUT_FLG__ACTIVE);
//      rep.save(userInfo);
//    }
  }

}