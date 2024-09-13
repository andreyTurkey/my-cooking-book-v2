package com.example.application.vaadinPart;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.example.application.model.PhotoLink;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Setter
@Slf4j
@VaadinSessionScope
@Component
public class UploadMemoryBuffer extends Div {

    private long count = 0;
    private Upload multiFileUpload;
    private List<PhotoLink> links = new ArrayList<>();

    public UploadMemoryBuffer(@Value("${photoDirectory}") String photoDir) {

        MultiFileMemoryBuffer multiFileMemoryBuffer = new MultiFileMemoryBuffer();
        multiFileUpload = new Upload(multiFileMemoryBuffer);
        getUpload().setDropAllowed(false);
        multiFileUpload.setMaxWidth("400px");
        multiFileUpload.setMaxFiles(3);
        multiFileUpload.setMaxFileSize(100_000_000);
        multiFileUpload.setAcceptedFileTypes(".jpg", ".jpeg", ".png", ".gif", ".tiff");

        multiFileUpload.addSucceededListener(event -> {
            String fileName = event.getFileName();

            InputStream fileData = multiFileMemoryBuffer
                    .getInputStream(fileName);
            long contentLength = event.getContentLength();
            String mimeType = event.getMIMEType();

            int x = mimeType.indexOf("/", 0);
            String fileFormat = mimeType.substring(x + 1);

            String fullPhotoDirectory = photoDir + ++count + "." + fileFormat;
            PhotoLink phl = new PhotoLink();
            phl.setLink(fullPhotoDirectory);
            log.debug("ЛИНК - " + phl);
            links.add(phl);

            try (FileOutputStream fos = new FileOutputStream(fullPhotoDirectory)) {
                byte[] buffer = new byte[1024];
                int len = fileData.read(buffer);
                while (len != -1) {
                    fos.write(buffer, 0, len);
                    len = fileData.read(buffer);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    public Upload getUpload() {
        return multiFileUpload;
    }

    public  List<PhotoLink> getLinks() {
        return links;
    }
}
