/**
 * 
 */
package net.visionvalley.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.visionvalley.model.Quote;

/**
 * @author Guna Palani
 *
 */
public interface QuotesRepository extends JpaRepository<Quote, Integer> {

	List<Quote> findByUserName(String username);

}
