import java.io.IOException;

public class ATM {

	public static void main(String[] args) throws IOException {
		OptionMenu optionMenu = new OptionMenu();
		introduction();
		optionMenu.mainMenu();
	}

	public static void introduction() {
		System.out.println("\n" + "=".repeat(80));
		System.out.println("                    SECURE ATM SIMULATION SYSTEM");
		System.out.println("=".repeat(80));
		System.out.println("                        Developed in Java");
		System.out.println("");
		System.out.println("Features:");
		System.out.println("• Secure PIN-based authentication");
		System.out.println("• Account lockout protection");
		System.out.println("• Transaction history tracking");
		System.out.println("• Session timeout security");
		System.out.println("• Checking and Savings accounts");
		System.out.println("• Fund transfers between accounts");
		System.out.println("• Account management tools");
		System.out.println("=".repeat(80));
		System.out.println("Press Enter to continue...");
		try {
			System.in.read();
		} catch (IOException e) {
			// Continue if enter press fails
		}
	}
}
