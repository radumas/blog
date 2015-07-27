package controllers;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Date;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;

import datastore.MigrateToMapDB;
import org.geotools.geometry.jts.JTS;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Coordinate;

import static java.util.Collections.sort;
import controllers.Secure.Security;
import datastore.VersionedDataStore;
import datastore.GlobalTx;
import models.Account;
import models.transit.Agency;
import models.transit.GtfsRouteType;
import models.transit.Route;
import models.transit.Stop;
import models.transit.StopTime;
import models.transit.Trip;
import models.transit.TripPattern;
import models.transit.TripPatternStop;
import play.Logger;
import play.Play;
import play.mvc.*;
import play.data.validation.*;
import play.data.validation.Validation.ValidationResult;
import play.libs.*;
import play.utils.*;

public class Bootstrap extends Controller {

    public static void index() {
    	GlobalTx tx = VersionedDataStore.getGlobalTx();
    	
    	try {
	    	if (tx.accounts.size() == 0)
	    		Bootstrap.adminForm();
	    	
	    	else if(tx.agencies.size() == 0)
	    		Bootstrap.agencyForm();
	    	
	    	else 
	    		Application.index();
    	}
    	finally {
    		tx.rollback();
    	}
    }
    
    public static void adminForm() {
    	GlobalTx tx = VersionedDataStore.getGlobalTx();
    	
    	try {
    		if(tx.accounts.size() > 0)
    			Bootstrap.agencyForm();
    	
    		render();
    	}
    	finally {
    		tx.rollback();
    	}    	
    }
    
    public static void createAdmin(String username, String password, String password2, String email) throws Throwable {
    	GlobalTx tx = VersionedDataStore.getGlobalTx();
        
    	try {
	    	if(tx.accounts.size() > 0 && !Play.configuration.getProperty("application.allowBootstrapAdminCreate").equals("true"))
	    		Bootstrap.index();
	    	
	    	validation.required(username).message("Username cannot be blank.");
	    	validation.required(password).message("Password cannot be blank.");
	    	validation.equals(password, password2).message("Passwords do not match.");
	    	
	    	if(validation.hasErrors()) {
	    		params.flash();
	    		validation.keep();
	            adminForm();
	            return;
	        }
	       	
	    	if (tx.accounts.containsKey(username)) {
	    		badRequest();
	    		adminForm();
	    		return;
	    	}
	    	
	    	Account acct = new Account(username, password, email, true, null);
	    	tx.accounts.put(acct.id, acct);
	    	tx.commit();
    	} finally {
    		tx.rollbackIfOpen();
    	}
	    	
    	Bootstrap.index();
    }
    
    public static void agencyForm() {
    	GlobalTx tx = VersionedDataStore.getGlobalTx();
    	
    	try {
	    	if (tx.accounts.size() == 0)
	    		Bootstrap.adminForm();
	    	
	    	if (tx.agencies.size() > 0)
	    		Application.index();
    	} finally {
    		tx.rollback();
    	}
	    	
    	render();
    }
    
    public static void createAgency( String gtfsId, String name, String url, @Required String timezone, @Required String language, String phone, Double defaultLat, Double defaultLon) throws Throwable {
    	GlobalTx tx = VersionedDataStore.getGlobalTx();
    	
    	try {
	    	if(tx.agencies.size() > 0)
	    		Bootstrap.index();
	    	
	    	validation.required(gtfsId).message("Agency GTFS ID cannot be blank.");
	    	validation.required(name).message("Agency name cannot be blank.");
	    	validation.required(url).message("Agency URL cannot be blank.");
	    	
	    	if(validation.hasErrors()) {
	    		params.flash();
	    		validation.keep();
	    		agencyForm();
	        }
	    	
	    	Agency agency = new Agency(gtfsId, name, url, timezone, language, phone);
	    	agency.generateId();
	    	
	    	agency.defaultLat = defaultLat;
	    	agency.defaultLon = defaultLon;
	    	
	    	tx.agencies.put(agency.id, agency);
	    	tx.commit();
    	} finally {
    		tx.rollbackIfOpen();
    	}
    	
    	Bootstrap.index();
    }

	/**
	 * Migrate a Postgres database dump. This is a bit on the hacky side. The dump must live in the dump/ subdirectory
	 * of the application working directory. This is not a strict Postgres db dump, but rather one produced by the
	 * export-database.sql script, which makes a bunch of CSV's of the tables.
	 */
	/*
	public static void migrate () {
		GlobalTx gtx = VersionedDataStore.getGlobalTx();
		if (!gtx.accounts.isEmpty()) {
			badRequest();
			return;
		}

		try {
			new MigrateToMapDB().migrate(new File("dump"));
			ok();
		} catch (Exception e) {
			e.printStackTrace();
			badRequest();
		}
	}*/
}

