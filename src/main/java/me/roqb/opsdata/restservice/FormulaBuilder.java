package me.roqb.opsdata.restservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormulaBuilder {

    private Map<String, String> variables = new HashMap<>();
    private List<ValuePair> pairs = new ArrayList<>();
    private int count = 0;

    private String formulaFormat = "AND(%s)";

    StringBuilder builder = new StringBuilder();

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    private class ValuePair {
        protected String dbLabel;
        protected String value;
    }

    public FormulaBuilder() {

    }

    public FormulaBuilder(String formulaFormat) {
        this.formulaFormat = formulaFormat;
    }

    /**
     * Appends the labels and values to a string builder and map which
     * will then be appended to the uri and replaced by the RestTemplate.
     *
     * @param dbLabel - ex: 'First Savior Function' <- known by service
     * @param value - ex: 'Se' <- input to service
     */
    public void append(String dbLabel, String value) {
        if (StringUtils.hasLength(value)) {
            String countLabel = count + "Label";
            String countValue = count + "Value";
            if (count > 0) {
                this.builder.append(",");
            }
            this.builder.append("{")
                    .append(countLabel)
                    .append("}='{")
                    .append(countValue)
                    .append("}'");
            this.variables.put(countLabel, "{" + dbLabel + "}");
            this.variables.put(countValue, value);
            count++;
        }
    }

    public String buildFormula() {
        if (count > 1) {
            return String.format(formulaFormat, builder.toString());
        } else {
            return builder.toString();
        }
    }

    public Map<String, String> getVariables() {
        return this.variables;
    }

}
