# Secure ATM Simulation System

## Overview
This is a comprehensive Java-based simulation of an ATM (Automated Teller Machine) system with advanced security features, transaction history tracking, and data persistence. The system provides a realistic banking experience with robust error handling and user-friendly interface.

## Features

### 🔐 Security Features
- **PIN-based Authentication**: Secure 4-digit PIN system
- **Account Lockout Protection**: Automatic lockout after 3 failed login attempts
- **Session Timeout**: 10-minute inactivity timeout for security
- **Input Validation**: Comprehensive validation for all user inputs
- **Transaction Limits**: Maximum transaction amount limits

### 💰 Banking Operations
- **Dual Account Types**: Checking and Savings accounts
- **Fund Transfers**: Transfer money between checking and savings
- **Balance Inquiries**: Real-time balance checking
- **Deposit/Withdrawal**: Secure money transactions
- **Transaction History**: Complete transaction logging with timestamps

### 👤 Account Management
- **Account Creation**: User-friendly account registration process
- **Account Holder Names**: Personal account identification
- **PIN Change**: Secure PIN modification functionality
- **Account Summary**: Comprehensive account overview

### 💾 Data Persistence
- **File-based Storage**: Account data saved between sessions
- **Automatic Backup**: Data automatically saved on exit and after account creation
- **Data Recovery**: Automatic data loading on startup

### 🖥️ User Interface
- **Professional Menus**: Clean, formatted console interface
- **Error Handling**: Comprehensive error messages and recovery
- **Demo Accounts**: Pre-loaded accounts for testing
- **Admin Panel**: Administrative overview of all accounts

## Project Structure

### Core Classes
- **`ATM.java`**: Main application entry point with welcome screen
- **`Account.java`**: Account data model with transaction handling
- **`OptionMenu.java`**: User interface and menu system
- **`DataManager.java`**: Data persistence and file I/O operations

### Key Components
- **Transaction History**: Inner class for tracking all transactions
- **Security System**: Account lockout and session management
- **Data Validation**: Input validation and error handling
- **File I/O**: Serialization for data persistence

## Installation & Setup

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Terminal/Command Prompt access

### Compilation
```bash
javac *.java
```

### Running the System
```bash
java ATM
```

## Demo Accounts

For testing purposes, the system includes pre-configured demo accounts:

| Customer Number | PIN  | Account Holder | Checking Balance | Savings Balance |
|----------------|------|----------------|------------------|-----------------|
| 952141         | 1919 | John Doe       | $1,000.00        | $5,000.00       |
| 123456         | 1234 | Jane Smith     | $2,500.00        | $7,500.00       |

## System Navigation

### Main Menu Options
1. **Login to Account** - Access existing account
2. **Create New Account** - Register new account
3. **View Demo Accounts** - Display test account credentials
4. **Admin Menu** - Administrative overview
5. **Exit** - Save data and exit system

### Account Services Menu
1. **Checkings Account** - Access checking account operations
2. **Savings Account** - Access savings account operations
3. **Account Summary** - View complete account information
4. **Transaction History** - View all transaction records
5. **Change PIN** - Modify account PIN
6. **Logout** - Return to main menu

### Account Operation Menus
1. **View Balance** - Check current balance
2. **Withdraw Funds** - Withdraw money from account
3. **Deposit Funds** - Deposit money to account
4. **Transfer Funds** - Transfer between checking and savings
5. **Recent Transactions** - View recent account-specific transactions
6. **Back to Main Menu** - Return to account services

## Security Features

### Account Lockout System
- Maximum 3 failed login attempts per session
- Account automatically locked after exceeding attempts
- Locked accounts require administrative unlock

### Session Management
- 10-minute inactivity timeout
- Automatic logout for security
- Session validation on each operation

### Data Protection
- PIN validation for all sensitive operations
- Transaction amount limits ($10,000 maximum)
- Secure data serialization

## Transaction System

### Supported Transaction Types
- **DEPOSIT**: Money added to account
- **WITHDRAW**: Money removed from account
- **TRANSFER OUT**: Money transferred from account
- **TRANSFER IN**: Money transferred to account

### Transaction Records
Each transaction includes:
- Transaction type and amount
- Account affected (Checking/Savings)
- Timestamp of transaction
- Resulting account balance

## Data Persistence

### File Storage
- Account data saved in `accounts.dat` file
- Uses Java serialization for data integrity
- Automatic save on exit and account creation

### Data Recovery
- Automatic loading of existing accounts on startup
- Graceful handling of missing or corrupted data files
- Fallback to demo accounts if no data exists

## Error Handling

### Input Validation
- Numeric input validation for all monetary values
- PIN format validation (4-digit requirement)
- Account number format validation (6-digit requirement)

### Exception Handling
- InputMismatchException for invalid input types
- FileNotFoundException for missing data files
- Comprehensive error messages and recovery options

## Administrative Features

### Admin Panel
- Total account count display
- Account status overview (ACTIVE/LOCKED)
- Account holder information
- Total balance summaries

## Usage Examples

### Creating a New Account
1. Select option 2 from main menu
2. Enter full name
3. Choose 6-digit customer number
4. Set 4-digit PIN (with confirmation)
5. Optional initial deposit
6. Account created and data saved

### Making a Transaction
1. Login with customer number and PIN
2. Select account type (Checking/Savings)
3. Choose transaction type (Deposit/Withdraw/Transfer)
4. Enter transaction amount
5. Confirm transaction
6. View updated balance

### Viewing Transaction History
1. Login to account
2. Select "Transaction History" from account services
3. View complete transaction log with timestamps
4. Or select "Recent Transactions" from account menus

## Technical Implementation

### Object-Oriented Design
- Encapsulated account data with private fields
- Separation of concerns between UI and business logic
- Modular design for easy maintenance and extension

### Data Structures
- HashMap for efficient account storage and retrieval
- ArrayList for transaction history management
- Serializable objects for data persistence

### Design Patterns
- Singleton pattern for data management
- Factory pattern for account creation
- Observer pattern for transaction logging

## Future Enhancements

### Potential Improvements
- Database integration (MySQL/PostgreSQL)
- Network connectivity for multi-terminal access
- Enhanced encryption for data security
- Receipt generation and printing
- Account statements and reporting
- Interest calculation for savings accounts
- Loan and credit features
- Mobile/web interface

### Scalability Considerations
- Multi-user concurrent access
- Load balancing for multiple ATM terminals
- Distributed database architecture
- Real-time transaction processing
- Integration with banking APIs

## Troubleshooting

### Common Issues

**Compilation Errors**
- Ensure JDK 8+ is installed
- Check classpath configuration
- Verify all Java files are in same directory

**Runtime Errors**
- Check file permissions for data storage
- Ensure sufficient disk space
- Verify Java runtime environment

**Data Loss**
- Check for `accounts.dat` file in working directory
- Ensure proper shutdown using menu option 5
- Backup `accounts.dat` file regularly

## Contributing

### Development Guidelines
- Follow Java coding conventions
- Add comprehensive comments
- Include error handling for new features
- Test thoroughly before committing
- Update documentation for new features

### Code Structure
- Maintain separation between UI and business logic
- Use descriptive variable and method names
- Include input validation for all user inputs
- Follow existing error handling patterns

## License

This project is developed for educational purposes and demonstrates core banking system concepts, security implementations, and Java programming best practices.

---

**Note**: This is a simulation system for educational purposes only. It should not be used for actual financial transactions or sensitive data handling in production environments.
