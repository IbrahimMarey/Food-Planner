package com.example.foodplanner.model.dtos;

import java.io.Serializable;

public class IngredientDto implements Serializable {
    public String idIngredient;
    public String strIngredient;
    public String strDescription;
    public Object strType;

    public IngredientDto(){

    }
    public IngredientDto(String idIngredient, String strIngredient, String strDescription, Object strType) {
        this.idIngredient = idIngredient;
        this.strIngredient = strIngredient;
        this.strDescription = strDescription;
        this.strType = strType;
    }

    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public Object getStrType() {
        return strType;
    }

    public void setStrType(Object strType) {
        this.strType = strType;
    }
}
