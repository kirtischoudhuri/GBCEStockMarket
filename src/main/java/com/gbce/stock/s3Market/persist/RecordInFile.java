package com.gbce.stock.s3Market.persist;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RecordInFile {

	private static Log log = LogFactory.getLog(RecordInFile.class);

	/**
	 * @author Kirti
	 * Record Trade in File
	 * 
	 * @param String
	 * @return TradeDataRecord.txt
	 *
	 */
	public static void tradeDataRecord(String data) {

		try{
			File file =new File("TradeDataRecord.txt");
			//if file doesn't exists, then create it
			if(!file.exists()){
				file.createNewFile();
			}
			//passing true to append file
			FileWriter fileWritter = new FileWriter(file.getName(),true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(data);
			bufferWritter.newLine();
			bufferWritter.close();
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
}

