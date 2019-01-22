package com.snm.service;

import com.snm.domain.SyncRecordBroken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

public interface SyncRecordBrokenService {

    Page<SyncRecordBroken> search(SearchQuery query);

    //分页查询
    Page<SyncRecordBroken> findAll(Pageable pageable);
}
