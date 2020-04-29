package com.projetpaparobin.documents.applicationstate;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;

import javax.imageio.ImageIO;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.objects.creators.comments.CommentCreator;
import com.projetpaparobin.objects.creators.extinguishers.ExtinguisherCreator;
import com.projetpaparobin.objects.creators.zones.ZoneCreator;

public class ApplicationStatePersister {

	private static LayoutHandler layoutHandler = LayoutHandler.getInstance();
	
	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	private static ExtinguisherCreator extinguisherCreator = ExtinguisherCreator.getInstance();
	private static CommentCreator commentCreator = CommentCreator.getInstance();
	
	private static JsonMapper mapper = new JsonMapper();
	
	private static AutomaticApplicationStateSaver automaticSaver;
	
	public static void startAutomaticSaver(int nbrMinutesBTWSaves) {
		stopAutomaticSaver();
		
		automaticSaver = new AutomaticApplicationStateSaver(nbrMinutesBTWSaves);
		automaticSaver.start();
	}
	
	public static void stopAutomaticSaver() {
		if(automaticSaver != null) {
			automaticSaver.stopThread();
		}
	}
	
	public static void saveState(File file) {
		ApplicationStatePOJO state = new ApplicationStatePOJO(layoutHandler.getZones(), layoutHandler.getComments(), layoutHandler.getBufImage());
		System.out.println("Saved file at : " + file.getAbsolutePath());
		try {
			mapper.writeValue(file, state);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void loadState(File file) {
		try {
			String xml = inputStreamToString(new FileInputStream(file));
			ApplicationStatePOJO state = mapper.readValue(xml, ApplicationStatePOJO.class);

			layoutHandler.fullReset();
			layoutHandler.setZones(state.getZones());
			layoutHandler.setExtinguishers(state.getExtinguishers());
			layoutHandler.setComments(state.getComments());
			
			if(state.getBase64Image() != null && !state.getBase64Image().isBlank()) {
				layoutHandler.setBufImage(ApplicationStatePersister.decodeToImage(state.getBase64Image()));
			}
			
			zoneCreator.canceled();			
			extinguisherCreator.canceled();
			commentCreator.canceled();

			ZoneCreator.getInstance().reset();
			ExtinguisherCreator.getInstance().reset();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String inputStreamToString(InputStream is) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    String line;
	    BufferedReader br = new BufferedReader(new InputStreamReader(is));
	    while ((line = br.readLine()) != null) {
	        sb.append(line);
	    }
	    br.close();
	    return sb.toString();
	}

	public static String encoretoString(BufferedImage bufImage) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
 
        try {
            ImageIO.write(bufImage, "png", bos);
            byte[] imageBytes = bos.toByteArray();
 
            imageString = Base64.getEncoder().encodeToString(imageBytes);
 
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
	}
	
	public static BufferedImage decodeToImage(String base64Image) {
        BufferedImage image = null;
        byte[] imageByte;
        try {
            imageByte = Base64.getDecoder().decode(base64Image);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
	}
}
