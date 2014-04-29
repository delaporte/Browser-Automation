package yt.alex.automation.browser.google.test.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import yt.alex.automation.browser.google.IGoogleConst;
import yt.alex.automation.browser.google.test.IFunctionnalTest;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

public class TestHttpUnit implements IFunctionnalTest {

    @Test
    public void testGoogleSearch() {
        try {
            //La moindre erreur JS leve une EcmaError, c'est un peu trop exigent donc on desactive
            HttpUnitOptions.setScriptingEnabled(false);
            
            WebConversation conversation = new WebConversation();
            WebRequest request = new GetMethodWebRequest(IGoogleConst.STR_URL_GOOGLE);

            WebResponse response = conversation.getResponse(request);
            assertEquals(IGoogleConst.STR_TITLE_GOOGLE, response.getTitle());
            
            WebForm searchForm = response.getForms()[0];
            request = searchForm.getRequest(IGoogleConst.STR_INPUT_SEARCH_BUTTON_GOOGLE);
            request.setParameter(IGoogleConst.STR_INPUT_SEARCH_TEXT_GOOGLE, IFunctionnalTest.STR_SEARCH_TEST);
            response = conversation.getResponse(request);
            
            assertEquals(IGoogleConst.STR_TITLE_GOOGLE_SEARCH, response.getTitle());
        } // catch (IOException | SAXException e2){
        catch (IOException e) {
            fail(e.getMessage());
        } catch (SAXException e) {
            fail(e.getMessage());
        }

    }

    @Test
    @Ignore
    public void testCheckPage() {
        //Not yet implemented
        fail();
    }

    @Test
    @Ignore
    public void testGoogleAutocomplete() {
        // TODO Auto-generated method stub
        
    }

}
