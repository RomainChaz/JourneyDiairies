package dodochazoenterprise.journeydiaries.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
    String id="id", name="name", dateFrom="dateFrom", dateTo="dateTo";

    public  DatabaseImplementor(Context context){
        mydb = Database.getInstance(context);
        bdd = mydb.getWritableDatabase();
    }

    public List<Journey> getJourneys(){
        Cursor c = null;
        bdd.beginTransaction();
        try{
        c = bdd.query(table, null, null, null, null, null, null);
            bdd.setTransactionSuccessful();
        }catch(Exception e){
            Log.e("BDD", "Impossible de récupérer les journées");
        }finally {
            bdd.endTransaction();
        }

        return cursorToJourneys(c);
    }


    public long create(Journey journey){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        long inserted = 0;
        bdd.beginTransaction();
        try{
            //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
            values.put(name, journey.getName());
            values.put(dateFrom, convertCalendarToString(journey.getFrom()));
            values.put(dateTo, convertCalendarToString(journey.getTo()));
            //on insère l'objet dans la BDD via le ContentValues
            inserted = bdd.insert(table, null, values);
            bdd.setTransactionSuccessful();
        }catch(Exception e){
            Log.e("BDD", "Impossible d'ajouter");
        }finally {
            bdd.endTransaction();
        }

        return inserted;
    }

    public int update(int id, Journey journey){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        bdd.beginTransaction();
        int updated=0;
        try{
            ContentValues values = new ContentValues();
            values.put(this.id,journey.getId());
            values.put(name, journey.getName());
            values.put(dateFrom, convertCalendarToString(journey.getFrom()));
            values.put(dateTo, convertCalendarToString(journey.getTo()));
            updated = bdd.update(table, values, "Id" + " = " + id, null);
            bdd.setTransactionSuccessful();
        }catch(Exception e){
            Log.e("BDD", "Impossible d'ajouter");
        }finally {
            bdd.endTransaction();
        }

        return updated;
    }

    public int delete(int id){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        bdd.beginTransaction();
        int deleted=0;
        try{
            deleted = bdd.delete(table,  "Id" + " = " + id, null);
            bdd.setTransactionSuccessful();
        }catch(Exception e){
            Log.e("BDD", "Impossible d'ajouter");
        }finally {
            bdd.endTransaction();
        }

        return deleted;
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
            int col=0;
            j.setId(c.getInt(col++));
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


