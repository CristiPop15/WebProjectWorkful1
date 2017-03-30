package com.workful.handler;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageHandler {

    public static String saveImage(byte[] imageArray, String email) throws IOException {
        String path = null;
        /** first transform array into image*/
        InputStream in = new ByteArrayInputStream(imageArray);
        BufferedImage bImageFromConvert = ImageIO.read(in);

        /** second store the image in folder and return path*/
        File pathToImage = new File("E:/ImgApp/"+email+".png"); //folder name and img name
        ImageIO.write(bImageFromConvert, "png", pathToImage);

        path = "E:/ImgApp/"+email+".png";
        return path;
    }


    public static byte[] getByteArray(String path){
        byte[] imageInByte = null;
        /**first get buffered img from path*/
        BufferedImage bfImg = null;

        try{
            bfImg = ImageIO.read(new File(path));
        }catch (IOException e){
            e.printStackTrace();
        }

        /**then convert buffimg to byte[]*/
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bfImg, "jpg", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        return imageInByte;
    }


}

