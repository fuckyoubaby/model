package com.changhong.system.repository;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.user.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:25
 */
public interface UserDao extends EntityObjectDao {

    /*********************************系统用户********************************************/

    UserDetails findUserByName(String username);

    List<User> loadUsers(String xingMing, int startPosition, int pageSize);

    int loadUserSize(String name);

    boolean loadUserExist(String userUuid, String username);

}
