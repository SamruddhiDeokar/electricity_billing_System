package ElectricityBilingSystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Signup extends JFrame implements ActionListener {
    Choice loginAsCho;
    TextField meterText, EmployerText, userNameText, nameText, passwordText;
    JButton create, back;

    Signup() {
        super("Signup page");

        getContentPane().setBackground(new Color(168, 203, 255));

        JLabel createAs = new JLabel("Create Account As");
        createAs.setBounds(30, 50, 125, 20);
        add(createAs);

        loginAsCho = new Choice();
        loginAsCho.add("Admin");
        loginAsCho.add("Customer");
        loginAsCho.setBounds(170, 50, 120, 20);
        add(loginAsCho);

        // Labels and fields for Admin and Customer
        JLabel meterNo = new JLabel("Meter Number");
        meterNo.setBounds(30, 100, 125, 20);
        meterNo.setVisible(false);
        add(meterNo);

        meterText = new TextField();
        meterText.setBounds(170, 100, 125, 20);
        meterText.setVisible(false);
        add(meterText);

        JLabel Employer = new JLabel("Employer ID");
        Employer.setBounds(30, 100, 125, 20);
        Employer.setVisible(true);
        add(Employer);

        EmployerText = new TextField();
        EmployerText.setBounds(170, 100, 125, 20);
        EmployerText.setVisible(true);
        add(EmployerText);

        JLabel userName = new JLabel("UserName");
        userName.setBounds(30, 140, 125, 20);
        add(userName);

        userNameText = new TextField();
        userNameText.setBounds(170, 140, 125, 20);
        add(userNameText);

        JLabel name = new JLabel("Name");
        name.setBounds(30, 180, 125, 20);
        add(name);

        nameText = new TextField("");
        nameText.setBounds(170, 180, 125, 20);
        add(nameText);

        // Password field
        JLabel password = new JLabel("Password");
        password.setBounds(30, 220, 125, 20);
        add(password);

        passwordText = new TextField();
        passwordText.setBounds(170, 220, 125, 20);
        add(passwordText);

        // ItemListener to toggle between Admin and Customer
        loginAsCho.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String user = loginAsCho.getSelectedItem();
                if (user.equals("Customer")) {
                    Employer.setVisible(false);
                    EmployerText.setVisible(false);
                    meterNo.setVisible(true);
                    meterText.setVisible(true);
                } else {
                    Employer.setVisible(true);
                    EmployerText.setVisible(true);
                    meterNo.setVisible(false);
                    meterText.setVisible(false);
                }
            }
        });

        create = new JButton("Create");
        create.setBackground(new Color(66, 127, 219));
        create.setBounds(50, 280, 100, 25);
        create.addActionListener(this);
        add(create);

        back = new JButton("Back");
        back.setBackground(new Color(66, 127, 219));
        back.setBounds(180, 285, 100, 25);
        back.addActionListener(this);
        add(back);

        String profileImagePath = "D:\\java_eclipseAdvance\\ElectricityBilingSystem\\src\\icon\\sign-up.png";
        ImageIcon profileOne = new ImageIcon(profileImagePath);
        Image profileTwo = profileOne.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        ImageIcon fprofileOne = new ImageIcon(profileTwo);
        JLabel profileLabel = new JLabel(fprofileOne);
        profileLabel.setBounds(320, 30, 250, 250);
        add(profileLabel);

        setSize(600, 380);
        setLocation(500, 200);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == create) {
            String sloginAs = loginAsCho.getSelectedItem();
            String susername = userNameText.getText();
            String sname = nameText.getText();
            String spassword = passwordText.getText();
            String smeter = meterText.getText();

            // Validate the input fields
            // Check for Customer-specific validation for meter number
            if (loginAsCho.getSelectedItem().equals("Customer") && smeter.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Meter Number is required for Customer!");
                return;
            }

            // Validate common fields for both Admin and Customer
            if (susername.isEmpty() || sname.isEmpty() || spassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required!");
                return;
            }

            try {
                // Assuming 'database' is your custom database class
                database db = new database();
                Connection conn = db.getConnection(); // Use getConnection() method to access connection

                String query = null;
                PreparedStatement stmt;

                if (loginAsCho.getSelectedItem().equals("Admin")) {
                    query = "INSERT INTO Signup (meter_no, username, name, password, usertype) VALUES (?, ?, ?, ?, ?)";
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, smeter);  // meter_no for Admin can be null or empty, but it's required for Customer
                    stmt.setString(2, susername);
                    stmt.setString(3, sname);
                    stmt.setString(4, spassword);
                    stmt.setString(5, sloginAs);
                    stmt.executeUpdate();
                } else { // For Customer
                    query = "INSERT INTO Signup (meter_no, username, name, password, usertype) VALUES (?, ?, ?, ?, ?)";
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, smeter);  // meter_no is required for Customer
                    stmt.setString(2, susername);
                    stmt.setString(3, sname);
                    stmt.setString(4, spassword);
                    stmt.setString(5, sloginAs);
                    stmt.executeUpdate();
                }

                JOptionPane.showMessageDialog(null, "Account Created Successfully");
                setVisible(false);
                new Login();  // Redirect to login page

                db.closeConnection();  // Close connection
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
                JOptionPane.showMessageDialog(null, "SQL Error: " + sqlEx.getMessage());
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
            }
        } else if (e.getSource() == back) {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new Signup();
    }
}
