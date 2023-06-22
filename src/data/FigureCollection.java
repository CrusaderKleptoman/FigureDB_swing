package data;

import java.util.ArrayList;
import java.util.List;

public class FigureCollection {
    private int ID;
    private String collectionName;
    private List<Figure> collection = new ArrayList<>();

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

    public FigureCollection(List<Figure> collection) {this.collection = collection; }

    public Figure getFigure(int id) {return collection.get(id);
    }
    public Figure getFigureWithID(int id) {
        for (Figure figure: collection)
        {
            if(figure.getID() == id) return figure;
        };
        return null;
    }
    public void setFigure(int id, Figure figure) {collection.set(id, figure);}
    public int getLastID()
    {
        return collection.get(collection.size()-1).getID();
    }
    public void addFigure(Figure figure)
    {
        if(figure.getID()==0){figure.setID(collection.get(collection.size()-1).getID()+1);}
        collection.add(figure);}
    public void deleteFigure(int id) {
        try {
            collection.remove(id);
        }
        catch (IndexOutOfBoundsException exception)
        {
            System.out.println("Выберите фигурку");
        }
    }
    public int getSize() {return collection.size();}

    public List<Figure> getCollection() {
        return collection;
    }

    public void setCollection(List<Figure> collection) {
        this.collection = collection;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
