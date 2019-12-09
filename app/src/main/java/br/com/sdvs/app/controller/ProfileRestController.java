package br.com.sdvs.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.sdvs.app.model.Profile;
import br.com.sdvs.app.repository.ProfileRepository;

@RestController
@RequestMapping("profiles")
public class ProfileRestController {

    @Autowired
    private ProfileRepository repository;

    @GetMapping(value = "/search")
    public ResponseEntity<Page<Profile>> search(
            @RequestParam("name") String name,
            @RequestParam("groupParticipant") String groupParticipant,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

        Page<Profile> page = null;

        if(name.isEmpty() && groupParticipant.isEmpty()){
            page = repository.findAll(pageable);
        }else if(!name.isEmpty() && groupParticipant.isEmpty()){
            page = repository.findAllByNameContaining(name, pageable);
        }else{
            page = repository.findAllByNameContainingByGroupParticipant("%"+name+"%", groupParticipant, pageable);
        }

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Profile>> findById(@PathVariable("id") Long id){
        Optional<Profile> entity = repository.findById(id);
        HttpStatus returnStatus = HttpStatus.OK;
        if (!entity.isPresent()) {
            returnStatus = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(entity, returnStatus);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Profile> create(@RequestBody Profile entity){
        Profile currentEntity = repository.findByCpf(entity.getCpf());
        if (currentEntity!=null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }else{
            repository.save(entity);
            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Profile> update(@PathVariable("id") Long id, @RequestBody Profile entity) {
        Optional<Profile> currentEntity = repository.findById(id);
        if (!currentEntity.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            repository.save(entity);
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Profile> delete(@PathVariable("id") Long id) {
        Optional<Profile> currentEntity = repository.findById(id);
        if (!currentEntity.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}