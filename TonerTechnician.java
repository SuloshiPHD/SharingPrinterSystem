/**
 * ********************************************************************
 * File:      TonerTechnician.java (Class)
 * Author:    Duneesha Suloshini (w1697801/2017336)
 * Contents:  6SENG002W CWK
 *  A class to represent a technician that replaces the printer's toner cartridge
 *  This class is a Thread.
 * ***********************************************************************
 */

package CommonPrinterSystem;

import java.util.Random;

public class TonerTechnician extends Technician {


    public TonerTechnician(String name, ThreadGroup group, LaserPrinter printer) {
        super(name, group, printer);
    }

    @Override
    public void run() {

        for (int i =0; i<3; i++){

            try{
                System.out.println("[TONER_TECHNICIAN] - Requested to replace toner");
                this.printer.replaceTonerCartridge();
                System.out.println("[TONER_TECHNICIAN] - Printer Status. " + printer.toString());
                sleep(GenerateRandomSleepTime());

            }catch(InterruptedException exception){

                System.out.println(exception.toString());
            }
        }
    }


    private int GenerateRandomSleepTime(){

        return new Random().nextInt(2000 - 1000 + 1 ) + 1000;
    }
}
