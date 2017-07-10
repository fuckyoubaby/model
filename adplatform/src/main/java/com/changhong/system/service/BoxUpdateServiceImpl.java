package com.changhong.system.service;

import java.util.List;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changhong.system.domain.box.Box;
import com.changhong.system.repository.BoxUpdateDao;
import com.changhong.system.web.facade.assember.BoxWebAssember;
import com.changhong.system.web.facade.dto.BoxDTO;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-20 
 * Time: 15:28:11
 */
@Service("boxUpdadeService")
public class BoxUpdateServiceImpl implements BoxUpdateService{

	@Autowired
	private BoxUpdateDao boxUpdateDao;

	@Override
	public List<BoxDTO> obtainBoxByCommunityAndPage(String community,
			int startPosition, int size) {
		List<Box> boxes = boxUpdateDao.findBoxByCommunityAndPage(community, startPosition, size);
		return BoxWebAssember.toBoxDTOList(boxes, false);
	}

	@Override
	public List<BoxDTO> obtainBoxByMacPage(String mac, int startPosition,
			int size) {
		List<Box> boxes = boxUpdateDao.findBoxByMacPage(mac, startPosition, size);
		return BoxWebAssember.toBoxDTOList(boxes, true);
	}

	@Override
	public int obtainBoxAmountByCommunity(String community) {
		return boxUpdateDao.findBoxAmountByCommunity(community);
	}

	@Override
	public int obtainBoxAmountByMacPage(String mac) {
		return boxUpdateDao.loadBoxAmountByMac(mac);
	}
	
	
}
