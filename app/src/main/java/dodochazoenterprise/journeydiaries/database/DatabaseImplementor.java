package dodochazoenterprise.journeydiaries.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dodochazoenterprise.journeydiaries.model.Journey;

/**
 * Created by Romain on 10/10/2017.
 */

public class DatabaseImplementor {

    Database mydb;
    SQLiteDatabase bdd;
    String table = "JOURNEY";

    public  DatabaseImplementor(Context context){
        mydb.getInstance(context);

    }

    public void open(){
        bdd = mydb.getWritableDatabase();
    }

    public List<Journey> getJourneys(){
        Cursor c = bdd.query(table, null, null, null, null, null, null);
        return cursorToJourneys(c);
    }


    public long create(Journey journey){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put("1", journey.getName());
        values.put("2", convertCalendarToString(journey.getFrom()));
        values.put("3", convertCalendarToString(journey.getTo()));
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(table, null, values);
    }

    public int update(int id, Journey journey){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        //values.put("0",journey.getId());
        values.put("1", journey.getName());
        values.put("2", convertCalendarToString(journey.getFrom()));
        values.put("2", convertCalendarToString(journey.getTo()));
        return bdd.update(table, values, "Id" + " = " +id, null);
    }


    //Cette méthode permet de convertir un cursor en un livre
    private List<Journey> cursorToJourneys(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return new ArrayList<>();

        List<Journey> journeys = new ArrayList<>();
        //Sinon on se place sur le premier élément
        c.moveToFirst();

        do {
            Journey j = new Journey();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor

            //j.setId(c.getInt(0));
            j.setName(c.getString(1));

            j.setFrom(convertStringToCalendar(c.getString(2)));
            j.setTo(convertStringToCalendar(c.getString(3)));

            journeys.add(j);
        } while (c.moveToNext());

        //On ferme le cursor
        c.close();

        //On retourne le livre
        return journeys;
    }

    private String convertCalendarToString(Calendar cal){
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            return format.format(cal.getTime());
    }

    private Calendar convertStringToCalendar(String date){
        Calendar cal = Calendar.getInstance();

        cal.setTime(new Date(date));
        return cal;
    }
}


