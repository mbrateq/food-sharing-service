package pl.sggw.foodsharingservice.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.sggw.foodsharingservice.model.dto.NoticeView;
import pl.sggw.foodsharingservice.model.entity.Notice;

@Mapper
public interface NoticeMapstructMapper {

    @Mapping(target = "noticeId", source = "noticeId")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "publicationDateTime", source = "publicationDateTime")
    @Mapping(target = "expirationDate", source = "expirationDate")
    public NoticeView toNoticeView(Notice notice);
}
