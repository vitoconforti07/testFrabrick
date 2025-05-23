package it.vito.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "conto_corrente")
public class ContoCorrente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conto_corrente")
    private Integer id;

    @Column(name = "id_account", unique = true)
    private Integer idAccount;

    @Column(name = "saldo")
    private Double saldo;

    @Column(name = "data_ultima_aggiornamento")
    @Temporal(TemporalType.DATE)
    private Date dataUltimoAggiornamento;

    @ManyToMany(mappedBy = "contoCorrenteSet")
    private Set<PersonaFisica> intestatari;

}
