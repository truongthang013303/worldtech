package com.example.demo1.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;
//convert byte[] base64 sang MulipartFile
public class BASE64DecodedMultipartFile implements MultipartFile
{
	private byte[] base64;
	private String name;
	private String originalFilename;
	
	public byte[] getBase64() {
		return base64;
	}

	public void setBase64(byte[] base64) {
		this.base64 = base64;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	@Override
	public String getName() 
	{
		return name;
	}

	@Override
	public String getOriginalFilename() 
	{
		return originalFilename;
	}

	@Override
	public String getContentType() 
	{
		return null;
	}

	@Override
	public boolean isEmpty() 
	{
		return base64 == null || base64.length == 0;
	}

	@Override
	public long getSize() 
	{
		return base64.length;
	}

	@Override
	public byte[] getBytes() throws IOException 
	{
		return base64;
	}

	@Override
	public InputStream getInputStream() throws IOException 
	{
		return new ByteArrayInputStream(base64);
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException 
	{
		try (FileOutputStream f = new FileOutputStream(dest)) 
		{
			f.write(base64);
		}
		
	}
}
