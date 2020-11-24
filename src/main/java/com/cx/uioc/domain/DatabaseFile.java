package com.cx.uioc.domain;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

import java.util.Arrays;

import javax.persistence.*;

/**
 * DatabaseFile Class
 */
@Entity
@Table(name = "files")
@Data
public class DatabaseFile {
	/**
	 * Properties
	 */
	/** Identifier */
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id")
	private String fileId;
	/** File name */
	private String fileName;
	/** Type of file */
	private String fileType;
	/** File data */
	@Lob
	@Column(length = 1024 * 1024 * 512)
	private byte[] data;

	/**
	 * Constructors
	 */
	/** Default */
	public DatabaseFile() {
	
	}
	
	/** Argued */
	public DatabaseFile(String fileName, String fileType, byte[] data) {
		this.fileName = fileName;
		this.fileType = fileType;
		
		if (data == null) {
			this.data = new byte [0];
		} else {
			this.data = Arrays.copyOf(data, data.length);
		}
	}
}
