package com.changhong.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.changhong.common.utils.CHLogUtils;
import com.changhong.system.domain.advertisment.PlayContent;
import com.changhong.system.domain.advertisment.PlayItem;
import com.changhong.system.repository.PlayContentDao;
import com.changhong.system.repository.PlayItemDao;
import com.changhong.system.web.facade.assember.PlayContentWebAssember;
import com.changhong.system.web.facade.assember.PlayItemWebAssember;
import com.changhong.system.web.facade.dto.PlayContentDTO;
import com.changhong.system.web.facade.dto.PlayItemDTO;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-13 
 * Time: 10:14:00
 *
 */
@Service("playContentService")
public class PlayContentServiceImpl implements PlayContentService{
	
	@Autowired
	private PlayContentDao playContentDao;
	
	@Autowired
	private PlayItemDao playItemDao;
	
	@Override
	public void savePlayContent(PlayContent pc) {
		if(pc!=null){
			playContentDao.saveOrUpdate(pc);
			CHLogUtils.doLog(PlayContentServiceImpl.class, "save new play content "+pc.getUuid(), "保存播放内容["+pc.getName()+"]");
		}
	}

	@Override
	public void savePlayContentDTO(PlayContentDTO dto) {
		PlayContent pc = PlayContentWebAssember.toDomain(dto);
		if(pc!=null){
			playContentDao.saveOrUpdate(pc);
			CHLogUtils.doLog(PlayContentServiceImpl.class, "save new play content "+pc.getUuid(), "保存播放内容["+pc.getName()+"]");
		}
	}

	@Override
	public void deleteByUUID(String uuid) {
		PlayContent pc = (PlayContent) playContentDao.findByUuid(uuid, PlayContent.class);
		if(pc!=null){
			playContentDao.delete(pc);
			CHLogUtils.doLog(PlayContentServiceImpl.class, "delete play content "+uuid, "保存播放内容["+pc.getName()+"]");
		}
	}

	@Override
	public void updateDTO(PlayContentDTO dto) {
		playContentDao.persist(PlayContentWebAssember.toDomain(dto));
		CHLogUtils.doLog(PlayContentServiceImpl.class, "update play content "+dto.getUuid(), "更新播放内容["+dto.getName()+"]");
	}

	@Override
	public PlayContentDTO obtainDTOByUUID(String uuid) {
		PlayContent pc = (PlayContent) playContentDao.findByUuid(uuid, PlayContent.class);
		
		return PlayContentWebAssember.toDTO(pc);
	}

	@Override
	public boolean obtainNameExists(String uuid, String name) {
		if(StringUtils.hasText(uuid)){
			return playContentDao.obtainNameExists(uuid, name);
		}else{
			return playContentDao.obtainNameExists(name);
		}
	}

	@Override
	public List<PlayContentDTO> obtainPlayContentDTOs(String filterName,
			int startPosition, int size) {
		List<PlayContent> lists = playContentDao.loadPlayContents(filterName, startPosition, size);
		return PlayContentWebAssember.toDTOList(lists);
	}

	@Override
	public int obtainAmount(String filterName) {
		return playContentDao.loadAmount(filterName);
	}

	@Override
	public List<PlayItemDTO> obtainPlayItemByUuid(String uuid) {
		List<PlayItem> items = playItemDao.loadByContentId(uuid);
		return PlayItemWebAssember.toDTOList(items);
	}

	@Override
	public PlayContentDTO obtainDTOByUpgradeId(String uuid) {
		PlayContent pc = playContentDao.loadByUpgradeId(uuid);
		return PlayContentWebAssember.toDTO(pc);
	}

	@Override
	public List<PlayContentDTO> obtainEnablePlayContentDTOs(String keyword,
			Integer indexNo, Integer pageSize) {
		List<PlayContent> lists = playContentDao.loadEnablePlayContents(keyword, indexNo, pageSize);
		return PlayContentWebAssember.toDTOList(lists);
	}

	@Override
	public int obtainEnablePlayContentDTOsAmount(String keyword) {
		return playContentDao.loadEnableAmount(keyword);
	}

	@Override
	public List<PlayContentDTO> obtainPlayContentDTOsByContentResourceId(
			String contentResourceId) {
		List<PlayContent> lists = playContentDao.loadPlayContentsByContentResourceId(contentResourceId);
		return PlayContentWebAssember.toDTOList(lists);
	}

}
