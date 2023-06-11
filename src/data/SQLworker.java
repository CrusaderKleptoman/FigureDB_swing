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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
