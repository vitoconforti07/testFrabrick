package it.vito.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "operazione_bancaria")
public class OperazioneBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "transaction_id", length = 255)
    private String transactionId;

    @Column(name = "operation_id", length = 255)
    private String operationId;

    @Column(name = "accounting_date")
    private LocalDate accountingDate;

    @Column(name = "value_date")
    private LocalDate valueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_enum_id")
    private TypeEnum typeEnum;

    @Column(name = "amount", precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "transaction_status", length = 50)
    private String transactionStatus;

    @Column(name = "is_debit")
    private Boolean isDebit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_conto_corrente")
    private ContoCorrente contoCorrente;

}
