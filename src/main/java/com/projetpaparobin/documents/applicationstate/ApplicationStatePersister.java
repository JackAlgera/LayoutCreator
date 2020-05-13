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
import java.util.ArrayList;
import java.util.Base64;

import javax.imageio.ImageIO;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.documents.tabs.TabHandler;

public class ApplicationStatePersister {

	private static TabHandler tabHandler = TabHandler.getInstance();
	
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
		ArrayList<ApplicationStatePOJO> layoutStates = new ArrayList<ApplicationStatePOJO>();
		for (LayoutHandler layoutHandler : tabHandler.getLayoutHandlers()) {
			layoutStates.add(new ApplicationStatePOJO(layoutHandler.getZones(), layoutHandler.getComments(), layoutHandler.getBufImage()));
		}

		try {
			mapper.writeValue(file, layoutStates);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void loadState(File file) {
		try {
			String xml = inputStreamToString(new FileInputStream(file));
			ArrayList<ApplicationStatePOJO> layoutStates = mapper.readValue(xml, new TypeReference<ArrayList<ApplicationStatePOJO>>() {});

			tabHandler.fullReset();
			
			for (ApplicationStatePOJO layoutState : layoutStates) {
				if(layoutState.getBase64Image() != null && !layoutState.getBase64Image().isBlank()) {
					BufferedImage buffImage = ApplicationStatePersister.decodeToImage(layoutState.getBase64Image());
					LayoutHandler newLayoutHandler = new LayoutHandler(buffImage);
					newLayoutHandler.setZones(layoutState.getZones());
					newLayoutHandler.setExtinguishers(layoutState.getExtinguishers());
					newLayoutHandler.setComments(layoutState.getComments());
					
					newLayoutHandler.getZoneCreator().canceled();
					newLayoutHandler.getExtinguisherCreator().canceled();
					newLayoutHandler.getCommentCreator().canceled();
					
					tabHandler.addLayoutHandler(newLayoutHandler);
				}
			}
			
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
