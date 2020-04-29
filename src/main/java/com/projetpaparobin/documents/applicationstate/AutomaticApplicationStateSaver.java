package com.projetpaparobin.documents.applicationstate;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.projetpaparobin.utils.UIElements;

public class AutomaticApplicationStateSaver extends Thread {

	private static long SLEEP_TIME = 500;
	
	private boolean isPaused = false, running = false;
	private double timeBTWSaves, maxTimeBTWSaves;
	private File file;
	
	public AutomaticApplicationStateSaver(int nbrMinutesBTWSaves) {
		this.maxTimeBTWSaves = nbrMinutesBTWSaves * 60 * 1000;
		this.timeBTWSaves = 0;
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
		
		this.file = new File("Autosave_du_" + dateFormatter.format(new Date()) + "." + UIElements.SAVE_FILE_TYPE);
	}
	
	@Override
	public void run() {
		running = true;
		while(running) {
			try {
				if(!isPaused) {
					if(timeBTWSaves > maxTimeBTWSaves) {
						timeBTWSaves -= maxTimeBTWSaves;
						ApplicationStatePersister.saveState(file);
					} else {
						timeBTWSaves += SLEEP_TIME;
					}
				}
				
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setPause(boolean isPaused) {
		this.isPaused = isPaused;
		this.timeBTWSaves = 0;
	}
	
	public void stopThread() {
		running = false;
	}
	
}
