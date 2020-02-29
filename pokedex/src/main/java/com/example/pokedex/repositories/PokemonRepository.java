package com.example.pokedex.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.pokedex.model.Pokemon;

public interface PokemonRepository extends PagingAndSortingRepository<Pokemon, Integer> {

	public Page<Pokemon> findAllByOrderByNameAsc(Pageable page);
	public Page<Pokemon> findAllByOrderByNameDesc(Pageable page);
	public Page<Pokemon> findAllByOrderByIdAsc(Pageable page);
	public Page<Pokemon> findAllByOrderByIdDesc(Pageable page);
	
	public Page<Pokemon> findByType1OrType2Like(Pageable page, String type1, String type2);
	
	@Query(value= "SELECT  DISTINCT type1 FROM Pokemon")
	public List<String> findAllType();
	
	@Query(value = "SELECT p  FROM Pokemon as p WHERE weight = (SELECT  MAX(weight) FROM p)")
	public List<Pokemon> findByMaxWeight();
	
	@Query(value = "SELECT p FROM Pokemon as p WHERE weight = (SELECT  MIN(weight) FROM p)")
	public List<Pokemon> findByMinWeight();
	
	@Query(value = "SELECT p FROM Pokemon as p WHERE height = (SELECT  MAX(height) FROM p)")
	public List<Pokemon> findByMaxHeight();
	
	@Query(value = "SELECT p FROM Pokemon as p WHERE height = (SELECT  MIN(height) FROM p)")
	public List<Pokemon> findByMinHeight();
}
