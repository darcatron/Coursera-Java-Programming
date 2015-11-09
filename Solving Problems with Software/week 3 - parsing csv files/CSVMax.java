/**
 * Find the highest (hottest) temperature in any number of files of CSV weather data chosen by the user.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class CSVMax {
	private CSVRecord hottestHourInFile(CSVParser parser) {
		CSVRecord largestSoFar = null;

		//For each row (currentRow) in the CSV File
		for (CSVRecord currentRow : parser) {
			largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
		}

		return largestSoFar;
	}


	public CSVRecord hottestInManyDays() {
		CSVRecord largestSoFar = null;
		DirectoryResource dr = new DirectoryResource();
		
		// iterate over files
		for (File f : dr.selectedFiles()) {
			FileResource fr = new FileResource(f);
			CSVRecord currentRow = hottestHourInFile(fr.getCSVParser());

			largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
		}

		return largestSoFar;
	}

	private CSVRecord coldestHourInFile(CSVParser parser) {
		CSVRecord smallestSoFar = null;

		//For each row (currentRow) in the CSV File
		for (CSVRecord currentRow : parser) {
			smallestSoFar = getSmallestOfTwo(currentRow, smallestSoFar, "TemperatureF");
		}

		return smallestSoFar;
	}

	public void fileWithColdestTemp() {
		File coldest_file = null;
		DirectoryResource dr = new DirectoryResource();
		
		// iterate over files
		for (File f : dr.selectedFiles()) {
			coldest_file = coldestFileOfTwo(f, coldest_file);
		}

		System.out.println("Coldest file: " + coldest_file);
		FileResource fr = new FileResource(coldest_file);
		System.out.println("Coldest temp is: " + coldestHourInFile(fr.getCSVParser()).get("TemperatureF"));
	}

	private CSVRecord getLargestOfTwo (CSVRecord currentRow, CSVRecord largestSoFar) {
		if (largestSoFar == null) {
			largestSoFar = currentRow;
		}
		else {
			double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
			double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));

			if (currentTemp > largestTemp) {
				largestSoFar = currentRow;
			}
		}
		return largestSoFar;
	}

	private CSVRecord getSmallestOfTwo (CSVRecord current_row, CSVRecord smallest_so_far, String col_name) {
		if (smallest_so_far == null) {
			smallest_so_far = current_row;
		}
		else {
			if (!current_row.get(col_name).equals("N/A") && !smallest_so_far.get(col_name).equals("N/A")
				&& !current_row.get(col_name).equals("-9999") && !smallest_so_far.get(col_name).equals("-9999")) {
				double current_val = Double.parseDouble(current_row.get(col_name));
				double largest_val = Double.parseDouble(smallest_so_far.get(col_name));

				if (current_val < largest_val) {
					smallest_so_far = current_row;
				}
			}
		}
		return smallest_so_far;
	}

	private File coldestFileOfTwo (File current_file, File smallest_so_far) {
		if (smallest_so_far == null) {
			smallest_so_far = current_file;
		}
		else {
			FileResource cur_file = new FileResource(current_file);
			FileResource smallest_file = new FileResource(smallest_so_far);
			CSVRecord current_row = coldestHourInFile(cur_file.getCSVParser());
			CSVRecord smallest_row = coldestHourInFile(smallest_file.getCSVParser());



			double currentTemp = Double.parseDouble(current_row.get("TemperatureF"));
			double smallestTemp = Double.parseDouble(smallest_row.get("TemperatureF"));

			if (currentTemp < smallestTemp) {
				smallest_so_far = current_file;
			}
		}
		return smallest_so_far;
	}

	private CSVRecord lowestHumidityInFile(CSVParser parser) {
		CSVRecord lowest_so_far = null;

		//For each row (current_row) in the CSV File
		for (CSVRecord current_row : parser) {
			lowest_so_far = getSmallestOfTwo(current_row, lowest_so_far, "Humidity");
		}

		return lowest_so_far;
	}

	public void lowestHumidityInManyFiles() {
		CSVRecord lowest_humidity = null;
		DirectoryResource dr = new DirectoryResource();

		for (File f : dr.selectedFiles()) {
			FileResource fr = new FileResource(f);
			CSVRecord cur_humidity = lowestHumidityInFile(fr.getCSVParser());

			lowest_humidity = getSmallestOfTwo(cur_humidity, lowest_humidity, "Humidity");			
		}

		System.out.println("Lowest Humidity is: " + lowest_humidity.get("Humidity") + " at " + lowest_humidity.get("DateUTC"));
	}

	public void avgTempInFile(int humidity) {
		FileResource fr = new FileResource();
		float total_temp = 0;
		int num_temps = 0;

		for (CSVRecord cur_row : fr.getCSVParser()) {
			if (Double.parseDouble(cur_row.get("Humidity")) >= humidity) {
				total_temp += Double.parseDouble(cur_row.get("TemperatureF"));
				num_temps++;
			}
		}

		if (num_temps == 0) {
			System.out.println("No temps with that humidity");
		}
		else {
			System.out.println("Avg Temp: " + (total_temp / num_temps) + " for humidity " + humidity);
		}
	}
}
