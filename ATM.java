import java.util.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

// Custom Exceptions
class IncorrectAmount extends RuntimeException {
    public IncorrectAmount(String s) {
        super(s);
    }
}

class InsufficientBalance extends RuntimeException {
    public InsufficientBalance(String s) {
        super(s);
    }
}

// ATM Interface
interface ATMOperations {
    void withdraw(int amount, Scanner sc);
    void deposit(int amount);
    void checkBalance();
    void exit();
}

// ATM Implementation
class ATMImpl implements ATMOperations {
    private int balance = 20000; // demo balance

    // Twilio credentials
    public static final String ACCOUNT_SID = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
    public static final String AUTH_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    public static final String TWILIO_NUMBER = "+xxxxxxxxxxx"; // ✅ Your Twilio number

    private int generateOTP() {
        Random rand = new Random();
        return 100000 + rand.nextInt(900000); // 6-digit OTP
    }

    private void sendOTP(String userNumber, int otp) {
        System.out.println("DEBUG: Using TWILIO_NUMBER = " + TWILIO_NUMBER);  // <-- debug print

        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            String messageBody = "Your ATM OTP is: " + otp;

            Message message = Message.creator(
                new PhoneNumber(userNumber),       // the recipient's number
                new PhoneNumber(TWILIO_NUMBER),    // your Twilio number as the sender
                messageBody
            ).create();

            System.out.println("OTP sent to your phone. SID: " + message.getSid());
        } catch (Exception e) {
            System.out.println("Failed to send OTP: " + e.getMessage());
        }
    }

    @Override
    public void withdraw(int amount, Scanner sc) {
        if (amount > balance) {
            throw new InsufficientBalance("Withdrawal amount exceeds available balance!");
        } else if (amount <= 0) {
            System.out.println("Enter a valid amount to withdraw");
        } else if (amount % 100 != 0) {
            throw new IncorrectAmount("Please enter the amount in multiples of 100");
        } else {
            // If withdrawal > 8000 → OTP required
            if (amount > 8000) {
                int otp = generateOTP();
                sendOTP("+91xxxxxxxxxx", otp); // Replace with your verified number

                System.out.print("Enter OTP sent to your registered mobile: ");
                int enteredOtp = sc.nextInt();
                if (enteredOtp != otp) {
                    System.out.println("Invalid OTP! Transaction cancelled.");
                    return;
                }
            }

            balance -= amount;
            System.out.println("Withdrawal successful");
            System.out.println("Remaining Balance: " + balance);
        }
    }

    @Override
    public void deposit(int amount) {
        if (amount <= 0) {
            System.out.println("Enter a valid amount to deposit");
        } else {
            balance += amount;
            System.out.println("Deposit successful");
            System.out.println("Updated Balance: " + balance);
        }
    }

    @Override
    public void checkBalance() {
        System.out.println("Current Balance: " + balance);
    }

    @Override
    public void exit() {
        System.out.println("Thank you for using the ATM");
    }
}

// ATM Main Class
public class ATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int pin = 1234;
        int attempts = 3;
        boolean verified = false;

        System.out.println("INSERT YOUR ATM CARD");

        while (attempts > 0) {
            System.out.print("Enter your 4-digit PIN: ");
            int enteredPin = sc.nextInt();

            if (enteredPin == pin) {
                verified = true;
                break;
            } else {
                attempts--;
                System.out.println("INVALID PIN! Attempts left: " + attempts);
                if (attempts == 0) {
                    System.out.println("Card temporarily blocked due to security reasons.");
                    sc.close();
                    return;
                }
            }
        }

        if (verified) {
            ATMOperations atm = new ATMImpl();
            while (true) {
                System.out.println("\n===== ATM MENU =====");
                System.out.println("1. WITHDRAW");
                System.out.println("2. DEPOSIT");
                System.out.println("3. CHECK BALANCE");
                System.out.println("4. EXIT");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter amount to withdraw: ");
                        int w = sc.nextInt();
                        atm.withdraw(w, sc);
                        break;
                    case 2:
                        System.out.print("Enter amount to deposit: ");
                        int d = sc.nextInt();
                        atm.deposit(d);
                        break;
                    case 3:
                        atm.checkBalance();
                        break;
                    case 4:
                        atm.exit();
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid option! Please try again.");
                }
            }
        }
    }
}