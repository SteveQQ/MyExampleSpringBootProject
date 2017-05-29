package com.steveq.search.api;

import com.steveq.profile.SearchService;
import com.steveq.search.LightTweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Adam on 2017-05-29.
 */
@RestController
@RequestMapping(value = "/api/search")
public class SearchApiController {

    private SearchService searchService;

    @Autowired
    public SearchApiController(SearchService searchService){
        this.searchService = searchService;
    }

    @RequestMapping(value = "/{searchType}", method = RequestMethod.GET)
    public List<LightTweet> search(@PathVariable String searchType, @MatrixVariable List<String> keywords){
        System.out.println("SEARCH TYPE : " + searchType);
        System.out.println("KEYWORDS : " + keywords);
        return searchService.searchApi(searchType, keywords);
    }
}
