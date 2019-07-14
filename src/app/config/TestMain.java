package app.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;

import com.univocity.parsers.csv.*;
import com.univocity.parsers.tsv.*;

import app.dao.BookLoanCheckInDAO;

public class TestMain {

    public static void main(String[] args) throws IOException {
	/*
	 * TsvParserSettings settings = new TsvParserSettings();
	 * settings.getFormat().setLineSeparator("\n");
	 * 
	 * TsvParser parser = new TsvParser(settings);
	 * 
	 * File file = new File(
	 * "C:\\Users\\Samiuddin\\Documents\\UTD\\Books and Notes\\Semester 1\\Database Design\\Projects\\ProgrammingAssignment1\\Schema\\latestSchema\\booksTSV.csv"
	 * ); List<String[]> allRows = parser.parseAll(file);
	 * 
	 * System.out.println(allRows.size()); createCSV(allRows);
	 */

    }

    /*
     * public static void createCSV(List<String[]> rows) throws IOException {
     * 
     * BufferedWriter b = new BufferedWriter(new FileWriter(
     * "C:\\Users\\Samiuddin\\Documents\\UTD\\Books and Notes\\Semester 1\\Database Design\\Projects\\ProgrammingAssignment1\\Schema\\latestSchema\\b.tsv"
     * )); BufferedWriter a = new BufferedWriter(new FileWriter(
     * "C:\\Users\\Samiuddin\\Documents\\UTD\\Books and Notes\\Semester 1\\Database Design\\Projects\\ProgrammingAssignment1\\Schema\\latestSchema\\a.txt"
     * )); BufferedWriter ba = new BufferedWriter(new FileWriter(
     * "C:\\Users\\Samiuddin\\Documents\\UTD\\Books and Notes\\Semester 1\\Database Design\\Projects\\ProgrammingAssignment1\\Schema\\latestSchema\\ba.txt"
     * )); int author_id = 1; HashMap<String, Integer> map = new HashMap<String,
     * Integer>(); try {
     * 
     * b.write("isbn" + "\t" + "isbn13" + "\t" + "title" + "\t" + "cover" + "\t" +
     * "publisher" + "\t" + "pages"); a.write("author_id,name");
     * ba.write("isbn,author_id"); b.write("\n"); a.write("\n"); ba.write("\n");
     * CsvWriterSettings settings = new CsvWriterSettings();
     * settings.setNullValue("?"); settings.getFormat().setComment('-');
     * settings.setEmptyValue("!"); settings.setSkipEmptyLines(false);
     * 
     * StringWriter output = new StringWriter(); CsvWriter writer = new
     * CsvWriter(output, settings);
     * 
     * for (int i = 1; i < rows.size(); i++) { String[] temp = rows.get(i); {
     * b.write(temp[0] + "\t" + temp[1] + "\t" + temp[2] + "\t" + temp[4] + "\t" +
     * temp[5] + "\t" + temp[6]); b.write("\n"); if (temp[3] != null) { String
     * authors[] = temp[3].split(","); for (int j = 0; j < authors.length; j++) { if
     * (map.get(authors[j]) == null) { a.write(author_id + ", " + authors[j]);
     * ba.write(temp[0] + ", " + author_id); a.write("\n"); ba.write("\n");
     * map.put(authors[j], author_id); author_id = author_id + 1; } else {
     * System.out.println("duplicate found: " + authors[j]); int aid =
     * map.get(authors[j]); ba.write(temp[0] + ", " + author_id); ba.write("\n"); }
     * } } } writer.writeRow(rows.get(i)); } } catch (Exception e) {
     * e.printStackTrace(); } finally { b.close(); a.close(); ba.close(); }
     * 
     * }
     */

}
