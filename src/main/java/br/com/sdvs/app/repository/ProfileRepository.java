package br.com.sdvs.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.sdvs.app.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

	Profile findByCpf(String cpf);

	Page<Profile> findAll(Pageable pageable);

	Page<Profile> findAllByNameContaining(String name, Pageable pageable);

	@Query("SELECT p FROM Profile p JOIN p.groupParticipant g where p.name LIKE ?1 AND g = ?2")
	Page<Profile> findAllByNameContainingByGroupParticipant(String name, String groupParticipant, Pageable pageable);
}