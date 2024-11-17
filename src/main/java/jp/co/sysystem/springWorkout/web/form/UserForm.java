package jp.co.sysystem.springWorkout.web.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * .
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
  //半角英数チェック用のアノテーション
  @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{validate.hwAlphanumeric}")
  private String Id;

  //全角文字のみのアノテーション
  @NotBlank(message = "{MSE009}")
  @Pattern(regexp = "^[^!-~｡-ﾟ]*$", message = "{MSE010}")
  private String Name;

  //半角カナのみのアノテーション
  @NotBlank(message = "{MSE012}")
  @Pattern(regexp = "^[ｦ-ﾟ]*$", message = "{MSE013}")
  private String Kana;
  
  //全角文字のみのアノテーション
  @NotBlank(message = "{MSE016}")
  @Pattern(regexp = "^((19|20)\\d\\d)/(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])*$",
      message = "{MSE018}")
  private String Birth;
  
  //全角文字のみのアノテーション
  @Pattern(regexp = "^[^!-~｡-ﾟ]*$", message = "{MSE019}")
  private String Club;
}
