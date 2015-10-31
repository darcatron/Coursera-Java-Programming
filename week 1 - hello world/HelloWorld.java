import edu.duke.*;
import java.io.*;

public class HelloWorld {
	/**
	 * Read file of ways to say hello and print each line of the file
	 */
	public void sayHello(){
		DirectoryResource rs = new DirectoryResource();
		for (File name : rs.selectedFiles()) {
			System.out.println(name);
		}
	}
}
