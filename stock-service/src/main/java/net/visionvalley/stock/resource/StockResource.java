/**
 * 
 */
package net.visionvalley.stock.resource;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;


/**
 * @author Guna Palani
 *
 */
@RestController
@RequestMapping("/rest/stock")
public class StockResource {
	
	private static Logger LOGGER = Logger.getLogger(StockResource.class.getName());
	
	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/{username}")
	public List<Quote> getStock(@PathVariable("username") final String userName){
		ResponseEntity<List<String>> quoteResponse = restTemplate.exchange("http://db-service:8300/rest/db/"+ userName, HttpMethod.GET, 
				null, new ParameterizedTypeReference<List<String>>() {
				});
		
		LOGGER.info("quoteResponse : "+quoteResponse);
		LOGGER.info("quoteResponse getBody : "+quoteResponse.getBody());
		
		List<String> quotes = quoteResponse.getBody();
		return quotes.stream()
				.map(quote -> {
					Stock stock = getStockPrice(quote);
					LOGGER.info("stock : "+stock);
					LOGGER.info("stock.getQuote().getPrice() : "+stock.getQuote().getPrice());
					return new Quote(quote, stock.getQuote().getPrice());
				})
 			.collect(Collectors.toList());
	}

	private Stock getStockPrice(String quote) {
		try {
			LOGGER.info("getStockPrice quote : "+quote);
			return YahooFinance.get(quote);
		} catch (IOException e) {
			e.printStackTrace();
			return new Stock(quote);
		}
	}
	
	private class Quote {
		private String quote;
		private BigDecimal price;
		
		public Quote(String quote, BigDecimal price) {
			this.setQuote(quote);
			this.setPrice(price);
		}

		public String getQuote() {
			return quote;
		}

		public void setQuote(String quote) {
			this.quote = quote;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}
	}
}
