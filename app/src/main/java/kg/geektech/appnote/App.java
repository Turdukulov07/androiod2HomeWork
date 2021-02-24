package kg.geektech.appnote;

import android.app.Application;

import androidx.room.Room;

import kg.geektech.appnote.room.AppDatabase;

public class App extends Application {
    private static AppDatabase database;

    public static AppDatabase getAppDatabase() {
        return database;
    }

    public static AppDatabase getDatabase() {
        return database;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        database = Room
                .databaseBuilder(this, AppDatabase.class, "name")
                .allowMainThreadQueries()
                .build();
    }
}
