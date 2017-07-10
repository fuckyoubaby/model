package com.changhong.system.web.controller.user;

import com.changhong.common.utils.SecurityUtils;
import com.changhong.common.web.session.SessionKey;
import com.changhong.system.service.UserService;
import com.changhong.system.web.facade.dto.PasswordDTO;
import com.changhong.system.web.facade.dto.UserDTO;
import com.changhong.system.web.paging.UserOverviewPaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:30
 */
@Controller
public class UserManagementController extends AbstractController {

    @Autowired
    private UserService userService;

    /***********************************************用户浏览部分******************************************************/

    @RequestMapping("/backend/usermanagement.html")
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(SessionKey.MENU_KEY, "SETTING_MANAGE");
        model.put(SessionKey.SUB_MENU_KEY, "USER_MANAGE");

        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        String filterName = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "filterName", ""));
        request.setAttribute("current", current);
        request.setAttribute("filterName", filterName);
        UserOverviewPaging paging = new UserOverviewPaging(userService);
        constructPaging(paging, current, filterName);
        List<UserDTO> users = paging.getItems();
        model.put("users", users);
        model.put("paging", paging);
        model.put("currentUser", SecurityUtils.currentUser());

        return new ModelAndView("backend/user/useroverview", model);
    }

    private void constructPaging(UserOverviewPaging paging, int current, String filterName) {
        paging.setCurrentPageNumber(current);
        paging.setFilterName(filterName);
    }

    /***********************************************用户表单模块****************************************************/

   @RequestMapping(value="/backend/userform.html", method= RequestMethod.GET)
    public String setUserForm(HttpServletRequest request, ModelMap model) {
        model.put(SessionKey.MENU_KEY, "SETTING_MANAGE");
        
        String userUuid = ServletRequestUtils.getStringParameter(request, "userUuid", "");
        String current = ServletRequestUtils.getStringParameter(request, "current", "");
        String filterName = ServletRequestUtils.getStringParameter(request, "filterName", "");
        request.setAttribute("current", current);
        request.setAttribute("filterName", filterName);
        
        UserDTO user = null;
        //List<UserDTO> managers = new ArrayList<UserDTO>();
        if (!StringUtils.hasText(userUuid)) {            
            user = new UserDTO();                        
        } else {
            user = userService.obtainUserByUuid(userUuid);
        }
        model.put("user", user);        
        model.put("current", current);  
        model.put("filterName", filterName);

        return "backend/user/userform";
    }

    @RequestMapping(value="/backend/userform.html", method= RequestMethod.POST)
    public String saveUserFrom(HttpServletRequest request, @ModelAttribute("user") UserDTO user,BindingResult errors, ModelMap model) {
        String userUuid = ServletRequestUtils.getStringParameter(request, "userUuid", "");
        String current = ServletRequestUtils.getStringParameter(request, "current", "");    //current返回第几页
        String filterName = ServletRequestUtils.getStringParameter(request, "filterName", "");  //filterName查询
        
        request.setAttribute("current", current);
        request.setAttribute("filterName", filterName);

        user.setUuid(userUuid);
        validateUserForm(request, user, errors);

        if (errors.hasErrors()) {
            model.putAll(errors.getModel());

            model.put("current", current);
            model.put("filterName", filterName);

            return "backend/user/userform";
        }

        userService.changeUserDetails(user);
        return "redirect:usermanagement.html?filterName=" + filterName + "&current=" + current;
    }

    private void validateUserForm(HttpServletRequest request, UserDTO user, BindingResult errors) {
        String userUuid = user.getUuid();
        String xingMing = user.getXingMing();
        String username = user.getUsername();

        if (!StringUtils.hasText(xingMing)) {
            errors.rejectValue("xingMing", "user.xingming.empty");
        }

        if (!StringUtils.hasText(username)) {
            errors.rejectValue("username", "user.username.empty");
        } else {
            boolean exist = userService.obtainUserExist(userUuid, username);    
            if (exist) {
                errors.rejectValue("username", "user.username.exist");
            }
        }
    }

    /***********************************************用户状态*************************************************/

    @RequestMapping("/backend/userstatus.html")
    protected String changeUserStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userUuid = ServletRequestUtils.getStringParameter(request, "userUuid", "");
        String current = ServletRequestUtils.getStringParameter(request, "current", "");
        String fitlerName = ServletRequestUtils.getStringParameter(request, "fitlerName", "");

        userService.changeStatusForUser(userUuid);

        return "redirect:usermanagement.html?fitlerName=" + fitlerName + "&current=" + current;
    }

    /*********************************************修改密码*******************************************************/

    @RequestMapping(value="/backend/passwordform.html", method= RequestMethod.GET)
    public String setPasswordForm(HttpServletRequest request, ModelMap model) {
        String currentUserUuid = SecurityUtils.currectUserUuid();
        PasswordDTO password = userService.obtainUserPassword(currentUserUuid);

        model.put("password", password);
        return "backend/user/passwordform";
    }

    @RequestMapping(value="/backend/passwordform.html", method= RequestMethod.POST)
    public String savePasswordFrom(HttpServletRequest request, @ModelAttribute("password") PasswordDTO password, BindingResult errors, ModelMap model) {
        String userUuid = ServletRequestUtils.getStringParameter(request, "userUuid", "");

        password.setUserUuid(userUuid);           
        validatePasswordForm(password, errors);   

        if (errors.hasErrors()) {
            model.putAll(errors.getModel());
            return "backend/user/passwordform";
        }

        userService.changeUserPassword(password.getUserUuid(), password.getNewPassword());
        return "redirect:dashboard.html";
    }

    private void validatePasswordForm(PasswordDTO password, BindingResult errors) {
        String userUuid = password.getUserUuid();
        String oldPassword = password.getOldPassword();
        String newPassword = password.getNewPassword();
        String newPasswordAgain = password.getNewPasswordAgain();

        if (!StringUtils.hasText(oldPassword)) {
            errors.rejectValue("oldPassword", "user.oldpassword.empty");     
        } else {
            boolean oldPasswordRight = userService.obtainOldPasswordRight(userUuid, oldPassword);  //旧密码是否正确
            if (!oldPasswordRight) {
                errors.rejectValue("oldPassword", "user.oldpassword.notright");
            }
        }
        if (errors.getFieldError("newPasswordAgain") == null) {
            if (!StringUtils.hasText(newPassword) || !StringUtils.hasText(newPasswordAgain)) {
                errors.rejectValue("newPasswordAgain", "user.password.empty");
            } else {
                if (!newPassword.equals(newPasswordAgain)) {
                    errors.rejectValue("newPasswordAgain", "user.password.notsame");
                }
            }
        }
    }
}
