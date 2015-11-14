/**
 * Print out total number of babies born, as well as for each gender, in a given CSV file of baby name data.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class BabyBirths {
	int COL_NAME = 0;
	int COL_GENDER = 1;
	int COL_NUM_BORN = 2;
	String PATH_NAMES_BY_YEAR = "C:/Users/Mathurshan/Desktop/School Assignments/Coursera/Java/Solving Problems with Software/week 4 - baby names project/us_babynames_by_year/";

	public void printInfo() {
		FileResource fr = new FileResource();

		for (CSVRecord rec : fr.getCSVParser(false)) {
			int num_born = Integer.parseInt(rec.get(COL_NUM_BORN));
			if (num_born <= 100) {
				System.out.println("Name " + rec.get(COL_NAME) +
						   " Gender " + rec.get(COL_GENDER) +
						   " Num Born " + rec.get(COL_NUM_BORN));
			}
		}
	}

	public void totalBirths(int year) {
		FileResource fr = new FileResource(PATH_NAMES_BY_YEAR + "yob" + year + ".csv");
		int total_boys = 0;
		int total_girls = 0;
		int num_boys_names = 0;
		int num_girls_names = 0;

		for (CSVRecord rec : fr.getCSVParser(false)) {
			int num_born = Integer.parseInt(rec.get(COL_NUM_BORN));

			if (rec.get(COL_GENDER).equals("M")) {
				total_boys += num_born;
				num_boys_names++;
			}
			else {
				total_girls += num_born;
				num_girls_names++;
			}
		}

		System.out.println("num females: " + total_girls + " num female names: " + num_girls_names);
		System.out.println("total males: " + total_boys + " num male names: " + num_boys_names);
		System.out.println("total births: " + (total_boys + total_girls) + " total names: " + (num_boys_names + num_girls_names));
	}

	public int getRank(int year, String name, String gender) {
		FileResource fr = new FileResource(PATH_NAMES_BY_YEAR + "yob" + year + ".csv");
		int rank = -1;
		int cur_col = 1;

		for (CSVRecord rec : fr.getCSVParser(false)) {
			if (rec.get(COL_GENDER).equals(gender)) {
				if (rec.get(COL_NAME).equals(name)) {
					rank = cur_col;
				}
				cur_col++;			
			}
		}

		System.out.println("rank of " + name + ": " + rank);
		return rank;
	}

	public String getName(int year, int rank, String gender) {
		FileResource fr = new FileResource(PATH_NAMES_BY_YEAR + "yob" + year + ".csv");
		int cur_col = 1;
		String name = "NO NAME";

		for (CSVRecord rec : fr.getCSVParser(false)) {
			if (rec.get(COL_GENDER).equals(gender)) {
				if (cur_col == rank) {
					name = rec.get(COL_NAME);
				}
				cur_col++;
			}
		}

		System.out.println("name of rank " + rank + ": " + name);
		return name;
	}

	public void whatIsNameInYear(String name, int year, int new_year, String gender) {
		int rank = getRank(year, name, gender);
		String new_name = getName(new_year, rank, gender);

		System.out.println(name + " in " + year + " => " + new_name + " in " + new_year);
	}

	public int yearOfHighestRank(String name, String gender) {
		DirectoryResource dr = new DirectoryResource();
		int year_of_best_rank = -1;
		int cur_rank = Integer.MAX_VALUE;
		int file_year = -1;
		int file_rank = -1;

		for (File f : dr.selectedFiles()) {
			FileResource fr = new FileResource(f);
			file_year = Integer.parseInt(f.getName().substring(3,7));
			file_rank = getRank(file_year, name, gender);

			if (file_rank < cur_rank) {
				year_of_best_rank = file_year;
				cur_rank = file_rank;
			}
		}

		return year_of_best_rank;
	}

	public float getAverageRank(String name, String gender) {
		DirectoryResource dr = new DirectoryResource();
		int num_files = 0;
		float total_rank = 0;
		int file_year = -1;

		for (File f : dr.selectedFiles()) {
			FileResource fr = new FileResource(f);

			file_year = Integer.parseInt(f.getName().substring(3,7));
			total_rank += getRank(file_year, name, gender);
			num_files++;
		}

		return (total_rank / num_files);
	}

	public int getTotalBirthsRankedHigher(int year, String name, String gender) {
		FileResource fr = new FileResource(PATH_NAMES_BY_YEAR + "yob" + year + ".csv");
		int cur_col = 1;
		int total_births = 0;
		int rank = getRank(year, name, gender);

		for (CSVRecord rec : fr.getCSVParser(false)) {
			if (rec.get(COL_GENDER).equals(gender)) {
				if (cur_col < rank) {
					total_births += Integer.parseInt(rec.get(COL_NUM_BORN));
				}
				cur_col++;
			}
		}

		return total_births;
	}
}
