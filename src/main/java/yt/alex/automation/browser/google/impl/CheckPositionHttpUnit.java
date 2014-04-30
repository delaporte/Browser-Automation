package yt.alex.automation.browser.google.impl;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.xml.sax.SAXException;

import yt.alex.automation.browser.google.ICheckPosition;
import yt.alex.automation.browser.google.IGoogleConst;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

public class CheckPositionHttpUnit implements ICheckPosition {

    public int checkPosition(String search, String url) {
        try {
            //La moindre erreur JS leve une EcmaError, c'est un peu trop exigent donc on desactive
            HttpUnitOptions.setScriptingEnabled(false);
            
            WebConversation conversation = new WebConversation();
            WebRequest request = new GetMethodWebRequest(IGoogleConst.STR_URL_GOOGLE);

            WebResponse response = conversation.getResponse(request);
            
            WebForm searchForm = response.getForms()[0];
            request = searchForm.getRequest(IGoogleConst.STR_INPUT_SEARCH_BUTTON_GOOGLE);
            request.setParameter(IGoogleConst.STR_INPUT_SEARCH_TEXT_GOOGLE, search);
            response = conversation.getResponse(request);
            
            for (int foundPage = 1; foundPage < 12; ++foundPage) {
                
                System.out.println(foundPage);
                System.out.println(response.getTitle());
                
                //On parcourt les liens
                WebLink[] nextPage = response.getLinks();
                for (WebLink webLink : nextPage) {
                    //Si on trouve l'url on est sur la bonne page
                    if (webLink.getURLString().contains(url)){
                        return foundPage;
                    }
                    System.out.println(webLink.getURLString());
                    //Sinon on passe à la page suivante
                    if (new Integer(foundPage + 1).equals(webLink.getText())){
                        response = webLink.click();
                    }
                }

                
            }
            
            
        } // catch (IOException | SAXException e2){
        catch (IOException e) {
            fail(e.getMessage());
        } catch (SAXException e) {
            fail(e.getMessage());
        }

        return 0;
    }
}
