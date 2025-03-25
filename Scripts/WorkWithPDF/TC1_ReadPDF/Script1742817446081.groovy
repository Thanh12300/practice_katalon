import com.kms.katalon.core.configuration.RunConfiguration

String path = RunConfiguration.getProjectDir() + '/Data Files/'

String pdfPath = "Data Files/dataPDF/dataPDF.pdf"
String pdfText = CustomKeywords.'com.kms.katalon.keyword.pdf.PDF.getAllText'(pdfPath)
List<String> rows = pdfText.split("\n")
List<List<String>> tableData = []
for (String line : rows) {
	List<String> row = line.split(/\s+/)
	tableData.add(row)
}
