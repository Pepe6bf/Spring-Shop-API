package shop.spring.dev.springshop.service.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import shop.spring.dev.springshop.domain.item.Item;
import shop.spring.dev.springshop.domain.item.ItemImg;
import shop.spring.dev.springshop.domain.item.ItemImgRepository;
import shop.spring.dev.springshop.domain.item.ItemRepository;
import shop.spring.dev.springshop.service.file.LocalFileService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class ItemImgService {

    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final LocalFileService localFileService;

    public Long saveItemImg(Long itemId, List<MultipartFile> itemImgFileList) throws Exception {

        // 이미지를 저장할 아이템 인덱스 조회
        Optional<Item> item = itemRepository.findById(itemId);

        for (int i = 0; i < itemImgFileList.size(); i++) {

            MultipartFile itemImgFile = itemImgFileList.get(i);
            String originalImgName = itemImgFile.getOriginalFilename();
            String storedImgName = null;
            String storedImgUrl = null;

            // 파일 업로드 (로컬 리소스 스토리지)
            if (StringUtils.hasText(originalImgName)) {
                storedImgName = fileUploadToLocal(originalImgName, itemImgFile);
            }
            storedImgUrl = "/storage/images/item/" + storedImgName;

            // 상품 이미지의 정보를 DB에 저장
            ItemImg itemImg = ItemImg.builder()
                    .storedImgName(storedImgName)
                    .originalImgName(originalImgName)
                    .imgUrl(storedImgUrl)
                    .isThumbnail((i == 0))
                    .item(item.get())
                    .build();

            itemImgRepository.save(itemImg);
        }

        return item.get().getId();
    }

    // 로컬 폴더에 이미지 저장
    private String fileUploadToLocal(String originalImgName, MultipartFile itemImgFile) throws Exception {
        return localFileService.uploadFile(originalImgName, itemImgFile);

    }
}
