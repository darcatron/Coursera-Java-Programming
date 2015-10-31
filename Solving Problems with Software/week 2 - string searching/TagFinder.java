/**
 * Finds a protein within a strand of DNA represented as a string of c,g,t,a letters.
 * A protein is a part of the DNA strand marked by start and stop codons (DNA triples)
 * that is a multiple of 3 letters long.
 *
 * @author Duke Software Team 
 */
import edu.duke.*;
import java.io.*;

public class TagFinder {
    public String findProtein(String dna) {
        dna = dna.toLowerCase();
        int start = dna.indexOf("atg");
        int stop_tag = dna.indexOf("tag", start + 3);
        int stop_tga = dna.indexOf("tga", start + 3);
        int stop_taa = dna.indexOf("taa", start + 3);
        int smallest_gene = Integer.MAX_VALUE;
        int smallest_stop = 0;

        while (true) {
            if (start == -1) {
                // no start codon found
                return "";
            }
            if (stop_tag == -1 && stop_tga == -1 && stop_taa == -1) {
                // no stop codon found
                return "";
            }
            
            if (stop_tag != -1) {
                if ((stop_tag - start) % 3 == 0) {
                    String gene = dna.substring(start, stop_tag + 3);

                    if (gene.length() < smallest_gene) {
                        smallest_gene = gene.length();
                        smallest_stop = stop_tag;
                    }
                }
            }

            if (stop_tga != -1) {
                if ((stop_tga - start) % 3 == 0) {
                    String gene = dna.substring(start, stop_tga + 3);

                    if (gene.length() < smallest_gene) {
                        smallest_gene = gene.length();
                        smallest_stop = stop_tga;
                    }
                }
            }

            if (stop_taa != -1) {
                if ((stop_taa - start) % 3 == 0) {
                    String gene = dna.substring(start, stop_taa + 3);

                    if (gene.length() < smallest_gene) {
                        smallest_gene = gene.length();
                        smallest_stop = stop_taa;
                    }
                }
            }
            if (smallest_stop != 0) {
                System.out.println(dna.substring(start, smallest_stop + 3));
            }

            if (smallest_stop == 0) {
                start = dna.indexOf("atg", start + 3);
            }
            else {
                start = dna.indexOf("atg", smallest_stop + 3);
            }
            stop_tag = dna.indexOf("tag", start + 3);
            stop_tga = dna.indexOf("tga", start + 3);
            stop_taa = dna.indexOf("taa", start + 3);
            smallest_gene = Integer.MAX_VALUE;
            smallest_stop = 0;
        }
    }
    
    
    public void testing() {
        String a = "cccatggggtttaaataataataggagagagagagagagttt";
        String ap = "atggggtttaaataataatag";
        //String a = "atgcctag";
        //String ap = "";
        //String a = "ATGCCCTAG";
        //String ap = "ATGCCCTAG";
        String result = findProtein(a);
        if (ap.equals(result)) {
            System.out.println("success for " + ap + " length " + ap.length());
        }
        else {
            System.out.println("mistake for input: " + a);
            System.out.println("got: " + result);
            System.out.println("not: " + ap);
        }
    }

    public void realTesting() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            String s = fr.asString();
            System.out.println("read " + s.length() + " characters");
            String result = findProtein(s);
            System.out.println("found " + result);
        }
    }
}
