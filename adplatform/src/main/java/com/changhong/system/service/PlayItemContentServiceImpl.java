package com.changhong.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changhong.common.utils.CHLogUtils;
import com.changhong.system.domain.advertisment.PlayItemContent;
import com.changhong.system.repository.PlayItemContentDao;
import com.changhong.system.web.facade.assember.PlayItemContentWebAssember;
import com.changhong.system.web.facade.dto.PlayItemContentDTO;
/**
 * 
 * Author: Guo xiaoyang 
 * Date: 2017-1-17 
 * Time: 17:52:21
 */
@Service("playItemContentService")
public class PlayItemContentServiceImpl implements PlayItemContentService {

	@Autowired
	private PlayItemContentDao playItemContentDao;
	
	@Override
	public List<PlayItemContentDTO> obtainContentByItemUuid(String itemUuid) {
		List<PlayItemContent> lists = playItemContentDao.loadByItemid(itemUuid);
		return PlayItemContentWebAssember.toDTOList(lists);
	}

	@Override
	public String saveDTO(PlayItemContentDTO dto) {
		PlayItemContent pic = PlayItemContentWebAssember.toDomain(dto);
		if(pic!=null){
			playItemContentDao.saveOrUpdate(pic);
			CHLogUtils.doLog(PlayItemContentServiceImpl.class, "add new play item content "+pic.getUuid()+" to play item "+dto.getPlayItemUuid(), "资源"+dto.getResourceUuid()+"添加到广告"+dto.getPlayItemUuid());
			return pic.getUuid();
		}else return null;
	}

	@Override
	public void deleteByUuid(String uuid) {
		PlayItemContent pic = (PlayItemContent) playItemContentDao.findByUuid(uuid, PlayItemContent.class);
		if(pic!=null){
			playItemContentDao.delete(pic);
			if(pic.getPlayItem()!=null){
				CHLogUtils.doLog(PlayItemContentServiceImpl.class, "delete play item content "+uuid+" from playItem "+pic.getPlayItem().getUuid(), "广告"+pic.getPlayItem().getName()+"移除资源"+pic.getResourceUuid());
			}else{
				CHLogUtils.doLog(PlayItemContentServiceImpl.class, "delete play item content "+uuid+" from playItem null ", "播放广告移除资源"+pic.getResourceUuid());
			}
		}
	}

	@Override
	public PlayItemContentDTO obtainItemContentDTOByUuid(String uuid) {
		PlayItemContent pic = (PlayItemContent)playItemContentDao.findByUuid(uuid, PlayItemContent.class);
		return PlayItemContentWebAssember.toDTO(pic);
	}

	@Override
	public PlayItemContent obtainItemContentByUuid(String uuid) {
		return (PlayItemContent)playItemContentDao.findByUuid(uuid, PlayItemContent.class);
	}

	@Override
	public void changeIndexByItemUuid(String itemUuid, int index) {
		
		playItemContentDao.changeIndexByItemUuid(itemUuid, index);
		CHLogUtils.doLog(PlayItemContentServiceImpl.class, "update play item content index > "+index+" in playItem "+itemUuid, "广告"+itemUuid+"更新资源的播放序列");
	}

	@Override
	public void changeIndexByUuid(String uuid, int index) {
		
		playItemContentDao.changeIndexByUuid(uuid,index);
		CHLogUtils.doLog(PlayItemContentServiceImpl.class, "update play item content "+uuid+" index ", "更新广告资源"+uuid+"的播放序列");
	}

	@Override
	public void updatePlayConfigInfo(String uuid, int repeat, double duration) {
		PlayItemContent pic = (PlayItemContent) playItemContentDao.findByUuid(uuid, PlayItemContent.class);
		if(pic!=null){
			if(repeat==-1){
				pic.setRepeat(null);
			}else{
				pic.setRepeat(repeat);
			}
			
			if(duration==-1){
				pic.setDuration(null);
			}else{
				pic.setDuration(duration);
			}
			PlayItemContentDTO dto = PlayItemContentWebAssember.toDTO(pic);
			if(pic.getPlayItem() !=null){
				if(dto.getAdverResourceDTO()!=null){
					CHLogUtils.doLog(PlayItemContentServiceImpl.class, "update play item content "+uuid+" in playItem "+pic.getPlayItem().getUuid(), "广告"+pic.getPlayItem().getName()+"更新资源"+dto.getAdverResourceDTO().getName()+"的播放参数");
				}else{
					CHLogUtils.doLog(PlayItemContentServiceImpl.class, "update play item content "+uuid+" in playItem "+pic.getPlayItem().getUuid(), "广告"+pic.getPlayItem().getName()+"更新资源"+dto.getResourceUuid()+"的播放参数");
				}
			}
		}
	}

}
