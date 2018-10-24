/**
 * 
 */
package net.visionvalley.resource;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.visionvalley.model.Quote;
import net.visionvalley.model.Quotes;
import net.visionvalley.repository.QuotesRepository;

/**
 * @author Guna Palani
 *
 */
@RestController
@RequestMapping("rest/db")
public class DbServiceResource {

	private static Logger LOGGER = Logger.getLogger(DbServiceResource.class.getName());
	
	@Autowired
	private QuotesRepository quotesRepository;

	@GetMapping("/{username}")
	public List<String> getQutes(@PathVariable("username") final String username){		
		return getQuoteByUserName(username);
	}
	
	@PostMapping("/add")
	public List<String> add(@RequestBody final Quotes quotes){		
		
		quotes.getQuotes()
		.stream()
		.map(quote -> new Quote(quotes.getUserName(), quote))
		.forEach(quote -> quotesRepository.save(quote));
		
		return getQuoteByUserName(quotes.getUserName());
	}
	
	@PostMapping("/delete/{username}")
	public List<String> delete(@PathVariable("username") final String username) {
		
		List<Quote> quotes = quotesRepository.findByUserName(username);
		quotesRepository.deleteAll(quotes);
		
		return getQuoteByUserName(username);
	}
	
	private List<String> getQuoteByUserName(String username){
		
		return quotesRepository.findByUserName(username)
				.stream()
				.map(Quote::getQuote)
				.collect(Collectors.toList());
	}
}
