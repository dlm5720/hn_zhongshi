package com.hnzs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource(value = {"classpath:ftp.properties"})
public class FtpUtil {
	private static final Logger logger = Logger.getLogger(FtpUtil.class);
	
	//ftp服务器地址
	@Value("${host_name}")
    public String hostname;
    //ftp服务器端口号默认为21
	@Value("${port}")
    public Integer port ;
    //ftp登录账号
	@Value("${ftpname}")
    public String ftpname;
    //ftp登录密码
	@Value("${password}")
    public String password;
	@Value("${fileurl}")
	public String fileurl;
	@Value("${is_enterLocalPassiveMode}")
	public String is_enterLocalPassiveMode;
    
    public FTPClient ftpClient = null;
    
    /**
    * 初始化ftp服务器
    */
   public void initFtpClient() {
       ftpClient = new FTPClient();
       ftpClient.setControlEncoding("utf-8");
       try {
           ftpClient.connect(hostname, port); //连接ftp服务器
           //ftpClient.enterLocalPassiveMode();  //被动模式
           //ftpClient.enterLocalActiveMode();    //主动模式
           ftpClient.login(ftpname, password); //登录ftp服务器
           int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
           if(!FTPReply.isPositiveCompletion(replyCode)){
               logger.info("connect failed...ftp服务器:"+this.hostname+":"+this.port);
               System.out.println("connect failed...ftp服务器:"+this.hostname+":"+this.port);
           }
           logger.info("connect successfu...ftp服务器:"+this.hostname+":"+this.port);
       }catch (Exception e) { 
          e.printStackTrace(); 
       } 
   }
   
