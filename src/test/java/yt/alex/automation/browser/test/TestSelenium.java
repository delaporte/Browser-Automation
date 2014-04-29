package yt.alex.automation.browser.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestSelenium implements IFunctionnalTest {
    @Test
    public void testGoogleSearch() {
        //WebDriver driver = new FirefoxDriver();
        //WebDriver driver = new ChromeDriver();
        //WebDriver driver = new InternetExplorerDriver();
        //WebDriver driver = new SafariDriver();
        
        //Embedded Nav
        WebDriver driver = new HtmlUnitDriver();

        driver.get(IFunctionnalTest.STR_URL_GOOGLE);

        assertEquals(IFunctionnalTest.STR_TITLE_GOOGLE, driver.getTitle());

        WebElement query = driver.findElement(By.name(IFunctionnalTest.STR_INPUT_SEARCH_TEXT_GOOGLE));
        query.sendKeys(IFunctionnalTest.STR_SEARCH_TEST);
        
        WebElement button = driver.findElement(By.name(IFunctionnalTest.STR_INPUT_SEARCH_BUTTON_GOOGLE));
        button.click();
        
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        
        //On attend que la page soit chargée (le div #resultStats => partie avec le nombre de resultats)
        WebDriverWait  wait = new WebDriverWait(driver, 5000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));
        
        assertEquals(IFunctionnalTest.STR_TITLE_GOOGLE_SEARCH, driver.getTitle());

        driver.quit();
    }

    public void testCheckPage() {
        fail();
    }
}
