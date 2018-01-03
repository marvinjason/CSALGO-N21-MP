
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class Main implements ActionListener
{
    private DatabaseConnection connection;
    private JFrame frame;
    private CardLayout layout;
    private JPanel container;
    private JPanel home;
    private JPanel ship;
    private JPanel track;
    private JButton hButtonShip;
    private JButton hButtonTrack;
    private JButton sButtonShip;
    private JButton sButtonBack;
    private JTextField sFieldSender;
    private JTextField sFieldReceiver;
    private JTextField sFieldKilos;
    private JTextField sFieldAmount;
    private JComboBox sComboSender;
    private JComboBox sComboReceiver;
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane pane;
    private JPanel tPanelSearch;
    private JLabel tLabelSearch;
    private JTextField tFieldSearch;
    private JButton tButtonSearchGo;
    private JButton tButtonSearchBack;
    private JToggleButton toggle;
    private JPanel tPanelInfo;
    private JPanel tPanelInfoTemplate;
    private JLabel tLabelID;
    private JLabel tLabelSenderName;
    private JLabel tLabelSenderAddress;
    private JLabel tLabelReceiverName;
    private JLabel tLabelReceiverAddress;
    private JLabel tLabelKilos;
    private JLabel tLabelAmount;
    private JLabel tLabelBatch;
    private JLabel tLabelShipDate;
    private JLabel tLabelShipTime;
    private JLabel tLabel1;
    private JLabel tLabel2;
    private JLabel tLabel3;
    private JLabel tLabel4;
    private JLabel tLabel5;
    private JLabel tLabel6;
    private JLabel tLabel7;
    private JLabel tLabel8;
    private JLabel tLabel9;
    private JLabel tLabel10;
    private JButton tButtonBack;
    private JPanel tPanelStatus;
    private JLabel tLabelStatus;
    
    private int batch = 1;
    private ArrayList<Record> recordList;
    private ArrayList<Record> searchList;
    
    private int[][] matrix = {{-1, 5, 12, 7, 3, 4},
                              {5, -1, 11, 9, 6, 3},
                              {12, 11, -1, 5, 8, 6},
                              {7, 9, 5, -1, 5, 15},
                              {3, 6, 8, 5, -1, 13},
                              {4, 3, 6, 15, 13, -1}};
    
    public Main()
    {
        connection = new DatabaseConnection();
        
        hButtonShip = new JButton("Ship");
        hButtonShip.setBackground(new Color(130, 130, 130));
        hButtonShip.setBounds(360, 110, 100, 40);
        hButtonShip.setFocusPainted(false);
        hButtonShip.setForeground(Color.WHITE);
        hButtonShip.setFont(new Font("Segoe UI", Font.BOLD, 18));
        hButtonShip.addActionListener(this);
        
        hButtonTrack = new JButton("Track");
        hButtonTrack.setBackground(new Color(130, 130, 130));
        hButtonTrack.setBounds(360, 175, 100, 40);
        hButtonTrack.setFocusPainted(false);
        hButtonTrack.setForeground(Color.WHITE);
        hButtonTrack.setFont(new Font("Segoe UI", Font.BOLD, 18));
        hButtonTrack.addActionListener(this);
        
        sButtonShip = new JButton("Ship");
        sButtonShip.setBackground(new Color(130, 130, 130));
        sButtonShip.setBounds(350, 300, 100, 40);
        sButtonShip.setFocusPainted(false);
        sButtonShip.setForeground(Color.WHITE);
        sButtonShip.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sButtonShip.addActionListener(this);
        
        sButtonBack = new JButton("Back");
        sButtonBack.setBackground(new Color(130, 130, 130));
        sButtonBack.setBounds(350, 370, 100, 40);
        sButtonBack.setFocusPainted(false);
        sButtonBack.setForeground(Color.WHITE);
        sButtonBack.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sButtonBack.addActionListener(this);
        
        sFieldSender = new JTextField();
        sFieldSender.setBounds(50, 160, 175, 25);
        sFieldReceiver = new JTextField();
        sFieldReceiver.setBounds(50, 230, 175, 25);
        sFieldKilos = new JTextField();
        sFieldKilos.setBounds(50, 300, 175, 25);
        sFieldAmount = new JTextField();
        sFieldAmount.setBounds(50, 370, 175, 25);
        
        String[] address = {"Quezon City", "Manila", "Caloocan", "Pasay", "Marikina", "Mandaluyong"};
        
        sComboSender = new JComboBox(address);
        sComboSender.setSelectedIndex(0);
        sComboSender.setBounds(255, 160, 150, 25);
        sComboReceiver = new JComboBox(address);
        sComboReceiver.setSelectedIndex(1);
        sComboReceiver.setBounds(255, 230, 150, 25);
        
        String[] header = {"Transac No.", "Recipient", "Batch"};
        
        model = new DefaultTableModel(header, 0);
        table = new JTable(model);
        table.setFocusable(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (!e.getValueIsAdjusting())
                {
                    int x = table.getSelectedRow();
                    
                    if (x != -1)
                    {
                        for (int i = 0; i < recordList.size(); i++)
                        {
                            if (recordList.get(i).getID() == Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()))
                            {
                                tLabel1.setText(Integer.toString(recordList.get(i).getID()));
                                tLabel2.setText(recordList.get(i).getSenderName());
                                tLabel3.setText(recordList.get(i).getSenderAddress());
                                tLabel4.setText(recordList.get(i).getRecipientName());
                                tLabel5.setText(recordList.get(i).getRecipientAddress());
                                tLabel6.setText(Integer.toString(recordList.get(i).getKilo()));
                                tLabel7.setText(Integer.toString(recordList.get(i).getAmount()));
                                tLabel8.setText(Integer.toString(recordList.get(i).getBatch()));
                                tLabel9.setText(recordList.get(i).getShipDate());
                                tLabel10.setText(recordList.get(i).getShipTime());
                                tLabelStatus.setText("Status:   " + Integer.toString(TSP.optimize(matrix)) + " km to destination");
                            }
                        }
                    }
                    else
                    {
                        tLabel1.setText(null);
                        tLabel2.setText(null);
                        tLabel3.setText(null);
                        tLabel4.setText(null);
                        tLabel5.setText(null);
                        tLabel6.setText(null);
                        tLabel7.setText(null);
                        tLabel8.setText(null);
                        tLabel9.setText(null);
                        tLabel10.setText(null);
                        tLabelStatus.setText("Status:");
                    }
                }
            }
        });
        table.getColumn("Transac No.").setMaxWidth(100);
        table.getColumn("Batch").setMaxWidth(100);
        pane = new JScrollPane(table);
        pane.setBounds(118, 90, 350, 100);
        
        tLabelSearch = new JLabel("Search:");
        
        tFieldSearch = new JTextField();
        tFieldSearch.setPreferredSize(new Dimension(150, 20));
        
        tButtonSearchGo = new JButton("Go");
        tButtonSearchGo.setPreferredSize(new Dimension(45, 20));
        tButtonSearchGo.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        tButtonSearchGo.setFocusPainted(false);
        tButtonSearchGo.addActionListener(this);
        
        tButtonSearchBack = new JButton("X");
        tButtonSearchBack.setPreferredSize(new Dimension(40, 20));
        tButtonSearchBack.setFont(new Font("Segoe UI", Font.BOLD, 9));
        tButtonSearchBack.setFocusPainted(false);
        tButtonSearchBack.addActionListener(this);
        
        tPanelSearch = new JPanel();
        tPanelSearch.setBackground(Color.WHITE);
        tPanelSearch.setBounds(118, 200, 350, 32);
        tPanelSearch.setBorder(BorderFactory.createEtchedBorder());
        tPanelSearch.add(tLabelSearch);
        tPanelSearch.add(tFieldSearch);
        tPanelSearch.add(tButtonSearchGo);
        tPanelSearch.add(tButtonSearchBack);
        
        toggle = new JToggleButton("Sort by Recipient");
        toggle.setBounds(368, 70, 100, 15);
        toggle.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        toggle.setFocusPainted(false);
        toggle.addActionListener(this);
        
        tLabelID = new JLabel("ID:");
        tLabelID.setBounds(0, 0, 100, 20);
        tLabelSenderName = new JLabel("Sender:");
        tLabelSenderName.setBounds(0, 20, 100, 20);
        tLabelSenderAddress = new JLabel("Address:");
        tLabelSenderAddress.setBounds(0, 40, 100, 20);
        tLabelReceiverName = new JLabel("Recipient:");
        tLabelReceiverName.setBounds(0, 60, 100, 20);
        tLabelReceiverAddress = new JLabel("Address:");
        tLabelReceiverAddress.setBounds(0, 80, 100, 20);
        tLabelKilos = new JLabel("Kilos:");
        tLabelKilos.setBounds(0, 100, 100, 20);
        tLabelAmount = new JLabel("Amount:");
        tLabelAmount.setBounds(0, 120, 100, 20);
        tLabelBatch = new JLabel("Batch:");
        tLabelBatch.setBounds(150, 0, 100, 20);
        tLabelShipDate = new JLabel("Ship Date:");
        tLabelShipDate.setBounds(150, 100, 100, 20);
        tLabelShipTime = new JLabel("Ship Time:");
        tLabelShipTime.setBounds(150, 120, 100, 20);
        
        tLabel1 = new JLabel();
        tLabel1.setBounds(80, 0, 50, 20);
        tLabel2 = new JLabel();
        tLabel2.setBounds(80, 20, 250, 20);
        tLabel3 = new JLabel();
        tLabel3.setBounds(80, 40, 250, 20);
        tLabel4 = new JLabel();
        tLabel4.setBounds(80, 60, 250, 20);
        tLabel5 = new JLabel();
        tLabel5.setBounds(80, 80, 250, 20);
        tLabel6 = new JLabel();
        tLabel6.setBounds(80, 100, 50, 20);
        tLabel7 = new JLabel();
        tLabel7.setBounds(80, 120, 50, 20);
        tLabel8 = new JLabel();
        tLabel8.setBounds(230, 0, 50, 20);
        tLabel9 = new JLabel();
        tLabel9.setBounds(230, 100, 80, 20);
        tLabel10 = new JLabel();
        tLabel10.setBounds(230, 120, 80, 20);
        
        tButtonBack = new JButton("Back");
        tButtonBack.setBackground(new Color(130, 130, 130));
        tButtonBack.setBounds(365, 430, 100, 40);
        tButtonBack.setFocusPainted(false);
        tButtonBack.setForeground(Color.WHITE);
        tButtonBack.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tButtonBack.addActionListener(this);
        
        tPanelInfoTemplate = new JPanel(null);
        tPanelInfoTemplate.setBackground(Color.WHITE);
        tPanelInfoTemplate.setPreferredSize(new Dimension(330, 140));
        tPanelInfoTemplate.add(tLabelID);
        tPanelInfoTemplate.add(tLabelSenderName);
        tPanelInfoTemplate.add(tLabelSenderAddress);
        tPanelInfoTemplate.add(tLabelReceiverName);
        tPanelInfoTemplate.add(tLabelReceiverAddress);
        tPanelInfoTemplate.add(tLabelKilos);
        tPanelInfoTemplate.add(tLabelAmount);
        tPanelInfoTemplate.add(tLabelBatch);
        tPanelInfoTemplate.add(tLabelShipDate);
        tPanelInfoTemplate.add(tLabelShipTime);
        tPanelInfoTemplate.add(tLabel1);
        tPanelInfoTemplate.add(tLabel2);
        tPanelInfoTemplate.add(tLabel3);
        tPanelInfoTemplate.add(tLabel4);
        tPanelInfoTemplate.add(tLabel5);
        tPanelInfoTemplate.add(tLabel6);
        tPanelInfoTemplate.add(tLabel7);
        tPanelInfoTemplate.add(tLabel8);
        tPanelInfoTemplate.add(tLabel9);
        tPanelInfoTemplate.add(tLabel10);
        tPanelInfoTemplate.setVisible(true);
        
        tPanelInfo = new JPanel();
        tPanelInfo.setBackground(Color.WHITE);
        tPanelInfo.setBounds(118, 243, 350, 155);
        tPanelInfo.setBorder(BorderFactory.createEtchedBorder());
        tPanelInfo.add(tPanelInfoTemplate);
        
        tLabelStatus = new JLabel("Status:");
        tLabelStatus.setBounds(10, 4, 190, 20);
        
        tPanelStatus = new JPanel(null);
        tPanelStatus.setBackground(Color.WHITE);
        tPanelStatus.setBounds(118, 435, 200, 30);
        tPanelStatus.setBorder(BorderFactory.createEtchedBorder());
        tPanelStatus.add(tLabelStatus);
        
        home = new JPanel(null){
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                
                try
                {
                    g.drawImage(ImageIO.read(this.getClass().getResource("/resources/home.png")), 0, 0, null);
                }
                catch (Exception ex)
                {
                    System.out.println("Exception Thrown: " + ex.getMessage());
                }
            }
        };
        
        home.setPreferredSize(new Dimension(500, 500));
        home.add(hButtonShip);
        home.add(hButtonTrack);
        
        ship = new JPanel(null){
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                
                try
                {
                    g.drawImage(ImageIO.read(this.getClass().getResource("/resources/ship.png")), 0, 0, null);
                }
                catch (Exception ex)
                {
                    System.out.println("Exception Thrown: " + ex.getMessage());
                }
            }
        };
        
        ship.setPreferredSize(new Dimension(500, 500));
        ship.add(sFieldSender);
        ship.add(sFieldReceiver);
        ship.add(sFieldKilos);
        ship.add(sFieldAmount);
        ship.add(sButtonShip);
        ship.add(sButtonBack);
        ship.add(sComboSender);
        ship.add(sComboReceiver);
        
        track = new JPanel(null){
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                
                try
                {
                    g.drawImage(ImageIO.read(this.getClass().getResource("/resources/track.png")), 0, 0, null);
                }
                catch (Exception ex)
                {
                    System.out.println("Exception Thrown: " + ex.getMessage());
                }
            }
        };
        
        track.setPreferredSize(new Dimension(500, 500));
        track.add(pane);
        track.add(tPanelSearch);
        track.add(toggle);
        track.add(tPanelInfo);
        track.add(tButtonBack);
        track.add(tPanelStatus);
        
        layout = new CardLayout();
        
        container = new JPanel(layout);
        container.add(home, "home");
        container.add(ship, "ship");
        container.add(track, "track");
        
        frame = new JFrame("Ortix - We like to move it");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setLocation(450, 125);
        frame.add(container);
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args)
    {
        Main main = new Main();
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == hButtonShip)
        {
            layout.show(container, "ship");
        }
        else if (e.getSource() == hButtonTrack)
        {
            layout.show(container, "track");
            
            //makeBatch();
            batch = 1;
            makeBatch2();
            fetchData();
            populateTable(recordList);
        }
        else if (e.getSource() == sButtonShip)
        {
            boolean temp = true;
            
            if (sFieldSender.getText().isEmpty())
            {
                sFieldSender.setBorder(BorderFactory.createLineBorder(Color.RED));
                temp = false;
            }
            else
            {
                sFieldSender.setBorder(null);
                sFieldSender.updateUI();
            }
            
            if (sFieldReceiver.getText().isEmpty())
            {
                sFieldReceiver.setBorder(BorderFactory.createLineBorder(Color.RED));
                temp = false;
            }
            else
            {
                sFieldReceiver.setBorder(null);
                sFieldReceiver.updateUI();
            }
            
            if (sFieldKilos.getText().isEmpty() || !isNumeric(sFieldKilos.getText()))
            {
                sFieldKilos.setBorder(BorderFactory.createLineBorder(Color.RED));
                temp = false;
            }
            else
            {
                sFieldKilos.setBorder(null);
                sFieldKilos.updateUI();
            }
            
            if (isNumeric(sFieldKilos.getText()))
            {
                if (Integer.parseInt(sFieldKilos.getText()) > 1000)
                {
                    sFieldKilos.setBorder(BorderFactory.createLineBorder(Color.RED));
                    temp = false;
                }
            }
            
            if (sFieldAmount.getText().isEmpty() || !isNumeric(sFieldAmount.getText()))
            {
                sFieldAmount.setBorder(BorderFactory.createLineBorder(Color.RED));
                temp = false;
            }
            else
            {
                sFieldAmount.setBorder(null);
                sFieldAmount.updateUI();
            }
            /*
            if (isNumeric(sFieldAmount.getText()))
            {
                if (Integer.parseInt(sFieldAmount.getText()) > 1000)
                {
                    sFieldAmount.setBorder(BorderFactory.createLineBorder(Color.RED));
                    temp = false;
                }
            }
            */
            if (temp)
            {
                connection.insert(sFieldSender.getText(), sComboSender.getSelectedItem().toString(), sFieldReceiver.getText(), sComboReceiver.getSelectedItem().toString(), sFieldKilos.getText(), sFieldAmount.getText());
                
                JOptionPane.showMessageDialog(container, "Shipped successfully!\n\nTransaction Number: " + connection.currentVal() + "\n\n", "Success!", JOptionPane.INFORMATION_MESSAGE);
                
                sButtonBack.doClick();
            }
        }
        else if (e.getSource() == sButtonBack)
        {
            sFieldSender.setBorder(null);
            sFieldReceiver.setBorder(null);
            sFieldKilos.setBorder(null);
            sFieldAmount.setBorder(null);

            sFieldSender.updateUI();
            sFieldReceiver.updateUI();
            sFieldKilos.updateUI();
            sFieldAmount.updateUI();
            
            sFieldSender.setText(null);
            sFieldReceiver.setText(null);
            sFieldKilos.setText(null);
            sFieldAmount.setText(null);
            
            layout.show(container, "home");
        }
        else if (e.getSource() == tButtonSearchGo)
        {
            search();
        }
        else if (e.getSource() == tButtonSearchBack)
        {
            populateTable(recordList);
        }
        else if (e.getSource() == toggle)
        {
            sort(recordList, toggle.isSelected());
            populateTable(recordList);
        }
        else if (e.getSource() == tButtonBack)
        {
            layout.show(container, "home");
        }
    }
    
    private void makeBatch2()
    {
        ArrayList<Integer> idList = new ArrayList();
        ArrayList<Integer> weightList = new ArrayList();
        ArrayList<Integer> costList = new ArrayList();

        ResultSet rs = connection.executeQuery("SELECT * FROM SHIPMENTS");

        try
        {   
            while (rs.next())
            {   
                idList.add(rs.getInt("ID"));
                weightList.add(rs.getInt("KILO"));
                costList.add(rs.getInt("AMOUNT"));
            }
        }
        catch (Exception ex)
        {
            System.out.println("Exception Thrown: " + ex.getMessage());
        }
        
        updateBatch(1000, idList, weightList, costList);
    }
    
    private void makeBatch()
    {
        ArrayList<Integer> idList = new ArrayList();
        ArrayList<Integer> weightList = new ArrayList();
        ArrayList<Integer> costList = new ArrayList();
        ArrayList<Sack> sackList = new ArrayList();

        ResultSet rs = connection.executeQuery("SELECT * FROM SHIPMENTS ORDER BY SENDER_ADDRESS ASC");

        try
        {   
            while (rs.next())
            {   
                Sack sack = new Sack(rs.getInt("ID"), rs.getInt("KILO"), rs.getInt("AMOUNT"), rs.getString("SENDER_ADDRESS"));
                sackList.add(sack);
            }
        }
        catch (Exception ex)
        {
            System.out.println("Exception Thrown: " + ex.getMessage());
        }

        String temp = "Caloocan";

        for (int i = 0; i < sackList.size(); i++)
        {
            if (!sackList.get(i).getAddress().equalsIgnoreCase(temp))
            {
                updateBatch(1000, idList, weightList, costList);

                idList.clear();
                weightList.clear();
                costList.clear();
                temp = sackList.get(i).getAddress();
            }

            idList.add(sackList.get(i).getID());
            weightList.add(sackList.get(i).getWeight());
            costList.add(sackList.get(i).getCost());

            if (i == sackList.size() - 1)
            {
                updateBatch(1000, idList, weightList, costList);
            }
        }
    }
    
    private void updateBatch(int threshold, ArrayList<Integer> id, ArrayList<Integer> weight, ArrayList<Integer> cost)
    {
        ArrayList<Integer> tempList = new ArrayList();
        ArrayList<Integer> optimizedList = new ArrayList();
        int n = id.size();
        int w = 0;
        int c = 0;
        int optimizedW = 0;
        int optimizedC = 0;
        
        for (int i = 1; i < (1 << n); i++)
        {
            String str = Integer.toBinaryString(i);
            
            for (int j = str.length() - 1, k = n - 1; j >= 0; j--, k--)
            {
                if (str.charAt(j) == '1')
                {
                    w += weight.get(k);
                    c += cost.get(k);
                    tempList.add(id.get(k));
                }
            }
            
            if (w <= threshold)
            {
                if (optimizedW == 0 && optimizedC == 0)
                {
                    optimizedW = w;
                    optimizedC = c;
                    optimizedList.clear();
                    optimizedList.addAll(tempList);
                }
                else if (optimizedW <= w)
                {
                    if (optimizedC <= c)
                    {
                        optimizedW = w;
                        optimizedC = c;
                        optimizedList.clear();
                        optimizedList.addAll(tempList);
                    }
                }
            }
            
            w = 0;
            c = 0;
            tempList.clear();
        }
        
        for (int i = 0; i < optimizedList.size(); i++)
        {
            int x = id.indexOf(optimizedList.get(i));
            connection.update(optimizedList.get(i), batch);

            id.remove(x);
            weight.remove(x);
            cost.remove(x);
        }
        
        ++batch;
        
        if (id.isEmpty())
        {
            return;
        }
        
        updateBatch(threshold, id, weight, cost);
    }
    
    private static boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
    
    public void fetchData()
    {
        recordList = new ArrayList();
        
        ResultSet rs = connection.executeQuery("SELECT * FROM SHIPMENTS ORDER BY BATCH");

        try
        {   
            while (rs.next())
            {   
                Record record = new Record(rs.getInt("ID"), rs.getString("SENDER_NAME"), rs.getString("SENDER_ADDRESS"), rs.getString("RECIPIENT_NAME"), rs.getString("RECIPIENT_ADDRESS"), rs.getInt("KILO"), rs.getInt("AMOUNT"), rs.getString("SHIP_DATE"), rs.getString("SHIP_TIME"), rs.getInt("BATCH"));
                recordList.add(record);
            }
            
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println("Exception Thrown: " + ex.getMessage());
        }
    }
    
    public void populateTable(ArrayList<Record> arg)
    {
        String[] str;
        
        int rc = model.getRowCount();

        for (int i = 0; i < rc; i++)
        {
            model.removeRow(0);
        }
        
        for (int i = 0; i < arg.size(); i++)
        {
            str = new String[3];
            
            str[0] = Integer.toString(arg.get(i).getID());
            str[1] = arg.get(i).getRecipientName();
            str[2] = Integer.toString(arg.get(i).getBatch());
            
            model.addRow(str);
        }
    }
    
    public void search()
    {
        StringPatternMatching patternMatch = new StringPatternMatching();
        String pattern = tFieldSearch.getText();
        searchList = new ArrayList();
        
        for (int i = 0; i < recordList.size(); i++)
        {
            char[] temp1 = recordList.get(i).getRecipientName().toLowerCase().toCharArray();
            char[] temp2 = pattern.toLowerCase().toCharArray();
            
            if (patternMatch.KMP(temp1, temp2))
            {
                searchList.add(recordList.get(i));
            }
        }
        
        populateTable(searchList);
    }
    
    public void sort(ArrayList<Record> arg, boolean toggle)
    {
        for (int i = 0; i < arg.size(); i++)
        {
            for (int j = i + 1; j < arg.size(); j++)
            {
                if (toggle)
                {
                    if (arg.get(i).getRecipientName().compareTo(arg.get(j).getRecipientName()) > 0)
                    {
                        Record temp = arg.get(i);
                        arg.remove(i);
                        arg.add(i, arg.get(j - 1));
                        arg.remove(j);
                        arg.add(j, temp);
                    }
                }
                else
                {
                    if (arg.get(i).getBatch() > arg.get(j).getBatch())
                    {
                        Record temp = arg.get(i);
                        arg.remove(i);
                        arg.add(i, arg.get(j - 1));
                        arg.remove(j);
                        arg.add(j, temp);
                    }
                }
            }
        }
    }
}
