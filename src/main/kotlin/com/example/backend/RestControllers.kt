package com.example.backend

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/1.0/marketing/campaigns")
class CarouselController {
    val carouselData =
        arrayListOf(
            CarouselData(
                1,
                201807242228,
                "https://api.appworks-school.tw/assets/201807242228/keyvisual.jpg",
                "於是\r\n我也想要給你\r\n一個那麼美好的自己。\r\n不朽《與自己和好如初》"
            ),
            CarouselData(
                2,
                201807242222,
                "https://api.appworks-school.tw/assets/201807242222/keyvisual.jpg",
                "永遠\r\n展現自信與專業\r\n無法抵擋的男人魅力。\r\n復古《再一次經典》"
            ),
            CarouselData(
                3,
                201807202140,
                "https://api.appworks-school.tw/assets/201807202140/keyvisual.jpg",
                "瞬間\r\n在城市的角落\r\n找到失落多時的記憶。\r\n印象《都會故事集》"
            )
        )
    val carousel: Carousel = Carousel(carouselData)

    @GetMapping fun CarouselData() = carousel
}

@RestController
@RequestMapping("api/1.0/products/{category}")
class ProductController {
    val productData = arrayListOf<ProductData>()

    val products: Product = Product(productData)

    @GetMapping
    fun getProducts(
        @PathVariable category: String,
        @RequestParam paging: Int
    ): ResponseEntity<List<Product>> = ResponseEntity.ok(listOf(products))
}

// // 在 RestControllers.kt 中新增一個新的 Controller
// @RestController
// @RequestMapping("api/v1/image")
// class ImageController(private val imageService: ImageService) {

//     @GetMapping("/{id}")
//     fun getImage(@PathVariable id: Long): ResponseEntity<ByteArray> {
//         val imageData = imageService.getImageDataById(id)
//         return ResponseEntity.ok()
//             .contentType(MediaType.IMAGE_JPEG)
//             .body(imageData)
//     }
// }

// // 創建一個 ImageService 來處理從 MariaDB 中取得圖片的邏輯
// @Service
// class ImageService(private val imageRepository: ImageRepository) {

//     fun getImageDataById(id: Long): ByteArray {
//         val image = imageRepository.findById(id)
//         // 在這裡實作從資料庫中取得圖片資料的邏輯
//         // 返回圖片的 byte array
//     }
// }

// // 創建一個 ImageRepository 來處理與 MariaDB 互動的邏輯
// @Repository
// interface ImageRepository : JpaRepository<Image, Long> {
//     // 在這裡可以定義取得圖片資料的方法
// }
