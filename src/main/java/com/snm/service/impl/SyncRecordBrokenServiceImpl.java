package com.snm.service.impl;

import com.snm.dao.SyncRecordBrokenRepository;
import com.snm.domain.SyncRecordBroken;
import com.snm.service.SyncRecordBrokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

@Service
public class SyncRecordBrokenServiceImpl implements SyncRecordBrokenService {

    @Autowired
    private SyncRecordBrokenRepository repository;

    @Override
    public Page<SyncRecordBroken> search(SearchQuery query) {
        return repository.search(query);
    }

    @Override
    public Page<SyncRecordBroken> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

}

