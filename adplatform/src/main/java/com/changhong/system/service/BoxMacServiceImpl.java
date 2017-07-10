package com.changhong.system.service;

import java.util.List;

import javax.crypto.MacSpi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changhong.common.domain.ResultObject;
import com.changhong.common.utils.CHListUtils;
import com.changhong.common.utils.CHLogUtils;
import com.changhong.system.domain.box.BoxMac;
import com.changhong.system.domain.box.BoxMacStatus;
import com.changhong.system.repository.BoxInfoDao;
import com.changhong.system.repository.BoxMacDao;
import com.changhong.system.web.facade.assember.BoxMacWebAssember;
import com.changhong.system.web.facade.dto.BoxMacDTO;
/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-3-16 
 * Time: 10:31:24
 *
 */

@Service("boxMacService")
public class BoxMacServiceImpl implements BoxMacService {

	@Autowired
	private BoxMacDao boxMacDao;
	
	@Autowired
	private BoxInfoDao boxInfoDao;
	
	@Override
	public BoxMacDTO obtainBoxMacDTO(String uuid) {
		BoxMac boxmac = (BoxMac) boxMacDao.findByUuid(uuid, BoxMac.class);
		return BoxMacWebAssember.toDTO(boxmac);
	}

	@Override
	public void saveBoxMacDTO(BoxMacDTO dto) {
		BoxMac boxmac = BoxMacWebAssember.toDomain(dto);
		if(boxmac!=null){
			boxMacDao.saveOrUpdate(boxmac);
			CHLogUtils.doLog(BoxMacServiceImpl.class, "add new box mac "+boxmac.getMac()+" into database", "添加新的MAC"+boxmac.getMac()+"到数据库");
		}
	}

	@Override
	public void saveBoxMac(BoxMac boxMac) {
		if(boxMac!=null){
			boxMacDao.saveOrUpdate(boxMac);
			CHLogUtils.doLog(BoxMacServiceImpl.class, "add or update box mac "+boxMac.getMac(), "添加或修改的MAC"+boxMac.getMac());
		}
	}

	@Override
	public boolean obtainMacExist(String mac) {
		
		return obtainMacExist(mac, null);
	}

	@Override
	public boolean obtainMacExist(String mac, String uuid) {
		return boxMacDao.findBoxMacDuplicate(mac,uuid);
	}

	@Override
	public List<BoxMacDTO> obtainBoxMacs(String filterName, int startPosition,
			int pageSize) {
		List<BoxMac> boxmacs = boxMacDao.findMacs(filterName, startPosition, pageSize);
		
		return BoxMacWebAssember.toDTOList(boxmacs);
	}

	@Override
	public int obtainBoxMacs(String filterName) {
		return boxMacDao.findMacsAmount(filterName);
	}

	@Override
	public void changeStatus(String boxmacUuid, BoxMacStatus bDisable) {
		BoxMac boxmac = (BoxMac) boxMacDao.findByUuid(boxmacUuid, BoxMac.class);
		if(boxmac!=null && bDisable!=null){
			BoxMacStatus t = boxmac.getMacStatus();
			boxmac.setMacStatus(bDisable);
			CHLogUtils.doLog(BoxMacServiceImpl.class, "add new box mac "+boxmac.getMac()+" into database", "添加新的MAC"+boxmac.getMac()+"到数据库");
		}
	}

	@Override
	public ResultObject obtainMacCanDeleted(String boxmacUuid) {
		BoxMac mac = (BoxMac) boxMacDao.findByUuid(boxmacUuid, BoxMac.class);
		if(mac!=null){
			String macnumber = mac.getMac();
			boolean exisit = boxInfoDao.findBoxMacDuplicate(null, macnumber);
			
			return new ResultObject(!exisit, exisit?"无法删除正使用的MAC":"可删除此MAC");
		}
		return new ResultObject(false, "无效的ID");
	}

	@Override
	public void deleteByUuid(String boxmacUuid) {
		BoxMac mac = (BoxMac) boxMacDao.findByUuid(boxmacUuid, BoxMac.class);
		if(mac!=null){
			boxMacDao.delete(mac);
			CHLogUtils.doLog(BoxMacServiceImpl.class, "delete box mac "+mac.getMac(), "删除设备MAC"+mac.getMac());
		}
	}

	@Override
	public void saveBoxMacs(List<BoxMac> boxmacs) {	
		if(CHListUtils.hasElement(boxmacs)){
			boxMacDao.saveAll(boxmacs);
			CHLogUtils.doLog(BoxMacServiceImpl.class, "batch import "+boxmacs.size()+" box macs", "批量导入"+boxmacs.size()+"条设备MAC");
		}
	}

	@Override
	public int deleteBatchImport() {
		return boxMacDao.deleteDuplicateMacs();
	}

	@Override
	public List<BoxMac> obtainEnableMac(String keyword, int startPosition,
			int pageSize) {
		return boxMacDao.findMacs(keyword, BoxMacStatus.B_INITE.name(), startPosition, pageSize, "mac");
	}

	@Override
	public BoxMac obtainByMac(String mac) {
		return boxMacDao.findByMac(mac);
	}

}
