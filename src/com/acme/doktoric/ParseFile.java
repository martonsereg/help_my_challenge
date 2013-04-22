package com.acme.doktoric;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ParseFile {

	/**
	 * @param args
	 */

	public static WritableCellFormat timesBoldUnderline;
	public static WritableCellFormat times;
	public static String inputFile;

	public static void write() throws IOException, WriteException {
		File file = new File("c://result.xls");
		List<FileObj> objs = getObjects();

		WorkbookSettings wbSettings = new WorkbookSettings();

		wbSettings.setLocale(new Locale("en", "EN"));

		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);

		createLabel(workbook, objs);

		workbook.write();
		workbook.close();
	}

	public static void createLabel(WritableWorkbook workbook, List<FileObj> objs)
			throws WriteException {
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		// Define the cell format
		times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);

		// Create create a bold font with unterlines
		WritableFont times10ptBoldUnderline = new WritableFont(
				WritableFont.TIMES, 10, WritableFont.BOLD, false,
				UnderlineStyle.SINGLE);
		timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
		// Lets automatically wrap the cells
		timesBoldUnderline.setWrap(true);

		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setFormat(timesBoldUnderline);
		cv.setAutosize(true);

		// Write a few headers
		int i = 0;
		int sheetnumber = 0;
		workbook.createSheet("Report", sheetnumber);
		WritableSheet sheet = workbook.getSheet(sheetnumber);
		String hour = "";
		sheetnumber++;
		for (FileObj obj : objs) {
			if (hour == "") {
				hour = getDay(obj.getText());
			}  if (!hour.equals(getDay(obj.getText()))) {
				
				workbook.createSheet("Report"+sheetnumber, sheetnumber);
				sheet = workbook.getSheet(sheetnumber);
				hour = getDay(obj.getText());
				i = 0;
				addLabel(sheet, 0, i, obj.getText());
				addLabel(sheet, 1, i, obj.NumasString());
				sheetnumber++;
			} else {
				System.out.println(obj.getText() + ": " + obj.getNum());
				addLabel(sheet, 0, i, obj.getText());
				addLabel(sheet, 1, i, obj.NumasString());
				i++;
			}
		}

	}

	public static String getDay(String text) {
		try {
			String[] block = text.split(" ");
			String hour = block[0];
			//block = hour.split(":");
			//hour = block[0];
			return hour;
		} catch (Exception ex) {
			System.out.println("pina");
		}
		return "";

	}

	public static void addLabel(WritableSheet sheet, int column, int row,
			String s) throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, s, times);
		sheet.addCell(label);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			write();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void WriteToFile(List<FileObj> objs) {
		try {

			String content = "This is the content to write into file";

			File file1 = new File("c:\\Users\\Ricsi\\Desktop\\week_1_dates.log");
			File file2 = new File(
					"c:\\Users\\Ricsi\\Desktop\\week_1_values.log");

			// if file doesnt exists, then create it
			if (!file1.exists()) {
				file1.createNewFile();
			}
			if (!file2.exists()) {
				file2.createNewFile();
			}

			FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
			FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
			BufferedWriter bw1 = new BufferedWriter(fw1);
			BufferedWriter bw2 = new BufferedWriter(fw2);
			for (FileObj fileObj : objs) {
				bw1.write(fileObj.getText());
				bw2.write(fileObj.getNum());
			}
			bw1.close();
			bw2.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<FileObj> getObjects() {
		List<FileObj> objs = new ArrayList<FileObj>();
		List<String> patterns = new ArrayList<String>();
		File file = new File("c:\\Users\\Ricsi\\Desktop\\week_2.log");
		Scanner input;
		try {
			input = new Scanner(file);
			while (input.hasNext()) {
				String nextLine = input.nextLine();
				String[] blocks = nextLine.split(" ");
				String[] times = blocks[1].split(":");

				String pattern = blocks[0] + " " + times[0] + ":" + times[1];

				if (patterns.indexOf(pattern) == -1) {
					patterns.add(pattern);
					objs.add(new FileObj(pattern, 1));
				} else {
					int i = patterns.indexOf(pattern);
					objs.get(i).setNum(objs.get(i).getNum() + 1);
				}

				System.out.println(pattern);
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return objs;
	}

}
