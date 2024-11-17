package jp.co.sysystem.springWorkout.web.form;

import java.sql.Timestamp;
import java.util.Calendar;
import javax.validation.constraints.AssertTrue;
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
public class RegisterForm {
  @NotBlank(message = "{MSE001}")
  @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{MSE002}")  //半角英数のみのバリデーション
    private String registerId;

  @NotBlank(message = "{MSE005}")
  @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{MSE006}")
  private String registerPassword;
  
  @NotBlank(message = "{MSE005}")
  @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{MSE008}")
  private String reenterPassword;
  
  @NotBlank(message = "{MSE009}")
  @Pattern(regexp = "^[^!-~｡-ﾟ]*$", message = "{MSE010}")  //全角文字のみのバリデーション
  private String registerName;

  @NotBlank(message = "{MSE012}")
  @Pattern(regexp = "^[ｦ-ﾟ]*$", message = "{MSE013}")  //半角カナのみのバリデーション
  private String registerKana;

  @NotBlank(message = "{MSE016}")
  private String registerBirthday;
  
  @Pattern(regexp = "^[^!-~｡-ﾟ]*$", message = "{MSE019}")
  private String registerClub;
  
  
  
}
