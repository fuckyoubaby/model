package com.changhong.system.web.facade.assember;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.changhong.system.domain.advertisment.AdverResource;
import com.changhong.system.domain.uploaddata.UploadADData;
import com.changhong.system.web.facade.dto.AdDataDTO;

public class AdDataWebAssember {

	public static List<AdDataDTO> toAdDataDTOs(List<UploadADData> uploadADDatas)
	{
		List<AdDataDTO> list = new ArrayList<>();
		//原来的list是按照资源id进行排序的
		String ruuid = "";
		double length = 0;
		int times = 0;
		AdDataDTO adDataDTO = null;
		for (int i = 0; i < uploadADDatas.size(); i++) {
			
			if (ruuid.equals(uploadADDatas.get(i).getAdverResource().getUuid())) {
				length += uploadADDatas.get(i).getTotalTime().doubleValue();
				times += uploadADDatas.get(i).getTimes();
				
				adDataDTO.setLength(length);
				adDataDTO.setResourceName(uploadADDatas.get(i).getAdverResource().getName());
				adDataDTO.setTimes(times);
				adDataDTO.setUuid(uploadADDatas.get(i).getUuid());
				
				list.remove(list.size()-1);
				list.add(adDataDTO);
				
			}else {
				ruuid = uploadADDatas.get(i).getAdverResource().getUuid();
				length = uploadADDatas.get(i).getTotalTime().doubleValue();
				times = uploadADDatas.get(i).getTimes();
				
				adDataDTO = new AdDataDTO();
				adDataDTO.setLength(length);
				adDataDTO.setResourceName(uploadADDatas.get(i).getAdverResource().getName());
				adDataDTO.setTimes(times);
				adDataDTO.setUuid(uploadADDatas.get(i).getUuid());
				
				list.add(adDataDTO);
			}
			
			
		}
		
		return list;
	}
	
	
	public static void main(String[] args) {
		List<UploadADData> uploadADDatas = new ArrayList<>();
		
		
		for (int i = 0; i < 10; i++) {
			
			UploadADData uploadADData = new UploadADData();
			AdverResource adverResource = new AdverResource();
			adverResource.setUuid("000001");
			adverResource.setName("ziyuan1");
			uploadADData.setAdverResource(adverResource);
			uploadADData.setTimes(5);
			uploadADData.setTotalTime(new BigDecimal(500));
			uploadADDatas.add(uploadADData);
		}
		for (int i = 0; i < 5; i++) {
			
			UploadADData uploadADData = new UploadADData();
			AdverResource adverResource = new AdverResource();
			adverResource.setUuid("000002");
			adverResource.setName("ziyuan2");
			uploadADData.setAdverResource(adverResource);
			uploadADData.setTimes(5);
			uploadADData.setTotalTime(new BigDecimal(500));
			uploadADDatas.add(uploadADData);
		}
		
		List<AdDataDTO> list = AdDataWebAssember.toAdDataDTOs(uploadADDatas);
		for (int i = 0; i < list.size(); i++) {
			System.out.println("---"+list.get(i).getResourceName()+"---"+list.get(i).getLength()+"---"+list.get(i).getTimes());
		}
	}
}
