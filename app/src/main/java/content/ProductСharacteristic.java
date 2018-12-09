package content;
public class ProductСharacteristic {
	String name;
	String value;
	boolean required;
	
	public ProductСharacteristic(String name) {
		this.name = name;
		this.required = true;
	}
	
	public ProductСharacteristic(String name, boolean required) {
		this.name = name;
		this.required = required;
	}
	
	public void setValue(String value) throws Exception{
		this.value = value;
	}
	
	public String getValue() throws Exception{
		return this.value;
	}
	
	public String getName() throws Exception{
		return this.name;
	}
	
	public boolean isRequired() throws Exception{
		return this.required;
	}
}
