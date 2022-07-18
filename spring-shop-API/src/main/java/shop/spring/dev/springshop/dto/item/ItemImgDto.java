package shop.spring.dev.springshop.dto.item;

import lombok.*;
import shop.spring.dev.springshop.domain.item.ItemImg;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemImgDto {

    private Long id;

    private String storedImgName;

    private String originalImgName;

    private String imgUrl;

    private Boolean isThumbnail;

    public static ItemImgDto of(ItemImg itemImg) {
        return ItemImgDto.builder()
                .id(itemImg.getId())
                .storedImgName(itemImg.getStoredImgName())
                .originalImgName(itemImg.getOriginalImgName())
                .imgUrl(itemImg.getImgUrl())
                .isThumbnail(itemImg.getIsThumbnail())
                .build();
    }

}
