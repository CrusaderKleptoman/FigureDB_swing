package data;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.HashMap;

public class SQLworker {
    private Connection connection;
    private Path filePath;

    public SQLworker() {setDefaultFilePath();}

    public Person createConnection()
    {
        Person person = new Person();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("select * from Person");
            FigureCollection baseCollection = new FigureCollection();

            while(rs.next())
            {
                person.setFirstName(rs.getString("First_Name"));
                person.setLastName(rs.getString("Last_Name"));
                person.setNickname(rs.getString("Nickname"));
                person.setPhone(rs.getString("Phone"));
                person.setPhone(rs.getString("Phone"));
                int collectionId = rs.getInt("Collection");

                PreparedStatement psBaseCollection = connection.prepareStatement("SELECT * FROM figureInfo");
                ResultSet rsBaseCollection = psBaseCollection.executeQuery();

                while(rsBaseCollection.next())
                {
                    Figure figure = new Figure();
                    figure.setID(rsBaseCollection.getInt("id_figure"));
                    figure.setName(rsBaseCollection.getString("name"));
                    figure.setFigureType(rsBaseCollection.getString("type"));
                    figure.setMaterial(rsBaseCollection.getString("material"));
                    figure.setCondition(rsBaseCollection.getString("condition"));
                    figure.setSize(rsBaseCollection.getFloat("size"));
                    figure.setScale(rsBaseCollection.getString("scale"));
                    figure.setDescription(rsBaseCollection.getString("description"));
                    figure.setImageName(rsBaseCollection.getString("imageName"));
                    figure.setBarterPossibility(rsBaseCollection.getBoolean("barterPosibility"));

                    baseCollection.addFigure(figure);
                }
                psBaseCollection.close();

                PreparedStatement psCollection = connection.prepareStatement("SELECT * FROM Collection WHERE id_collection = ?");
                psCollection.setInt(1, collectionId);
                ResultSet rsCollection = psCollection.executeQuery();
                FigureCollection collection = new FigureCollection();
                collection.setID(collectionId);

                while(rsCollection.next())
                {
                    int figureId = rsCollection.getInt("Figure");
                    Figure figure = baseCollection.getFigure(figureId);
                    collection.addFigure(figure);
                }
                psCollection.close();

                collection.setCollectionName(rs.getString("CollectionName"));

                person.addCollection(collection);
                person.setTradeOption(rs.getString("TradeOption"));
                person.setDeliveryOption(rs.getString("DeliveryOption"));

            }
            person.setBaseCollection(baseCollection);
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return person;
    }

    public void SaveDB(Person person)
    {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            String sql = "TRUNCATE TABLE Person";
            statement.executeUpdate(sql);

            sql = "TRUNCATE TABLE Collection";
            statement.executeUpdate(sql);

            sql = "TRUNCATE TABLE figureInfo";
            statement.executeUpdate(sql);

            sql = "INSERT INTO figureInfo (id_figure, name, type, material, condition, size, scale, manufacturer, description, imageName, barterPosibility) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement psFigure = connection.prepareStatement(sql);

            for (Figure figure: person.getBaseCollection().getCollection()) {
                psFigure.setInt(1, figure.getID());
                psFigure.setString(2, figure.getName());
                psFigure.setString(3, figure.getFigureType());
                psFigure.setString(4, figure.getMaterial());
                psFigure.setString(5, figure.getCondition());
                psFigure.setFloat(6, figure.getSize());
                psFigure.setString(7, figure.getScale());
                psFigure.setString(8, figure.getManufacturer());
                psFigure.setString(9, figure.getDescription());
                psFigure.setString(10, figure.getImageName());
                psFigure.setInt(11, figure.barterInt());
                psFigure.executeUpdate();
            }
            psFigure.close();

            sql = "INSERT INTO Collection (id_collection, Figure)  VALUES (?, ?)";
            PreparedStatement psCollection = connection.prepareStatement(sql);

            for(FigureCollection collection: person.getCollection())
            {
                for(Figure figure: collection.getCollection())
                {
                    psCollection.setInt(1, collection.getID());
                    psCollection.setInt(2, figure.getID());
                }
            }
            psCollection.close();

            sql = "INSERT INTO Person (First_Name, Last_Name, Nickname, Phone, Collection, CollectionName, TradeOption, DeliveryOption) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psPerson = connection.prepareStatement(sql);

            for(FigureCollection collection: person.getCollection())
            {
                psPerson.setString(1, person.getFirstName());
                psPerson.setString(2, person.getLastName());
                psPerson.setString(3, person.getNickname());
                psPerson.setString(4, person.getPhone());
                psPerson.setInt(5, collection.getID());
                psPerson.setString(6, collection.getCollectionName());
                psPerson.setString(7, person.getTradeOption());
                psPerson.setString(8, person.getDeliveryOption());
            }
            psFigure.close();

        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }
    public void setDefaultFilePath() {this.filePath = (Paths.get(System.getProperty("user.dir") + "\\Figure.db"));}
}
