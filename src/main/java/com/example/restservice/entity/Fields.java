package com.example.restservice.entity;

import com.example.restservice.entity.picture.Picture;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Fields{
    @JsonProperty("Name") 
    public String name;
    @JsonProperty("Picture") 
    public List<Picture> picture;
    @JsonProperty("Type")
    public String type;
    @JsonProperty("Tags")
    public List<String> tags;
}
