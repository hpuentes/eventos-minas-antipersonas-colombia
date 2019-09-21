package com.visual.geojson.util;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.visual.geojson.geometry.GeometryCollection;
import com.visual.geojson.geometry.LineString;
import com.visual.geojson.geometry.MultiLineString;
import com.visual.geojson.geometry.MultiPoint;
import com.visual.geojson.geometry.MultiPolygon;
import com.visual.geojson.geometry.Point;
import com.visual.geojson.geometry.Polygon;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
	@JsonSubTypes.Type(value = Point.class, name = "Point"),
	@JsonSubTypes.Type(value = MultiPoint.class, name = "MultiPoint"),
	@JsonSubTypes.Type(value = LineString.class, name = "LineString"),
	@JsonSubTypes.Type(value = MultiLineString.class, name = "MultiLineString"),
	@JsonSubTypes.Type(value = Polygon.class, name = "Polygon"),
	@JsonSubTypes.Type(value = MultiPolygon.class, name = "MultiPolygon"),
	@JsonSubTypes.Type(value = GeometryCollection.class, name = "GeometryCollection")})
public class GeometryMixin {

}
