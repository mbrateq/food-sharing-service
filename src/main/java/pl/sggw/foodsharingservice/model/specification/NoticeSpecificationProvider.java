package pl.sggw.foodsharingservice.model.specification;

import org.springframework.data.jpa.domain.Specification;
import pl.sggw.foodsharingservice.model.entity.Notice;

import java.time.LocalDate;

import static pl.sggw.foodsharingservice.model.specification.NoticeSpecifications.withActive;
import static pl.sggw.foodsharingservice.model.specification.NoticeSpecifications.withContentContains;
import static pl.sggw.foodsharingservice.model.specification.NoticeSpecifications.withExpirationDateBefore;
import static pl.sggw.foodsharingservice.model.specification.NoticeSpecifications.withTitleContains;

public class NoticeSpecificationProvider {

  public static Specification<Notice> prepareQuerySpecification(String query, boolean active) {
    return Specification.where(withContentContains(query).or(withTitleContains(query)))
            .and(prepareDateSpecification(active));
  }

  public static Specification<Notice> prepareDateSpecification(boolean active) {
    return Specification.where(withExpirationDateBefore(LocalDate.now()).and(prepareActiveFlagSpecification(active)));
  }

  public static Specification<Notice> prepareActiveFlagSpecification(boolean active) {
    return Specification.where(withActive(active));
  }
}
