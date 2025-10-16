package ductv.example;

import java.util.logging.Logger;

// Abstraction
interface Printer {
    void print(String message);
}

// Console implementation using Logger (không dùng System.out)
class ConsolePrinter implements Printer {
    private static final Logger logger = Logger.getLogger(ConsolePrinter.class.getName());

    @Override
    public void print(String message) {
        // logger thay cho System.out
        logger.info(message);
    }
}

// Report không khởi tạo Printer trực tiếp nữa — dependency injected
class Report {
    private final Printer printer;

    // constructor injection giúp dễ test & mở rộng
    public Report(Printer printer) {
        this.printer = printer;
    }

    public void generate() {
        String output = "Generating report...";
        // Report chỉ lo tạo nội dung; việc xuất do Printer xử lý
        printer.print(output);
    }

    // ví dụ main demo
    public static void main(String[] args) {
        Printer printer = new ConsolePrinter();
        Report report = new Report(printer);
        report.generate();
    }
}
