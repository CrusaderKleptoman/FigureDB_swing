package data;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

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
        return 10;
    }

    @Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0: return String.class;
            case 1: return String.class;
            case 2: return String.class;
            case 3: return String.class;
            case 4: return Float.class;
            case 5: return String.class;
            case 6: return String.class;
            case 7: return String.class;
            case 8: return String.class;
            case 9: return Boolean.class;
            default: return Object.class;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "Название";
            case 1: return "Тип";
            case 2: return "Материал";
            case 3: return "Состояние";
            case 4: return "Размер(мм)";
            case 5: return "Масштаб";
            case 6: return "Производитель";
            case 7: return "Описание";
            case 8: return "Изображение";
            case 9: return "Бартер";
            default: return "";
        }
    }
    // Функция определения данных ячейки
    @Override
    public Object getValueAt(int row, int column)
    {
        // Данные для стобцов
        switch (column) {
            case 0: return collection.getFigure(row).getName();
            case 1: return collection.getFigure(row).getFigureType();
            case 2: return collection.getFigure(row).getMaterial();
            case 3: return collection.getFigure(row).getCondition();
            case 4: return collection.getFigure(row).getSize();
            case 5: return collection.getFigure(row).getScale();
            case 6: return collection.getFigure(row).getManufacturer();
            case 7: return collection.getFigure(row).getDescription();
            case 8: return collection.getFigure(row).getImageName();
            case 9: return collection.getFigure(row).isBarterPossibility();

        }
        return "Не определена";
    }

    public FigureCollection getCollection() {
        return collection;
    }

    public void setCollection(FigureCollection collection) {
        this.collection = collection;
    }
}
