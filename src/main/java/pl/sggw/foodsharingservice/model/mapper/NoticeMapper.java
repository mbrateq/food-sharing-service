package pl.sggw.foodsharingservice.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.sggw.foodsharingservice.model.entity.Notice;
import pl.sggw.foodsharingservice.model.view.NoticeView;

@Mapper
public interface NoticeMapper {

    @Mapping(target = "username", expression = "java((notice.getAuthor()!=null) ? notice.getAuthor().getUsername() : null)")
    NoticeView toNoticeView(Notice notice);
}
