package com.uestc.nowcoder.wenda.service;

import com.uestc.nowcoder.wenda.model.Question;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author CLthinking
 * @date 2019/8/19 下午 04:17
 */
@Service
public class SearchService {
    // solr 的服务地址
    private static final String SOLR_URL = "http://127.0.0.1:8983/solr/wenda";
    private SolrClient client = new HttpSolrClient.Builder(SOLR_URL).build();
    private static final String QUESTION_TITLE_FIEILD = "question_title";
    private static final String QUESTION_CONTENT_FIEILD = "question_content";

    // 调用solr的搜索服务返回符合搜索条件的问题，支持分页
    public List<Question>  searchQuestion(String keyword, int offset, int count,
                                          String hlPre, String hlPos) throws IOException, SolrServerException {
        List<Question> questionList = new ArrayList<>();
        SolrQuery query = new SolrQuery(keyword);
        query.setRows(count);
        query.setStart(offset);
        query.setHighlight(true);
        query.setHighlightSimplePre(hlPre);
        query.setHighlightSimplePost(hlPos);
        query.set("hl.fl", QUESTION_TITLE_FIEILD + "," + QUESTION_CONTENT_FIEILD);
        QueryResponse response = client.query(query);

        for (Map.Entry<String, Map<String, List<String>>> entry : response.getHighlighting().entrySet()) {
            Question question = new Question();
            question.setId(Integer.parseInt(entry.getKey()));
            if (entry.getValue().containsKey(QUESTION_CONTENT_FIEILD)) {
                List<String> contentList = entry.getValue().get(QUESTION_CONTENT_FIEILD);
                if (contentList.size() > 0) {
                    question.setContent(contentList.get(0));
                }
            }
            if (entry.getValue().containsKey(QUESTION_TITLE_FIEILD)) {
                List<String> titleList = entry.getValue().get(QUESTION_TITLE_FIEILD);
                if (titleList.size() > 0) {
                    question.setTitle(titleList.get(0));
                }
            }
            questionList.add(question);
        }
        return questionList;
    }

    // 给新增加的问题索引入库，这样就可以被搜索出来了
    public boolean indexQuestion(int qid, String title, String content) throws IOException, SolrServerException {
        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id", qid);
        doc.setField(QUESTION_TITLE_FIEILD, title);
        doc.setField(QUESTION_CONTENT_FIEILD, content);
        UpdateResponse response = client.add(doc, 1000);
        return response != null && response.getStatus() == 0;
    }
}
