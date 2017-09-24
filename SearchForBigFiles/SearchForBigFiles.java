/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package odaiproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author mubarak
 */
public class SearchForBigFiles {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("/Users/mubarknawar");
        File list[] = file.listFiles();
        ArrayList path = new ArrayList();

        PrintWriter input = new PrintWriter("Unsorted.txt");

        //Read all the files in the list
        for (int i = 0; i < list.length; i++) {

            long t = list[i].length() / 1024;
            long t2 = t / 1024;
            long t3 = t2 / 1024;
            //if the file's size is greater than 1 GB, print its name and size
            if (t3 > 1) {
                input.print(list[i].getName() + ",," + t3 + ",,GB" + "\n");
                //else if the file is Directory, get its path and store it in the arraylist
            } else if (list[i].isDirectory() == true) {

                path.add(list[i].getPath());

            }

        }
        ///// Finished getting the files 

        while (!path.isEmpty()) {

            searchDirectory(path, input);

        }

        input.flush();
        input.close();
        //Finished Part 1
        /// -----------------------------------//

        //Part 2 , from the txt file generated, I will sort this file by the files size
        sorting();

    }

    public static void searchDirectory(ArrayList path, PrintWriter input) {
        /// Reading the file path from the arraylist, Repeat what was done in Part 1
        String path2 = (String) path.get(0);
        File file = new File(path2);
        File list[] = file.listFiles();

        for (int i = 0; i < list.length; i++) {

            long t = list[i].length() / 1024;
            long t2 = t / 1024;
            long t3 = t2 / 1024;
            if (t3 > 1) {
                input.print(list[i].getName() + ",," + t3 + ",,GB" + "\n");

            } else if (list[i].isDirectory() == true) {

                path.add(list[i].getPath());

            }

        }
        //After finishing reading the path, delete it from the arraylist
        path.remove(0);

    }

    public static void sorting() throws FileNotFoundException {

        //I will read the unsorted file line by line and store it into an arraylist
        File file = new File("Unsorted.txt");

        Scanner unsorted = new Scanner(file);

        ArrayList names = new ArrayList();
        ArrayList size = new ArrayList();

        //Reading the txt file
        while (unsorted.hasNext()) {

            String line = unsorted.nextLine();
            String[] tst = line.split(",,");
            names.add(tst[0]);
            size.add(Integer.parseInt(tst[1]));

        }

        //copy the items in the arraylist of the names to a new String array of names and int array of size
        String names2[] = new String[names.size()];
        int size2[] = new int[size.size()];
        //copy the items from the arraylist to the array
        for (int i = 0; i < size2.length; i++) {
            size2[i] = (int) size.get(i);
        }
        names2 = (String[]) names.toArray(names2);

        //now I sort the two arrays 
        for (int i = 1; i < size2.length; i++) {
            int temp = size2[i];
            String tempNames = names2[i];
            int j;
            for (j = i - 1; j >= 0 && temp < size2[j]; j--) {
                size2[j + 1] = size2[j];
                size2[j] = temp;

                names2[j + 1] = names2[j];
                names2[j] = tempNames;

            }
        }

        //now write the arrays again into a text file 
        File finish = new File("sorted.txt");

        PrintWriter output = new PrintWriter(finish);
        output.print("File                                  size\n");
        for (int i = size2.length - 1; i >= 0; i--) {

            output.print(names2[i] + "        " + size2[i] + "GB\n");
        }
        output.flush();
        output.close();

    }

}
