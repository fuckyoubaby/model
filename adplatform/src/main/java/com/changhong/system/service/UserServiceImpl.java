package com.changhong.system.service;

import com.changhong.common.thread.ApplicationThreadPool;
import com.changhong.common.utils.SecurityUtils;
import com.changhong.system.domain.log.ActionModule;
import com.changhong.system.domain.log.ActionType;
import com.changhong.system.domain.user.User;
import com.changhong.system.log.ApplicationLog;
import com.changhong.system.repository.UserDao;
import com.changhong.system.web.event.ActionLogEvent;
import com.changhong.system.web.facade.assember.*;
import com.changhong.system.web.facade.dto.UserDTO;
import com.changhong.system.web.facade.dto.PasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:20
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        return userDao.findUserByName(username);
    }

    public UserDTO obtainUserByUuid(String userUuid) {
        User user = (User) userDao.findByUuid(userUuid, User.class);
        return UserWebAssember.toUserDTO(user);
    }

    public List<UserDTO> obtainUsers(String name, int startPosition, int pageSize) {
        List<User> users = userDao.loadUsers(name, startPosition, pageSize);
        return UserWebAssember.toUserDTOList(users);
    }

    public int obtainUserSize(String name) {
        return userDao.loadUserSize(name);
    }

    public boolean obtainUserExist(String userUuid, String username) {
        return userDao.loadUserExist(userUuid, username);
    }

    public void changeUserDetails(UserDTO userDTO) {
        User user = UserWebAssember.toUserDomain(userDTO);
        userDao.persist(user);

        //日志部分
        ActionLogEvent event = null;
        User current = SecurityUtils.currentUser();
        if (StringUtils.hasText(userDTO.getUuid())) {
            ApplicationLog.infoWithCurrentUser(UserServiceImpl.class, "change the user " + user.getUuid() + " info");
            event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_SYSTEM, "更新用户信息" + user.getXingMing());
        } else {
            ApplicationLog.infoWithCurrentUser(UserServiceImpl.class, "add the user " + user.getUuid() + " info");
            event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_SYSTEM, "添加用户信息" + user.getXingMing());
        }
        ApplicationThreadPool.executeThread(event);
    }

    public void changeStatusForUser(String userUuid) {
        User user = (User) userDao.findByUuid(userUuid, User.class);
        if (user.isEnabled()) {
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }

        //日志部分
        ApplicationLog.infoWithCurrentUser(UserServiceImpl.class, "change the user " + userUuid + " status");
        User current = SecurityUtils.currentUser();
        ActionLogEvent event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_SYSTEM, "更新用户状态" + user.getXingMing());
        ApplicationThreadPool.executeThread(event);
    }

    public PasswordDTO obtainUserPassword(String userUuid) {
        User user = (User) userDao.findByUuid(userUuid, User.class);
        return UserWebAssember.toPasswordDTO(user);
    }

    public boolean obtainOldPasswordRight(String userUuid, String oldPassword) {
        User user = (User) userDao.findByUuid(userUuid, User.class);
        return user.getPassword().equals(oldPassword) ? true : false;
    }

    public void changeUserPassword(String userUuid, String newPassword) {
        User user = (User) userDao.findByUuid(userUuid, User.class);
        user.setPassword(newPassword);

        //日志部分
        ApplicationLog.infoWithCurrentUser(UserServiceImpl.class, "change the user " + userUuid + " password");
        User current = SecurityUtils.currentUser();
        ActionLogEvent event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_SYSTEM, "修改用户" + user.getXingMing() + "密码");
        ApplicationThreadPool.executeThread(event);
    }
}
