package machinelearning.guimachinelearning;

public class IrisData {
    double sepalLength;
    double sepalWidth;
    double petalLength;
    double petalWidth;
    String species;
    String prediksi;
    public IrisData(double sepalLength, double sepalWidth, double petalLength, double petalWidth, String species) {
        this.sepalLength = sepalLength;
        this.sepalWidth = sepalWidth;
        this.petalLength = petalLength;
        this.petalWidth = petalWidth;
        this.species = species;
        this.prediksi = "";
    }
    public double getSepalLength() {
        return sepalLength;
    }
    public void setSepalLength(double sepalLength) {
        this.sepalLength = sepalLength;
    }
    public double getSepalWidth() {
        return sepalWidth;
    }
    public void setSepalWidth(double sepalWidth) {
        this.sepalWidth = sepalWidth;
    }
    public double getPetalLength() {
        return petalLength;
    }
    public void setPetalLength(double petalLength) {
        this.petalLength = petalLength;
    }
    public double getPetalWidth() {
        return petalWidth;
    }
    public void setPetalWidth(double petalWidth) {
        this.petalWidth = petalWidth;
    }
    public String getSpecies() {
        return species;
    }
    public void setSpecies(String species) {
        this.species = species;
    }
    public String getPrediksi() {
        return prediksi;
    }
    public void setPrediksi(String prediksi) {
        this.prediksi = prediksi;
    }
}