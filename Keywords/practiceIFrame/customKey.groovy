package practiceIFrame

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI




public class customKey {
	
	@Keyword
	def checkIframe() {
	    WebUI.openBrowser('')
	    WebUI.navigateToUrl('https://demoqa.com/frames')
	
	    WebDriver driver = DriverFactory.getWebDriver()
	
	    WebUI.delay(2) 
	
	    WebElement iframeElement = driver.findElement(By.xpath("//iframe[@id='frame1']"))
	    driver.switchTo().frame(iframeElement)
	
	    WebElement heading = driver.findElement(By.xpath("//h1[@id='sampleHeading']"))
	    String actualText = heading.getText()
	    assert actualText.equals("This is a sample page") : "Text is NOT match: "
	
	    driver.switchTo().defaultContent()
	
	    WebUI.closeBrowser()
	}
	
	@Keyword
	def checkNestedIframe() {
		WebUI.openBrowser('')
		WebUI.navigateToUrl('https://demoqa.com/nestedframes')
		WebDriver driver = DriverFactory.getWebDriver()
		
		WebElement parentFrame = driver.findElement(By.xpath("//iframe[@id='frame1']"))
		driver.switchTo().frame(parentFrame)
		KeywordUtil.markPassed("Go to parent frame")
		
		WebElement childFrame = driver.findElement(By.tagName("iframe"))
		driver.switchTo().frame(childFrame)
		KeywordUtil.markPassed("Go to child frame")
		WebElement childText = driver.findElement(By.tagName("p"))
		String actualText = childText.getText()
		assert actualText.equals("Child Iframe") : "Text không khớp, giá trị thực tế: " + actualText
	
		driver.switchTo().parentFrame()
		
		driver.switchTo().defaultContent()
	
		WebUI.closeBrowser()
		
	}
}
