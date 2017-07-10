package com.changhong.common.repository;

import com.changhong.system.repository.ContentUpgradeAreaDao;
import com.changhong.system.repository.ContentUpgradeBoxDao;
import com.changhong.system.repository.ContentUpgradeCommunityDao;
import com.changhong.system.repository.MetaDao;
import com.changhong.system.repository.UserDao;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 下午1:21
 */
public class EntityLoadHolder {

    private static UserDao userDao;

    private static MetaDao metaDao;
    
    private static ContentUpgradeBoxDao contentUpgradeBoxDao;
    
    private static ContentUpgradeCommunityDao contentUpgradeCommunityDao;
    
    private static ContentUpgradeAreaDao contentUpgradeAreaDao;
    

    public static UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
    	EntityLoadHolder.userDao = userDao;
    }

    public static MetaDao getMetaDao() {
        return metaDao;
    }

    public void setMetaDao(MetaDao metaDao) {
    	EntityLoadHolder.metaDao = metaDao;
    }

	public static ContentUpgradeBoxDao getContentUpgradeBoxDao() {
		return contentUpgradeBoxDao;
	}

	public void setContentUpgradeBoxDao(ContentUpgradeBoxDao contentUpgradeBoxDao) {
		EntityLoadHolder.contentUpgradeBoxDao = contentUpgradeBoxDao;
	}

	public static ContentUpgradeCommunityDao getContentUpgradeCommunityDao() {
		return contentUpgradeCommunityDao;
	}

	public  void setContentUpgradeCommunityDao(
			ContentUpgradeCommunityDao contentUpgradeCommunityDao) {
		EntityLoadHolder.contentUpgradeCommunityDao = contentUpgradeCommunityDao;
	}

	public static ContentUpgradeAreaDao getContentUpgradeAreaDao() {
		return contentUpgradeAreaDao;
	}

	public void setContentUpgradeAreaDao(ContentUpgradeAreaDao contentUpgradeAreaDao) {
		EntityLoadHolder.contentUpgradeAreaDao = contentUpgradeAreaDao;
	}
}
