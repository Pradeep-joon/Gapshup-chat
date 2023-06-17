package gapshup.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.text.*;
import java.util.Calendar;
import java.net.*;
import java.io.*;

public class Client implements ActionListener {
   


    JTextField text;
    static JPanel messArea;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame frame = new JFrame();
    static JScrollPane scrollPane;

    Client() {
       


        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        frame.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360, 20, 30, 30);
        p1.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(420, 20, 10, 25);
        p1.add(morevert);

        JLabel profileName = new JLabel("KC Bhadwa");
        profileName.setBounds(110, 15, 100, 18);
        profileName.setForeground(Color.WHITE);
        profileName.setFont(new Font("SAN_SERIF ", Font.BOLD, 18));
        p1.add(profileName);

        JLabel profileStatus = new JLabel("Active");
        profileStatus.setBounds(110, 35, 100, 18);
        profileStatus.setForeground(Color.WHITE);
        profileStatus.setFont(new Font("SAN_SERIF ", Font.ITALIC, 14));
        p1.add(profileStatus);

        messArea = new JPanel();
        messArea.setBounds(5, 75, 440, 570);
        frame.add(messArea);

//        scrollPane = new JScrollPane(messArea);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setPreferredSize(new Dimension(10, 50));
//        frame.add(scrollPane, BorderLayout.CENTER); // Assuming BorderLayout is used
        scrollPane = new JScrollPane(messArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(440, 570));
        scrollPane.setBounds(5, 75, 440, 570);
        frame.add(scrollPane);

        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        frame.add(text);

        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        send.addActionListener(this);
        frame.add(send);

        frame.setSize(450, 700);
        frame.setLocation(800, 50);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(null);
        frame.setUndecorated(true);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String sendoutput = text.getText();

            JPanel p2 = formatLabel(sendoutput);

            messArea.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());

            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            messArea.add(vertical, BorderLayout.PAGE_START);
            dout.writeUTF(sendoutput);
            text.setText(" ");

            frame.repaint();
            frame.invalidate();
            frame.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static JPanel formatLabel(String sendoutput) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style = \"width:130px\">" + sendoutput + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 18));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);

        return panel;

    }

    public static void main(String args[]) {
        new Client();

        try {

            Socket skts = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(skts.getInputStream());
            dout = new DataOutputStream(skts.getOutputStream());
            while (true) {
                messArea.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);
                JPanel leftrecieve = new JPanel(new BorderLayout());
                leftrecieve.add(panel, BorderLayout.LINE_START);
                vertical.add(leftrecieve);

                vertical.add(Box.createVerticalStrut(15));
                messArea.add(vertical, BorderLayout.PAGE_START);
                frame.validate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
