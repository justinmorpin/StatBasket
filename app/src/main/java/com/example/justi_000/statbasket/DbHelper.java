package com.example.justi_000.statbasket;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DbHelper extends SQLiteOpenHelper
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //region DATABASE INFO

    // Logcat tag
    private static final String LOG = "DBHelper";
    // Database Version
    private static final int DATABASE_VERSION = 10;
    // Database Name
    private static final String DATABASE_NAME = "StatBasket";

    //endregion DATABASE INFO

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //region TABLE NAMES

    private static final String TABLE_TEAM = "team";
    private static final String TABLE_PLAYER = "player";
    private static final String TABLE_PLAYER_TEAM = "player_team";
    private static final String TABLE_GAME = "game";
    private static final String TABLE_ACTIVE_PLAYERS = "active_players";
    private static final String TABLE_PLAYER_STATS = "player_stats";

    //endregion TABLE NAMES

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //region TABLE COLUMN NAMES

    // COMMON COLUMNS
    private static final String COLUMN_CREATED_DATE = "created_date";

    // TEAM COLUMNS
    private static final String COLUMN_TEAM_ID = "team_id";
    private static final String COLUMN_TEAM_NAME = "team_name";

    // PLAYER COLUMNS
    private static final String COLUMN_PLAYER_ID = "player_id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_NUMBER = "number";
    private static final String COLUMN_HEIGHT_FEET = "height_feet";
    private static final String COLUMN_HEIGHT_INCHES = "height_inches";

    // PLAYER_TEAM COLUMNS
    private static final String COLUMN_PLAYER_TEAM_ID = "player_team_id";

    // GAME COLUMNS
    private static final String COLUMN_GAME_ID = "game_id";
    private static final String COLUMN_HOME_TEAM_ID = "home_team_id";
    private static final String COLUMN_AWAY_TEAM_ID = "away_team_id";
    private static final String COLUMN_LOCATION = "location";

    // GAME STATS COLUMNS
    private static final String COLUMN_MADE_FT = "free_throws_made";
    private static final String COLUMN_MISSED_FT = "free_throws_missed";
    private static final String COLUMN_MADE_TWOS = "twos_made";
    private static final String COLUMN_MISSED_TWOS = "twos_missed";
    private static final String COLUMN_MADE_THREES = "threes_made";
    private static final String COLUMN_MISSED_THREES = "threes_missed";
    private static final String COLUMN_DEF_REBS = "defensive_rebounds";
    private static final String COLUMN_OFF_REBS = "offensive_rebounds";
    private static final String COLUMN_ASSISTS = "assists";
    private static final String COLUMN_STEALS = "steals";
    private static final String COLUMN_TURNOVERS = "turnovers";
    private static final String COLUMN_BLOCKS = "blocks";
    private static final String COLUMN_FOULS = "fouls";


    //endregion TABLE COLUMN NAMES

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //region CREATE TABLE STATEMENTS

    // TEAM table create statement
    private static final String CREATE_TABLE_TEAM = "CREATE TABLE " + TABLE_TEAM + "("
            + COLUMN_TEAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TEAM_NAME + " TEXT NOT NULL UNIQUE,"
            + COLUMN_CREATED_DATE + " DATETIME)";

    // PLAYER table create statement
    private static final String CREATE_TABLE_PLAYER = "CREATE TABLE " + TABLE_PLAYER + "("
            + COLUMN_PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_FIRST_NAME + " TEXT,"
            + COLUMN_LAST_NAME + " TEXT,"
            + COLUMN_NUMBER + " INTEGER,"
            + COLUMN_HEIGHT_FEET + " INTEGER,"
            + COLUMN_HEIGHT_INCHES + " INTEGER,"
            + COLUMN_CREATED_DATE + " DATETIME)";

    // PLAYER_TEAM table create statement
    private static final String CREATE_TABLE_PLAYER_TEAM = "CREATE TABLE " + TABLE_PLAYER_TEAM + "("
            + COLUMN_PLAYER_TEAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TEAM_ID + " INTEGER,"
            + COLUMN_PLAYER_ID + " INTEGER,"
            + COLUMN_CREATED_DATE + " DATETIME)";

    // GAME table create statement
    private static final String CREATE_TABLE_GAME = "CREATE TABLE " + TABLE_GAME + "("
            + COLUMN_GAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_HOME_TEAM_ID + " INTEGER,"
            + COLUMN_AWAY_TEAM_ID + " INTEGER,"
            + COLUMN_LOCATION + " TEXT,"
            + COLUMN_CREATED_DATE + " DATETIME)";

    // ACTIVE_PLAYERS table create statement
    private static final String CREATE_TABLE_ACTIVE_PLAYERS = "CREATE TABLE " + TABLE_ACTIVE_PLAYERS + "("
            + COLUMN_GAME_ID + " INTEGER,"
            + COLUMN_PLAYER_ID + " INTEGER)";

    // PLAYER_STATS table create statement
    private static final String CREATE_TABLE_PLAYER_STATS = "CREATE TABLE " + TABLE_PLAYER_STATS + "("
            + COLUMN_GAME_ID        + " INTEGER NOT NULL,"
            + COLUMN_PLAYER_ID      + " INTEGER NOT NULL,"
            + COLUMN_MADE_FT        + " INTEGER,"
            + COLUMN_MISSED_FT      + " INTEGER,"
            + COLUMN_MADE_TWOS      + " INTEGER,"
            + COLUMN_MISSED_TWOS    + " INTEGER,"
            + COLUMN_MADE_THREES    + " INTEGER,"
            + COLUMN_MISSED_THREES  + " INTEGER,"
            + COLUMN_DEF_REBS       + " INTEGER,"
            + COLUMN_OFF_REBS       + " INTEGER,"
            + COLUMN_ASSISTS        + " INTEGER,"
            + COLUMN_STEALS         + " INTEGER,"
            + COLUMN_TURNOVERS      + " INTEGER,"
            + COLUMN_BLOCKS         + " INTEGER,"
            + COLUMN_FOULS          + " INTEGER,"
            + " CONSTRAINT pk_player_game PRIMARY KEY (" + COLUMN_GAME_ID + ", " + COLUMN_PLAYER_ID + "))";

    //endregion CREATE TABLE STATEMENTS

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //region DbHelper METHODS (Create and Drop Tables)

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_PLAYER);
        db.execSQL(CREATE_TABLE_TEAM);
        db.execSQL(CREATE_TABLE_PLAYER_TEAM);
        db.execSQL(CREATE_TABLE_GAME);
        db.execSQL(CREATE_TABLE_ACTIVE_PLAYERS);
        db.execSQL(CREATE_TABLE_PLAYER_STATS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER_TEAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVE_PLAYERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER_STATS);

        // create new tables
        onCreate(db);
    }

    //endregion DbHelper METHODS (Create and Drop Tables)

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //region PLAYER METHODS

    public long createPlayer(Player player, long team_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, player.getFirstName());
        values.put(COLUMN_LAST_NAME, player.getLastName());
        values.put(COLUMN_NUMBER, player.getNumber());
        values.put(COLUMN_HEIGHT_FEET, player.getHeightFeet());
        values.put(COLUMN_HEIGHT_INCHES, player.getHeightInches());
        values.put(COLUMN_CREATED_DATE, getCurrentDateTime());

        // insert row
        long player_id = db.insert(TABLE_PLAYER, null, values);

        // assign a team to player
        createPlayerTeam(player_id, team_id);

        return player_id;
    }

    public Player getPlayer(long player_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_PLAYER + " WHERE "
                + COLUMN_PLAYER_ID + " = " + player_id;

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query, null);
        Player player = new Player();

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            player.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_PLAYER_ID)));
            player.setFirstName((cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME))));
            player.setLastName((cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME))));
            player.setNumber((cursor.getInt(cursor.getColumnIndex(COLUMN_NUMBER))));
            player.setHeightFeet((cursor.getInt(cursor.getColumnIndex(COLUMN_HEIGHT_FEET))));
            player.setHeightInches((cursor.getInt(cursor.getColumnIndex(COLUMN_HEIGHT_INCHES))));
            player.setCreatedDate(cursor.getString(cursor.getColumnIndex(COLUMN_CREATED_DATE)));
        }

        return player;
    }

    public List<Player> getAllPlayers(long team_id) {
        List<Player> players = new ArrayList<>();
        String query = "SELECT  P." + COLUMN_PLAYER_ID + ", P." + COLUMN_FIRST_NAME + ", P." + COLUMN_LAST_NAME
                + ", P." + COLUMN_NUMBER + ", P." + COLUMN_HEIGHT_FEET + ", P." + COLUMN_HEIGHT_INCHES + ", P." + COLUMN_CREATED_DATE
                + " FROM " + TABLE_PLAYER + " P"
                + " INNER JOIN " + TABLE_PLAYER_TEAM + " PT ON PT." + COLUMN_PLAYER_ID + " = P." + COLUMN_PLAYER_ID
                + " WHERE " + COLUMN_TEAM_ID + " = " + team_id;

        Log.e(LOG, query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //add all rows from database to list of teams
        if (cursor.moveToFirst()) {
            do {
                Player player = new Player();
                player.setId(cursor.getInt((cursor.getColumnIndex(COLUMN_PLAYER_ID))));
                player.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
                player.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
                player.setNumber(cursor.getInt(cursor.getColumnIndex(COLUMN_NUMBER)));
                player.setHeight(cursor.getInt(cursor.getColumnIndex(COLUMN_HEIGHT_FEET)), cursor.getInt(cursor.getColumnIndex(COLUMN_HEIGHT_INCHES)));
                player.setCreatedDate(cursor.getString(cursor.getColumnIndex(COLUMN_CREATED_DATE)));
                players.add(player);
            } while (cursor.moveToNext());
        }

        return players;
    }

    public int updatePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        if(player.getId() > 0) {
            values.put(COLUMN_FIRST_NAME, player.getFirstName());
            values.put(COLUMN_LAST_NAME, player.getLastName());
            values.put(COLUMN_NUMBER, player.getNumber());
            values.put(COLUMN_HEIGHT_FEET, player.getHeightFeet());
            values.put(COLUMN_HEIGHT_INCHES, player.getHeightInches());

            return db.update(TABLE_PLAYER, values, COLUMN_PLAYER_ID + " = ?",
                    new String[]{String.valueOf(player.getId())});
        }
        else return 0;
    }


    public int deletePlayer(long player_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(player_id > 0) {
            return db.delete(TABLE_PLAYER, COLUMN_PLAYER_ID + " = ?",
                    new String[]{String.valueOf(player_id)});
        }
        else return 0;
    }

    //endregion PLAYER METHODS

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //region TEAM METHODS

    public long createTeam(Team team) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TEAM_NAME, team.getName());
        values.put(COLUMN_CREATED_DATE, getCurrentDateTime());

        // insert row
        long team_id = db.insert(TABLE_TEAM, null, values);

        return team_id;
    }

    public Team getTeam(long team_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_TEAM + " WHERE "
                + COLUMN_TEAM_ID + " = " + team_id;

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query, null);
        Team team = new Team();

        if (cursor.getCount() != 0)
        {
            cursor.moveToFirst();

            team.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_TEAM_ID)));
            team.setName((cursor.getString(cursor.getColumnIndex(COLUMN_TEAM_NAME))));
            team.setCreatedDate(cursor.getString(cursor.getColumnIndex(COLUMN_CREATED_DATE)));
        }

        return team;
    }

    public Team getTeamFromName(String team_name) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_TEAM + " WHERE "
                + COLUMN_TEAM_NAME + " = '" + team_name + "'";

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query, null);
        Team team = new Team();

        if (cursor.moveToFirst())
        {
            team.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_TEAM_ID)));
            team.setName((cursor.getString(cursor.getColumnIndex(COLUMN_TEAM_NAME))));
            team.setCreatedDate(cursor.getString(cursor.getColumnIndex(COLUMN_CREATED_DATE)));
        }

        return team;
    }

    public List<Team> getAllTeams() {
        List<Team> teams = new ArrayList<Team>();
        String query = "SELECT  * FROM " + TABLE_TEAM;

        Log.e(LOG, query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //add all rows from database to list of teams
        if (cursor.moveToFirst()) {
            do {
                Team team = new Team();
                team.setId(cursor.getInt((cursor.getColumnIndex(COLUMN_TEAM_ID))));
                team.setName((cursor.getString(cursor.getColumnIndex(COLUMN_TEAM_NAME))));
                team.setCreatedDate(cursor.getString(cursor.getColumnIndex(COLUMN_CREATED_DATE)));
                teams.add(team);
            } while (cursor.moveToNext());
        }

        return teams;
    }

    public int updateTeam(Team team) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        if(team.getId() > 0) {
            values.put(COLUMN_TEAM_NAME, team.getName());

            return db.update(TABLE_TEAM, values, COLUMN_TEAM_ID + " = ?",
                    new String[]{String.valueOf(team.getId())});
        }
        else return 0;
    }


    public int deleteTeam(long team_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(team_id > 0) {
            return db.delete(TABLE_TEAM, COLUMN_TEAM_ID + " = ?",
                    new String[]{String.valueOf(team_id)});
        }
        else return 0;
    }

    //endregion TEAM METHODS

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //region PLAYER_TEAM METHODS

    public long createPlayerTeam(long player_id, long team_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PLAYER_ID, player_id);
        values.put(COLUMN_TEAM_ID, team_id);
        values.put(COLUMN_CREATED_DATE, getCurrentDateTime());

        long player_team_id = db.insert(TABLE_PLAYER_TEAM, null, values);

        return player_team_id;
    }

    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    //endregion PLAYER_TEAM METHODS

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //region GAME METHODS

    public long createGame(Game game) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_HOME_TEAM_ID, game.getHomeTeamId());
        values.put(COLUMN_AWAY_TEAM_ID, game.getAwayTeamId());
        values.put(COLUMN_LOCATION, game.getLocation());
        values.put(COLUMN_CREATED_DATE, getCurrentDateTime());

        // insert row
        long game_id = db.insert(TABLE_GAME, null, values);

        return game_id;
    }

    public Game getGame(long game_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_GAME + " WHERE "
                + COLUMN_GAME_ID + " = " + game_id;

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query, null);
        Game game = new Game();

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            game.setGameId((cursor.getLong(cursor.getColumnIndex(COLUMN_GAME_ID))));
            game.setHomeTeamId((cursor.getLong(cursor.getColumnIndex(COLUMN_HOME_TEAM_ID))));
            game.setAwayTeamId((cursor.getLong(cursor.getColumnIndex(COLUMN_AWAY_TEAM_ID))));
            game.setLocation((cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION))));
            game.setCreatedDate(cursor.getString(cursor.getColumnIndex(COLUMN_CREATED_DATE)));
        }

        return game;
    }

    public List<Game> getAllGames(long team_id) {
        List<Game> games = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_GAME
                + " WHERE " + COLUMN_HOME_TEAM_ID + " = " + team_id
                + " OR " + COLUMN_AWAY_TEAM_ID + " = " + team_id
                + " ORDER BY " + COLUMN_CREATED_DATE + " DESC";

        Log.e(LOG, query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //add all rows from database to list of teams
        if (cursor.moveToFirst()) {
            do {
                Game game = new Game();
                game.setGameId(cursor.getInt(cursor.getColumnIndex(COLUMN_GAME_ID)));
                game.setHomeTeamId(cursor.getInt(cursor.getColumnIndex(COLUMN_HOME_TEAM_ID)));
                game.setAwayTeamId(cursor.getInt(cursor.getColumnIndex(COLUMN_AWAY_TEAM_ID)));
                game.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));
                game.setCreatedDate(cursor.getString(cursor.getColumnIndex(COLUMN_CREATED_DATE)));
                games.add(game);
            } while (cursor.moveToNext());
        }

        return games;
    }

    public int updateGame(Game game) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        if(game.getGameId() > 0) {
            values.put(COLUMN_HOME_TEAM_ID, game.getHomeTeamId());
            values.put(COLUMN_AWAY_TEAM_ID, game.getAwayTeamId());
            values.put(COLUMN_LOCATION, game.getLocation());

            return db.update(TABLE_GAME, values, COLUMN_GAME_ID + " = ?",
                    new String[]{String.valueOf(game.getGameId())});
        }
        else return 0;
    }

    //endregion GAME METHODS

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //region ACTIVE_PLAYERS METHODS

    public long createActivePlayers(long game_id, List<Player> player_list) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;

        if(game_id > 0) {
            db.delete(TABLE_ACTIVE_PLAYERS, COLUMN_GAME_ID + " = ?", new String[]{String.valueOf(game_id)});
        }

        for (int index = 0; index < player_list.size(); index++) {
            values = new ContentValues();
            values.put(COLUMN_GAME_ID, game_id);
            values.put(COLUMN_PLAYER_ID, player_list.get(index).getId());
            db.insert(TABLE_ACTIVE_PLAYERS, null, values);
        }

        return game_id;
    }

    public List<Player> getActivePlayers(long game_id) {
        List<Player> players = new ArrayList<>();
        Player player;
        long player_id;

        String query = "SELECT *"
                + " FROM " + TABLE_ACTIVE_PLAYERS
                + " WHERE " + COLUMN_GAME_ID + " = " + game_id;

        Log.e(LOG, query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                player_id = cursor.getLong(cursor.getColumnIndex(COLUMN_PLAYER_ID));
                player = getPlayer(player_id);
                if (player.getId() > 0) {
                    players.add(player);
                }
            } while (cursor.moveToNext());
        }

        return players;
    }

//    boolean activePlayerExistsForGame(long game_id)
//    {
//        String query = "SELECT *"
//                + " FROM " + TABLE_ACTIVE_PLAYERS
//                + " WHERE " + COLUMN_GAME_ID + " = " + game_id;
//
//        Log.e(LOG, query);
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//
//        if (cursor.moveToFirst())
//            return true;
//        else
//            return false;
//    }

    //endregion ACTIVE_PLAYERS METHODS
}