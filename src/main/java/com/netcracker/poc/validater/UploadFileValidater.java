package com.netcracker.poc.validater;

public class UploadFileValidater {
    public static boolean isValidJavaFile(String fileName){
        return fileName != null &&
                fileName.toLowerCase().endsWith(".java");
    }

}
