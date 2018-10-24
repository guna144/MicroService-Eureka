/**
 * 
 */
package net.visionvalley.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Guna Palani
 *
 */
@Entity
@Table(name = "quote", catalog = "packetdb")
public class Quote {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "quote")
	private String quote;
	
	public Quote() {}

	public Quote(String userName, String quote) {
		this.userName = userName;
		this.quote = quote;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}
	
	
	
}
