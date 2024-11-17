package jp.co.sysystem.springWorkout.domain.repository;

import org.springframework.data.repository.CrudRepository;

import jp.co.sysystem.springWorkout.domain.table.User;


/**
 * Spring-data-jdbc用リポジトリクラス<br>
 * save/delete等の処理や、自動生成クエリを利用可能。<br>
 * カスタムクエリは、基本的に {@link jp.co.sysystem.springWorkout.domain.jooqRepository jooqRespoitory} を利用する。
 * @see <a href="https://docs.spring.io/spring-data/jdbc/docs/1.1.7.RELEASE/reference/html/#reference">spring-data-jdbc</a>
 * @version 1.0.0 2020/05/13 新規作成
 */
public interface LoginUserRepository extends CrudRepository<User, String> {

}
