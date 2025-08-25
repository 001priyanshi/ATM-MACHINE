import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class OptionMenu {
	Scanner menuInput = new Scanner(System.in);
	DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");
	HashMap<Integer, Account> data = new HashMap<Integer, Account>();
	private LocalDateTime sessionStartTime;
	private static final int SESSION_TIMEOUT_MINUTES = 10;
	private static final int MAX_LOGIN_ATTEMPTS = 3;

	public void getLogin() throws IOException {
		boolean end = false;
		int customerNumber = 0;
		int pinNumber = 0;
		int loginAttempts = 0;
		
		displayWelcomeMessage();
		
		while (!end && loginAttempts < MAX_LOGIN_ATTEMPTS) {
			try {
				System.out.print("\nEnter your customer number: ");
				customerNumber = menuInput.nextInt();
				
				// Check if account exists
				if (!data.containsKey(customerNumber)) {
					System.out.println("\nAccount not found. Please check your customer number.");
					loginAttempts++;
					continue;
				}
				
				Account acc = data.get(customerNumber);
				
				// Check if account is locked
				if (acc.isLocked()) {
					System.out.println("\nYour account is locked due to multiple failed login attempts.");
					System.out.println("Please contact customer service to unlock your account.");
					return;
				}
				
				System.out.print("Enter your PIN number: ");
				pinNumber = menuInput.nextInt();
				
				if (acc.validatePin(pinNumber)) {
					acc.resetFailedAttempts();
					sessionStartTime = LocalDateTime.now();
					System.out.println("\nLogin successful! Welcome" + 
						(acc.getAccountHolderName() != null ? ", " + acc.getAccountHolderName() : "") + "!");
					getAccountType(acc);
					end = true;
				} else {
					acc.incrementFailedAttempts();
					loginAttempts++;
					System.out.println("\nIncorrect PIN. Attempt " + loginAttempts + " of " + MAX_LOGIN_ATTEMPTS);
					
					if (acc.isLocked()) {
						System.out.println("Account locked due to multiple failed attempts.");
						return;
					}
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid input. Please enter numbers only.");
				menuInput.next(); // Clear invalid input
				loginAttempts++;
			}
		}
		
		if (loginAttempts >= MAX_LOGIN_ATTEMPTS) {
			System.out.println("\nMaximum login attempts exceeded. Please try again later.");
		}
	}

	public void getAccountType(Account acc) {
		boolean end = false;
		while (!end) {
			// Check session timeout
			if (isSessionExpired()) {
				System.out.println("\nSession expired due to inactivity. Please login again.");
				return;
			}
			
			try {
				displayAccountTypeMenu();
				int selection = menuInput.nextInt();

				switch (selection) {
				case 1:
					getChecking(acc);
					break;
				case 2:
					getSaving(acc);
					break;
				case 3:
					acc.displayAccountSummary();
					break;
				case 4:
					acc.displayTransactionHistory();
					break;
				case 5:
					changePIN(acc);
					break;
				case 6:
					System.out.println("\nThank you for using our ATM service!");
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice. Please select 1-6.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid input. Please enter a number.");
				menuInput.next();
			}
		}
	}

	public void getChecking(Account acc) {
		boolean end = false;
		while (!end) {
			if (isSessionExpired()) {
				System.out.println("\nSession expired. Returning to main menu.");
				return;
			}
			
			try {
				displayCheckingMenu();
				int selection = menuInput.nextInt();

				switch (selection) {
				case 1:
					System.out.println("\nCheckings Account Balance: " + moneyFormat.format(acc.getCheckingBalance()));
					break;
				case 2:
					acc.getCheckingWithdrawInput();
					break;
				case 3:
					acc.getCheckingDepositInput();
					break;
				case 4:
					acc.getTransferInput("Checkings");
					break;
				case 5:
					displayRecentTransactions(acc, "Checking");
					break;
				case 6:
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice. Please select 1-6.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid input. Please enter a number.");
				menuInput.next();
			}
		}
	}

	public void getSaving(Account acc) {
		boolean end = false;
		while (!end) {
			if (isSessionExpired()) {
				System.out.println("\nSession expired. Returning to main menu.");
				return;
			}
			
			try {
				displaySavingsMenu();
				int selection = menuInput.nextInt();
				switch (selection) {
				case 1:
					System.out.println("\nSavings Account Balance: " + moneyFormat.format(acc.getSavingBalance()));
					break;
				case 2:
					acc.getsavingWithdrawInput();
					break;
				case 3:
					acc.getSavingDepositInput();
					break;
				case 4:
					acc.getTransferInput("Savings");
					break;
				case 5:
					displayRecentTransactions(acc, "Savings");
					break;
				case 6:
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice. Please select 1-6.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid input. Please enter a number.");
				menuInput.next();
			}
		}
	}

	public void createAccount() throws IOException {
		int cst_no = 0;
		String accountHolderName = "";
		boolean end = false;
		
		System.out.println("\n" + "=".repeat(50));
		System.out.println("ACCOUNT REGISTRATION");
		System.out.println("=".repeat(50));
		
		// Get account holder name
		System.out.print("Enter your full name: ");
		menuInput.nextLine(); // Clear buffer
		accountHolderName = menuInput.nextLine().trim();
		
		if (accountHolderName.isEmpty()) {
			System.out.println("Name cannot be empty. Registration cancelled.");
			return;
		}
		
		// Get customer number
		while (!end) {
			try {
				System.out.print("Enter desired customer number (6 digits): ");
				cst_no = menuInput.nextInt();
				
				if (String.valueOf(cst_no).length() != 6) {
					System.out.println("Customer number must be exactly 6 digits.");
					continue;
				}
				
				if (data.containsKey(cst_no)) {
					System.out.println("This customer number is already registered. Please choose another.");
				} else {
					end = true;
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter numbers only.");
				menuInput.next();
			}
		}
		
		// Get PIN
		int pin = 0;
		boolean validPin = false;
		while (!validPin) {
			try {
				System.out.print("Enter a 4-digit PIN: ");
				pin = menuInput.nextInt();
				
				if (String.valueOf(pin).length() == 4) {
					System.out.print("Confirm your PIN: ");
					int confirmPin = menuInput.nextInt();
					
					if (pin == confirmPin) {
						validPin = true;
					} else {
						System.out.println("PINs do not match. Please try again.");
					}
				} else {
					System.out.println("PIN must be exactly 4 digits.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter numbers only.");
				menuInput.next();
			}
		}
		
		// Create account with initial deposit option
		System.out.print("Would you like to make an initial deposit? (y/n): ");
		String depositChoice = menuInput.next();
		
		Account newAccount = new Account(cst_no, pin, accountHolderName);
		
		if (depositChoice.toLowerCase().startsWith("y")) {
			makeInitialDeposit(newAccount);
		}
		
		data.put(cst_no, newAccount);
		DataManager.saveAccounts(data); // Save immediately after account creation
		
		System.out.println("\n" + "=".repeat(50));
		System.out.println("ACCOUNT CREATED SUCCESSFULLY!");
		System.out.println("Customer Number: " + cst_no);
		System.out.println("Account Holder: " + accountHolderName);
		System.out.println("=".repeat(50));
		System.out.println("Please login to access your account.");
		
		getLogin();
	}

	public void mainMenu() throws IOException {
		// Load existing accounts or create demo accounts
		data = DataManager.loadAccounts();
		
		// If no accounts exist, create demo accounts
		if (data.isEmpty()) {
			data.put(952141, new Account(952141, 1919, "John Doe", 1000, 5000));
			data.put(123456, new Account(123456, 1234, "Jane Smith", 2500, 7500));
			System.out.println("Demo accounts created.");
		}
		
		boolean end = false;
		while (!end) {
			try {
				displayMainMenu();
				int choice = menuInput.nextInt();
				switch (choice) {
				case 1:
					getLogin();
					break;
				case 2:
					createAccount();
					break;
				case 3:
					displayDemoAccounts();
					break;
				case 4:
					adminMenu();
					break;
				case 5:
					System.out.println("\nSaving account data...");
					DataManager.saveAccounts(data);
					System.out.println("\nThank You for using our ATM service!");
					System.out.println("Have a great day!");
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice. Please select 1-5.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid input. Please enter a number.");
				menuInput.next();
			}
		}
		menuInput.close();
		System.exit(0);
	}

	// Helper Methods
	private boolean isSessionExpired() {
		if (sessionStartTime == null) return false;
		return ChronoUnit.MINUTES.between(sessionStartTime, LocalDateTime.now()) > SESSION_TIMEOUT_MINUTES;
	}

	private void displayWelcomeMessage() {
		System.out.println("\n" + "=".repeat(60));
		System.out.println("           WELCOME TO SECURE ATM SYSTEM");
		System.out.println("=".repeat(60));
	}

	private void displayMainMenu() {
		System.out.println("\n" + "=".repeat(50));
		System.out.println("                MAIN MENU");
		System.out.println("=".repeat(50));
		System.out.println(" 1. Login to Account");
		System.out.println(" 2. Create New Account");
		System.out.println(" 3. View Demo Accounts");
		System.out.println(" 4. Admin Menu");
		System.out.println(" 5. Exit");
		System.out.println("=".repeat(50));
		System.out.print("Choice: ");
	}

	private void displayAccountTypeMenu() {
		System.out.println("\n" + "=".repeat(50));
		System.out.println("              ACCOUNT SERVICES");
		System.out.println("=".repeat(50));
		System.out.println(" 1. Checkings Account");
		System.out.println(" 2. Savings Account");
		System.out.println(" 3. Account Summary");
		System.out.println(" 4. Transaction History");
		System.out.println(" 5. Change PIN");
		System.out.println(" 6. Logout");
		System.out.println("=".repeat(50));
		System.out.print("Choice: ");
	}

	private void displayCheckingMenu() {
		System.out.println("\n" + "=".repeat(50));
		System.out.println("            CHECKINGS ACCOUNT");
		System.out.println("=".repeat(50));
		System.out.println(" 1. View Balance");
		System.out.println(" 2. Withdraw Funds");
		System.out.println(" 3. Deposit Funds");
		System.out.println(" 4. Transfer Funds");
		System.out.println(" 5. Recent Transactions");
		System.out.println(" 6. Back to Main Menu");
		System.out.println("=".repeat(50));
		System.out.print("Choice: ");
	}

	private void displaySavingsMenu() {
		System.out.println("\n" + "=".repeat(50));
		System.out.println("             SAVINGS ACCOUNT");
		System.out.println("=".repeat(50));
		System.out.println(" 1. View Balance");
		System.out.println(" 2. Withdraw Funds");
		System.out.println(" 3. Deposit Funds");
		System.out.println(" 4. Transfer Funds");
		System.out.println(" 5. Recent Transactions");
		System.out.println(" 6. Back to Main Menu");
		System.out.println("=".repeat(50));
		System.out.print("Choice: ");
	}

	private void displayDemoAccounts() {
		System.out.println("\n" + "=".repeat(60));
		System.out.println("                DEMO ACCOUNTS");
		System.out.println("=".repeat(60));
		System.out.println("Customer: 952141 | PIN: 1919 | Name: John Doe");
		System.out.println("Customer: 123456 | PIN: 1234 | Name: Jane Smith");
		System.out.println("=".repeat(60));
	}

	private void makeInitialDeposit(Account account) {
		try {
			System.out.print("Enter initial deposit amount for Checking: $");
			double checkingDeposit = menuInput.nextDouble();
			if (checkingDeposit > 0) {
				account.calcCheckingDeposit(checkingDeposit);
			}

			System.out.print("Enter initial deposit amount for Savings: $");
			double savingsDeposit = menuInput.nextDouble();
			if (savingsDeposit > 0) {
				account.calcSavingDeposit(savingsDeposit);
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid amount entered. Skipping initial deposit.");
			menuInput.next();
		}
	}

	private void displayRecentTransactions(Account acc, String accountType) {
		System.out.println("\nRecent " + accountType + " Account Transactions:");
		System.out.println("-".repeat(50));
		
		var transactions = acc.getTransactionHistory();
		int count = 0;
		for (int i = transactions.size() - 1; i >= 0 && count < 5; i--) {
			Account.Transaction transaction = transactions.get(i);
			if (transaction.toString().contains(accountType)) {
				System.out.println(transaction);
				count++;
			}
		}
		
		if (count == 0) {
			System.out.println("No recent transactions found for " + accountType + " account.");
		}
	}

	private void changePIN(Account acc) {
		try {
			System.out.print("Enter current PIN: ");
			int currentPin = menuInput.nextInt();
			
			if (!acc.validatePin(currentPin)) {
				System.out.println("Incorrect current PIN.");
				return;
			}
			
			System.out.print("Enter new 4-digit PIN: ");
			int newPin = menuInput.nextInt();
			
			if (String.valueOf(newPin).length() != 4) {
				System.out.println("PIN must be exactly 4 digits.");
				return;
			}
			
			System.out.print("Confirm new PIN: ");
			int confirmPin = menuInput.nextInt();
			
			if (newPin != confirmPin) {
				System.out.println("PINs do not match.");
				return;
			}
			
			acc.setPinNumber(newPin);
			System.out.println("PIN changed successfully!");
			
		} catch (InputMismatchException e) {
			System.out.println("Invalid input. PIN change cancelled.");
			menuInput.next();
		}
	}

	private void adminMenu() {
		System.out.println("\n" + "=".repeat(50));
		System.out.println("              ADMIN MENU");
		System.out.println("=".repeat(50));
		System.out.println("Total Accounts: " + data.size());
		System.out.println("=".repeat(50));
		
		for (Map.Entry<Integer, Account> entry : data.entrySet()) {
			Account acc = entry.getValue();
			System.out.println("Customer: " + acc.getCustomerNumber() + 
				" | Name: " + (acc.getAccountHolderName() != null ? acc.getAccountHolderName() : "N/A") +
				" | Status: " + (acc.isLocked() ? "LOCKED" : "ACTIVE") +
				" | Total Balance: " + moneyFormat.format(acc.getCheckingBalance() + acc.getSavingBalance()));
		}
		System.out.println("=".repeat(50));
	}
}
