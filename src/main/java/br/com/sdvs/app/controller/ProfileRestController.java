package br.com.sdvs.app.controller;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.sdvs.app.dto.ProfileDto;
import br.com.sdvs.app.model.Profile;
import br.com.sdvs.app.repository.ProfileRepository;
import br.com.sdvs.app.service.ProfileService;

@RestController
@RequestMapping("profiles")
public class ProfileRestController {

    @Autowired
    private ProfileRepository repository;

    @Autowired
    private ProfileService service;

    @GetMapping(value = "/import")
    public ResponseEntity<String> setFakeData(@PathVariable("filePath") String filePath) {

        String result = service.setFakeData(filePath);

        if(result.equals("FAIL")){

            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Page<Profile>> search(
            @RequestParam("name") String name,
            @RequestParam("groupParticipant") String groupParticipant,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

        Page<Profile> page = null;

        if(name.equals("") && groupParticipant.equals("")){
            page = repository.findAll(pageable);
        }else if(!name.equals("") && groupParticipant.equals("")){
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
    public ResponseEntity<Profile> create(@RequestBody ProfileDto dto){

        Profile currentEntity = repository.findByCpf(dto.getCpf());

        if (currentEntity!=null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        ModelMapper modelMapper = new ModelMapper();
        Profile persistentProfile = new Profile();
        modelMapper.map(dto, persistentProfile);

        repository.save(persistentProfile);
        return new ResponseEntity<>(persistentProfile, HttpStatus.CREATED);

    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Profile> update(@PathVariable("id") Long id, @RequestBody ProfileDto dto) {

        Optional<Profile> currentEntity = repository.findById(id);

        if (!currentEntity.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ModelMapper modelMapper = new ModelMapper();
        Profile persistentProfile = new Profile();
        modelMapper.map(dto, persistentProfile);
        repository.save(persistentProfile);

        return new ResponseEntity<>(persistentProfile, HttpStatus.OK);
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Profile> delete(@PathVariable("id") Long id) {

        Optional<Profile> currentEntity = repository.findById(id);

        if (!currentEntity.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}