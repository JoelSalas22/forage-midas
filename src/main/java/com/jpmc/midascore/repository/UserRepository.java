package com.jpmc.midascore.repository;

import com.jpmc.midascore.entity.UserRecord;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserRecord, Long> {

    UserRecord findById(long id);


    @Query("SELECT u.balance FROM UserRecord u where u.id = ?1")
    float getBalanceById(long id);

    @Transactional
    @Modifying
    @Query("update UserRecord u set u.balance = ?2 where u.id = ?1")
    void updateBalanceById(long id, float balance);

    UserRecord findByName(@NonNull String name);


}
