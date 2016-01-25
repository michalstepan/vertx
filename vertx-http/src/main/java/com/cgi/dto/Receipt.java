package com.cgi.dto;

import com.cgi.common.ContentType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by stepanm on 18.1.2016.
 */
@XmlRootElement
public class Receipt implements Serializable {

    private Date date;
    private Long id;
    private byte[] data;
    private ContentType contentType;

    public Receipt(Date date, Long id, byte[] data, ContentType contentType) {
        this.date = date;
        this.id = id;
        this.data = data;
        this.contentType = contentType;
    }

    public Receipt() {
    }

    @XmlElement
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @XmlElement
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @XmlElement
    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }
}
