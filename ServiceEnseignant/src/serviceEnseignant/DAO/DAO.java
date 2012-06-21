package serviceEnseignant.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.util.GregorianCalendar;

import connection.ConnectionOracle;

public abstract class DAO<T> {
	public Connection connect = ConnectionOracle.getInstance();
	
	/**
	 * Permet de r�cup�rer un objet via son ID
	 * @param id
	 * @return
	 */
	public abstract T find(int id);
	
	/**
	 * Permet de cr�er une entr�e dans la base de donn�es
	 * par rapport � un objet
	 * @param obj
	 */
	public abstract T create(T obj);
	
	/**
	 * Permet de mettre � jour les donn�es d'une entr�e dans la base 
	 * @param obj
	 */
	public abstract T update(T obj);
	
	/**
	 * Permet la suppression d'une entr�e de la base
	 * @param obj
	 */
	public abstract void delete(T obj);
	
	/**
	 * Permet de convertir un GregorianCalendar en date sous Oracle
	 * @param date
	 */
	public static String dateFromJavaToOracle(GregorianCalendar date){
		int year = date.get(GregorianCalendar.YEAR);
		int month = date.get(GregorianCalendar.MONTH);
		int day = date.get(GregorianCalendar.DAY_OF_MONTH);
		int hour = date.get(GregorianCalendar.HOUR_OF_DAY);
		int minute = date.get(GregorianCalendar.MINUTE);
		int second = date.get(GregorianCalendar.SECOND);
		return "TO_DATE('"+day+"/"+month+"/"+year+" "+hour+":"+minute+":"+second+"','DD/MM/YYYY HH24:MI:SS')";
	}
	
	/**
	 * Permet de convertir une date sous Oracle en un GregorianCalendar
	 * @param date
	 */
	public static GregorianCalendar dateFromOracleToJava(Date date){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		return gc;		
	}
	
	/**
	 * Permet de convertir un booleen en entier sous Oracle
	 * @param b
	 */
	public static int booleanFromJavaToOracle(boolean b){
		return b?1:0;
	}
	
	/**
	 * Permet de convertir un entier en booleen sous Oracle
	 * @param i
	 */
	public static boolean booleanFromOracleToJava(int i){
		return (i==1)?true:false;
	}

}
