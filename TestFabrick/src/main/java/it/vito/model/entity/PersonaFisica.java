package it.vito.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "persona_fisica")
public class PersonaFisica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona_fisica")
    private Integer id;

    @Column(name = "nome_persona_fisica", unique = true)
    private String nome;

    @Column(name = "cognome_persona_fisica", unique = true)
    private String cognome;

    @Column(name = "codice_fiscale_persona_fisica", unique = true, length = 16)
    private String codiceFiscale;

    @ManyToMany
    @JoinTable(name = "persona_conto_corrente", joinColumns = @JoinColumn(name = "id_persona_fisica"), inverseJoinColumns = @JoinColumn(name = "id_conto_corrente"))
    private Set<ContoCorrente> contoCorrenteSet;


}
