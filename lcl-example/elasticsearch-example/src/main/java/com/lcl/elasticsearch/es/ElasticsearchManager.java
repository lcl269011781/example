package com.lcl.elasticsearch.es;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
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
     * 添加文档内容
     */
    public static <T> IndexResponse insertDoc(String index, T obj) throws IOException {
        IndexRequest request = new IndexRequest(index);
        request.timeout(TimeValue.timeValueSeconds(1L));
        request.source(JSONObject.toJSONString(obj), XContentType.JSON);
        return restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }


}
