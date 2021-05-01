package project.watch_and_relax.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import project.watch_and_relax.model.binding.AnnouncementAddBindingModel;
import project.watch_and_relax.model.entity.Announcement;

@Mapper
public interface AnnouncementMapper {
    AnnouncementMapper INSTANCE=Mappers.getMapper(AnnouncementMapper.class);
    Announcement mapAnnouncementBindingToEntity(AnnouncementAddBindingModel announcementAddBindingModel);
    AnnouncementAddBindingModel mapAnnouncementToBindingModel(Announcement announcement);

}
