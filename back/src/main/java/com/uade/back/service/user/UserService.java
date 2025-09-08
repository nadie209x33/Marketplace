package com.uade.back.service.user;

public interface UserService {
    void upgradeToAdmin(Integer userId);
    void downgradeToUser(Integer userId);
}
