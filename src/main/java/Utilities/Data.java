package Utilities;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Data {

	XSSFSheet sheet = null;
	FileInputStream file = null;
	XSSFWorkbook workbook = null;
	List<List<Object>> data = null;
	int testCaseRow = -1;
	int testCaseDataRow = -1;
	public final String PATH = "./src/test/resources/TestData.xlsx";

	public Data(String sheetName) {
		this.data = Util.GetExcelTableInto2DArrayListString(PATH, sheetName);
	}

	public String get(String Label) {
		for (int i = 0; i < data.get(testCaseRow).size(); i++) {
			String colName = data.get(testCaseRow).get(i).toString();
			if (colName.equals(Label)) {
				return data.get(testCaseDataRow).get(i).toString();
			}
		}
		return "";
	}

	public void setIndex(String testDataSet) {
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).get(1).toString().equals(testDataSet)) {
				this.testCaseDataRow = i;
				break;
			}
		}
	}

	public void setColIndex(String testCaseName) {
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).get(1).toString().equals(testCaseName)) {
				this.testCaseRow = i;
				break;
			}
		}
	}

	public ArrayList<String> getDataSets(String testCaseName) {
		ArrayList<String> dataSets = new ArrayList();
		try {
		for (int i = 0; i < data.size(); i++) {
			if ((data.get(i).get(1).toString().contains(testCaseName))
					&& (data.get(i).get(2).toString().equalsIgnoreCase("yes"))) {
				dataSets.add(data.get(i).get(1).toString());
			}
		}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return dataSets;
	}
}
