package jp.co.sysystem.springWorkout.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sysystem.springWorkout.domain.jooqRepository.SearchUserJooqRepository;
import jp.co.sysystem.springWorkout.domain.repository.LoginUserRepository;
import jp.co.sysystem.springWorkout.web.form.UserForm;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * ログイン処理定義クラス
 * @version 1.0.0 2020/05/13 新規作成
 */
@Service
@Transactional
@Slf4j
public class SearchService {
  /**
   * SpringDataJDBCによるリポジトリ処理
   */
  @Autowired
  protected LoginUserRepository rep;

  /**
   * JOOQによるデータ取得処理
   */
  @Autowired
  private SearchUserJooqRepository jrep;

  /**
   * ユーザーID、名前、またはカナでユーザーを検索するメソッド。
   * 
   * @param searchId 検索するユーザーID
   * @param searchName 検索するユーザー名
   * @param searchKana 検索するユーザーのカナ
   * @return 検索結果のユーザーリスト。ユーザーが見つからない場合はnullを返す｡
   */
  public List<UserForm> searchUserId(String searchId, String searchName, String searchKana) {
    List<UserForm> search = new ArrayList<UserForm>();
    search = jrep.findById(searchId, searchName, searchKana);
    if (!search.isEmpty()) {
      return search;
    } else {
      log.warn(String.format("指定されたIDは存在しませんでした。"));
    }
    return null;
  }

}