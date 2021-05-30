/**
 * ********************************************************************
 * File:      PaperTechnician.java (Class)
 * Author:    Duneesha Suloshini (w1697801/2017336)
 * Contents:  6SENG002W CWK
 * A class to represent a paper technician that can refill the printer's
 * paper trays. This class is a Thread.
 * ***********************************************************************
 */

package CommonPrinterSystem;

import java.util.Random;

public class PaperTechnician extends Technician {

    /**
     *  creating single constructor to keep private data members that hold the information associated with
     *  paper technician.
     */
    public PaperTechnician(String name, ThreadGroup group, LaserPrinter printer) {

        super(name, group, printer);
    }

    //Attempt to refill the printer's paper trays three times
    @Override
    public void run() {

        for(int j= 0; j<3; j++){

            try{
                System.out.println("[PAPER_TECHNICIAN] - Requested to refill paper.");
                this.printer.refillPaper();
                System.out.println("[PAPER_TECHNICIAN] - Printer Status :  " + printer.toString());

                // "sleep" for a random amount of time between each attempt to refill the paper.
                sleep(GenerateRandomSleepTime());

            }catch (InterruptedException exception){

                System.out.println(exception.toString());
            }
        }
    }

    /**
     * generating random sleep time duration
     * @return duration of sleep time
     */

    private int GenerateRandomSleepTime(){

        return new Random().nextInt(2000-1000+1)+1000;
    }
}
