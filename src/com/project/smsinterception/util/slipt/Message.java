package com.project.smsinterception.util.slipt;

import java.util.ArrayList;
import java.util.*;

public class Message {
	//private StringBuilder str = new StringBuilder();
	static int count=0;
	private String messageStr;
	public int smsLength;
	private ArrayList<String> SentenceList = new ArrayList<String>();
	private double Variance;
	private int WordsNumber;
	public Message(String str) {
		messageStr = str;
		smsLength = messageStr.length();
		count++;
	}
	
	public void DivideIntoSentence() {
		int i = 0;
		int j = 0;
		String str;
		int length = messageStr.length();
		/*for (i = 0; i < messageStr.length(); i++) {
			if (isChinese(messageStr.charAt(i))) {
				System.out.print(messageStr.charAt(i));
			}
			if (isChinesePunctuation(messageStr.charAt(i))){
				System.out.println();
			}
		}*/
		while (i < length && j <length){
			while (i<length-1 && !isChinese(messageStr.charAt(i)) ) i++;
			j = i+1;
			while (j<length-1 && isChinese(messageStr.charAt(j)) ) j++;
			str = messageStr.substring(i, j);
			SentenceList.add(str);
			WordsNumber += str.length(); 
			i = j+1;
		}
		SentenceNumVariance();
	}
	public void SentenceNumVariance(){
		double mean = 0;
		double lengthTot = 0;
		double DblVariance =0;
		for (int i=0 ; i<SentenceList.size() ; i++){
			lengthTot += SentenceList.get(i).length() ;
		}
		mean = lengthTot / SentenceList.size() ;
		for (int i=0 ; i<SentenceList.size() ; i++){
			DblVariance += (mean - SentenceList.get(i).length() ) * (mean - SentenceList.get(i).length());
		}
		Variance = Math.sqrt(DblVariance);
	}
	public int getWordsNumber(){
		return WordsNumber;
	}
	public double getVariance(){
		return Variance;
	}
	public void printList(){
		for (int i=0 ; i<SentenceList.size() ; i++){
			System.out.println(SentenceList.get(i));
		}
		System.out.println(Variance);
	}
	
	public boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (c != ' ' && (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A)) {
			return true;
		}
		return false;
	}

	public boolean isChinesePunctuation(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| c == ' '
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}
	
	public ArrayList<String> getList(){
		return SentenceList;
	}
}
