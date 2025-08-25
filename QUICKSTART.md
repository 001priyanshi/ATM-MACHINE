# Quick Start Guide - ATM Simulation System

## Getting Started in 3 Steps

### 1. Compile the System
```bash
javac *.java
```

### 2. Run the Application
```bash
java ATM
```

### 3. Test with Demo Accounts

**Demo Account 1:**
- Customer Number: `952141`
- PIN: `1919`
- Account Holder: John Doe

**Demo Account 2:**
- Customer Number: `123456`
- PIN: `1234`
- Account Holder: Jane Smith

## Basic Operations

### Login Process
1. Choose option `1` from main menu
2. Enter customer number (e.g., `952141`)
3. Enter PIN (e.g., `1919`)
4. Access granted!

### Make a Deposit
1. Login to your account
2. Select `1` for Checking Account (or `2` for Savings)
3. Choose `3` for Deposit Funds
4. Enter amount (e.g., `100.00`)
5. Confirm transaction

### Check Balance
1. Login to your account
2. Select account type (Checking/Savings)
3. Choose `1` for View Balance
4. Balance displayed instantly

### View Transaction History
1. Login to your account
2. Select `4` for Transaction History
3. View complete transaction log

### Create New Account
1. Choose option `2` from main menu
2. Enter your full name
3. Choose a 6-digit customer number
4. Set a 4-digit PIN
5. Optionally make initial deposit
6. Account created and ready to use!

## Navigation Tips

- Use numbers (1-6) to navigate menus
- Always exit using option `5` to save data
- Session timeout: 10 minutes of inactivity
- Maximum transaction: $10,000
- PIN attempts: 3 maximum before lockout

## File Structure After First Run
```
workspace/
├── ATM.java
├── Account.java
├── OptionMenu.java
├── DataManager.java
├── ATM.class (compiled files)
├── Account.class
├── OptionMenu.class
├── DataManager.class
├── accounts.dat (your saved data)
├── Readme.md
└── QUICKSTART.md
```

## Troubleshooting

**Can't compile?**
- Make sure you have Java JDK 8+ installed
- All .java files should be in the same directory

**Lost your data?**
- Always exit using menu option 5 to save
- Look for `accounts.dat` file in the directory

**Account locked?**
- Wait and try again later
- Use demo accounts for testing

## Need Help?
Check the full `Readme.md` for comprehensive documentation and advanced features.

---
**Happy Banking!** 🏦