package com.uade.back.service.user;

import com.uade.back.dto.user.AdminUserUpdateDTO;
import com.uade.back.dto.user.UserUpdateDTO;

public interface UserService {
    void upgradeToAdmin(Integer userId);
    void downgradeToUser(Integer userId);
    void updateUser(UserUpdateDTO userUpdateDTO);
    void adminUpdateUser(Integer userId, AdminUserUpdateDTO adminUserUpdateDTO);
}
