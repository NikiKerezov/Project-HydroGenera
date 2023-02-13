package DependancyContainer.Models;

public class LocalDataSettings {
    private int dataLifespan;
    private String fileFormat;

    public String getPath() {return path;}

    public void setPath(String path) {this.path = path;}

    private String path;

    public int getDataLifespan() {
        return dataLifespan;
    }

    public void setDataLifespan(int dataLifespan) {
        this.dataLifespan = dataLifespan;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }
}
