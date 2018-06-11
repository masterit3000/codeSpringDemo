/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elearningbackend.utility;

import java.sql.Timestamp;

/**
 *
 * @author c1508l3694
 */
public abstract class CodeGenerator {
    public static String generateQuestionCode(String subcategoryCode){
        return String.format("%sQ%s", subCate(subcategoryCode), getCodePostFix());
    }

    public static String generateAnswerCode(){
        return String.format("A%s", getCodePostFix());
    }

    public static String generateLessionCode(){
        return String.format("L%s", getCodePostFix());
    }

    public static String getCodePostFix() {
        return String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(6);
    }

    public static String generateFileUrl(String fileName) {
        return String.format("%s%s", String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()), fileName);
    }

    private static String subCate(String cate){
        String sub = cate.substring(0,2);
        return sub;
    }
}
