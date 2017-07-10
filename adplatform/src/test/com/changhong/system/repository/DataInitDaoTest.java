package com.changhong.system.repository;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.changhong.common.utils.CHStringUtils;
import com.changhong.system.domain.box.Box;
import com.changhong.system.domain.box.BoxMac;
import com.changhong.system.domain.box.BoxMacStatus;
import com.changhong.system.domain.box.Community;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.changhong.system.domain.advertisment.PlayContent;
import com.changhong.system.domain.upgrade.ContentUpgradeArea;


/**
 * 
 * @author shijinxiang
 * 2017年2月14日
 * 上午10:30:37
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"/database.xml", "/applicationContext.xml"})
public class DataInitDaoTest {

    @Autowired
    BoxInfoDao boxInfoDao;

     @Resource
     SessionFactory sessionFactory;

    HibernateTemplate hibernateTemplate;

    @Before
    public void setUp() {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    @After
    public void tearDown() {
        hibernateTemplate = null;
    }

	@Test
	public void testInsertBoxMac() {
        List<BoxMac> macs = new ArrayList<>();

        for (int i = 1; i <= 70000; i++) {
            BoxMac mac = new BoxMac();
            mac.setMacStatus(BoxMacStatus.B_INITE);
            mac.setMac(CHStringUtils.getRandomString(2).toUpperCase() + ":" + CHStringUtils.getRandomString(2).toUpperCase() + ":" + CHStringUtils.getRandomString(2).toUpperCase() + ":" + CHStringUtils.getRandomString(2).toUpperCase() + ":"+ CHStringUtils.getRandomString(2).toUpperCase() + ":" + CHStringUtils.getRandomString(2).toUpperCase());
            macs.add(mac);

            if (i % 1000 == 0) {
                boxInfoDao.saveAll(macs);
                macs.clear();
                System.out.println("finish number " + i);
            }
        }
    }

    @Test
	public void testInsertBoxInfo() throws InterruptedException {
        List<Community> communities = hibernateTemplate.find("from Community");
        List<Box> boxes = new ArrayList<>();

        for (int i = 1; i <= 100000; i++) {
            Box box = new Box();
            box.setSsidName("name");
            box.setSsidPassword("pass");
            box.setFirmwareVersion("1.0");
            box.setMac(CHStringUtils.getRandomString(2).toUpperCase() + ":" + CHStringUtils.getRandomString(2).toUpperCase() + ":" + CHStringUtils.getRandomString(2).toUpperCase() + ":" + CHStringUtils.getRandomString(2).toUpperCase() + ":" + CHStringUtils.getRandomString(2).toUpperCase() + ":" + CHStringUtils.getRandomString(2).toUpperCase());
            box.setCommunity(communities.get(Integer.valueOf(CHStringUtils.getRandomNumber(1))));
            boxes.add(box);

            if (i % 2 == 0) {
                boxInfoDao.saveAll(boxes);
                boxes.clear();
                System.out.println("finish number " + i);
            }
        }
    }
}
