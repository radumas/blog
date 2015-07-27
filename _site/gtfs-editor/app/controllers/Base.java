package controllers;

import play.*;
import play.mvc.*;
import play.data.binding.As;
import play.db.jpa.JPA;
import utils.JacksonSerializers;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Query;

import static java.util.Collections.sort;

import org.geotools.geometry.jts.JTS;
import org.joda.time.LocalDate;
import org.mapdb.Fun.Tuple2;
import org.opengis.referencing.operation.MathTransform;
import org.python.google.common.collect.Collections2;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.Function;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.linearref.LengthLocationMap;
import com.vividsolutions.jts.linearref.LinearLocation;
import com.vividsolutions.jts.linearref.LocationIndexedLine;

import models.*;
import models.transit.Agency;
import models.transit.Route;
import models.transit.RouteType;
import models.transit.ScheduleException;
import models.transit.ServiceCalendar;
import models.transit.Stop;
import models.transit.StopTime;
import models.transit.Trip;
import models.transit.TripPattern;
import models.transit.TripPatternStop;

public class Base {
    public static ObjectMapper mapper = new ObjectMapper();
    private static JsonFactory jf = new JsonFactory();
    
    static {
    	SimpleModule mod = new SimpleModule();
    	mod.addDeserializer(LineString.class, new JacksonSerializers.EncodedPolylineDeserializer());
    	mod.addSerializer(LineString.class, new JacksonSerializers.EncodedPolylineSerializer());
    	mod.addDeserializer(LocalDate.class, new JacksonSerializers.LocalDateDeserializer());
    	mod.addSerializer(LocalDate.class, new JacksonSerializers.LocalDateSerializer());
    	mapper.registerModule(mod);
    }

    public static String toJson(Object pojo, boolean prettyPrint)
            throws JsonMappingException, JsonGenerationException, IOException {
                StringWriter sw = new StringWriter();
                JsonGenerator jg = jf.createJsonGenerator(sw);
                if (prettyPrint) {
                    jg.useDefaultPrettyPrinter();
                }
                mapper.writeValue(jg, pojo);
                return sw.toString();
    }
}