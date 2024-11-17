package jp.co.sysystem.springWorkout.domain.table;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import lombok.Data;

/**
 * Spring-data-jdbcで使用するエンティティ定義<br>
 * USERテーブル定義
 * @version 1.0.0 : 2020/05/13 新規作成
 */
@Data
public class User implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  private String id;
  private String pass;
  private String name;
  private String kana;
}
