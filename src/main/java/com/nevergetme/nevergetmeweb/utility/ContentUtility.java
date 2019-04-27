package com.nevergetme.nevergetmeweb.utility;

public class ContentUtility {
    private static final int SHORT_CUT_LINE=2;
    public static String getArticleShortCut(String articleContent){
        return articleContent.substring(100);
//        int index=0;
//        int count=0;
//        while (count<SHORT_CUT_LINE){
//            int k=articleContent.indexOf("\n",index);
//            if(k!=-1){
//                k=index;
//                count++;
//            }else{
//                break;
//            }
//        }
//        return articleContent.substring(index);
    }
}
