package jp.co.sysystem.springWorkout.web.controller.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jp.co.sysystem.springWorkout.service.LoginService;
import jp.co.sysystem.springWorkout.util.MessageUtil;
import jp.co.sysystem.springWorkout.web.form.LoginForm;
import jp.co.sysystem.springWorkout.web.form.SearchForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ログイン画面コントローラー.

 * @version 1.0.0 2020/05/13 新規作成
 */
@Controller
@EnableAutoConfiguration
@Slf4j
public class LoginController {
// クラスのフィールドやメソッドに、依存関係にある他のオブジェクトを自動的に注入する.
  @Autowired
  HttpSession session;

  @Autowired
  public MessageUtil msgutil;

  @Autowired
  public LoginService login;

  /// URL定義
  public static final String LOGIN_FORM_URL = "/";
  public static final String LOGIN_PROCESS_URL = "/login";
  public static final String LOGOUT_URL = "/logout";

  ///ページ定義
  public static final String LOGIN_PAGE = "page/login";
  public static final String SEARCH_PAGE = "page/search";

  /**
   * ログイン画面表示.
   * ログイン前でも表示可能
   */
  // HTTPリクエストを処理するメソッドやクラスとURLをマッピングする
  @RequestMapping(value = LOGIN_FORM_URL, method = RequestMethod.GET)
  public String showLoginPage(HttpServletRequest request, Model model) {
    // ログインフォームを格納.    Modelクラスは、コントローラからビューへデータを渡すための容器です。
    model.addAttribute("loginForm", new LoginForm());

    return LOGIN_PAGE;
  }

  /**
   * ログイン処理.<br>
   * ログイン成功である場合、メニュー画面へリダイレクトする。

   * @param form a
   * @param bindingResult a
   * @param model a
   * @return a
   */
  @RequestMapping(value = LOGIN_PROCESS_URL, method = RequestMethod.POST)
  public String processLogin(
      @Validated @ModelAttribute LoginForm form,
      BindingResult bindingResult,
      Model model) {
    // BeanValidationの結果確認
    if (bindingResult.hasErrors()) {
      // エラーメッセージをリソースファイルから取得
      String msg = msgutil.getMessage("login.failure");
      log.debug(msg);
      // エラーメッセージを画面に表示する
      model.addAttribute("msg", msg);

      // ログインに失敗したら、もう一度ログイン画面
      // ログインフォームを格納
      model.addAttribute("loginForm", form);
      return LOGIN_PAGE;
    }

    // ログインユーザー情報の正当性判定
    if (null == login.checkLoginUser(form.getId(), form.getPassword())) {
      // エラーメッセージをリソースファイルから取得
      String msg = msgutil.getMessage("login.failure");
      log.debug(msg);
      // エラーメッセージを画面に表示する
      model.addAttribute("msg", msg);

      // ログインフォームを格納
      model.addAttribute("loginForm", form);
      return LOGIN_PAGE;                                            
    }
    //セッションに保存
    session.setAttribute("loginForm", form);
    // ログインフォームを格納
    model.addAttribute("loginForm", new LoginForm());
    model.addAttribute("searchForm", new SearchForm());
    return SEARCH_PAGE;
  }

  /**
   * ログアウト.

   * @param model a
   * @return a
   */
  @RequestMapping(value = LOGOUT_URL, method = RequestMethod.GET)
  public String logout(Model model) {
    // 既存セッションを削除
    session.invalidate();
    // 遷移先のログイン画面で使用する空のForm
    model.addAttribute("loginForm", new LoginForm());
    return LOGIN_PAGE;
  }
}
