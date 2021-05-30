/**
 * ********************************************************************
 * File:      LaserPrinter.java (Class)
 * Author:    Duneesha Suloshini (w1697801/2017336)
 * Contents:  6SENG002W CWK
 * This model a shared printer and is a MONITOR not a thread.
 * Implements the Serviceprinter interface
 * ***********************************************************************
 */

package CommonPrinterSystem;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LaserPrinter implements ServicePrinter {

    /**
     * declaring variables that holds information associated
     * with the printer
     */
    private int currentLevelOfPaper;
    private int currentLevelOfToner;
    private int printedDocumentCount;
    private String printer_IDName;
    private ThreadGroup studentGrp;

    /**
     * creating ReEntrant lock object which provides equal chance for all threads (first come first serve)
     * fairness true lock
     */
    private Lock reentrantLock = new ReentrantLock(true);
    //creating condition to coordinate between threads
    private Condition conditionMet = reentrantLock.newCondition();


    // A single constructor that initializes the printer.
    public LaserPrinter(String printer_IDName, ThreadGroup studentGrp) {
        this.currentLevelOfPaper = ServicePrinter.Full_Paper_Tray;
        this.currentLevelOfToner = ServicePrinter.Full_Toner_Level;
        this.printedDocumentCount = 0;
        this.printer_IDName = printer_IDName;
        this.studentGrp = studentGrp;
    }

    @Override
    public void replaceTonerCartridge() {
        reentrantLock.lock();
        /** when the toner level is increased than the minimum toner level,
         it cannot be replaced, as it would be a waste of toner, and the
         technician should wait. But he or she is only prepared to wait for 5 seconds
         before checking if it can be replaced it again.
         */

        try {

            while (this.currentLevelOfToner > LaserPrinter.Minimum_Toner_Level) {
                //check whether printer has finished serving all the student group
                if (this.checkActiveStudentCount()) {
                    //displaying the message ; waiting for toner replacement
                    System.out.println("[PRINTER] - Usage of printer is over. No need to replace toner cartridge. ");
                    break;

                } else {

                    //if there is no active student the printing process has finished
                    System.out.println(this.toString());
                    System.out.println("[PRINTER] - Minimum toner level has not reached to refill. Waiting to check again.");
                    displayLogMessage(7,0);
                    conditionMet.await(5, TimeUnit.SECONDS);
                }
            }

            /** when the current toner level decreased to minimum toner level, it is time
             * for toner replacement
             */
            if (this.currentLevelOfToner < LaserPrinter.Minimum_Toner_Level) {
                this.currentLevelOfToner = LaserPrinter.Full_Toner_Level;
                //display toner replacement successful message
                System.out.println("[PRINTER] - Toner cartridge has replaced Successfully....");
                System.out.println(this.toString());

            }
            // notifying all the other threads
            conditionMet.signalAll();

        } catch (InterruptedException ex) {
            System.out.println(ex.toString());
        } finally {
            reentrantLock.unlock();
        }
    }


    @Override
    public void refillPaper() {
        reentrantLock.lock();
        //check for the need to refill papers repeatedly
        try {
            while (this.currentLevelOfPaper + LaserPrinter.SheetsPerPack > LaserPrinter.Full_Paper_Tray) {

                if (this.checkActiveStudentCount()) {
                    //displaying the message: waiting paper refill

                    System.out.println("[PRINTER] - Usage of printer is over. No need to refill paper");
                    break;

                } else {
                    //student has finished printing process
                    System.out.println(this.toString());
                    System.out.println("[PRINTER] - Refilling paper will exceed the capacity. Waiting to check again ");
                    displayLogMessage(7,0);
                    conditionMet.await(5, TimeUnit.SECONDS);
                }

            }
            //refill paper
            if (this.currentLevelOfPaper + LaserPrinter.SheetsPerPack <= LaserPrinter.Full_Paper_Tray) {
                int newLevelOfPaper = currentLevelOfPaper += LaserPrinter.SheetsPerPack;
                //display output message:  paper refill successful message

                System.out.println("[PRINTER] - Paper has refilled Successfully, [ New Paper Level ] : " + newLevelOfPaper);
                System.out.println(this.toString());
            }
            // notifying all the other threads
            conditionMet.signalAll();

        } catch (InterruptedException exception) {
            System.out.println(exception.toString());
        } finally {
            reentrantLock.unlock();
        }

    }


    @Override
    public void printDocument(Document document) {

        reentrantLock.lock();

        /** waiting until there is enough of resources to print
         * If either the paper or toner (or both) are less than number of pages then the document cannot be
         * printed and printing must wait until there is enough of both to print it.
         */
        try {
            System.out.println(this.toString());

            while (this.currentLevelOfPaper < document.getNumberOfPages() || this.currentLevelOfPaper < document.getNumberOfPages()) {
                String displaymsg;
                if (this.currentLevelOfPaper < document.getNumberOfPages()) {
                    displaymsg = "Insufficient Paper level. Waiting until refilled";
                } else if (this.currentLevelOfToner < document.getNumberOfPages()) {
                    displaymsg = "Insufficient Toner level. Waiting until cartridge is replaced.";
                } else {
                    displaymsg = "Insufficient both Paper & Toner level. Waiting until refilled.";
                }
                System.out.println("[PRINTER] - " + displaymsg);
                conditionMet.await();
            }

            //check whether the printer has sufficient resources to print the document
            if (this.currentLevelOfPaper >= document.getNumberOfPages() && this.currentLevelOfToner >= document.getNumberOfPages()) {
                //printing the document
                currentLevelOfPaper -= document.getNumberOfPages();
                currentLevelOfToner -= document.getNumberOfPages();
                printedDocumentCount += 1;
                //showing printing successful message
                System.out.println("[PRINTED DOCUMENT] - " + document);
                System.out.println(this.toString());

            }

            //notifying all the treads
            conditionMet.signalAll();

        } catch (InterruptedException exception) {

            System.out.println(exception.toString());
        } finally {
                reentrantLock.unlock();
        }


    }

    /**
     * check whether active count of the
     * thread group is zero,
     * in oder to prevent from waiting in time laps
     *
     * @return if printer has finished or not serving all the student group
     */
    public boolean checkActiveStudentCount() {

        return studentGrp.activeCount() == 0;
    }

    private void displayLogMessage(int messageNo, int count) {

        switch (messageNo) {
            case 0:
                System.out.println(this.toString());
                break;
            case 1:
                System.out.println("Waiting for documents to print....");
                break;
            case 2:
                System.out.println("Document has printed Successfully....");
                break;
            case 3:
                System.out.println("Waiting for paper to refill....");
                break;
            case 4:
                System.out.println("Paper has refilled Successfully, [ New Paper Level ] : " + count);
                break;
            case 5:
                System.out.println("Waiting for toner replacement.... ");
                break;
            case 6:
                System.out.println("Toner has replaced Successfully....");
                break;
            case 7:
                System.out.println("Student has finished the printing process. ");
                break;

        }
    }

    /**
     * method that returns a String representation of the current state of the
     * printer.
     */
    @Override
    public String toString() {
        return "[ PrinterID: " + printer_IDName +
                ", Paper Level: " + currentLevelOfPaper +
                ", Toner Level: " + currentLevelOfToner +
                ", No of Documents Printed: " + printedDocumentCount +
                " ]";
    }
}
