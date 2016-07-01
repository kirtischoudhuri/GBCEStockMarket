package com.gbce.stock.s3Market.persist;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RecordInFile {
public static void tradeDataRecord(String data) {

	try{
		File file =new File("TradeDataRecord.txt");
		//if file doesn't exists, then create it
		if(!file.exists()){
			file.createNewFile();
		}
		//true = append file
		FileWriter fileWritter = new FileWriter(file.getName(),true);
	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	        bufferWritter.write(data);
	        bufferWritter.newLine();
	        bufferWritter.close();
	}catch(IOException e){
		e.printStackTrace();
	}
}
}

