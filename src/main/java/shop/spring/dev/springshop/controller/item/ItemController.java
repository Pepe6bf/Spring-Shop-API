package shop.spring.dev.springshop.controller.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.spring.dev.springshop.dto.item.ItemResponseDto;
import shop.spring.dev.springshop.dto.item.ItemSaveRequestDto;
import shop.spring.dev.springshop.service.item.ItemImgService;
import shop.spring.dev.springshop.service.item.ItemService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ItemController {

    private final ItemService itemService;
    private final ItemImgService itemImgService;

    // 상품의 텍스트 정보 등록을 먼저 요청받는다.

    /**
     * item의 정보 등록 컨트롤
     * @param itemSaveRequestDto
     * @return
     */
    @PostMapping("/admin/item/new")
    public Long saveItem(@RequestBody ItemSaveRequestDto itemSaveRequestDto) {

        Long savedItemId = null;

        try {
            savedItemId = itemService.saveItem(itemSaveRequestDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return savedItemId;
    }

    // 이후 상품의 이미지들을 요청받는다.

    /**
     * 아이템의 이미지 등록 컨트롤
     * @param itemId
     * @param itemImgFileList
     * @return
     * @throws Exception
     */
    @PostMapping("/admin/itemImg/new")
    public Long saveItemImg(@RequestParam("itemId") Long itemId,
                            @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) throws Exception {

        // 첫번째 상품 이미지는 필수!!
        if (itemImgFileList.get(0).isEmpty()) {
            throw new Exception();
        }

        return itemImgService.saveItemImg(itemId, itemImgFileList);
    }

    @GetMapping("/admin/item/{itemId}")
    public ItemResponseDto getItem(@PathVariable("itemId") Long itemId) {

        try {
            ItemResponseDto itemResponseDto = itemService.getItem(itemId);
            return itemResponseDto;
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }
}
