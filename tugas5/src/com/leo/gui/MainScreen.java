package com.leo.gui;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class MainScreen extends  JFrame {
    private static final String URL = "jdbc:mysql://localhost:3306/contoh";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private JPanel PanelMain;
    private JTable jTabelMahasiswa;
    private JTextField textFieldNim;
    private JTextField textFieldNama;
    private JTextField textFieldIpk;
    private JButton buttonAdd;
    private JButton buttonUpdate;
    private JButton buttonDelete;
    private JTextField textFieldFiter;
    private JButton buttonFilter;
    private JButton buttonClear;
    private JScrollPane scrollpane;
    private DefaultTableModel defaultTableModel = new DefaultTableModel();
    private String selectedNim = "";

    public  MainScreen(){
        this.setContentPane(PanelMain);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        refreshTable(
                getMahasiswa()
        );

        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nim = textFieldNim.getText();
                String nama = textFieldNama.getText();
                double ipk = Double.parseDouble(textFieldIpk.getText());
                Mahasiswa mahasiswa = new Mahasiswa();
                mahasiswa.setNim(nim);
                mahasiswa.setNama(nama);
                mahasiswa.setIpk(ipk);
                insertMahasiswa(mahasiswa);
                clearFrom();
                refreshTable(getMahasiswa());
            }
        });
        this.jTabelMahasiswa.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        int row = jTabelMahasiswa.getSelectedRow();
                        if (row < 0)
                            return;
                        String nim = jTabelMahasiswa.getValueAt(row,0).toString();
                        if (selectedNim.equals(nim))
                            return;
                        selectedNim = nim;
                        String nama = jTabelMahasiswa.getValueAt(row,1).toString();
                        String ipk = jTabelMahasiswa.getValueAt(row,2).toString();

                        textFieldNim.setText(nim);
                        textFieldNama.setText(nama);
                        textFieldIpk.setText(ipk);
                    }
                }
        );
        buttonUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nim = textFieldNim.getText();
                String nama = textFieldNama.getText();
                double ipk = Double.parseDouble(textFieldIpk.getText());
                Mahasiswa mahasiswa = new Mahasiswa();
                mahasiswa.setNim(nim);
                mahasiswa.setNama(nama);
                mahasiswa.setIpk(ipk);
                updateMahasiswa(mahasiswa);
                clearFrom();
                refreshTable(getMahasiswa());
            }
        });
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nim = textFieldNim.getText();
                deleteMahasiswa(nim);
                clearFrom();
                refreshTable(getMahasiswa());
            }
        });
        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFrom();
            }
        });

        buttonFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nama = textFieldFiter.getText();
                refreshTable(filterMahasiswa(nama));
            }
        });
    }
    public static void main(String[] args) {
        MainScreen mainScreen = new MainScreen();
        mainScreen.setVisible(true);



    }
    public  void refreshTable(List<Mahasiswa> arrayListMahasiswa){
            Object [][] data = new Object[arrayListMahasiswa.size()][3];
            for (int i = 0; i < arrayListMahasiswa.size(); i++){
                data[i] = new Object[]{
                        arrayListMahasiswa.get(i).getNim(),
                        arrayListMahasiswa.get(i).getNama(),
                        arrayListMahasiswa.get(i).getIpk()
                };

            }
            defaultTableModel = new DefaultTableModel(
                data,
                    new String [] {"NIM", "Nama", " Ipk"}
            );
            jTabelMahasiswa.setModel(defaultTableModel);

    }
    private void clearFrom(){
        textFieldNama.setText("");
        textFieldNim.setText("");
        textFieldIpk.setText("");
    }
    public static void executeSql(String sql){
        try{
            Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        }catch (Exception e){
        }
    }
    private static ResultSet executeQuery(String query){
        try{
            Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        }catch (Exception e){
            return null;
        }
    }
    public static  void insertMahasiswa(Mahasiswa mahasiswa){
        String sql = "insert into mahasiswa values (" +
                "'" +mahasiswa.getNim() + "', " +
                "'" +mahasiswa.getNama() + "', " +
                "'" +mahasiswa.getIpk() + "')";
        executeSql(sql);

    }
    public static void deleteMahasiswa(String nim){
        String sql = "delete from mahasiswa " +
                "where nim = '"+ nim +"'";
        executeSql(sql);
    }
    public static void updateMahasiswa(Mahasiswa mahasiswa){
        String sql = "update mahasiswa set " +
                "nama = '"+ mahasiswa.getNama()+"', " +
                "ipk = '"+ mahasiswa.getIpk()+"' " +
                "where nim = '"+ mahasiswa.getNim() +"'";
        executeSql(sql);
    }
    private static List<Mahasiswa> getMahasiswa(){
        List<Mahasiswa> arrayListMahasiswa = new ArrayList<>();
        ResultSet resultSet = executeQuery("SELECT * FROM mahasiswa");
        try{
            while (resultSet.next()){
                String nim = resultSet.getString("nim");
                String nama = resultSet.getString("nama");
                double ipk = Double.parseDouble(resultSet.getString("ipk"));
                System.out.print(nim);
                System.out.print(nama);
                System.out.print(ipk);
                System.out.println();

                Mahasiswa mahasiswa = new Mahasiswa();
                mahasiswa.setNim(nim);
                mahasiswa.setNama(nama);
                mahasiswa.setIpk(ipk);

                arrayListMahasiswa.add(mahasiswa);
            }
        }catch (Exception e){
            return null;
        }
        return arrayListMahasiswa;
    }
    private static List<Mahasiswa> filterMahasiswa(String fileterNama){
        List<Mahasiswa> arrayListMahasiswa = new ArrayList<>();
        ResultSet resultSet = executeQuery("SELECT * FROM mahasiswa where nama like '%" + fileterNama +"%'");
        try{
            while (resultSet.next()){
                String nim = resultSet.getString("nim");
                String nama = resultSet.getString("nama");
                double ipk = Double.parseDouble(resultSet.getString("ipk"));
                System.out.print(nim);
                System.out.print(nama);
                System.out.print(ipk);
                System.out.println();

                Mahasiswa mahasiswa = new Mahasiswa();
                mahasiswa.setNim(nim);
                mahasiswa.setNama(nama);
                mahasiswa.setIpk(ipk);

                arrayListMahasiswa.add(mahasiswa);
            }
        }catch (Exception e){
            return null;
        }
        return arrayListMahasiswa;
    }
}