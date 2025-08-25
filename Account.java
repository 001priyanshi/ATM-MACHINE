import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	// Account variables
	private int customerNumber;
	private int pinNumber;
	private double checkingBalance = 0;
	private double savingBalance = 0;
	private String accountHolderName;
	private LocalDateTime accountCreationDate;
	private int failedLoginAttempts = 0;
	private boolean isLocked = false;
	private List<Transaction> transactionHistory;

	transient Scanner input = new Scanner(System.in);
	transient DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");
	transient DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	// Inner class for transaction records
	public static class Transaction implements Serializable {
		private static final long serialVersionUID = 1L;
		private String type;
		private String account;
		private double amount;
		private double balanceAfter;
		private LocalDateTime timestamp;

		public Transaction(String type, String account, double amount, double balanceAfter) {
			this.type = type;
			this.account = account;
			this.amount = amount;
			this.balanceAfter = balanceAfter;
			this.timestamp = LocalDateTime.now();
		}

		@Override
		public String toString() {
			DecimalFormat format = new DecimalFormat("'$'###,##0.00");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			return String.format("%-12s | %-10s | %12s | %12s | %s", 
				type, account, format.format(amount), format.format(balanceAfter), timestamp.format(formatter));
		}
	}

	public Account() {
		this.transactionHistory = new ArrayList<>();
		this.accountCreationDate = LocalDateTime.now();
	}

	public Account(int customerNumber, int pinNumber) {
		this.customerNumber = customerNumber;
		this.pinNumber = pinNumber;
		this.transactionHistory = new ArrayList<>();
		this.accountCreationDate = LocalDateTime.now();
	}

	public Account(int customerNumber, int pinNumber, String accountHolderName) {
		this.customerNumber = customerNumber;
		this.pinNumber = pinNumber;
		this.accountHolderName = accountHolderName;
		this.transactionHistory = new ArrayList<>();
		this.accountCreationDate = LocalDateTime.now();
	}

	public Account(int customerNumber, int pinNumber, double checkingBalance, double savingBalance) {
		this.customerNumber = customerNumber;
		this.pinNumber = pinNumber;
		this.checkingBalance = checkingBalance;
		this.savingBalance = savingBalance;
		this.transactionHistory = new ArrayList<>();
		this.accountCreationDate = LocalDateTime.now();
	}

	public Account(int customerNumber, int pinNumber, String accountHolderName, double checkingBalance, double savingBalance) {
		this.customerNumber = customerNumber;
		this.pinNumber = pinNumber;
		this.accountHolderName = accountHolderName;
		this.checkingBalance = checkingBalance;
		this.savingBalance = savingBalance;
		this.transactionHistory = new ArrayList<>();
		this.accountCreationDate = LocalDateTime.now();
	}

	public int setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
		return customerNumber;
	}

	public int getCustomerNumber() {
		return customerNumber;
	}

	public int setPinNumber(int pinNumber) {
		this.pinNumber = pinNumber;
		return pinNumber;
	}

	public int getPinNumber() {
		return pinNumber;
	}

	public double getCheckingBalance() {
		return checkingBalance;
	}

	public double getSavingBalance() {
		return savingBalance;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public LocalDateTime getAccountCreationDate() {
		return accountCreationDate;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public int getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	public void incrementFailedAttempts() {
		this.failedLoginAttempts++;
		if (this.failedLoginAttempts >= 3) {
			this.isLocked = true;
		}
	}

	public void resetFailedAttempts() {
		this.failedLoginAttempts = 0;
	}

	public void unlockAccount() {
		this.isLocked = false;
		this.failedLoginAttempts = 0;
	}

	public List<Transaction> getTransactionHistory() {
		return new ArrayList<>(transactionHistory);
	}

	private void addTransaction(String type, String account, double amount, double balanceAfter) {
		transactionHistory.add(new Transaction(type, account, amount, balanceAfter));
	}

	public double calcCheckingWithdraw(double amount) {
		checkingBalance = (checkingBalance - amount);
		addTransaction("WITHDRAW", "Checking", amount, checkingBalance);
		return checkingBalance;
	}

	public double calcSavingWithdraw(double amount) {
		savingBalance = (savingBalance - amount);
		addTransaction("WITHDRAW", "Savings", amount, savingBalance);
		return savingBalance;
	}

	public double calcCheckingDeposit(double amount) {
		checkingBalance = (checkingBalance + amount);
		addTransaction("DEPOSIT", "Checking", amount, checkingBalance);
		return checkingBalance;
	}

	public double calcSavingDeposit(double amount) {
		savingBalance = (savingBalance + amount);
		addTransaction("DEPOSIT", "Savings", amount, savingBalance);
		return savingBalance;
	}

	public void calcCheckTransfer(double amount) {
		checkingBalance = checkingBalance - amount;
		savingBalance = savingBalance + amount;
		addTransaction("TRANSFER OUT", "Checking", amount, checkingBalance);
		addTransaction("TRANSFER IN", "Savings", amount, savingBalance);
	}

	public void calcSavingTransfer(double amount) {
		savingBalance = savingBalance - amount;
		checkingBalance = checkingBalance + amount;
		addTransaction("TRANSFER OUT", "Savings", amount, savingBalance);
		addTransaction("TRANSFER IN", "Checking", amount, checkingBalance);
	}

	public void getCheckingWithdrawInput() {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nCurrent Checkings Account Balance: " + moneyFormat.format(checkingBalance));
				System.out.print("\nAmount you want to withdraw from Checkings Account: ");
				double amount = input.nextDouble();
				if ((checkingBalance - amount) >= 0 && amount >= 0) {
					calcCheckingWithdraw(amount);
					System.out.println("\nCurrent Checkings Account Balance: " + moneyFormat.format(checkingBalance));
					end = true;
				} else {
					System.out.println("\nBalance Cannot be Negative.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				input.next();
			}
		}
	}

	public void getsavingWithdrawInput() {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingBalance));
				System.out.print("\nAmount you want to withdraw from Savings Account: ");
				double amount = input.nextDouble();
				if ((savingBalance - amount) >= 0 && amount >= 0) {
					calcSavingWithdraw(amount);
					System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingBalance));
					end = true;
				} else {
					System.out.println("\nBalance Cannot Be Negative.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				input.next();
			}
		}
	}

	public void getCheckingDepositInput() {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nCurrent Checkings Account Balance: " + moneyFormat.format(checkingBalance));
				System.out.print("\nAmount you want to deposit from Checkings Account: ");
				double amount = input.nextDouble();
				if ((checkingBalance + amount) >= 0 && amount >= 0) {
					calcCheckingDeposit(amount);
					System.out.println("\nCurrent Checkings Account Balance: " + moneyFormat.format(checkingBalance));
					end = true;
				} else {
					System.out.println("\nBalance Cannot Be Negative.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				input.next();
			}
		}
	}

	public void getSavingDepositInput() {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingBalance));
				System.out.print("\nAmount you want to deposit into your Savings Account: ");
				double amount = input.nextDouble();

				if ((savingBalance + amount) >= 0 && amount >= 0) {
					calcSavingDeposit(amount);
					System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingBalance));
					end = true;
				} else {
					System.out.println("\nBalance Cannot Be Negative.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				input.next();
			}
		}
	}

	public void getTransferInput(String accType) {
		boolean end = false;
		while (!end) {
			try {
				if (accType.equals("Checkings")) {
					System.out.println("\nSelect an account you wish to tranfers funds to:");
					System.out.println("1. Savings");
					System.out.println("2. Exit");
					System.out.print("\nChoice: ");
					int choice = input.nextInt();
					switch (choice) {
					case 1:
						System.out.println("\nCurrent Checkings Account Balance: " + moneyFormat.format(checkingBalance));
						System.out.print("\nAmount you want to deposit into your Savings Account: ");
						double amount = input.nextDouble();
						if ((savingBalance + amount) >= 0 && (checkingBalance - amount) >= 0 && amount >= 0) {
							calcCheckTransfer(amount);
							System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingBalance));
							System.out.println(
									"\nCurrent Checkings Account Balance: " + moneyFormat.format(checkingBalance));
							end = true;
						} else {
							System.out.println("\nBalance Cannot Be Negative.");
						}
						break;
					case 2:
						return;
					default:
						System.out.println("\nInvalid Choice.");
						break;
					}
				} else if (accType.equals("Savings")) {
					System.out.println("\nSelect an account you wish to tranfers funds to: ");
					System.out.println("1. Checkings");
					System.out.println("2. Exit");
					System.out.print("\nChoice: ");
					int choice = input.nextInt();
					switch (choice) {
					case 1:
						System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingBalance));
						System.out.print("\nAmount you want to deposit into your savings account: ");
						double amount = input.nextDouble();
						if ((checkingBalance + amount) >= 0 && (savingBalance - amount) >= 0 && amount >= 0) {
							calcSavingTransfer(amount);
							System.out.println("\nCurrent checkings account balance: " + moneyFormat.format(checkingBalance));
							System.out.println("\nCurrent savings account balance: " + moneyFormat.format(savingBalance));
							end = true;
						} else {
							System.out.println("\nBalance Cannot Be Negative.");
						}
						break;
					case 2:
						return;
					default:
						System.out.println("\nInvalid Choice.");
						break;
					}
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				input.next();
			}
		}
	}

	public void displayTransactionHistory() {
		System.out.println("\n" + "=".repeat(80));
		System.out.println("TRANSACTION HISTORY FOR ACCOUNT: " + customerNumber);
		if (accountHolderName != null) {
			System.out.println("Account Holder: " + accountHolderName);
		}
		System.out.println("=".repeat(80));
		
		if (transactionHistory.isEmpty()) {
			System.out.println("No transactions found.");
		} else {
			System.out.printf("%-12s | %-10s | %12s | %12s | %s%n", 
				"TYPE", "ACCOUNT", "AMOUNT", "BALANCE", "TIMESTAMP");
			System.out.println("-".repeat(80));
			
			for (Transaction transaction : transactionHistory) {
				System.out.println(transaction);
			}
		}
		System.out.println("=".repeat(80));
	}

	public void displayAccountSummary() {
		System.out.println("\n" + "=".repeat(60));
		System.out.println("ACCOUNT SUMMARY");
		System.out.println("=".repeat(60));
		System.out.println("Customer Number: " + customerNumber);
		if (accountHolderName != null) {
			System.out.println("Account Holder: " + accountHolderName);
		}
		System.out.println("Account Created: " + accountCreationDate.format(dateFormatter));
		System.out.println("Checking Balance: " + moneyFormat.format(checkingBalance));
		System.out.println("Savings Balance: " + moneyFormat.format(savingBalance));
		System.out.println("Total Balance: " + moneyFormat.format(checkingBalance + savingBalance));
		System.out.println("Total Transactions: " + transactionHistory.size());
		System.out.println("Account Status: " + (isLocked ? "LOCKED" : "ACTIVE"));
		System.out.println("=".repeat(60));
	}

	public boolean validatePin(int inputPin) {
		return this.pinNumber == inputPin;
	}

	public boolean hasMinimumBalance(String accountType, double amount) {
		if (accountType.equalsIgnoreCase("checking")) {
			return checkingBalance >= amount;
		} else if (accountType.equalsIgnoreCase("savings")) {
			return savingBalance >= amount;
		}
		return false;
	}

	public boolean isValidAmount(double amount) {
		return amount > 0 && amount <= 10000; // Maximum transaction limit
	}

	// Method to reinitialize transient fields after deserialization
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		input = new Scanner(System.in);
		moneyFormat = new DecimalFormat("'$'###,##0.00");
		dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	}
}
