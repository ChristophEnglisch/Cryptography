package de.cenglisch.cryptography.configuration;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "configuration_setting")
public class ConfigurationSetting {
    @Id
    private String id;
    private String value;

    public ConfigurationSetting() {
    }

    public ConfigurationSetting(String id, boolean value) {
        this.id = id;
        this.value = String.valueOf(value);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getValue() {
        return Boolean.parseBoolean(value);
    }

    public void setValue(boolean value) {
        this.value = String.valueOf(value);
    }
}
