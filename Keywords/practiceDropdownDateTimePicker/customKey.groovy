package practiceDropdownDateTimePicker

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
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class customKey {
	
	@Keyword
	def submitForm(String firstName, String lastName, String email, String gender, String phoneNumber, String dob, String subject,String path, String address,
		String state, String city) {
		WebUI.setText(findTestObject('Object Repository/Dropdownlist_Datetimepicker/input_firstName'), firstName)
		WebUI.setText(findTestObject('Object Repository/Dropdownlist_Datetimepicker/input_lastName'), lastName)
		switch(gender) {
			case 'Male':
				WebUI.click(findTestObject('Object Repository/Dropdownlist_Datetimepicker/rdo_gender', [('gender'): gender]))
				break
			case 'Female':
				WebUI.click(findTestObject('Object Repository/Dropdownlist_Datetimepicker/rdo_gender', [('gender'): gender]))
				break
			case 'Other':
				WebUI.click(findTestObject('Object Repository/Dropdownlist_Datetimepicker/rdo_gender', [('gender'): gender]))
				break
			default:
				KeywordUtil.markFailed("Please choose gender")
		}
		WebUI.setText(findTestObject('Object Repository/Dropdownlist_Datetimepicker/input_phoneNumber'), phoneNumber)
		WebUI.setText(findTestObject('Object Repository/Dropdownlist_Datetimepicker/dtp_dob'), dob)
		WebUI.setText(findTestObject('Object Repository/Dropdownlist_Datetimepicker/input_subject'), subject)
		WebUI.uploadFile(findTestObject('Object Repository/Dropdownlist_Datetimepicker/input_uploadFile'), path)
		WebUI.setText(findTestObject('Object Repository/Dropdownlist_Datetimepicker/input_address'), address)
		WebUI.selectOptionByLabel(findTestObject('Object Repository/Dropdownlist_Datetimepicker/dropdown_state'), state, false)
		WebUI.selectOptionByLabel(findTestObject('Object Repository/Dropdownlist_Datetimepicker/dropdown_city'), city, false)
	}
}



