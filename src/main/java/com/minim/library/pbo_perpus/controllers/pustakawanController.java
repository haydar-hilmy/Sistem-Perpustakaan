package com.minim.library.pbo_perpus.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.minim.library.pbo_perpus.models.Pustakawan;
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
public class pustakawanController implements Initializable {
    Stage stage;
    ObservableList<Pustakawan> listPustakawan = FXCollections.observableArrayList();
    boolean flagAdd = true;
    @FXML
    private TableColumn<Pustakawan, Integer> cidPustakawan;
    @FXML
    private TableColumn<Pustakawan, String> cnama;
    @FXML
    private TableColumn<Pustakawan, String> calamat;
    @FXML
    private TableView<Pustakawan> tbPustakawan;
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
    private TextField tfidPustakawan;
    @FXML
    private TextField tfnama;
    @FXML
    private TextField tfalamat;
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
            String sql = "DELETE FROM pustakawan WHERE id_pustakawan = ?";
            PreparedStatement st = null;
            try {
                st = conn.prepareStatement(sql);
                st.setString(1,tfidPustakawan.getText());
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
        tfidPustakawan.setEditable(false);
        tfnama.requestFocus();
    }
    @FXML
    void update(ActionEvent event) {
        Connection conn = DBConnection.getConn();
        if (flagAdd){
            String sql="INSERT INTO pustakawan(nama, alamat) values (?, ?)";
            try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, tfnama.getText());
                st.setString(2, tfalamat.getText());
                st.executeUpdate();
                loadData();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
//do something..
        } else {
            String sql = "UPDATE pustakawan SET nama = ?, alamat = ? WHERE id_pustakawan = ?";
            try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1,tfnama.getText() );
                st.setString(2,tfalamat.getText());
                st.setInt(3,Integer.valueOf(tfidPustakawan.getText()));
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
        Pustakawan p = tbPustakawan.getSelectionModel().getSelectedItem();
        tfidPustakawan.setText(String.valueOf(p.getIdPustakawan()));
        tfnama.setText(p.getNama());
        tfalamat.setText(p.getAlamat());
    }
    void initTabel(){
        cidPustakawan.setCellValueFactory(new PropertyValueFactory<Pustakawan, Integer>("idPustakawan"));
        cnama.setCellValueFactory(new PropertyValueFactory<Pustakawan, String>("nama"));
        calamat.setCellValueFactory(new PropertyValueFactory<Pustakawan, String>("alamat"));
    }
    void loadData(){
        listPustakawan = DBUtil.getDataPustakawan();
        tbPustakawan.setItems(listPustakawan);
    }
    void setFilter(){
        System.out.println("Anggota Filtered");
        FilteredList<Pustakawan> filterData= new FilteredList<>(listPustakawan,b->true);
        tfsearch.textProperty().addListener((observable,oldValue,newValue)->{
            filterData.setPredicate(Pustakawan->{
                if (newValue.isEmpty() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Pustakawan.getNama().toLowerCase().indexOf(searchKeyword)>-1){
                    return true;
                }else if (String.valueOf(Pustakawan.getIdPustakawan()).toLowerCase().indexOf(searchKeyword) >-1){
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<Pustakawan> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(tbPustakawan.comparatorProperty());
        tbPustakawan.setItems(sortedData);
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
        tfidPustakawan.setText(null);
        tfnama.setText(null);
        tfalamat.setText(null);
    }
    protected void setButton(Boolean b1,Boolean b2,Boolean b3,Boolean b4,Boolean b5){
        bAdd.setDisable(!b1);
        bEdit.setDisable(!b2);
        bDel.setDisable(!b3);
        bUpdate.setDisable(!b4);
        bCancel.setDisable(!b5);
    }
    protected void setTeks(Boolean b){
        tfidPustakawan.setEditable(false);
        tfnama.setEditable(b);
        tfalamat.setEditable(b);
    }
}