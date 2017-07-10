package com.changhong.system.web.controller.meta;

import com.changhong.common.domain.Option;
import com.changhong.common.web.session.SessionKey;
import com.changhong.system.service.MetaDataService;
import com.changhong.system.web.facade.dto.MetaDataDTO;
import com.changhong.system.web.paging.MetaDataOverviewPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 16-11-21
 * Time: 上午9:10
 */
@Controller
public class MetaDataManagementController {

    @Autowired
    private MetaDataService metaDataService;

    /***********************************************元数据浏览部分******************************************************/

    @RequestMapping("/backend/metadatamanagement.html")
    protected ModelAndView handleMateDataOverview(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(SessionKey.MENU_KEY, "SETTING_MANAGE");
        model.put(SessionKey.SUB_MENU_KEY, "META_MANAGE");

        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        request.setAttribute("current", current);

        MetaDataOverviewPaging paging = new MetaDataOverviewPaging(metaDataService);
        constructPaging(paging, current);
        List<MetaDataDTO> metaDatas = paging.getItems();
        model.put("metaDatas", metaDatas);
        model.put("paging", paging);

        return new ModelAndView("backend/meta/metadataoverview", model);
    }

    private void constructPaging(MetaDataOverviewPaging paging, int current) {
        paging.setCurrentPageNumber(current);
    }

    /***********************************************用户表单模块****************************************************/

   @RequestMapping(value="/backend/metadataform.html", method= RequestMethod.GET)
    public String setMetaDataForm(HttpServletRequest request, ModelMap model) {
        String metaDataUuid = ServletRequestUtils.getStringParameter(request, "metaDataUuid", "");
        String current = ServletRequestUtils.getStringParameter(request, "current", "");
        request.setAttribute("current", current);

        MetaDataDTO metaData = null;
        if (!StringUtils.hasText(metaDataUuid)) {
            metaData = new MetaDataDTO();
        } else {
            metaData = metaDataService.obtainMetaUuidByUuid(metaDataUuid);
        }
        model.put("metaData", metaData);
        model.put("current", current);

       return "backend/meta/metadataform";
    }

    @RequestMapping(value="/backend/metadataform.html", method= RequestMethod.POST)
    public String saveUserFrom(HttpServletRequest request, @ModelAttribute("metaData") MetaDataDTO metaData, BindingResult errors, ModelMap model) {
        String metaDataUuid = ServletRequestUtils.getStringParameter(request, "metaDataUuid", "");
        String current = ServletRequestUtils.getStringParameter(request, "current", "");
        request.setAttribute("current", current);
        request.setAttribute("metaDataUuid", metaDataUuid);

        metaData.setUuid(metaDataUuid);
        validateUserForm(request, metaData, errors);

        if (errors.hasErrors()) {
            model.putAll(errors.getModel());
            model.put("current", current);

            return "backend/meta/metadataform";
        }

        metaDataService.changeMetaDataDetails(metaData);
        return "redirect:metadatamanagement.html?current=" + current;
    }

    private void validateUserForm(HttpServletRequest request, MetaDataDTO metaData, BindingResult errors) {
        String startTime = metaData.getStartTime();
        String endTime = metaData.getEndTime();
        String heartInternal = metaData.getHeartInterval();

        if (!StringUtils.hasText(startTime)) {
            errors.rejectValue("startTime", "meta.start.time.empty");
        }
        if (!StringUtils.hasText(endTime)) {
            errors.rejectValue("endTime", "meta.end.time.empty");
        }
        if (!StringUtils.hasText(heartInternal)) {
            errors.rejectValue("heartInternal", "meta.heart.internal.empty");
        }
    }
}
