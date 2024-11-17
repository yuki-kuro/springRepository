package jp.co.sysystem.springWorkout.web.controller.page;

import static jp.co.sysystem.springWorkout.web.controller.page.LoginController.*;

import javax.servlet.http.HttpSession;
import jp.co.sysystem.springWorkout.service.UserDeleteService;
import jp.co.sysystem.springWorkout.util.MessageUtil;
import jp.co.sysystem.springWorkout.web.form.LoginForm;
import jp.co.sysystem.springWorkout.web.form.ResultTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 削除画面コントローラー。
 *
 * <p>削除関係のリクエストを処理するクラス。
 *
 */
@Controller
@EnableAutoConfiguration
@Slf4j
public class DeleteController {

  @Autowired
  HttpSession session;

  @Autowired
  public MessageUtil msgutil;

  @Autowired
  public UserDeleteService ds;

  /// URL定義
  public static final String DELETE_USER_URL = "/deleteUserPage";
  public static final String DELETE_EXE_URL = "/detele_exe";
  public static final String SEARCH_FORM_URL = "/searchPage";

  ///ページ定義
  public static final String SEARCH_PAGE = "page/search";
  public static final String DELETE_USER_PAGE = "page/delete_user";

  /**
   * 削除ボタンが押された時の処理。
   *
   * <p>ResultTableクラスのidフィールドに削除対象のidを保持して削除画面へ遷移。
   *
   * @param rt ResultTable型。検索画面で選択された削除対象のIDを含む。
   */
  @RequestMapping(value = DELETE_USER_URL, method = RequestMethod.POST)
  public String deleteUser(
      @ModelAttribute ResultTable rt, Model model) {

    //セッションにログイン情報が無ければログインページへ遷移
    if (session.getAttribute(AUTHENTICATED) == null) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_PAGE;
    }

    //IDからデータを取得する

    ResultTable u = ds.checkDeleteUser(rt.getId());

    if (null == u) {
      log.warn(String.format("USERテーブルにIDが存在しません[id:%s]", u));
      String msg = msgutil.getMessage("dbacces.deleteFailed");
      model.addAttribute("msg", msg);
      model.addAttribute("resultTable", new ResultTable());
      return DELETE_USER_PAGE;
    }

    model.addAttribute("resultTable", u);
    return DELETE_USER_PAGE;
  }

  /**
   * 削除確認画面で削除ボタンが押された時の処理。
   *
   * <p>画面に表示されたユーザーをユーザーマスタ、ユーザーマスタ詳細テーブルから削除実行し、
   * 検索画面にリダイレクトする
   *
   * @param rt ResultTable型。削除確認画面に表示されたユーザー情報。
   */
  @RequestMapping(value = DELETE_EXE_URL, method = RequestMethod.POST)
  public String delete_exe(
      @ModelAttribute ResultTable rt, RedirectAttributes redirectAttributes,
      Model model) {

    //セッションにログイン情報が無ければログインページへ遷移
    if (session.getAttribute(AUTHENTICATED) == null) {
      model.addAttribute("loginForm", new LoginForm());
      return LOGIN_PAGE;
    }

    try {

      int[] rd = ds.exeDeleteUser(rt.getId());

      if (rd[0] == 0 || rd[1] == 0) {
        String msg = msgutil.getMessage("dbacces.deleteFailed");
        model.addAttribute("msg", msg);
        model.addAttribute("resultTable", rt);
        return DELETE_USER_PAGE;
      }

    } catch (Exception e) {

      String msg = msgutil.getMessage("dbacces.deleteFailed");
      model.addAttribute("msg", msg);
      model.addAttribute("resultTable", rt);
      return DELETE_USER_PAGE;
    }

    //削除実行の後に検索ページへ遷移する
    redirectAttributes.addFlashAttribute("status", "delete");
    return "redirect:" + SEARCH_FORM_URL;

  }

}
