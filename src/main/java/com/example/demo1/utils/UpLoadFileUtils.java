package com.example.demo1.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.example.demo1.dto.NewDTO;


@Component
public class UpLoadFileUtils 
{
	public String saveImgage(byte[] bytes, String name) 
	{
		java.io.File dir = new java.io.File("src/main/resources/static/images");
		if(dir.exists()==false)
		{
			if(dir.mkdirs()) 
			{
				System.out.println("Directory is created!");
			}
			else
			{
				System.out.println("Failed to create directory!");
			}
			System.out.println(dir.getAbsolutePath());
		}
		else
		{
			System.out.println("!exists");
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
			dto.setName(null);
			return dto;
		}
}
