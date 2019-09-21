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
import com.visual.geojson.geometry.Geometry;
import com.visual.geojson.geometry.Point;
import com.visual.geojson.object.Feature;
import com.visual.geojson.object.FeatureCollection;
import com.visual.geojson.object.IdentifiedFeature;

public class MinasCSVToJson {
	
	private static final String Point = "Point";
	
	public static void main(String[] args){
		MinasCSVToJson toJson = new MinasCSVToJson();
		
		toJson.createGeojsonFile("eventos_minas.json","Eventos_Minas_Antipersonal_en_Colombia.csv",Point,true,"AÑO","MES","TIPO_EVENTO","DEPARTAMENTO","MUNICIPIO","TIPO_AREA");
				
	}
		
	private void createGeojsonFile(String outFile, String inFile, String geometry, boolean withProperties, String... propertiesToShow) {
		ObjectMapper serializer = new ObjectMapper();
		FeatureCollection jsonCollection = this.createFeatureCollection(inFile,geometry,withProperties,propertiesToShow);		
		try {
			File jsonFile = new File(outFile);
			if(jsonFile.exists())
				jsonFile.delete();
			FileOutputStream jsonOut = new FileOutputStream(new File(outFile));
			serializer.writerWithDefaultPrettyPrinter().writeValue(jsonOut, jsonCollection);
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

	private FeatureCollection createFeatureCollection(String inFile, String geometry, boolean withProperties, String[] propertiesToShow) {
		
		FeatureCollection jsonCollection = new FeatureCollection();

		Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream(inFile));

		String propertiesStr = scanner.nextLine();
		String[] properties = propertiesStr.split(",");
		
		List<Feature> features = new ArrayList<Feature>();
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
				propertiesMap.put("MES_AÑO",propertiesMap.get("AÑO")+"-"+propertiesMap.get("MES"));
			}
			else
				feature.setProperties(new HashMap<>());

			features.add(feature);
		}
		jsonCollection.setFeatures(features);
		scanner.close();
		
		return jsonCollection;

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
