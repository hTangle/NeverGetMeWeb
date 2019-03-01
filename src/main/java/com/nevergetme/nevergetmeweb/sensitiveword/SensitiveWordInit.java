package com.nevergetme.nevergetmeweb.sensitiveword;

import com.nevergetme.nevergetmeweb.setting.ArgsProperty;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class SensitiveWordInit {
    private String ENCODING = "utf-8";    //字符编码
    @SuppressWarnings("rawtypes")
    public HashMap sensitiveWordMap;

    public SensitiveWordInit(){
        super();
    }
    public Map initKeyWord(){
        try {
            //读取敏感词库
            Set<String> keyWordSet = readSensitiveWordFile();
            //将敏感词库加入到HashMap中
            addSensitiveWordToHashMap(keyWordSet);
            //spring获取application，然后application.setAttribute("sensitiveWordMap",sensitiveWordMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sensitiveWordMap;
    }
    private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
        sensitiveWordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while(iterator.hasNext()){
            key = iterator.next();    //关键字
            nowMap = sensitiveWordMap;
            for(int i = 0 ; i < key.length() ; i++){
                char keyChar = key.charAt(i);       //转换成char型
                Object wordMap = nowMap.get(keyChar);       //获取

                if(wordMap != null){        //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                }
                else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String,String>();
                    newWorMap.put("isEnd", "0");     //不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if(i == key.length() - 1){
                    nowMap.put("isEnd", "1");    //最后一个
                }
            }
        }
    }

    private List<String> readSeneitiveWordFromMySQL(){
        List<String> list=new ArrayList<>();
        ArgsProperty argsProperty=new ArgsProperty();
        Connection conn;
        String driver="com.mysql.jdbc.Driver";
        String url="jdbc:mysql://localhost:3306/Sensitiveword";
        String user=argsProperty.getUsername();
        String password=argsProperty.getPassword();
        try {
            Class.forName(driver);
            conn= DriverManager.getConnection(url,user,password);
            if(!conn.isClosed()){
                System.out.println("Success");
            }
            Statement statement=conn.createStatement();
            //PreparedStatement pstmt=conn.prepareStatement("insert into words values(NULL,?)");

            String sql="select * from words";
            ResultSet rs=statement.executeQuery(sql);
            String words=null;
            while (rs.next()){
                words=rs.getString("words");
                list.add(words);
                //System.out.println(words);
            }
            rs.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 读取敏感词库中的内容，将内容添加到set集合中
     */
    private Set<String> readSensitiveWordFile() throws Exception{
        Set<String> set = null;
        //System.out.println(Thread.currentThread().getContextClassLoader().getResource("")+"SensitiveWord.txt");
        //System.out.println(ServletContext.getRealPath("/"));
        //File file = new File(Thread.currentThread().getContextClassLoader().getResource("")+"SensitiveWord.txt");//读取文件,测试使用的文件，正式部署到服务器上需要替换该文件
        //File file=new File("E:\\program\\SpringExamFirst\\out\\artifacts\\SpringExamFirst_war_exploded\\WEB-INF\\classes\\SensitiveWord.txt");
        //InputStreamReader read = new InputStreamReader(new FileInputStream(file),ENCODING);
        try {
            List<String> words=readSeneitiveWordFromMySQL();
            set = new HashSet<String>();
            for(String word:words){
                set.add(word);
            }
            //System.out.println(set);
//            if(file.isFile() && file.exists()){      //文件流是否存在
//                set = new HashSet<String>();
//                BufferedReader bufferedReader = new BufferedReader(read);
//                String txt = null;
//                while((txt = bufferedReader.readLine()) != null){    //读取文件，将文件内容放入到set中
//                    set.add(txt);
//                }
//            }
//            else{         //不存在抛出异常信息
//                file=new File("/home/SensitiveWord.txt");
//                if(file.isFile() && file.exists()){      //文件流是否存在
//                    set = new HashSet<String>();
//                    BufferedReader bufferedReader = new BufferedReader(read);
//                    String txt = null;
//                    while((txt = bufferedReader.readLine()) != null){    //读取文件，将文件内容放入到set中
//                        set.add(txt);
//                    }
//                }
//                else
//                    throw new Exception("敏感词库文件不存在");
//            }
        } catch (Exception e) {
            throw e;
        }
        System.out.println(set);
        return set;
    }
}
