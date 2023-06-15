package data;

public class Figure {
    private int ID;
    private String name;
    private String figureType;
    private String material;
    private String condition;
    private float size;
    private String scale;
    private String manufacturer;
    private String description;
    private String imageName;
    private boolean barterPossibility;

    public Figure() {}

    public Figure(int id, String name, String figureType, String material, String condition, float size, String scale, String manufacturer, String description, String imageName, boolean barterPossibility) {
        this.ID = id;
        this.name = name;
        this.figureType = figureType;
        this.material = material;
        this.condition = condition;
        this.size = size;
        this.scale = scale;
        this.manufacturer = manufacturer;
        this.description = description;
        this.imageName = imageName;
        this.barterPossibility = barterPossibility;
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFigureType() {
        return figureType;
    }

    public void setFigureType(String figureType) {
        this.figureType = figureType;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public boolean isBarterPossibility() {
        return barterPossibility;
    }

    public void setBarterPossibility(boolean barterPossibility) {
        this.barterPossibility = barterPossibility;
    }
    public int barterInt(){
        if(barterPossibility) return 1;
        else return 0;
    }
}
