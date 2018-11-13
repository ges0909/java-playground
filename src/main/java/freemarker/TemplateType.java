package freemarker;

public enum TemplateType {
    MeteringPointCounter_Alarm("meteringpointcounteralarm");

    String value;

    private TemplateType(String value) {
        this.value = value;
    }

    public String value() {
        return this.value + ".ftl";
    }
}