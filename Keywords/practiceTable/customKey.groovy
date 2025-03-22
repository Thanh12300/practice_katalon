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
		ArrayList<String> headers = getHeaderOfTable(table)
		for(String header in headers) {
			if(header.contains(nameCol)) {
				return headers.indexOf(header) + 1;
			}
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
	def checkSortAscending(TestObject object, String excelFile, String colName, String order) {
		WebElement table = WebUI.findWebElement(object)
		WebElement header = table.findElement(By.xpath(".//th[@role='columnheader' and contains(text(), '"+ colName + "')]"))
		WebElement icon = header.findElement(By.xpath(".//span"))
		String sortIconText = icon.getText().trim()
		header.click()
		WebUI.delay(2)
		ArrayList<String> listTableSort = getValueOfColumnName(object, colName)
		TestData data = findTestData(excelFile)
		ArrayList<String> listDataFromExcel = new ArrayList<>()
		int numberOfRow = data.getRowNumbers()
		for (int i = 1; i <= numberOfRow; i++) {
			Object valueObj = data.getObjectValue(colName, i)
			if (valueObj != null && !valueObj.toString().trim().isEmpty()) {
				listDataFromExcel.add(valueObj.toString().trim())
			}
		}
		if (order.equalsIgnoreCase("🔼")) {
			Collections.sort(listDataFromExcel)
		}
		if (listDataFromExcel.size() != listTableSort.size()) {
			KeywordUtil.markFailedAndStop("Size mismatch: Expected list size " + listDataFromExcel.size() + ", Actual list size " + listTableSort.size())
		}
	
		// So sánh kết quả sắp xếp
		if (listDataFromExcel.equals(listTableSort)) {
			KeywordUtil.logInfo("The column '${colName}' is correctly sorted in ${order} order.")
		} else {
			KeywordUtil.markFailedAndStop("The column '${colName}' is NOT sorted correctly in ${order} order.")
		}
	}
	
	
	@Keyword
	def checkSortDescending(TestObject object, String excelFile, String colName, String order) {
		WebElement table = WebUI.findWebElement(object)
		WebElement header = table.findElement(By.xpath(".//th[@role='columnheader' and contains(text(), '"+ colName + "')]"))
		WebElement icon = header.findElement(By.xpath(".//span"))
		String sortIconText = icon.getText().trim()
		header.click()
		header.click()
		WebUI.delay(2)
		ArrayList<String> listTableSort = getValueOfColumnName(object, colName)
		TestData data = findTestData(excelFile)
		ArrayList<String> listDataFromExcel = new ArrayList<>()
		int numberOfRow = data.getRowNumbers()
		for (int i = 1; i <= numberOfRow; i++) {
			Object valueObj = data.getObjectValue(colName, i)
			if (valueObj != null && !valueObj.toString().trim().isEmpty()) {
				listDataFromExcel.add(valueObj.toString().trim())
			}
		}
		if (order.equalsIgnoreCase("🔽")) {
			Collections.sort(listDataFromExcel, Collections.reverseOrder())
		}
		if (listDataFromExcel.size() != listTableSort.size()) {
			KeywordUtil.markFailedAndStop("Size mismatch: Expected list size " + listDataFromExcel.size() + ", Actual list size " + listTableSort.size())
		}
	
		// So sánh kết quả sắp xếp
		if (listDataFromExcel.equals(listTableSort)) {
			KeywordUtil.logInfo("The column '${colName}' is correctly sorted in ${order} order.")
		} else {
			KeywordUtil.markFailedAndStop("The column '${colName}' is NOT sorted correctly in ${order} order.")
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

