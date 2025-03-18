package practiceExcelFile

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class practiceExcel {
	//Excel
	@Keyword
	def countValueOfColumn() {
		TestData data = findTestData('Test_Data')
		int numberOfRow = data.getRowNumbers()
		println 'number of row: '+ numberOfRow

		ArrayList<String> listId = new ArrayList()
		ArrayList<String> listName = new ArrayList()
		ArrayList<String> listPassword = new ArrayList()
		for(int i = 1; i <= numberOfRow; i++) {
			String valueId = data.getObjectValue('Id', i)
			String valueName = data.getObjectValue('Name', i)
			String valuePassword = data.getObjectValue('Password', i)

			if(valueId != null && !valueId.trim().isEmpty()) {
				listId.add(valueName)
			}
			if(valueName != null && !valueName.trim().isEmpty()) {
				listName.add(valueName)
			}
			if(valuePassword != null && !valuePassword.trim().isEmpty()) {
				listPassword.add(valuePassword)
			}
		}
		println('Count of LastName values: ' + listId.size())
		println('Count of FirstName values: ' + listName.size())
		println('Count of Age values: ' + listPassword.size())
	}
}
