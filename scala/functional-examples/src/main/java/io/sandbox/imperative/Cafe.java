package io.sandbox.imperative;

import java.util.ArrayList;
import java.util.List;

public class Cafe {

	public Coffee buyCoffee(CreditCard cc) {
		Coffee cup  =  new Coffee(2.5);
		cc.charge(cup.getPrice());

		return cup;
	}

	/**
	 *  Let's say there is not other way to call the external API. Calling it this way
	 *  will be less performatic rather than calling in batches.
	 *  It may will inquire extra charges calling external  APIs several times
	 *  Requires external framework to test the solution and ensure the side effects happened as expected
	 */
	public List<Coffee> buyCoffees(CreditCard cc, Integer ammount) {

		List<Coffee> result  =  new ArrayList<>();
		for (int i  =  0; i < ammount; i++) {
			result.add(buyCoffee(cc));
		}

		return result;
	}

}


class CreditCard {

	public void charge(Double price) {
		// do some side effect calling VISA, Mastercard or any other Transactional system
	}

}

class Coffee {
	private Double  price;

	public Coffee(Double price) {
		this.price = price;
	}

	public Double getPrice() {
		return price;
	}
}



