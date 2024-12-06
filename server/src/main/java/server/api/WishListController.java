package server.api;

import commons.WishList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.HotelRepository;
import server.database.PersonRepository;
import server.database.WishListRepository;

@RestController
@RequestMapping("api/wishlist")
public class WishListController {

    WishListRepository wishListRepository;

    /**
     * Constructor for WishListController
     * @param wishListRepository the wish list repository
     */
    public WishListController(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    /**
     * Get mapping for /api/wishlist/shared/{code} to get a wish list by sharing code
     * @param code the sharing code specified in the path
     * @return the wish list with the specified sharing code
     */
    @GetMapping("/shared/{code}")
    public ResponseEntity<WishList> getWishListBySharingCode(@PathVariable String code) {
        return wishListRepository.findBySharingCode(code)
                .map(wishList -> ResponseEntity.ok(wishList))
                .orElse(ResponseEntity.notFound().build());
    }

}
