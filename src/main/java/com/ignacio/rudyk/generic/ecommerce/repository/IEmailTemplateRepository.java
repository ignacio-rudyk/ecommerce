package com.ignacio.rudyk.generic.ecommerce.repository;

import com.ignacio.rudyk.generic.ecommerce.repository.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {

    Optional<EmailTemplate> findByEmailCodeAndEnabledTrue(String emailCode);

}
