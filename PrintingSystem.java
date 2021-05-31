/**
 * ********************************************************************
 * File:      PrintingSystem.java (Class)
 * Author:    Duneesha Suloshini (w1697801/2017336)
 * Contents:  6SENG002W CWK
 * A class to represent the Printing System of all the clients of
 * the printing system.
 * ***********************************************************************
 */


package CommonPrinterSystem;

public class PrintingSystem {

    public static void main(String[] args) {

        //Creates 2 thread groups: one for the students & one for the technicians
        ThreadGroup studentGroup = new ThreadGroup( "Initializing Student Thread Group");
        System.out.println("[SHARED_PRINTING_SYSTEM] - " + studentGroup.getName());
        ThreadGroup technicianGroup = new ThreadGroup("Initializing Technician Thread Group");
        System.out.println("[SHARED_PRINTING_SYSTEM] - " + technicianGroup.getName());

        //One instance of the LaserPrinter class
        //This printer object is to be shared by the students & the two technicians.
        LaserPrinter laserPrinterobj = new LaserPrinter("HP-OFFICEJET5230", studentGroup);
        System.out.println("[SHARED_PRINTING_SYSTEM] - Initializing printer");


        //creating four students for printing Documents
        Student student1 = new Student("DUNEESHA", studentGroup,laserPrinterobj);
        System.out.println("[SHARED_PRINTING_SYSTEM] - Initializing student: " + student1.getName());
        Student student2 = new Student("SUMUDU", studentGroup,laserPrinterobj);
        System.out.println("[SHARED_PRINTING_SYSTEM] - Initializing student: " + student2.getName());
        Student student3 = new Student("NEYTHAN", studentGroup,laserPrinterobj);
        System.out.println("[SHARED_PRINTING_SYSTEM] - Initializing student: " + student3.getName());
        Student student4 = new Student("RANDIMA", studentGroup,laserPrinterobj);
        System.out.println("[SHARED_PRINTING_SYSTEM] - Initializing student: " + student4.getName());

        //creating technician threads (paper & toner technician)
        Technician paperTechnician = new PaperTechnician("Paper Technician - VIHAN", technicianGroup, laserPrinterobj);
        System.out.println("[SHARED_PRINTING_SYSTEM] - Initializing " + paperTechnician.getName());
        Technician tonerTechnician = new TonerTechnician("Toner technician - HIMASHIE", technicianGroup, laserPrinterobj);
        System.out.println("[SHARED_PRINTING_SYSTEM] - Initializing " + tonerTechnician.getName());

        //starting all student threads
        student1.start();
        System.out.println("[SHARED_PRINTING_SYSTEM] - Started student: " + student1.getName());
        student2.start();
        System.out.println("[SHARED_PRINTING_SYSTEM] - Started student: " + student2.getName());
        student3.start();
        System.out.println("[SHARED_PRINTING_SYSTEM] - Started student: " + student3.getName());
        student4.start();
        System.out.println("[SHARED_PRINTING_SYSTEM] - Started student: " + student4.getName());

        //starting all technician threads
        paperTechnician.start();
        System.out.println("[SHARED_PRINTING_SYSTEM] - Started paper technician: " + paperTechnician.getName());
        tonerTechnician.start();
        System.out.println("[SHARED_PRINTING_SYSTEM] - Started toner technician: " + tonerTechnician.getName());

        try {

            student1.join();
            System.out.println("[SHARED_PRINTING_SYSTEM] -  " + student1.getName() + " completed execution. ");
            student2.join();
            System.out.println("[SHARED_PRINTING_SYSTEM] -  " + student2.getName() + " completed execution. ");
            student3.join();
            System.out.println("[SHARED_PRINTING_SYSTEM] -  " + student3.getName() + " completed execution. ");
            student4.join();
            System.out.println("[SHARED_PRINTING_SYSTEM] -  " + student4.getName() + " completed execution. ");

            paperTechnician.join();
            System.out.println("[SHARED_PRINTING_SYSTEM] -  " + paperTechnician.getName() + " completed execution. ");
            tonerTechnician.join();
            System.out.println("[SHARED_PRINTING_SYSTEM] -  " + tonerTechnician.getName() + " completed execution. ");

            System.out.println("[SHARED_PRINTING_SYSTEM] -  Tasks completed successfully.." + laserPrinterobj.toString() );


        }catch (InterruptedException exception){

            System.out.println(exception.toString());
        }



    }
}