   /**
    * 上传文件
    * @param pathname ftp服务保存地址
    * @param fileName 上传到ftp的文件名
    *  @param originfilename 待上传文件的名称（绝对地址） * 
    * @return
    */
    @SuppressWarnings("static-access")
	public boolean uploadFile( String pathname, String fileName,String originfilename){
        boolean flag = false;
        InputStream inputStream = null;
        try{
            System.out.println("开始上传文件");
            inputStream = new FileInputStream(new File(originfilename));
            initFtpClient();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            CreateDirecroty(pathname);
            ftpClient.makeDirectory(pathname);
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            ftpClient.logout();
            flag = true;
            System.out.println("上传文件成功");
        }catch (Exception e) {
            System.out.println("上传文件失败");
            e.printStackTrace();
        }finally{
            if(ftpClient.isConnected()){ 
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            } 
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            } 
        }
        return flag;
    }
    
    /**
     * 上传文件
     * @param pathname ftp服务保存地址
     * @param fileName 上传到ftp的文件名
     * @param inputStream 输入文件流 
     * @return
     */
    @SuppressWarnings("static-access")
	public boolean uploadFile( String pathname, String fileName,InputStream inputStream){
        boolean flag = false;
        try{
            logger.info("开始上传文件");
            initFtpClient();
            if("yes".equals(is_enterLocalPassiveMode)){
            	ftpClient.enterLocalPassiveMode();
            }
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            CreateDirecroty(pathname);
            ftpClient.makeDirectory(pathname);
            ftpClient.changeWorkingDirectory(pathname);
            flag = ftpClient.storeFile(fileName, inputStream);
            logger.info("文件名："+fileName+"flag:"+flag);
            ftpClient.logout();
            logger.info("上传文件成功");
        }catch (Exception e) {
        	logger.info("上传文件失败");
            System.out.println("上传文件失败");
            e.printStackTrace();
        }finally{
            if(ftpClient.isConnected()){ 
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            } 
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            } 
        }
        return flag;
    }
    
    //改变目录路径
    public boolean changeWorkingDirectory(String directory) {
       boolean flag = true;
       try {
           flag = ftpClient.changeWorkingDirectory(directory);
//           if (flag) {
//             System.out.println("进入文件夹" + directory + " 成功！");
//           } else {
//               System.out.println("进入文件夹" + directory + " 失败！开始创建文件夹");
//           }
       } catch (IOException ioe) {
           ioe.printStackTrace();
       }
       return flag;
   }
    
    //创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
    public boolean CreateDirecroty(String remote) throws IOException {
        boolean success = true;
        String directory = remote + "/";
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            String path = "";
            String paths = "";
            while (true) {
                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
                path = path + "/" + subDirectory;
                if (!existFile(path)) {
                    if (makeDirectory(subDirectory)) {
                        changeWorkingDirectory(subDirectory);
                    } else {
                        //System.out.println("创建目录[" + subDirectory + "]失败");
                        changeWorkingDirectory(subDirectory);
                    }
                } else {
                    changeWorkingDirectory(subDirectory);
                }

                paths = paths + "/" + subDirectory;
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }

    //判断ftp服务器文件是否存在    
    public boolean existFile(String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }
    
    //创建目录
    public boolean makeDirectory(String dir) {
        boolean flag = true;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (!flag) {
            	System.out.println("创建文件夹" + dir + " 失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    
    /** * 下载文件 * 
     * @param pathname FTP服务器文件目录 * 
     * @param filename 文件名称 * 
     * @param os 下载后的文件路径 *
     * @return */
     public  OutputStream downloadFile(String pathname, String filename, OutputStream os){ 
         try { 
             logger.info("开始下载文件");
             initFtpClient();
             //切换FTP目录 
             ftpClient.changeWorkingDirectory(pathname); 
             FTPFile[] ftpFiles = ftpClient.listFiles(); 
             for(FTPFile file : ftpFiles){ 
                 if(filename.equalsIgnoreCase(file.getName())){ 
                     ftpClient.retrieveFile(file.getName(), os); 
                 } 
             } 
             //ftpClient.logout(); 
             logger.info("下载文件成功");
         } catch (Exception e) { 
             System.out.println("下载文件失败");
             logger.info("下载文件失败");
             e.printStackTrace(); 
         } 
         return os; 
     }
     
     /** * 删除文件 * 
      * @param pathname FTP服务器保存目录 * 
      * @param filename 要删除的文件名称 * 
      * @return */ 
      public boolean deleteFile(String pathname, String filename){ 
          boolean flag = false; 
          try { 
              logger.info("开始删除文件");
              initFtpClient();
              //切换FTP目录 
              ftpClient.changeWorkingDirectory(pathname); 
              ftpClient.dele(filename); 
              ftpClient.logout();
              flag = true; 
              logger.info("删除文件成功");
          } catch (Exception e) { 
              System.out.println("删除文件失败");
              logger.info("删除文件失败");
              e.printStackTrace(); 
          } finally {
              if(ftpClient.isConnected()){ 
                  try{
                      ftpClient.disconnect();
                  }catch(IOException e){
                      e.printStackTrace();
                  }
              } 
          }
          return flag; 
      }
      
      
      /**
       * 上传文件 -- 大文件分块上传
       * @param pathname ftp服务保存地址
       * @param fileName 上传到ftp的文件名
       * @param inputStream 输入文件流 
       * @return
       */
      @SuppressWarnings("static-access")
   	public boolean uploadFileCut(String pathname, String fileName,String uuid,InputStream inputStream){
          OutputStream outputStream =null;
          boolean flag = false;
          try{
//   	       	if(ftpClient==null || !ftpClient.isConnected()){
//   	    		
//   	    	}
              logger.info("开始上传文件");
              initFtpClient();
              if("yes".equals(is_enterLocalPassiveMode)){
           	   ftpClient.enterLocalPassiveMode();
              }
              ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
//              OutLog.outLog(pathname+uuid);
              if(!existFile(pathname+uuid)){
//           	   OutLog.outLog("上传的文件不存在,创建!");
                  CreateDirecroty(pathname);
                  ftpClient.makeDirectory(pathname);
                  //切换工作路径
                  ftpClient.changeWorkingDirectory(pathname);
                  //生成文件,保存第一片数据
                  ftpClient.storeFile(uuid, inputStream);
              }else{
//           	   OutLog.outLog("上传的文件已存在,开始往文件里面灌入数据!");
              }
              //把inputStream流中的数据灌入 文件的后面
              //第一次这里获取是空,不需要灌入,从第二次开始灌入.
              outputStream=ftpClient.appendFileStream(pathname+uuid);

              byte[] bytes = new byte[1024];
              int len = 0;
              while ((len = (inputStream.read(bytes)))>0){
                  outputStream.write(bytes,0,len);
              }
              flag=true;
              //关闭流
              if(null != inputStream)
           	   inputStream.close();
              if(null != outputStream)
           	   outputStream.close();
              //退出ftp
              ftpClient.logout();
              logger.info("上传文件成功");
          }catch (Exception e) {
              OutLog.outLog("上传文件失败:"+e.toString());
              e.printStackTrace();
              flag=false;
          }finally{
       	   if(null != outputStream){
                  try {
               	   outputStream.close();
                  } catch (IOException e) {
                      e.printStackTrace();
                  } 
              } 
              if(null != inputStream){
                  try {
                      inputStream.close();
                  } catch (IOException e) {
                      e.printStackTrace();
                  } 
              }
              if(ftpClient.isConnected()){ 
                  try{
                 	 ftpClient.disconnect();
                  }catch(IOException e){
                      e.printStackTrace();
                  }
              } 
          }
          return flag;
      }
      
      
      /**
       * 上传文件
       * @param pathname ftp服务保存地址
       * @param fileName 上传到ftp的文件名
       * @param uuid 输入文件流
       * @return
       */
      @SuppressWarnings("static-access")
   	public boolean renameFile(String pathname, String fileName,String uuid){
          boolean flag = false;
          try{
              initFtpClient();
              if("yes".equals(is_enterLocalPassiveMode)){
           	   ftpClient.enterLocalPassiveMode();
              }
              ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
              //切换工作路径
              ftpClient.changeWorkingDirectory(pathname);
//              String pwd = ftpClient.printWorkingDirectory();
//              OutLog.outLog("当前工作路径:"+pwd);
              flag = ftpClient.rename(uuid,fileName); 


              ftpClient.logout();

              logger.info("上传文件成功");
          }catch (Exception e) {
          	logger.info("上传文件失败");
              OutLog.outLog("上传文件失败");
              e.printStackTrace();
          }finally{
              if(ftpClient.isConnected()){ 
                  try{
                 	 ftpClient.disconnect();
                  }catch(IOException e){
                      e.printStackTrace();
                  }
              } 
          }
          return flag;
      }
      
}
