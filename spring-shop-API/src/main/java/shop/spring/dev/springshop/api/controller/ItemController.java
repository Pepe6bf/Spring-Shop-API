package shop.spring.dev.springshop.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.spring.dev.springshop.api.response.ResponseService;
import shop.spring.dev.springshop.api.response.SingleResult;
import shop.spring.dev.springshop.dto.item.ItemIdDto;
import shop.spring.dev.springshop.dto.item.ItemResponseDto;
import shop.spring.dev.springshop.dto.item.ItemSaveRequestDto;
import shop.spring.dev.springshop.dto.item.ItemUpdateRequestDto;
import shop.spring.dev.springshop.service.item.ItemImgService;
import shop.spring.dev.springshop.service.item.ItemService;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static shop.spring.dev.springshop.constant.BaseResponseStatus.*;

@RequiredArgsConstructor
@RestController
public class ItemController {

    private final ItemService itemService;
    private final ItemImgService itemImgService;
    private final ResponseService responseService;

    /**
     * 상품 정보 등록 API
     */
    @PostMapping("/admin/item/new")
    public ResponseEntity<SingleResult<ItemIdDto>> saveItem(@RequestBody ItemSaveRequestDto itemSaveRequestDto) {

        Long savedItemId = null;

        try {
            savedItemId = itemService.saveItem(itemSaveRequestDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ItemIdDto responseDto = ItemIdDto.builder().itemId(savedItemId).build();
        return new ResponseEntity<>(responseService.getSingleResult(CREATED_ITEM, responseDto), CREATED);
    }

    /**
     * 상품 이미지 등록 API
     */
    @PostMapping("/admin/itemImg/new")
    public ResponseEntity<SingleResult<ItemIdDto>> saveItemImg(@RequestParam("itemId") Long itemId,
                                                               @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) throws Exception {

        // 첫번째 상품 이미지는 필수!!
        if (itemImgFileList.get(0).isEmpty()) {
            throw new Exception();
        }

        ItemIdDto responseDto = ItemIdDto.builder().itemId(itemImgService.saveItemImg(itemId, itemImgFileList)).build();
        return new ResponseEntity<>(responseService.getSingleResult(CREATED_ITEM, responseDto), CREATED);
    }

    /**
     * 상품 단건 조회 API
     */
    @GetMapping("/admin/item/{itemId}")
    public ResponseEntity<SingleResult<ItemResponseDto>> getItem(@PathVariable Long itemId) {

        ItemResponseDto itemResponseDto = itemService.getItem(itemId);

        return ResponseEntity
                .status(OK)
                .body(responseService.getSingleResult(READ_ITEM, itemResponseDto));

    }

    /**
     * 상품 정보 변경 API
     */
    @PatchMapping("/admin/item/{itemId}")
    public ResponseEntity<SingleResult<ItemIdDto>> updateItem(@PathVariable Long itemId,
                                                              @RequestBody ItemUpdateRequestDto itemUpdateRequestDto) {

        ItemIdDto responseDto = ItemIdDto.builder().itemId(itemService.updateItem(itemId, itemUpdateRequestDto)).build();

        return ResponseEntity
                .status(OK)
                .body(responseService.getSingleResult(UPDATED_ITEM, responseDto));
    }

    /**
     * 상품 이미지 변경 API
     */
    @PatchMapping("/admin/itemImg/{itemId}")
    public ResponseEntity<SingleResult<ItemIdDto>> updateItemImg(@PathVariable Long itemId,
                                                                 @RequestParam("updateItemImgId") List<Long> updateItemImgIdList,
                                                                 @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList,
                                                                 @RequestParam("deleteItemImgId") List<Long> deleteItemImgIdList) throws Exception {

        Long updatedItemId = null;

        try {
            updatedItemId = itemImgService.updateItemImg(itemId, updateItemImgIdList, itemImgFileList, deleteItemImgIdList);
        } catch (Exception e) {
            throw e;
        }

        ItemIdDto responseDto = ItemIdDto
                .builder()
                .itemId(updatedItemId)
                .build();

        return ResponseEntity
                .status(OK)
                .body(responseService.getSingleResult(UPDATED_ITEM, responseDto));

    }
}
