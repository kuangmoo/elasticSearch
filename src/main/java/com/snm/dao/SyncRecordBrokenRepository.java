package com.snm.dao;

import com.snm.domain.SyncRecordBroken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyncRecordBrokenRepository extends ElasticsearchRepository<SyncRecordBroken,Long> {

    //分页查询
    Page<SyncRecordBroken> findAll(Pageable pageable);
}
