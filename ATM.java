import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class ATM extends JFrame
{
    private double balance = 1000.00;   
    private final JTextArea display_text;      
    private final ArrayList<String> transaction_history;   

    public ATM() 
    {
        transaction_history = new ArrayList<>();
        setTitle("Pacific Standard bank");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); 

        display_text = new JTextArea();
        display_text.setEditable(false);
        display_text.setFont(new Font("Courier New", Font.PLAIN, 16));
        display_text.setBackground(Color.WHITE);  
        display_text.setForeground(new Color(50, 50, 50));  
        display_text.setText("Welcome To Maze Bank\n Please Enter Your PIN :\n");
        add(display_text, BorderLayout.CENTER);

        JPanel buttonPanel1 = new JPanel();
        JPanel buttonPanel2 = new JPanel();
        buttonPanel1.setLayout(new GridLayout(3 , 1));
        buttonPanel2.setLayout(new GridLayout(3 , 1));

        JButton withdrawButton = new JButton("Withdraw");
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton depositButton = new JButton("       Deposit        ");
        JButton amttransferButton = new JButton("Transfer");
        JButton transationhistorybutton = new JButton("Transaction History");
        JButton exitButton = new JButton("Exit");

        Color buttonColor = new Color(70, 130, 180); 
        Color hoveColor = new Color(100, 150, 200); 
        Color buttonTextColor = Color.WHITE; 


        withdrawButton.setBackground(buttonColor);
        checkBalanceButton.setBackground(buttonColor);
        depositButton.setBackground(buttonColor);
        amttransferButton.setBackground(buttonColor);
        transationhistorybutton.setBackground(buttonColor);
        exitButton.setBackground(buttonColor);

        withdrawButton.setForeground(buttonTextColor);
        checkBalanceButton.setForeground(buttonTextColor);
        depositButton.setForeground(buttonTextColor);
        amttransferButton.setForeground(buttonTextColor);
        transationhistorybutton.setForeground(buttonTextColor);
        exitButton.setForeground(buttonTextColor);

        withdrawButton.setRolloverEnabled(true);
        checkBalanceButton.setRolloverEnabled(true);
        depositButton.setRolloverEnabled(true);
        amttransferButton.setRolloverEnabled(true);
        transationhistorybutton.setRolloverEnabled(true);
        exitButton.setRolloverEnabled(true);

        withdrawButton.addActionListener(new WithdrawAction());
        depositButton.addActionListener(new DepositAction());
        checkBalanceButton.addActionListener(new CheckBalanceAction());
        amttransferButton.addActionListener(new TransferAction()); 
        transationhistorybutton.addActionListener(new HistoryAction()); 
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel1.add(withdrawButton);
        buttonPanel1.add(depositButton);
        buttonPanel1.add(amttransferButton);
        buttonPanel2.add(checkBalanceButton);
        buttonPanel2.add(transationhistorybutton);
        buttonPanel2.add(exitButton);

        add(buttonPanel1,BorderLayout.WEST);
        add(buttonPanel2,BorderLayout.EAST);
        String PIN = JOptionPane.showInputDialog("Enter Your PIN: ");
        if(PIN != null && PIN.equals("2589")) 
        {
            display_text.setText("PIN Accepted.\nChoose An Operation.");
        }
        else 
        {
            JOptionPane.showMessageDialog(ATM.this,"Invalid PIN.\nPlease Try Again");
            System.exit(0);
        }
    }

    private class WithdrawAction implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            String amt = JOptionPane.showInputDialog(ATM.this,"Enter Amount to Withdraw: ");
            if(amt != null) 
            {
                try 
                {
                    double amount = Double.parseDouble(amt);
                    if(amount > 0 && amount <= balance) 
                    {
                        balance = balance - amount;
                        transaction_history.add("\nWithdrew: ₹" + amount);
                        display_text.setText(" Withdrawal of ₹"+ amount +" Succesful.\nCurrent Balance: ₹" + balance);
                    }
                    else if(balance <= 0 || amount > balance) 
                    {
                        display_text.setText("Insufficient Funds.");
                    }
                }   
                catch (NumberFormatException ex) 
                {
                    display_text.setText("Invalid Amount.\nPlease Try Again");
                }
            }
        }
    }

    private class DepositAction implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            String amt = JOptionPane.showInputDialog(ATM.this,"Enter Amount to Deposit: ");
            double amount = Double.parseDouble(amt);
            if(amount > 0) 
            {
                try 
                {
                    balance = balance + amount;
                    transaction_history.add("\nDeposited: ₹" + amount);
                    display_text.setText("₹" + amount + " Deposited Succesfully.\nCurrent Balance: ₹" + balance);
                }

                catch (NumberFormatException ex) 
                {
                    display_text.setText("Invalid Amount.\nPlease Try Again");
                }
            }
            else
            {
                display_text.setText("Please enter valid amount");
            }
        }
    }

    private class CheckBalanceAction implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            display_text.setText("Current Balance: ₹" + balance);
        }
    }
    
    private class TransferAction implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            String recp = JOptionPane.showInputDialog(ATM.this,"Enter Recipient's Account No. : ");
            if(recp != null)
            {
                try
                {
                    long recpAcc = Long.parseLong(recp);
                    if(recpAcc > 99999999999L && recpAcc < 1000000000000L) 
                    {
                        String amt = JOptionPane.showInputDialog(ATM.this,"Enter Amount: ");
                        double amount = Double.parseDouble(amt);
                        if(amount > 0 && amount <= balance) 
                        {
                            balance = balance - amount;
                            transaction_history.add("\nTransferred Amount: ₹" + amount + "\nTransfer Account: " + recp + "\n");
                            display_text.setText("₹" + amount + " transferred Succesfully.\nCurrent Balance: ₹" + balance);
                        }
                        else if(balance <= 0 || amount > balance) 
                        {
                            display_text.setText("Insufficient Funds.");
                        }
                    }
                    else 
                    {
                        display_text.setText("Invalid Account No.\nPlease Try Again");
                    } 
                }
                catch (NumberFormatException ex) 
                {
                    display_text.setText("Invalid Amount.\nPlease Try Again");
                }
            }
        }
    }

    private class HistoryAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(transaction_history.isEmpty()) 
            {
                display_text.setText("No Transaction History \n ");
            }
            else
            {
                StringBuilder history = new StringBuilder("Transaction History : \n");
                for(String transaction : transaction_history)
                {
                    history.append(transaction).append("\n");
                }
                display_text.setText(history.toString());
            }
        }
    }

    public static void main(String[] args) 
    {
            ATM atm = new ATM();
            atm.setVisible(true);
    }
}