package edu.t1.repository;

import edu.t1.entity.UserLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLimitRepository extends JpaRepository<UserLimit, Long> {
    @Query("""
            update UserLimit ul set ul.currLimit = ul.defLimit
            """)
    @Modifying
    void resetCurrLimitOnNewDay();

}
