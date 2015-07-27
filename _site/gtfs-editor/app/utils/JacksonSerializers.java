package utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.google.common.io.BaseEncoding;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.mapdb.Fun.Tuple2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class JacksonSerializers {
	private static final BaseEncoding encoder = BaseEncoding.base64Url();

	public static class Tuple2Serializer extends StdScalarSerializer<Tuple2<String, String>> {
		public Tuple2Serializer () {
			super(Tuple2.class, true);
		}
		
		@Override
		public void serialize(Tuple2<String, String> t2, JsonGenerator jgen,
				SerializerProvider arg2) throws IOException,
				JsonProcessingException {
			jgen.writeString(serialize(t2));
		}
		
		public static String serialize (Tuple2<String, String> t2) {
			try {
				return encoder.encode(t2.a.getBytes("UTF-8")) + ":" + encoder.encode(t2.b.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}
	
	public static class Tuple2Deserializer extends StdScalarDeserializer<Tuple2<String, String>> {
		public Tuple2Deserializer () {
			super(Tuple2.class);
		}
		
		@Override
		public Tuple2<String, String> deserialize(JsonParser jp,
				DeserializationContext arg1) throws IOException,
				JsonProcessingException {			
			return deserialize(jp.readValueAs(String.class));
		}
		
		public static Tuple2<String, String> deserialize (String serialized) throws IOException {
			String[] val = serialized.split(":");
			if (val.length != 2) {
				throw new IOException("Unable to parse value");
			}
			
			return new Tuple2<String, String>(new String(encoder.decode(val[0]), "UTF-8"), new String(encoder.decode(val[1]), "UTF-8"));
		}
	}
	
	public static class Tuple2IntSerializer extends StdScalarSerializer<Tuple2<String, Integer>> {
		public Tuple2IntSerializer () {
			super(Tuple2.class, true);
		}
		
		@Override
		public void serialize(Tuple2<String, Integer> t2, JsonGenerator jgen,
				SerializerProvider arg2) throws IOException,
				JsonProcessingException {
			jgen.writeString(serialize(t2));
		}
		
		public static String serialize (Tuple2<String, Integer> t2) {
			try {
				return encoder.encode(t2.a.getBytes("UTF-8")) + ":" + t2.b.toString();
			} catch (UnsupportedEncodingException e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}
	
	public static class Tuple2IntDeserializer extends StdScalarDeserializer<Tuple2<String, Integer>> {
		public Tuple2IntDeserializer () {
			super(Tuple2.class);
		}
		
		@Override
		public Tuple2<String, Integer> deserialize(JsonParser jp,
				DeserializationContext arg1) throws IOException,
				JsonProcessingException {			
			return deserialize(jp.readValueAs(String.class));
		}
		
		public static Tuple2<String, Integer> deserialize (String serialized) throws IOException {
			String[] val = serialized.split(":");
			if (val.length != 2) {
				throw new IOException("Unable to parse value");
			}
			
			return new Tuple2<String, Integer>(new String(encoder.decode(val[0]), "UTF-8"), Integer.parseInt(val[1]));
		}
	}
	
	/** serialize a JTS linestring as an encoded polyline */
	public static class EncodedPolylineSerializer extends StdScalarSerializer<LineString> {
		public EncodedPolylineSerializer() {
			super(LineString.class, false);
		}

		@Override
		public void serialize(LineString geom, JsonGenerator jgen,
				SerializerProvider arg2) throws IOException,
				JsonGenerationException {
			jgen.writeString(PolylineEncoder.createEncodings(geom).getPoints());
		}
	}
	
	/** deserialize an encoded polyline to a JTS linestring */
	public static class EncodedPolylineDeserializer extends StdScalarDeserializer<LineString> {
		private GeometryFactory geometryFactory = new GeometryFactory();
		
		public EncodedPolylineDeserializer () {
			super(LineString.class);
		}

		@Override
		public LineString deserialize(JsonParser jp,
				DeserializationContext arg1) throws IOException,
				JsonProcessingException {
			//
			List<Coordinate> coords = PolylineEncoder.decode(new EncodedPolylineBean(jp.getValueAsString(), null, 0));
			return geometryFactory.createLineString(coords.toArray(new Coordinate[coords.size()]));
		}
	}
	
	/** serialize local dates as noon GMT epoch times */
	public static class LocalDateSerializer extends StdScalarSerializer<LocalDate> {
		public LocalDateSerializer() {
			super(LocalDate.class, false);
		}
		
		@Override
		public void serialize(LocalDate ld, JsonGenerator jgen,
				SerializerProvider arg2) throws IOException,
				JsonGenerationException {
			jgen.writeNumber(ld.toDateTime(new LocalTime(12, 0, 0), DateTimeZone.UTC).getMillis());
		}
	}
	
	/** deserialize local dates from GMT epochs */
	public static class LocalDateDeserializer extends StdScalarDeserializer<LocalDate> {
		public LocalDateDeserializer () {
			super(LocalDate.class);
		}

		@Override
		public LocalDate deserialize(JsonParser jp,
				DeserializationContext arg1) throws IOException,
				JsonProcessingException {
			return new LocalDate(jp.getLongValue(), DateTimeZone.UTC);
		}	
	}

	public static final DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");

	/** Serialize a local date to an ISO date (year-month-day) */
	public static class LocalDateIsoSerializer extends StdScalarSerializer<LocalDate> {
		public LocalDateIsoSerializer () {
			super(LocalDate.class, false);
		}

		@Override
		public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
			jsonGenerator.writeString(localDate.toString(format));
		}
	}

	/** Deserialize an ISO date (year-month-day) */
	public static class LocalDateIsoDeserializer extends StdScalarDeserializer<LocalDate> {
		public LocalDateIsoDeserializer () {
			super(LocalDate.class);
		}

		@Override
		public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
			return LocalDate.parse(jsonParser.getValueAsString(), format);
		}

	}
}
