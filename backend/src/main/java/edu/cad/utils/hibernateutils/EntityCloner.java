/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cad.utils.hibernateutils;

import org.hibernate.internal.util.SerializationHelper;

import java.io.Serializable;

/**
 *
 * @author Олена
 */
public class EntityCloner {
    
    public static <T> T clone(Class<T> clazz, T dtls) { 
        T clonedObject = (T) SerializationHelper.clone((Serializable) dtls); 
        return clonedObject; 
  }
}
