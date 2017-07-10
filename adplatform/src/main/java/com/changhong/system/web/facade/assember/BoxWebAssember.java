package com.changhong.system.web.facade.assember;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.common.utils.CHDateUtils;
import com.changhong.system.domain.box.Box;
import com.changhong.system.domain.box.Community;
import com.changhong.system.web.facade.dto.BoxDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:58
 */
public class BoxWebAssember {

    public static Box toBoxDomain(BoxDTO dto) {
        Box box = null;
        if(dto == null) return null;
        String mac = dto.getMac();
        String ssidName = buildSsidName(mac);
        String ssidPass = buildSsidPass(mac);
        
        if (StringUtils.hasText(dto.getUuid())) {
            box = (Box) EntityLoadHolder.getUserDao().findByUuid(dto.getUuid(), Box.class);
            box.setMac(dto.getMac());
            box.setNote(dto.getNote());
           
            //ssid为空时，设置为预定值
            if(!StringUtils.hasText(dto.getSsidName())){
            	box.setSsidName(ssidName);
            	box.setSsidPassword(ssidPass);
            }else{
            	//ssid不为预定值时，重置ssid和密码
            	if(!dto.getSsidName().equals(ssidName) || !dto.getSsidPassword().equals(ssidPass)){
            		box.setSsidName(ssidName);
            		box.setSsidPassword(ssidPass);
            	}
            }
        } else {
            box = new Box(dto.getMac(), dto.getNote(), ssidName, ssidPass);
            Community community = (Community) EntityLoadHolder.getUserDao().findByUuid(dto.getCommunityUuid(), Community.class);
            box.setCommunity(community);
        }

        return box;
    }

    public static BoxDTO toBoxDTO(Box box, boolean loadArea) {
        if (box == null) {
            return null;
        }
        final String uuid = box.getUuid();
        final String timestamp = CHDateUtils.getFullDateFormat(box.getTimestamp());
        final String mac = box.getMac();
        final String ssidName = box.getSsidName();
        final String ssidPassword = box.getSsidPassword();
        final String note = box.getNote();
        final String version = box.getFirmwareVersion();
        final String lastReportTime = CHDateUtils.getFullDateFormat(box.getLastReportTime());
        final boolean isOnline = box.isOnline();
        final int cpuRate = box.getCpuRate();
        final int memRate = box.getMemRate();
        final int diskRate = box.getDiskRate();

        Community community = box.getCommunity();
        final String communityUuid = community.getUuid();

        BoxDTO dto = new BoxDTO(uuid, timestamp, mac, ssidName, ssidPassword, note, version, lastReportTime, isOnline,
                cpuRate, memRate, diskRate, communityUuid);

        if (loadArea) {
            dto.setCommunityPath(community.getArea().getFullAreaPath() + "/" + community.getName());
        }
        return dto;
    }

    public static List<BoxDTO> toBoxDTOList(List<Box> boxes, boolean loadArea) {
        List<BoxDTO> dtos = new ArrayList<BoxDTO>();
        if (boxes != null) {
            for (Box loop : boxes) {
                dtos.add(toBoxDTO(loop, loadArea));
            }
        }
        return dtos;
    }
    
    private static String buildSsidName(String mac){
    	String targetStr = mac.replaceAll(":", "").toLowerCase();
    	return "chad_"+targetStr.substring(targetStr.length()-6);
    }
    
    private static String buildSsidPass(String mac){
    	String targetStr = mac.replaceAll(":", "").toLowerCase();
    	return "chpwd"+targetStr.substring(0,3);
    }
    
}
