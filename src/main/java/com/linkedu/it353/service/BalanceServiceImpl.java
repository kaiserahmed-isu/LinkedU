package com.linkedu.it353.service;

import com.linkedu.it353.model.Balance;
import com.linkedu.it353.model.User;
import com.linkedu.it353.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Kaiser on 5/2/2017.
 */
@Service("balanceService")
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private BalanceRepository balanceRepository;

    @Override
    public Balance findBalance(User user) {
        return balanceRepository.findOneByUser(user);
    }

    @Override
    public void saveBalance(Balance balance) {
        balanceRepository.save(balance);
    }
}
