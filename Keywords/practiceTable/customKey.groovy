package practiceTable

import static com.kms.katalon.core.testdata.TestDataFactory.findTestData

import java.util.stream.Collectors

import javax.lang.model.util.Elements

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

public class customKey {

	@Keyword
	def getHeaderOfTable(TestObject object) {
		WebElement table = WebUI.findWebElement(object)  
		ArrayList<WebElement> element = table.findElements(By.xpath(".//th"))   
		ArrayList<String> headerList = new ArrayList()   
		for(int i = 0; i<element.size(); i++) {
			String header = element.get(i).getText()    
			headerList.add(header)
		}
		return headerList   // Trả về danh sách tiêu đề cột
	}

	@Keyword
	def getIndexOfColumnName(TestObject table, String nameCol ) {
		ArrayList<String> headers = getHeaderOfTable(table)
		println('name col: ' + nameCol;)
		return headers.indexOf(nameCol) + 1
	}


	@Keyword
	def getValueOfColumnName(TestObject object, String nameCol) {
		int index = getIndexOfColumnName(object, nameCol)
		WebElement table = WebUI.findWebElement(object)
		ArrayList<WebElement> elements = table.findElements(By.xpath("//th[text()='" + nameCol + "']//ancestor::thead//following::tbody//tr//td[" + index + "]"))
		
		KeywordUtil.logInfo(nameCol)
		KeywordUtil.logInfo('index: ' + index.toString())
		
		ArrayList<String> valueList = new ArrayList()
		for(int i = 0; i<elements.size(); i++) {
			String value = elements.get(i).getText()
			KeywordUtil.logInfo(value)
			valueList.add(value)
		}
		return valueList
	}


	@Keyword
	def getValueOfRow(TestObject object, int indexRow) {
		ArrayList<String> headers = getHeaderOfTable(object);
		WebElement table = WebUI.findWebElement(object);
		List<WebElement> rowCells = table.findElements(By.xpath("//tbody/tr[" + indexRow + "]/td"));

		Map<String, String> rowValue = new LinkedHashMap<>();
		
		
		for (int i = 0; i < headers.size(); i++) {
			String header = headers.get(i);
			String value = rowCells.get(i).getText();
			rowValue.put(header, value);
		}
		println rowValue
		return rowValue;
	}
	
	@Keyword
	def checkSortAscending(TestObject object, String excelFile, String colName) {
		WebElement table = WebUI.findWebElement(object)
		WebElement element = table.findElement(By.xpath(".//th[@role='columnheader' and contains(text(), '"+ colName + "')]"))
		element.click()
		WebUI.delay(2)
		ArrayList<String> listActualSort = getValueOfColumnName(object, colName)
		KeywordUtil.logInfo("Column name:" + colName)
		KeywordUtil.logInfo('Actual: ' + listActualSort.size())
		
		//debug
		KeywordUtil.logInfo("Actual List Elements:")
		for (String value : listActualSort) {
			KeywordUtil.logInfo(value)
		}
		
		TestData data = findTestData(excelFile)
		ArrayList<String> listExpectedSort = new ArrayList()
		int numberOfRow = data.getRowNumbers()
		for(int i = 1; i <= numberOfRow; i++) {
			String value = data.getObjectValue(colName, i)
			if (value != null && !value.trim().isEmpty()) {
				
				listExpectedSort.add(value.toString().trim()) 
			}
		}
		
		//debug
//		KeywordUtil.logInfo("Expected List Elements:")
//		for (String value : listExpectedSort) {
//			KeywordUtil.logInfo(value)
//		}
				
		if (listExpectedSort.size() != listActualSort.size()) {
			KeywordUtil.markFailedAndStop("Size in expected list and size in actual list is not equal")
		}
		
		
		KeywordUtil.logInfo('Expected: ' + listExpectedSort.size())
		
		if(listExpectedSort.equals(listActualSort)) {
			KeywordUtil.logInfo("The column '$colName' is correctly sorted in ascending order.")
		}
		else {
			KeywordUtil.markFailedAndStop("The column '$colName' is NOT sorted correctly.")
		}
	}
	
}

