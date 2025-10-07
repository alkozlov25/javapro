package edu.t1.repository;

import edu.t1.entity.Operations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationsRepository extends JpaRepository<Operations, Long> {
    @Query("""
            update Operations op set op.state = "DELETED" where op.id = :id
            """)
    @Modifying
    void cancelOperation(Long id);

}
