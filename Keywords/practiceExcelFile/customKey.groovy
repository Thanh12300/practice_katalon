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

public class customKey {
	
	@Keyword
	def countValueOfColumn() {
		TestData data = findTestData('Test_Data')
		int numberOfRow = data.getRowNumbers()
		println 'number of row: '+ numberOfRow
		
		ArrayList<String> listLastName = new ArrayList()
		ArrayList<String> listFirstName = new ArrayList()
		ArrayList<String> listAge = new ArrayList()
		for(int i = 1; i <= numberOfRow; i++) {
			String valueLastName = data.getObjectValue('Last Name', i)
			String valueFirstName = data.getObjectValue('First Name', i)
			String valueAge = data.getObjectValue('Age', i)
			
			if(valueLastName != null && !valueLastName.trim().isEmpty()) {
				listLastName.add(valueLastName)
			}
			if(valueFirstName != null && !valueFirstName.trim().isEmpty()) {
				listFirstName.add(valueFirstName)
			}
			if(valueAge != null && !valueAge.trim().isEmpty()) {
				listAge.add(valueAge)
			}
		}
		println('Count of LastName values: ' + listLastName.size())
		println('Count of FirstName values: ' + listFirstName.size())
		println('Count of Age values: ' + listAge.size())
	}
}

