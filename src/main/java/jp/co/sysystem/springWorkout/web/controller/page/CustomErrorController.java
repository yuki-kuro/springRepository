package jp.co.sysystem.springWorkout.web.controller.page;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * エラー画面コントローラー。
 *
 * <p>エラー関係のリクエストを処理するクラス。
 */
@Controller
public class CustomErrorController extends AbstractErrorController {

  // URL定義
  private static final String ERROR_PATH = "/error";

  ///ページ定義
  public static final String ERROR_PAGE = "page/error";

  @Autowired
  public CustomErrorController(ErrorAttributes errorAttributes) {
    super(errorAttributes);
  }


  /**
   * エラー画面表示処理。
   *
   * <p>エラーの内容を画面に表示する。
   */
  @RequestMapping(ERROR_PATH)
  public String handleErrors(HttpServletRequest request, Model model) {
    HttpStatus status = getStatus(request);

    if (status.equals(HttpStatus.NOT_FOUND)) {
      model.addAttribute("errmessage", "ページを検索できない/リンク先のページが存在しない");
    }
    if (status.equals(HttpStatus.FORBIDDEN)) {
      model.addAttribute("errmessage", "アクセスできない");
    }
    if (status.equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
      model.addAttribute("errmessage", "データ操作エラー");
    }

    // エラー情報を取得
    Map<String, Object> attr = getErrorAttributes(request, true);
    model.addAttribute("status", status.value());
    model.addAttribute("timestamp", attr.get("timestamp"));
    model.addAttribute("error", attr.get("error"));
    model.addAttribute("message", attr.get("message"));
    model.addAttribute("trace", attr.get("trace"));
    model.addAttribute("path", attr.get("path"));
    model.addAttribute("exception", attr);
    return ERROR_PAGE;
  }

  /**
   * エラー画面のパスを返すメソッドのオーバーライド。
   */
  @Override
  public String getErrorPath() {
    return ERROR_PATH;
  }
}
