package com.example.demo1.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.demo1.dto.NewDTO;


@Component
public class UpLoadFileUtils 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(UpLoadFileUtils.class);
	public String saveImgage(byte[] bytes, String name) 
	{
		java.io.File dir = new java.io.File("src/main/resources/static/images");
		if(dir.exists()==false)
		{
			if(dir.mkdirs()) 
			{
				System.out.println("Directory is created!");
				LOGGER.info("Directory is created!");
			}
			else
			{
				System.out.println("Failed to create directory!");
				LOGGER.info("Failed to create directory!");
			}
			System.out.println(dir.getAbsolutePath());
			LOGGER.info(dir.getAbsolutePath());
		}
		else
		{
			System.out.println(dir.getPath() +"-exists");
			LOGGER.info(dir.getPath() +"-exists");
		}
		String originalName = StringUtils.substringBeforeLast(name, ".");
		String originalNameAndDate = originalName +"-"+String.valueOf( new Date().getTime()+".jpg" );
		File savedFile = new File(dir.getAbsoluteFile() + File.separator + originalNameAndDate);
		try(FileOutputStream fileOutputStream = new FileOutputStream(savedFile))
		{
			System.out.println("path of img on server:" + savedFile.getPath());
			fileOutputStream.write(bytes);
			fileOutputStream.close();
			return originalNameAndDate;
		} catch (IOException e) 
		{
			e.printStackTrace();		
		}
		return null;
	}	  
		public NewDTO uploadFile(NewDTO dto) 
		{
			byte[] decodeBase64 = Base64.getDecoder().decode(dto.getThumbnail().getBytes());// dữ liệu của file
			String nameFile = dto.getName().toLowerCase();//Original Name File
			String nameAfterLoad = this.saveImgage(decodeBase64, nameFile);
			dto.setThumbnail(nameAfterLoad);
			dto.setName(null);//Name of image set is null after uploaded
			return dto;
		}

/*	String fileName = "abc.txt";
	String[] extensions = {".txt", ".doc", ".pdf"};*/
	public static boolean checkIfFileHasExtension(String s, String[] extn) {
		return Arrays.stream(extn).anyMatch(entry -> s.endsWith(entry));
	}
}
