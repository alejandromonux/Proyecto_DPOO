package com.dpo.centralized_restaurant.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ConfigurationTableKey {

    @Column(name = "configuration_id")
    Long configuraitonId;

    @Column(name = "table_id")
    Long tableId;

    public ConfigurationTableKey(){}

    public ConfigurationTableKey(Long configuraitonId, Long tableId) {
        this.configuraitonId = configuraitonId;
        this.tableId = tableId;
    }

    public Long getConfiguraitonId() {
        return configuraitonId;
    }

    public void setConfiguraitonId(Long configuraitonId) {
        this.configuraitonId = configuraitonId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }
}
