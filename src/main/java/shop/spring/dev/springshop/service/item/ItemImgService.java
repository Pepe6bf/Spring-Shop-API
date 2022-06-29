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

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class ItemImgService {

    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final LocalFileService localFileService;

    /**
     * 상품의 이미지를 저장하는 메서드
     * @param itemId
     * @param itemImgFileList
     * @return
     * @throws Exception
     */
    public Long saveItemImg(Long itemId, List<MultipartFile> itemImgFileList) throws Exception {

        // 이미지를 저장할 아이템 인덱스 조회
        Optional<Item> item = itemRepository.findById(itemId);

        for (int i = 0; i < itemImgFileList.size(); i++) {

            // file 업데이트
            if (i == 0 && itemImgFileList.get(i) == null)
                continue;

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

    /**
     * 상품의 이미지를 변경하는 메서드
     * @throws Exception
     */
    public Long updateItemImg(Long itemId,
                              List<Long> itemImgIdList,
                              List<MultipartFile> itemImgFileList,
                              List<Long> deleteItemImgIdList) throws Exception {

        int idListSize = itemImgIdList.size();
        int fileListSize = itemImgFileList.size();

        // 이미지를 추가로 등록한다면
        if (fileListSize > idListSize && !(fileListSize == 1 && (itemImgFileList.get(0).getOriginalFilename() == ""))) {

            List<MultipartFile> newImgList = new ArrayList<>();
            newImgList.add(null);
            for (int i = idListSize; i < fileListSize; i++) {
                newImgList.add(itemImgFileList.get(i));
            }

            saveItemImg(itemId, newImgList);
        }

        // 기존 이미지를 삭제할 경우
        if (deleteItemImgIdList.size() != 0) {
            for (Long deleteItemImgId : deleteItemImgIdList) {
                ItemImg deleteItemImg = itemImgRepository.findById(deleteItemImgId)
                        .orElseThrow(EntityNotFoundException::new);
                localFileService.deleteFile(deleteItemImg.getStoredImgName());
                itemImgRepository.delete(deleteItemImg);
            }
        }

        // 기존 이미지 수정
        for (int i = 0; i < itemImgIdList.size(); i++) {
            Long itemImgId = itemImgIdList.get(i);
            MultipartFile itemImgFile = itemImgFileList.get(i);

            if (!itemImgFile.isEmpty()) {
                ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                        .orElseThrow(EntityNotFoundException::new);

                // 기존 이미지 파일 삭제
                if (StringUtils.hasText(savedItemImg.getStoredImgName())) {
                    localFileService.deleteFile(savedItemImg.getStoredImgName());
                }

                // 새 파일 업로드
                String newOriginalFilename = itemImgFile.getOriginalFilename();
                String newStoredFileName = fileUploadToLocal(newOriginalFilename, itemImgFile);
                String newStoredImgUrl = "/storage/images/item/" + newStoredFileName;

                // 새 파일 정보 DB에 저장
                savedItemImg.updateItemImg(newStoredFileName, newOriginalFilename, newStoredImgUrl);
            }
        }

        return itemId;
    }

    // 로컬 폴더에 이미지 저장
    private String fileUploadToLocal(String originalImgName, MultipartFile itemImgFile) throws Exception {
        return localFileService.uploadFile(originalImgName, itemImgFile);
    }
}
