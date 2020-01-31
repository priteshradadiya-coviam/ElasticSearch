
package com.example.demo.searchQueryBuilder;



import com.example.demo.entity.Action;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SearchQueryBuilder {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    public List<Action> getAll(String text,String action) {

        QueryBuilder query= QueryBuilders.boolQuery()
                .must(QueryBuilders.queryStringQuery(text).
                        lenient(true)

                        .field("userId")
                        )
                .must(QueryBuilders.queryStringQuery(action)
                        .lenient(true)
                        .field("action")
                        );

        NativeSearchQuery queryBuilder=new NativeSearchQueryBuilder().withQuery(query).build();

        List<Action> actionList= elasticsearchTemplate.queryForList(queryBuilder, Action.class);
        return actionList;
    }


    public Map<String,Long> getTagListCount(String action)
    {
        QueryBuilder query= QueryBuilders.boolQuery()
                .must(QueryBuilders.queryStringQuery(action).
                        lenient(true)

                        .field("action")
                );


        NativeSearchQuery queryBuilder=new NativeSearchQueryBuilder().withQuery(query).build();

        List<Action> actionList= elasticsearchTemplate.queryForList(queryBuilder, Action.class);

        Map<String, Long> countTagList = actionList.stream().collect(Collectors
                .groupingBy(Action::getTag, Collectors.counting()));

        countTagList.forEach((k, v) -> {
            System.out.println(k + " : " + v);
        });


        return countTagList;

    }


    public String getMostPopularTag(String channel, String like)
    {
        QueryBuilder query= QueryBuilders.boolQuery()
                .must(QueryBuilders.queryStringQuery(channel).
                        lenient(true)

                        .field("channel")
                )
                .must(QueryBuilders.queryStringQuery(like)
                        .lenient(true)
                        .field("action")
                );

        NativeSearchQuery queryBuilder=new NativeSearchQueryBuilder().withQuery(query).build();

        List<Action> actionList= elasticsearchTemplate.queryForList(queryBuilder, Action.class);

        Map<String, Long> countTagList = (HashMap<String,Long>)actionList.stream().collect(Collectors
                .groupingBy(Action::getTag, Collectors.counting()));


        Map.Entry<String, Long> maxEntry = null;

        for(Map.Entry<String,Long> entry:countTagList.entrySet())
        {
            if(entry.getValue().compareTo(maxEntry.getValue())>0)
                maxEntry=entry;
        }

        return maxEntry.getKey();

    }
}



