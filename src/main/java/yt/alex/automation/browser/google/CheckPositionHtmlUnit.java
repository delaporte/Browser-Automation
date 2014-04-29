package yt.alex.automation.browser.google;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class CheckPositionHtmlUnit implements ICheckPosition {

    public int checkPosition(String search, String url) {
        try {
            final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
            HtmlPage page;

            page = webClient.getPage(IGoogleConst.STR_URL_GOOGLE);

            HtmlForm form = page.getForms().get(0);
            HtmlTextInput textField = form.getInputByName(IGoogleConst.STR_INPUT_SEARCH_TEXT_GOOGLE);
            textField.setValueAttribute(search);

            HtmlButton button = (HtmlButton) form.getElementsByAttribute("button", "name", IGoogleConst.STR_INPUT_SEARCH_BUTTON_GOOGLE)
                    .get(0);

            HtmlPage page2 = button.click();

            for (int foundPage = 1; foundPage < 10; ++foundPage) {

                HtmlElement resultPage = page2.getDocumentElement();
                if (resultPage.getElementsByAttribute("a", "href", url).size() > 0) {
                    return foundPage;
                }
                
                HtmlDivision pagination = page2.getHtmlElementById("navcnt");
                List<HtmlElement> cur = pagination.getHtmlElementsByTagName("td");
                boolean clickNext = false;
                for (HtmlElement htmlElement : cur) {
                    if (clickNext){
                        page2 = htmlElement.getHtmlElementsByTagName("a").get(0).click();
                        break;
                    }
                    if ("cur".equals(htmlElement.getAttribute("class"))){
                        clickNext = true;
                        System.out.println("Clic next page " + foundPage);
                    }
                }
            }

            webClient.closeAllWindows();
        } catch (FailingHttpStatusCodeException e) {
            fail(e.getMessage());
        } catch (MalformedURLException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }

        return 0;
    }
}
