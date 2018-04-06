<#compress>

<#if scope=="subject">
	Metering Point Alarm: ${name}
</#if>

<#if scope=="body">
	<#if status=="on">
		Alarm for Metering Point ${name} is raised on ${date} at ${time}.
	<#elseif status=="off">
		Alarm for Metering Point ${name} is cleared on ${date} at ${time}.
	</#if>
</#if>

</#compress>