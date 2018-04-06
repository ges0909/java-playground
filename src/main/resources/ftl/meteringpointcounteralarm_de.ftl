<#compress>

<#if scope=="subject">
	Metering Point Alarm: ${name}
</#if>

<#if scope=="body">
	<#if status=="on">
		Alarm für Metering Point ${name} wurde am ${date} um ${time} ausgelöst.
	<#elseif status=="off">
		Alarm für Metering Point ${name} wurde ${date} um ${time} gelöscht.
	</#if>
</#if>

</#compress>