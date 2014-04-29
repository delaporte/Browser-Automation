package yt.alex.automation.browser.google.test.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import yt.alex.automation.browser.google.IGoogleConst;
import yt.alex.automation.browser.google.test.IFunctionnalTest;

public class TestSelenium implements IFunctionnalTest {
    private static WebDriver driver;
    
    @BeforeClass
    public static void beforeClass(){
        driver = new FirefoxDriver();
        //driver = new ChromeDriver();
        //driver = new InternetExplorerDriver();
        //driver = new SafariDriver();
        
        //Embedded Nav
        //driver = new HtmlUnitDriver();
    }
    
    @AfterClass
    public static void afterClass(){
        driver.quit();
    }
    
    @Test
    public void testGoogleSearch() {

        driver.get(IGoogleConst.STR_URL_GOOGLE);

        assertEquals(IGoogleConst.STR_TITLE_GOOGLE, driver.getTitle());

        WebElement query = driver.findElement(By.name(IGoogleConst.STR_INPUT_SEARCH_TEXT_GOOGLE));
        query.sendKeys(IFunctionnalTest.STR_SEARCH_TEST);
        
        WebElement button = driver.findElement(By.name(IGoogleConst.STR_INPUT_SEARCH_BUTTON_GOOGLE));
        button.click();
        
        //On attend que la page soit chargée (le div #resultStats => partie avec le nombre de resultats)
        WebDriverWait  wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));
        
        assertEquals(IGoogleConst.STR_TITLE_GOOGLE_SEARCH, driver.getTitle());
    }

    @Test
    @Ignore
    public void testCheckPage() {
        //Not yet implemented
        fail();
    }

    @Test
    public void testGoogleAutocomplete() {
        driver.get(IGoogleConst.STR_URL_GOOGLE);
        
        WebElement autocompleteTable = driver.findElement(By.className(IGoogleConst.STR_AUTOCOMPLETE_TABLE_CLASS));
        //Le bloc autocomplete ne doit pas être visible avant que l'on commence à entrer du texte
        assertEquals("none", autocompleteTable.getCssValue("display"));
        
        WebElement query = driver.findElement(By.name(IGoogleConst.STR_INPUT_SEARCH_TEXT_GOOGLE));
        query.sendKeys(IFunctionnalTest.STR_SEARCH_TEST);
        
        WebDriverWait  wait = new WebDriverWait(driver, 5);
        //On attend que les propositions soient chargés
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className(IGoogleConst.STR_AUTOCOMPLETE_TABLE_LINE_CLASS)));
        
        autocompleteTable = driver.findElement(By.className(IGoogleConst.STR_AUTOCOMPLETE_TABLE_CLASS));
        //Le bloc autocomplete doit maintenant être visible
        assertEquals("table-cell", autocompleteTable.getCssValue("display"));
    }
    
    
}
