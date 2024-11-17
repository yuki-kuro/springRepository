package jp.co.sysystem.springWorkout.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sysystem.springWorkout.domain.jooqRepository.LoginUserJooqRepository;
import jp.co.sysystem.springWorkout.domain.jooqRepository.RegisterUserJooqRepository;
import jp.co.sysystem.springWorkout.domain.table.User;
import jp.co.sysystem.springWorkout.domain.table.Userdetail;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;



/**
 * 新規登録処理定義クラス.
 */
@Service
@Transactional
@Slf4j
public class RegisterService {
  
  /**
   * JOOQによるデータ取得処理.
   */
  @Autowired
  private LoginUserJooqRepository jrep;
  
  /**
   * JOOQによるデータ取得処理.
   */
  @Autowired
  private RegisterUserJooqRepository rjrep;
  
  /**
   * 登録しようとしているIDが重複しているかどうかを判定するメソッド.
   */
  public boolean checkRegisterId(@NonNull String registerId) {
    // 登録しようとしているIDでユーザー情報を取得
    User u = jrep.findById(registerId);
    
    // ユーザー情報が取得できなければ重複なし
    if (null == u) {
      return false;
    }
    return true;
  }

  /**
   * 登録するメソッド.
   */
  public boolean register(User user, Userdetail userdetail) {
if(rjrep.insertOne(user, userdetail)) {
    return true;
}else {
  return false;
}

  }
}
