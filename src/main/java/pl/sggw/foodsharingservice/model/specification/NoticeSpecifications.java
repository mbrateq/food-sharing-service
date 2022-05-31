package pl.sggw.foodsharingservice.model.specification;

import org.springframework.data.jpa.domain.Specification;
import pl.sggw.foodsharingservice.model.entity.Notice;
import pl.sggw.foodsharingservice.model.entity.Notice_;

import java.time.LocalDate;

public class NoticeSpecifications {

  public static Specification<Notice> withTitleContains(String query) {
    return (root, query1, criteriaBuilder) ->
        criteriaBuilder.like(
            criteriaBuilder.upper(root.get(Notice_.TITLE)), "%" + query.toUpperCase() + "%");
  }

  public static Specification<Notice> withContentContains(String query) {
    return (root, query1, criteriaBuilder) ->
        criteriaBuilder.like(
            criteriaBuilder.upper(root.get(Notice_.CONTENT)), "%" + query.toUpperCase() + "%");
  }

  public static Specification<Notice> withExpirationDateBefore(LocalDate queryDate) {
    return (root, query1, criteriaBuilder) ->
        criteriaBuilder.and(
            criteriaBuilder.greaterThanOrEqualTo(root.get(Notice_.EXPIRATION_DATE), queryDate));
  }

  public static Specification<Notice> withActive(boolean active) {
    return (root, query1, criteriaBuilder) ->
            criteriaBuilder.and(
                    criteriaBuilder.equal(root.get(Notice_.ACTIVE), active));
  }
}
