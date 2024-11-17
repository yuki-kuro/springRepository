package jp.co.sysystem.springWorkout.domain.jooqRepository;

import static jp.co.sysystem.springWorkout.domain.jooqObject.tables.User.*;
import static jp.co.sysystem.springWorkout.domain.jooqObject.tables.Userdetail.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.sysystem.springWorkout.domain.table.Userdetail;


@Component
public class RegisterUserJooqRepository {
  @Autowired
  private DSLContext dsl;
  
  
  public boolean insertOne(jp.co.sysystem.springWorkout.domain.table.User user, Userdetail userdetail) {
    
    String dateString = userdetail.getBirth();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
Timestamp timestamp = null;
    try {
        // StringをDateに変換
        Date parsedDate = dateFormat.parse(dateString);

        // DateをTimestampに変換
         timestamp = new Timestamp(parsedDate.getTime());

    } catch (ParseException e) {
        e.printStackTrace();
    }
    
    int ret = dsl.insertInto(USER, USER.ID, USER.PASS, USER.NAME, USER.KANA)
        .values(user.getId(), user.getPass(), user.getName(), user.getKana())
        .execute();
    int ret2 = dsl.insertInto(USERDETAIL, USER.ID, USERDETAIL.BIRTH, USERDETAIL.CLUB)
        .values(user.getId(), timestamp , userdetail.getClub())
        .execute();
    if (ret > 0 && ret2 > 0) {
    return true;
    } else {
      return false;
    }
  }
}
