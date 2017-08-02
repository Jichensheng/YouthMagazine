package com.jcs.rtext.custom;

/**
 * author：Jics
 * 2017/8/2 10:06
 */
public class Test {
	public static void main(String[] args){
		String s="你好阿道夫！abcd—《你好》";
		char[] chars=new char[s.length()];
		for (int i = 0; i < s.length(); i++) {
			chars[i]= s.charAt(i)=='—'?'|':(s.charAt(i)=='《'?'﹁':(s.charAt(i)=='》'?'﹂':s.charAt(i)));
		}
		for (char aChar : chars) {
			System.out.println(String.valueOf(aChar));
		}
	}
}
