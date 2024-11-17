package jp.co.sysystem.springWorkout.web.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchForm {
  //半角英数チェック用のアノテーション
  @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{MSE002}")
  private String searchId;

  //全角チェック用のアノテーション
  @Pattern(regexp = "^[^ -~｡-ﾟ]*$", message = "{MSE010}")
  private String searchName;
  
  //半角カナチェック用のアノテーション
  @Pattern(regexp = "^[ｦ-ﾟ]*$", message = "{MSE013}")
  private String searchKana;
}
