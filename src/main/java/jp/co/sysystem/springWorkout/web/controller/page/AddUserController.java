
package jp.co.sysystem.springWorkout.web.controller.page;

import static jp.co.sysystem.springWorkout.web.controller.page.LoginController.*;

import java.text.ParseException;
import javax.servlet.http.HttpSession;
import jp.co.sysystem.springWorkout.domain.table.User;
import jp.co.sysystem.springWorkout.service.AddUserService;
import jp.co.sysystem.springWorkout.util.MessageUtil;
import jp.co.sysystem.springWorkout.web.form.AddUserForm;
import jp.co.sysystem.springWorkout.web.form.LoginForm;
import jp.co.sysystem.springWorkout.web.form.group.AddUserIdGroup;
import jp.co.sysystem.springWorkout.web.form.group.AddUserOtherGroup;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 新規登録入力、新規登録確認画面コントローラー。
 *
 * <p>新規登録関係のリクエストを処理するクラス
 *
 * @version 1.0.0 2020/06/10 新規作成
 */

@Controller
@EnableAutoConfiguration
@Slf4j
public class AddUserController {

  @Autowired
  HttpSession session;

  @Autowired
  public MessageUtil msgutil;

  @Autowired
  public AddUserService adduser;

  /// URL定義
  public static final String ADD_USER_URL = "/addUserPage";
  public static final String ADD_USER_PROCESS_URL = "/addUser";
  public static final String SEARCH_URL = "/search";
  public static final String ADD_USER_CONFIRM_URL = "/addUserConfirmPage";
  public static final String SEARCH_FORM_URL = "/searchPage";

  ///ページ定義
  public static final String SEARCH_PAGE = "page/search";
  public static final String ADD_USER_PAGE = "page/add_user";
  public static final String ADD_USER_CONFIRM_PAGE = "page/add_user_confirm";

  /**
   * 新規登録画面遷移処理。
   *
   * <p>検索画面から新規登録ボタンで遷移してきた場合の処理。
   * ログイン済みであれば新規登録画面へ遷移する。
   */
  @RequestMapping(value = ADD_USER_URL, method = RequestMethod.GET)
  public String addUser(Model model) {

    //セッションにログイン情報が無ければログインページへ遷移
    if (session.getAttribute(AUTHENTICATED) == null) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_PAGE;
    }

    // フォームを送信する。
    model.addAttribute("addUserForm", new AddUserForm());

