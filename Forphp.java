package forphp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.crypto.IllegalBlockSizeException;
import tw.com.chttl.CertUtil;
import tw.com.chttl.CryptoUtil;
import tw.com.chttl.Module;
import tw.com.chttl.OCSP;
import tw.com.chttl.RevokedRecord;
import tw.com.chttl.TLCRL;
import tw.com.chttl.Token;
import tw.com.chttl.TokenException;

public class Forphp
{

	public Forphp()
	{
		
	}
	

	public static void main(String[] args)
    {
	  
	 }
	
   
    public String getCertAll(Token tok) throws CertificateException
 	{
        X509Certificate cert = tok.getCert(Token.ID_ENCRYPT);
        return "主體=" + cert.getSubjectDN() + ",發行者=" + cert.getIssuerDN() +",效期始=" + cert.getNotBefore() +
      	       ",效期止=" + cert.getNotAfter() +",SN=" + cert.getSerialNumber().toString(16) +",正附卡註記=" + CertUtil.getCardType(cert)+
      	       ",憑證種類=" + CertUtil.getCertType(cert)+",統一編號=" + CertUtil.getEnterpriseId(cert)+",組織OID=" + CertUtil.getOrgOID(cert)+
      	       ",身份證末碼=" + CertUtil.getPersonId(cert)+",主體形式=" + CertUtil.getSubjectType(cert)+",主體OID=" + CertUtil.getSubjectTypeOID(cert);
	}
    
    public String getCert(Token tok) throws CertificateException
    {
    	X509Certificate cert = tok.getCert(Token.ID_ENCRYPT);
    	return "main=" + cert.getSubjectDN() +",stime=" + cert.getNotBefore() +
       	       ",etime=" + cert.getNotAfter() +",sn=" + cert.getSerialNumber().toString(16)+",codeid=" + CertUtil.getEnterpriseId(cert);
    }
	
	public Token getToken()
	{
		Forphp php = new Forphp();
        Module.initialize();
        php.ListToken();
        Module module = Module.getInstance();
        Token tok = module.getToken(module.getTokens()[0]);
		return tok;
	}
	
	
	public void ListToken()
    {
	    Module module = Module.getInstance();
	    int[] tokIds = module.getTokens();
	    for (int i = 0; i < tokIds.length; i++) 
	    {
           System.out.println(tokIds[i]);
        }
	}
	
	 public boolean PHPLogin(Token tok,String password) throws IOException, TokenException
	 {
	     boolean login = tok.login(password);
	     return login;
	 }
	 
	 public boolean PHPLogout(Token tok)
	 {
		 boolean logout = tok.logout();
		 return logout;
	 }
	 
	 public String PHPTokenPresent(Token tok)
	 {
	     return "Present=" + tok.isTokenPresent();
	 }
	 
	 public String PHPSign(Token tok,String algorithm) throws CertificateException, NoSuchAlgorithmException
	 {
	      byte[] tbs = new byte[16];
	      for (int i = 0; i < tbs.length; i++) 
	      {
	         tbs[i] = 31;
	      }
	      X509Certificate cert = tok.getCert(Token.ID_SIGN);
	      byte[] sig = tok.sign(Token.ID_SIGN, tbs,algorithm);
	      if (sig == null)
	      {
	       	 return "簽章失敗";
	      }
	      String str = "簽章方法=" + algorithm + ",簽章=" + new BigInteger(sig).toString(16) + ",校驗值=" + CryptoUtil.verify(cert, tbs, sig, algorithm);
	      return str;
	  }
	  
	  public String PHPEncrypt(Token tok) throws CertificateException, InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException
	  {
	      byte[] key = new byte[16];
	      for (int i = 0; i < key.length; i++) 
	      {
	         key[i] = 33;
	      }
	      X509Certificate cert = tok.getCert(Token.ID_ENCRYPT);
	      byte[] wrap = CryptoUtil.wrap(cert, key);
	      Key k = tok.unwrap(Token.ID_ENCRYPT, wrap, "AES");
	      if (k == null)
	      {
	          return "加密失敗";
	      }
	      return "加密金鑰=" + new BigInteger(wrap).toString(16)+ ",解密金鑰=" + new BigInteger(1, k.getEncoded()).toString(16);
	  }
	  
	  
	  public String PHPOCSP(Token tok) throws CertificateException
	  {
	      X509Certificate cert = tok.getCert(Token.ID_ENCRYPT);
	      if(OCSP.checkStatus(cert)==0)
	      {
	    	  return "線上憑證查詢結果=" + "正常";
	      }
	      else
	      {
	    	  return "線上憑證查詢結果=" + "異常";
	      } 
	  }
}
