package com.fxml.pbo.pbofxml;
import com.fxml.pbo.pbofxml.Anggota;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;
public class DBUtil {
    public static ObservableList<Anggota> getDataAnggota(){
        ObservableList<Anggota> listAnggota = FXCollections.observableArrayList();
        try {
            Connection c = DBConnection.getConn();
            String sql = "SELECT * FROM anggota";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Anggota ag = new Anggota(rs.getInt("id_anggota"),rs.getString("nama"),rs.getString("alamat"),rs.getString("instansi"));
                listAnggota.add(ag);
            }
            return listAnggota;
        } catch (SQLException ex) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null,ex);
            return null;
        }
    }
}