package jp.co.sysystem.springWorkout.web.form;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 変更入力フォームクラス
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserForm {

  /**
   *  ID
   *  <pre>
   *    半角が入力されているかどうかチェック
   *  </pre>
   */
  @Pattern(regexp = "^([0-9a-zA-Z]+)?$", message = "{validate.enCharcode}")
  private String id;

  /**
   *  名前
   *  <pre>
   *    ブランクチェック
   *    全角が入力されているかどうかチェック
   *  </pre>
   */
  @NotBlank(message = "{validate.notBlank}")
  @Pattern(regexp = "^([^ -~｡-ﾟ]+)?$", message = "{validate.jpCharcode}")
  private String name;

  /**
   * カナ
   * <pre>
   *    ブランクチェック
   *    半角カナが入力されているかどうかチェック
   * </pre>
   */
  @NotBlank(message = "{validate.notBlank}")
  @Pattern(regexp = "^([ｦ-ﾟ]+)?$", message = "{validate.kanaCharcode}")
  private String kana;

  /**
   * 生年月日
   * <pre>
   *    NULLチェック
   *    yyyy/mm/ddフォーマットチェック
   *    過去の日付が入力されているかどうかチェック
   * </pre>
   */
  @NotNull(message = "{validate.notBlank}")
  @DateTimeFormat(pattern = "yyyy/MM/dd")
  @Past(message = "{validate.invalidDate}")
  private Date birth;

  /**
   * 委員会
   * <pre>
   *    全角が入力されているかどうかチェック
   * </pre>
   */
  @Pattern(regexp = "^([^ -~｡-ﾟ]+)?$", message = "{validate.jpCharcode}")
  private String club;

}
