package yt.alex.automation.browser.google.test.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import yt.alex.automation.browser.google.IGoogleConst;
import yt.alex.automation.browser.google.impl.CheckPositionHtmlUnit;
import yt.alex.automation.browser.google.test.IFunctionnalTest;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class TestHtmlUnit implements IFunctionnalTest {

    private static WebClient webClient;

    @BeforeClass
    public static void beforeClass() {
        //webClient = new WebClient();
        webClient = new WebClient(BrowserVersion.FIREFOX_24);
        //webClient = new WebClient(BrowserVersion.CHROME);
    }

    @AfterClass
    public static void afterClass() {
        webClient.closeAllWindows();
    }

    @Test
    public void testGoogleSearch() {
        try {

            HtmlPage page = webClient.getPage(IGoogleConst.STR_URL_GOOGLE);

            assertEquals(IGoogleConst.STR_TITLE_GOOGLE, page.getTitleText());

            HtmlForm form = page.getForms().get(0);
            form.getInputByName(IGoogleConst.STR_INPUT_SEARCH_TEXT_GOOGLE).type(IFunctionnalTest.STR_SEARCH_TEST);

            HtmlButton button = (HtmlButton) form.getElementsByAttribute("button", "name", IGoogleConst.STR_INPUT_SEARCH_BUTTON_GOOGLE)
                    .get(0);

            HtmlPage page2 = button.click();

            assertEquals(IGoogleConst.STR_TITLE_GOOGLE_SEARCH, page2.getTitleText());

        } catch (FailingHttpStatusCodeException e) {
            fail(e.getMessage());
        } catch (MalformedURLException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCheckPage() {
        assertEquals(1, new CheckPositionHtmlUnit().checkPosition("Alexis DELAPORTE", "http://www.alexis-delaporte.com/"));
        assertEquals(7, new CheckPositionHtmlUnit().checkPosition("DELAPORTE", "http://www.alexis-delaporte.com/"));
    }

    @Test
    public void testGoogleAutocomplete() {
        try {
            HtmlPage page = webClient.getPage(IGoogleConst.STR_URL_GOOGLE);
            
            webClient.waitForBackgroundJavaScript(10000);
            
            List<HtmlElement> autocompleteTable = page.getBody().getElementsByAttribute("td", "class", IGoogleConst.STR_AUTOCOMPLETE_TABLE_TD_CLASS);
            assertEquals(1, autocompleteTable.size());
            assertTrue(autocompleteTable.get(0).getAttribute("style").contains("display: none"));
            


            HtmlForm form = page.getForms().get(0);
            HtmlTextInput textField = form.getInputByName(IGoogleConst.STR_INPUT_SEARCH_TEXT_GOOGLE);
            textField.click();
            textField.type(IFunctionnalTest.STR_SEARCH_TEST);
            
            for (int i = 0; i < 20; i++) {
                //Wait for autocompletblock load
                if (!autocompleteTable.get(0).getAttribute("style").contains("display: none")) {
                    break;
                }
                webClient.waitForBackgroundJavaScript(1000);
            }
            
            autocompleteTable = page.getBody().getElementsByAttribute("td", "class", IGoogleConst.STR_AUTOCOMPLETE_TABLE_TD_CLASS);
            assertTrue(!autocompleteTable.get(0).getAttribute("style").contains("display: none"));
            //Oups !!! Seems to never load the autocomplete block
            
            
            
        } catch (FailingHttpStatusCodeException e) {
            fail(e.getMessage());
        } catch (MalformedURLException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }

    }
}
