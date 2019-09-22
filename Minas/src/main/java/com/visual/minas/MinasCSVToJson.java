package com.visual.minas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.axes.HasPositionalPredChecker;
import com.visual.geojson.geometry.Geometry;
import com.visual.geojson.geometry.Point;
import com.visual.geojson.object.Feature;
import com.visual.geojson.object.FeatureCollection;
import com.visual.geojson.object.IdentifiedFeature;

public class MinasCSVToJson {
	
	private static final String Point = "Point";
	private HashMap<String,String> types = new HashMap<>();
	private HashMap<String,FeatureCollection> featureCollections = new HashMap<>();

	public MinasCSVToJson(){
		this.init();
	}

	private void init() {
		this.types.put("Accidente por MAP","1");
		this.types.put("Accidente por MUSE","2");
		this.types.put("Arsenal almacenada","3");
		this.types.put("Desminado militar en operaciones","4");
		this.types.put("Incautaciones","5");
		this.types.put("Municiones sin explotar","6");
		this.types.put("Producción de Minas (Fábrica)","7");
		this.types.put("Sospecha de campo minado","8");
	}

	public static void main(String[] args){
		MinasCSVToJson toJson = new MinasCSVToJson();
		
		toJson.createGeojsonFiles("Eventos_Minas_Antipersonal_en_Colombia.csv",Point,true,
				"AÑO","TIPO_EVENTO","DEPARTAMENTO","MUNICIPIO","TIPO_AREA");
				
	}
		
	private void createGeojsonFiles(String inFile, String geometry, boolean withProperties, String... propertiesToShow) {

		for(int i = 1990; i <= 2019; i++)
		{
			FeatureCollection jsonCollection = new FeatureCollection();
			List<Feature> features = new ArrayList<Feature>();
			jsonCollection.setFeatures(features);
			featureCollections.put(i+"",jsonCollection);
		}

		this.createFeatureCollections(inFile,geometry,withProperties,propertiesToShow);

		ArrayList<String> years = new ArrayList<>(this.featureCollections.keySet());
		for(String year: years)
		{
			this.saveFile(year+".json",this.featureCollections.get(year));
		}
	}

	private void saveFile(String outFile, FeatureCollection featureCollection) {
		try {
			File jsonFile = new File(outFile);
			if(jsonFile.exists())
				jsonFile.delete();
			FileOutputStream jsonOut = new FileOutputStream(new File(outFile));
			ObjectMapper serializer = new ObjectMapper();
			serializer.writerWithDefaultPrettyPrinter().writeValue(jsonOut, featureCollection);
			jsonOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createFeatureCollections(String inFile, String geometry, boolean withProperties, String[] propertiesToShow) {

		Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream(inFile));

		String propertiesStr = scanner.nextLine();
		String[] properties = propertiesStr.split(",");

		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			String[] values = line.split(",");
			Map<String, Serializable> propertiesMap = this.createFeatureProperties(properties, values);
			
			Geometry geometryObj = null;
			if(geometry.equals(Point))
			{
				String latitud = values[10];
				String longitud = values[11];
				geometryObj = new Point(Double.parseDouble(longitud.replace("'", "")),Double.parseDouble(latitud.replace("'", "")));
			}
			Feature feature = new Feature(geometryObj);
			
			if(withProperties) {
				if(propertiesToShow.length > 0)
					propertiesMap = this.filterProperties(propertiesToShow,propertiesMap);
				feature.setProperties(propertiesMap);
				propertiesMap.put("TIPO_EVENTO",this.types.get(propertiesMap.get("TIPO_EVENTO")));
			}
			else
				feature.setProperties(new HashMap<>());

			this.featureCollections.get(propertiesMap.get("AÑO")).getFeatures().add(feature);
		}
		scanner.close();
	}

	private Map<String, Serializable> filterProperties(String[] propertiesToShow,
			Map<String, Serializable> propertiesMap) {
		Map<String, Serializable> newPropertiesMap = new HashMap<>();
		for(String property: propertiesToShow) {
			newPropertiesMap.put(property, propertiesMap.get(property));
		}
		return newPropertiesMap;
	}

	private Map<String, Serializable> createFeatureProperties(String[] properties, String[] values) {
		Map<String, Serializable> propertiesMap = new HashMap<>();
		for (int i = 0; i < properties.length; i++) {
			if(values.length <= i)
				propertiesMap.put(properties[i], "");
			else
				propertiesMap.put(properties[i], values[i].replace("'", ""));
		}
		return propertiesMap;
	}

}
