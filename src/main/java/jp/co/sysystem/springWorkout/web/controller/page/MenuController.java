package jp.co.sysystem.springWorkout.web.controller.page;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
public class MenuController {

  /** メニューページのURL */
  public static final String MENU_PAGE_URL = "/menu";
  /** メニューページのhtmlファイルパス */
  public static final String MENU_PAGE = "page/menu";

  @RequestMapping(value = MENU_PAGE_URL)
  public String showMenuPage() {
    return MENU_PAGE;
  }
}
