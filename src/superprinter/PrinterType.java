package superprinter;

public enum PrinterType {
    PRINTER_1,
    PRINTER_2,
    PRINTER_FISCAL;


    @Override
    public String toString() {
        switch (this) {
            case PRINTER_1:
                System.out.println("Printer 1");
                break;
            case PRINTER_2:
                System.out.println("Printer 2");
                break;
            case PRINTER_FISCAL:
                System.out.println("Printer Fiscal");
                break;
        }
        return super.toString();
    }



};