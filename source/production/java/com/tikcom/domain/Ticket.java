package com.tikcom.domain;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TICKET")
public class Ticket
{
	@Id
	@GeneratedValue
	private long ticketId;
	
	@Column(name = "CUSTOMER_NAME",nullable = false,length = 50)
    private String customerName;

	@Column(name = "SUBJECT",nullable = false,length = 100)
    private String subject;

	@Column(name = "BODY")
    private String body;

	//until applying DER specs and embeddable in hibernate make transient
    @Transient
    private Map<String, Attachment> attachments = new LinkedHashMap<>();

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public Attachment getAttachment(String name)
    {
        return this.attachments.get(name);
    }

    public Collection<Attachment> getAttachments()
    {
        return this.attachments.values();
    }

    public void addAttachment(Attachment attachment)
    {
        this.attachments.put(attachment.getName(), attachment);
    }

    public int getNumberOfAttachments()
    {
        return this.attachments.size();
    }
}
