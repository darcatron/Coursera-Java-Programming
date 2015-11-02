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
    public StorageResource findProtein(String dna) {
        StorageResource genes = new StorageResource();
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
                return genes;
            }
            if (stop_tag == -1 && stop_tga == -1 && stop_taa == -1) {
                // no stop codon found
                return genes;
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
                genes.add(dna.substring(start, smallest_stop + 3));
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
    

    public void realTesting() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            String s = fr.asString();

            StorageResource result = findProtein(s);
            System.out.println("found " + result.size() + " genes");

            int num_over_60 = 0;
            int num_cg_over_35 = 0;
            int num_ctg = (s.length() - s.toLowerCase().replace("ctg", "").length()) / 3;
            System.out.println("found CTG " + num_ctg + " times");            
            int longest_gene = 0;

            for (String gene : result.data()) {
                if (gene.length() > 60) {
                    num_over_60 = num_over_60 + 1;
                }

                float num_c = gene.length() - gene.replace("c", "").length();
                float num_g = gene.length() - gene.replace("g", "").length();
                float ratio = (num_c + num_g) / gene.length();
                if (ratio > .35) {
                    num_cg_over_35 = num_cg_over_35 + 1;
                }

                if (gene.length() > longest_gene) {
                    longest_gene = gene.length();
                }

            }
            System.out.println("found " + num_over_60 + " genes over 60");
            System.out.println("found " + num_cg_over_35 + " genes over .35 cg");
            System.out.println("longest_gene: " + longest_gene);
        }
    }
}
