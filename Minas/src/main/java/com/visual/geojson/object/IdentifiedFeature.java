package com.visual.geojson.object;

import com.visual.geojson.geometry.Geometry;

public class IdentifiedFeature extends Feature {

	private long id;

	public IdentifiedFeature() {
	}

	public IdentifiedFeature(Geometry geometry, long identifier) {
		super(geometry);
		id = identifier;
	}

	public long getId() {
		return id;
	}

}
