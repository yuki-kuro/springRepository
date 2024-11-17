package jp.co.sysystem.springWorkout.service;

import jp.co.sysystem.springWorkout.domain.jooqRepository.UpdateUserJooqRepository;
import jp.co.sysystem.springWorkout.web.form.UpdateUserForm;
import lombok.NonNull;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.MappingException;
import org.jooq.exception.TooManyRowsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 更新処理定義クラス。
 *
 * @version 1.0.0 2020/06/16 新規作成
 */
@Service
@Transactional(readOnly = true)
public class UpdateService {

  /**
   * JOOQによるデータ取得処理
   */
  @Autowired
  private UpdateUserJooqRepository jrep;

  /**
   * 検索画面で更新対象として選択したレコードのidをもとに、データを取得。
   *
   * @param id String型、NotNull。更新対象のユーザーID。
   * @return UpdateUserForm型。検索されたユーザー情報を返す。ユーザー情報がなかった場合Nullが返される。
   */
  public UpdateUserForm getUserData(@NonNull String id)
      throws DataAccessException, MappingException, TooManyRowsException {
    UpdateUserForm form =  jrep.selectUserAndUserdetail(id);
    return form;
  }


  /**
   * フォームで入力した情報をもとにデータを更新。
   *
   * @param form UpdateUserForm型、NotNull。更新画面で入力されたIDなどを含むユーザー情報。
   * @throws DataAccessException DataAccessException
   */
  @Transactional(readOnly = false)
  public void updateRecords(@NonNull UpdateUserForm form) throws DataAccessException {
    //更新する前、テーブルをロックする
    jrep.findByIdForUpdate(form.getId());
    // ユーザーマスタを更新
    jrep.updateUser(form.getId(), form.getName(), form.getKana());

    // ユーザーマスタ詳細を更新
    jrep.updateUserDetail(form.getId(), form.getBirth(), form.getClub());
  }
}
