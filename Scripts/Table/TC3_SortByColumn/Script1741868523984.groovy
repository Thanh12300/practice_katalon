import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI



WebUI.openBrowser('')
WebUI.navigateToUrl('https://utkarsh-react-table-demo.netlify.app/sort')

TestObject tableObject = findTestObject('Object Repository/Table/tb_name')
String excelFile = "Data Files/test_data"
String columnName = "Last Name"

CustomKeywords.'practiceTable.customKey.checkSortDescending'(tableObject, excelFile, columnName, "🔽")