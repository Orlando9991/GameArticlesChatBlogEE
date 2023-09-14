/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.tools;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author orlan
 */
public class ImageByteConverter {
    

    private ImageByteConverter(){}

    
    public static byte [] getByteOfImage(UploadedFile imgFile) throws IOException{
        if (imgFile != null) {
            String fileName = imgFile.getFileName();
            String extension = fileName.substring(fileName.lastIndexOf(".")+1);
            
            BufferedImage image = ImageIO.read(imgFile.getInputStream());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, extension, baos);
            
            return baos.toByteArray();
        }
        throw new IOException("Image file Null");
    }
    
  
    
    
    
}
