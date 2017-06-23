# TW_DigitalCard

1.下載Java.inc & JavaBridge.jar    從  http://php-java-bridge.sourceforge.net/pjb/download.php  下載


2.下載&安裝 自然人憑證卡片管理工具、讀卡機驅動以及申請HiSecure API套件 (這邊使用HiSecureJava2.2.0版本)  從  https://gpkiapi.nat.gov.tw/hisecure/product/no1.do  下載
  

3.將HiSecure API套件內 P11JNI.dll安裝至windows\system32 或 JRE_HOME\lib\i386下
  ，將P11JNI.jar複製至JRE_HOME\lib\ext目錄下
  

4.JavaBridge.jar 點開後至http://localhost:8080/查看JavaBridge.log，找到java.ext.dirs 後面的路徑即是
  JavaBridge.jar目錄，需將要與PHP 溝通的JAVA檔案放置該目錄位置下
  
  
5.PHP檔內需require_once('Java.inc')


使用方式:Forphp.java為主要與HiSecure API套件溝通，SmartCardController則負責實例化Forphp.java操作
        HiSecure API套件，憑證使用上都需依照Token，Forphp.java內getToken()方法中 Token tok = 
        module.getToken(module.getTokens()[0]);獲取Token值，這邊的做法是PHP端透過login()獲取這個
        Token並設置在Session中，使用上就將此Session傳至Forphp.java即可
