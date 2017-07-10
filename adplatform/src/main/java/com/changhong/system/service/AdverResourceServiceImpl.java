package com.changhong.system.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changhong.common.domain.RecordeResourceInfo;
import com.changhong.common.thread.ApplicationThreadPool;
import com.changhong.common.utils.CHLogUtils;
import com.changhong.common.utils.SecurityUtils;
import com.changhong.system.domain.advertisment.AdverResource;
import com.changhong.system.domain.log.ActionModule;
import com.changhong.system.domain.log.ActionType;
import com.changhong.system.domain.user.User;
import com.changhong.system.log.ApplicationLog;
import com.changhong.system.repository.AdverResourceDao;
import com.changhong.system.web.event.ActionLogEvent;
import com.changhong.system.web.facade.assember.AdverResourceWebAssember;
import com.changhong.system.web.facade.dto.AdverResourceDTO;
/**
 * 
 * Author: Guo xiaoyang
 * Date: 2016-12-9
 * Time: 10:18:52
 *
 */
@Service("adverResourceService")
public class AdverResourceServiceImpl implements AdverResourceService{

	@Autowired
	private AdverResourceDao adverResourceDao;
	
	
	
	@Override
	public void saveAdverResource(AdverResource ar) {
		if(ar!=null){
			adverResourceDao.saveOrUpdate(ar);
			CHLogUtils.doLog(AdverResourceServiceImpl.class, "save the adver resource "+ar.getUuid(), "添加["+ar.getName()+"]新广告资源");
		}
	}
	
	
	@Override
	public void saveAdverResourceDTO(AdverResourceDTO dto) {
		AdverResource ar = AdverResourceWebAssember.toDomain(dto);
		if(ar!=null){
			adverResourceDao.saveOrUpdate(ar);
			//日志
			CHLogUtils.doLog(AdverResourceServiceImpl.class,  "save the adver resource "+ar.getUuid(),"添加["+dto.getName()+"]新广告资源" );
//			ApplicationLog.infoWithCurrentUser(AdverResourceServiceImpl.class, "save the adver resource "+ar.getUuid());
//			User current = SecurityUtils.currentUser();
//			ActionLogEvent event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_AD, "添加"+ar.getName()+"新广告资源");
//			ApplicationThreadPool.executeThread(event);
		}
	}

	@Override
	public void deleteByUUID(String uuid) {
		AdverResource ar = (AdverResource) adverResourceDao.findByUuid(uuid, AdverResource.class);
		adverResourceDao.delete(ar);
		CHLogUtils.doLog(AdverResourceServiceImpl.class,  "delete the advertisment resource "+ar.getUuid(),"删除["+ar.getName()+"] 广告资源");
//		ApplicationLog.infoWithCurrentUser(AdverResourceServiceImpl.class, "delete the advertisment resource "+ar.getUuid());
//		User current = SecurityUtils.currentUser();
//		ActionLogEvent event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_AD, "删除["+ar.getName()+"] 广告资源");
//		ApplicationThreadPool.executeThread(event);
	}

	@Override
	public void updateAdverResourceDTO(AdverResourceDTO dto) {
		adverResourceDao.persist(AdverResourceWebAssember.toDomain(dto));
		CHLogUtils.doLog(AdverResourceServiceImpl.class, "update the advertisment resource "+dto.getUuid(),"修改["+dto.getName()+"] 广告资源");
	}

	@Override
	public AdverResourceDTO obtainDTOByUuid(String uuid) {
		AdverResource ar = (AdverResource) adverResourceDao.findByUuid(uuid, AdverResource.class);
		return AdverResourceWebAssember.toDTO(ar);
	}

	@Override
	public boolean obtainAdverResourceExist(String uuid, String name) {
		return adverResourceDao.obtainNameExist(uuid, name);
	}

	@Override
	public List<AdverResourceDTO> obtainAdverResources(String filterName,
			int startPosition, int pageSize) {
		List<AdverResource> lists = adverResourceDao.loadAdverResources(filterName, startPosition, pageSize); 
		return AdverResourceWebAssember.toDTOList(lists);
	}

	@Override
	public int obtainAdverResourceAmount(String filterName) {
		return adverResourceDao.loadAdverResourceSize(filterName);
	}

	@Override
	public void changeStatus(String uuid) {
		AdverResource ar = (AdverResource) adverResourceDao.findByUuid(uuid, AdverResource.class);
		ar.setStatus(!ar.isStatus());
	}

	@Override
	public List<AdverResourceDTO> obtainEnableAdverResources(String filterName,
			int startPosition, int pageSize) {
		List<AdverResource> lists = adverResourceDao.loadEnableAdverResources(filterName, startPosition, pageSize); 
		return AdverResourceWebAssember.toDTOList(lists);
	}

	@Override
	public int obtainEnableAdverResourceAmount(String filterName) {
		return adverResourceDao.loadEnableAdverResourceSize(filterName);
	}


	@Override
	public List<RecordeResourceInfo> obtainRecordResourceInfo() {
		
		List infos = adverResourceDao.loadRecordResourceInfo();
		List<RecordeResourceInfo> resourceInfos = new ArrayList<RecordeResourceInfo>();
		Iterator it = infos.iterator();
		while(it.hasNext()){
			Map map = (Map)it.next();
			RecordeResourceInfo rri = new RecordeResourceInfo((long) map.get("amount"), new BigDecimal((double)map.get("size")));
			resourceInfos.add(rri);
		}
		return resourceInfos;
	}


	@Override
	public List<String> obtainFileNamesByPath(String keyword) {
		return adverResourceDao.loadFileNamesByPath(keyword);
	}


	@Override
	public double obtainRecordPathSize(List<String> garbageRecordes) {
		if(garbageRecordes!=null && garbageRecordes.size()>0){
			return adverResourceDao.loadRecordSize(garbageRecordes);
		}
		return 0;
	}


	@Override
	public int deleteByGarbageRecordes(List<String> garbageRecordes) {
		if(garbageRecordes!=null && garbageRecordes.size()>0 ){
			int r =  adverResourceDao.deleteByRecords(garbageRecordes);
			CHLogUtils.doLog(AdverResourceServiceImpl.class, "delete the trashed advertisment resource ","删除"+r+"个无效广告资源");
			return r;
		}
		return 0;
	}


	@Override
	public AdverResource obtainAdverResourceByUuid(String uuid) {
		
		return (AdverResource) adverResourceDao.findByUuid(uuid, AdverResource.class);
	}
	
	
}
