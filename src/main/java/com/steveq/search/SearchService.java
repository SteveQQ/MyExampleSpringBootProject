package com.steveq.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Adam on 2017-05-21.
 */
@Service
public class SearchService {
    private Twitter twitter;

    @Autowired
    public SearchService(Twitter twitter){
        this.twitter = twitter;
    }

    public List<Tweet> search(String searchType, List<String> keywords){
        List<SearchParameters> searches = keywords.stream()
                .map(taste -> createSearchParam(searchType, taste))
                    .collect(Collectors.toList());
        List<Tweet> results = searches.stream()
                .map(params -> twitter.searchOperations()
                    .search(params))
                        .flatMap(searchResults -> searchResults.getTweets()
                            .stream())
                                .collect(Collectors.toList());
        return results;
    }

    private SearchParameters.ResultType getResultType(String searchType){
        for(SearchParameters.ResultType knownType : SearchParameters.ResultType.values()){
            if(knownType.name().equalsIgnoreCase(searchType)){
                return knownType;
            }
        }
        return SearchParameters.ResultType.RECENT;
    }

    private SearchParameters createSearchParam(String searchType, String taste){
        SearchParameters searchParameters = new SearchParameters(taste);
        SearchParameters.ResultType resultType = getResultType(searchType);
        searchParameters.resultType(resultType);
        searchParameters.count(3);
        return searchParameters;
    }

}
