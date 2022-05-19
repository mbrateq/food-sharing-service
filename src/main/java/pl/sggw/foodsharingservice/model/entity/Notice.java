package pl.sggw.foodsharingservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import pl.sggw.foodsharingservice.model.types.CategoryType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="NOTICES", indexes = @Index(name="PTD", columnList = "PUBLICATION_DATE"))
@Entity
public class Notice extends RepresentationModel<Notice> implements Serializable {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notice_generator")
//    @SequenceGenerator(name = "notice_generator", sequenceName = "notices_notice_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTICE_ID")
    private Long noticeId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "TEXT_CONTENT")
    private String content;

    @Column(name = "CATEGORY")
    @Enumerated(EnumType.STRING)
    private CategoryType category;

    @Column(name = "PUBLICATION_DATE")
    private LocalDateTime publicationDateTime;

    @Column(name = "EXPIRATION_DATE")
    private LocalDate expirationDate;

    @Column(name = "ACTIVE", columnDefinition = "boolean default true")
    @Builder.Default private boolean active = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTHOR")
    private User author;



}
