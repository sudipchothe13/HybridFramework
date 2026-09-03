package pojoLayer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CustomerDetails {

	public String partyIdentifier;
	public String name;
	public String email;
	public String phone;
	public String address;

}
