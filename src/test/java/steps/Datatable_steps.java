package steps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import cucumber.api.java.en.*;
import helpers.Price;

public class Datatable_steps {
	private List<Integer> numbers;
	private int sum;
	private Map<String, Integer> priceList;
	private int totalSum = 0;
	private HashMap<String, Price> intPriceList = new HashMap<String, Price>();
	private int sekSum;
	private int euroSum;

	@Given("^a list of numbers:$")
	public void a_list_of_numbers(List<Integer> numbers){
		this.numbers = numbers;
	}
	
	@When("^I sum them$")
	public void i_sum_them() {
		for (Integer num : numbers) {
			sum +=num;
		}
	}
	
	@Then("^I should get \"(\\d+)\"$")
	public void i_should_get(int expectedSum) {
		Assert.assertEquals("Unexpected sum, expected " + expectedSum + " but actual total was " + sum, expectedSum, sum);
	}
	
	@Given("^a price per item list:$")
	public void a_price_per_item_list(Map<String,Integer> priceList) {
		this.priceList = priceList;
	}
	
	@When("^I order \"(\\d+)\" \"(.*)\" and \"(\\d+)\" \"(.*)\"$")
	public void i_order_and_i_order(int quanityFirstItem, String firstItem,
									int quanitySecondItem, String secondItem){
		int firstPrice = priceList.get(firstItem);
		int secondPrice = priceList.get(secondItem);
		
		totalSum += firstPrice;
		totalSum += secondPrice;
	}
	
	@Then("^my total owed is \"(.*)\"$") 
	public void my_total_owed_is(int expectedAmt) {
		Assert.assertEquals("Expected owed " + expectedAmt + " did not match actual " + totalSum, expectedAmt, totalSum);
	}
	
	@Given("^the price list for an international coffee shop$")
    public void the_price_list_for_an_international_coffee_shop(List<Price> prices) {

		int numPrices = prices.size();
		System.out.println("numPrices = " + numPrices);
		for(Price price : prices) {
			String key = price.getProduct();
			intPriceList.put(key,  price);
		}
	}
	
	@When("^I buy \"(\\d+)\" \"(.*)\" and \"(\\d+)\" \"(.*)\"$")
    public void i_order_coffee_and_donut(int numberOfFirstItems, String firstItem,
                                         int numberOfSecondItems, String secondItem) throws Throwable {
        Price firstPrice = intPriceList.get(firstItem);
        calculate(numberOfFirstItems, firstPrice);
        Price secondPrice = intPriceList.get(secondItem);
        calculate(numberOfSecondItems, secondPrice);
    }
	
	private void calculate(int numberOfItems, Price price) {
        if (price.getCurrency().equals("SEK")) {
            sekSum += numberOfItems * price.getPrice();
            return;
        }
        if (price.getCurrency().equals("EUR")) {
            euroSum += numberOfItems * price.getPrice();
            return;
        }
        throw new IllegalArgumentException("The currency is unknown");
    }
	
	@Then("^I should pay \"(\\d+)\" EUR be \"(.*)\" and \"(\\d+)\" SEK be \"(.*)\"$")
    public void should_I_pay_EUR_and_SEK(int expectedEuroSum, String eurHappy, int expectedSekSum, String sekHappy) throws Throwable {
        boolean eurHappyBool = false;
        boolean sekHappyBool = false;
		
		Assert.assertEquals(expectedEuroSum, euroSum);
        Assert.assertEquals(expectedSekSum,sekSum);
        
        if (eurHappy.equalsIgnoreCase("happy")) {
        	eurHappyBool = true;
        }
        
        if (sekHappy.equalsIgnoreCase("happy")) {
        	sekHappyBool = true;
        }
        
        Assert.assertEquals(eurHappyBool, intPriceList.get("coffee").isHappy());
        Assert.assertEquals(sekHappyBool, intPriceList.get("donut").isHappy());
    }
}
