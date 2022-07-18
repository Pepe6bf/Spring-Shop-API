package shop.spring.dev.springshop.domain.item;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.spring.dev.springshop.domain.global.BaseEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "item_img")
@Entity
public class ItemImg extends BaseEntity {

    @Column(name = "item_img_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storedImgName; // 이미지 파일명

    private String originalImgName; // 원본 이미지 파일명

    private String imgUrl;  // 이미지 조회 경로

    private Boolean isThumbnail;    // 대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public ItemImg(String storedImgName, String originalImgName, String imgUrl, Boolean isThumbnail, Item item) {
        this.storedImgName = storedImgName;
        this.originalImgName = originalImgName;
        this.imgUrl = imgUrl;
        this.isThumbnail = isThumbnail;
        this.item = item;
    }

    public void updateItemImg(String storedImgName, String originalImgName, String imgUrl) {
        this.storedImgName = storedImgName;
        this.originalImgName = originalImgName;
        this.imgUrl = imgUrl;
    }
}
