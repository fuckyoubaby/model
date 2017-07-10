package com.changhong.system.domain.box;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.changhong.common.domain.EntityBase;
import com.changhong.common.utils.CHListUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: pengjie
 * Date: 2016/11/21
 * Time: 10:53
 */
public class Area extends EntityBase {

    private String name;

    private Area parentArea;

    private List<Area> childAreas;

    private List<Community> communities;

    public boolean isLeaf() {
        if (!CHListUtils.hasElement(childAreas)) {
            return true;
        }
        return false;
    }

    public List getAllAreaAbove() {
        List<Area> all = new ArrayList<Area>();
        Area parent = this.getParentArea();
        if (parent != null) {
            all.addAll(parent.getAllAreaAbove());
            all.add(parent);
        }
        return all;
    }

    public List<Area> getAllSitesAboveWithItself() {
        List<Area> all = new ArrayList<Area>();
        Area parent = this.getParentArea();
        if (parent != null) {
            all.addAll(parent.getAllAreaAbove());
            all.add(parent);
        }
        all.add(this);
        return all;
    }

    public List<Area> getAllSitesBelow() {
		List<Area> all = new ArrayList<Area>();
		List<Area> children = this.getChildAreas();
		if (children != null) {
			all.addAll(children);
			Iterator i = children.iterator();
			while (i.hasNext()) {
				Area area = (Area) i.next();
				all.addAll(area.getAllSitesBelow());
			}
		}
		return all;
	}

    public List<Area> getAllSitesBelowWithItself() {
		List<Area> all = new ArrayList<Area>();
        all.add(this);
		List<Area> children = this.getChildAreas();
		if (children != null) {
			all.addAll(children);
			Iterator i = children.iterator();
			while (i.hasNext()) {
				Area area = (Area) i.next();
				all.addAll(area.getAllSitesBelow());
			}
		}
		return all;
	}

    public String getFullAreaPath() {
        List<Area> all = getAllSitesAboveWithItself();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < all.size(); i++) {
            Area site = all.get(i);
            builder.append("/" + site.getName());
        }

        return builder.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Area getParentArea() {
        return parentArea;
    }

    public void setParentArea(Area parentArea) {
        this.parentArea = parentArea;
    }

    public List<Area> getChildAreas() {
        return childAreas;
    }

    public void setChildAreas(List<Area> childAreas) {
        this.childAreas = childAreas;
    }

    public List<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(List<Community> communities) {
        this.communities = communities;
    }
}
