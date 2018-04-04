FreeMarker Vorlage Beispiel: ${message}  

=======================
== Liste der Länder  ==
=======================
<#list countries as country>
	${country_index + 1}. ${country}
</#list>