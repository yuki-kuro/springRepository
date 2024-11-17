package jp.co.sysystem.springWorkout.web.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public class UserForm {
    //半角英数チェック
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{MSE002}")
    private String Id;

    //空白チェック
    @NotBlank(message = "{MSE009}")
    //全角チェック
    @Pattern(regexp = "^[^ -~｡-ﾟ]*$", message = "{MSE010}")
    private String Name;
    
    //空白チェック
    @NotBlank(message = "{MSE012}")
    //半角カナチェック
    @Pattern(regexp = "^[ｦ-ﾟ]*$", message = "{MSE013}")
    private String Kana;
    
    //空白チェック
    @NotBlank(message = "{MSE016}")
    //年月日チェック
    @Pattern(regexp = "^((19|20)\\d\\d)/(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])*$", message = "{MSE018}")
    private String Birth;
    
    //全角チェック
    @Pattern(regexp = "^[^ -~｡-ﾟ]*$", message = "{MSE019}")
    private String Club;
  }
