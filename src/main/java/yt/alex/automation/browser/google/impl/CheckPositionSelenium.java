package yt.alex.automation.browser.google.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import yt.alex.automation.browser.google.ICheckPosition;
import yt.alex.automation.browser.google.IGoogleConst;

public class CheckPositionSelenium implements ICheckPosition {

    public int checkPosition(String search, String url) {
            WebDriver driver = new FirefoxDriver();
            
            driver.get(IGoogleConst.STR_URL_GOOGLE);
            
            WebElement query = driver.findElement(By.name(IGoogleConst.STR_INPUT_SEARCH_TEXT_GOOGLE));
            query.sendKeys(search);
            
            WebElement button = driver.findElement(By.name(IGoogleConst.STR_INPUT_SEARCH_BUTTON_GOOGLE));
            button.click();
            
          //On attend que la page soit chargée (le div #resultStats => partie avec le nombre de resultats)
            WebDriverWait  wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));
            
            for (int foundPage = 1; foundPage < 12; ++foundPage) {
                //On parcourt les liens
                try {
                    driver.findElement(By.cssSelector("a[href*='" + url + "']"));
                    driver.quit();
                    return foundPage;
                } catch (NoSuchElementException e) {
                    //Not found, do nothing
                }
                
                driver.findElement(By.linkText(new Integer(foundPage + 1).toString())).click();
                
                wait = new WebDriverWait(driver, 5);
                // La page courante n'est pas un lien, attendre qu'il redevienne un lien signifie que la page suivante a été chargée
                wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(new Integer(foundPage).toString())));
                
            }           

        return 0;
    }
}
