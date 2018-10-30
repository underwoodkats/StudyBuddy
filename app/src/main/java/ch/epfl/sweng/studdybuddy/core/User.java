package ch.epfl.sweng.studdybuddy.core;

final public class User
{
    private String name;
    private ID<User> userID;


    public ID<User> getUserID()
    {
        return userID;
    }

    public void setUserID(ID<User> userID)
    {
        this.userID = userID;
    }


    public User(String name, ID<User> userId)
    {
        //this();
        this.name = name;
        this.userID = userId;
    }

    public User(String name, String uId) {
        this.name = name;
        this.userID = new ID<>(uId);
    }

    public User() {}


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}