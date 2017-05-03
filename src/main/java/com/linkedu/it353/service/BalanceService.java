package com.linkedu.it353.service;


import com.linkedu.it353.model.Balance;
import com.linkedu.it353.model.User;

/**
 * Created by Kaiser on 5/2/2017.
 */

public interface BalanceService {
    public Balance findBalance(User user);
    public void saveBalance(Balance balance);
}
