package com.searchmodule.tests;

import com.searchmodule.pages.SearchPage;

import org.testng.Assert;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import tests.BaseTest;

public class SearchTest extends BaseTest {

    @Test(description = "search for Info")
    @Parameters({"keyword"})
    public void search(String keyword){
        SearchPage searchPage = new SearchPage(driver);
        searchPage.goTo();
        searchPage.doSearch(keyword);
        searchPage.goToVideos();
        int size = searchPage.getResult();
        System.out.println("The keword searched is :"+ keyword);
        Assert.assertTrue(size > 0);
    }



}
