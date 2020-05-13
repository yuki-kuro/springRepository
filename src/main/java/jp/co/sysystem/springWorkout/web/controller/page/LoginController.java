package jp.co.sysystem.springWorkout.web.controller.page;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sysystem.springWorkout.util.MessageUtil;
import jp.co.sysystem.springWorkout.web.form.LoginForm;

@Controller
@EnableAutoConfiguration
public class LoginController {

  @Autowired
  HttpSession session;

  @Autowired
  public MessageUtil msgutil;

  /// URL定義
  public static final String LOGIN_FORM_URL = "/";
  public static final String LOGIN_PROCESS_URL = "/login";
  public static final String LOGOUT_URL = "/logout";

  ///ページ定義
  public static final String LOGIN_PAGE = "page/login";

  /**
   * ログイン画面表示
   * ログイン前でも表示可能
   */
  @RequestMapping(value = { LOGIN_FORM_URL })
  public String showLoginPage(HttpServletRequest request, Model model) {

    String msg = null;
    // SpringSecurityのエラー情報を取得
    Exception securityException = (Exception) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
    // リクエストパラメータの一覧を取得
    Map<String, String[]> parameters = request.getParameterMap();

    if (securityException != null) {
      msg = securityException.getMessage();
    } else if (parameters.containsKey("timeout")) {
      msg = msgutil.getMessage("system.session.timeout");
    }

    // メッセージを格納
    model.addAttribute("msg", msg);

    // ログインフォームを格納
    model.addAttribute("LoginForm", new LoginForm());

    return LOGIN_PAGE;
  }

  /**
   * ログアウト
   * @param model
   * @return
   */
  @RequestMapping(value = LOGOUT_URL, method = RequestMethod.GET)
  public String logout(Model model) {
    model.addAttribute("LoginForm", new LoginForm());
    return LOGIN_PAGE;
  }
}