    return ADD_USER_PAGE;
  }

  /**
   * 新規登録画面遷移処理。
   *
   * <p>新規登録確認画面から戻るボタンで遷移してきた場合の処理。
   *
   * @param form AddUserForm型。新規登録確認画面で表示されたユーザー情報。
   */
  @RequestMapping(value = ADD_USER_URL, method = RequestMethod.POST)
  public String addUser(
      @Validated @ModelAttribute AddUserForm form,
      BindingResult bindingResult,
      Model model) {

    //セッションにログイン情報が無ければログインページへ遷移
    if (session.getAttribute(AUTHENTICATED) == null) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_PAGE;
    }

    // BeanValidationの結果確認
    if (bindingResult.hasErrors()) {

      model.addAttribute("addUserForm", new AddUserForm());
      return ADD_USER_PAGE;
    }

    model.addAttribute("addUserForm", form);

    return ADD_USER_PAGE;
  }

  /**
   * 入力処理。
   *
   * <p>入力に不備がない場合、新規登録確認画面へ遷移する。
   *
   * @param form AddUserForm型。新規登録入力画面で入力されたユーザー情報。
   */
  @RequestMapping(value = ADD_USER_PROCESS_URL, method = RequestMethod.POST)
  public String processAdd(@Validated({ AddUserIdGroup.class, AddUserOtherGroup.class })
              @ModelAttribute AddUserForm form,
      BindingResult bindingResult,
      Model model) throws ParseException {

    //セッションにログイン情報が無ければログインページへ遷移
    if (session.getAttribute(AUTHENTICATED) == null) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_PAGE;
    }

    // BeanValidationの結果確認
    if (bindingResult.hasErrors()) {
      // 新規登録フォームからの入力値確認結果にエラーがある場合の処理

      model.addAttribute("addUserForm", form);
      return ADD_USER_PAGE;
    }

    // 同一ID確認処理
    User u = adduser.checkId(form.getId());
    if (u != null) {
      // ユーザーデータが取得できた場合
      if (u.getId().equals(form.getId())) {

        // BindingResultへエラーメッセージを設定
        msgutil.addBindingResultFieldError(
            bindingResult, "id", "dbacces.alreadyUse", "addUserForm.id");

        // 新規登録画面で使用するFormを送信
        model.addAttribute("addUserForm", form);
        return ADD_USER_PAGE;
      }
    }

    // パスワードが一致しなかった場合
    if (!form.getPassword().equals(form.getRePassword())) {

      // エラーメッセージをリソースファイルから取得
      String msg = msgutil.getMessage("dbacces.notSamePass");
      log.debug(msg);
      // エラーメッセージを画面に表示する
      model.addAttribute("msg", msg);

      model.addAttribute("addUserForm", form);

      return ADD_USER_PAGE;
    }

    model.addAttribute("addUserForm", form);

    return ADD_USER_CONFIRM_PAGE;
  }

  /**
   * ユーザーID確認。
   *
   * <p>ID入力に不備がない場合、同一IDがないかをチェックする。
   *
   * @param form AddUserForm型。新規登録入力画面で入力されたユーザー情報。
   */
  @RequestMapping(value = ADD_USER_PROCESS_URL, params = "check", method = RequestMethod.POST)
  public String checkIdAddUser(
      @Validated(AddUserIdGroup.class) @ModelAttribute AddUserForm form,
      BindingResult bindingResult,
      Model model) {

    //セッションにログイン情報が無ければログインページへ遷移
    if (session.getAttribute(AUTHENTICATED) == null) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_PAGE;
    }

    // BeanValidationの結果確認
    if (bindingResult.hasErrors()) {
      // 遷移先の新規登録画面で使用するFormを送信
      model.addAttribute("addUserForm", form);
      return ADD_USER_PAGE;
    }

    // ID入力欄が空の場合
    if (form.getId().isEmpty()) {
      // 遷移先の新規登録画面で使用するFormを送信
      model.addAttribute("addUserForm", form);
      return ADD_USER_PAGE;
    }

    // 同一ID確認処理
    User u = adduser.checkId(form.getId());
    if (u != null) {
      // ユーザーデータが取得できた場合
      if (u.getId().equals(form.getId())) {
        // 同一IDがあった場合
        String msg = msgutil.getMessage("data.notUse");
        log.debug(msg);
        // 失敗メッセージを画面に表示する
        model.addAttribute("msg", msg);

        // 新規登録画面で使用するFormを送信
        model.addAttribute("addUserForm", form);
        return ADD_USER_PAGE;
      }
    } else {
      // メッセージをリソースファイルから取得
      String msg = msgutil.getMessage("data.canUse");

      // メッセージを画面に表示する
      model.addAttribute("smsg", msg);
    }

    // 新規登録画面で使用するFormを送信
    model.addAttribute("addUserForm", form);

    return ADD_USER_PAGE;

  }

  /**
   * 登録処理。
   *
   * <p>登録成功である場合、検索画面へリダイレクトする。
   *
   * @param form AddUserForm型。新規登録確認画面で表示されたユーザー情報。
   */
  @RequestMapping(value = ADD_USER_CONFIRM_URL, method = RequestMethod.POST)
  public String processAddUser(
      @Validated @ModelAttribute AddUserForm form,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes,
      Model model) throws ParseException {

    //セッションにログイン情報が無ければログインページへ遷移
    if (session.getAttribute(AUTHENTICATED) == null) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_PAGE;
    }

    // BeanValidationの結果確認
    if (bindingResult.hasErrors()) {

      // エラーメッセージをリソースファイルから取得
      String msg = msgutil.getMessage("dbacces.registFailed");
      log.debug(msg);
      // エラーメッセージを画面に表示する
      model.addAttribute("msg", msg);

      model.addAttribute("addUserForm", form);
      return ADD_USER_CONFIRM_PAGE;
    }

    // 入力した値をデータベースに追加する。
    try {
      adduser.insertUser(form);
      //adduser.InsertUserdetail(form.getId(),form.getBirth(),form.getClub());
    } catch (Exception e) {
      // 登録処理に失敗した場合
      String msg = msgutil.getMessage("dbacces.registFailed");
      log.debug(msg);
      // 失敗メッセージを画面に表示する
      model.addAttribute("msg", msg);

      // 遷移先の新規登録画面で使用するFormを送信
      model.addAttribute("addUserForm", form);
      return ADD_USER_CONFIRM_PAGE;
    }

    //登録処理に成功した場合
    String msg = msgutil.getMessage("data.regist");
    log.info(msg);

    redirectAttributes.addFlashAttribute("status", "add");
    return "redirect:" + SEARCH_FORM_URL;
  }

}
