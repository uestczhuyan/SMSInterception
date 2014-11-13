package com.project.smsinterception.util.slipt;

import com.project.smsinterception.MyApplication;

public class ClassConditionalProbability 
{
	private static TrainingDataManager tdm = new TrainingDataManager(MyApplication.INSTANCE);
	private static final double M = 0F;
	
	/**
	* 计算类条件概率
	* @param x 给定的文本属性
	* @param c 给定的分类
	* @return 给定条件下的类条件概率
	*/
	public static double calculatePxc(String x , int Classification) 
	{
		double ret = 0F;
		double Nxc = tdm.getCountContainKeyOfClassification(x , Classification);
		double Nc = tdm.getTrainingCountOfClassification(Classification);
		ret = (Nxc + 1) / (Nc + M);
		return ret;
	}
	
	public static double getPriorProbability(int i){
		if (i == 0) return tdm.getProbabilyOfC0();
		else return tdm.getPorbabilityOfC1();
	}
}
