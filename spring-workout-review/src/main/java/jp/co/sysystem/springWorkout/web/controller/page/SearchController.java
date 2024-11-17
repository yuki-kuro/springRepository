package jp.co.sysystem.springWorkout.web.controller.page;

import java.util.List;

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

import jp.co.sysystem.springWorkout.service.SearchService;
import jp.co.sysystem.springWorkout.util.MessageUtil;
import jp.co.sysystem.springWorkout.web.form.LoginForm;
import jp.co.sysystem.springWorkout.web.form.SearchForm;
import jp.co.sysystem.springWorkout.web.form.UserForm;
import lombok.extern.slf4j.Slf4j;

/**
 * ログイン画面コントローラー
 * @version 1.0.0 2020/05/13 新規作成
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
  public SearchService search;

  /// URL定義
  public static final String SEARCH_PROCESS_URL = "/search";
    
  public static final String LOGIN_FORM_URL = "/";
  public static final String LOGIN_PROCESS_URL = "/login";
  public static final String LOGOUT_URL = "/logout";

  ///ページ定義
  public static final String LOGIN_PAGE = "page/login";
  public static final String SEARCH_PAGE = "page/search";

  /**
   *
   */
  @RequestMapping(value = SEARCH_PROCESS_URL, method = RequestMethod.POST)
  public String processSearch(@Validated @ModelAttribute SearchForm searchForm,
      BindingResult bindingResult,
      Model model) {
    // ログインフォームをセッションから取得
    LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");
    
    // テキストボックスがすべて空の場合
    if(searchForm.getSearchId().isEmpty() && searchForm.getSearchName().isEmpty() && searchForm.getSearchKana().isEmpty()) {
      String msg = msgutil.getMessage("MSE015");
      log.debug(msg);
      // modelに追加
      model.addAttribute("msg", msg);
      model.addAttribute("loginForm", loginForm);
      model.addAttribute("searchForm", searchForm);
      // 検索画面を表示
      return SEARCH_PAGE;
    }
    
    // 入力エラーがある場合
    if(bindingResult.hasErrors() ) {
      // エラーメッセージをリソースファイルから取得
      String msg = msgutil.getMessage("search.idfailure");
      log.debug(msg);
      // modelに追加
      model.addAttribute("msg", msg);
      model.addAttribute("loginForm", loginForm);
      model.addAttribute("searchForm", searchForm);
      // 検索画面を表示
      return SEARCH_PAGE;
    }
    
    // 問題がなければ検索を行う
    List<UserForm> searchResults = search.searchUserId(searchForm.getSearchId(), searchForm.getSearchName(), searchForm.getSearchKana());
    if (searchResults == null) {
      String msg = msgutil.getMessage("MSE022");
      log.debug(msg);
      // modelに追加
      model.addAttribute("msg", msg);
      model.addAttribute("loginForm", loginForm);
      model.addAttribute("searchForm", searchForm);
      // 検索画面を表示
      return SEARCH_PAGE;
    }
    // modelに追加
    model.addAttribute("searchResults", searchResults);
    model.addAttribute("loginForm", loginForm);
    model.addAttribute("searchForm", searchForm);
    // 検索画面を表示
    return SEARCH_PAGE;
  }

  
}
