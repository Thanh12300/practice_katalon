package practiceExcelFile


import javax.swing.DefaultRowSorter.Row

import org.apache.poi.sl.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testdata.TestData

import groovyjarjarpicocli.CommandLine.Help.TextTable.Cell







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


	@Keyword
	
		def getDataFromExcel(String filePath) {
			List<Map<String, String>> allData = []  // Danh sách chứa tất cả dữ liệu từ file Excel
			FileInputStream file = new FileInputStream(new File(filePath))
			Workbook workbook = new XSSFWorkbook(file)
	
			for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
				Sheet sheet = workbook.getSheetAt(sheetIndex)
				println("\n=== Đọc dữ liệu từ Sheet: " + sheet.getSheetName() + " ===")
	
				Map<String, Integer> headerIndex = [:] // Lưu vị trí cột theo tên cột
				Row headerRow = sheet.getRow(0) // Lấy hàng đầu tiên làm Header
	
				if (headerRow == null) {
					println("Sheet ${sheet.getSheetName()} không có dữ liệu")
					continue
				}
	
				// 🔹 BƯỚC 1: Lấy vị trí của các cột theo tiêu đề
				for (Cell cell : headerRow) {
					headerIndex[cell.toString()] = cell.columnIndex
				}
				println("📌 Header: ${headerIndex}")
	
				// 🔹 BƯỚC 2: Lặp qua từng hàng và lấy dữ liệu
				for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
					Row row = sheet.getRow(rowIndex)
					if (row == null) continue // Bỏ qua hàng trống
	
					Map<String, String> rowData = [:] // Lưu dữ liệu của từng dòng
					headerIndex.each { columnName, columnIndex ->
						Cell cell = row.getCell(columnIndex)
						rowData[columnName] = (cell != null) ? cell.toString().trim() : ""
					}
					allData.add(rowData) // Thêm dòng dữ liệu vào danh sách
				}
			}
	
			workbook.close()
			file.close()
	
			return allData // Trả về toàn bộ dữ liệu từ Excel dưới dạng danh sách Map
		}
	

	@Keyword
	def getDataByColumnName(String filePath, String nameCol) {
		TestData data = findTestData(filePath)
		ArrayList<String> valueOfCol = new ArrayList()
		for(int i = 1; i <= data.size; i++){
			String value = data.getObjectValue(nameCol,i)
			if(value!=null && !value.trim().isEmpty()) {
				valueOfCol.add(value)
			}
		}
		return valueOfCol
	}

	@Keyword
    def writeInExcel(String filePath, String sheetName, List<List<String>> data) {
        File file = new File(filePath)
        Workbook workbook
        Sheet sheet

        // Kiểm tra file có tồn tại không
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file)
            workbook = new XSSFWorkbook(fis)
            sheet = workbook.getSheet(sheetName)
            if (sheet == null) {
                sheet = workbook.createSheet(sheetName) // Tạo sheet mới nếu chưa có
            }
            fis.close()
        } else {
            workbook = new XSSFWorkbook()
            sheet = workbook.createSheet(sheetName)
        }

        // Ghi dữ liệu vào sheet
        int rowNum = sheet.getLastRowNum() + 1 // Bắt đầu từ dòng tiếp theo
        for (List<String> rowData : data) {
            Row row = sheet.createRow(rowNum++)
            int cellNum = 0
            for (String cellValue : rowData) {
                Cell cell = row.createCell(cellNum++)
                cell.setCellValue(cellValue)
            }
        }

        // Ghi dữ liệu vào file Excel
        FileOutputStream fos = new FileOutputStream(file)
        workbook.write(fos)
        workbook.close()
        fos.close()

        println("Ghi dữ liệu vào Excel thành công: ${filePath}")
    }

	
}
