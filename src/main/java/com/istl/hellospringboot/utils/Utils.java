/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istl.hellospringboot.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class Utils {
    public static final Integer RECORD_STATUS_ONE = 1;
    public static final String OPERATION_SUCESS = "SUCCESS";
    public static final String OPERATION_CREATE = "CREATE";
    public static final String OPERATION_UPDATE = "UPDATE";
    public static final String OPERATION_FAILED = "FAILED";
    public static final Integer DEFAULT_LIMIT = 100;
    
    public static void copyBean(final Object from, final Object to) {
        Map<String, Field> fromFields = analyze(from);
        Map<String, Field> toFields = analyze(to);
        fromFields.keySet().retainAll(toFields.keySet());
        fromFields.entrySet().forEach((fromFieldEntry) -> {
            final String name = fromFieldEntry.getKey();
            final Field sourceField = fromFieldEntry.getValue();
            final Field targetField = toFields.get(name);
            if (targetField.getType().isAssignableFrom(sourceField.getType())) {
                sourceField.setAccessible(true);
                if (!(Modifier.isFinal(targetField.getModifiers()))) {
                    targetField.setAccessible(true);
                    try {
                        Object ob = sourceField.getType().equals(String.class)                                
                                && sourceField.get(from) != null
                                && !isEmpty(((String) sourceField.get(from)).trim()) ?
                                ((String) sourceField.get(from)).trim() : sourceField.get(from);
                        targetField.set(to, ob);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException("Can't access field!");
                    }
                }
            }
        });
    }
    
    private static Map<String, Field> analyze(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }
        Map<String, Field> map = new TreeMap<>();
        Class<?> current = object.getClass();
        while (current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    if (!map.containsKey(field.getName())) {
                        map.put(field.getName(), field);
                    }
                }
            }
            current = current.getSuperclass();
        }

        return map;
    }
    
    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }
    
    public static boolean isInList(Integer value,Integer ...cases) {
        if(value == null ) {
            return false;
        }
        if(cases == null) {
            return false;
        }
        return Arrays.asList(cases).stream().filter(v->v.intValue()== value.intValue()).findFirst().isPresent();
    }
    
    public static boolean isNull(Integer value) {
        return value == null || value == 0;
    }
    
    public static String generateSearchQuery(Object criteria){
        String whereQuery = "";
        boolean isFound = false;
        String fieldName = "";
        for(Field criteriaField : criteria.getClass().getDeclaredFields()){
            
            isFound = false;
            
            fieldName = criteriaField.getName();
            
            if(fieldName.equalsIgnoreCase("startIndex") || fieldName.equalsIgnoreCase("limit") || fieldName.equalsIgnoreCase("order") || fieldName.equalsIgnoreCase("sortColumnName")|| fieldName.equalsIgnoreCase("SORT_COLUMNS")){
                continue;
            }                        
            
            try {
                Character first = fieldName.charAt(0);
                String getter = "";
                if(!criteriaField.getType().toString().equals(Boolean.class.toString())){
                    getter = "get" + first.toString().toUpperCase() + fieldName.substring(1, fieldName.length());
                }
                Method m = criteria.getClass().getMethod(getter);
                m.setAccessible(true);
                if(criteriaField.getType().toString().equals(String.class.toString())){                    
                    String val = (String) m.invoke(criteria);
                    if(!isEmpty(val)){
                        isFound = true;
                    }
                }
                if(criteriaField.getType().toString().equals(Integer.class.toString())){                    
                    Integer val = (Integer) m.invoke(criteria);
                    if(!isNull(val)){
                        isFound = true;
                    }
                }
                
                if(criteriaField.getType().toString().equals(Long.class.toString())){                    
                    Long val = (Long) m.invoke(criteria);
                    if(!isNull(val)){
                        isFound = true;
                    }
                }
                if(criteriaField.getType().toString().equals(BigInteger.class.toString())){                    
                    BigInteger val = (BigInteger) m.invoke(criteria);
                    if(!isNull(val)){
                        isFound = true;
                    }
                }
                if(criteriaField.getType().toString().equals(Short.class.toString())){                    
                    Short val = (Short) m.invoke(criteria);
                    if(!isNull(val)){
                        isFound = true;
                    }
                }
                if(criteriaField.getType().toString().equals(Date.class.toString())){                    
                    Date val = (Date) m.invoke(criteria);
                    if(val != null){
                        isFound = true;
                    }
                }
                if(isFound){
                    boolean likeSearch = false;
                    boolean fromDate = false; 
                    boolean toDate = false; 
                    boolean upper = false;
                    boolean gte = false;
                    boolean lte = false;
                    String objAnnotation = "";
                    if(criteriaField.getAnnotations().length > 0){
                        for(Annotation an : criteriaField.getAnnotations()){                            
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("com.istl.hellospringboot.utils.Like")){
                                likeSearch = true;
                            }
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("com.istl.hellospringboot.utils.FromDate")){
                                fromDate = true;
                            }
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("com.istl.hellospringboot.utils.ToDate")){
                                toDate = true;
                            }
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("com.istl.hellospringboot.utils.ToUpper")){
                                upper = true;
                            }
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("com.istl.hellospringboot.utils.Gte")){
                                lte = true;
                            }
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("com.istl.hellospringboot.utils.Lte")){
                                gte = true;
                            }
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("com.istl.hellospringboot.utils.Obj")){
                                Obj obj = criteriaField.getAnnotation(Obj.class);
                                if(obj != null){
                                    objAnnotation = obj.value();
                                }
                            }
                        }
                    }
                    if(likeSearch){
                        if(upper){
                            whereQuery += " and upper(o." + fieldName + ") like :" + fieldName;
                        }
                        else{
                            whereQuery += " and o." + fieldName + " like :" + fieldName;
                        }
                    }
                    else if(fromDate){
                        fieldName = fieldName.replace("From", "");
                        whereQuery += " and o." + fieldName + " >= :" + fieldName;
                    }
                    else if(toDate){
                        fieldName = fieldName.replace("To", "");
                        whereQuery += " and o." + fieldName + " <= :" + fieldName;
                    }
                    else if(gte){
                        fieldName = fieldName.replace("Gte", "");
                        whereQuery += " and o." + fieldName + " >= :" + fieldName;
                    }
                    else if(lte){
                        fieldName = fieldName.replace("Lte", "");
                        whereQuery += " and o." + fieldName + " <= :" + fieldName;
                    }
                    else{
                        if(!isEmpty(objAnnotation)){
                            whereQuery += " and o." + fieldName + "." + objAnnotation + " = :" + fieldName;
                        }
                        else{
                            if(upper){
                                whereQuery += " and upper(o." + fieldName + ") = :" + fieldName;
                            }
                            else{
                                whereQuery += " and o." + fieldName + " = :" + fieldName;
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } 
        }
        return whereQuery;
    }
    
    
    public static boolean isNull(Short value) {
        if (value == null) {
            return true;
        }
        return value <= 0;
    }
    
    public static boolean isNull(Long value) {
        return value == null || value.intValue() == 0;
    }
    
    public static boolean isNull(BigDecimal number) {
        return number == null || number.intValue() == 0;
    }
    
    public static boolean isNull(BigInteger number) {
        return number == null || number.intValue() == 0;
    }
    
    public static String listToString(List<Integer> values) {
        String val = "";
        if(values == null || values.isEmpty()) {
            return val;
        }
        val = values.stream().map((v) -> v+",").reduce(val, String::concat);
        if(val.length() >1) {
            val = val.substring(0, val.lastIndexOf(','));
        }
        
        return val;
    }

    public static String getMd5Hex(String s) {
        String result = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(s.getBytes());
            result = toHex(digest);
        }
        catch (NoSuchAlgorithmException e) {
        }
        return result;
    }

    public static String toHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (int i = 0; i < a.length; i++) {
            sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
            sb.append(Character.forDigit(a[i] & 0x0f, 16));
        }
        return sb.toString();
    }
    
    public static Query setQueryParameter(Query q, Object criteria){
        
        boolean isFound = false;
        String fieldName = "";
        for(Field criteriaField : criteria.getClass().getDeclaredFields()){
            
            isFound = false;
            
            fieldName = criteriaField.getName();
            
            if(fieldName.equalsIgnoreCase("startIndex") || fieldName.equalsIgnoreCase("limit")  || fieldName.equalsIgnoreCase("order") || fieldName.equalsIgnoreCase("sortColumnName")|| fieldName.equalsIgnoreCase("SORT_COLUMNS")){
                continue;
            }            
            Object val = null;
            try {
                Character first = fieldName.charAt(0);
                String getter = "";
                if(!criteriaField.getType().toString().equals(Boolean.class.toString())){
                    getter = "get" + first.toString().toUpperCase() + fieldName.substring(1, fieldName.length());
                }
                Method m = criteria.getClass().getMethod(getter);
                m.setAccessible(true);
                
                if(criteriaField.getType().toString().equals(String.class.toString())){                    
                    val = m.invoke(criteria);
                    if(!Utils.isEmpty((String)val)){
                        isFound = true;
                    }
                }
                if(criteriaField.getType().toString().equals(Integer.class.toString())){                    
                    val = m.invoke(criteria);
                    if(!Utils.isNull((Integer)val)){
                        isFound = true;
                    }
                }
                
                if(criteriaField.getType().toString().equals(Long.class.toString())){                    
                    val = m.invoke(criteria);
                    if(!Utils.isNull((Long)val)){
                        isFound = true;
                    }
                }
                
                if(criteriaField.getType().toString().equals(BigInteger.class.toString())){                    
                    val = m.invoke(criteria);
                    if(!Utils.isNull((BigInteger)val)){
                        isFound = true;
                    }
                }
                if(criteriaField.getType().toString().equals(Short.class.toString())){                    
                    val = m.invoke(criteria);
                    if(!Utils.isNull((Short) val)){
                        isFound = true;
                    }
                }
                if(criteriaField.getType().toString().equals(Date.class.toString())){                    
                    val = m.invoke(criteria);
                    if((Date) val != null){
                        isFound = true;
                    }
                }
                if(isFound){
                    boolean likeSearch = false;
                    boolean fromDate = false; 
                    boolean toDate = false;
                    boolean upper = false;
                    if(criteriaField.getAnnotations().length > 0){
                        for(Annotation an : criteriaField.getAnnotations()){                            
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("com.istl.hellospringboot.utils.Like")){
                                likeSearch = true;
                            }
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("com.istl.hellospringboot.utils.FromDate")){
                                fromDate = true;
                            }
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("com.istl.hellospringboot.utils.ToDate")){
                                toDate = true;
                            }
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("com.istl.hellospringboot.utils.ToUpper")){
                                upper = true;
                            }
                        }
                    }
                    if(likeSearch){
                        q.setParameter(fieldName, "%" + (upper ? ((String) val).toUpperCase() : (String) val) + "%");
                    }
                    else if(fromDate){
                        fieldName = fieldName.replace("From", "");
                        q.setParameter(fieldName, (Date)val);
                    }
                    else if(toDate){
                        fieldName = fieldName.replace("To", "");
                        q.setParameter(fieldName, (Date)val);
                    }
                    else{
                        if (upper && val != null) {
                            q.setParameter(fieldName, ((String) val).toUpperCase());
                        } else {
                            q.setParameter(fieldName, val);
                        }

                    }
                }
            } catch (Exception ex) {
                if(ex.getMessage() != null && ex.getMessage().contains("did not match expected type")){
                   if(val instanceof Integer){
                       Short s = ((Integer)val).shortValue();
                       q.setParameter(fieldName, s);
                   }
                   if(val instanceof Short){
                       Integer s = ((Short)val).intValue();
                       q.setParameter(fieldName, s);
                   }
                }
                //ex.printStackTrace();
            } 
        }
        return q;
    }
    
    
    public static final boolean indexExists(Object[] objects,int position) {
        if(objects == null || objects.length <position) {
            return false;
        }
        
        try {
            if(objects[position] == null){
                return false;
            }
        } catch(ArrayIndexOutOfBoundsException e) {
            return false;
        }
        
        return true;
    }
    
    public static String toUpper(String value, boolean isTrim) {
        if (isEmpty(value)) {
            return null;
        }

        if (isTrim) {
            return value.trim().toUpperCase();
        }

        return value.toUpperCase();
    }
    
}
