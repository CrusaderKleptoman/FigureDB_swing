package data;

import java.sql.*;
import java.util.HashMap;

public class SQLworker {
    private Connection connection;
    private String filePath;

    public SQLworker() {}

    public Person createConnection()
    {
        Person person = new Person();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("select * from Person");


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
                FigureCollection baseCollection = new FigureCollection();
                while(rsBaseCollection.next())
                {
                    Figure figure = new Figure();
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

                PreparedStatement psCollection = connection.prepareStatement("SELECT * FROM Collection WHERE id_collection = ?");
                psCollection.setInt(1, collectionId);
                ResultSet rsCollection = psCollection.executeQuery();
                FigureCollection collection = new FigureCollection();
                collection.setID(collectionId);
                while(rsCollection.next())
                {
                    int figureId = rsCollection.getInt("Figure");

                    PreparedStatement psFigure = connection.prepareStatement("SELECT * FROM figureInfo WHERE id_figure = ?");
                    psFigure.setInt(1, figureId);
                    ResultSet rsFigure = psFigure.executeQuery();

                    Figure figure = new Figure();
                    figure.setID(figureId);
                    figure.setName(rsFigure.getString("name"));
                    figure.setFigureType(rsFigure.getString("type"));
                    figure.setMaterial(rsFigure.getString("material"));
                    figure.setCondition(rsFigure.getString("condition"));
                    figure.setSize(rsFigure.getFloat("size"));
                    figure.setScale(rsFigure.getString("scale"));
                    figure.setDescription(rsFigure.getString("description"));
                    figure.setImageName(rsFigure.getString("imageName"));
                    figure.setBarterPossibility(rsFigure.getBoolean("barterPosibility"));

                    collection.addFigure(figure);
                }
                collection.setCollectionName(rs.getString("CollectionName"));
                person.setBaseCollection(baseCollection);
                person.addCollection(collection);
                person.setTradeOption(rs.getString("TradeOption"));
                person.setDeliveryOption(rs.getString("DeliveryOption"));

            }

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

            sql = "INSERT INTO figureInfo (name, type, material, condition, size, scale, manufacturer, description, imageName, barterPosibility) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (Figure figure: person.getBaseCollection().getCollection()) {
                preparedStatement.setString(1, figure.getName());
                preparedStatement.setString(2, figure.getFigureType());
                preparedStatement.setString(3, figure.getMaterial());
                preparedStatement.setString(4, figure.getCondition());
                preparedStatement.setFloat(5, figure.getSize());
                preparedStatement.setString(6, figure.getScale());
                preparedStatement.setString(7, figure.getManufacturer());
                preparedStatement.setString(8, figure.getDescription());
                preparedStatement.setString(9, figure.getImageName());
                preparedStatement.setInt(10, figure.barterInt());
                preparedStatement.executeUpdate();
            }
            /*

            while(rs.next())
            {
                person.setFirstName(rs.getString("First_Name"));
                person.setLastName(rs.getString("Last_Name"));
                person.setNickname(rs.getString("Nickname"));
                person.setPhone(rs.getString("Phone"));
                person.setPhone(rs.getString("Phone"));
                int collectionId = rs.getInt("Collection");
                PreparedStatement psCollection = connection.prepareStatement("SELECT * FROM Collection WHERE id_collection = ?");
                psCollection.setInt(1, collectionId);
                ResultSet rsCollection = psCollection.executeQuery();
                FigureCollection collection = new FigureCollection();
                while(rsCollection.next())
                {
                    int figureId = rsCollection.getInt("Figure");

                    PreparedStatement psFigure = connection.prepareStatement("SELECT * FROM figureInfo WHERE id_figure = ?");
                    psFigure.setInt(1, figureId);
                    ResultSet rsFigure = psFigure.executeQuery();

                    Figure figure = new Figure();
                    figure.setName(rsFigure.getString("name"));
                    figure.setFigureType(rsFigure.getString("type"));
                    figure.setMaterial(rsFigure.getString("material"));
                    figure.setCondition(rsFigure.getString("condition"));
                    figure.setSize(rsFigure.getFloat("size"));
                    figure.setScale(rsFigure.getString("scale"));
                    figure.setDescription(rsFigure.getString("description"));
                    figure.setImageName(rsFigure.getString("imageName"));
                    figure.setBarterPossibility(rsFigure.getBoolean("barterPosibility"));

                    collection.addFigure(figure);
                }
                collection.setCollectionName(rs.getString("CollectionName"));
                person.addCollection(collection);
                person.setTradeOption(rs.getString("TradeOption"));
                person.setDeliveryOption(rs.getString("DeliveryOption"));

            }*/

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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
