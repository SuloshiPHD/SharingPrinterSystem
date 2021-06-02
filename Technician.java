package CommonPrinterSystem;

public class Technician extends Thread{

    protected LaserPrinter printer;
    protected String name;

    public Technician(String name, ThreadGroup group,LaserPrinter printer) {
        super(group,name);
        this.printer = printer;
    }


}
