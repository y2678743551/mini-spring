package org.springframework.beans.factory;

import java.util.ArrayList;
import java.util.List;
/*
 * @Date:2026/9/2
 * */
public class PropertyValues {
    private final List<PropertyValue> propertyValueList=new ArrayList<>();
    public void addPropertyValue(PropertyValue propertyValue){
        propertyValueList.add(propertyValue);
    }
    public PropertyValue getPropertyValue(String name){
        for (PropertyValue propertyValue : propertyValueList) {
            if (propertyValue.getName().equals(name)) {
                return propertyValue;
            }
        }
        return null;
    }
    public List<PropertyValue> getPropertyValueList() {
        return new ArrayList<>(propertyValueList);
    }
    public int size() {
        return propertyValueList.size();
    }
    public boolean isEmpty() {
        return propertyValueList.isEmpty();
    }

    public boolean contains(String name) {

        return this.getPropertyValue(name) != null;
    }
}
