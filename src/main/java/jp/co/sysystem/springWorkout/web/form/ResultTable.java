package jp.co.sysystem.springWorkout.web.form;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 検索結果保持用クラス
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultTable {

  private String id;

  private String name;

  private String kana;

  /**
   * Date型を指定のパターンにフォーマットする
   */
  @DateTimeFormat(pattern = "yyyy/MM/dd")
  private Date birth;

  private String club;

  private int no;
}
