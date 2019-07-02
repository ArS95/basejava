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

        getFail(new File("C:\\Программирование\\basejava"));
    }

    public static void getFail(File file) throws IOException {
        File[] files;
        if (file.isDirectory()) {
            files = file.listFiles();
            for (File isFile : files) {
                if (isFile.isDirectory()) {
                    getFail(isFile);
                } else {
                    System.out.println(isFile.getName());
                }
            }
        } else {
            System.out.println(file.getName());
        }

//        File[] files = file.listFiles();
//        for (File isFile : files) {
//            if (isFile.isDirectory()) {
//                getFail(isFile);
//            } else {
//                System.out.println(isFile.getName());
//            }
//        }
    }
}
