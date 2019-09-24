# El Centro, norte y occidente de Colombia las zonas más afectadas por las minas antipersonales.
## URL visualización: https://observablehq.com/@hpuentes/el-centro-norte-y-occidente-de-colombia-las-zonas-mas-afecta
Colombia ha sido afectado por eventos relacionados con minas antipersonales durante muchos años, dentro de esta visualización se presenta la distribución geográfica de minas por año, desde 1990 hasta 2019. 
## Objetivo
El principal objetivo es identificar los puntos foco y por cada uno de estos entender como es la distribución por tipo de evento, por ejemplo un accidente MAP, un accidente MUSE, incautación de arsenal almacenado, etc. Las zonas donde se observa mayor afectación en los diferentes años ha sido la zona centro, occidente y norte de Colombia, zonas donde se los grupos al margen de la ley centran la mayoría de actividades insurgentes.
## Autor 
Hermes Puentes Navarro https://www.linkedin.com/in/hermes-puentes-navarro-1898b2b3/

Combining code from Bård Romstad´s block http://bl.ocks.org/gisminister/10001728
### Fuente: Datos Abiertos Bogotá
###  Eventos Minas Antipersonal en Colombia.
Entidad: Departamento Administrativo de la Presidencia de la República.
Area o dependencia: Oficina del Alto Comisionado para la Paz - Descontamina Colombia.
Base de datos con los eventos por presencia o sospecha de presencia de minas antipersonal (MAP), artefactos explosivos improvisado (AEI) con características de minas antipersonal y municiones sin explotar (MUSE) en Colombia en el periodo comprendido entre 1990 al mes anterior de la fecha de corte (Septiembre 2019).
https://www.datos.gov.co/Inclusi-n-Social-y-Reconciliaci-n/Eventos-Minas-Antipersonal-en-Colombia/sgp4-3e6k
Descripción detallada de los datos: http://www.accioncontraminas.gov.co/estadisticas/Paginas/Bases-de-Datos.aspx
Los datos descargados de la fuente de Datos Abiertos Bogotá fueron preparados para construir construir una fuente geojson que cumple con el siguiente formato:

{ "type" : "FeatureCollection", "features" :
[ 

{ "type" : "Feature",
"properties" : { "TIPO_EVENTO" : "1", "DEPARTAMENTO" : "ANTIOQUIA", "TIPO_AREA" : "Rural", "AÑO" : "2011", "MUNICIPIO" : "ANORÍ" }, "geometry" : { "type" : "Point", "coordinates" : [ -75.06333333, 7.2925 ] } },

{ "type" : "Feature", "properties" : { "TIPO_EVENTO" : "1", "DEPARTAMENTO" : "CAUCA", "TIPO_AREA" : "Rural", "AÑO" : "2011", "MUNICIPIO" : "MIRANDA" }, "geometry" : { "type" : "Point", "coordinates" : [ -76.07583333, 3.197777778 ] } } 
...
...
] }

Donde por cada punto se cuenta con la siguiente información:

TIPO_EVENTO: {"1": "Accidente por MAP(Minas Antipersonal)", "2": "Accidente por MUSE(Municiones sin explotar)", "3": "Arsenal almacenada", "4": "Desminado militar en operaciones","5": "Incautaciones", "6":"Municiones sin explotar","7":"Producción de Minas (Fábrica)","8":"Sospecha de campo minado"}

Tipo area: Rural o urbana.

Año: Año del evento.

Municipio: Municipio del evento.

El desarrollo de preparación de datos se hizo en java y se encuentra en github: https://github.com/hpuentes/eventos-minas-antipersonas-colombia/tree/master/Minas

## Tarea Principal.
T1. Presentar la distribución geográfica de los eventos de minas antipersona en el territorio Colombiano.
(Present)-(SpatialData)

## Tareas secundarias.
T2. Presentar la distribución geográfica de los eventos de minas antipersona por año.
(Present)-(SpatialData)

T3. Comparar los diferentes clusters de eventos de minas antipersona según tipo de eventos.
(Compare)-(Distribution) 

## WHAT?
Attributes:

* Ubicación espacial del evento mina antipersona: Geographic geometry data.
* Año: Ordered, Quatitative sequential.
* Tipo de evento antipersona: Categorical.

## WHY?
* Presentar la distribución geográfica de los eventos de minas antipersona en el territorio Colombiano.
(Present)-(SpatialData)
* Presentar la distribución geográfica de los eventos de minas antipersona por año.
(Present)-(SpatialData)
* Comparar los diferentes clusters de eventos de minas antipersona según tipo de eventos.
(Compare)-(Distribution) 

## HOW?
* Space: Ubicación geográfica de los eventos de mina antipersona.
* Select: Selecciona año de los eventos de mina antipersona, selecciona cada fragmento de pie (Tipo de evento por cluster) para obtener detalles.
* Embed: Por cada barra de rango de edad se fragmenta por tipo de evento de mina antipersona.
Area marks with angle channel: Pie chart para presentar la cantidad eventos por cada tipo en cada cluster.

### Channels:
* HUE: Colores para cada tipo de evento.
* Area: Pie por fragmentos por cada tipo de evento.

## Eventos o incidentes relacionados con minas anti persona en el territorio nacional por año seleccionado (De 1990 a 2019).
Se debe seleccionar el combobox para cambiar la vista de los eventos de mina antipersona según el año seleccionado. Tooltip de la derecha indica los colores del pie chart de cada cluster relacionado a un tipo de evento de mina antipersona. Se presentan clusters por diferentes zonas geográficas del territorio Colombiano donde se agrupan los eventos de mina antipersona por cercania. 

![Eventos de mina antipersona](https://github.com/hpuentes/eventos-minas-antipersonas-colombia/blob/master/minas.png?raw=true)

## Conclusiones

Las minas antipersona han estado presentes dentro del territorio Colombiano a lo largo de muchos años, estos eventos en su mayoría se han presentado en la zona central, occidental y norte del país, zonas donde los grupos al margen de la ley han tenido mayor actividad insurgente.Se evidencia durante los últimos años una alta actividad de desminado militar, sin embargo sigue presentandose accidentes MAP(Minas Antipersonal) y MUSE(Munición sin explotar), situación que pone en riesgo a la población civil y al ejercito nacional.

## Tecnologías usadas
* D3@5
* Javascript
* Java
* Geojson
* leaflet_markercluster
* leaflet JS
* Despliegue en el sitio observablehq.
