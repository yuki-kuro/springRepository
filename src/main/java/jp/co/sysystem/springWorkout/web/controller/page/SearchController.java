package jp.co.sysystem.springWorkout.web.controller.page;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jp.co.sysystem.springWorkout.service.LoginService;
import jp.co.sysystem.springWorkout.service.SearchService;
import jp.co.sysystem.springWorkout.util.MessageUtil;
import jp.co.sysystem.springWorkout.web.form.LoginForm;
import jp.co.sysystem.springWorkout.web.form.RegisterForm;
import jp.co.sysystem.springWorkout.web.form.SearchForm;
import jp.co.sysystem.springWorkout.web.form.UserForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 検索画面コントローラー.
 */
@Controller
@EnableAutoConfiguration
@Slf4j
public class SearchController {

  @Autowired
  HttpSession session;

  @Autowired
  public MessageUtil msgutil;

  @Autowired
  public LoginService login;
  
  @Autowired
  public SearchService search;

  /// URL定義
  public static final String SEARCH_PROCESS_URL = "/search";
  public static final String REGISTRATION_URL = "/reg";
  public static final String EDIT_URL = "/edit";
  public static final String DELETE_URL = "/delete";

  ///ページ定義
  public static final String SEARCH_PAGE = "page/search";
  public static final String REGISTRATION_PAGE = "page/reg";
  public static final String EDIT_PAGE = "page/edit";
  public static final String DELETE_PAGE = "page/delete";

  /**
   * セッションからログインフォームを取得し､サーチフォームを新しく作成して.
   * 保存して検索画面に戻す
   * /searchのget()メソッドでアクセスする｡
   */
  @RequestMapping(value = SEARCH_PROCESS_URL, method = RequestMethod.GET)
  public String showSearchPage(HttpServletRequest request, Model model) {
    LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");
    model.addAttribute("searchForm", new SearchForm());
    model.addAttribute("loginForm", loginForm);
    return SEARCH_PAGE;
  }
  /**
   * エラーがなければ､searchresultsに検索結果を格納し､検索画面に返す.
   * /searchのpost()メソッドでアクセスする｡
   */
  
  @RequestMapping(value = SEARCH_PROCESS_URL, method = RequestMethod.POST)
  public String processSearch(
      @Validated @ModelAttribute SearchForm searchForm,
      BindingResult bindingResult,
      Model model) {
    List<UserForm> searchResult = new ArrayList<UserForm>();
    LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");
    // すべてのテキストボックスが空
    if (searchForm.getSearchid().isEmpty() && searchForm.getSearchname().isEmpty() 
        && searchForm.getSearchkana().isEmpty()) {
      String msg = msgutil.getMessage("MSE015");
      log.debug(msg);
      model.addAttribute("msg", msg);
      model.addAttribute("loginForm", loginForm);
      model.addAttribute("searchForm", searchForm);
      return SEARCH_PAGE;
    }
    // バインディングエラー
    if (bindingResult.hasErrors()) {
      String msg = msgutil.getMessage("search.idfailure");
      log.debug(msg);
      model.addAttribute("msg", msg);
      model.addAttribute("loginForm", loginForm);
      model.addAttribute("searchForm", searchForm);
      return SEARCH_PAGE;
    }
    // 検索を行う
    searchResult = search.searchUserId(
        searchForm.getSearchid(), searchForm.getSearchname(), searchForm.getSearchkana());
    // 検索結果がnullならエラー表示
    if (searchResult == null) {
      String msg = msgutil.getMessage("MSE022");
      log.debug(msg);
      model.addAttribute("msg", msg);
      model.addAttribute("loginForm", loginForm);
      model.addAttribute("searchForm", searchForm);
      return SEARCH_PAGE;
    }
    // 検索成功時の処理
    model.addAttribute("searchresults", searchResult);
    model.addAttribute("loginForm", loginForm);
    model.addAttribute("searchForm", searchForm);
    return SEARCH_PAGE;
  }
  
  /**
   *. 
   */
  @RequestMapping(value = REGISTRATION_URL, method = RequestMethod.POST)
  public String moveRegi(
      SearchForm searchForm,
      Model model) {
    LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");
    model.addAttribute("loginForm", loginForm);
    model.addAttribute("registerForm", new RegisterForm());
    return REGISTRATION_PAGE;
  }
  
  /**
   * .
   */
  @RequestMapping(value = EDIT_URL, method = RequestMethod.POST)
  public String moveEdit(
      SearchForm searchForm,
      Model model) {
    return EDIT_PAGE;
  }
  
  /**
   * .
   */
  @RequestMapping(value = DELETE_URL, method = RequestMethod.POST)
  public String moveDelete(
      SearchForm searchForm,
      Model model) {
    return DELETE_PAGE;
  }
}
