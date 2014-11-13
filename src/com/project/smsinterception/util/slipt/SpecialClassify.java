package com.project.smsinterception.util.slipt;

import java.util.ArrayList;


public class SpecialClassify {
	private String messageStr;
	private ArrayList<String> SentenceList = new ArrayList<String>();
	private String[] Specialwords={"爱你", "喜欢你" , "死" , "杀你"};
	public SpecialClassify(String str) {
		messageStr = str;
	}
	
	public boolean CheckSpecialWordsMethod(){
		DivideIntoSentence();
		boolean f = true ; 
		for (int i=0 ; i<SentenceList.size() ; i++){
			String str = SentenceList.get(i);
			for (int j=0 ; j<Specialwords.length ; j++)
			{
				if (str.contains(Specialwords[j])) f=false;
			}
		}
		return f;
	}
	public void DivideIntoSentence() {
		int i = 0;
		int j = 0;
		String str;
		int length = messageStr.length();
		while (i < length && j <length){
			while (i<length-1 && !isChinese(messageStr.charAt(i)) ) i++;
			j = i+1;
			while (j<length-1 && isChinese(messageStr.charAt(j)) ) j++;
			str = messageStr.substring(i, j);
			SentenceList.add(str);
			i = j+1;
		}
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
}
