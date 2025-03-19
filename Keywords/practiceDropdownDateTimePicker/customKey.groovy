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
	def submitForm(String firstName, String lastName, String email, String gender, String phoneNumber, String subject,String path, String address,
			String state, String city, List<String> hobbies) {
		WebUI.setText(findTestObject('Object Repository/Dropdownlist_Datetimepicker/input_firstName'), firstName)
		WebUI.setText(findTestObject('Object Repository/Dropdownlist_Datetimepicker/input_lastName'), lastName)
		WebUI.setText(findTestObject('Object Repository/Dropdownlist_Datetimepicker/input_email'), email)
		WebUI.click(findTestObject('Object Repository/Dropdownlist_Datetimepicker/rdo_gender', [('gender'): gender]))
		
		WebUI.setText(findTestObject('Object Repository/Dropdownlist_Datetimepicker/input_phoneNumber'), phoneNumber)
		//WebUI.setText(findTestObject('Object Repository/Dropdownlist_Datetimepicker/dtp_dob'), dob)
		WebUI.setText(findTestObject('Object Repository/Dropdownlist_Datetimepicker/input_subject'), subject)
		for (String hobby : hobbies) {
			WebUI.click(findTestObject('Object Repository/Dropdownlist_Datetimepicker/chk_hobby', [('hobby'): hobby]))
		}
		
		WebUI.uploadFile(findTestObject('Object Repository/Dropdownlist_Datetimepicker/input_uploadFile'), path)
		WebUI.setText(findTestObject('Object Repository/Dropdownlist_Datetimepicker/input_address'), address)

		
		WebUI.click(findTestObject('Object Repository/Dropdownlist_Datetimepicker/dropdown_state')) 
		WebUI.click(findTestObject('Object Repository/Dropdownlist_Datetimepicker/dropdown_state_option', [('state'): state]))
		
		
		
		WebUI.click(findTestObject('Object Repository/Dropdownlist_Datetimepicker/dropdown_city')) 
		WebUI.click(findTestObject('Object Repository/Dropdownlist_Datetimepicker/dropdown_state_option', [('city'): city]))
		//WebUI.click(findTestObject('Object Repository/Dropdownlist_Datetimepicker/btn_submit'))
	}
}



