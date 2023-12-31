package Bank_Management_Systems;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.*;

public class Withdraw extends JFrame implements ActionListener {

    JTextField amount;
    JButton Withdraw,back;
    String pin;
    Withdraw(String pin){
        this.pin = pin;
        setLayout(null);

        ImageIcon i1 =new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900,900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,900,900);
        add(image);

        JLabel text = new JLabel("Enter The Amount You Want to Withdraw");
        text.setForeground(Color.white);
        text.setFont(new Font("System",Font.BOLD,16));
        text.setBounds(170,300,400,20);
        image.add(text);

        amount = new JTextField();
        amount.setFont(new Font("Rale way",Font.BOLD,22));
        amount.setBounds(170,350,320,25);
        image.add(amount);

        Withdraw = new JButton("Withdraw");
        Withdraw.setBounds(355,485,150,30);
        Withdraw.addActionListener(this);
        image.add(Withdraw);

        back = new JButton("Back");
        back.setBounds(355,520,150,30);
        back.addActionListener(this);
        image.add(back);

        setSize(900,900);
        setLocation(300,0);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == Withdraw){
            String amount_Number = amount.getText();
            Date date = new Date();
            if (amount_Number.equals("")){
                JOptionPane.showMessageDialog(null,"Please enter the amount you want to withdraw!!!");
            }else {
                try {
                    conn conn = new conn();
                    ResultSet rs = conn.s.executeQuery("Select * from bank where pin ='"+pin+"'");
                    int balance = 0;
                    while (rs.next()){
                        if (rs.getString("type").equals("Deposit")){
                            balance += Integer.parseInt(rs.getString("amount"));
                        }else {
                            balance -= Integer.parseInt(rs.getString("amount"));
                        }
                    }if(ae.getSource() != back && balance < Integer.parseInt(amount_Number)){
                        JOptionPane.showMessageDialog(null,"Insufficient Balance");
                    }else {
                        String query = "insert into bank Values('" + pin + "','" + date + "','Withdraw','" + amount_Number + "')";
                        conn.s.executeUpdate(query);
                        JOptionPane.showMessageDialog(null,amount_Number+" Taka Withdraw Successfully");
                    }
                    setVisible(false);
                    new Transaction(pin).setVisible(true);
                }catch (Exception e){
                    System.out.println();
                }
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Transaction(pin).setVisible(true);
        }
    }

    public static void main(String[]args){
        new Withdraw("");
    }
}

