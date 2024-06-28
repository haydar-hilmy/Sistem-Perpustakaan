package com.minim.library.pbo_perpus.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.minim.library.pbo_perpus.models.Buku;
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
public class bukuController implements Initializable {
    Stage stage;
    ObservableList<Buku> listBuku = FXCollections.observableArrayList() ;//ObservableList<Produk> listBrg ;
    boolean flagAdd = true;
    @FXML
    private TableColumn<Buku, Integer> cidBuku;
    @FXML
    private TableColumn<Buku, String> cjudul;
    @FXML
    private TableColumn<Buku, String> cpenerbit;
    @FXML
    private TableColumn<Buku, String> cpenulis;
    @FXML
    private TableColumn<Buku, Integer> ctahun_terbit;
    @FXML
    private TableView<Buku> tbBuku;
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
    private TextField tfidBuku;
    @FXML
    private TextField tfjudul;
    @FXML
    private TextField tfpenerbit;
    @FXML
    private TextField tfpenulis;
    @FXML
    private TextField tftahun_terbit;
    @FXML
    private TextField tfsearch;
    @FXML
    void add(ActionEvent event) {
        setButton(false,false,false,true,true);
        clearTeks();
        setTeks(true);
        tfidBuku.requestFocus();
    }
    @FXML
    void cancel(ActionEvent event) {
        setButton(true,true,true,false,false);
        clearTeks();
    }
    @FXML
    void del(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "hapusdata ?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Connection conn = DBConnection.getConn();
            String sql="DELETE FROM buku WHERE id_buku = ?";
            PreparedStatement st = null;
            try {
                st = conn.prepareStatement(sql);
                st.setString(1,tfidBuku.getText());
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
        tfidBuku.setEditable(false);
        tfjudul.requestFocus();
    }
    @FXML
    void update(ActionEvent event) {
        Connection conn = DBConnection.getConn();
        if (flagAdd){
            String sql="INSERT INTO buku(judul, penerbit, penulis, tahun_terbit) values (?,?,?,?)";
            try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1,tfjudul.getText());
                st.setString(2,tfpenerbit.getText());
                st.setString(3,tfpenulis.getText());
                st.setInt(4,Integer.valueOf(tftahun_terbit.getText()));
                st.executeUpdate();
                loadData();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
//do something..
        } else {
            String sql="UPDATE buku SET judul = ?, penerbit = ?, penulis = ?, tahun_terbit = ? WHERE id_buku = ?";
            try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1,tfjudul.getText() );
                st.setString(2,tfpenerbit.getText());
                st.setString(3,tfpenulis.getText());
                st.setInt(4,Integer.valueOf(tftahun_terbit.getText()));
                st.setInt(5,Integer.valueOf(tfidBuku.getText()));
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
        Buku p = tbBuku.getSelectionModel().getSelectedItem();  
        tfidBuku.setText(String.valueOf(p.getIdBuku()));
        tfjudul.setText(p.getJudul());
        tfpenerbit.setText(p.getPenerbit());
        tfpenulis.setText(p.getPenulis());
        tftahun_terbit.setText(String.valueOf(p.getTahun_terbit()));
//tfkode.setText(b.getKode());
//MHS m = tbmhs.getSelectionModel().getSelectedItem();
    }
    void initTabel(){
        cidBuku.setCellValueFactory(new PropertyValueFactory<Buku,Integer>("idBuku"));
        cjudul.setCellValueFactory(new PropertyValueFactory<Buku,String>("judul"));
        cpenerbit.setCellValueFactory(new PropertyValueFactory<Buku,String>("penerbit"));
        cpenulis.setCellValueFactory(new PropertyValueFactory<Buku,String>("penulis"));
        ctahun_terbit.setCellValueFactory(new PropertyValueFactory<Buku,Integer>("tahun_terbit"));
    }
    void loadData(){
        listBuku= DBUtil.getDataBuku();
        tbBuku.setItems(listBuku);
    }
    void setFilter(){
        FilteredList<Buku> filterData= new FilteredList<>(listBuku,b->true);tfsearch.textProperty().addListener((observable,oldValue,newValue)->{
            filterData.setPredicate(Buku->{
                if (newValue.isEmpty() || newValue == null){
                    return true;
                }
                String searchKeyword=newValue.toLowerCase();
                if (Buku.getJudul().toLowerCase().indexOf(searchKeyword)>-1){
                    return true;
                }else if (String.valueOf(Buku.getIdBuku()).toLowerCase().indexOf(searchKeyword) >-1){
                    return true;
                    }
                else
                    return false;
            });
        });
        SortedList<Buku> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(tbBuku.comparatorProperty());tbBuku.setItems(sortedData);
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
        tfidBuku.setText(null);
        tfjudul.setText(null);
        tfpenerbit.setText(null);
        tfpenulis.setText(null);
        tftahun_terbit.setText(null);
    }
    protected void setButton(Boolean b1,Boolean b2,Boolean b3,Boolean b4,Boolean b5){
        bAdd.setDisable(!b1);
        bEdit.setDisable(!b2);
        bDel.setDisable(!b3);
        bUpdate.setDisable(!b4);
        bCancel.setDisable(!b5);
    }
    protected void setTeks(Boolean b){
        tfidBuku.setEditable(false);
        tfjudul.setEditable(b);
        tfpenerbit.setEditable(b);
        tfpenulis.setEditable(b);
        tftahun_terbit.setEditable(b);
    }
}