package view;

import data.FigureModel;
import data.Person;
import data.PersonModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GUI {
    private static JFrame jFrame;
    private static JTextArea personInfo;
    private static JTextField filePathInput;
    private static Box tablePersonContents;
    private static Box tableFigureContents;
    private static JPanel infoBox;
    public static void MainWindow()
    {
        jFrame = new JFrame("Главное меню");
        MyTable();
        ButtonPanel();
        personInfo = new JTextArea("");
        filePathInput = new JTextField("C:\\dataBase\\Figure.db");

        personInfo.setRows(1);
        infoBox = new JPanel();
        infoBox.setLayout(new GridLayout(0,1));

        infoBox.add(new Label("Путь к файлу базы данных фигурок", Label.CENTER));
        infoBox.add(filePathInput);
        infoBox.add(new Label("Информация о владельце базы данных", Label.CENTER));
        infoBox.add(personInfo);

        jFrame.getContentPane().add(infoBox, BorderLayout.NORTH);

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLocationByPlatform(true);
        jFrame.setSize(960, 400);
        jFrame.setVisible(true);
    }
    private static JTable jTablePerson;
    private static JTable jTableFigure;
    private static PersonModel personModel;
    private static FigureModel figureModel;

    private static void MyTable()
    {
        jTablePerson = new JTable();
        personModel = new PersonModel();
        jTablePerson.setModel(personModel);
        tablePersonContents = new Box(BoxLayout.Y_AXIS);
        tablePersonContents.add(new JScrollPane(jTablePerson));
        jFrame.getContentPane().add(tablePersonContents, BorderLayout.WEST);

        jTableFigure = new JTable();
        figureModel = new FigureModel();
        jTableFigure.setModel(figureModel);
        tableFigureContents = new Box(BoxLayout.Y_AXIS);
        tableFigureContents.add(new JScrollPane(jTableFigure));
        jFrame.getContentPane().add(tableFigureContents, BorderLayout.CENTER);


    }
    private static JPanel myButtonPanel;
    private static void ButtonPanel()
    {
        myButtonPanel = new JPanel();
        myButtonPanel.setLayout(new GridLayout(1,0));

        JButton buttonSetDB = new JButton("Выбрать файл базы данных");
        JButton buttonSetDefaultDB = new JButton("Выбрать базовый файл базы данных");
        JButton buttonChoseCollection = new JButton("Выбрать коллекцию");
        JButton buttonAddFigure = new JButton("Добавить фигурку");
        JButton buttonChangeFigure = new JButton("Изменить фигурку");
        JButton buttonDeleteFigure = new JButton("Удалить фигурку");
        JButton buttonSaveDB = new JButton("Сохранить базу данных");

        buttonSetDB.addActionListener(e -> {
            try
            {
                personModel.setFilePath(Paths.get(filePathInput.getText()));
                personModel.readDB();
                personModel.fireTableDataChanged();
                Person person = personModel.getPerson();
                personInfo.setText(person.toString());
            }
            catch (Exception io)
            {}
        });
        buttonSetDefaultDB.addActionListener(e -> {
            try
            {
                personModel.setDefaultFilePath();
                personModel.readDB();
                personModel.fireTableDataChanged();
                Person person = personModel.getPerson();
                personInfo.setText(person.toString());
            }
            catch (Exception io)
            {}
        });
        buttonChoseCollection.addActionListener(e -> {
            try
            {
                int id = jTablePerson.getSelectedRow();
                figureModel.setCollection(personModel.getPerson().getCollection().get(id));
                figureModel.fireTableDataChanged();
            }
            catch (Exception io){}
        });

        myButtonPanel.add(buttonSetDB);
        myButtonPanel.add(buttonSetDefaultDB);
        myButtonPanel.add(buttonChoseCollection);
        myButtonPanel.add(buttonAddFigure);
        myButtonPanel.add(buttonChangeFigure);
        myButtonPanel.add(buttonDeleteFigure);

        jFrame.add(myButtonPanel, BorderLayout.SOUTH);
    }
    private static boolean isNumeric(String text)
    {
        boolean numeric = false;
        char symbol;
        for (int i = 0; i < text.length(); i++) {
            symbol = text.charAt(i);
            for (int j = 48; j < 58; j++) {
                if(symbol == (char)j) {numeric = true;}
            }
            if (!numeric) {return false;}
        }
        return numeric;
    }
}
