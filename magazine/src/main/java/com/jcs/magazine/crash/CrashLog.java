package com.jcs.magazine.crash;

import java.io.Serializable;

public class CrashLog
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String board;
  private String bootloader;
  private String brand;
  private String cpuAbi;
  private String cpuAbi2;
  private String device;
  private String display;
  private String dpi;
  private String error;
  private String fingerprint;
  private String hardware;
  private String height;
  private String host;
  private String id;
  private String loginName;
  private String manufacturer;
  private String model;
  private String product;
  private String radio;
  private String serial;
  private String tags;
  private String time;
  private String token;
  private String type;
  private String user;
  private String versionCode;
  private String versionName;
  private String width;

  public String getBoard()
  {
    return this.board;
  }

  public String getBootloader()
  {
    return this.bootloader;
  }

  public String getBrand()
  {
    return this.brand;
  }

  public String getCpuAbi()
  {
    return this.cpuAbi;
  }

  public String getCpuAbi2()
  {
    return this.cpuAbi2;
  }

  public String getDevice()
  {
    return this.device;
  }

  public String getDisplay()
  {
    return this.display;
  }

  public String getDpi()
  {
    return this.dpi;
  }

  public String getError()
  {
    return this.error;
  }

  public String getFingerprint()
  {
    return this.fingerprint;
  }

  public String getHardware()
  {
    return this.hardware;
  }

  public String getHeight()
  {
    return this.height;
  }

  public String getHost()
  {
    return this.host;
  }

  public String getId()
  {
    return this.id;
  }

  public String getLoginName()
  {
    return this.loginName;
  }

  public String getManufacturer()
  {
    return this.manufacturer;
  }

  public String getModel()
  {
    return this.model;
  }

  public String getProduct()
  {
    return this.product;
  }

  public String getRadio()
  {
    return this.radio;
  }

  public String getSerial()
  {
    return this.serial;
  }

  public String getTags()
  {
    return this.tags;
  }

  public String getTime()
  {
    return this.time;
  }

  public String getToken()
  {
    return this.token;
  }

  public String getType()
  {
    return this.type;
  }

  public String getUser()
  {
    return this.user;
  }

  public String getVersionCode()
  {
    return this.versionCode;
  }

  public String getVersionName()
  {
    return this.versionName;
  }

  public String getWidth()
  {
    return this.width;
  }

  public void setBoard(String paramString)
  {
    this.board = paramString;
  }

  public void setBootloader(String paramString)
  {
    this.bootloader = paramString;
  }

  public void setBrand(String paramString)
  {
    this.brand = paramString;
  }

  public void setCpuAbi(String paramString)
  {
    this.cpuAbi = paramString;
  }

  public void setCpuAbi2(String paramString)
  {
    this.cpuAbi2 = paramString;
  }

  public void setDevice(String paramString)
  {
    this.device = paramString;
  }

  public void setDisplay(String paramString)
  {
    this.display = paramString;
  }

  public void setDpi(String paramString)
  {
    this.dpi = paramString;
  }

  public void setError(String paramString)
  {
    this.error = paramString;
  }

  public void setFingerprint(String paramString)
  {
    this.fingerprint = paramString;
  }

  public void setHardware(String paramString)
  {
    this.hardware = paramString;
  }

  public void setHeight(String paramString)
  {
    this.height = paramString;
  }

  public void setHost(String paramString)
  {
    this.host = paramString;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public void setLoginName(String paramString)
  {
    this.loginName = paramString;
  }

  public void setManufacturer(String paramString)
  {
    this.manufacturer = paramString;
  }

  public void setModel(String paramString)
  {
    this.model = paramString;
  }

  public void setProduct(String paramString)
  {
    this.product = paramString;
  }

  public void setRadio(String paramString)
  {
    this.radio = paramString;
  }

  public void setSerial(String paramString)
  {
    this.serial = paramString;
  }

  public void setTags(String paramString)
  {
    this.tags = paramString;
  }

  public void setTime(String paramString)
  {
    this.time = paramString;
  }

  public void setToken(String paramString)
  {
    this.token = paramString;
  }

  public void setType(String paramString)
  {
    this.type = paramString;
  }

  public void setUser(String paramString)
  {
    this.user = paramString;
  }

  public void setVersionCode(String paramString)
  {
    this.versionCode = paramString;
  }

  public void setVersionName(String paramString)
  {
    this.versionName = paramString;
  }

  public void setWidth(String paramString)
  {
    this.width = paramString;
  }

  public String toString()
  {
    return "CrashLog{\nid='" + this.id + '\'' + "\n display='" + this.display + '\'' + "\n product='" + this.product + '\'' + "\n device='" + this.device + '\'' + "\n board='" + this.board + '\'' + "\n cpu_abi='" + this.cpuAbi + '\'' + "\n cpu_abi2='" + this.cpuAbi2 + '\'' + "\n manufacturer='" + this.manufacturer + '\'' + "\n brand='" + this.brand + '\'' + "\n model='" + this.model + '\'' + "\n bootloader='" + this.bootloader + '\'' + "\n radio='" + this.radio + '\'' + "\n hardware='" + this.hardware + '\'' + "\n serial='" + this.serial + '\'' + "\n type='" + this.type + '\'' + "\n tags='" + this.tags + '\'' + "\n fingerprint='" + this.fingerprint + '\'' + "\n time=" + this.time + "\n user='" + this.user + '\'' + "\n host='" + this.host + '\'' + "\n error='" + this.error + '\'' + "\n width=" + this.width + "\n height=" + this.height + "\n dpi=" + this.dpi + "\n loginName='" + this.loginName + '\'' + "\n versionName='" + this.versionName + '\'' + "\n versionCode='" + this.versionCode + '\'' + "\n" + '}';
  }
}
