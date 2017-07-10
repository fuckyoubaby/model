package com.changhong.common.domain;

/**
 * User: Jack Wang
 * Date: 16-9-26
 * Time: 下午2:09
 */
public class SelectOption implements Option {

    private String label;

    private String value;

    public SelectOption(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }
}
