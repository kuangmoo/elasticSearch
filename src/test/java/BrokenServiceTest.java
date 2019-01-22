import com.snm.domain.SyncRecordBroken;
import com.snm.service.SyncRecordBrokenService;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@Log4j2
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class BrokenServiceTest {


    @Autowired
    private SyncRecordBrokenService service;
    @Autowired
   private ElasticsearchTemplate elasticsearchTemplate;

    /**分页查询*/
    @Test
    public void findAllPage(){
        Pageable pageable = PageRequest.of(1,10);
        Page<SyncRecordBroken> page = service.findAll(pageable);
        for(SyncRecordBroken broken:page.getContent()){
            System.out.println(broken);
        }
    }

    @Test
    public void findAllTest(){


        BoolQueryBuilder builder = QueryBuilders.boolQuery();

        builder.must(QueryBuilders.wildcardQuery("titlecn","*小鲁班*"));
        //builder.must(QueryBuilders.wildcardQuery("vid","*0528u4*"));

        builder.must(QueryBuilders.rangeQuery("updatedat").gte("2018-01-04").lte("2018-01-05").format("yyyy-MM-dd"));
        //builder.must(QueryBuilders.rangeQuery("updatedat").from("2018-01-04").to("2018-01-05").format("yyyy-MM-dd"));
        //按照博客的评论数的排序是依次降低
        FieldSortBuilder sort = SortBuilders.fieldSort("titlelength").order(SortOrder.ASC);
        //注意开始是从0开始，有点类似sql中的方法limit 的查询
        PageRequest page = PageRequest.of(0, 10);

        //2.构建查询
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //将搜索条件设置到构建中
        nativeSearchQueryBuilder.withQuery(builder);
        //将分页设置到构建中
        nativeSearchQueryBuilder.withPageable(page);
        //将排序设置到构建中
        nativeSearchQueryBuilder.withSort(sort);
        //生产NativeSearchQuery
        NativeSearchQuery query = nativeSearchQueryBuilder.build();
        log.info("=================query:{}",query.getQuery());
        //3.执行方法1
        Page<SyncRecordBroken> brokens = service.search(query);
        List<SyncRecordBroken> content = brokens.getContent();
        for(SyncRecordBroken broken:content){
            log.info("=================:{}",broken);
        }

        //执行方法2：注意，这儿执行的时候还有个方法那就是使用elasticsearchTemplate
        //执行方法2的时候需要加上注解


        /*List<SyncRecordBroken> blogList = elasticsearchTemplate.queryForList(query, SyncRecordBroken.class);
        for(SyncRecordBroken broken:blogList){
            log.info("=================:{}",broken);
        }*/
/*
        //4.获取总条数(用于前端分页)
        int total = (int) brokens.getTotalElements();
        log.info("=================:{}",total);
        //5.获取查询到的数据内容（返回给前端）
        List<SyncRecordBroken> content = brokens.getContent();
        for(SyncRecordBroken broken:content){
            log.info(broken);
           System.out.println(broken);
        }*/

/*
        Client client = elasticsearchTemplate.getClient();
       SearchRequestBuilder searchRequestBuilder = client.prepareSearch("index_syncrecordbroken")
                .setTypes("doc")
               //.setTypes("syncRecordBroken")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.wildcardQuery("titlecn", "*鲁班*"));// Query
                //.setQuery(QueryBuilders.wildcardQuery("vid", "*05285*"));// Query
        log.info(searchRequestBuilder);
        SearchHit[] hits = searchRequestBuilder.get().getHits().getHits();
        for(SearchHit hit : hits){
            log.info(hit.getSourceAsString());
        }*/
    }
}
