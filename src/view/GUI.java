package view;

import data.Figure;
import data.FigureModel;
import data.Person;
import data.PersonModel;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
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
        jFrame.setSize(1500, 400);
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
                filePathInput.setForeground(Color.BLACK);
                personModel.setFilePath(Paths.get(filePathInput.getText()));
                if(!Files.exists(personModel.getFilePath()))
                {
                    filePathInput.setText("Неверный путь к файлу");
                    filePathInput.setForeground(Color.RED);
                    throw new Exception("Неверный путь к файлу");
                }
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
                filePathInput.setForeground(Color.BLACK);
                personModel.setDefaultFilePath();
                if(!Files.exists(personModel.getFilePath()))
                {
                    filePathInput.setText("Ошибка, удалена базовая база данных");
                    filePathInput.setForeground(Color.RED);
                    throw new Exception("Ошибка, удалена базовая база данных");
                }
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
            catch (NullPointerException io)
            {
                System.out.println("Не выбрана коллекция");;
            }
            catch (Exception io){}
        });
        buttonAddFigure.addActionListener(e -> {
            try {
                JDialog jDialogAddFigure = new JDialog(jFrame, "Создание фигуры", true);
                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new GridLayout(0, 10));

                JTextField name = new JTextField("Название");
                JTextField type = new JTextField("Тип");
                JTextField material = new JTextField("Материал");
                JTextField condition = new JTextField("Состояние");
                JTextField size = new JTextField("Размер");
                JTextField scale = new JTextField("Масштаб");
                JTextField manufacturer = new JTextField("Производитель");
                JTextField description = new JTextField("Описание");
                JTextField imageName = new JTextField("Название изображения");
                JCheckBox barterPosibility = new JCheckBox();

                JButton createFigure = new JButton("Создать фигурку");
                createFigure.addActionListener(e1 -> {
                    Figure newFigure = new Figure();
                    newFigure.setName(name.getText());
                    newFigure.setFigureType(type.getText());
                    newFigure.setMaterial(material.getText());
                    newFigure.setCondition(condition.getText());
                    if(!isNumeric(size.getText())) {size.setText("0");}
                    newFigure.setSize(Float.parseFloat(size.getText()));
                    newFigure.setScale(scale.getText());
                    newFigure.setManufacturer(manufacturer.getText());
                    newFigure.setDescription(description.getText());
                    newFigure.setImageName(imageName.getText());
                    newFigure.setBarterPossibility(barterPosibility.isSelected());

                    newFigure.setID(personModel.getPerson().getFigureWithLastID()+1);

                    personModel.addFigure(newFigure);
                    figureModel.addFigure(newFigure);

                    jDialogAddFigure.dispose();
                });

                dialogPanel.add(new JLabel("Название"));
                dialogPanel.add(new JLabel("Тип"));
                dialogPanel.add(new JLabel("Материал"));
                dialogPanel.add(new JLabel("Состояние"));
                dialogPanel.add(new JLabel("Размер"));
                dialogPanel.add(new JLabel("Масштаб"));
                dialogPanel.add(new JLabel("Производитель"));
                dialogPanel.add(new JLabel("Описание"));
                dialogPanel.add(new JLabel("Название изображения"));
                dialogPanel.add(new JLabel("Бартер"));

                dialogPanel.add(name);
                dialogPanel.add(type);
                dialogPanel.add(material);
                dialogPanel.add(condition);
                dialogPanel.add(size);
                dialogPanel.add(scale);
                dialogPanel.add(manufacturer);
                dialogPanel.add(description);
                dialogPanel.add(imageName);
                dialogPanel.add(barterPosibility);

                dialogPanel.add(createFigure);

                jDialogAddFigure.setContentPane(dialogPanel);
                jDialogAddFigure.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                jDialogAddFigure.setSize(1500,110);
                jDialogAddFigure.setVisible(true);

                figureModel.fireTableDataChanged();
            }
            catch (Exception io){}
        });
        buttonChangeFigure.addActionListener(e -> {try {
            int id = jTableFigure.getSelectedRow();
            Figure customFigure = figureModel.getFigure(id);
            JDialog jDialogChangeFigure = new JDialog(jFrame, "Изменение фигуры", true);
            JPanel dialogPanel = new JPanel();
            dialogPanel.setLayout(new GridLayout(0, 10));

            JTextField name = new JTextField(customFigure.getName());
            JTextField type = new JTextField(customFigure.getFigureType());
            JTextField material = new JTextField(customFigure.getMaterial());
            JTextField condition = new JTextField(customFigure.getCondition());
            JTextField size = new JTextField(String.valueOf(customFigure.getSize()));
            JTextField scale = new JTextField(customFigure.getScale());
            JTextField manufacturer = new JTextField(customFigure.getManufacturer());
            JTextField description = new JTextField(customFigure.getDescription());
            JTextField imageName = new JTextField(customFigure.getImageName());
            JCheckBox barterPosibility = new JCheckBox();

            JButton changeFigure = new JButton("Изменить фигурку");
            changeFigure.addActionListener(e1 -> {
                customFigure.setName(name.getText());
                customFigure.setFigureType(type.getText());
                customFigure.setMaterial(material.getText());
                customFigure.setCondition(condition.getText());
                if(!isNumeric(size.getText())) {size.setText("0");}
                customFigure.setSize(Float.parseFloat(size.getText()));
                customFigure.setScale(scale.getText());
                customFigure.setManufacturer(manufacturer.getText());
                customFigure.setDescription(description.getText());
                customFigure.setImageName(imageName.getText());
                customFigure.setBarterPossibility(barterPosibility.isSelected());

                personModel.setFigure(customFigure, id);
                figureModel.setFigure(customFigure, id);

                jDialogChangeFigure.dispose();
            });

            dialogPanel.add(new JLabel("Название"));
            dialogPanel.add(new JLabel("Тип"));
            dialogPanel.add(new JLabel("Материал"));
            dialogPanel.add(new JLabel("Состояние"));
            dialogPanel.add(new JLabel("Размер"));
            dialogPanel.add(new JLabel("Масштаб"));
            dialogPanel.add(new JLabel("Производитель"));
            dialogPanel.add(new JLabel("Описание"));
            dialogPanel.add(new JLabel("Название изображения"));
            dialogPanel.add(new JLabel("Бартер"));

            dialogPanel.add(name);
            dialogPanel.add(type);
            dialogPanel.add(material);
            dialogPanel.add(condition);
            dialogPanel.add(size);
            dialogPanel.add(scale);
            dialogPanel.add(manufacturer);
            dialogPanel.add(description);
            dialogPanel.add(imageName);
            dialogPanel.add(barterPosibility);

            dialogPanel.add(changeFigure);

            jDialogChangeFigure.setContentPane(dialogPanel);
            jDialogChangeFigure.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            jDialogChangeFigure.setSize(1500,110);
            jDialogChangeFigure.setVisible(true);

            figureModel.fireTableDataChanged();
        }
        catch (Exception io){}});
        buttonDeleteFigure.addActionListener(e -> {
            figureModel.deleteFigure(jTableFigure.getSelectedRow());
            figureModel.fireTableDataChanged();
        });
        buttonSaveDB.addActionListener(e -> {personModel.saveDB();});

        myButtonPanel.add(buttonSetDB);
        myButtonPanel.add(buttonSetDefaultDB);
        myButtonPanel.add(buttonChoseCollection);
        myButtonPanel.add(buttonAddFigure);
        myButtonPanel.add(buttonChangeFigure);
        myButtonPanel.add(buttonDeleteFigure);
        myButtonPanel.add(buttonSaveDB);

        jFrame.add(myButtonPanel, BorderLayout.SOUTH);
    }
    private static boolean isNumeric(String text)
    {
        boolean numeric = false;
        char symbol;
        for (int i = 0; i < text.length(); i++) {
            symbol = text.charAt(i);
            if(symbol == '.') {numeric = true;}
            else for (int j = 48; j < 58; j++) {
                if(symbol == (char)j) {numeric = true;}
            }
            if (!numeric) {return false;}
        }
        return numeric;
    }
}
