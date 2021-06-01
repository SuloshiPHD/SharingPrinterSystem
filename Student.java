/** *********************************************************************
 * File:      Student.java (Class)
 * Author:    Duneesha Suloshini (w1697801/2017336)
 * Contents:  6SENG002W CWK
 *  A class to represent a student that can print several documents called Student.
 *  This class is a thread.
 ************************************************************************ */

package CommonPrinterSystem;

import java.util.Random;

public class Student extends Thread {

    private LaserPrinter printer;


    /* using a single constructor to initialize student information
     including the student thread group
     */
    public Student(String name, ThreadGroup threadGroup, LaserPrinter printer) {
        super(threadGroup, name);
        this.printer = printer;
    }

    @Override
    public void run() {
        /* creating five instances to represent documents that the student
        request the printer to print
         */

        for (int x = 0; x < 5; x++ ){
            String docName = "Document_" + ( x + 1 );
            Document document = new Document(this.getName(), docName, GenerateRandomPageCount());
            System.out.println("[STUDENT] - [" + this.getName() + "] Requested to print the document." + document);
            this.printer.printDocument(document);
            System.out.println("[STUDENT] - Printer Status. " + printer.toString());

            try {

                sleep(GenerateRandomSleepTime());

            }catch (InterruptedException exception){

                System.out.println(exception.toString());

            }
        }
    }


    @Override
    public String toString() {
        return "Student Name: " + this.getName() + "\t" + "Finished printing document"; }

    /**
     * Generate random page count within range of 1 to 20 (inclusive) r.nextInt(high-low+1) + low;
     * @return random page count
     */
    public int GenerateRandomPageCount(){
       return new Random().nextInt(19) + 1;
    }

    public int GenerateRandomSleepTime(){
        return new Random().nextInt(2000-1000+1)+1000;
    }
}
