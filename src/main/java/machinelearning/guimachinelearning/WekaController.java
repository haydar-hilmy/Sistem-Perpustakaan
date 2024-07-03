package machinelearning.guimachinelearning;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import weka.classifiers.Evaluation;
import weka.core.*;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;
import weka.classifiers.trees.J48;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.core.converters.ConverterUtils.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.Remove;
import weka.gui.explorer.ExplorerDefaults;
//import weka.gui.explorer.Explorer;
import weka.gui.explorer.*;
import weka.gui.treevisualizer.NodePlace;
import weka.gui.treevisualizer.TreeBuild;
import weka.gui.treevisualizer.TreeDisplayListener;
import weka.gui.treevisualizer.TreeVisualizer;
public class WekaController {
    Instances data1;
    J48 tree;
    @FXML
    private Button btModelJ48;
    @FXML
    private Button btnLoad;
    @FXML
    private TableColumn<IrisData, String> speciesCol;
    @FXML
    private TableColumn<IrisData, Double> petalLengthCol;
    @FXML
    private TableColumn<IrisData, Double> petalWidthCol;
    @FXML
    private TableColumn<IrisData, Double> sepalLengthCol;
    @FXML
    private TableColumn<IrisData, Double> sepalWidthCol;
    @FXML
    private TableView<IrisData> tvIris;
    @FXML
    private TableColumn<IrisData, String> speciesTrCol;
    @FXML
    private TableColumn<IrisData, String> prediksiTrCol;
    @FXML
    private TableColumn<IrisData, Double> petalLengthTrCol;
    @FXML
    private TableColumn<IrisData, Double> petalWidthTrCol;
    @FXML
    private TableColumn<IrisData, Double> sepalLengthTrCol;
    @FXML
    private TableColumn<IrisData, Double> sepalWidthTrCol;
    @FXML
    private TableView<IrisData> tvIrisTrain;
    @FXML
    private TextArea taHasil;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnProcess;
    @FXML
    private TextField tfpetalL;
    @FXML
    private TextField tfpetalW;
    @FXML
    private TextField tfsepalL;
    @FXML
    private TextField tfsepalW;
    @FXML
    private TextField tfspecies;
    @FXML
    private TextArea taHasil2;
    @FXML
    void loadIris(ActionEvent event) {
        loadDataIris();
    }
    @FXML
    void prosesJ48(ActionEvent event) {
        J48B();
    }
    @FXML
    void clear(ActionEvent event) {
        tfspecies.setText("");
        tfsepalL.setText("");
        tfsepalW.setText("");
        tfpetalL.setText("");
        tfpetalW.setText("");
        tfsepalL.requestFocus();
    }
    @FXML
    void prosesKlasifikasi(ActionEvent event) {
        double[] values = new double[4];
        values[0]= Double.parseDouble(tfsepalL.getText());
        values[1]= Double.parseDouble(tfsepalW.getText());
        values[2]= Double.parseDouble(tfpetalL.getText());
        values[3]= Double.parseDouble(tfpetalW.getText());
        DenseInstance instance = new DenseInstance(1.0, values);
        instance.setDataset(data1);
// Make prediction
        double prediction;
        try {
            prediction = tree.classifyInstance(instance);
            int intprediction = (int) prediction;
            String hasil="";
            if (prediction==0)
                hasil="Iris-setosa";
            else if (prediction==1)
                hasil="Iris-versicolor";
            else if (prediction==2)
                hasil="Iris-Virginica";
            else
                hasil="none";
            tfspecies.setText(hasil+"\n kode:"+prediction);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    void loadDataIris(){
        DataSource source = null;
        try {
            source = new DataSource("D:/Iris.arff");
            Instances data = source.getDataSet();
            data1 = data;
            ObservableList<IrisData> datairis = convertInstancesToIrisData(data);
            sepalLengthCol.setCellValueFactory(new PropertyValueFactory<>("sepalLength"));
            sepalWidthCol.setCellValueFactory(new PropertyValueFactory<>("sepalWidth"));
            petalLengthCol.setCellValueFactory(new PropertyValueFactory<>("petalLength"));
            petalWidthCol.setCellValueFactory(new PropertyValueFactory<>("petalWidth"));
            speciesCol.setCellValueFactory(new PropertyValueFactory<>("species"));
// Mengatur data ke TableView
            tvIris.setItems(datairis);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private ObservableList<IrisData> convertInstancesToIrisData(Instances instances) {
        ObservableList<IrisData> irisData = FXCollections.observableArrayList();
        for (Instance instance : instances) {
            double sepalLength = instance.value(0);
            double sepalWidth = instance.value(1);
            double petalLength = instance.value(2);
            double petalWidth = instance.value(3);
            String species = instance.stringValue(4);
            irisData.add(new IrisData(sepalLength, sepalWidth, petalLength,petalWidth, species));
        }
        return irisData;
    }
    void J48B(){
        DataSource source = null;
        try {
            data1.setClassIndex(data1.numAttributes()-1);
            data1.randomize(new java.util.Random(42));
            int trainSize = (int) Math.round(data1.numInstances() * 0.8);int testSize = data1.numInstances() - trainSize;
            Instances trainData = new Instances(data1, 0, trainSize);
            Instances testData = new Instances(data1, trainSize, testSize);// Membangun model klasifikasi dengan algoritma J48 (C4.5)
            tree = new J48();
            tree.buildClassifier(trainData);
// Membuat visualisasi tree
            String hasil="";
            taHasil2.setText(tree.toString());
// Mengevaluasi model pada data uji
            Evaluation eval = new Evaluation(trainData);
            eval.evaluateModel(tree, testData);
            hasil=hasil+eval.toSummaryString();
            hasil=hasil+"Akurasi: "+eval.pctCorrect();
            taHasil.setText(hasil);
//data1=dataset;
//membentuk datatest
            ObservableList<IrisData> data =
                    FXCollections.observableArrayList();
//menampilkan datatesting ke tableview
            for (int i = 0; i < testData.numInstances(); i++) {
                double prediction =
                        tree.classifyInstance(testData.instance(i));
                String actual =
                        testData.instance(i).toString(testData.classIndex());
                String predicted = testData.classAttribute().value((int)prediction);
                double sepalLength =
                        Double.parseDouble(testData.instance(i).toString(testData.attribute(0)));double sepalWidth =
                        Double.parseDouble(testData.instance(i).toString(testData.attribute(1)));double petalLength =
                        Double.parseDouble(testData.instance(i).toString(testData.attribute(2)));double petalWidth =
                        Double.parseDouble(testData.instance(i).toString(testData.attribute(3)));IrisData d = new
                        IrisData(sepalLength,sepalWidth,petalLength,petalWidth,actual);
                d.setPrediksi(predicted);
                data.add(d);
            }
            sepalLengthTrCol.setCellValueFactory(new
                    PropertyValueFactory<>("sepalLength"));
            sepalWidthTrCol.setCellValueFactory(new
                    PropertyValueFactory<>("sepalWidth"));
            petalLengthTrCol.setCellValueFactory(new
                    PropertyValueFactory<>("petalLength"));
            petalWidthTrCol.setCellValueFactory(new
                    PropertyValueFactory<>("petalWidth"));
            speciesTrCol.setCellValueFactory(new
                    PropertyValueFactory<>("species"));
            prediksiTrCol.setCellValueFactory(new
                    PropertyValueFactory<>("prediksi"));
            tvIrisTrain.setItems(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}