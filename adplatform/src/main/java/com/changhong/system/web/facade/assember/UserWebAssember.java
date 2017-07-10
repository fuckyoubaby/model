package com.changhong.system.web.facade.assember;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.system.domain.user.User;
import com.changhong.system.web.facade.dto.UserDTO;
import com.changhong.system.web.facade.dto.PasswordDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:58
 */
public class UserWebAssember {

    public static User toUserDomain(UserDTO dto) {
        User user = null;
        if(dto == null) return null;

        if (StringUtils.hasText(dto.getUuid())) {
            user = (User) EntityLoadHolder.getUserDao().findByUuid(dto.getUuid(), User.class);
            user.setXingMing(dto.getXingMing());
            user.setUsername(dto.getUsername());
            user.setContactWay(dto.getContactWay());
        } else {
            user = new User(dto.getXingMing(), dto.getContactWay(), dto.getUsername(), dto.getPassword());
        }
        return user;
    }

    public static UserDTO toUserDTO(User user) {
        final String uuid = user.getUuid();
        final String xingMing = user.getXingMing();
        final String contactWay = user.getContactWay();
        final String username = user.getUsername();
        final String password = user.getPassword();
        final boolean enabled = user.isEnabled();

        UserDTO dto =  new UserDTO(uuid, xingMing, contactWay, username, password, enabled);

        return dto;
    }

    public static List<UserDTO> toUserDTOList(List<User> users) {
        List<UserDTO> dtos = new ArrayList<UserDTO>();
        if (users != null) {
            for (User loop : users) {
                dtos.add(toUserDTO(loop));
            }
        }
        return dtos;
    }

    public static PasswordDTO toPasswordDTO(User user) {
        final String uuid = user.getUuid();
        final String xingMing = user.getXingMing();
        final String userName = user.getUsername();
        return new PasswordDTO(uuid, xingMing, userName);
    }
}
