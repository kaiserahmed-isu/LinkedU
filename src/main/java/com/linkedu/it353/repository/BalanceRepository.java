package com.linkedu.it353.repository;

import com.linkedu.it353.model.Balance;
import com.linkedu.it353.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Kaiser on 5/2/2017.
 */
@Repository("balanceRepository")
public interface BalanceRepository extends JpaRepository<Balance, Long> {
    public Balance findOneByUser(User user);
}
