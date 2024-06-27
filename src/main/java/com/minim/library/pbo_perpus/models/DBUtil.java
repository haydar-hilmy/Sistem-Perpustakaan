package com.minim.library.pbo_perpus.models;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.util.logging.Logger;
import java.util.logging.Level;

public class DBUtil {
    public static void changeScene(ActionEvent event,String fxmlFile,String title,String userName) {
        Parent root = null;
        if (userName != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtil.class.getResource(fxmlFile));
                root = loader.load();
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBUtil.class.getResource(fxmlFile));}
            catch (IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root,1100,800));
        stage.show();
    }
    public static ObservableList<Buku> getDataBuku(){
        ObservableList<Buku> listBuku = FXCollections.observableArrayList();
        try {
            Connection c = DBConnection.getConn();
            String sql = "SELECT * FROM buku";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Buku m = new Buku(rs.getInt("id_buku"),rs.getString("judul"),rs.getString("penerbit"),rs.getString("penulis"),rs.getInt("tahun_terbit"));
                listBuku.add(m);
            }
            return listBuku;
        } catch (SQLException ex) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null,ex);
            return null;
        }
    }
}