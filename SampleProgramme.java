package com.test;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class SampleProgramme {

	/**
	 * @param args
	 */
	private List<String> companyName;
	private Map<String, CompanyData> companyData;
	public SampleProgramme()
	{
		companyName=new ArrayList<String>();
		companyData=new HashMap<String, CompanyData>();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filePath=args[0];
		SampleProgramme sp=new SampleProgramme();
		int output=sp.readDataAndFindMaxSharePrice(filePath);
		if(output==1) //in case of success
		{
			sp.printData();
		}
	}
	
	public void printData() {
		// TODO Auto-generated method stub
		Iterator<String> listIterator=companyName.iterator();
		
		while(listIterator.hasNext())
		{
			
			String companyName=listIterator.next();
			//System.out.println(companyName);
			CompanyData cd=companyData.get(companyName);
			System.out.println("Company Name "+companyName+", Max Share Price "+cd.getSharePrice()+ ", Year "+cd.getMonth()+"-"+cd.getYear());
		}
		
	}

	public int readDataAndFindMaxSharePrice(String filePath)
	{
		//Read Data from File
		File f=new File(filePath);
		try
		{		
		FileInputStream fin = new FileInputStream(f);
		DataInputStream in = new DataInputStream(fin);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		  //Read File Line By Line
		
		int LineNumber=1;
		while ((strLine = br.readLine()) != null)   {			
		
		String data[]=strLine.split(",");
		CompanyData cdata=new CompanyData();
		for(int count=0;count<data.length;count++)
		{
			int listIndex=count-2;
			if(LineNumber==1)				
			{
				if(count >1)
				{
					companyName.add(data[count].trim());
				}
			}
			else
			{
				if(count==0)
				{
					cdata.setYear(Integer.parseInt(data[count].trim()));		
				}
				else if(count==1)
				{
					cdata.setMonth(data[count].trim());
				}
				else
				{
					cdata.setSharePrice(Integer.parseInt(data[count].trim()));
					
					CompanyData cadd=new CompanyData();
					cadd.setMonth(cdata.getMonth());
					cadd.setYear(cdata.getYear());
					cadd.setSharePrice(cdata.getSharePrice());
					
					if(!companyData.containsKey(companyName.get(listIndex)))
					{
						companyData.put(companyName.get(listIndex), cadd);
					}
					else
					{
						CompanyData cd=companyData.get(companyName.get(listIndex));
						if(cd.getSharePrice()<cadd.getSharePrice())
						{
							companyData.replace(companyName.get(listIndex), cadd);
						}
					}
						
					
				}
				
			}
			
		}
		LineNumber++;
		}
		in.close();
		fin.close();
		return 1;	
		}
		catch(FileNotFoundException  e)
		{
			System.out.println("File " + f.getAbsolutePath() +  " could not be found on filesystem");
			return -1;
		}
		catch(IOException ioe)
		{
			System.out.println("Exception while reading the file" + ioe);
			return -1;
		}
		
	}
	
}
