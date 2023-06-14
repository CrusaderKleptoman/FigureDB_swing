package data;

import javax.swing.table.AbstractTableModel;
import java.nio.file.Path;

public class PersonModel extends AbstractTableModel {
    private static Person person = new Person();
    private static SQLworker figureDB = new SQLworker();

    public PersonModel(){

    }

    public PersonModel(Path filePath)
    {
        figureDB.setFilePath(filePath);
        readDB();
    }

    public static void readDB()
    {
        person = figureDB.createConnection();
    }

    @Override
    public int getRowCount() {
        return person.getCollection().size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }
    @Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0: return String.class;
            case 1: return Integer.class;

            default: return Object.class;
        }
    }
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "Название";
            case 1: return "Количество";
            default: return "";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return person.getCollection().get(rowIndex).getCollectionName();
            case 1: return person.getCollection().get(rowIndex).getSize();

        }
        return "Не определена";
    }

    public static Person getPerson() {
        return person;
    }

    public static void setPerson(Person person) {
        PersonModel.person = person;
    }
    public static void setFilePath(Path filePath)
    {
        figureDB.setFilePath(filePath);
    }

    public static Path getFilePath()
    {
        return figureDB.getFilePath();
    }

    public static void setDefaultFilePath() {figureDB.setDefaultFilePath();}
}
