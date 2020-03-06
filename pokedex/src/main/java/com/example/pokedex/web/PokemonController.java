package com.example.pokedex.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pokedex.model.Pokemon;
import com.example.pokedex.repositories.PokemonRepository;


@RestController
@CrossOrigin
@RequestMapping("/pokedex")
public class PokemonController {
	@Autowired
	private PokemonRepository pokemonRepository;

	@GetMapping("/list")
	public Page<Pokemon> findAll(@PageableDefault(page = 0, size = 8) Pageable page){
		return this.pokemonRepository.findAll(page);
	}

	@GetMapping("/list-asc")
	public Page<Pokemon> findAllByNameAsc(@PageableDefault(page = 0, size = 8) Pageable page){
		return this.pokemonRepository.findAllByOrderByNameAsc(page);
	}

	@GetMapping("/list-id-asc")
	public Page<Pokemon> findAlltByIdAsc(@PageableDefault(page = 0, size = 8) Pageable page){
		return this.pokemonRepository.findAllByOrderByIdAsc(page);
	}

	@GetMapping("/list-desc")
	public Page<Pokemon> findAllByNameDesc(@PageableDefault(page = 0, size = 8) Pageable page){
		return this.pokemonRepository.findAllByOrderByNameDesc(page);
	}
	@GetMapping("/list-id-desc")
	public Page<Pokemon> findAllByIdDesc(@PageableDefault(page = 0, size = 8) Pageable page){
		return this.pokemonRepository.findAllByOrderByIdDesc(page);
	}
	
	@GetMapping("/pokemon/details/{id:[0-9]+}")
	public ResponseEntity<Pokemon> findById(@PathVariable("id") int id){
		Optional<Pokemon> pokemon = this.pokemonRepository.findById(id);
		if(!pokemon.isPresent())
			return new ResponseEntity<Pokemon>(HttpStatus.NOT_FOUND);
			
		return new ResponseEntity<Pokemon>(pokemon.get(), HttpStatus.OK);
	}
	
	
	@GetMapping("/list-type")
	public ResponseEntity<List<String>> findDistinctType1(){
		List<String> list_type = this.pokemonRepository.findAllType();
		return new ResponseEntity<>(list_type, HttpStatus.OK);
	}
	
	@GetMapping("/search/types")
	public Page<Pokemon> findByType1Asc(@PageableDefault(page = 0, size = 200) Pageable page,
										@RequestParam("type1") String type1,
										@RequestParam("type2") String type2){
		return this.pokemonRepository.findByType1OrType2Like(page, type1, type2);
	}

	
	
	@GetMapping("/pokemon/max/{key:[a-zA-Z]+}")
	public ResponseEntity<Page<Pokemon>> findPokemonMaxDimension(
			@PathVariable("key") String key,
			@PageableDefault(page = 0, size = 8) Pageable page){

		List<Pokemon> pokemons = null;
		if(key.toLowerCase().equals("weight"))
			pokemons = this.pokemonRepository.findByMaxWeight();
		else if(key.toLowerCase().equals("height"))
			pokemons = this.pokemonRepository.findByMaxHeight();
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		PageImpl pageable =  new PageImpl<Pokemon>(pokemons,page, pokemons.size());

		if(pageable.getSize()==0)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(pageable, HttpStatus.OK);
	}

	@GetMapping("/pokemon/min/{key:[a-zA-Z]+}")
	public ResponseEntity<Page<Pokemon>> findPokemonMinDimension(
			@PathVariable("key") String key,
			@PageableDefault(page = 0, size = 8) Pageable page){

		List<Pokemon> pokemons = null;
		if(key.toLowerCase().equals("weight"))
			pokemons = this.pokemonRepository.findByMinWeight();
		else if(key.toLowerCase().equals("height"))
			pokemons = this.pokemonRepository.findByMinHeight();
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		PageImpl pageable =  new PageImpl<Pokemon>(pokemons,page, pokemons.size());

		if(pageable.getSize()==0)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(pageable, HttpStatus.OK);
	}
}
