package data;

import java.util.ArrayList;
import java.util.List;

public class FigureCollection {
    private String collectionName;
    private List<Figure> collection = new ArrayList<>();
    private int size = 0;

    public FigureCollection() {
    }

    public FigureCollection(String name) {
        this.collectionName = name;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public FigureCollection(List<Figure> collection) {this.collection = collection; size = collection.size();}
    public Figure getFigure(int id) { return collection.get(id); }
    public void setFigure(int id, Figure figure) {collection.set(id, figure);}
    public void addFigure(Figure figure) { collection.add(figure); size = collection.size();}
    public int getSize() {return collection.size();}

}
