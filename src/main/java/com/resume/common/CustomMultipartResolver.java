package com.resume.common;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
    
public class CustomMultipartResolver extends CommonsMultipartResolver {    
        
    private static Map<String, String> progressMap = new ConcurrentHashMap<String, String>();  
      
    @Override      
    public MultipartParsingResult parseRequest(HttpServletRequest request)      
            throws MultipartException {  
    	
    	final HttpSession session = request.getSession();
        String encoding = determineEncoding(request);      
        FileUpload fileUpload = prepareFileUpload(encoding);    
        ProgressListener progressListener = new ProgressListener() {  
            public void update(long pBytesRead, long pContentLength, int pItems) {  
                float tmp = (float)pBytesRead;  
                float percentage = tmp/pContentLength;  
                System.out.println("11" + "," + percentage);
                if (percentage >= 1) {
                	session.setAttribute("videoFile", 1);
                } else {  
                	session.setAttribute("videoFile", percentage);
                }  
            }  
        };  
        fileUpload.setProgressListener(progressListener);      
        try {      
            List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);      
            return parseFileItems(fileItems, encoding);      
        }      
        catch (FileUploadBase.SizeLimitExceededException ex) {      
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);      
        }      
        catch (FileUploadException ex) {      
            throw new MultipartException("Could not parse multipart servlet request", ex);      
        }      
    }      
  
    public String getProgress(String flag) {  
        return progressMap.get(flag);  
    }  
}   
