package jp.co.sysystem.springWorkout.domain.repository;

import org.springframework.data.repository.CrudRepository;

import jp.co.sysystem.springWorkout.domain.table.User;


public interface LoginUserRepository extends CrudRepository<User, String> {

}
