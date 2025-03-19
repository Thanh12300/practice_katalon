package practiceMultipleWindow

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.edge.EdgeDriver
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI




public class customKey {
	
	@Keyword
	def openNewWindow() {
		WebUI.openBrowser('')
		WebDriver driver = DriverFactory.getWebDriver()
		driver.get('https://demoqa.com/browser-windows')
		driver.findElement(By.id('windowButton')).click()
		Set<String> allWindows = driver.getWindowHandles()
		String mainWindow = driver.getWindowHandle()
		for (String window : allWindows) {
			if (!window.equals(mainWindow)) {
				driver.switchTo().window(window)
				break
			}
		}
		if (driver.findElement(By.id('sampleHeading')).isDisplayed()) {
			KeywordUtil.markPassed("Element is displayed")
		} else {
			KeywordUtil.markFailed("Element is NOT displayed")
		}
		
		driver.close()
	}
	
	@Keyword
	def openNewBrowser() {
	    // Chrome
	    System.setProperty("webdriver.chrome.driver", DriverFactory.getChromeDriverPath())
	    WebDriver chromeDriver = new ChromeDriver()
	    chromeDriver.manage().window().maximize()
	    chromeDriver.get("https://demoqa.com/browser-windows")
	
	    String mainChromeWindow = chromeDriver.getWindowHandle()
	    
	    chromeDriver.findElement(By.id('windowButton')).click()
	    Thread.sleep(2000) 
	
	    Set<String> allWindowsChrome = chromeDriver.getWindowHandles()
	    for (String window : allWindowsChrome) {
	        if (!window.equals(mainChromeWindow)) {
	            chromeDriver.switchTo().window(window)
	            break
	        }
	    }
	
	     if (chromeDriver.findElement(By.id('sampleHeading')).isDisplayed()) {
	            KeywordUtil.markPassed("Element in Chrome is displayed")
	     } else {
	            KeywordUtil.markFailed("Element in Chrome is NOT displayed")
	     }
	
	    // Edge
	    System.setProperty("webdriver.edge.driver", DriverFactory.getEdgeDriverPath())
	    WebDriver edgeDriver = new EdgeDriver()
	    edgeDriver.manage().window().maximize()
	    edgeDriver.get("https://demoqa.com/browser-windows")
	
	    String mainEdgeWindow = edgeDriver.getWindowHandle()
	    
	    edgeDriver.findElement(By.id('windowButton')).click()
	    Set<String> allWindowsEdge = edgeDriver.getWindowHandles()
	    for (String window : allWindowsEdge) {
	        if (!window.equals(mainEdgeWindow)) {
	            edgeDriver.switchTo().window(window)
	            break
	        }
	    }
	
	     if (edgeDriver.findElement(By.id('sampleHeading')).isDisplayed()) {
	            KeywordUtil.markPassed("Element in Edge is displayed")
	     } else {
	            KeywordUtil.markFailed("Element in Edge is NOT displayed")
	     }
	}
}
