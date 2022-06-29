package shop.spring.dev.springshop.domain.item;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.spring.dev.springshop.constant.ItemSellStatus;
import shop.spring.dev.springshop.domain.global.BaseEntity;
import shop.spring.dev.springshop.dto.item.ItemUpdateRequestDto;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "item")
@Entity
public class Item extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String itemName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockNumber;    // 재고 수량

    @Column(nullable = false)
    private String itemDetail;  // 상품 상세 설명

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    @Builder
    public Item(String itemName, int price, int stockNumber, String itemDetail, ItemSellStatus itemSellStatus) {
        this.itemName = itemName;
        this.price = price;
        this.stockNumber = stockNumber;
        this.itemDetail = itemDetail;
        this.itemSellStatus = itemSellStatus;
    }

    public void updateItem(ItemUpdateRequestDto itemUpdateRequestDto) {
        this.itemName = itemUpdateRequestDto.getItemName();
        this.price = itemUpdateRequestDto.getPrice();
        this.stockNumber = itemUpdateRequestDto.getStockNumber();
        this.itemDetail = itemUpdateRequestDto.getItemDetail();
        this.itemSellStatus = itemUpdateRequestDto.getItemSellStatus();
    }
}
