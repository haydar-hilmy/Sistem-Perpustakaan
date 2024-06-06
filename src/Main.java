import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Main extends Application {
    boolean flagEdit;
    TableView<Buku> tableView;
    TableView<Pustakawan> tableViewPustakawan;
    TableView<Anggota> tableViewAnggota;

    TableColumn<Buku, Integer> idBuku;
    TableColumn<Buku, String> judul;
    TableColumn<Buku, String> penerbit;
    TableColumn<Buku, String> penulis;
    TableColumn<Buku, Integer> tahun_terbit;

    TableColumn<Pustakawan, Integer> idPustakawan;
    TableColumn<Pustakawan, String> namaPustakawan;
    TableColumn<Pustakawan, String> alamatPustakawan;

    TableColumn<Anggota, Integer> idAnggota;
    TableColumn<Anggota, String> namaAnggota;
    TableColumn<Anggota, String> alamatAnggota;
    TableColumn<Anggota, String> instansiAnggota;

    TextField tfIdBuku;
    TextField tfJudul;
    TextField tfPenerbit;
    TextField tfPenulis;
    TextField tfTahunTerbit;

    TextField tfIdPustakawan;
    TextField tfNamaPustakawan;
    TextField tfAlamatPustakawan;

    TextField tfIdAnggota;
    TextField tfNamaAnggota;
    TextField tfAlamatAnggota;
    TextField tfInstansiAnggota;

    Button bUpdate;
    Button bCancel;
    Button bAdd;
    Button bEdit;
    Button bDel;
    BorderPane border;

    @Override
    public void start(Stage stage) throws Exception {
        border = new BorderPane();
        HBox hbox = addHBox();
        border.setTop(hbox);
        border.setLeft(addVBox());
        border.setCenter(addVBoxHome());
        stage.setTitle("Sistem Perpustakaan");
        Scene scene = new Scene(border);
        stage.setScene(scene);
        stage.setTitle("Perpustakaan Minim Digital");
        stage.show();
    }

    private HBox addHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10); // Gap between nodes
        hbox.setStyle("-fx-background-color: #336699;");
        Text tjudul = new Text("Sistem Perpustakaan");
        tjudul.setFont(Font.font("Verdana", 20));
        tjudul.setFill(Color.WHITESMOKE);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().add(tjudul);
        return hbox;
    }

    private VBox addVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10)); // Set all sides to 10
        vbox.setSpacing(8); // Gap between nodes
        Text title = new Text("Perpustakaan");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);
        Hyperlink options[] = new Hyperlink[] {
                new Hyperlink("Home"),
                new Hyperlink("Buku"),
                new Hyperlink("Pustakawan"),
                new Hyperlink("Anggota"),
                new Hyperlink("Peminjaman"),
                new Hyperlink("Koleksi Buku"),
                new Hyperlink("Exit") };
        for (int i = 0; i < 7; i++) {
            // Add offset to left side to indent from title
            VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
            vbox.getChildren().add(options[i]);
        }
        options[0].setOnAction(e -> {
            border.setCenter(addVBoxHome());
            System.out.println("Click Home");
        });
        options[1].setOnAction(e -> {
            border.setCenter(addVBoxBuku());
            System.out.println("Click Buku");
        });
        options[2].setOnAction(e -> {
            border.setCenter(addVBoxPustakawan());
            System.out.println("Click Pustakawan");
        });
        options[3].setOnAction(e -> {
            border.setCenter(addVBoxAnggota());
            System.out.println("Click Anggota");
        });
        options[6].setOnAction(e -> {
            System.exit(0);
        });
        return vbox;
    }

    private FlowPane AddFlowPaneHome() {
        FlowPane fp = new FlowPane();
        Text tjudul = new Text("Home");
        tjudul.setFont(Font.font("Arial", 18));
        fp.setAlignment(Pos.CENTER);
        FileInputStream input = null;
        try {
            input = new FileInputStream("D:/UDINUS/s4 Pemrograman Berbasis Orientasi/pert9/bg.jpg");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image image = new Image(input);
        ImageView imageview = new ImageView(image);
        fp.getChildren().add(tjudul);
        fp.getChildren().add(imageview);
        return fp;
    }

    private VBox addVBoxHome() {
        VBox vb = new VBox();
        vb.setFillWidth(true);
        Text tjudul = new Text("Home");
        tjudul.setFont(Font.font("Arial", 18));
        vb.setAlignment(Pos.CENTER);
        FileInputStream input = null;
        try {
            input = new FileInputStream("D:/UDINUS/s4 Pemrograman Berbasis Orientasi/pert9/bg.jpg");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image image = new Image(input);
        ImageView imageview = new ImageView(image);
        imageview.setFitHeight(500);
        imageview.setPreserveRatio(true);
        vb.getChildren().add(tjudul);
        vb.getChildren().add(imageview);
        return vb;
    }

    StackPane spTableBuku() {
        StackPane sp = new StackPane();
        tableView = new TableView<Buku>();
        idBuku = new TableColumn<>("idBuku");
        judul = new TableColumn<>("judul");
        penerbit = new TableColumn<>("penerbit");
        penulis = new TableColumn<>("penulis");
        tahun_terbit = new TableColumn<Buku, Integer>("tahun_terbit");
        tableView.getColumns().addAll(idBuku, judul, penerbit, penulis, tahun_terbit);
        idBuku.setCellValueFactory(new PropertyValueFactory<Buku, Integer>("idBuku"));
        judul.setCellValueFactory(new PropertyValueFactory<Buku, String>("judul"));
        penerbit.setCellValueFactory(new PropertyValueFactory<Buku, String>("penerbit"));
        penulis.setCellValueFactory(new PropertyValueFactory<Buku, String>("penulis"));
        tahun_terbit.setCellValueFactory(new PropertyValueFactory<Buku, Integer>("tahun_terbit"));
        // tableView.setItems(listBuku);
        // tableView.getItems().add(new Buku(1, "Buku A", "Erlangga","Joko",2024));
        // tableView.getItems().add(new Buku(2, "Buku B", "Andik
        // Offside","Dino",2024));// tableView.getItems().add(new Buku(3, "Buku C",
        // "ElekMedia","Jojon",2024));
        sp.getChildren().add(tableView);
        return sp;
    }

    StackPane spTablePustakawan() {
        StackPane sp = new StackPane();
        tableViewPustakawan = new TableView<Pustakawan>();
        idPustakawan = new TableColumn<>("idPustakawan");
        namaPustakawan = new TableColumn<>("namaPustakawan");
        alamatPustakawan = new TableColumn<>("alamatPustakawan");
        tableViewPustakawan.getColumns().addAll(idPustakawan, namaPustakawan, alamatPustakawan);
        idPustakawan.setCellValueFactory(new PropertyValueFactory<Pustakawan, Integer>("idPustakawan"));
        namaPustakawan.setCellValueFactory(new PropertyValueFactory<Pustakawan, String>("nama"));
        alamatPustakawan.setCellValueFactory(new PropertyValueFactory<Pustakawan, String>("alamat"));
        sp.getChildren().add(tableViewPustakawan);
        return sp;
    }

    StackPane spTableAnggota() {
        StackPane sp = new StackPane();
        tableViewAnggota = new TableView<Anggota>();
        idAnggota = new TableColumn<>("idAnggota");
        namaAnggota = new TableColumn<>("namaAnggota");
        alamatAnggota = new TableColumn<>("alamatAnggota");
        instansiAnggota = new TableColumn<>("instansiAnggota");
        tableViewAnggota.getColumns().addAll(idAnggota, namaAnggota, alamatAnggota, instansiAnggota);
        idAnggota.setCellValueFactory(new PropertyValueFactory<Anggota, Integer>("idAnggota"));
        namaAnggota.setCellValueFactory(new PropertyValueFactory<Anggota, String>("nama"));
        alamatAnggota.setCellValueFactory(new PropertyValueFactory<Anggota, String>("alamat"));
        instansiAnggota.setCellValueFactory(new PropertyValueFactory<Anggota, String>("instansi"));
        sp.getChildren().add(tableViewAnggota);
        return sp;
    }

    GridPane gpFormBuku() {
        flagEdit = false;
        GridPane gp = new GridPane();
        gp.setPrefHeight(500);
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(10, 10, 10, 10));
        Label lbJudulForm = new Label("Form Data Buku");
        Label lbIdBuku = new Label("Id Buku");
        Label lbJudul = new Label("Judul");
        Label lbPenerbit = new Label("Penerbit");
        Label lbPenulis = new Label("Penulis");
        Label lbTahunTerbit = new Label("Tahun Terbit");
        tfIdBuku = new TextField();
        tfJudul = new TextField();
        tfPenerbit = new TextField();
        tfPenulis = new TextField();
        tfTahunTerbit = new TextField();
        bUpdate = new Button("Update");
        bCancel = new Button("Cancel");
        bAdd = new Button("Add");
        bEdit = new Button("Edit");
        bDel = new Button("Del");
        bAdd.setPrefWidth(100);
        bEdit.setPrefWidth(100);
        bDel.setPrefWidth(100);
        bUpdate.setPrefWidth(100);
        bCancel.setPrefWidth(100);
        bUpdate.setOnAction(e -> {
            int idBuku, tahunTerbit;
            String judul, penulis, penerbit;
            idBuku = Integer.parseInt(tfIdBuku.getText());
            judul = tfJudul.getText();
            penulis = tfPenulis.getText();
            penerbit = tfPenerbit.getText();
            tahunTerbit = Integer.parseInt(tfTahunTerbit.getText());
            Buku b = new Buku(idBuku, judul, penerbit, penulis, tahunTerbit);
            if (flagEdit == false) {
                tableView.getItems().add(b);
            } else {
                int idx = tableView.getSelectionModel().getSelectedIndex();
                tableView.getItems().set(idx, b);
            }
            teksAktif(false, "buku");
            buttonAktif(false);
            clearTeks("buku");
            flagEdit = true;
        });
        bEdit.setOnAction(e -> {
            buttonAktif(true);
            teksAktif(true, "buku");
            flagEdit = true;
            int idx = tableView.getSelectionModel().getSelectedIndex();
            tfIdBuku.setText(String.valueOf(tableView.getItems().get(idx).getIdBuku()));
            tfJudul.setText(tableView.getItems().get(idx).getJudul());
            tfPenerbit.setText(tableView.getItems().get(idx).getPenulis());
            tfPenulis.setText(tableView.getItems().get(idx).getPenulis());
            tfTahunTerbit.setText(String.valueOf(tableView.getItems().get(idx).getTahun_terbit()));
        });
        bAdd.setOnAction(e -> {
            flagEdit = false;
            clearTeks("buku");
            teksAktif(true, "buku");
            buttonAktif(true);
        });
        bCancel.setOnAction(e -> {
            teksAktif(false, "buku");
            buttonAktif(false);
        });
        bDel.setOnAction(e -> {
            int idx = tableView.getSelectionModel().getSelectedIndex();
            tableView.getItems().remove(idx);
            clearTeks("buku");
        });
        TilePane tp1 = new TilePane();
        // TilePane tp2 = new TilePane();
        tp1.getChildren().addAll(bAdd, bEdit, bDel, bUpdate, bCancel);
        // tp2.getChildren().addAll(bUpdate,bCancel);
        gp.addRow(0, new Label(""), lbJudulForm);
        gp.addRow(1, lbIdBuku, tfIdBuku);
        gp.addRow(2, lbJudul, tfJudul);
        gp.addRow(3, lbPenerbit, tfPenerbit);
        gp.addRow(4, lbPenulis, tfPenulis);
        gp.addRow(5, lbTahunTerbit, tfTahunTerbit);
        gp.addRow(6, new Label(""), tp1);
        teksAktif(false, "buku");
        buttonAktif(false);
        return gp;
    }

    GridPane gpFormPustakawan() {
        flagEdit = false;
        GridPane gp = new GridPane();
        gp.setPrefHeight(500);
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(10, 10, 10, 10));
        Label lbJudulForm = new Label("Form Data Pustakawan");
        Label lbIdPustakawan = new Label("Id Pustakawan");
        Label lbNama = new Label("Nama");
        Label lbAlamat = new Label("Alamat");
        tfIdPustakawan = new TextField();
        tfNamaPustakawan = new TextField();
        tfAlamatPustakawan = new TextField();
        bUpdate = new Button("Update");
        bCancel = new Button("Cancel");
        bAdd = new Button("Add");
        bEdit = new Button("Edit");
        bDel = new Button("Del");
        bAdd.setPrefWidth(100);
        bEdit.setPrefWidth(100);
        bDel.setPrefWidth(100);
        bUpdate.setPrefWidth(100);
        bCancel.setPrefWidth(100);
        bUpdate.setOnAction(e -> {
            int idPustakawan;
            String nama, alamat;
            idPustakawan = Integer.parseInt(tfIdPustakawan.getText());
            nama = tfNamaPustakawan.getText();
            alamat = tfAlamatPustakawan.getText();
            Pustakawan p = new Pustakawan(idPustakawan, nama, alamat);
            if (flagEdit == false) {
                tableViewPustakawan.getItems().add(p);
            } else {
                int idx = tableViewPustakawan.getSelectionModel().getSelectedIndex();
                tableViewPustakawan.getItems().set(idx, p);
            }
            teksAktif(false, "pustakawan");
            buttonAktif(false);
            clearTeks("pustakawan");
            flagEdit = true;
        });
        bEdit.setOnAction(e -> {
            buttonAktif(true);
            teksAktif(true, "pustakawan");
            flagEdit = true;
            int idx = tableViewPustakawan.getSelectionModel().getSelectedIndex();
            tfIdPustakawan.setText(String.valueOf(tableViewPustakawan.getItems().get(idx).getIdPustakawan()));
            tfNamaPustakawan.setText(tableViewPustakawan.getItems().get(idx).getNama());
            tfAlamatPustakawan.setText(tableViewPustakawan.getItems().get(idx).getAlamat());
        });
        bAdd.setOnAction(e -> {
            flagEdit = false;
            clearTeks("pustakawan");
            teksAktif(true, "pustakawan");
            buttonAktif(true);
        });
        bCancel.setOnAction(e -> {
            teksAktif(false, "pustakawan");
            buttonAktif(false);
        });
        bDel.setOnAction(e -> {
            int idx = tableViewPustakawan.getSelectionModel().getSelectedIndex();
            if(idx != -1) {
                tableViewPustakawan.getItems().remove(idx);
                clearTeks("pustakawan");
            } else {
                System.out.println("Silakan pilih satu data pustakawan!");
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Peringatan");
                alert.setHeaderText(null);
                alert.setContentText("Silakan pilih satu data pustakawan!");

                alert.showAndWait();
            }
        });
        TilePane tp1 = new TilePane();
        tp1.getChildren().addAll(bAdd, bEdit, bDel, bUpdate, bCancel);
        gp.addRow(0, new Label(""), lbJudulForm);
        gp.addRow(1, lbIdPustakawan, tfIdPustakawan);
        gp.addRow(2, lbNama, tfNamaPustakawan);
        gp.addRow(3, lbAlamat, tfAlamatPustakawan);
        gp.addRow(6, new Label(""), tp1);
        teksAktif(false, "pustakawan");
        buttonAktif(false);
        return gp;
    }

    GridPane gpFormAnggota() {
        flagEdit = false;
        GridPane gp = new GridPane();
        gp.setPrefHeight(500);
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(10, 10, 10, 10));
        Label lbJudulForm = new Label("Form Data Anggota");
        Label lbIdAnggota = new Label("Id Anggota");
        Label lbNama = new Label("Nama");
        Label lbAlamat = new Label("Alamat");
        Label lbInstansi = new Label("Instansi");
        tfIdAnggota = new TextField();
        tfNamaAnggota = new TextField();
        tfAlamatAnggota = new TextField();
        tfInstansiAnggota = new TextField();
        bUpdate = new Button("Update");
        bCancel = new Button("Cancel");
        bAdd = new Button("Add");
        bEdit = new Button("Edit");
        bDel = new Button("Del");
        bAdd.setPrefWidth(100);
        bEdit.setPrefWidth(100);
        bDel.setPrefWidth(100);
        bUpdate.setPrefWidth(100);
        bCancel.setPrefWidth(100);
        bUpdate.setOnAction(e -> {
            int idAnggota;
            String nama, alamat, instansi;
            idAnggota = Integer.parseInt(tfIdAnggota.getText());
            nama = tfNamaAnggota.getText();
            alamat = tfAlamatAnggota.getText();
            instansi = tfInstansiAnggota.getText();
            Anggota ag = new Anggota(idAnggota, nama, alamat, instansi);
            if (flagEdit == false) {
                tableViewAnggota.getItems().add(ag);
            } else {
                int idx = tableViewAnggota.getSelectionModel().getSelectedIndex();
                tableViewAnggota.getItems().set(idx, ag);
            }
            teksAktif(false, "anggota");
            buttonAktif(false);
            clearTeks("anggota");
            flagEdit = true;
        });
        bEdit.setOnAction(e -> {
            buttonAktif(true);
            teksAktif(true, "anggota");
            flagEdit = true;
            int idx = tableViewAnggota.getSelectionModel().getSelectedIndex();
            tfIdAnggota.setText(String.valueOf(tableViewAnggota.getItems().get(idx).getIdAnggota()));
            tfNamaAnggota.setText(tableViewAnggota.getItems().get(idx).getNama());
            tfAlamatAnggota.setText(tableViewAnggota.getItems().get(idx).getAlamat());
            tfInstansiAnggota.setText(tableViewAnggota.getItems().get(idx).getInstansi());
        });
        bAdd.setOnAction(e -> {
            flagEdit = false;
            clearTeks("anggota");
            teksAktif(true, "anggota");
            buttonAktif(true);
        });
        bCancel.setOnAction(e -> {
            teksAktif(false, "anggota");
            buttonAktif(false);
        });
        bDel.setOnAction(e -> {
            int idx = tableViewAnggota.getSelectionModel().getSelectedIndex();
            tableViewAnggota.getItems().remove(idx);
            clearTeks("anggota");
        });
        TilePane tp1 = new TilePane();
        tp1.getChildren().addAll(bAdd, bEdit, bDel, bUpdate, bCancel);
        gp.addRow(0, new Label(""), lbJudulForm);
        gp.addRow(1, lbIdAnggota, tfIdAnggota);
        gp.addRow(2, lbNama, tfNamaAnggota);
        gp.addRow(3, lbAlamat, tfAlamatAnggota);
        gp.addRow(3, lbInstansi, tfInstansiAnggota);
        gp.addRow(6, new Label(""), tp1);
        teksAktif(false, "anggota");
        buttonAktif(false);
        return gp;
    }

    private VBox addVBoxBuku() {
        VBox vb = new VBox();
        Text tjudul = new Text("Form Data Buku");
        tjudul.setFont(Font.font("Arial", 18));
        vb.getChildren().add(tjudul);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().add(spTableBuku());
        vb.getChildren().add(gpFormBuku());
        return vb;
    }

    private VBox addVBoxPustakawan() {
        VBox vb = new VBox();
        Text tjudul = new Text("Form Data Pustakawan");
        tjudul.setFont(Font.font("Arial", 18));
        vb.getChildren().add(tjudul);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().add(spTablePustakawan());
        vb.getChildren().add(gpFormPustakawan());
        return vb;
    }

    private VBox addVBoxAnggota() {
        VBox vb = new VBox();
        Text tjudul = new Text("Form Data Anggota");
        tjudul.setFont(Font.font("Arial", 18));
        vb.getChildren().add(tjudul);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().add(spTableAnggota());
        vb.getChildren().add(gpFormAnggota());
        return vb;
    }

    public void buttonAktif(boolean nonAktif) {
        bAdd.setDisable(nonAktif);
        bEdit.setDisable(nonAktif);
        bDel.setDisable(nonAktif);
        bUpdate.setDisable(!nonAktif);
        bCancel.setDisable(!nonAktif);
    }

    public void teksAktif(boolean aktif, String div) {
        if(div == "buku"){
            tfIdBuku.setEditable(aktif);
            tfJudul.setEditable(aktif);
            tfPenerbit.setEditable(aktif);
            tfPenulis.setEditable(aktif);
            tfTahunTerbit.setEditable(aktif);
        } else if(div == "pustakawan"){
            tfIdPustakawan.setEditable(aktif);
            tfNamaPustakawan.setEditable(aktif);
            tfAlamatPustakawan.setEditable(aktif);
        } else if(div == "anggota"){
            tfIdAnggota.setEditable(aktif);
            tfNamaAnggota.setEditable(aktif);
            tfAlamatAnggota.setEditable(aktif);
            tfInstansiAnggota.setEditable(aktif);
        }
    }

    public void clearTeks(String div) {
        if(div == "buku"){
            tfIdBuku.setText("");
            tfJudul.setText("");
            tfPenerbit.setText("");
            tfPenulis.setText("");
            tfTahunTerbit.setText("");
        } else if(div == "pustakawan"){
            tfIdPustakawan.setText("");
            tfNamaPustakawan.setText("");
            tfAlamatPustakawan.setText("");
        } else if(div == "anggota"){
            tfIdAnggota.setText("");
            tfNamaAnggota.setText("");
            tfAlamatAnggota.setText("");
            tfInstansiAnggota.setText("");
        }
    }
}