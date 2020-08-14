package com.lcl.elasticsearch.es;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ClassName: ElasticsearchManager
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/24 17:42
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Component
@Slf4j
public class ElasticsearchManager {

    private static RestHighLevelClient restHighLevelClient;

    @Autowired
    public void setRestHighLevelClient(RestHighLevelClient restHighLevelClient) {
        ElasticsearchManager.restHighLevelClient = restHighLevelClient;
    }

    /**
     * 创建索引
     */
    public static void createIndex(String index) throws IOException {
        restHighLevelClient.indices().create(new CreateIndexRequest(index), RequestOptions.DEFAULT);
    }

    /**
     * 判断索引是否存在
     */
    public static boolean existsIndex(String index) throws IOException {
        return restHighLevelClient.indices().exists(new GetIndexRequest(index), RequestOptions.DEFAULT);
    }

    /**
     * 删除索引
     */
    public static void deleteIndex(String index) throws IOException {
        restHighLevelClient.delete(new DeleteRequest(index), RequestOptions.DEFAULT);
    }

    /**
     * 同步添加文档内容
     */
    public static <T> IndexResponse insertDocSync(String index, String id, T obj) throws IOException {
        IndexRequest request = new IndexRequest(index);
        request.id(id);
        request.timeout(TimeValue.timeValueSeconds(1L));
        request.source(JSONObject.toJSONString(obj), XContentType.JSON);
        return restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }

    /**
     * 异步添加文档内容
     */
    public static <T> void insertDocAsync(String index, String id, T obj) {
        IndexRequest request = new IndexRequest(index);
        request.id(id);
        request.timeout(TimeValue.timeValueSeconds(1L));
        request.source(JSONObject.toJSONString(obj), XContentType.JSON);
        restHighLevelClient.indexAsync(request, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                log.info("===>>>成功");
            }

            @Override
            public void onFailure(Exception e) {
                log.info("===>>>失败");
            }
        });
    }

    public static String search(String index, String key, String value) throws IOException {
        //1.创建检索请求
        SearchRequest searchRequest = new SearchRequest();
        //执行索引
        searchRequest.indices(index);
        //执行条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchRequest.source(searchSourceBuilder);
        searchSourceBuilder.query(QueryBuilders.matchQuery(key, value));
        //2.执行检索
        SearchResponse result = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //3.分析结果
        return result.toString();
    }

}
