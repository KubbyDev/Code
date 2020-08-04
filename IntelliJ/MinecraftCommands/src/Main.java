public class Main {

    public static void main(String... args) {

        // Initialises everything
        CommandSupplier supplier = CommandSupplier.fromFile(args[0]);
        Bot bot = new Bot();

        // Small delay to let the user select the minecraft window
        for(int i = 3; i >= 1; i--) {
            System.out.println("Starting in "+ i + "...");
            sleep(1000);
        }
        System.out.println("Starting...");

        // Types the commands
        while(!supplier.empty()) {
            String command = supplier.next();
            System.out.println(command);
            bot.typeCommand(command);
            sleep(200);
        }
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) { e.printStackTrace(); }
    }

}
