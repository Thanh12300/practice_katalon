package practiceTable

import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI




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
		return headerList
	}

	@Keyword
	def getIndexOfColumnName(TestObject table, String nameCol ) {
		KeywordUtil.logInfo("table name: " + table)
		ArrayList<String> headers = getHeaderOfTable(table)
		if(headers.contains(nameCol)) {
			return headers.indexOf(nameCol) + 1
		}
	}


	@Keyword
	def getValueOfColumnName(TestObject object, String nameCol) {
		int index = getIndexOfColumnName(object, nameCol)
		WebElement table = WebUI.findWebElement(object)
		ArrayList<WebElement> elements = table.findElements(By.xpath("//th[text()='" + nameCol + "']//ancestor::thead//following::tbody//tr//td[" + index + "]"))

		ArrayList<String> valueList = new ArrayList()
		for(int i = 0; i<elements.size(); i++) {
			String value = elements.get(i).getText()
			KeywordUtil.logInfo(value)
			valueList.add(value)
		}
		return valueList
	}


	@Keyword
	def getValueOfRow(TestObject object) {
	    ArrayList<String> headers = getHeaderOfTable(object)
	    WebElement table = WebUI.findWebElement(object)
	
	    List<WebElement> rows = table.findElements(By.xpath(".//thead//th//ancestor::thead//following::tbody//tr"))
	    
	    List<Map<String, String>> tableData = []
	
	    for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
	        List<WebElement> rowCells = rows[rowIndex].findElements(By.tagName("td"))
	        Map<String, String> rowValue = new LinkedHashMap<>()
	
	        for (int i = 0; i < headers.size(); i++) {
	            String header = headers.get(i)
	            String value = rowCells[i].getText().trim()
	            rowValue.put(header, value)
	        }
	        
	        tableData.add(rowValue)
	        //println "Row ${rowIndex + 1}: " + rowValue
	    }
	
	    return tableData
	}

	@Keyword
	def checkSort(TestObject object, String excelFile, String colName, String order) {
		WebElement table = WebUI.findWebElement(object)
		WebElement element = table.findElement(By.xpath(".//th[@role='columnheader' and contains(text(), '"+ colName + "')]"))

		element.click()
		WebUI.delay(2)

		TestData data = findTestData(excelFile)
		ArrayList<String> listExpectedSort = new ArrayList()
		int numberOfRow = data.getRowNumbers()
		for(int i = 1; i <= numberOfRow; i++) {
			Object valueObj = data.getObjectValue(colName, i)
			if (valueObj != null && !valueObj.toString().trim().isEmpty()) {
				listExpectedSort.add(valueObj.toString().trim())
			}
		}

		KeywordUtil.logInfo("Expected sort: ")
		for(int i = 0; i<listExpectedSort.size(); i++) {
			KeywordUtil.logInfo(listExpectedSort.get(i).toString())
		}

		ArrayList<String> listActualSort = getValueOfColumnName(object, colName)
		KeywordUtil.logInfo("Actual Sort: ")
		for(int i = 1; i<listActualSort.size(); i++) {
			KeywordUtil.logInfo(listActualSort.get(i).toString())
		}

		if (order.equalsIgnoreCase("asc")) {
			Collections.sort(listExpectedSort)
		} else if (order.equalsIgnoreCase("desc")) {
			Collections.sort(listExpectedSort, Collections.reverseOrder())
		} else {
			KeywordUtil.markFailedAndStop("Invalid sort order. Use 'asc' or 'desc'.")
		}

		if (listExpectedSort.size() != listActualSort.size()) {
			KeywordUtil.markFailedAndStop("Size mismatch: Expected list size " + listExpectedSort.size() + ", Actual list size " + listActualSort.size())
		}

		if (listExpectedSort.equals(listActualSort)) {
			KeywordUtil.logInfo("The column '$colName' is correctly sorted in $order order.")
		} else {
			KeywordUtil.markFailedAndStop("The column '$colName' is NOT sorted correctly in $order order.")
		}
	}


	@Keyword
	def checkFilter(TestObject object, String filter, String colName) {
		WebUI.setText(findTestObject('Object Repository/Table/input_filter',[('colName'):colName]), filter)
		WebUI.delay(2)

		WebElement table = WebUI.findWebElement(object)
		int index = getIndexOfColumnName(object, colName)
		ArrayList<WebElement> rows = table.findElement(By.xpath(".//thead//th[text()='"+ colName +"']//ancestor::thead//following::tbody//td["+ index +"]"))
		boolean isValid = true
		for(int i = 1; i<rows.size(); i++) {
			if(!rows.contains(filter)) {
				KeywordUtil.logInfo("Invalid")
				isValid = false
			}
		}
		if (isValid) {
			KeywordUtil.markPassed("Filter works correctly")
		} else {
			KeywordUtil.markFailed("Filter works incorrectly")
		}
	}

	@Keyword
	def checkPagination(TestObject object, int rowInPage) {
		WebElement table = WebUI.findWebElement(object)
		WebUI.selectOptionByValue(findTestObject('Object Repository/Table/select_showEntries'), rowInPage.toString(), false)
		
		 List<Map<String, String>> firstPageData = getValueOfRow(object)
		 int countRow = firstPageData.size()
		
		if(countRow <= rowInPage) {
			KeywordUtil.logInfo("Show Entries: ${rowInPage}, Show rows: ${countRow}")
		}
		else {
			KeywordUtil.markFailed("Show Entries: ${rowInPage}, Show rows: ${countRow}")
		}
		
		
		boolean hasNextPage = true
		int currentPage = 1
		while(hasNextPage) {
			if(WebUI.verifyElementClickable(findTestObject('Object Repository/Table/btn_next'), FailureHandling.OPTIONAL)) {
				WebUI.click(findTestObject('Object Repository/Table/btn_next'))
				WebUI.delay(2)
				
				List<Map<String, String>> newPageData = getValueOfRow(object)
				if(firstPageData != newPageData) {
					KeywordUtil.logInfo("Dữ liệu đã thay đổi khi chuyển trang ${currentPage + 1}")
				}
				else {
					KeywordUtil.markFailed("Lỗi: Dữ liệu không thay đổi khi chuyển sang trang ${currentPage + 1}")
				}
				currentPage++
			}
			else {
				hasNextPage = false
				KeywordUtil.logInfo("Đã đến trang cuối cùng")
			}
		}
		
		while(currentPage > 1) {
			if(WebUI.verifyElementClickable(findTestObject('Object Repository/Table/btn_prev'), FailureHandling.OPTIONAL)) {
				WebUI.click(findTestObject('Object Repository/Table/btn_prev'))
				WebUI.delay(2)
				currentPage--
			}
		}
		if(WebUI.verifyElementClickable(findTestObject('Object Repository/Table/btn_lastpage'), FailureHandling.OPTIONAL)) {
			WebUI.click(findTestObject('Object Repository/Table/btn_lastpage'))
			WebUI.delay(2)
			KeywordUtil.logInfo("Đã chuyển đến trang cuối cùng")
		}
		if(WebUI.verifyElementClickable(findTestObject('Object Repository/Table/btn_firstpage'), FailureHandling.OPTIONAL)) {
			WebUI.click(findTestObject('Object Repository/Table/btn_firstpage'))
			WebUI.delay(2)
			KeywordUtil.logInfo("Đã quay về trang đầu tiên")
		}
	}
}

