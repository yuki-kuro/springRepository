package jp.co.sysystem.springWorkout.service;

import jp.co.sysystem.springWorkout.domain.jooqRepository.DeleteUserJooqRepository;
import jp.co.sysystem.springWorkout.domain.repository.LoginUserRepository;
import jp.co.sysystem.springWorkout.web.form.ResultTable;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 削除処理定義クラス。
 *
 * @version 1.0.0 2020/06/24 新規作成
 */
@Service
@Transactional(readOnly = true)
public class UserDeleteService {

  /**
   * SpringDataJDBCによるリポジトリ処理
   */
  @Autowired
  protected LoginUserRepository rep;

  /**
   * JOOQによるデータ取得処理
   */
  @Autowired
  private DeleteUserJooqRepository jrep;

  /**
   * 検索画面で選択された削除対象IDから削除対象レコードを検索する。
   *
   * @param id String型、NotNull。削除対象のユーザーID。
   * @return ResultTable型。検索されたユーザー情報を返す。検索結果が0件の場合Nullが返される。
   */
  public ResultTable checkDeleteUser(@NonNull String id) {

    // IDでユーザー情報を取得
    ResultTable r = jrep.findById(id);

    return r;

  }

  /**
   * 削除対象のユーザーIDと一致するレコードを、ユーザーマスタ、ユーザーマスタ詳細テーブルから削除する。
   *
   * @param id String型、NotNull。削除対象のユーザーID。
   * @return {@code int[]}型。ユーザーマスタとユーザーマスタ詳細から削除成功した件数をそれぞれ返す。
   */
  @Transactional(readOnly = false)
  public int[] exeDeleteUser(@NonNull String id) {

    jrep.findByIdForUpdate(id);

    int[] rd = new int[2];

    rd[0] = jrep.userDelete(id);

    rd[1] = jrep.userDetailDelete(id);

    return rd;

  }

}
