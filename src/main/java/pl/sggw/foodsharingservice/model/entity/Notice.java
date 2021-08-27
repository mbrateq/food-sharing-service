package pl.sggw.foodsharingservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="NOTICES", indexes = @Index(name="ptd", columnList = "PUBLICATION_DATE"))
@Entity
public class Notice extends RepresentationModel<Notice> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTICE_ID")
    private long noticeId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "PUBLICATION_DATE")
    private LocalDateTime publicationDateTime;

    @Column(name = "EXPIRATION_DATE")
    private LocalDate expirationDate;


}
