package jp.co.sysystem.springWorkout.web.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class AdviceController {

//	@Value("${view.encoding}")
	public String pageEncoding = "UTF-8";

	/**
	 * 全画面共通の設定を実施<br>
	 * <pre>
	 * ・pageEncoding：設定ファイルから取得（標準はUTF-8）
	 * </pre>
	 * @param model
	 */
	@ModelAttribute
	public void modelAttribute(Model model) {
		// ページエンコーディング
		model.addAttribute("pageEncoding", pageEncoding);
	}

	/**
	 * Controllerで処理されなかったExceptionを処理
	 * エラー画面へ遷移する
	 * @param exception
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void exceptionHandler(
			MethodArgumentNotValidException exception,
			HttpServletRequest request,
			HttpServletResponse response) {

		// ControllerでキャッチされなかったExceptionのログ
		log.error(exception.getMessage(), exception);

		try {
			// アクセス許可がなければ、メニュー画面へフォワード
			RequestDispatcher dispatch = request.getRequestDispatcher("/");
			dispatch.forward(request, response);
		} catch (Exception e) {
			// ログを出してあきらめる
			log.error(e.getMessage(), e);
		}
	}
}
