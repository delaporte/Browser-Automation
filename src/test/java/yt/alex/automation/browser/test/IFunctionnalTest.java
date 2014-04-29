package yt.alex.automation.browser.test;

public interface IFunctionnalTest {
    static String STR_URL_GOOGLE = "http://www.google.fr";
    static String STR_TITLE_GOOGLE = "Google";
    static String STR_TITLE_GOOGLE_SEARCH = "test - Recherche Google";
    static String STR_INPUT_SEARCH_TEXT_GOOGLE = "q";
    static String STR_INPUT_SEARCH_BUTTON_GOOGLE = "btnG";
    
    static String STR_SEARCH_TEST = "test";
    
    void testGoogleSearch();
    
    void testCheckPage();
}
