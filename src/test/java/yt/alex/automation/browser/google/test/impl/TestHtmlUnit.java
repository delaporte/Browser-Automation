package yt.alex.automation.browser.google.test.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Ignore;
import org.junit.Test;

import yt.alex.automation.browser.google.CheckPositionHtmlUnit;
import yt.alex.automation.browser.google.IGoogleConst;
import yt.alex.automation.browser.google.test.IFunctionnalTest;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class TestHtmlUnit implements IFunctionnalTest {

    @Test
    public void testGoogleSearch() {
        try {
            //final WebClient webClient = new WebClient();
            final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
            HtmlPage page;

            page = webClient.getPage(IGoogleConst.STR_URL_GOOGLE);

            assertEquals(IGoogleConst.STR_TITLE_GOOGLE, page.getTitleText());

            HtmlForm form = page.getForms().get(0);
            HtmlTextInput textField = form.getInputByName(IGoogleConst.STR_INPUT_SEARCH_TEXT_GOOGLE);
            textField.setValueAttribute(IFunctionnalTest.STR_SEARCH_TEST);

            HtmlButton button = (HtmlButton) form.getElementsByAttribute("button", "name", IGoogleConst.STR_INPUT_SEARCH_BUTTON_GOOGLE).get(0);

            HtmlPage page2 = button.click();

            assertEquals(IGoogleConst.STR_TITLE_GOOGLE_SEARCH, page2.getTitleText());

            webClient.closeAllWindows();
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
    @Ignore
    public void testGoogleAutocomplete() {
        // TODO Auto-generated method stub
        
    }
}
