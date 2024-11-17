package jp.co.sysystem.springWorkout.web.controller.page;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.sysystem.springWorkout.domain.table.User;
import jp.co.sysystem.springWorkout.domain.table.Userdetail;
import jp.co.sysystem.springWorkout.service.RegisterService;
import jp.co.sysystem.springWorkout.util.MessageUtil;
import jp.co.sysystem.springWorkout.web.form.LoginForm;
import jp.co.sysystem.springWorkout.web.form.RegisterForm;
import jp.co.sysystem.springWorkout.web.form.SearchForm;
import lombok.extern.slf4j.Slf4j;

/**
 * 新規登録画面コントローラー.
 */
@Controller
@SessionAttributes("registerForm")
@EnableAutoConfiguration
@Slf4j
public class RegisterController {
  @Autowired
  HttpSession session;

  @Autowired
  public MessageUtil msgutil;

  @Autowired
  public RegisterService reg;

  public static final String REGISTRATION_CHECK_URL = "/reg/check";
  public static final String REGISTRATION_CONFIRM_URL = "/reg/confirm";
  public static final String REGISTRATION_YES_URL = "/reg/yes";

  public static final String REGISTRATION_PAGE = "page/reg";
  public static final String REGISTRATION_CONFIRM_PAGE = "page/reg-confirm";
  public static final String SEARCH_PAGE = "page/search";

  @ModelAttribute("registerForm")
  public RegisterForm registerForm() {
    return new RegisterForm();
  }
  @GetMapping(REGISTRATION_CHECK_URL)
  public String back(
      @Validated @ModelAttribute RegisterForm registerForm,
      BindingResult bindingResult,
      Model model) {
    // ログインフォームをセッションから取得､格納し､画面遷移
    LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");
    model.addAttribute("loginForm", loginForm);
    return REGISTRATION_PAGE;
  }

  /**
   * ID確認ボタンを押したときに呼び出される.
   * バインド､バリデーション､重複を確認し新規登録画面に戻す
   */
  @PostMapping(REGISTRATION_CHECK_URL)
  public String checkId(
      @Validated @ModelAttribute RegisterForm registerForm,
      BindingResult bindingResult,
      Model model) {
    // bindingResultにエラーが格納されている場合の処理
    if (bindingResult.hasErrors()) {
      // ログインフォームをセッションから取得､格納し､画面遷移
      LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");
      model.addAttribute("loginForm", loginForm);
      return REGISTRATION_PAGE;
    }

    // 登録しようとしているIDが重複している場合の処理
    if (reg.checkRegisterId(registerForm.getRegisterId())) {
      // message.propertiesからメッセージを取得し､msgに格納
      String msg = msgutil.getMessage("MSI005");
      log.debug(msg);
      model.addAttribute("msg", msg);

      // ログインフォームをセッションから取得､格納し､画面遷移
      LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");
      model.addAttribute("loginForm", loginForm);
      return REGISTRATION_PAGE;
    }

    // message.propertiesからメッセージを取得し､msgに格納
    String msg = msgutil.getMessage("MSI004");
    log.debug(msg);
    model.addAttribute("msg", msg);

    // ログインフォームをセッションから取得､格納し､画面遷移
    LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");
    model.addAttribute("loginForm", loginForm);
    return REGISTRATION_PAGE;
  }

  /**
   * .
  
   * @param registerForm a
   * @param bindingResult a
   * @param model a
   * @return a
   */
  @PostMapping(REGISTRATION_CONFIRM_URL)
  public String showRegistrationConfirmPage(
      @Validated @ModelAttribute RegisterForm registerForm,
      BindingResult bindingResult,
      Model model) {

    // bindingResultにエラーが格納されている場合の処理
    if (bindingResult.hasErrors()) {
      // ログインフォームをセッションから取得､格納し､画面遷移
      LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");
      model.addAttribute("loginForm", loginForm);
      return REGISTRATION_PAGE;
    }

    // パスワードとパスワード再入力が異なる場合の処理
    if (!registerForm.getRegisterPassword().equals(registerForm.getReenterPassword())) {
      // message.propertiesからメッセージを取得し､msgに格納
      String msg = msgutil.getMessage("MSE025");
      log.debug(msg);
      model.addAttribute("msg", msg);

      // ログインフォームをセッションから取得､格納し､画面遷移
      LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");
      model.addAttribute("loginForm", loginForm);
      return REGISTRATION_PAGE;
    }

    // ログインフォームをセッションから取得､格納し､画面遷移
    LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");
    model.addAttribute("loginForm", loginForm);
    return REGISTRATION_CONFIRM_PAGE;
  }

  @PostMapping(REGISTRATION_YES_URL)
  public String showRegistrationResults(
      @Validated @ModelAttribute RegisterForm registerForm,
      BindingResult bindingResult,
      Model model) {
    // データベースに登録
    
    User user = new User();
    Userdetail userdetail = new Userdetail();
    user.setId(registerForm.getRegisterId());
    user.setPass(registerForm.getRegisterPassword());
    user.setName(registerForm.getRegisterName());
    user.setKana(registerForm.getRegisterKana());
    userdetail.setBirth(registerForm.getRegisterBirthday());
    userdetail.setClub(registerForm.getRegisterClub());
    
    
    
    if(reg.register(user, userdetail)) {
    
    //登録できたときの処理
    // 登録成功メッセージを保存
    String msg = msgutil.getMessage("MSI007");
    log.debug(msg);
    model.addAttribute("msg", msg);
    // 検索フォームを生成し保存
    model.addAttribute("searchForm", new SearchForm());
    // ログインフォームをセッションから取得､格納し､画面遷移
    LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");    
    model.addAttribute("loginForm", loginForm);
    return SEARCH_PAGE;
    }else {
    // 登録できなかったときの処理
      // message.propertiesからメッセージを取得し､msgに格納
      String msg = msgutil.getMessage("MSE024");
      log.debug(msg);
      model.addAttribute("msg", msg);
      
      // ログインフォームをセッションから取得､格納し､画面遷移
      LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");
      model.addAttribute("loginForm", loginForm);
      return REGISTRATION_CONFIRM_PAGE;
    }
    
  }

}
