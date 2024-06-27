package com.minim.library.pbo_perpus.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.minim.library.pbo_perpus.models.Anggota;
import com.minim.library.pbo_perpus.models.DBConnection;
import com.minim.library.pbo_perpus.models.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
public class anggotaController implements Initializable {
    Stage stage;
    ObservableList<Anggota> listAnggota = FXCollections.observableArrayList();
    boolean flagAdd = true;
    @FXML
    private TableColumn<Anggota, Integer> cidAnggota;
    @FXML
    private TableColumn<Anggota, String> cnama;
    @FXML
    private TableColumn<Anggota, String> calamat;
    @FXML
    private TableColumn<Anggota, String> cinstansi;
    @FXML
    private TableView<Anggota> tbAnggota;
    @FXML
    private Button bAdd;
    @FXML
    private Button bCancel;
    @FXML
    private Button bDel;
    @FXML
    private Button bEdit;
    @FXML
    private Button bUpdate;
    @FXML
    private TextField tfidAnggota;
    @FXML
    private TextField tfnama;
    @FXML
    private TextField tfalamat;
    @FXML
    private TextField tfinstansi;
    @FXML
    private TextField tfsearch;
    @FXML
    void add(ActionEvent event) {
        setButton(false,false,false,true,true);
        clearTeks();
        setTeks(true);
        tfnama.requestFocus();
    }
    @FXML
    void cancel(ActionEvent event) {
        setButton(true,true,true,false,false);
        clearTeks();
    }
    @FXML
    void del(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "hapus data ?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Connection conn = DBConnection.getConn();
            String sql = "DELETE FROM anggota WHERE id_anggota = ?";
            PreparedStatement st = null;
            try {
                st = conn.prepareStatement(sql);
                st.setString(1,tfidAnggota.getText());
                st.executeUpdate();
                loadData();
                clearTeks();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void edit(ActionEvent event) {
        flagAdd=false;
        setButton(false,false,false,true,true);
        setTeks(true);
        tfidAnggota.setEditable(false);
        tfnama.requestFocus();
    }
    @FXML
    void update(ActionEvent event) {
        Connection conn = DBConnection.getConn();
        if (flagAdd){
            String sql="INSERT INTO anggota(nama, alamat, instansi) values (?, ?, ?)";
            try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, tfnama.getText());
                st.setString(2, tfalamat.getText());
                st.setString(3, tfinstansi.getText());
                st.executeUpdate();
                loadData();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
//do something..
        } else {
            String sql = "UPDATE anggota SET nama = ?, alamat = ?, instansi = ? WHERE id_anggota = ?";
            try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1,tfnama.getText() );
                st.setString(2,tfalamat.getText());
                st.setString(3,tfinstansi.getText());
                st.setInt(4,Integer.valueOf(tfidAnggota.getText()));
                st.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        loadData();
        setButton(true,true,true,false,false);
        clearTeks();
    }
    @FXML
    private Button btnPilih;
    @FXML
    private TextField tfKeyword;
    @FXML
    void pilihProduk(MouseEvent event) {
        Anggota p = tbAnggota.getSelectionModel().getSelectedItem();
        tfidAnggota.setText(String.valueOf(p.getIdAnggota()));
        tfnama.setText(p.getNama());
        tfalamat.setText(p.getAlamat());
        tfinstansi.setText(p.getInstansi());
    }
    void initTabel(){
        cidAnggota.setCellValueFactory(new PropertyValueFactory<Anggota, Integer>("idAnggota"));
        cnama.setCellValueFactory(new PropertyValueFactory<Anggota, String>("nama"));
        calamat.setCellValueFactory(new PropertyValueFactory<Anggota, String>("alamat"));
        cinstansi.setCellValueFactory(new PropertyValueFactory<Anggota, String>("instansi"));
    }
    void loadData(){
        listAnggota = DBUtil.getDataAnggota();
        tbAnggota.setItems(listAnggota);
    }
    void setFilter(){
        System.out.println("Anggota Filtered");
        FilteredList<Anggota> filterData= new FilteredList<>(listAnggota,b->true);
        tfsearch.textProperty().addListener((observable,oldValue,newValue)->{
            filterData.setPredicate(Anggota->{
                if (newValue.isEmpty() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Anggota.getNama().toLowerCase().indexOf(searchKeyword)>-1){
                    return true;
                }else if (String.valueOf(Anggota.getIdAnggota()).toLowerCase().indexOf(searchKeyword) >-1){
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<Anggota> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(tbAnggota.comparatorProperty());
        tbAnggota.setItems(sortedData);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTabel();
        loadData();
        setFilter();
        setButton(true,true,true,false,false);
        setTeks(false);
    }
    protected void clearTeks(){
        tfidAnggota.setText(null);
        tfnama.setText(null);
        tfalamat.setText(null);
        tfinstansi.setText(null);
    }
    protected void setButton(Boolean b1,Boolean b2,Boolean b3,Boolean b4,Boolean b5){
        bAdd.setDisable(!b1);
        bEdit.setDisable(!b2);
        bDel.setDisable(!b3);
        bUpdate.setDisable(!b4);
        bCancel.setDisable(!b5);
    }
    protected void setTeks(Boolean b){
        tfidAnggota.setEditable(false);
        tfnama.setEditable(b);
        tfalamat.setEditable(b);
        tfinstansi.setEditable(b);
    }
}