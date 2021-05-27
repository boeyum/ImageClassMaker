package no.jib.utillities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ImageClassMaker {
    private static BufferedImage img = null;
    private static String imagename = new String();
    private static String classname = new String();
    private static String pakkename = new String();
    private static String type = new String();
    private static boolean inn = false;
    private static boolean ut = false;
    private static boolean pakke = false;
    private static boolean typ = false;

    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if(args[i].equalsIgnoreCase("-i")) {
                i++;
                if(i < args.length) {
                    imagename = args[i];
                    inn = true;
                    if(imagename.contains(".png")) {
                        type = "png";
                        typ = true;
                    }
                    if(imagename.contains(".jpg")) {
                        type = "jpg";
                        typ = true;
                    }
                    if(imagename.contains(".jpeg")) {
                        type = "jpg";
                        typ = true;
                    }
                }
            }
            if(args[i].equalsIgnoreCase("-c")) {
                i++;
                if(i < args.length) {
                    classname = args[i];
                    ut = true;
                }
            }
            if(args[i].equalsIgnoreCase("-p")) {
                i++;
                if(i < args.length) {
                    pakkename = args[i];
                    pakke = true;
                }
            }
        }
        if((!inn) || (!ut)) {
            System.out.println("ERROR: Missing parameters input, stopping!");
            System.exit(1);
        }
        if(!pakke) {
            System.out.println("ERROR: No package name, setting default name.");
            pakkename = "no.name.example";
        }
        if(!loadImage()) {
            System.out.println("ERROR: Can't load image with name: " + imagename);
            System.exit(1);
        }
        if(!checkImage()) {
            System.out.println("ERROR: Image to big for making a image class!");
            System.exit(1);
        }
        if(!createClass()) {
            System.out.println("ERROR: Can't write the JAVA file, stopping!");
            System.exit(1);
        }
        System.exit(0);
    }

    private static boolean loadImage() {
        try {
            img = ImageIO.read(new File(imagename));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean checkImage() {
        int height = img.getHeight();
        int width = img.getWidth();
        int raster = (width * height);
        int pixels = img.getColorModel().getPixelSize();
        int bpix = (pixels / 8);
        int max = (2147483642 / bpix);
        if(raster > max) {
            return false;
        }
        System.out.println("IMAGE ANALYSES:");
        System.out.println(String.format("      Size (height/width)     : %d / %d",height,width));
        System.out.println(String.format("      Pixel depth (pix / byte): %d / %d",pixels,bpix));
        return true;
    }

    private static boolean createClass() {
        String encoded = convertTo();
        if(encoded == null) {
            return false;
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(String.format("data/%s.java",classname));
            fileWriter.write(String.format("package %s;\n\n",pakkename));
            fileWriter.write(String.format("import javax.imageio.ImageIO;\n"));
            fileWriter.write(String.format("import java.awt.image.BufferedImage;\n"));
            fileWriter.write(String.format("import java.io.ByteArrayInputStream;\n\n"));
            fileWriter.write(String.format("public class %s {\n",classname));
            fileWriter.write(String.format("\tprivate String logodata = \"%s\";\n\n",encoded));
            fileWriter.write("\tpublic BufferedImage getLogo() throws IOException {\n");
            fileWriter.write("\t\tbyte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(logodata);\n");
            fileWriter.write("\t\tBufferedImage imgtmp = ImageIO.read(new ByteArrayInputStream(imageBytes));\n");
            fileWriter.write("\t\treturn imgtmp;\n");
            fileWriter.write("\t}\n\n");
            fileWriter.write("\tpublic byte[] getLogoBytes() {\n");
            fileWriter.write("\t\tbyte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(logodata);\n");
            fileWriter.write("\t\treturn imageBytes;\n");
            fileWriter.write("\t}\n");
            fileWriter.write("}\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static String convertTo() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, type, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByte);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
