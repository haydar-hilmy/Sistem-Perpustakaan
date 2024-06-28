module machinelearning.guimachinelearning {
    requires javafx.controls;
    requires javafx.fxml;


    opens machinelearning.guimachinelearning to javafx.fxml;
    exports machinelearning.guimachinelearning;
}