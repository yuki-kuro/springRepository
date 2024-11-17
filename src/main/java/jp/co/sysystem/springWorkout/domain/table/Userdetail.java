package jp.co.sysystem.springWorkout.domain.table;

import java.io.Serializable;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Spring-data-jdbcで使用するエンティティ定義.<br>
 * USERテーブル定義

 * @version 1.0.0 : 2020/05/13 新規作成
 */
@Data
public class Userdetail implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  private String no;
  private String id;
  private String birth;
  private String club;
}
