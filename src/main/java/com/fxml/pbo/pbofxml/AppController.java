package com.fxml.pbo.pbofxml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

public class AppController implements Initializable {
    ObservableList<Anggota> listAnggota = FXCollections.observableArrayList();
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


//    TAB ANGGOTA
    @FXML
    private TabPane tabPaneAnggota;
    @FXML
    private Tab tabAnggotaForm;
    @FXML
    private Tab tabAnggotaFormEdit;
    @FXML
    private Tab tabAnggotaTableView;
    @FXML
    private Button btnEditAnggota;
    @FXML
    private Button btnSubmitAnggota;
    @FXML
    private Button btnSubmitAnggotaEdit;
    @FXML
    private Button btnCancelAnggota;
    @FXML
    private Button btnCancelAnggotaEdit;

    // TEXTFIELD ANGGOTA
    @FXML
    private TextField tfSearchAnggota;
    @FXML
    private TextField tfIdAnggotaEdit, tfNamaAnggotaEdit, tfAlamatAnggotaEdit, tfInstansiAnggotaEdit;
    @FXML
    private TextField tfNamaAnggotaAdd, tfAlamatAnggotaAdd, tfInstansiAnggotaAdd;

    @FXML
    void chooseAnggota(MouseEvent event) {
            Anggota ag = tvAnggota.getSelectionModel().getSelectedItem();
        if(ag != null){
            tfIdAnggotaEdit.setText(String.valueOf(ag.getIdAnggota()));
            tfNamaAnggotaEdit.setText(ag.getNama());
            tfAlamatAnggotaEdit.setText(ag.getAlamat());
            tfInstansiAnggotaEdit.setText(ag.getInstansi());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTfEditAnggota(false, true);

        btnEditAnggota.setOnAction(event -> {
            tabPaneAnggota.getSelectionModel().select(tabAnggotaFormEdit);
            setTfEditAnggota(true, false);
            tfNamaAnggotaEdit.requestFocus();
            System.out.println("Moved -> Edit Data");
        });

        btnSubmitAnggotaEdit.setOnAction(event -> {
            Connection conn = DBConnection.getConn();
            String sql = "UPDATE anggota SET nama = ?, alamat = ?, instansi = ? WHERE id_anggota = ?";
            try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, tfNamaAnggotaEdit.getText());
                st.setString(2, tfAlamatAnggotaEdit.getText());
                st.setString(3, tfInstansiAnggotaEdit.getText());
                st.setInt(4, Integer.valueOf(tfIdAnggotaEdit.getText()));
                st.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            loadDataAnggota();
            setTfEditAnggota(false, true);
            System.out.println("Edited Data");
            tabPaneAnggota.getSelectionModel().select(tabAnggotaTableView);
            System.out.println("Moved -> Table View Anggota");
        });

        btnCancelAnggotaEdit.setOnAction(event -> {
            setTfEditAnggota(false, true);
            System.out.println("Cancel Edit");
        });

        btnSubmitAnggota.setOnAction(event -> {
            Connection conn = DBConnection.getConn();
            String sql = "INSERT INTO anggota (nama, alamat, instansi) VALUES (?, ?, ?)";
            try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, tfNamaAnggotaAdd.getText());
                st.setString(2, tfAlamatAnggotaAdd.getText());
                st.setString(3, tfInstansiAnggotaAdd.getText());
                st.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            setTfAddAnggota(true, true);
            loadDataAnggota();
            System.out.println("Added Anggota");
            tabPaneAnggota.getSelectionModel().select(tabAnggotaTableView);
            System.out.println("Moved -> Table View Anggota");
        });

        btnCancelAnggota.setOnAction(event -> {
            // bersihkan textfield
            System.out.println("Reset Form");
            setTfAddAnggota(true, true);
        });

        initTableAnggota();
        loadDataAnggota();
        setFilterAnggota();
    }

    void setFilterAnggota(){
        FilteredList<Anggota> filterDataAnggota = new FilteredList<>(listAnggota, ag -> true);
        tfSearchAnggota.textProperty().addListener((observable, oldValue, newValue)->{
            filterDataAnggota.setPredicate(Anggota->{
                if (newValue.isEmpty() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Anggota.getNama().toLowerCase().indexOf(searchKeyword)>-1){
                    return true;
                } else if (String.valueOf(Anggota.getIdAnggota()).toLowerCase().indexOf(searchKeyword) >-1){
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<Anggota> sortedDataAnggota = new SortedList<>(filterDataAnggota);
        sortedDataAnggota.comparatorProperty().bind(tvAnggota.comparatorProperty());
        tvAnggota.setItems(sortedDataAnggota);
    }

    void initTableAnggota(){
        tcIdAnggota.setCellValueFactory(new PropertyValueFactory<Anggota, Integer>("idAnggota"));
        tcNamaAnggota.setCellValueFactory(new PropertyValueFactory<Anggota, String>("nama"));
        tcAlamatAnggota.setCellValueFactory(new PropertyValueFactory<Anggota, String>("alamat"));
        tcInstansiAnggota.setCellValueFactory(new PropertyValueFactory<Anggota, String>("instansi"));
    }

    void setTfEditAnggota(Boolean isActive, Boolean clear){
        if(clear == true){
            tfIdAnggotaEdit.setText(null);
            tfNamaAnggotaEdit.setText(null);
            tfAlamatAnggotaEdit.setText(null);
            tfInstansiAnggotaEdit.setText(null);
        }
        tfIdAnggotaEdit.setEditable(false);
        tfNamaAnggotaEdit.setEditable(isActive);
        tfAlamatAnggotaEdit.setEditable(isActive);
        tfInstansiAnggotaEdit.setEditable(isActive);
    }

    void setTfAddAnggota(Boolean isActive, Boolean clear){
        if(clear == true){
            tfNamaAnggotaAdd.setText(null);
            tfAlamatAnggotaAdd.setText(null);
            tfInstansiAnggotaAdd.setText(null);
        }
        tfNamaAnggotaAdd.setEditable(isActive);
        tfAlamatAnggotaAdd.setEditable(isActive);
        tfInstansiAnggotaAdd.setEditable(isActive);
    }

    @FXML
    void deleteAnggota(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete Data?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Connection conn = DBConnection.getConn();
            String sql = "DELETE FROM anggota WHERE id_anggota = ?";
            PreparedStatement st = null;
            try {
                st = conn.prepareStatement(sql);
                st.setString(1, tfIdAnggotaEdit.getText());
                st.executeUpdate();
                loadDataAnggota();
                setTfEditAnggota(false, true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void loadDataAnggota(){
        listAnggota = DBUtil.getDataAnggota();
        tvAnggota.setItems(listAnggota);
    }

}