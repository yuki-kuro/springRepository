package jp.co.sysystem.springWorkout.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jp.co.sysystem.springWorkout.service.LoginService;
import jp.co.sysystem.springWorkout.web.controller.page.LoginController;
import jp.co.sysystem.springWorkout.web.controller.page.MenuController;

@Configuration
@EnableWebSecurity
@Order(3)
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {
  private LoginService userDetailsService;

  /**
   * セッションタイムアウト発生時の処理定義
   */
  @Autowired
  SessionExpiredDetectingLoginUrlAuthenticationEntryPoint sessionExpiredDetectingLoginUrlAuthenticationEntryPoint;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // 認証設定
    // 認証URLの設定
    http.antMatcher("/**").authorizeRequests()
        // ログイン画面は全ユーザーがアクセスできる
        .antMatchers(
            LoginController.LOGIN_FORM_URL,
            LoginController.LOGIN_FORM_URL + "?**")
        .permitAll()
        // 全てのURLリクエストは認証されているユーザーしかアクセスできない
        .anyRequest().authenticated()
        .and()

        // ログイン押下時の設定
        .formLogin()
        // ログイン画面のURL
        .loginPage(LoginController.LOGIN_FORM_URL)
        // ログインの処理をするURL
        .loginProcessingUrl(LoginController.LOGIN_PROCESS_URL)
        // ログインに失敗した時のURL
        .failureUrl(LoginController.LOGIN_FORM_URL + "?error")
        // ログインが成功した時のURL
        // 第2引数のboolean
        //   true  : ログイン画面した後必ずtopにとばされる
        //   false : (認証されてなくて一度ログイン画面に飛ばされても)ログインしたら指定したURLに飛んでくれる
        .defaultSuccessUrl(MenuController.MENU_PAGE_URL, false)
        // リクエストで渡されるログインID項目
        .usernameParameter("id")
        // リクエストで渡されるパスワード項目
        .passwordParameter("password")
        .permitAll()

        // ログアウト
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher(LoginController.LOGOUT_URL + "**"))
        .logoutSuccessUrl(LoginController.LOGIN_FORM_URL)

        // 例外処理
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(sessionExpiredDetectingLoginUrlAuthenticationEntryPoint)

        // アクセス不可ページの設定(デフォルト画面を使わない)
        .and()
        .exceptionHandling()
        .accessDeniedPage("/error/accessDenied.html");
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    // 認証対象外のパスを設定
    web.ignoring().antMatchers("/img/**", "/css/**", "/js/**", "/webjars/**", "/fonts/**", "/pc/**", "/scss/**",
        "/favicon.ico");
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        // ユーザー認証する処理を設定する
        .userDetailsService(this.userDetailsService)
        // 入力値をbcryptでハッシュ化した値でパスワード認証を行う
        .passwordEncoder(new BCryptPasswordEncoder());
  }

  @Autowired
  public void setUserDetailsService(LoginService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }
}
