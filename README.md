# TW_DigitalCard

1.下載Java.inc & JavaBridge.jar    從  http://php-java-bridge.sourceforge.net/pjb/download.php

2.下載&安裝 自然人憑證卡片管理工具、讀卡機驅動以及申請HiSecure API套件 (這邊使用HiSecureJava2.2.0版本)          
  從   https://gpkiapi.nat.gov.tw/hisecure/product/no1.do
  
3.將HiSecure API套件內 P11JNI.dll安裝至windows\system32 或 JRE_HOME\lib\i386下
  P11JNI.jar複製至JRE_HOME\lib\ext目錄下
  
4.JavaBridge.jar 點開後至http://localhost:8080/查看JavaBridge.log，找到java.ext.dirs 後面的路徑即是JavaBridge.jar目錄
  ，需將要與PHP 溝通的JAVA檔案放置該位置
  
5.PHP檔內需require_once('Java.inc')
