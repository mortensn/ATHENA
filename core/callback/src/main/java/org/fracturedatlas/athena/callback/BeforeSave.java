package org.fracturedatlas.athena.callback;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeSave {
    String type();
}
