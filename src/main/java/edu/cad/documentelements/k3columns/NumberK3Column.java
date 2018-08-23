/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cad.documentelements.k3columns;

import edu.cad.utils.k3.K3SubjectEntity;

/**
 *
 * @author Олександр
 */
public class NumberK3Column extends AbstractK3Column {

    public NumberK3Column(int columnNumber) {
        super(columnNumber);
    }

    @Override
    public String getValue(K3SubjectEntity subject) {
        return Integer.toString(subject.getNumber());
    }
    
}
