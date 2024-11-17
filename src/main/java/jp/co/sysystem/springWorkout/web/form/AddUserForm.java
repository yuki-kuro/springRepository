
package jp.co.sysystem.springWorkout.web.form;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import jp.co.sysystem.springWorkout.web.form.group.AddUserIdGroup;
import jp.co.sysystem.springWorkout.web.form.group.AddUserOtherGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/** 新規登録入力用変数宣言クラス
 * @author maruno.reona
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserForm {

  /**
   * ID
   * ブランクチェック
   * 半角英数字チェック
   */
  @NotBlank(groups = { AddUserIdGroup.class,
      AddUserOtherGroup.class }, message = "{validate.notBlank}")
  @Pattern(groups = { AddUserIdGroup.class,
      AddUserOtherGroup.class }, regexp = "^[a-zA-Z0-9]*$", message = "{validate.enCharcode}")
  private String id;

  /**
   * パスワード
   * ブランクチェック
   * 半角英数字チェック
   */
  @NotBlank(groups = AddUserOtherGroup.class, message = "{validate.notBlank}")
  @Pattern(groups = AddUserOtherGroup.class,
      regexp = "^[a-zA-Z0-9]*$", message = "{validate.enCharcode}")
  private String password;

  /**
   * パスワード再入力
   * ブランクチェック
   * 半角英数字チェック
   */
  @NotBlank(groups = AddUserOtherGroup.class, message = "{validate.notBlank}")
  @Pattern(groups = AddUserOtherGroup.class,
          regexp = "^[a-zA-Z0-9]*$", message = "{validate.enCharcode}")
  private String rePassword;

  /**
   * 名前
   * ブランクチェック
   * 全角チェック
   */
  @NotBlank(groups = AddUserOtherGroup.class, message = "{validate.notBlank}")
  @Pattern(groups = AddUserOtherGroup.class,
          regexp = "^[^ -~｡-ﾟ]*$", message = "{validate.jpCharcode}")
  private String name;

  /**
   * カナ
   * ブランクチェック
   * 半角カナチェック
   */
  @NotBlank(groups = AddUserOtherGroup.class, message = "{validate.notBlank}")
  @Pattern(groups = AddUserOtherGroup.class,
        regexp = "^([ｦ-ﾟ]+)?$", message = "{validate.kanaCharcode}")
  private String kana;

  /**
   * 生年月日
   * NULLチェック
   * フォーマットチェック
   * 過去の日付チェック
   */
  @NotNull(groups = AddUserOtherGroup.class, message = "{validate.notBlank}")
  @DateTimeFormat(pattern = "yyyy/MM/dd")
  @Past(groups = AddUserOtherGroup.class, message = "{validate.invalidDate}")
  private Date birth;

  /**
   * 委員会
   * 全角チェック
   */
  @Pattern(groups = AddUserOtherGroup.class,
      regexp = "^[^ -~｡-ﾟ]*$", message = "{validate.jpCharcode}")
  private String club;
}