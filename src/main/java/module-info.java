module machinelearning.guimachinelearning {
    requires javafx.controls;
    requires javafx.fxml;
    requires weka.stable;

    opens machinelearning.guimachinelearning to javafx.fxml;
    exports machinelearning.guimachinelearning;
}