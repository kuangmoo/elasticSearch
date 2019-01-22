package com.snm.domain;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 注解解释：
 * indexName="blob3",索引的名称（必填项）
 * type="_doc",6.0后只有_doc
 *
 * Id：主键的唯一标识
 * index=true,是否索引，如果是索引表示该字段能够搜索
 * analyzer="ik_smart",//存储时使用的分词器
 * store=true,是否存储
 * searchAnalyzer="ik_smart",搜索时使用的分词器
 * type = FieldType.text数据类型
 *
 */
@Document(indexName = "index_syncrecordbroken",type = "doc")
@Data
public class SyncRecordBroken implements Serializable {
    private Integer id;

    private String titlecn;

    private String cid;

    private String vid;

    private Date updatedat;

    private String categoryid;

    private String status;

    private String maintype;

    private String servicetype;

    private String contentid;

    private String subheading;

    private Long titlelength;
}
