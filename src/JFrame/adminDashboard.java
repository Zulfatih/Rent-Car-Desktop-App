package JFrame;

import Login.Koneksi;
import com.sun.prism.image.ViewPort;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Zulfatih
 */
public class adminDashboard extends javax.swing.JFrame {

    /**
     * Creates new form adminDashboard
     */
    public adminDashboard() {
        initComponents();
        setExtendedState(JFrame.adminDashboard.MAXIMIZED_BOTH);
        tabel_listuser();
        tabel_listmobil();
        tabel_listadmin();
        tabel_history_transaksi();
        tabel_history_pengembalian();
        tabel_history_denda();
        id_admin_ai();
        id_car_ai();
    }

        private void id_admin_ai(){
        try{
            Connection konek = Koneksi.getKoneksi();
            String sql = "SELECT MAX(id_user) FROM tbl_user";
            PreparedStatement p = konek.prepareStatement(sql);
            ResultSet rs = p.executeQuery();
            if(rs.next()){
                int id = rs.getInt(1);
                int n = id+1;
                txt_id_admin.setText(Integer.toString(n));
            }
                
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        }

        private void id_car_ai(){
        try{
            Connection konek = Koneksi.getKoneksi();
            String sql = "SELECT MAX(id_car) FROM tbl_mobil";
            PreparedStatement p = konek.prepareStatement(sql);
            ResultSet rs = p.executeQuery();
            if(rs.next()){
                int id = rs.getInt(1);
                int n = id+1;
                txt_id_car.setText(Integer.toString(n));
            }
                
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        }
    
    private void update_table_addadmin(){
        try{
            Connection konek = Koneksi.getKoneksi();
            String sql = "SELECT * FROM tbl_user where user_type= 'admin'";
            PreparedStatement p = konek.prepareStatement(sql);
            ResultSet rs = null;
            rs = p.executeQuery();
            tabel_listadmin.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            
        }
    }
    
    private void clear_input_admin(){
        txt_id_admin.setText("");          
        txt_fullname_admin.setText("");
        txt_username_admin.setText(""); 
        txt_password_admin.setText("");
        txt_address_admin.setText("");
    }
    
    public void tabel_history_transaksi(){
        DefaultTableModel tb = new DefaultTableModel();
        tb.addColumn("Kode Transaksi");
        tb.addColumn("Nama Penyewa");
        tb.addColumn("Mobil Disewa");
        tb.addColumn("Pengambilan");
        tb.addColumn("Pengembalian");
        tb.addColumn("Harga Perhari");
        tb.addColumn("Total Hari Sewa");
        tb.addColumn("Total Bayar");
        tb.addColumn("Status");
        tabel_history_transaksi.setModel(tb);
        
        try{
            Connection Konek = Koneksi.getKoneksi();
            java.sql.Statement status = Konek.createStatement();
            String sql = "SELECT * FROM tbl_transaksi WHERE status='done' or status='success'";
            ResultSet success = status.executeQuery(sql);
            while(success.next()){
                tb.addRow(new Object[]{
                success.getString("id"),
                success.getString("nama_penyewa"),
                success.getString("mobil_disewa"),
                success.getString("tgl_pengambilan"),
                success.getString("tgl_pengembalian"),
                success.getString("harga_perhari"),
                success.getString("hari_sewa"),
                success.getString("total_bayar"),
                success.getString("status"),
            });
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data Gagal Ditampilkan");
        }
    }
    
    public void tabel_history_pengembalian(){
        DefaultTableModel tb = new DefaultTableModel();
        tb.addColumn("Kode Transaksi");
        tb.addColumn("Nama Penyewa");
        tb.addColumn("Tgl pengambilan");
        tb.addColumn("Tgl Pengembalian");
        tb.addColumn("Tgl Dikembalikan");
        tb.addColumn("Mobil Disewa");
        tb.addColumn("Total Hari Sewa");
        tb.addColumn("Status");
        tabel_history_transaksi1.setModel(tb);
        
        try{
            Connection Konek = Koneksi.getKoneksi();
            java.sql.Statement status = Konek.createStatement();
            String sql = "SELECT * FROM tbl_pengembalian";
            ResultSet success = status.executeQuery(sql);
            while(success.next()){
                tb.addRow(new Object[]{
                success.getString("kode_transaksi"),
                success.getString("nama_penyewa"),
                success.getString("jadwal_pengambilan"),
                success.getString("jadwal_pengembalian"),
                success.getString("tgl_pengembalian"),
                success.getString("mobil_disewa"),
                success.getString("total_hari_sewa"),
                success.getString("status")
            });
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data Gagal Ditampilkan");
        }
    }
    
    public void tabel_history_denda(){
        DefaultTableModel tb = new DefaultTableModel();
        tb.addColumn("Kode Transaksi");
        tb.addColumn("Nama Penyewa");
        tb.addColumn("Tgl Pengembalian");
        tb.addColumn("Tgl Dikembalikan");
        tb.addColumn("Mobil Disewa");
        tb.addColumn("Denda Perhari");
        tb.addColumn("Keterlambatan");
        tb.addColumn("Total Denda");
        tb.addColumn("Status");
        tabel_history_denda.setModel(tb);
        
        try{
            Connection Konek = Koneksi.getKoneksi();
            java.sql.Statement status = Konek.createStatement();
            String sql = "SELECT * FROM tbl_denda";
            ResultSet success = status.executeQuery(sql);
            while(success.next()){
                tb.addRow(new Object[]{
                success.getString("kode_transaksi"),
                success.getString("nama_penyewa"),
                success.getString("tgl_pengembalian"),
                success.getString("tgl_dikembalikan"),
                success.getString("mobil_disewa"),
                success.getString("denda_perhari"),
                success.getString("keterlambatan"),
                success.getString("total_denda"),
                success.getString("status")
            });
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data Gagal Ditampilkan");
        }
    }
    
    public void tabel_listadmin(){
        DefaultTableModel tb = new DefaultTableModel();
        tb.addColumn("ID Admin");
        tb.addColumn("Nama Admin");
        tb.addColumn("Username");
        tb.addColumn("Password");
        tb.addColumn("Alamat");
        tb.addColumn("User Type");
        tabel_listadmin.setModel(tb);
        
        try{
            Connection Konek = Koneksi.getKoneksi();
            java.sql.Statement status = Konek.createStatement();
            String sql = "SELECT * FROM tbl_user WHERE user_type = 'admin'";
            ResultSet success = status.executeQuery(sql);
            while(success.next()){
                tb.addRow(new Object[]{
                success.getString("id_user"),
                success.getString("name"),
                success.getString("username"),
                success.getString("password"),
                success.getString("alamat"),
                success.getString("user_type"),
            });
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data Gagal Ditampilkan");
        }
    }
    
    
    
    public void tabel_listuser(){
        DefaultTableModel tb = new DefaultTableModel();
        tb.addColumn("ID User");
        tb.addColumn("Fullname");
        tb.addColumn("Username");
        tb.addColumn("Address");
        tb.addColumn("User Type");
        tabel_datauser.setModel(tb);
        
        try{
            Connection Konek = Koneksi.getKoneksi();
            java.sql.Statement status = Konek.createStatement();
            String sql = "SELECT * FROM tbl_user";
            ResultSet success = status.executeQuery(sql);
            while(success.next()){
                tb.addRow(new Object[]{
                success.getString("id_user"),
                success.getString("name"),
                success.getString("username"),
                success.getString("alamat"),
                success.getString("user_type"),
            });
                
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data Gagal Ditampilkan");
        }
    }
    
    private void update_table_addcar(){
        try{
            Connection konek = Koneksi.getKoneksi();
            String sql = "SELECT * FROM tbl_mobil";
            PreparedStatement p = konek.prepareStatement(sql);
            ResultSet rs = null;
            rs = p.executeQuery();
            tabel_listmobil.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            
        }
    }
    
    private void clear_input_car(){
        txt_id_car.setText("");          
        txt_name_car.setText("");
        txt_plat_car.setText(""); 
        txt_tahun_car.setText("");
        txt_ac_car.setSelectedIndex(0);
        txt_seat_car.setText("");
        txt_transmisi_car.setSelectedIndex(0);
        txt_fuel_car.setText("");
        txt_price_car.setText("");
        txt_status_car.setSelectedIndex(0);
    }
    
    public void tabel_listmobil(){
        DefaultTableModel tb = new DefaultTableModel();
        tb.addColumn("ID Mobil");
        tb.addColumn("Nama Mobil");
        tb.addColumn("Plat Nomor");
        tb.addColumn("Tahun");
        tb.addColumn("AC");
        tb.addColumn("Seat");
        tb.addColumn("Transmisi");
        tb.addColumn("Fuel");
        tb.addColumn("Price");
        tb.addColumn("Status");
        tabel_listmobil.setModel(tb);
        
        try{
            Connection Konek = Koneksi.getKoneksi();
            java.sql.Statement status = Konek.createStatement();
            String sql = "SELECT * FROM tbl_mobil";
            ResultSet success = status.executeQuery(sql);
            while(success.next()){
                tb.addRow(new Object[]{
                success.getString("id_car"),
                success.getString("name"),
                success.getString("plat"),
                success.getString("year"),
                success.getString("ac"),
                success.getString("seat"),
                success.getString("transmission"),
                success.getString("fuel"),
                success.getString("price"),
                success.getString("status"),
                
            });
                
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data Gagal Ditampilkan");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button_addcar = new javax.swing.JButton();
        button_addadmin = new javax.swing.JButton();
        button_history = new javax.swing.JButton();
        button_logout = new javax.swing.JButton();
        button_datauser = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        button_history1 = new javax.swing.JButton();
        button_history2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_id_car = new javax.swing.JTextField();
        txt_name_car = new javax.swing.JTextField();
        txt_plat_car = new javax.swing.JTextField();
        txt_tahun_car = new javax.swing.JTextField();
        button_save_car = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_price_car = new javax.swing.JTextField();
        txt_ac_car = new javax.swing.JComboBox<>();
        txt_transmisi_car = new javax.swing.JComboBox<>();
        txt_fuel_car = new javax.swing.JTextField();
        txt_status_car = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        txt_seat_car = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabel_listmobil = new javax.swing.JTable();
        button_edit_car = new javax.swing.JButton();
        button_delete_car = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_id_admin = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txt_fullname_admin = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_username_admin = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txt_password_admin = new javax.swing.JPasswordField();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_address_admin = new javax.swing.JTextArea();
        tombol_save_admin = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabel_listadmin = new javax.swing.JTable();
        tombol_edit_admin = new javax.swing.JButton();
        tombol_delete_admin = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabel_history_transaksi = new javax.swing.JTable();
        button_print_transaksi = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabel_history_transaksi1 = new javax.swing.JTable();
        button_print_pengembalian = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabel_history_denda = new javax.swing.JTable();
        button_print_denda = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabel_datauser = new javax.swing.JTable();
        button_print_datauser = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1280, 720));

        container.setBackground(new java.awt.Color(204, 204, 204));

        button_addcar.setBackground(new java.awt.Color(204, 204, 204));
        button_addcar.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        button_addcar.setText("Tambah Mobil");
        button_addcar.setBorder(null);
        button_addcar.setBorderPainted(false);
        button_addcar.setContentAreaFilled(false);
        button_addcar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_addcar.setFocusPainted(false);
        button_addcar.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        button_addcar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_addcarActionPerformed(evt);
            }
        });

        button_addadmin.setBackground(new java.awt.Color(204, 204, 204));
        button_addadmin.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        button_addadmin.setText("Tambah Admin");
        button_addadmin.setBorder(null);
        button_addadmin.setBorderPainted(false);
        button_addadmin.setContentAreaFilled(false);
        button_addadmin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_addadmin.setFocusPainted(false);
        button_addadmin.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        button_addadmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_addadminActionPerformed(evt);
            }
        });

        button_history.setBackground(new java.awt.Color(204, 204, 204));
        button_history.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        button_history.setText("Histori Transaksi");
        button_history.setBorder(null);
        button_history.setBorderPainted(false);
        button_history.setContentAreaFilled(false);
        button_history.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_history.setFocusPainted(false);
        button_history.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        button_history.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_historyActionPerformed(evt);
            }
        });

        button_logout.setBackground(new java.awt.Color(204, 204, 204));
        button_logout.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        button_logout.setForeground(new java.awt.Color(255, 0, 0));
        button_logout.setText("Keluar");
        button_logout.setBorder(null);
        button_logout.setBorderPainted(false);
        button_logout.setContentAreaFilled(false);
        button_logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_logout.setFocusPainted(false);
        button_logout.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        button_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_logoutActionPerformed(evt);
            }
        });

        button_datauser.setBackground(new java.awt.Color(204, 204, 204));
        button_datauser.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        button_datauser.setText("Data User");
        button_datauser.setBorder(null);
        button_datauser.setBorderPainted(false);
        button_datauser.setContentAreaFilled(false);
        button_datauser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_datauser.setFocusPainted(false);
        button_datauser.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        button_datauser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_datauserActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\zulfa\\OneDrive\\Desktop\\Rent-Car-Project\\src\\main\\java\\Assets\\logo.png")); // NOI18N
        jLabel1.setLabelFor(jLabel1);
        jLabel1.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                jLabel1ComponentAdded(evt);
            }
        });

        button_history1.setBackground(new java.awt.Color(204, 204, 204));
        button_history1.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        button_history1.setText("Histori Pengembalian");
        button_history1.setBorder(null);
        button_history1.setBorderPainted(false);
        button_history1.setContentAreaFilled(false);
        button_history1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_history1.setFocusPainted(false);
        button_history1.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        button_history1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_history1ActionPerformed(evt);
            }
        });

        button_history2.setBackground(new java.awt.Color(204, 204, 204));
        button_history2.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        button_history2.setText("Histori Denda");
        button_history2.setBorder(null);
        button_history2.setBorderPainted(false);
        button_history2.setContentAreaFilled(false);
        button_history2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_history2.setFocusPainted(false);
        button_history2.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        button_history2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_history2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout containerLayout = new javax.swing.GroupLayout(container);
        container.setLayout(containerLayout);
        containerLayout.setHorizontalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_history2)
                    .addComponent(button_history1)
                    .addComponent(button_logout)
                    .addComponent(button_addadmin)
                    .addComponent(button_datauser)
                    .addComponent(button_addcar)
                    .addGroup(containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel1)
                        .addComponent(button_history)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        containerLayout.setVerticalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(39, 39, 39)
                .addComponent(button_addcar)
                .addGap(18, 18, 18)
                .addComponent(button_addadmin)
                .addGap(18, 18, 18)
                .addComponent(button_history)
                .addGap(18, 18, 18)
                .addComponent(button_history1)
                .addGap(18, 18, 18)
                .addComponent(button_history2)
                .addGap(18, 18, 18)
                .addComponent(button_datauser)
                .addGap(18, 18, 18)
                .addComponent(button_logout)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.setBackground(new java.awt.Color(204, 204, 204));
        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane1.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(952, 720));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(952, 720));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jPanel5.setBackground(new java.awt.Color(153, 0, 0));
        jPanel5.setPreferredSize(new java.awt.Dimension(952, 78));

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Add Mobil");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(459, 459, 459)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel3.setText("ID");

        jLabel4.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel4.setText("Name");

        jLabel5.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel5.setText("Plat");

        jLabel6.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel6.setText("Tahun");

        txt_id_car.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N

        txt_name_car.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N

        txt_plat_car.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N

        txt_tahun_car.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N

        button_save_car.setBackground(new java.awt.Color(153, 0, 0));
        button_save_car.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        button_save_car.setForeground(new java.awt.Color(255, 255, 255));
        button_save_car.setText("Save");
        button_save_car.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_save_carActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel7.setText("AC");

        jLabel8.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel8.setText("Transmisi");

        jLabel9.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel9.setText("Fuel");

        jLabel10.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel10.setText("Price");

        jLabel11.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel11.setText("Status");

        txt_price_car.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N

        txt_ac_car.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        txt_ac_car.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No" }));

        txt_transmisi_car.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        txt_transmisi_car.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Manual", "Matic" }));

        txt_fuel_car.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N

        txt_status_car.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        txt_status_car.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "available", "booked" }));

        jLabel22.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel22.setText("Seat");

        txt_seat_car.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N

        jScrollPane4.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N

        tabel_listmobil.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        tabel_listmobil.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabel_listmobil.setGridColor(new java.awt.Color(204, 204, 204));
        tabel_listmobil.setRowHeight(40);
        tabel_listmobil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_listmobilMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabel_listmobil);

        button_edit_car.setBackground(new java.awt.Color(153, 0, 0));
        button_edit_car.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        button_edit_car.setForeground(new java.awt.Color(255, 255, 255));
        button_edit_car.setText("Edit");
        button_edit_car.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_edit_carActionPerformed(evt);
            }
        });

        button_delete_car.setBackground(new java.awt.Color(153, 0, 0));
        button_delete_car.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        button_delete_car.setForeground(new java.awt.Color(255, 255, 255));
        button_delete_car.setText("Delete");
        button_delete_car.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_delete_carActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1129, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(213, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(txt_id_car, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_tahun_car, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_ac_car, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_name_car, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_plat_car, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(80, 80, 80)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel22))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_seat_car)
                    .addComponent(txt_status_car, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_price_car)
                    .addComponent(txt_fuel_car)
                    .addComponent(txt_transmisi_car, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(180, 180, 180))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(312, 312, 312)
                .addComponent(button_save_car, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_edit_car, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_delete_car, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_id_car, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_name_car, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_plat_car, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_tahun_car, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txt_ac_car, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_seat_car, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_transmisi_car, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_fuel_car, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_price_car, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_status_car, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))))
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_save_car)
                    .addComponent(button_edit_car)
                    .addComponent(button_delete_car))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tambah-mobil", jPanel1);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jPanel6.setBackground(new java.awt.Color(153, 0, 0));
        jPanel6.setPreferredSize(new java.awt.Dimension(952, 78));

        jLabel12.setBackground(new java.awt.Color(204, 204, 204));
        jLabel12.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Add Admin");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(459, 459, 459)
                .addComponent(jLabel12)
                .addContainerGap(463, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel12)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel13.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel13.setText("ID");

        txt_id_admin.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        txt_id_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_id_adminActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel14.setText("Fullname");

        txt_fullname_admin.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        txt_fullname_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fullname_adminActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel15.setText("Username");

        txt_username_admin.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        txt_username_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_username_adminActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel16.setText("Password");

        txt_password_admin.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        txt_password_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_password_adminActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel17.setText("Address");

        txt_address_admin.setColumns(20);
        txt_address_admin.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        txt_address_admin.setRows(5);
        txt_address_admin.setPreferredSize(new java.awt.Dimension(100, 114));
        jScrollPane1.setViewportView(txt_address_admin);

        tombol_save_admin.setBackground(new java.awt.Color(153, 0, 0));
        tombol_save_admin.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        tombol_save_admin.setForeground(new java.awt.Color(255, 255, 255));
        tombol_save_admin.setText("Save");
        tombol_save_admin.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tombol_save_admin.setBorderPainted(false);
        tombol_save_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombol_save_adminActionPerformed(evt);
            }
        });

        jScrollPane5.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N

        tabel_listadmin.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        tabel_listadmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabel_listadmin.setGridColor(new java.awt.Color(204, 204, 204));
        tabel_listadmin.setRowHeight(40);
        tabel_listadmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_listadminMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tabel_listadmin);

        tombol_edit_admin.setBackground(new java.awt.Color(153, 0, 0));
        tombol_edit_admin.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        tombol_edit_admin.setForeground(new java.awt.Color(255, 255, 255));
        tombol_edit_admin.setText("Edit");
        tombol_edit_admin.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tombol_edit_admin.setBorderPainted(false);
        tombol_edit_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombol_edit_adminActionPerformed(evt);
            }
        });

        tombol_delete_admin.setBackground(new java.awt.Color(153, 0, 0));
        tombol_delete_admin.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        tombol_delete_admin.setForeground(new java.awt.Color(255, 255, 255));
        tombol_delete_admin.setText("Delete");
        tombol_delete_admin.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tombol_delete_admin.setBorderPainted(false);
        tombol_delete_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombol_delete_adminActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1129, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_username_admin, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14))
                                .addGap(24, 24, 24)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_id_admin, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                                    .addComponent(txt_fullname_admin))))
                        .addGap(53, 53, 53)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel16))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_password_admin)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(260, 260, 260)
                .addComponent(tombol_save_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tombol_edit_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tombol_delete_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(txt_id_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(9, 9, 9)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(txt_fullname_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(jLabel17)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txt_username_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_password_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tombol_save_admin)
                    .addComponent(tombol_edit_admin)
                    .addComponent(tombol_delete_admin))
                .addGap(36, 36, 36)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tambah-admin", jPanel2);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jPanel7.setBackground(new java.awt.Color(153, 0, 0));
        jPanel7.setPreferredSize(new java.awt.Dimension(952, 78));

        jLabel18.setBackground(new java.awt.Color(204, 204, 204));
        jLabel18.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Histori Transaksi");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(336, 336, 336))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addContainerGap())
        );

        jScrollPane6.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N

        tabel_history_transaksi.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        tabel_history_transaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabel_history_transaksi.setGridColor(new java.awt.Color(204, 204, 204));
        tabel_history_transaksi.setRowHeight(40);
        jScrollPane6.setViewportView(tabel_history_transaksi);

        button_print_transaksi.setBackground(new java.awt.Color(153, 0, 0));
        button_print_transaksi.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        button_print_transaksi.setForeground(new java.awt.Color(255, 255, 255));
        button_print_transaksi.setText("Print");
        button_print_transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_print_transaksiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 1129, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1117, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 886, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button_print_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(button_print_transaksi)
                .addGap(0, 115, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("histori-transaksi", jPanel3);

        jPanel11.setBackground(new java.awt.Color(204, 204, 204));

        jPanel12.setBackground(new java.awt.Color(153, 0, 0));
        jPanel12.setPreferredSize(new java.awt.Dimension(952, 78));

        jLabel20.setBackground(new java.awt.Color(204, 204, 204));
        jLabel20.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Histori Pengembalian");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(408, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addGap(307, 307, 307))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addContainerGap())
        );

        jScrollPane7.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N

        tabel_history_transaksi1.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        tabel_history_transaksi1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabel_history_transaksi1.setGridColor(new java.awt.Color(204, 204, 204));
        tabel_history_transaksi1.setRowHeight(40);
        jScrollPane7.setViewportView(tabel_history_transaksi1);

        button_print_pengembalian.setBackground(new java.awt.Color(153, 0, 0));
        button_print_pengembalian.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        button_print_pengembalian.setForeground(new java.awt.Color(255, 255, 255));
        button_print_pengembalian.setText("Print");
        button_print_pengembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_print_pengembalianActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 1079, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(button_print_pengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)))
                .addGap(39, 39, 39))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_print_pengembalian)
                .addGap(0, 275, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 50, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("histori-pengembalian", jPanel9);

        jPanel13.setBackground(new java.awt.Color(204, 204, 204));

        jPanel14.setBackground(new java.awt.Color(153, 0, 0));
        jPanel14.setPreferredSize(new java.awt.Dimension(952, 78));

        jLabel21.setBackground(new java.awt.Color(204, 204, 204));
        jLabel21.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Histori Denda");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(490, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addGap(343, 343, 343))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addContainerGap())
        );

        jScrollPane8.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N

        tabel_history_denda.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        tabel_history_denda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabel_history_denda.setGridColor(new java.awt.Color(204, 204, 204));
        tabel_history_denda.setRowHeight(40);
        jScrollPane8.setViewportView(tabel_history_denda);

        button_print_denda.setBackground(new java.awt.Color(153, 0, 0));
        button_print_denda.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        button_print_denda.setForeground(new java.awt.Color(255, 255, 255));
        button_print_denda.setText("Print");
        button_print_denda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_print_dendaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 1064, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8)
                .addGap(68, 68, 68))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button_print_denda, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(button_print_denda)
                .addGap(0, 269, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 34, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("histori-denda", jPanel10);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setPreferredSize(new java.awt.Dimension(952, 800));

        jPanel8.setBackground(new java.awt.Color(153, 0, 0));
        jPanel8.setPreferredSize(new java.awt.Dimension(952, 78));

        jLabel19.setBackground(new java.awt.Color(204, 204, 204));
        jLabel19.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Data User");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(566, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addGap(384, 384, 384))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addContainerGap())
        );

        jScrollPane3.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N

        tabel_datauser.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        tabel_datauser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabel_datauser.setGridColor(new java.awt.Color(204, 204, 204));
        tabel_datauser.setRowHeight(40);
        jScrollPane3.setViewportView(tabel_datauser);

        button_print_datauser.setBackground(new java.awt.Color(153, 0, 0));
        button_print_datauser.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        button_print_datauser.setForeground(new java.awt.Color(255, 255, 255));
        button_print_datauser.setText("Print");
        button_print_datauser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_print_datauserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 1129, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button_print_datauser, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(button_print_datauser)
                .addContainerGap(115, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("datauser", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1129, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(container, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE))
                .addGap(0, 566, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1ComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jLabel1ComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1ComponentAdded

    private void button_datauserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_datauserActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_button_datauserActionPerformed

    private void button_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_logoutActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new Login().setVisible(true);
    }//GEN-LAST:event_button_logoutActionPerformed

    private void button_historyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_historyActionPerformed
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_button_historyActionPerformed

    private void button_addadminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_addadminActionPerformed
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_button_addadminActionPerformed

    private void button_addcarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_addcarActionPerformed
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_button_addcarActionPerformed

    private void button_save_carActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_save_carActionPerformed
        // TODO add your handling code here:
        String id = txt_id_car.getText().trim();
        String nama = txt_name_car.getText().trim();
        String plat = txt_plat_car.getText().trim();
        String tahun = txt_tahun_car.getText().trim();
        String ac = txt_ac_car.getSelectedItem().toString().trim();
        String seat = txt_seat_car.getText().trim();
        String transmisi = txt_transmisi_car.getSelectedItem().toString().trim();
        String fuel = txt_fuel_car.getText().trim();
        String price = txt_price_car.getText().trim();
        String status = txt_status_car.getSelectedItem().toString().trim();
        
        if(id.equals("") || nama.equals("") || plat.equals("") || tahun.equals("") || ac.equals("") || seat.equals("") || transmisi.equals("") || fuel.equals("") || price.equals("") || status.equals("")){
            JOptionPane.showMessageDialog(null, "Ada bagian yang kosong");
        }else{
            try{
                Connection konek = Koneksi.getKoneksi();
                String sql = "INSERT INTO tbl_mobil (id_car, name, plat, year, ac, seat, transmission, fuel, price, status) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement p = konek.prepareStatement(sql);
                
                p.setString(1, id);
                p.setString(2, nama);
                p.setString(3, plat);
                p.setString(4, tahun);
                p.setString(5, ac);
                p.setString(6, seat);
                p.setString(7, transmisi);
                p.setString(8, fuel);
                p.setString(9, price);
                p.setString(10, status);
                p.executeUpdate();
                p.close();
                update_table_addcar();
                JOptionPane.showMessageDialog(null, "Add Car Successfully");
                clear_input_car();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "System Error");
            }
            
        }
    }//GEN-LAST:event_button_save_carActionPerformed

    private void txt_id_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_id_adminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_id_adminActionPerformed

    private void txt_fullname_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fullname_adminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fullname_adminActionPerformed

    private void txt_username_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_username_adminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_username_adminActionPerformed

    private void txt_password_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_password_adminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_password_adminActionPerformed

    private void tombol_save_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombol_save_adminActionPerformed
        // TODO add your handling code here:
        String id = txt_id_admin.getText().toString().trim();
        String fullname = txt_fullname_admin.getText().toString().trim();
        String username = txt_username_admin.getText().toString().trim();
        String password = txt_password_admin.getText().toString().trim();
        String address = txt_address_admin.getText().toString().trim();
        String type = "admin";

        if(username.equals("") || password.equals("") || fullname.equals("") || address.equals("")){
            JOptionPane.showMessageDialog(null, "Ada bagian yang kosong");
        }else{
            try{
                Connection konek = Koneksi.getKoneksi();
                String sql = "INSERT INTO tbl_user (id_user, name, username, password, alamat, user_type) VALUES(?, ?, ?, ?, ?, ?)";
                PreparedStatement p = konek.prepareStatement(sql);

                p.setString(1, id);
                p.setString(2, fullname);
                p.setString(3, username);
                p.setString(4, password);
                p.setString(5, address);
                p.setString(6, type);
                p.executeUpdate();
                p.close();
                update_table_addadmin();
                JOptionPane.showMessageDialog(null, "Add Admin Successfully");
                clear_input_admin();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "System Error");
            }

        }
        
    }//GEN-LAST:event_tombol_save_adminActionPerformed

    private void tabel_listadminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_listadminMouseClicked
        int row = tabel_listadmin.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel)tabel_listadmin.getModel();
        //Muhammad Zulfatih - R6T
        txt_id_admin.setText(model.getValueAt(row, 0).toString());
        txt_fullname_admin.setText(model.getValueAt(row, 1).toString());
        txt_username_admin.setText(model.getValueAt(row, 2).toString());
        txt_password_admin.setText(model.getValueAt(row, 3).toString());
        txt_address_admin.setText(model.getValueAt(row, 4).toString());
    }//GEN-LAST:event_tabel_listadminMouseClicked

    private void tombol_edit_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombol_edit_adminActionPerformed
        try{
                String sql = "UPDATE tbl_user set"
                        + " name = '" + txt_fullname_admin.getText() + "',"
                        + " username = '" + txt_username_admin.getText()+ "',"
                        + " password = '" + txt_password_admin.getText() + "',"
                        + " alamat = '" + txt_address_admin.getText() + "',"
                        + " user_type = 'admin'"
                        + " where id_user = '" + txt_id_admin.getText() + "'";
                Connection konek = Koneksi.getKoneksi();
                PreparedStatement p = konek.prepareStatement(sql);          //Muhammad Zulfatih - R6T 
                p.execute();                                               
                update_table_addadmin();
                JOptionPane.showMessageDialog(null, "Edit Data Berhasil");
                clear_input_admin();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "System Error");
            }
    }//GEN-LAST:event_tombol_edit_adminActionPerformed

    private void tombol_delete_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombol_delete_adminActionPerformed
        try{
                Connection konek = Koneksi.getKoneksi();
                int row = tabel_listadmin.getSelectedRow();
                String value = (tabel_listadmin.getModel().getValueAt(row, 0).toString());
                String sql = "DELETE FROM tbl_user WHERE id_user="+value;
                PreparedStatement p = konek.prepareStatement(sql);
                p.executeUpdate();        
                p.close();
                update_table_addadmin();
                JOptionPane.showMessageDialog(null, "Hapus Data Berhasil");
                clear_input_admin();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "System Error");
            }
    }//GEN-LAST:event_tombol_delete_adminActionPerformed

    private void button_edit_carActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_edit_carActionPerformed
        try{
                String sql = "UPDATE tbl_mobil set"
                        + " name = '" + txt_name_car.getText() + "',"
                        + " plat = '" + txt_plat_car.getText()+ "',"
                        + " year = '" + txt_tahun_car.getText() + "',"
                        + " ac = '" + txt_ac_car.getSelectedItem().toString() + "',"
                        + " seat = '" + txt_seat_car.getText() + "',"
                        + " transmission = '" + txt_transmisi_car.getSelectedItem().toString() + "',"
                        + " fuel = '" + txt_fuel_car.getText() + "',"
                        + " price = '" + txt_price_car.getText() + "',"
                        + " status = '" + txt_status_car.getSelectedItem().toString() + "'"
                        + " where id_car = '" + txt_id_car.getText() + "'";
                Connection konek = Koneksi.getKoneksi();
                PreparedStatement p = konek.prepareStatement(sql);          //Muhammad Zulfatih - R6T 
                p.execute();                                               
                update_table_addcar();
                JOptionPane.showMessageDialog(null, "Edit Data Berhasil");
                clear_input_car();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "System Error");
            }
    }//GEN-LAST:event_button_edit_carActionPerformed

    private void button_delete_carActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_delete_carActionPerformed
        try{
                Connection konek = Koneksi.getKoneksi();
                int row = tabel_listmobil.getSelectedRow();
                String value = (tabel_listmobil.getModel().getValueAt(row, 0).toString());
                String sql = "DELETE FROM tbl_mobil WHERE id_car="+value;
                PreparedStatement p = konek.prepareStatement(sql);
                p.executeUpdate();          // Muhammad Zufatih
                p.close();                  // R6T
                update_table_addcar();
                JOptionPane.showMessageDialog(null, "Hapus Data Berhasil");
                clear_input_car();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "System Error");
            }
    }//GEN-LAST:event_button_delete_carActionPerformed

    private void tabel_listmobilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_listmobilMouseClicked
        int row = tabel_listmobil.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel)tabel_listmobil.getModel();
        txt_id_car.setText(model.getValueAt(row, 0).toString());
        txt_name_car.setText(model.getValueAt(row, 1).toString());
        txt_plat_car.setText(model.getValueAt(row, 2).toString());
        txt_tahun_car.setText(model.getValueAt(row, 3).toString());
        txt_ac_car.setSelectedItem(model.getValueAt(row, 4).toString());
        txt_seat_car.setText(model.getValueAt(row, 5).toString());
        txt_transmisi_car.setSelectedItem(model.getValueAt(row, 6).toString());
        txt_fuel_car.setText(model.getValueAt(row, 7).toString());
        txt_price_car.setText(model.getValueAt(row, 8).toString());
        txt_status_car.setSelectedItem(model.getValueAt(row, 9).toString());
    }//GEN-LAST:event_tabel_listmobilMouseClicked

    private void button_print_datauserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_print_datauserActionPerformed
