/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

@ManagedBean
@ViewScoped
public class FileUploadMBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Part file1;
    private String message;
    
     public static final int BUFFER_SIZE = 1024;

    public Part getFile1() {
        return file1;
    }

    public void setFile1(Part file1) {
        this.file1 = file1;
    }
   
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String uploadFile() throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context
                .getExternalContext().getContext();
        String path = servletContext.getRealPath("");
        boolean file1Success = false;
        
        if (file1.getSize() > 0) {
            String fileName = Utils.getFileNameFromPart(file1);
            /**
            * destination where the file will be uploaded
            */
            File outputFile = new File(path + File.separator + "WEB-INF"
                    + File.separator + fileName);
            inputStream = file1.getInputStream();
            outputStream = new FileOutputStream(outputFile);
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = 0;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            
            if (outputStream != null) {
                outputStream.close();
            }
            
            if (inputStream != null) {
                inputStream.close();
            }
            
            file1Success = true;
        }
        
        
        if (file1Success) {
            System.out.println("File uploaded to : " + path);
            setMessage("File successfully uploaded to " + path);
        } else {
            setMessage("Error, select atleast one file!");
        }
       
        
        return null;
    }
}
