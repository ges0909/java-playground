package freemarker;

public enum TemplateType {
    MeteringPointCounter_Alarm("metering-point-counter-alarm");

    String value;

    TemplateType(String value) {
        this.value = value;
    }

    public String value() {
        return this.value + ".ftl";
    }
}
