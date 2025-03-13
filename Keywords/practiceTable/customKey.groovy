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
		WebElement table = WebUI.findWebElement(object)  // Tìm phần tử bảng
		ArrayList<WebElement> element = table.findElements(By.xpath(".//th"))   // Tìm tất cả các tiêu đề cột (th)
		ArrayList<String> headerList = new ArrayList()    // Tạo danh sách để lưu tiêu đề
		for(int i = 0; i<element.size(); i++) {
			String header = element.get(i).getText()    // Lấy nội dung của từng th và thêm vào danh sách
			headerList.add(header)
			
		}
		return headerList   // Trả về danh sách tiêu đề cột
	}
	
	@Keyword
	def getIndexOfColumnName(TestObject table, String nameCol ) {
		ArrayList<WebElement> headers = getHeaderOfTable(table) 
		return headers.indexOf(nameCol) + 1
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
	}
	

	@Keyword
	public Map<String, String> getValueOfRow(TestObject object, int indexRow) {
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

	
	
}

