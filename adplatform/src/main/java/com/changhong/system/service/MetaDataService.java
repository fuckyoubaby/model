package com.changhong.system.service;

import com.changhong.system.web.facade.dto.MetaDataDTO;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 16-11-21
 * Time: 上午9:17
 */
public interface MetaDataService {

    List<MetaDataDTO> obtainMetaDatas(int startPosition, int pageSize);

    int obtainMetaDataSize();

    MetaDataDTO obtainMetaUuidByUuid(String metaDataUuid);

    void changeMetaDataDetails(MetaDataDTO dto);
}
