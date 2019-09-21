
package com.visual.geojson.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class FeaturePropertiesDeserializer extends JsonDeserializer<Map<String, Serializable>>{

	@Override
	public Map<String, Serializable> deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return new HashMap<>();
	}
	
}
