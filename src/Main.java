public class Main {
    public static void main(String[] args) {
        DataPackage data = new DataPackage(10, "nz", 1, 2, 3, 4, 5);
        SaveToFile file = SaveToFile.getInstance();
        file.saveDataPackageToFile(data);
    }
}