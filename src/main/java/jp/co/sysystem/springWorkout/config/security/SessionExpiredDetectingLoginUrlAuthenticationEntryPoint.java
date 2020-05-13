package jp.co.sysystem.springWorkout.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * セッションタイムアウト発生時のExceptionカスタマイズクラス<br>
 * SpringSecurityのLoginUrlAuthenticationEntryPointを継承
 */
public class SessionExpiredDetectingLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

	/**
	 * コンストラクタ
	 * @param loginFormUrl
	 */
	public SessionExpiredDetectingLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	/* (非 Javadoc)
	 * @see org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint#commence(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void commence(
			HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {

		// Ajax通信である場合の処理と分岐
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			// Ajax通信である場合は、HTTPステータスを未認証に設定して返却
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		// 通常の画面遷移である場合は、セッションタイムアウトパラメータをつけてログイン画面へ遷移
		// ログイン画面へのURLは buildRedirectUrlToLoginPage メソッドで作成される
		super.commence(request, response, authException);
	}

	/* (非 Javadoc)
	 * @see org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint#buildRedirectUrlToLoginPage(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	protected String buildRedirectUrlToLoginPage(
			HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {

		String redirectUrl = super.buildRedirectUrlToLoginPage(request, response, authException);
		if (isRequestedSessionInvalid(request)) {
			redirectUrl += redirectUrl.contains("?") ? "&" : "?";
			redirectUrl += "timeout";
		}
		return redirectUrl;
	}

	/**
	 * セッションタイムアウトか、セッション未作成なのかを判定
	 * @param request
	 * @return セッションタイムアウトである場合：true
	 */
	private boolean isRequestedSessionInvalid(HttpServletRequest request) {
		return request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid();
	}
}
