package data;

import java.util.ArrayList;
import java.util.HashMap;

public class Person {
    private String firstName;
    private String lastName;
    private String nickname;
    private String phone;
    private ArrayList<FigureCollection> collection = new ArrayList<>();
    private FigureCollection baseCollection;
    private String tradeOption;
    private String deliveryOption;

    public Person(){}

    public Person(String firstName, String lastName, String nickname, String phone, ArrayList<FigureCollection> collection, String tradeOption, String deliveryOption) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.phone = phone;
        this.collection = collection;
        this.tradeOption = tradeOption;
        this.deliveryOption = deliveryOption;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<FigureCollection> getCollection() {
        return collection;
    }

    public void setCollection(ArrayList<FigureCollection> collection) {
        this.collection = collection;
    }

    public void addCollection(FigureCollection collection) {
        this.collection.add(collection);
    }

    public String getTradeOption() {
        return tradeOption;
    }

    public void setTradeOption(String tradeOption) {
        this.tradeOption = tradeOption;
    }

    public String getDeliveryOption() {
        return deliveryOption;
    }

    public void setDeliveryOption(String deliveryOption) {
        this.deliveryOption = deliveryOption;
    }

    public FigureCollection getBaseCollection() {
        return baseCollection;
    }

    public void setBaseCollection(FigureCollection baseCollection) {
        this.baseCollection = baseCollection;
    }

    @Override
    public String toString() {
        return firstName + ' ' + lastName + ", \'" + nickname + "\', " + phone + ", общий размер коллекции = " + baseCollection.getSize();
    }
}
