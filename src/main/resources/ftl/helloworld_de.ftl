FreeMarker Vorlage Beispiel: ${message}  

=======================
== Liste der L�nder  ==
=======================
<#list countries as country>
	${country_index + 1}. ${country}
</#list>