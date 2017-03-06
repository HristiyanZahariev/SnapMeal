package com.snapmeal.service;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by hristiyan on 28.02.17.
 */
@XmlRootElement
public class UserSearchTags {
    @XmlElement(name = "id")
    private long id;
    @XmlElement(name = "name")
    private String name;
}
