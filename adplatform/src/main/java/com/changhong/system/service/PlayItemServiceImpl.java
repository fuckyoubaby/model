package com.changhong.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changhong.common.utils.CHLogUtils;
import com.changhong.system.domain.advertisment.PlayItem;
import com.changhong.system.repository.PlayItemDao;
import com.changhong.system.web.facade.assember.PlayItemWebAssember;
import com.changhong.system.web.facade.dto.PlayItemDTO;
/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-16 
 * Time: 11:14:18
 */
@Service("playItemService")
public class PlayItemServiceImpl implements PlayItemService {

	@Autowired
	private PlayItemDao playItemDao;
	
	@Override
	public List<PlayItemDTO> obtainEnableItems(String keyword,
			int startPosition, int size) {
		List<PlayItem> lists = playItemDao.loadEnableByKeyword(keyword, startPosition, size);
		return PlayItemWebAssember.toDTOList(lists);
	}

	@Override
	public int obtainEnableItemsAmount(String keyword) {
		return playItemDao.loadEnableBykeyword(keyword);
	}

	@Override
	public PlayItemDTO otainByUuid(String uuid) {
		return PlayItemWebAssember.toDTO((PlayItem)playItemDao.findByUuid(uuid, PlayItem.class));
	}

	@Override
	public boolean obtainNameExists(String uuid, String name) {
		return playItemDao.obtainNameExists(uuid, name);
	}

	@Override
	public void saveDTO(PlayItemDTO playItemDTO) {
		PlayItem pi = PlayItemWebAssember.toDomain(playItemDTO);
		if(pi!=null){
			playItemDao.saveOrUpdate(pi);
			CHLogUtils.doLog(PlayItemServiceImpl.class, "add new play item "+pi.getUuid(),"新增广告"+playItemDTO.getName());
		}
	}

	@Override
	public void updateDTO(PlayItemDTO playItemDTO) {
		PlayItem pi = PlayItemWebAssember.toDomain(playItemDTO);
		playItemDao.persist(pi);
		CHLogUtils.doLog(PlayItemServiceImpl.class, "update play item "+playItemDTO.getUuid(),"更新广告"+playItemDTO.getName()+"信息");
	}

	@Override
	public void changeIndex(String preUuid, int preIndex) {
		preIndex=preIndex<=0?1:preIndex;
		PlayItem playItem = (PlayItem) playItemDao.findByUuid(preUuid, PlayItem.class);
		playItem.setIndex(preIndex);
		CHLogUtils.doLog(PlayItemServiceImpl.class, "update play item "+preUuid+" index","更新广告"+playItem.getName()+"播放次序为"+preIndex);
	}

	@Override
	public void cancelNull(String uuid) {
		PlayItem playItem = (PlayItem) playItemDao.findByUuid(uuid, PlayItem.class);
		
		int index = playItem.getIndex();
		String contentUuid = playItem.getPlayContent().getUuid();
		playItem.setIndex(null);
		playItem.setPlayContent(null);
		playItem.setEndDate(null);
		playItem.setRepeat(null);
		playItem.setStartDate(null);
		
		playItemDao.resortItem(contentUuid, index);
		if(playItem.getPlayContent()!=null){
			CHLogUtils.doLog(PlayItemServiceImpl.class, "delete Play item "+uuid+" index from play content"+contentUuid,"删除播放内容"+playItem.getPlayContent().getName()+"的广告"+playItem.getName());
		}else{
			CHLogUtils.doLog(PlayItemServiceImpl.class, "delete Play item "+uuid+" index from play content"+contentUuid,"删除播放内容"+contentUuid+"的广告"+playItem.getName());
		}
	}

	@Override
	public void deleteItem(String uuid) {
		PlayItem pi = (PlayItem) playItemDao.findByUuid(uuid, PlayItem.class);
		playItemDao.delete(pi);
		CHLogUtils.doLog(PlayItemServiceImpl.class, "delete play item "+pi.getUuid(),"删除广告"+pi.getName());
	}

	

}
