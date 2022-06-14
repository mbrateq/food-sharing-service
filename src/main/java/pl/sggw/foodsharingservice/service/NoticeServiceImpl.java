package pl.sggw.foodsharingservice.service;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.sggw.foodsharingservice.message.ErrorMessages;
import pl.sggw.foodsharingservice.model.dto.CreateNoticeDto;
import pl.sggw.foodsharingservice.model.dto.UpdateNoticeDto;
import pl.sggw.foodsharingservice.model.entity.Notice;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.model.mapper.NoticeMapper;
import pl.sggw.foodsharingservice.model.repository.NoticeRepository;
import pl.sggw.foodsharingservice.model.repository.UserRepository;
import pl.sggw.foodsharingservice.model.view.NoticeView;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static pl.sggw.foodsharingservice.model.specification.NoticeSpecificationProvider.prepareDateSpecification;
import static pl.sggw.foodsharingservice.model.specification.NoticeSpecificationProvider.prepareQuerySpecification;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

  private final NoticeRepository noticeRepository;
  private final UserRepository userRepository;

  private final NoticeMapper noticeMapper = Mappers.getMapper(NoticeMapper.class);

  @Override
  public Page<NoticeView> searchNoticesByQuery(Optional<String> query, Pageable pageable) {
    if (query.isPresent()) {
      final String queryStr = query.get().trim();
      if (queryStr.length() <= 2) {
        throw new ValidationException(format(ErrorMessages.QUERY_TOO_SHORT_MESSAGE, query.get()));
      }
      final Page<Notice> resultPage =
          noticeRepository.findAll(
              Specification.where(prepareQuerySpecification(queryStr, true)), pageable);
      if (0 == resultPage.stream().count()) {
        throw new EntityNotFoundException(
            format("cannot find notice with pattern: %s.", query.get()));
      } else {
        return new PageImpl<>(
            resultPage.stream()
                .map(notice -> noticeMapper.toNoticeView(notice))
                .collect(Collectors.toList()),
            pageable,
            resultPage.getTotalElements());
      }
    } else {
      final Page<Notice> resultPage =
          noticeRepository.findAll(Specification.where(prepareDateSpecification(true)), pageable);
      return new PageImpl<>(
          resultPage.stream()
              .map(notice -> noticeMapper.toNoticeView(notice))
              .collect(Collectors.toList()),
          pageable,
          resultPage.getTotalElements());
    }
  }

  @Override
  public Page<NoticeView> searchInactiveNoticesByQuery(Optional<String> query, Pageable pageable) {
    if (query.isPresent()) {
      final String queryStr = query.get().trim();
      if (queryStr.length() <= 2) {
        throw new ValidationException(format(ErrorMessages.QUERY_TOO_SHORT_MESSAGE, query.get()));
      }
      final Page<Notice> resultPage =
          noticeRepository.findAll(
              Specification.where(prepareQuerySpecification(queryStr, false)), pageable);
      return new PageImpl<>(
          resultPage.stream()
              .map(notice -> noticeMapper.toNoticeView(notice))
              .collect(Collectors.toList()),
          pageable,
          resultPage.getTotalElements());
    } else {
      final Page<Notice> resultPage =
          noticeRepository.findAll(prepareDateSpecification(false), pageable);
      return new PageImpl<>(
          resultPage.stream()
              .map(notice -> noticeMapper.toNoticeView(notice))
              .collect(Collectors.toList()),
          pageable,
          resultPage.getTotalElements());
    }
  }

  @Override
  public NoticeView createNotice(CreateNoticeDto createNoticeDto, String username) {
    final User user =
        userRepository
            .findByUsernameAndToDeleteFalse(username)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        format(ErrorMessages.USER_NOT_EXISTS_WITH_USERNAME_MESSAGE, username)));
    return noticeMapper.toNoticeView(
        noticeRepository.save(
            Notice.builder()
                .title(createNoticeDto.getTitle())
                .content(createNoticeDto.getContent())
                .expirationDate(createNoticeDto.getExpirationDate())
                .publicationDateTime(LocalDateTime.now())
                .category(createNoticeDto.getCategoryType())
                .author(user)
                .build()));
  }

  @Override
  public NoticeView updateNotice(long id, UpdateNoticeDto updateNoticeDto, String username) {
    final User user =
        userRepository
            .findByUsernameAndToDeleteFalse(username)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        format(ErrorMessages.USER_NOT_EXISTS_WITH_USERNAME_MESSAGE, username)));
    final Notice notice =
        noticeRepository
            .findByAuthorAndAndNoticeId(user, id)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        format(
                            ErrorMessages.NOTICE_NOT_EXISTS_WITH_AUTHOR_ID_MESSAGE, username, id)));
    return noticeMapper.toNoticeView(
        noticeRepository.save(
            notice.toBuilder()
                .title(updateNoticeDto.getTitle())
                .content(updateNoticeDto.getContent())
                .expirationDate(updateNoticeDto.getExpirationDate())
                .publicationDateTime(LocalDateTime.now())
                .build()));
  }

  @Override
  public boolean deactivateNotice(long id, String username) {
    final User user =
        userRepository
            .findByUsernameAndToDeleteFalse(username)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        format(ErrorMessages.USER_NOT_EXISTS_WITH_USERNAME_MESSAGE, username)));
    final Notice notice =
        noticeRepository
            .findByAuthorAndAndNoticeId(user, id)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        format(
                            ErrorMessages.NOTICE_NOT_EXISTS_WITH_AUTHOR_ID_MESSAGE, username, id)));
    noticeRepository.save(notice.toBuilder().active(false).build());
    return true;
  }

  @Override
  public boolean deleteNotice(long id) {
    if (noticeRepository.existsById(id)) {
      noticeRepository.deleteById(id);
      return true;
    } else {
      return false;
    }
  }
}
