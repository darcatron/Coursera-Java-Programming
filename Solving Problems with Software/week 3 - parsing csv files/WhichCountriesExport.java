/**
 * Reads a chosen CSV file of country exports and prints each country that exports coffee.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.lang.*;

public class WhichCountriesExport {
	private void listExporters(CSVParser parser, StorageResource exportsOfInterest) {
		String export = null;
		String country = null;
		boolean exporting_country;
		int num_exporting_countries = 0;

		//for each row in the CSV File
		for (CSVRecord record : parser) {
			//Look at the "Exports" column
			export = record.get("Exports");
			exporting_country = true;

			//Check if it contains each item in exportsOfInterest
			for (String item : exportsOfInterest.data()) {
				if (!export.contains(item)) {
					exporting_country = false;
					break;
				}
			}
			if (exporting_country == true) {
				//write down the "Country" from that row
				country = record.get("Country");
				System.out.println(country);
				num_exporting_countries++;
			}
		}

		System.out.println("Num Exporting Countries: " + num_exporting_countries);
	}

	/* Prints countries that export all the items*/
	public void whoExports() {
		FileResource fr = new FileResource();
		CSVParser parser = fr.getCSVParser();
		StorageResource items = new StorageResource();

		items.add("cocoa");
		listExporters(parser, items);
	}

	public void getCountryInfo(String country) {
		FileResource fr = new FileResource();
		CSVParser parser = fr.getCSVParser();
		countryInfo(parser, country);
	}

	private void countryInfo (CSVParser parser, String country_to_find) {
		boolean no_countries_found = true;

		//for each row in the CSV File
		for (CSVRecord record : parser) {
			//Look at the "Country" column
			String country_on_rec = record.get("Country");
			//Check if it contains exportOfInterest
			if (country_on_rec.equals(country_to_find)) {
				//If so, write down the country info from that row
				System.out.println(country_to_find + ": " + record.get("Exports") + ": " + record.get("Value (dollars)"));
				no_countries_found = false;
			}
		}

		if (no_countries_found) {
			System.out.println("NOT FOUND");
		}
	}

	public void bigExporters(String amount_to_find) {
		FileResource fr = new FileResource();
		CSVParser parser = fr.getCSVParser();

		for (CSVRecord record : parser) {
			String amount_on_rec = record.get("Value (dollars)");

			if (amount_on_rec.length() > amount_to_find.length()) {
				System.out.println(record.get("Country") + " " + record.get("Value (dollars)"));
			}
		}
	}
}
