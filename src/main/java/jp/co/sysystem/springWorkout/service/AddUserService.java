package jp.co.sysystem.springWorkout.service;

import java.text.ParseException;
import jp.co.sysystem.springWorkout.domain.jooqRepository.AddUserJooqRepository;
import jp.co.sysystem.springWorkout.domain.table.User;
import jp.co.sysystem.springWorkout.util.MessageUtil;
import jp.co.sysystem.springWorkout.web.form.AddUserForm;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 新規登録処理定義クラス。
 *
 * @version 1.0.0 2020/06/10 新規作成
 */
@Service
@Transactional(readOnly = true)
public class AddUserService {

  @Autowired
  public MessageUtil msgutil;

  /**
   * JOOQによるデータ取得処理
   */
  @Autowired
  private AddUserJooqRepository jrep;

  /**
   * ユーザーID確認処理。
   *
   * <p>新規登録画面から受け取った「ID」を使用して、
   * DBで管理されたユーザー情報にそのIDが登録されているかを確認する。
   *
   * @param id String型、NotNull。新規登録対象のユーザーID。
   * @return User型。検索されたユーザー情報を返す。ユーザー情報がなかった場合Nullが返される。
   */
  public User checkId(@NonNull String id) {

    // ログインIDでユーザー情報を取得
    User u = jrep.findById(id);

    return u;
  }

  /**
   * ユーザーとユーザー詳細にデータ追加処理。
   *
   * <p>入力された値をもとに関数を呼び出し、追加処理を行う。
   *
   * @param form AddUserForm型、NotNull。新規登録画面で入力されたユーザーIDなどを含むユーザー情報。
   * @throws ParseException ParseException
   */
  @Transactional(readOnly = false)
  public void insertUser(@NonNull AddUserForm form) throws ParseException {

    // ユーザーテーブル
    jrep.addUser(form.getId(), form.getPassword(), form.getName(), form.getKana());

    // ユーザー詳細テーブル
    jrep.addUserDetail(form.getId(), form.getBirth(), form.getClub());

  }

}
