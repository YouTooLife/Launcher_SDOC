package net.youtoolife.launcher.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Observable;


public class Download extends Observable implements Runnable {
	  private static final int MAX_BUFFER_SIZE = 1024;
	
	  public static final int DOWNLOADING = 0;
	  public static final int COMPLETE = 1;
	
	  //private URL url; 
	  private String files[]; 
	  private int size; 
	  private int downloaded; 
	  private int status; 
	  
	  private String dir = System.getProperty("user.home")+"/YouTooLife/STALKER_DOC/";
	  private String fileName = null;
	  
	  private long value = 0;
	  //private long fullSize = 0;
	  private String state = "";
	  /**
	   * 
	   * @param url
	   */
	  public Download() {
		//this.url = url;
		
		//download();
	  }
	  /**
	   * 
	   */
	  public void download(String files[]) {
		this.files = files;
	    Thread thread = new Thread(this);
	    thread.start();
	  }
	  /**
	   * 
	   * @param url
	   * @return
	   */
	 /* private String getFileName(URL url) {
	    String fileName = url.getFile();
	    return fileName.substring(fileName.lastIndexOf('/') + 1);
	  }*/
	  /**
	   * 
	   */
	  public void run() {
		for (int i = 0; i < files.length; i++) {
		size = -1;
		downloaded = 0;
		status = DOWNLOADING;
		System.out.println("Downloading... "+files[i]);
	    RandomAccessFile file = null;
	    InputStream stream = null;
	
	    try {
	    	URL url = new URL("http://ytl.96.lt/download/dwns.php?user=nop&file=STALKER_DOC_JARs/"+files[i]);
	      HttpURLConnection connection =
	        (HttpURLConnection) url.openConnection();
	      connection.setRequestProperty("Range",
	    downloaded + "-");
	
	  connection.connect();
	  System.out.println("D1");
	
	  if (connection.getResponseCode() / 100 != 2) {
	    //error();
		  System.out.println("Error");
	  }
	
	  int contentLength = connection.getContentLength();
	  if (contentLength < 1) {
	    //error();
		  System.out.println("Error2");
	  }
	
	  if (size == -1) {
	    size = contentLength;
	    stateChanged();
	  }
	  
		if ((files[i].contains("mac/")) || (files[i].contains("win32/"))
				|| (files[i].contains("win64/")) || (files[i].contains("linux64/"))
						|| (files[i].contains("linux32/"))) {
			System.out.println("D2");
									fileName = files[i].substring(files[i].lastIndexOf('/') + 1);
								} else { fileName = files[i]; System.out.println("D22"); }
		
	  file = new RandomAccessFile(dir+fileName, "rw");
	  file.seek(downloaded);
	  System.out.println("D3");
	
	  stream = connection.getInputStream();
	  while (status == DOWNLOADING) {
	    byte buffer[];
	    if (size - downloaded > MAX_BUFFER_SIZE) {
	      buffer = new byte[MAX_BUFFER_SIZE];
	    } else {
	      buffer = new byte[size - downloaded];
	    }
	
	    int read = stream.read(buffer);
	    if (read == -1)
	      break;
	
	    file.write(buffer, 0, read);
	    downloaded += read;
	    stateChanged();
	  }
	
	  if(status == DOWNLOADING) {
	    status = COMPLETE;
	    stateChanged();
	    System.out.println("D4");
	  }
	} catch(Exception e) {
	  //error();
	} finally {
	  if (file != null) 
	    try {
	      file.close();
	    } catch (Exception e) {
	    	// ...
	    }      
	
	  if (stream != null) 
	    try {
	      stream.close();
	    } catch (Exception e) {
	    	// ...
	        }      
		}
		  }
		try {
			Runtime.getRuntime().exec("java -jar "+dir+"STALKER_DOC.jar");
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	  private double longDoable(long a) {
			double size = a/1000.0f;
			size = new BigDecimal(size).setScale(2, 0).doubleValue();
			return size;
		}
	
	  // Notify observers that this download's status has changed.
	  private  void stateChanged() throws IOException {
		  //System.out.println(downloaded+"/"+size+"- - -"+status+getDone());
		  /*String fn = null;
		  if (!fileName.contains("/")) {
			  fn = fileName.substring(fileName.lastIndexOf('/') + 1);
		  }
		  else {
			  fn = fileName;
		  }*/
		  //Main.label.setText("File: "+fileName+"\n - "+longDoable(downloaded)+"/"+longDoable(size)+" (KBytes)");
		  //Main.progressBar.setValue(getDownloaded()/(getSize()/100));
		 // longDoubleSize = longDoable(size);
		  state = ("File: "+fileName+"\n - "+longDoable(downloaded)+"/"+longDoable(size)+" (KBytes)");
		  if ((size /(350-25)) > 1)
			  value = (downloaded/(size/(350-25))); 
		 // Main.progressBar.setValue(downloaded/(size/100));
		  
	  }
	  
	  public String getState() {
		  return state;
	  }
	  public long getValue() {
		  return value;
	  }
}
