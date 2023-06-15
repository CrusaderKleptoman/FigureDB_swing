package data;

import javax.swing.table.AbstractTableModel;

public class FigureModel extends AbstractTableModel {
    private FigureCollection collection = new FigureCollection();
    public FigureModel(){
        collection = new FigureCollection();
    }

    public FigureModel(PersonModel person, int id)
    {
        collection = person.getPerson().getCollection().get(id);
    }

    @Override
    public int getRowCount() {
        return collection.getSize();
    }

    @Override
    public int getColumnCount() {
        return 11;
    }

    @Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0: return Integer.class;
            case 1: return String.class;
            case 2: return String.class;
            case 3: return String.class;
            case 4: return String.class;
            case 5: return Float.class;
            case 6: return String.class;
            case 7: return String.class;
            case 8: return String.class;
            case 9: return String.class;
            case 10: return Boolean.class;
            default: return Object.class;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "ID";
            case 1: return "Название";
            case 2: return "Тип";
            case 3: return "Материал";
            case 4: return "Состояние";
            case 5: return "Размер(мм)";
            case 6: return "Масштаб";
            case 7: return "Производитель";
            case 8: return "Описание";
            case 9: return "Изображение";
            case 10: return "Бартер";
            default: return "";
        }
    }
    // Функция определения данных ячейки
    @Override
    public Object getValueAt(int row, int column)
    {
        // Данные для стобцов
        switch (column) {
            case 0: return collection.getFigure(row).getID();
            case 1: return collection.getFigure(row).getName();
            case 2: return collection.getFigure(row).getFigureType();
            case 3: return collection.getFigure(row).getMaterial();
            case 4: return collection.getFigure(row).getCondition();
            case 5: return collection.getFigure(row).getSize();
            case 6: return collection.getFigure(row).getScale();
            case 7: return collection.getFigure(row).getManufacturer();
            case 8: return collection.getFigure(row).getDescription();
            case 9: return collection.getFigure(row).getImageName();
            case 10: return collection.getFigure(row).isBarterPossibility();

        }
        return "Не определена";
    }

    public FigureCollection getCollection() {
        return collection;
    }

    public void setCollection(FigureCollection collection) {
        this.collection = collection;
    }
    public void deleteFigure(int id) { this.collection.deleteFigure(id);}
    public void addFigure(Figure figure) {this.collection.addFigure(figure);}
    public void setFigure(Figure figure, int id) {this.collection.setFigure(id, figure);}
    public Figure getFigure(int id) {return this.collection.getFigure(id);}
}