//        try{
//            String namaFile = "C:\\Users\\zulfa\\OneDrive\\Documents\\Muhammad Zulfatih\\data kuliah\\R6T\\Pemrograman Visual\\Rent_Car\\src\\Report\\report_datauser.jrxml";
//            Connection con = Koneksi.getKoneksi();
//            HashMap parameter = new HashMap();
//            File report_file = new File(namaFile);
//            JasperReport jasperreport = (JasperReport) JRLoader.loadObject(report_file);
//            JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport, parameter, con);
//            JasperViewer.viewReport(jasperprint, false);
//            JasperViewer.setDefaultLookAndFeelDecorated(true);
//        }catch(Exception e){
//            JOptionPane.showMessageDialog(null, e.getMessage());
//        }

        try {
            Connection konek = Koneksi.getKoneksi();
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\zulfa\\OneDrive\\Documents\\Muhammad Zulfatih\\data kuliah\\R6T\\Pemrograman Visual\\Rent_Car\\src\\Report\\report_datauser.jrxml");
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, null, konek);
            JasperViewer.viewReport(jprint);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
//        try{
//            JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("Report/report_datauser.jrxml"), null, Koneksi.getKoneksi());
//            JasperViewer.viewReport(jp);
//        }catch(Exception e){
//            JOptionPane.showMessageDialog(rootPane, e);
//        }
    }//GEN-LAST:event_button_print_datauserActionPerformed

    private void button_print_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_print_transaksiActionPerformed
        try {
            Connection konek = Koneksi.getKoneksi();
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\zulfa\\OneDrive\\Documents\\Muhammad Zulfatih\\data kuliah\\R6T\\Pemrograman Visual\\Rent_Car\\src\\Report\\report_histori_transaksi.jrxml");
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, null, konek);
            JasperViewer.viewReport(jprint);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_button_print_transaksiActionPerformed

    private void button_print_pengembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_print_pengembalianActionPerformed
        try {
            Connection konek = Koneksi.getKoneksi();
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\zulfa\\OneDrive\\Documents\\Muhammad Zulfatih\\data kuliah\\R6T\\Pemrograman Visual\\Rent_Car\\src\\Report\\report_histori_pengembalian.jrxml");
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, null, konek);
            JasperViewer.viewReport(jprint);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_button_print_pengembalianActionPerformed

    private void button_print_dendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_print_dendaActionPerformed
        try {
            Connection konek = Koneksi.getKoneksi();
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\zulfa\\OneDrive\\Documents\\Muhammad Zulfatih\\data kuliah\\R6T\\Pemrograman Visual\\Rent_Car\\src\\Report\\report_histori_denda.jrxml");
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, null, konek);
            JasperViewer.viewReport(jprint);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_button_print_dendaActionPerformed

    private void button_history1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_history1ActionPerformed
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_button_history1ActionPerformed

    private void button_history2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_history2ActionPerformed
        jTabbedPane1.setSelectedIndex(4);
    }//GEN-LAST:event_button_history2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(adminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(adminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(adminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(adminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new adminDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_addadmin;
    private javax.swing.JButton button_addcar;
    private javax.swing.JButton button_datauser;
    private javax.swing.JButton button_delete_car;
    private javax.swing.JButton button_edit_car;
    private javax.swing.JButton button_history;
    private javax.swing.JButton button_history1;
    private javax.swing.JButton button_history2;
    private javax.swing.JButton button_logout;
    private javax.swing.JButton button_print_datauser;
    private javax.swing.JButton button_print_denda;
    private javax.swing.JButton button_print_pengembalian;
    private javax.swing.JButton button_print_transaksi;
    private javax.swing.JButton button_save_car;
    public static final javax.swing.JPanel container = new javax.swing.JPanel();
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private static final javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
    private static final javax.swing.JPanel jPanel10 = new javax.swing.JPanel();
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private static final javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
    private static final javax.swing.JPanel jPanel3 = new javax.swing.JPanel();
    private static final javax.swing.JPanel jPanel4 = new javax.swing.JPanel();
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private static final javax.swing.JPanel jPanel9 = new javax.swing.JPanel();
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private static final javax.swing.JTabbedPane jTabbedPane1 = new javax.swing.JTabbedPane();
    private javax.swing.JTable tabel_datauser;
    private javax.swing.JTable tabel_history_denda;
    private javax.swing.JTable tabel_history_transaksi;
    private javax.swing.JTable tabel_history_transaksi1;
    private javax.swing.JTable tabel_listadmin;
    private javax.swing.JTable tabel_listmobil;
    private javax.swing.JButton tombol_delete_admin;
    private javax.swing.JButton tombol_edit_admin;
    private javax.swing.JButton tombol_save_admin;
    private javax.swing.JComboBox<String> txt_ac_car;
    private javax.swing.JTextArea txt_address_admin;
    private javax.swing.JTextField txt_fuel_car;
    private javax.swing.JTextField txt_fullname_admin;
    private javax.swing.JTextField txt_id_admin;
    private javax.swing.JTextField txt_id_car;
    private javax.swing.JTextField txt_name_car;
    private javax.swing.JPasswordField txt_password_admin;
    private javax.swing.JTextField txt_plat_car;
    private javax.swing.JTextField txt_price_car;
    private javax.swing.JTextField txt_seat_car;
    private javax.swing.JComboBox<String> txt_status_car;
    private javax.swing.JTextField txt_tahun_car;
    private javax.swing.JComboBox<String> txt_transmisi_car;
    private javax.swing.JTextField txt_username_admin;
    // End of variables declaration//GEN-END:variables
}
