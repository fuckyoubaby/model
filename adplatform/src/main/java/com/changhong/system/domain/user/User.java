package com.changhong.system.domain.user;

import com.changhong.common.domain.EntityBase;
import com.changhong.common.thread.ApplicationThreadPool;
import com.changhong.common.utils.JodaUtils;
import com.changhong.common.utils.SecurityUtils;
import com.changhong.system.domain.log.ActionModule;
import com.changhong.system.domain.log.ActionType;
import com.changhong.system.log.ApplicationLog;
import com.changhong.system.web.event.ActionLogEvent;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:15
 */
public class User extends EntityBase implements UserDetails {

    private final static int CODE_VALIDATE_TIME_HOUR = 1;

    private String xingMing;
    private String contactWay;

    private String username;
    private String password;
    private boolean enabled = true;

    private String code;
    private DateTime codeDate;

    public User() {
    }

    public User(String xingMing, String contactWay, String username, String password) {
        this.xingMing = xingMing;
        this.contactWay = contactWay;
        this.username = username;
        this.password = password;
        this.enabled = true;
    }

    public void updateActiviteInfo(String code) {
        setCodeDate(JodaUtils.currentTime());
        setCode(code);
    }

    public boolean isActiviteInfoRight(String code) {
        DateTime current = JodaUtils.currentTime();
        if (current.minusHours(CODE_VALIDATE_TIME_HOUR).compareTo(codeDate) <= 0 && code.equals(this.code)) {
            return true;
        }
        return false;
    }

    /*************************************************GETTER**********************************************************/

    public String getXingMing() {
        return xingMing;
    }

    public void setXingMing(String xingMing) {
        this.xingMing = xingMing;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DateTime getCodeDate() {
        return codeDate;
    }

    public void setCodeDate(DateTime codeDate) {
        this.codeDate = codeDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        if ("chadadmin".equals(username)) {
            Role role = new Role("ROLE_ADMIN");
            grantedAuthorities.add(role);
        } else {
            Role role = new Role("ROLE_USER");
            grantedAuthorities.add(role);
        }

         //日志部分
        ApplicationLog.infoWithCurrentUser(User.class, getUuid() + " login the system");
        ActionLogEvent event = new ActionLogEvent(getUuid(), getXingMing(), ActionType.A_LOG, ActionModule.A_LOGIN, "登录系统");
        ApplicationThreadPool.executeThread(event);

        return grantedAuthorities;
    }

    //不要改动这个方法
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (username != null ? !username.equals(user.username) : user.username != null) return false;

        return true;
    }

    //不要改动这个方法
    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }

    public static void main(String[] args) {
        Map<User, String> model = new HashMap<>();
        User user1 = new User();
        user1.setUuid("1");
        
        User user2 = new User();
        user2.setUuid("2");

        model.put(user1, "1");
        String s = model.get(user2);
        System.out.println(s);
    }
}