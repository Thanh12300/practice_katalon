package practiceDB

import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI




public class customKey {
	@Keyword
public static void login(String data) {
    // Ensure the TestData object is properly loaded
    TestData testData = findTestData('Data Files/db/' + data)

    // Get the row count from the Test Data
    int rowCount = testData.getRowNumbers()

    // Loop through all rows in the Test Data
    for (int i = 1; i <= rowCount; i++) {
        WebUI.openBrowser('')
        WebUI.navigateToUrl('https://opensource-demo.orangehrmlive.com/web/index.php/auth/login')

        // Retrieve values for username and password from each row
        String username = testData.getValue('username', i)
        String password = testData.getValue('password', i)

        // Fill in the login form
        WebUI.setText(findTestObject('Object Repository/WorkWithDB/input_username'), username)
        WebUI.setText(findTestObject('Object Repository/WorkWithDB/input_password'), password)

        // Click the login button
        WebUI.click(findTestObject('Object Repository/WorkWithDB/btn_login'))

        // Check if login was successful
        boolean isLoggedIn = WebUI.verifyElementPresent(findTestObject('Object Repository/WorkWithDB/txt_dashboard'),10,FailureHandling.OPTIONAL )
        if (isLoggedIn) {
            KeywordUtil.markPassed("Login successful for user: " + username)
            // Do logout or other actions if needed
            WebUI.closeBrowser()
        } else {
            boolean isErrorMessagePresent = WebUI.verifyElementPresent(findTestObject('Object Repository/WorkWithDB/txt_message',[('message'):'Invalid credentials']), 5)
            if (isErrorMessagePresent) {
                KeywordUtil.markPassed("Login failed for user: " + username + " due to invalid credentials")
            } else {
                KeywordUtil.markFailed("Login failed for user: " + username + " - Unknown error")
            }
            WebUI.closeBrowser()
        }
    }
}
}
