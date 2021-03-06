package com.example.justi_000.statbasket;


public class Player
{
    long player_id;
    String first_name;
    String last_name;
    int number;
    int height_feet;
    int height_inches;
    String created_date;

    // constructors
    public Player()
    {
        player_id = 0;
        first_name = "";
        last_name = "";
        number = 0;
        height_feet = 3;
        height_inches = 0;
        created_date = "";
    }

    public Player(String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public Player(long id, String first_name, String last_name, int number, int height_feet, int height_inches) {
        this.player_id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.number = number;
        this.height_feet = height_feet;
        this.height_inches = height_inches;
    }

    public Player(String first_name, String last_name, int number, int height_feet, int height_inches) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.number = number;
        this.height_feet = height_feet;
        this.height_inches = height_inches;
    }

    // setters
    public void setId(long id) {
        this.player_id = id;
    }
    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }
    public void setLastName(String last_name) {
        this.last_name = last_name;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public void setHeight(int height_feet, int height_inches) {
        this.height_feet = height_feet;
        this.height_inches = height_inches;
    }
    public void setHeightFeet(int height_feet) {
        this.height_feet = height_feet;
    }
    public void setHeightInches(int height_inches) {
        this.height_inches = height_inches;
    }
    public void setCreatedDate(String created_date) { this.created_date = created_date; }

    // getters
    public long getId() {
        return this.player_id;
    }
    public String getFirstName() {
        return this.first_name;
    }
    public String getLastName() { return this.last_name; }
    public int getNumber() {
        return this.number;
    }
    public int getHeightFeet() {
        return this.height_feet;
    }
    public int getHeightInches() {
        return this.height_inches;
    }
    public String getCreatedDate() { return this.created_date; }
}
