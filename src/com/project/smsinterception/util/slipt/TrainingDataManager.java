package com.project.smsinterception.util.slipt;

import java.io.*;
import java.util.*;

import com.project.smsinterception.R;


import android.content.Context;
public class TrainingDataManager {
	private static  String[] WishMessage = null;
	private static String[] NormalMessage = null;
	private static int count=0;
	private static int Nc = 0;
	private static float P0 = 0F; //P0表示祝福短信占总短信的概率
	private static float P1 = 0F; //P1表示常用短信占总短信的概率
	public TrainingDataManager(Context context) {
		
		if(WishMessage != null && NormalMessage!= null){
			return;
		}
		WishMessage = new String[1300];
		NormalMessage =new String[30000];
		InputStream filein = context.getResources().openRawResource(R.raw.sfsms);
		InputStream filetrain = context.getResources().openRawResource(R.raw.yufaku);
		//读入春节祝福短信语料库
		BufferedReader dr = null;
		try {
			dr = new BufferedReader(new InputStreamReader(filein)); 
			String key = null;
			while (true) {
				key = dr.readLine();
				if(key == null){
					break;
				}
				WishMessage[count] = key;
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(filein != null){
				try {
					filein.close();
					filein = null;
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			if(dr != null){
				try {
					dr.close();
					dr = null;
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
		//读入常用短信入料库
		try {
			dr = new BufferedReader(new InputStreamReader(filetrain)); 
			String key = null;
			while (true) {
				key = dr.readLine();
				if(key == null){
					break;
				}
				NormalMessage[Nc] = key;
				Nc++;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(filetrain != null){
				try {
					filetrain.close();
					filetrain = null;
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			if(dr != null){
				try {
					dr.close();
					dr = null;
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
		System.out.println(count+"CC:");
		System.out.println(Nc+"NR:");
		P0 = (float) ((count*1.0) / (count + Nc));
		P1 = (float) (Nc*1.0) / (count + Nc);
	}
	public double getProbabilyOfC0(){
		return P0;
	}
	public double getPorbabilityOfC1(){
		return P1;
	}
	
	//返回训练库中第i条短信的内容String
	public  String getText(int i) throws FileNotFoundException,IOException 
	{
		return WishMessage[i-1];
	}
	
	public int getTrainingCountOfClassification(int i)
	{
		if (i==0) return count+1;
		else return Nc+1;
	}
	
	public int getCountContainKeyOfClassification(String key ,int Classification) 
	{
		int ret = 0;
		if (Classification == 0){
		for (int i=0 ; i<count ; i++){
			if (WishMessage[i].contains(key)){
				ret++;
			}
		}
		}
		else {
			for (int i=0 ; i<Nc ; i++){
				if (NormalMessage[i].contains(key))
					ret++;
			}
		}
		return ret;
	}
}
