package jp.co.sysystem.springWorkout.web.controller.page;

import static jp.co.sysystem.springWorkout.web.controller.page.LoginController.*;

import javax.servlet.http.HttpSession;
import jp.co.sysystem.springWorkout.service.SearchService;
import jp.co.sysystem.springWorkout.service.UpdateService;
import jp.co.sysystem.springWorkout.util.MessageUtil;
import jp.co.sysystem.springWorkout.web.form.LoginForm;
import jp.co.sysystem.springWorkout.web.form.ResultTable;
import jp.co.sysystem.springWorkout.web.form.UpdateUserForm;
import lombok.extern.slf4j.Slf4j;
import org.jooq.exception.DataAccessException;
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
 * 変更画面コントローラー。
 *
 * <p>変更画面に関係するリクエストを処理するクラス。
 *
 * @version 1.0.1 2020/06/22 新規作成
 */
@Controller
@EnableAutoConfiguration
@Slf4j
public class UpdateController {

  @Autowired
  HttpSession session;

  @Autowired
  public MessageUtil msgutil;

  @Autowired
  public UpdateService update;

  @Autowired
  public SearchService search;

  /// URL定義
  public static final String UPDATE_USER_URL = "/updateUserPage"; // 検索画面内更新ボタン押下
  public static final String BACK_UPDATE_USER_URL = "/backUpdateUserPage"; // 検索画面内更新ボタン押下
  public static final String UPDATE_USER_CONFIRM_URL = "/updateUserConfirmPage"; // 変更内容確認
  public static final String UPDATE_USER_PROCESS_URL = "/updateUser"; // 変更処理
  public static final String SEARCH_FORM_URL = "/searchPage"; // 更新成功後フォワード先

  ///ページ定義
  public static final String UPDATE_USER_PAGE = "page/update_user"; // 変更入力画面
  public static final String UPDATE_USER_CONFIRM_PAGE = "page/update_user_confirm"; // 変更確認画面
  public static final String SEARCH_PAGE = "page/search"; // 検索画面

  /**
   * 変更入力画面表示処理。
   *
   * <p>検索画面で選択されたユーザーIDからユーザー情報を取得し、変更入力画面へ遷移。
   *
   * @param rt ResultTable型。検索画面で選択されたユーザーIDを含む。
   */
  @RequestMapping(value = UPDATE_USER_URL, method = RequestMethod.POST)
  public String updateUser(
      @ModelAttribute UpdateUserForm form,
      @ModelAttribute ResultTable rt, Model model) {

    //セッションにログイン情報が無ければログインページへ遷移
    if (session.getAttribute(AUTHENTICATED) == null) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_PAGE;
    }

    // 対象レコードデータ取得
    String tgtId = rt.getId();
    form = update.getUserData(tgtId);

    // 値の受け渡し処理
    model.addAttribute("updateUserForm", form);
    return UPDATE_USER_PAGE;
  }

  /**
   * 変更確認画面表示処理。
   *
   * <p>変更入力画面の入力内容に不備がない場合、変更確認画面へ遷移する。
   *
   * @param form UpdateUserForm型。変更入力画面で入力されたユーザー情報。
   */
  @RequestMapping(value = UPDATE_USER_CONFIRM_URL, method = RequestMethod.POST)
  public String userConfirm(
      @Validated @ModelAttribute UpdateUserForm form,
      BindingResult bindingResult,
      Model model) {

    //セッションにログイン情報が無ければログインページへ遷移
    if (session.getAttribute(AUTHENTICATED) == null) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_PAGE;
    }

    // 変更内容に不備があったら、不備詳細とフォームを格納してもう一度変更入力画面へ遷移
    if (bindingResult.hasErrors()) {
      model.addAttribute("updateUserForm", form);
      return UPDATE_USER_PAGE;
    }

    // 値の受け渡し処理
    model.addAttribute("updateUserForm", form);
    return UPDATE_USER_CONFIRM_PAGE;
  }

  /**
   * 更新実行処理。
   *
   * <p>更新確認ダイアログで「はい」が押されたら更新処理<br>
   * 更新成功した場合は、検索画面に遷移する。<br>
   * 更新失敗した場合は、変更確認画面に戻る。
   *
   * @param form UpdateUserForm型。更新確認画面に表示されたユーザー情報。
   */
  @RequestMapping(value = UPDATE_USER_PROCESS_URL, params = "yes", method = RequestMethod.POST)
  public String update(
      @Validated @ModelAttribute UpdateUserForm form,
      RedirectAttributes redirectAttributes,
      BindingResult bindingResult,
      Model model) {

    //セッションにログイン情報が無ければログインページへ遷移
    if (session.getAttribute(AUTHENTICATED) == null) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_PAGE;
    }

    // 更新処理
    try {
      update.updateRecords(form);
    } catch (DataAccessException dae) {
      // 更新失敗した場合は変更確認画面に戻る
      // 更新失敗メッセージを格納
      String msg = msgutil.getMessage("dbacces.updateFailed");
      model.addAttribute("msg", msg);
      // ログ出力
      log.warn(msg);
      return UPDATE_USER_CONFIRM_PAGE;
    }

    // 検索画面内の再検索処理に遷移
    redirectAttributes.addFlashAttribute("status", "update");
    return "redirect:" + SEARCH_FORM_URL;
  }

  /**
   * 変更確認画面の戻るボタン押下時の処理。
   *
   * <p>変更入力画面へ遷移。
   *
   * @param form UpdateUserForm型。変更確認画面に表示されたユーザー情報。
   */
  @RequestMapping(value = BACK_UPDATE_USER_URL, method = RequestMethod.POST)
  public String backUpdateUser(
      @ModelAttribute UpdateUserForm form,
      @ModelAttribute ResultTable rt, Model model) {

    //セッションにログイン情報が無ければログインページへ遷移
    if (session.getAttribute(AUTHENTICATED) == null) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_PAGE;
    }

    // 値の受け渡し処理
    model.addAttribute("updateUserForm", form);
    return UPDATE_USER_PAGE;
  }
}
