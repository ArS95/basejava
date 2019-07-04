package com.urise.webapp;

import java.io.File;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {
//        File file = new File(".\\.gitignore");
//        try {
//            System.out.println(file.getCanonicalPath());
//        } catch (IOException e) {
//            throw new RuntimeException("Error",e);
//        }
//        File dir = new File(".\\src\\com\\urise\\webapp");
//        System.out.println(dir.isDirectory());
//        String[] list = dir.list();
//        if (list != null) {
//            for (String name : list) {
//                System.out.println(name);
//            }
//        }
//
//        try (FileInputStream fis = new FileInputStream(file)){
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        showFiles(new File("D:\\Темиртасов\\Программирование!!!!\\basejava-master"), "");
    }

    public static void showFiles(File directory, String tab) throws IOException {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(tab + "File: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(tab + "Directory: " + file.getName());
                    showFiles(file, tab + "\t");
                }
            }
        }
    }
}
