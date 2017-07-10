package com.changhong.system.service;

import com.changhong.system.web.facade.dto.UserDTO;
import com.changhong.system.web.facade.dto.PasswordDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:20
 */
public interface UserService extends UserDetailsService {

    UserDTO obtainUserByUuid(String userUuid);

    List<UserDTO> obtainUsers(String xingMing, int startPosition, int pageSize);

    int obtainUserSize(String xingMing);

    boolean obtainUserExist(String userUuid, String username);

    void changeUserDetails(UserDTO userDTO);

    void changeStatusForUser(String userUuid);

    PasswordDTO obtainUserPassword(String userUuid);

    boolean obtainOldPasswordRight(String userUuid, String oldPassword);

    void changeUserPassword(String userUuid, String newPassword);
}
