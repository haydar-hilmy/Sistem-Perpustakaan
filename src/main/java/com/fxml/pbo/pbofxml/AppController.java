package com.fxml.pbo.pbofxml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AppController implements Initializable {
    ObservableList<Anggota> listAnggota = FXCollections.observableArrayList() ;
    @FXML
    private TableView<Anggota> tvAnggota;
    @FXML
    private TableColumn<Anggota, Integer> tcIdAnggota;
    @FXML
    private TableColumn<Anggota, String> tcNamaAnggota;
    @FXML
    private TableColumn<Anggota, String> tcAlamatAnggota;
    @FXML
    private TableColumn<Anggota, String> tcInstansiAnggota;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTableAnggota();
        loadDataAnggota();
//        setFilter();
//        setButton(true,true,true,false,false);
//        setTeks(false);
//        setFilter();
    }
    void initTableAnggota(){
        tcIdAnggota.setCellValueFactory(new PropertyValueFactory<Anggota,Integer>("idAnggota"));
        tcNamaAnggota.setCellValueFactory(new PropertyValueFactory<Anggota,String>("nama"));
        tcAlamatAnggota.setCellValueFactory(new PropertyValueFactory<Anggota,String>("penerbit"));
        tcInstansiAnggota.setCellValueFactory(new PropertyValueFactory<Anggota,String>("instansi"));
    }

    void loadDataAnggota(){
        listAnggota = DBUtil.getDataAnggota();
        tvAnggota.setItems(listAnggota);
    }

}