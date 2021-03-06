package org.constellation.dto.metadata;

import java.io.Serializable;

/**
 * Pojo class that represents a group of users
 *
 * @author Mehdi Sidhoum (Geomatys).
 */
public class GroupBrief implements Serializable{
    private Integer id;
    private String name;

    public GroupBrief() {
    }

    public GroupBrief(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
