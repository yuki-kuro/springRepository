package jp.co.sysystem.springWorkout.web.controller.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sysystem.springWorkout.util.MessageUtil;
import jp.co.sysystem.springWorkout.web.form.LoginForm;
import lombok.extern.slf4j.Slf4j;

@Controller
@EnableAutoConfiguration
@Slf4j
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
  @RequestMapping(value = LOGIN_FORM_URL, method = RequestMethod.GET)
  public String showLoginPage(HttpServletRequest request, Model model) {
    // ログインフォームを格納
    model.addAttribute("LoginForm", new LoginForm());

    return LOGIN_PAGE;
  }

  @RequestMapping(value = LOGIN_PROCESS_URL, method = RequestMethod.POST)
  public String processLogin(
      @Validated @ModelAttribute LoginForm form,
      BindingResult bindingResult,
      Model model) {

    if (bindingResult.hasErrors()) {
      // TODO: ログインフォームからの入力値確認結果にエラーがある場合
      // TODO: エラーメッセージを画面に表示する
      // TODO: ログインに失敗したら、もう一度ログイン画面
      String msg = msgutil.getMessage("login.failure");
      log.info(msg);
      model.addAttribute("msg", msg);
      // ログインフォームを格納
      model.addAttribute("LoginForm", form);
      return LOGIN_PAGE;
    }

    return "redirect:" + MenuController.MENU_PAGE_URL;
  }

  /**
   * ログアウト
   * @param model
   * @return
   */
  @RequestMapping(value = LOGOUT_URL, method = RequestMethod.GET)
  public String logout(Model model) {
    // 既存セッションを削除
    session.invalidate();
    model.addAttribute("LoginForm", new LoginForm());
    return LOGIN_PAGE;
  }
}
