package helpers;

public class Price {
	private String product;
    private String currency;
    private Integer price;
    private boolean happy;
    
    public Price(String product, Integer price, String currency){
    	this.product = product;
        this.price = price;
        this.currency = currency;
    }
    
    public String getProduct() {
        return product;
    }

    public Integer getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }
    
    public boolean isHappy() {
    	return happy;
    }
}
